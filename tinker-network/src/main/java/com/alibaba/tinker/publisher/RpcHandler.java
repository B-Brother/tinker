package com.alibaba.tinker.publisher;

import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.Properties;

import com.alibaba.tinker.constants.ProtocolConstants;
import com.alibaba.tinker.protocol.HessianHelper;
import com.alibaba.tinker.protocol.ProtocolParser;
import com.alibaba.tinker.protocol.request.TinkerRequestDetail;
import com.alibaba.tinker.util.ArrayUtil;
import com.alibaba.tinker.util.NumberUtil;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
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
        Channel channel = ctx.channel();  
        
        channel.writeAndFlush(buildResponseByteBuf(request, result, m));
	}
	
	/**
	 *  第1-8个字节:    magicNumber: B-TINKER (my name :) )
		第9-14个字节:   提供服务端TINKER版本(1.0.3)
		第15个字节:     类型( 01:request, 02:response, 03: heartBeat )
		第16个字节:     响应状态( 01:success,  02:exception, 03:error )
		第17个字节:     序列化方式 {01: java原生, 02:Hessian2, 03:Hessian3, 04:Hessian4,  11: kyo,  21:protobuf}
		第18-25个字节:  客户端请求ID, requestID。 
		第26-27个字节:  数据信息包长度，最长为65536.
		第28个字节至末尾: 序列化数据
	 * @return
	 */
	private ByteBuf buildResponseByteBuf(TinkerRequestDetail request, Object response, Method m){
		byte[] invokeByte = new byte[0];
    	
    	// 魔数 & 版本
    	invokeByte = ArrayUtil.concat(invokeByte, ProtocolConstants.MAGIC_NUMBER.getBytes());
    	invokeByte = ArrayUtil.concat(invokeByte, ProtocolConstants.TINKER_VERSION.getBytes());
    	
    	// 协议类型: response
    	invokeByte = ArrayUtil.concat(invokeByte, ProtocolConstants.PROTOCOL_TYPE_RESPONSE_CODE);
    	
    	// 返回状态
    	invokeByte = ArrayUtil.concat(invokeByte, ProtocolConstants.PROTOCOL_TYPE_RESPONSE_SUCCESS_CODE);
    	
    	// 序列化方式: hessian4
    	invokeByte = ArrayUtil.concat(invokeByte, ProtocolConstants.SERIALIZABLE_TYPE_HESSIAN4_CODE);
    	 
    	// 自增长ID
    	invokeByte = ArrayUtil.concat(invokeByte, NumberUtil.longToByte8(request.getRequestId())); 
 
    	
		String typeName = m.getReturnType().getName();
    	// 返回的方法类型字符串长度
		invokeByte = ArrayUtil.concat(invokeByte, typeName.length()); 
		
    	// 返回的方法类型字符串表达
		invokeByte = ArrayUtil.concat(invokeByte, typeName.getBytes()); 
    	
    	byte[] responseByte = HessianHelper.serialize(response);
    	
    	// 返回的包长度
    	invokeByte = ArrayUtil.concat(invokeByte, NumberUtil.intToByte4(responseByte.length)); 
    	     
    	// 真正的数据包
    	invokeByte = ArrayUtil.concat(invokeByte, responseByte); 
    	
    	return Unpooled.copiedBuffer(invokeByte);
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
