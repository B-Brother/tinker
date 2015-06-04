package com.alibaba.tinker.spring;

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

import java.lang.reflect.Proxy; 

import org.springframework.beans.factory.FactoryBean;

import com.alibaba.tinker.cache.RegisterSuccessCache; 
import com.alibaba.tinker.ex.TinkerInitException; 
import com.alibaba.tinker.future.RegisterSuccessFuture;  
import com.alibaba.tinker.metadata.ServiceMetadata;
import com.alibaba.tinker.publisher.PublisherHandler;
import com.alibaba.tinker.publisher.RpcHandler;
import com.alibaba.tinker.util.Host;

@SuppressWarnings("rawtypes")
public class TinkerFactoryBean implements FactoryBean {

	private String serviceName;

	private Object target;

	private String version;

	private int timeout;

	private int threadPoolCoreSize;

	private int threadPoolMaxiumSize;

	private String serializableType;
	
	// 这个metadata对象就是包含了上边的所有属性的对象, 感觉从设计上，不是太合理 TODO
	private ServiceMetadata metadata;
	
	@Override
	public Object getObject() throws Exception {
		// 首先做一些基本的判断。
		Class interfaceClazz = Class.forName(serviceName);
		if(interfaceClazz == null){
			throw new TinkerInitException("接口类不存在，serviceName=" + serviceName);
		}
		
		if(!interfaceClazz.isInterface()){
			throw new TinkerInitException("提供的接口类非标准java接口, serviceName=" + serviceName);
		}
		 
		if(!isInterface(target.getClass(), interfaceClazz.getName())){
			throw new TinkerInitException("提供的实现类并非是提供服务的实现类，serviceName=" + serviceName);
		}    
		
    	// 生成代理对象 
		Object proxyObject = null;
		try {
			Class clazz = Class.forName(serviceName);
			
			Class[] interfaceArr = new Class[1];
			interfaceArr[0] = clazz;
			 
			proxyObject = Proxy.newProxyInstance(
	                this.getClass().getClassLoader(),  
	                interfaceArr,   
	                new TinkerProviderProxy(metadata)  
	                );
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		 
		return proxyObject;
	}
	
	/**
	 * 服务端启动需要做两件事情。
	 * 
	 * 1. 和注册中心建立连接。告诉RC，我这边的ip是多少，提供了什么样的服务
	 * 2. 本地打开12200端口，可以让消费者来调用到
	 * 
	 */
	public void init(){
		// step 1
		ServiceMetadata metadata = new ServiceMetadata();
		metadata.setServiceName(serviceName);
		metadata.setVersion(version);
		metadata.setTarget(metadata);
		metadata.setThreadPoolMaxiumSize(threadPoolMaxiumSize);
		metadata.setThreadPoolCoreSize(threadPoolCoreSize); 
		
		this.metadata = metadata;
		
		buildConnection2RegisterCenter(metadata);
		
		// step 2
		startRpc();
		
		System.out.println("服务" + serviceName + "初始化完成。");
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

	// ----------------------private-------------------------
	
	/**
	 * 
	 */
	public void startRpc(){  
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
	
    /**
     *  和注册中心(RC)建立连接
     *  
     *  通道建立时: 发出请求服务地址提供列表消息
     *  RC回应: 地址列表信息，回写到ServiceAddressCache 
     */ 
	private void buildConnection2RegisterCenter(final ServiceMetadata metadata){  
		RegisterSuccessFuture future = new RegisterSuccessFuture();
		
		RegisterSuccessCache.getInstance().put(metadata.getServiceName(), future);
		
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
		                    		 new PublisherHandler(metadata));
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
	 
	public boolean isInterface(Class c, String szInterface) {
		Class[] face = c.getInterfaces();
		for (int i = 0, j = face.length; i < j; i++) {
			if (face[i].getName().equals(szInterface)) {
				return true;
			} else {
				Class[] face1 = face[i].getInterfaces();
				for (int x = 0; x < face1.length; x++) {
					if (face1[x].getName().equals(szInterface)) {
						return true;
					} else if (isInterface(face1[x], szInterface)) {
						return true;
					}
				}
			} 
		}
		if (null != c.getSuperclass()) {
			return isInterface(c.getSuperclass(), szInterface);
		}
		return false;
	}

	// ----------------------setter--------------------------

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public void setTarget(Object target) {
		this.target = target;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	public void setThreadPoolCoreSize(int threadPoolCoreSize) {
		this.threadPoolCoreSize = threadPoolCoreSize;
	}

	public void setThreadPoolMaxiumSize(int threadPoolMaxiumSize) {
		this.threadPoolMaxiumSize = threadPoolMaxiumSize;
	}

	public String getServiceName() {
		return serviceName;
	}

	public String getSerializableType() {
		return serializableType;
	}

	public void setSerializableType(String serializableType) {
		this.serializableType = serializableType;
	}

	public Object getTarget() {
		return target;
	}

	public String getVersion() {
		return version;
	}

	public int getTimeout() {
		return timeout;
	}

	public int getThreadPoolCoreSize() {
		return threadPoolCoreSize;
	}

	public int getThreadPoolMaxiumSize() {
		return threadPoolMaxiumSize;
	}
}
