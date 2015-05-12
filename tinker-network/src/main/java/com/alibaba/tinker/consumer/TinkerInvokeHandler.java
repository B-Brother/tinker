package com.alibaba.tinker.consumer;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;  
import java.util.ArrayList; 
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.alibaba.tinker.cache.ChannelCache;
import com.alibaba.tinker.cache.ServiceAddressCache;
import com.alibaba.tinker.constants.ProtocolConstants;
import com.alibaba.tinker.ex.TinkerWithoutPublisherException;
import com.alibaba.tinker.generator.UUIDGenerator;
import com.alibaba.tinker.handler.ConsumerHandler;
import com.alibaba.tinker.handler.RegisterCenterHandler;
import com.alibaba.tinker.holder.ResponseHolder; 
import com.alibaba.tinker.protocol.HessianHelper;
import com.alibaba.tinker.protocol.request.TinkerRequest;
import com.alibaba.tinker.protocol.response.TinkerResponse;
import com.alibaba.tinker.util.ArrayUtil;
import com.alibaba.tinker.util.Host; 
import com.alibaba.tinker.util.NumberUtil;

/**
 * 所有客户端的调用入口。客户端启动后所有的方法都经过这里分发。
 * 
 * @author yingchao.zyc
 *
 */
public class TinkerInvokeHandler implements InvocationHandler {
	
	private Consumer consumer;
 
	public TinkerInvokeHandler(Consumer consumer) { 
		this.consumer = consumer;
	}

