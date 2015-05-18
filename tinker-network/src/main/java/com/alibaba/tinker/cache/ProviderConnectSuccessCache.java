package com.alibaba.tinker.cache;

import java.util.HashMap; 

import com.alibaba.tinker.future.ProviderConnectSuccessFuture;

public class ProviderConnectSuccessCache {
	private static ProviderConnectSuccessCache cache = null;
	
	// 地址缓存池。 要调用的方法的地址
	private static HashMap<String, ProviderConnectSuccessFuture> successMap = new HashMap<String, ProviderConnectSuccessFuture>();
	 
	private ProviderConnectSuccessCache(){}
	 
	public static ProviderConnectSuccessCache getInstance(){
		if(cache == null){
			cache = new ProviderConnectSuccessCache();
		}
		
		return cache;
	}
	
	public void put(String key, ProviderConnectSuccessFuture value){
		successMap.put(key, value);
	}
	
	public ProviderConnectSuccessFuture get(String key){
		return successMap.get(key);
	}
}
