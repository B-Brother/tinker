package com.alibaba.tinker.rc.container;

import com.alibaba.tinker.rc.handler.RequestDispatchHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by yingchao.zyc on 2016/3/25.
 */
public class NettyServerBootstrap {

    private Logger logger = LoggerFactory.getLogger(NettyServerBootstrap.class);

    private final int port = 8848;

    /**
     * 初始化Netty的对外监听服务。
     */
    public NettyServerBootstrap(){
        logger.info("rc server start.");

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

                            //p.addLast(new LoggingHandler(LogLevel.INFO));
                            p.addLast(
                                    new ObjectEncoder(),
                                    new ObjectDecoder(ClassResolvers.cacheDisabled(null)),
                                    new RequestDispatchHandler());
                        }
                    });

            // Start the server.
            ChannelFuture f = b.bind(port).sync();

            System.out.println("rc is running...");

            // Wait until the server socket is closed.
            f.channel().closeFuture().sync();
        } catch(Exception e) {
            // TODO 这里的异常处理方式不太恰当
            throw new RuntimeException(e);
        } finally {
            // Shut down all event loops to terminate all threads.
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
