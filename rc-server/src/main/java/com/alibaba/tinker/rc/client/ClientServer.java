package com.alibaba.tinker.rc.client;

import com.alibaba.fastjson.JSONObject;
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

import java.io.UnsupportedEncodingException;
import java.net.InetAddress;

/**
 *
 * 客户端服务器。用来向rc发送数据
 */
public class ClientServer {

    private Channel channel;

    public ClientServer(){
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

                            p.addLast(
                                    new ObjectEncoder(),
                                    new ObjectDecoder(ClassResolvers.cacheDisabled(null)),
                                    new ClientHandler());
                        }
                    });

            // Start the server.
            ChannelFuture f = b.bind(InetAddress.getByName(HostUtil.RC_HOST), HostUtil.RC_PORT).sync();

            channel = f.channel();
        } catch(Exception e) {
            // TODO 这里的异常处理方式不太恰当
            throw new RuntimeException(e);
        }
    }

    /**
     * 发送内容到配置中心
     *
     * @param context
     */
    public RCResponse send(RequestContext context){

        byte[] data = null;
        try {
            data = JSONObject.toJSONString(context).getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            // DO WHAT ?  TODO
        }
        channel.writeAndFlush(data);
    }
}
