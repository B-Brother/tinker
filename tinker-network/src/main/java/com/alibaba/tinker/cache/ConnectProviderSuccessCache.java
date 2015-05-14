package com.alibaba.tinker.cache;

import java.util.HashMap;

import com.alibaba.tinker.future.ConnectProviderSuccessFuture; 

public class ConnectProviderSuccessCache {
	private static ConnectProviderSuccessCache cache = null;
	
	// 地址缓存池。 要调用的方法的地址
	private static HashMap<String, ConnectProviderSuccessFuture> successMap = new HashMap<String, ConnectProviderSuccessFuture>();
	 
	private ConnectProviderSuccessCache(){}
	 
	public static ConnectProviderSuccessCache getInstance(){
		if(cache == null){
			cache = new ConnectProviderSuccessCache();
		}
		
		return cache;
	}
	
	public void put(String key, ConnectProviderSuccessFuture value){
		successMap.put(key, value);
	}
	
	public ConnectProviderSuccessFuture get(String key){
		return successMap.get(key);
	}
}
