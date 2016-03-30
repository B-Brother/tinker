package com.alibaba.tinker.rc.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.charset.Charset;
import java.util.Base64;

public class ClientHandler extends SimpleChannelInboundHandler<String> {

    /**
     * 这里很重要。用来处理所有我们自己发出去的请求的response。
     *
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        // 解码msg到RCResponse对象。

        // 将值回写到信号量，通知response已经返回
    }

    private RCResponse getResponse(String msg){

    }

}















