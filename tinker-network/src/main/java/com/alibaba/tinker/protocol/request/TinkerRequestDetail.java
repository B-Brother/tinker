package com.alibaba.tinker.protocol.request;

import java.util.HashMap;
import java.util.Map;

public class TinkerRequestDetail {
	private String magicNumber;
	
	private String tinkerVersion; 
	
	private String serilizableType;
	
	private long requestId;
	
	private String serviceName;
	
	private String methodName;
	
	@SuppressWarnings("rawtypes")
	private Class[] typeArray;
	
	private Object[] valueArray;
	
	private Map<String, Object> map = new HashMap<String, Object>();

	public String getMagicNumber() {
		return magicNumber;
	}

	public void setMagicNumber(String magicNumber) {
		this.magicNumber = magicNumber;
	}

	public String getTinkerVersion() {
		return tinkerVersion;
	}

	public void setTinkerVersion(String tinkerVersion) {
		this.tinkerVersion = tinkerVersion;
	}

	public String getSerilizableType() {
		return serilizableType;
	}

	public void setSerilizableType(String serilizableType) {
		this.serilizableType = serilizableType;
	}

	public long getRequestId() {
		return requestId;
	}

	public void setRequestId(long requestId) {
		this.requestId = requestId;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public Class[] getTypeArray() {
		return typeArray;
	}

	public void setTypeArray(Class[] typeArray) {
		this.typeArray = typeArray;
	}

	public Object[] getValueArray() {
		return valueArray;
	}

	public void setValueArray(Object[] valueArray) {
		this.valueArray = valueArray;
	}

	public Map<String, Object> getMap() {
		return map;
	}

	public void setMap(Map<String, Object> map) {
		this.map = map;
	} 
}
