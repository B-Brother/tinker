package com.alibaba.tinker.cache;

import java.util.HashMap;

import com.alibaba.tinker.future.RegisterSuccessFuture;

public class RegisterSuccessCache {
	private static RegisterSuccessCache cache = null;
	
	// 地址缓存池。 要调用的方法的地址
	private static HashMap<String, RegisterSuccessFuture> successMap = new HashMap<String, RegisterSuccessFuture>();
	 
	private RegisterSuccessCache(){}
	 
	public static RegisterSuccessCache getInstance(){
		if(cache == null){
			cache = new RegisterSuccessCache();
		}
		
		return cache;
	}
	
	public void put(String key, RegisterSuccessFuture value){
		successMap.put(key, value);
	}
	
	public RegisterSuccessFuture get(String key){
		return successMap.get(key);
	}
}
