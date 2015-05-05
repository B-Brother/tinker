package com.alibaba.tinker.handler;
   
import com.alibaba.tinker.ex.TinkerProtocolParseException; 
import com.alibaba.tinker.holder.ResponseHolder;
import com.alibaba.tinker.protocol.HessianHelper;
import com.alibaba.tinker.protocol.request.TinkerRequest;
import com.alibaba.tinker.protocol.response.TinkerResponse;
import com.alibaba.tinker.util.NumberUtil; 

import static com.alibaba.tinker.constants.ProtocolConstants.*;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
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
    	System.out.println("收到服务提供者的response响应消息。");
    	
    	// 根据协议头来进行区分
    	ByteBuf buffer = Unpooled.copiedBuffer((byte[])msg);
    	
    	String magicNumber = new String(buffer.readBytes(8).array());
    	if(!MAGIC_NUMBER.equals(magicNumber)){
    		throw new TinkerProtocolParseException("魔数标志位解析错误，magicNumber=" + magicNumber);
    	}
    	
    	String tinkerVersion = new String(buffer.readBytes(6).array());
    	if(tinkerVersion == null || "".equals(tinkerVersion)){
    		throw new TinkerProtocolParseException("tinker版本号为空，response解析异常");
    	}
    	
    	int type = buffer.readByte();
    	if(type != 2){
    		throw new TinkerProtocolParseException("非标准的返回消息格式, type=" + type);
    	}
    	
    	// 响应状态
    	int status = buffer.readByte(); 
    	
    	// 序列化方式
    	int serType = buffer.readByte();
    
    	// 实际数据包长度
    	int dataLength = NumberUtil.byte4ToInt(buffer.readBytes(2).array(), 0);
    	
    	Object data = HessianHelper.deserialize(buffer.readBytes(dataLength).array());
    	 
    	TinkerResponse response = ResponseHolder.getInstance().getResponse(request.getRequestId());
    	
    	// 先暂时不转换。我想先验证结果....--!
    	response.setProtocolType(type + "");
    	response.setSerializationType(serType + "");
    	response.setStatus(status + "");
    	response.setTinkerVersion(tinkerVersion);

    	// 实际调用结果返回
    	response.putResponseData(data); 	
    }
}





