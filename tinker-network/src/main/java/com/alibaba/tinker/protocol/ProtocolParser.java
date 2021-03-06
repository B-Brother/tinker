package com.alibaba.tinker.protocol;
 
import java.util.HashMap;

import io.netty.buffer.ByteBuf;

import com.alibaba.tinker.constants.TypeConstants;
import com.alibaba.tinker.ex.TinkerProtocolParseException; 
import com.alibaba.tinker.holder.ResponseHolder;
import com.alibaba.tinker.protocol.request.TinkerRequest;
import com.alibaba.tinker.protocol.request.TinkerRequestDetail;
import com.alibaba.tinker.protocol.response.TinkerResponse;
import com.alibaba.tinker.util.NumberUtil;

import static com.alibaba.tinker.constants.ProtocolConstants.*;

public class ProtocolParser {
	/**
	 *  第1-8个字节:    magicNumber: B-TINKER (my name :) )
		第9-14个字节:   客户端TINKER版本(1.0.3)
		第15个字节:     类型( 01:request, 02:response, 03: heartBeat, 04: ipRequest， 05: ipResponse )
		第16个字节:     序列化方式 {01: java原生, 02:Hessian2, 03:Hessian3, 04:Hessian4,  11: kyo,  21:protobuf} 
		第25-28个字节:  自增ID. 标识唯一调用
		
		第25个字节:     描述服务长度字节，即0-255，服务描述长度最大只能到255。
		第26个字节:		描述方法长度字节，即0-255，方法字符串长度最大只能到255。
		第27个字节:     描述方法参数个数，即0-255，参数最多只能255个
		
		根据第27个字节的情况，假如第27个字节的十进制转换是2
		第28个字节:     第一个参数类型的字符长度，如long是4，boolean是7，com.alibaba.bogda.ResourceDTO是29，同理，不可大于255
		第29个字节:	第二个参数类型的字符长度
		
		假如第一个参数类型是long，第二个参数类型是boolean
		第30个字节:	x09，为什么是9因为要考虑负数的情况，需要多补上一位
		第31个字节:     x01  boolean类型不需要考虑正负的问题
		
		假如请求的接口叫com.alibaba.ceres.BpmUserService:1.0.daily(42),调用的方法叫getUser(7)
		
		第32-73个字节:  来保存com.alibaba.ceres.BpmUserService:1.0.daily这个字符串
		第74-80个字节:  来保存getUser这个字符串
		
		假如第一个参数的类型是long, 第二个参数是boolean
		
		第81-85个字节:  字符串long
		第86-92个字节:  字符串boolean
		
		第93-101个字节:　序列化后的long类型数字，比如33209L
		第102个字节:	boolean类型值。T or F.
		
		第103-114个字节:  额外信息传入的长度。一般来说额外信息是map类型传入的。由于2个字节，最长长度为65535.
		
		假如消息的长度是100.
		
		第115-214个字节: 额外信息。 

	 * @param buf
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static TinkerRequestDetail parseTinkerRequest(ByteBuf buf){ 
		ByteBuf tempBuf = null;
		TinkerRequestDetail detail = new TinkerRequestDetail();
		
		// 魔数
		tempBuf = buf.readBytes(8);
		String magicNumberString = new String(tempBuf.array());
		if(!magicNumberString.equals(MAGIC_NUMBER)){
			throw new TinkerProtocolParseException("Tinker调用请求反序列化异常! magicNumber=" + magicNumberString);
		}
		detail.setMagicNumber(magicNumberString);
		
		// 客户端Tinker版本
		tempBuf = buf.readBytes(6);
		String clientTinkerVersion = new String(tempBuf.array());
		detail.setTinkerVersion(clientTinkerVersion);
		
		// 请求类型 
		int requestType = buf.readByte();
		if(requestType != PROTOCOL_TYPE_REQUEST_CODE){
			throw new TinkerProtocolParseException("Tinker调用请求反序列化异常! requestType=" + requestType);
		} 
		
		// 序列化类型
		int serializableType = buf.readByte();
		if(!serializableCodeList.contains(serializableType)){
			throw new TinkerProtocolParseException("Tinker调用请求反序列化异常! serializableType=" + serializableType);
		}
		detail.setSerilizableType("HESSIAN4");
		 
		// requestID
		tempBuf = buf.readBytes(8); 
		long key = NumberUtil.byteToLong(tempBuf.array());
		detail.setRequestId(key);
		
		// 服务长度字节数 
		int serviceLength = buf.readByte();
		if(serviceLength <= 0){
			throw new TinkerProtocolParseException("Tinker调用请求反序列化异常! serviceLength=" + serviceLength);
		}
		
		// 方法长度字节数 
		int methodLength = buf.readByte();
		if(methodLength <= 0){
			throw new TinkerProtocolParseException("Tinker调用请求反序列化异常! methodLength=" + methodLength);
		}
		
		// 方法参数个数 
		int methodParamsCount = buf.readByte();
		if(methodParamsCount < 0){
			throw new TinkerProtocolParseException("Tinker调用请求反序列化异常! methodParamsCount=" + methodParamsCount);
		}
		
		// 保存方法签名各个参数类型的字符串长度 
		int[] methodByteLengthArray = new int[methodParamsCount];
		// 保存方法签名各个参数类型所占据的字节数，比如boolean是1，long是9(考虑正负)，int是5(考虑正负)
		int[] methodParamValueLengthArray = new int[methodParamsCount];
		// 保存方法签名中参数类型的字符串。如直接保存了字符串string， int，long。
		@SuppressWarnings("rawtypes")
		Class[] methodParamTypeArray = new Class[methodParamsCount];
		// 保存了参数类型。
		Object[] methodParamValueArray = new Object[methodParamsCount];
		
		// 用来保存方法参数的类型，以及方法的参数值。
		if(methodParamsCount > 0){ 
			// 首先算出每个方法的参数类型的字符串表达占据了几个字节
			for (int i = 0; i < methodParamsCount; i++) {
				methodByteLengthArray[i] = buf.readByte();
			}
			
			// 然后再算出每个方法的参数类型值实际占据的字节数, 这个值不需要我们自己算，客户端算好传过来
			for (int i = 0; i < methodParamsCount; i++) {
				methodParamValueLengthArray[i] = NumberUtil.byte4ToInt(buf.readBytes(4).array(), 0);
			}
		}
		
		// 调用服务名
  		tempBuf = buf.readBytes(serviceLength);
		String serviceName = new String(tempBuf.array());
		detail.setServiceName(serviceName);
		
		// 调用服务方法名
		tempBuf = buf.readBytes(methodLength);
		String methodName = new String(tempBuf.array());
		detail.setMethodName(methodName);
		
		if(methodParamsCount > 0){ 
			for (int i = 0; i < methodParamsCount; i++) {
				tempBuf = buf.readBytes(methodByteLengthArray[i]);
				
				String type = new String(tempBuf.array());
				if(TypeConstants.easyTypeMapping.get(type) != null){
					methodParamTypeArray[i] = TypeConstants.easyTypeMapping.get(type);
				} else {
					try {
						methodParamTypeArray[i] = Class.forName(type);
					} catch (ClassNotFoundException e) { 
						e.printStackTrace();
					}
				} 
			}
			
			// 计算方法的参数值。
			for (int i = 0; i < methodParamsCount; i++) {
				tempBuf = buf.readBytes(methodParamValueLengthArray[i]);
				
				methodParamValueArray[i] = HessianHelper.deserialize(tempBuf.array()); 
			}
		}
		
		// 额外的属性信息长度
		tempBuf = buf.readBytes(4); 
		int attributeLength = NumberUtil.byte4ToInt(tempBuf.array(), 0);
		HashMap<String, Object> attributeMap = new HashMap<String, Object>() ;
		
		if(attributeLength > 0){
			tempBuf = buf.readBytes(attributeLength);  
			attributeMap = (HashMap<String, Object>) HessianHelper.deserialize(tempBuf.array()); 
		}
		
		detail.setTypeArray(methodParamTypeArray);
		
		// 观察类型和值的匹配程度，如果遇到short和byte则强行转型value。
		// 这样做的目的主要是为了解决Hessian不支持直接序列化byte和short类型以及char类型 
		for (int i = 0; i < methodParamTypeArray.length; i++) {
			String typeName = methodParamTypeArray[i].getName();
			
			if(typeName.equals("short") || typeName.equals("java.lang.Short")){
				methodParamValueArray[i] = (short)(int)methodParamValueArray[i];
			}  
			if(typeName.equals("byte") || typeName.equals("java.lang.Byte")){
				methodParamValueArray[i] = (byte)(int)methodParamValueArray[i];
			}
			if(typeName.equals("char") || typeName.equals("java.lang.Character")){
				// 我们认为当类型是char，但是实际的value是字符串的时候。直接取字符串的第一个char就好了。
				char v = methodParamValueArray[i].toString().charAt(0);
				methodParamValueArray[i] = v;
			}
		}
		
		detail.setValueArray(methodParamValueArray);
		detail.setMap(attributeMap);
		
		return detail;
	} 
	 
	public static TinkerResponse parseTinkerResponse(ByteBuf buf){ 
		ByteBuf tempBuf = null;
		TinkerResponse response = new TinkerResponse();
		
		// 魔数
		tempBuf = buf.readBytes(8);
		String magicNumberString = new String(tempBuf.array());
		if(!magicNumberString.equals(MAGIC_NUMBER)){
			throw new TinkerProtocolParseException("Tinker调用请求反序列化异常! magicNumber=" + magicNumberString);
		} 
		
		// 客户端Tinker版本
		tempBuf = buf.readBytes(6);
		String clientTinkerVersion = new String(tempBuf.array());
		  
		// 请求类型 
		int requestType = buf.readByte();
		if(requestType != PROTOCOL_TYPE_RESPONSE_CODE){
			throw new TinkerProtocolParseException("Tinker调用请求反序列化异常! requestType=" + requestType);
		} 
		response.setProtocolType("");
		
		// 序列化状态
		int status = buf.readByte(); 
		
		// 序列化类型
		int serializableType = buf.readByte();
		if(!serializableCodeList.contains(serializableType)){
			throw new TinkerProtocolParseException("Tinker调用请求反序列化异常! serializableType=" + serializableType);
		}
		response.setSerializationType("HESSIAN4");
		 
		// requestID
		tempBuf = buf.readBytes(8); 
		long key = NumberUtil.byteToLong(tempBuf.array());
		
		response.setStatus("SUCCESS");
		 
		
		int typeLength = buf.readByte();
		
		tempBuf = buf.readBytes(typeLength);
		String typeName = new String(tempBuf.array());
		 
		tempBuf = buf.readBytes(4); 
		int dataLength = NumberUtil.byte4ToInt(tempBuf.array(), 0);
		
		tempBuf = buf.readBytes(dataLength);
		Object result = HessianHelper.deserialize(tempBuf.array());
		
		if(typeName.equals("short") || typeName.equals("java.lang.Short")){
			result = (short)(int)result;
		}  
		if(typeName.equals("byte") || typeName.equals("java.lang.Byte")){
			result = (byte)(int)result;
		}
		if(typeName.equals("char") || typeName.equals("java.lang.Character")){
			// 我们认为当类型是char，但是实际的value是字符串的时候。直接取字符串的第一个char就好了。
			char v = result.toString().charAt(0);
			result = v;
		}
		
		// 这里实现需要调整 TODO
		// 拿到Response
    	TinkerResponse responseKeep = ResponseHolder.getInstance().getResponse(key);
    	responseKeep.putResponseData(result);
		return response;
	}	
}






 