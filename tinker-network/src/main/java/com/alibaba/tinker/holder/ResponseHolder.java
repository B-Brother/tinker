package com.alibaba.tinker.holder;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.alibaba.tinker.protocol.response.TinkerResponse;

/**
 * 结果储存器，本质是一个map。
 */
public class ResponseHolder {
	
	private static ResponseHolder responseHolder;
	
	private final Map<Long, TinkerResponse> responseMap = new ConcurrentHashMap<Long, TinkerResponse>();

	private ResponseHolder(){
		
	}
	
	public static ResponseHolder getInstance(){
		if(responseHolder == null){
			responseHolder = new ResponseHolder(); 
		}
		
		return responseHolder;
	}
	
	public synchronized Object writeResponse(Long key, TinkerResponse response){
		return responseMap.put(key, response);
	}
	
	public synchronized TinkerResponse getResponse(Long key){
		return responseMap.get(key);
	}
}
