package com.alibaba.tinker.handler;
    
import com.alibaba.tinker.protocol.ProtocolParser; 
import com.alibaba.tinker.protocol.request.TinkerRequest;

import io.netty.buffer.ByteBuf;  
import io.netty.channel.ChannelHandlerContext; 
import io.netty.channel.ChannelInboundHandlerAdapter; 

public class ConsumerHandler extends ChannelInboundHandlerAdapter { 
	
	private TinkerRequest request;
	
	public ConsumerHandler(TinkerRequest request) {
		this.request = request;
	}
	
	
    /**
     *  第1-8个字节:    magicNumber: B-TINKER
	 *	第9-14个字节:   提供服务端TINKER版本(1.0.3)
	 *	第15个字节:     类型( 01:request, 02:response, 03: heartBeat )
	 *	第16个字节:	      响应状态( 01:success,  02:exception, 03:error )
	 *	第17个字节:     序列化方式 {01: java原生, 02:Hessian2, 03:Hessian3, 04:Hessian4,  11: kyo,  21:protobuf}
	 *	第16-17个字节:  数据信息包长度，最长为65536.
	 *	第18个字节至末尾: 序列化数据
	 *	------------------------------------------------------------------------------
	 *	一个完整的TINKER的RESPONSE协议.
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg)
    		throws Exception {
    	System.out.println("客户端:收到服务提供者的response响应消息。");
    	
    	ProtocolParser.parseTinkerResponse(request, (ByteBuf) msg); 	
    }
}





