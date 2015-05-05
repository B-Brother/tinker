package com.alibaba.tinker.protocol.request;

import java.lang.reflect.Method;
import java.util.HashMap;

import com.alibaba.tinker.protocol.TinkerProtocol;

/**
 * 根据tinker客户端传过来的请求数据包装映射起来的对象。
 * 
 * @author yingchao.zyc
 *
 */ 
public class TinkerRequest extends TinkerProtocol{
	
	private long requestId;
	
	// 序列化类型。 Hessian? ProtoBuf? kyo? 
	private String serizaliableType;
	 
	// 调用方法名称
	private Method method;
	 
	private String serviceName;
	
	// 方法参数value值。
	private Object[] paramValues;
	 
	// 额外属性
	private HashMap<String, Object> attributeMap = new HashMap<String, Object>();
  
	
	//----------------setter and getter------------------
	
	public long getRequestId() {
		return requestId;
	}

	public void setRequestId(long requestId) {
		this.requestId = requestId;
	}

	public String getSerizaliableType() {
		return serizaliableType;
	}

	public void setSerizaliableType(String serizaliableType) {
		this.serizaliableType = serizaliableType;
	}

	public Method getMethod() {
		return method;
	}

	public void setMethod(Method method) {
		this.method = method;
	}  
 
	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public Object[] getParamValues() {
		return paramValues;
	}

	public void setParamValues(Object[] paramValues) {
		this.paramValues = paramValues;
	}

	public HashMap<String, Object> getAttributeMap() {
		return attributeMap;
	}

	public void setAttributeMap(HashMap<String, Object> attributeMap) {
		this.attributeMap = attributeMap;
	} 
}