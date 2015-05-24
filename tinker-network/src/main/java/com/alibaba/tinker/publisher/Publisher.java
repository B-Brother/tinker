package com.alibaba.tinker.publisher; 

import com.alibaba.tinker.threads.ThreadPoolManager;
import com.alibaba.tinker.util.Host;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public final class Publisher { 
	
    private String serviceName;
     
    public Publisher(String serviceName) { 
		this.serviceName = serviceName;
	}
 
    public void forRegisterCenter(){ 
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
		              
		                     p.addLast(new ObjectEncoder(),
		                               new ObjectDecoder(ClassResolvers.cacheDisabled(null)),
		                               new PublisherHandler(serviceName));
		                 }
		             });

		            ChannelFuture f = b.connect(Host.RC_HOST, 8007).sync();
		 
		            f.channel().closeFuture().sync();
		        } catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
		            // Shut down the event loop to terminate all threads.
		            group.shutdownGracefully();
		        } 
			}
		}).start();
        
        // 将线程池的初始化放到这里很明显不合适。但是由于初始时候和RC交互的协议设计的很丑陋，
        // so，先放到这里，后边调整。 
        // PS: 这个线程池的各个size后续也需要做到可配置
        ThreadPoolManager threadPoolManager = ThreadPoolManager.getInstance();
        threadPoolManager.allocateThreadPool(serviceName, 10, 30);
    }
    
    /**
     * 打开12200端口，供消费者来调用
     */
    public void forRpc(){  
        new Thread(new Runnable() {
			
			@Override
			public void run() {

		        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
		        EventLoopGroup workerGroup = new NioEventLoopGroup();
		        try {
		            ServerBootstrap b = new ServerBootstrap();
		            b.group(bossGroup, workerGroup)
		             .channel(NioServerSocketChannel.class)
		             .option(ChannelOption.SO_BACKLOG, 100)
		             .handler(new LoggingHandler(LogLevel.INFO))
		             .childHandler(new ChannelInitializer<SocketChannel>() {
		                 @Override
		                 public void initChannel(SocketChannel ch) throws Exception {
		                     ChannelPipeline p = ch.pipeline(); 
		                     
		                     p.addLast(new RpcHandler());
		                 }
		             });

		            // Start the server.
		            ChannelFuture f = b.bind(12200).sync();

		            // Wait until the server socket is closed.
		            f.channel().closeFuture().sync();
		        } catch (Exception e) { 
					e.printStackTrace();
				} finally {
		            // Shut down all event loops to terminate all threads.
		            bossGroup.shutdownGracefully();
		            workerGroup.shutdownGracefully();
		        }
			}
		}).start();
    } 
}
