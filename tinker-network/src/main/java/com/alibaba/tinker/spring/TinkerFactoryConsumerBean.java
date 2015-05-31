package com.alibaba.tinker.spring;

import io.netty.bootstrap.Bootstrap; 
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

import java.lang.reflect.Proxy; 
import java.util.List; 

import org.springframework.beans.factory.FactoryBean;

import com.alibaba.tinker.cache.ChannelCache;
import com.alibaba.tinker.cache.ProviderAddressCache;
import com.alibaba.tinker.cache.ProviderConnectSuccessCache;
import com.alibaba.tinker.cache.RegisterSuccessCache;
import com.alibaba.tinker.ex.TinkerConnectException;
import com.alibaba.tinker.future.ProviderConnectSuccessFuture;
import com.alibaba.tinker.future.RegisterSuccessFuture;
import com.alibaba.tinker.handler.ConsumerHandler;
import com.alibaba.tinker.handler.RegisterCenterHandler;
import com.alibaba.tinker.metadata.ServiceMetadata;
import com.alibaba.tinker.util.Host;

@SuppressWarnings("rawtypes")
public class TinkerFactoryConsumerBean implements FactoryBean{

	private String serviceName;
	
	private String version;
	
	private String serializableType; 

	@Override
	public Object getObject() throws Exception {
		// 生成代理对象
    	Class clazz;
		try {
			clazz = Class.forName(serviceName);
			
			Class[] interfaceArr = new Class[1];
			interfaceArr[0] = clazz;
			
			ServiceMetadata metadata = new ServiceMetadata();
			metadata.setServiceName(serviceName);
			metadata.setVersion(version);
			metadata.setSerializableType(serializableType);
			 
			Object proxyObj = Proxy.newProxyInstance(
	                this.getClass().getClassLoader(),  
	                interfaceArr,   
	                new TinkerProviderProxy(metadata)    
	                );
			
			return proxyObj;
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;  
	}

	@Override
	public Class getObjectType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}
	
	public void init(){ 
		buildConnection2RegisterCenter(serviceName);
		
		// 判断RC是否已经连接完成
		boolean isRcConnectReady = RegisterSuccessCache.getInstance().get(serviceName).get();
    	if(isRcConnectReady){ 
    		buildConnection2Provider(serviceName);    		
    	}else{
    		throw new TinkerConnectException("客户端:连接注册中心异常，serviceName=" + serviceName);
    	}
    	
    	System.out.println("客户端:" + serviceName + "已经初始化连接完成。");
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
		              
		                     p.addLast(
		                             new ObjectEncoder(),
		                             new ObjectDecoder(ClassResolvers.cacheDisabled(null)),
		                    		 new RegisterCenterHandler(serviceName));
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
		ProviderConnectSuccessFuture future = new ProviderConnectSuccessFuture();
		
		ProviderConnectSuccessCache.getInstance().put(serviceName, future); 
		 
    	final List<String> providerIPList = ProviderAddressCache.getInstance().get(serviceName).get();
		
    	new Thread(new Runnable() {
			
			@Override
			public void run() {
		    	   
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
		                         
		                         p.addLast(new ConsumerHandler());
		                     }
		                 });

		                
		                // 去连接每一个服务提供者的12200端口
		                ChannelFuture f = b.connect(Host.RC_HOST, 12200).sync();  
		                 
		                // 客户端把每个已经连接的Channel都缓存起来，
		                ChannelCache.getInstance().put(serviceName, f.channel());
		                 
		            } catch (InterruptedException e) { 
		    			e.printStackTrace(); 
					}  
				}
				
				ProviderConnectSuccessFuture connectFuture = ProviderConnectSuccessCache.getInstance().get(serviceName);
                connectFuture.putStatus(true);
			}
		}).start();
    } 

	//- - - - - - - - - - - - - - - - - - - - - - - - - - - -
	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getSerializableType() {
		return serializableType;
	}

	public void setSerializableType(String serializableType) {
		this.serializableType = serializableType;
	}
}
