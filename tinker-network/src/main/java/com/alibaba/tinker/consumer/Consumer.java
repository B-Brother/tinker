package com.alibaba.tinker.consumer;
 
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.lang.reflect.Proxy; 
import java.util.List;

import com.alibaba.tinker.cache.ChannelCache;
import com.alibaba.tinker.cache.RegisterSuccessCache;
import com.alibaba.tinker.cache.ServiceAddressCache;
import com.alibaba.tinker.ex.TinkerWithoutPublisherException;
import com.alibaba.tinker.future.RegisterSuccessFuture;
import com.alibaba.tinker.generator.UUIDGenerator;
import com.alibaba.tinker.handler.ConsumerHandler;
import com.alibaba.tinker.handler.RegisterCenterHandler;
import com.alibaba.tinker.protocol.request.TinkerRequest;
import com.alibaba.tinker.util.Host;
 
public final class Consumer {  
     
    private String serviceName;
    
    // 被代理的远程对象
    private Object proxy;
 
	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	} 

	/**
	 * init初始化需要做的事情如下:
	 * 
	 * 1. 生成代理对象。
	 * 2. 和注册中心(RC)建立起连接
	 */ 
	public void init(){
		buildProxyObject();
		
		buildConnection2RegisterCenter(serviceName);
		
		buildConnection2Provider(serviceName);
    }
    
    public Object getObject(){
    	return proxy;
    }  
    
    // ---- private
    
    @SuppressWarnings("rawtypes")
	private void buildProxyObject(){
    	// 生成代理对象
    	Class clazz;
		try {
			clazz = Class.forName(serviceName.substring(0, serviceName.lastIndexOf(":")));
			
			Class[] interfaceArr = new Class[1];
			interfaceArr[0] = clazz;
			 
			Object proxyObj = Proxy.newProxyInstance(
	                this.getClass().getClassLoader(),  
	                interfaceArr,   
	                new TinkerInvokeHandler(this)    
	                );
			
			proxy = proxyObj;
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
    }
    
    /**
     *  和注册中心(RC)建立连接
     *  
     *  通道建立时: 发出请求服务地址提供列表消息
     *  RC回应: 地址列表信息，回写到ServiceAddressCache
     */ 
	private void buildConnection2RegisterCenter(final String serviceName){  
		RegisterSuccessFuture future = new RegisterSuccessFuture();
		
		RegisterSuccessCache.getInstance().put(serviceName, future);
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
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
		                     p.addLast(new RegisterCenterHandler(serviceName));
		                     }
		                 });
		  
		                ChannelFuture f = b.connect(Host.RC_HOST, 8007).sync(); 
		                
		                RegisterSuccessFuture connectFuture = RegisterSuccessCache.getInstance().get(serviceName);
		                connectFuture.putStatus(true);
		                

		            // Wait until the connection is closed.
		            f.channel().closeFuture().sync(); 
		        } catch (InterruptedException e) { 
					e.printStackTrace();
				} finally { 
		            group.shutdownGracefully();
		        }  
			}
		}).start();
    }
	
	/**
     *  和服务提供者建立连接
     */ 
	private void buildConnection2Provider(final String serviceName){  
		
		// build TinkerRequest
		long requestId = UUIDGenerator.getNextKey(); 
		
		final TinkerRequest request = new TinkerRequest(); 
		request.setRequestId(requestId);
		request.setSerizaliableType("HESSIAN4");
		request.setServiceName(serviceName);
					
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
		                         p.addLast(new ConsumerHandler());
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
}









