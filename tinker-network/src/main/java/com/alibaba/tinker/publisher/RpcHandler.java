package com.alibaba.tinker.publisher;

import java.io.InputStream;
import java.lang.reflect.Method;

import java.util.Properties;

import com.alibaba.tinker.protocol.HessianHelper;
import com.alibaba.tinker.protocol.ProtocolParser;
import com.alibaba.tinker.protocol.request.TinkerRequestDetail;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext; 
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * 处理服务端接收到的请求。
 * 
 * @author yingchao.zyc
 *
 */
public class RpcHandler extends ChannelInboundHandlerAdapter {
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		System.out.println("服务提供者:通道已经激活。");
	} 
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		System.out.println("服务提供者:收到客户端的远程方法调用请求。");
		TinkerRequestDetail request = ProtocolParser.parseTinkerRequest((ByteBuf) msg);
		 
		InputStream in = ClassLoader.getSystemResourceAsStream("provider.properties");
		Properties p = new Properties();
		p.load(in);
		
		String implClass = p.getProperty(request.getServiceName().substring(0, request.getServiceName().indexOf(":")));
		Class clazz = Class.forName(implClass);
	    Object c = clazz.newInstance();
        Method m = clazz.getDeclaredMethod(request.getMethodName(), request.getTypeArray());   
        
        // 第一个最简单的调用只支持无参数的方法调用
        Object result = m.invoke(c, request.getValueArray()); 
        
        // 无返回值
        if(result == null){
        	return;
        }

        byte[] resultBytes = HessianHelper.serialize(result);
        Channel channel = ctx.channel(); 
        
        channel.write(resultBytes);
	}
	 
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        // Close the connection when an exception is raised.
        cause.printStackTrace();
        ctx.close();
    }
}
