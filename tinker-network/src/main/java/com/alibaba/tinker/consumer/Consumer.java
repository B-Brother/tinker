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

import com.alibaba.tinker.handler.RegisterCenterHandler;
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
}