	/**
	 * 通过反射机制动态执行真实角色的每一个方法
	 */ 
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		try { 
			// build TinkerRequest
			long requestId = UUIDGenerator.getNextKey(); 
			
			TinkerRequest request = new TinkerRequest();
			request.setMethod(method);
			request.setParamValues(args);
			request.setRequestId(requestId);
			request.setSerizaliableType("HESSIAN4");
			request.setServiceName(consumer.getServiceName());
			
			return invoke(request);  
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} 
	}
	
	public Object invoke(TinkerRequest request){ 
    	String serviceName = request.getServiceName(); 
    	
    	// 根据service标识名称去获取服务端提供者地址列表
    	buildConnection2RegisterCenter(serviceName);
    	
		// 开发版本，先等5秒，等服务提供者IP准备好，后续再想更优雅的办法
    	try {
			TimeUnit.SECONDS.sleep(5);
		} catch (InterruptedException e) { 
			e.printStackTrace();
		}
    	  
    	// 和服务端建立连接(这里应该和所有的服务端都建立连接)
    	buildConnection2Provider(serviceName, request);
    	
		// 开发版本，先等5秒，等客户端连接完成
    	try {
			TimeUnit.SECONDS.sleep(5);
		} catch (InterruptedException e) { 
			e.printStackTrace();
		}
    	
    	// 当和服务端建立连接之后，应该有一堆ChannelList了。  
    	// 这时候随机抽取一个Channel, 进行连接。
    	Channel channel = ChannelCache.getInstance().select(serviceName);
    	
    	// 本地调用唯一的标识key。
    	long key = UUIDGenerator.getNextKey(); 
    	ResponseHolder.getInstance().writeResponse(key, new TinkerResponse());
    	
    	// 向这个随机选出来的服务提供者发送远程方法调用数据
    	channel.writeAndFlush(buildInvokeByteInfo(request, key));
    	
    	// 拿到Response
    	TinkerResponse response = ResponseHolder.getInstance().getResponse(key);
    	
    	// 远程数据返回, 用CountDownLatch来控制线程之间的通信
    	return response.getData();
    }
    
    //================================================================================================================
    
    /**
     *  和服务提供者建立连接
     */ 
	private void buildConnection2Provider(final String serviceName, final TinkerRequest request){    	 
    	new Thread(new Runnable() {
			
			@Override
			public void run() {
		    	List<String> providerIPList = ServiceAddressCache.getInstance().get(serviceName);
		    	
		    	// 为什么这时候抛出异常？ 我觉得获取地址应该是初始化就你要做好的事情，现在已经到准备调用阶段了
		    	if(providerIPList == null || providerIPList.size() == 0){
		    		throw new TinkerWithoutPublisherException("无法找到服务提供者, serviceName=" + serviceName);
		    	}
		    	
				for (String providerIp : providerIPList) {
		    		EventLoopGroup group = new NioEventLoopGroup();
		            try {
		            	Bootstrap b = new Bootstrap();
		                b.group(group)
		                 .channel(NioSocketChannel.class)
		                 .option(ChannelOption.TCP_NODELAY, true)
		                 .handler(new ChannelInitializer<SocketChannel>() {
		                     @Override
		                     public void initChannel(SocketChannel ch) throws Exception {
		                         ChannelPipeline p = ch.pipeline();
		                 
		                         //p.addLast(new LoggingHandler(LogLevel.INFO));
		                         p.addLast(new ConsumerHandler(request));
		                     }
		                 });

		                
		                // 去连接每一个服务提供者的12200端口
		                ChannelFuture f = b.connect(Host.RC_HOST, 12200).sync();    
		                
		                // 客户端把每个已经连接的Channel都缓存起来，
		                ChannelCache.getInstance().put(serviceName, f.channel());

		                // Wait until the connection is closed.
		                f.channel().closeFuture().sync(); 
		            } catch (InterruptedException e) { 
		    			e.printStackTrace(); 
					} finally { 
		                group.shutdownGracefully();
		            } 
				}
			}
		}).start();
    }
    
    
    
	/**
	 *  第1-8个字节:    magicNumber: B-TINKER (my name :) )
		第9-14个字节:   客户端TINKER版本(1.0.3)
		第15个字节:     类型( 01:request, 02:response, 03: heartBeat, 04: ipRequest， 05: ipResponse )
		第16个字节:     序列化方式 {01: java原生, 02:Hessian2, 03:Hessian3, 04:Hessian4,  11: kyo,  21:protobuf} 
		第25-28个字节:  自增ID. 标识唯一调用
		
		第25个字节:     描述服务长度字节，即0-255，服务描述长度最大只能到255。
		第26个字节:		描述方法长度字节，即0-255，方法字符串长度最大只能到255。
		第27个字节:     描述方法参数个数，即0-255，参数最多只能255个
		
		根据第27个字节的情况，假如第27个字节的十进制转换是2
		第28个字节:     第一个参数类型的字符长度，如long是4，boolean是7，com.alibaba.bogda.ResourceDTO是29，同理，不可大于255
		第29个字节:	第二个参数类型的字符长度
		
		假如第一个参数类型是long，第二个参数类型是boolean
		第30个字节:	x09，为什么是9因为要考虑负数的情况，需要多补上一位
		第31个字节:     x01  boolean类型不需要考虑正负的问题
		
		假如请求的接口叫com.alibaba.ceres.BpmUserService:1.0.daily(42),调用的方法叫getUser(7)
		
		第32-73个字节:  来保存com.alibaba.ceres.BpmUserService:1.0.daily这个字符串
		第74-80个字节:  来保存getUser这个字符串
		
		假如第一个参数的类型是long, 第二个参数是boolean
		
		第81-85个字节:  字符串long
		第86-92个字节:  字符串boolean
		
		第93-101个字节:　序列化后的long类型数字，比如33209L
		第102个字节:	boolean类型值。T or F.
		
		第103-114个字节:  额外信息传入的长度。一般来说额外信息是map类型传入的。由于2个字节，最长长度为65535.
		
		假如消息的长度是100.
		
		第115-214个字节: 额外信息。 
	 * 
	 * 
	 * 
	 * @param request
	 * @param key
	 * @return
	 */
    private ByteBuf buildInvokeByteInfo(TinkerRequest request, long key){
    	byte[] invokeByte = new byte[0];
    	
    	// 魔数 & 版本
    	invokeByte = ArrayUtil.concat(invokeByte, ProtocolConstants.MAGIC_NUMBER.getBytes());
    	invokeByte = ArrayUtil.concat(invokeByte, ProtocolConstants.TINKER_VERSION.getBytes());
    	
    	// 协议类型: request
    	invokeByte = ArrayUtil.concat(invokeByte, ProtocolConstants.PROTOCOL_TYPE_REQUEST_CODE);
    	
    	// 序列化方式: hessian4
    	invokeByte = ArrayUtil.concat(invokeByte, ProtocolConstants.SERIALIZABLE_TYPE_HESSIAN4_CODE);
    	 
    	// 自增长ID
    	invokeByte = ArrayUtil.concat(invokeByte, NumberUtil.longToByte8(key));
    	
    	// 服务名称字节长度数
    	invokeByte = ArrayUtil.concat(invokeByte, request.getServiceName().length());
    	
    	// 方法长度字节长度数
    	invokeByte = ArrayUtil.concat(invokeByte, request.getMethod().getName().length());
    	
    	// ***方法参数个数, 这个参数很关键，决定了下边几个字节参数的大小顺序***
    	int paramCount = request.getMethod().getGenericParameterTypes().length;
    	invokeByte = ArrayUtil.concat(invokeByte, paramCount);
    	
    	// 参数类型描述字节长度数
    	for (int i = 0; i < paramCount; i++) {
    		invokeByte = ArrayUtil.concat(invokeByte, request.getMethod().getParameterTypes()[i].getName().length());
		}
    	
    	// 类型所占用的类型字节长度数
    	List<byte[]> valueBytesList = new ArrayList<byte[]>();
    	if(paramCount > 0){
	    	Object values[] = request.getParamValues();
	    	
	    	for(Object value : values){
	    		valueBytesList.add(HessianHelper.serialize(value));
	    	} 
	    	for (int i = 0; i < paramCount; i++) { 
	    		invokeByte = ArrayUtil.concat(invokeByte, valueBytesList.get(i).length, true);
			}
    	}
    	 
    	// 保存服务名称的字符串字节数组的表达
    	invokeByte = ArrayUtil.concat(invokeByte, request.getServiceName().getBytes());
    	// 保存服务方法的字符串字节数组的表达
    	invokeByte = ArrayUtil.concat(invokeByte, request.getMethod().getName().getBytes());
    	
    	// 类型的字符串表达
    	for (int i = 0; i < paramCount; i++) {
    		invokeByte = ArrayUtil.concat(invokeByte, request.getMethod().getParameterTypes()[i].getName().getBytes());
		} 
    	
    	// 值的表达
    	for (int i = 0; i < paramCount; i++) {
    		invokeByte = ArrayUtil.concat(invokeByte, valueBytesList.get(i));
		} 

    	byte attributeByte[] = HessianHelper.serialize(request.getAttributeMap());
    	
    	// 额外信息的长度
    	invokeByte = ArrayUtil.concat(invokeByte, NumberUtil.intToByte4(attributeByte.length));
    	
    	// 额外信息的内容
    	invokeByte = ArrayUtil.concat(invokeByte, attributeByte);
    	     
    	return Unpooled.copiedBuffer(invokeByte);
    } 
}







