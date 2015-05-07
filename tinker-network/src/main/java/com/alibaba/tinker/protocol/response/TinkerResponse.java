package com.alibaba.tinker.protocol.response;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * 远程方法返回
 * 
 * @author yingchao.zyc
 *
 */
public class TinkerResponse {
	private String tinkerVersion;
	
	private String protocolType;
	
	private String status;

	private String serializationType;
	  
	private Object responseData;
  
	// countdown数据
	private CountDownLatch countDownLatch = new CountDownLatch(1); 
	
	/**
	 * 数据回写
	 * 
	 * @param responseData
	 */
	public void putResponseData(Object responseData){
		this.responseData = responseData;
		
		// 数据减少，await释放
		countDownLatch.countDown();
	}

	/**
	 * 获取response数据
	 * 
	 * @return
	 */
	public Object getData(){
		try {
			countDownLatch.await(3, TimeUnit.SECONDS);
		} catch (InterruptedException e) { 
			e.printStackTrace();
		} 
		 
		return responseData;
	}

	public String getTinkerVersion() {
		return tinkerVersion;
	}

	public void setTinkerVersion(String tinkerVersion) {
		this.tinkerVersion = tinkerVersion;
	}

	public String getProtocolType() {
		return protocolType;
	}

	public void setProtocolType(String protocolType) {
		this.protocolType = protocolType;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSerializationType() {
		return serializationType;
	}

	public void setSerializationType(String serializationType) {
		this.serializationType = serializationType;
	} 
}





















