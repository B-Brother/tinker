package com.alibaba.tinker.cache;

import java.util.HashMap;

import com.alibaba.tinker.future.ProviderAddressFuture;

public class ProviderAddressCache {
	
	private static ProviderAddressCache cache = null;
	
	// 地址缓存池。 要调用的方法的地址
	private static HashMap<String, ProviderAddressFuture> successMap = new HashMap<String, ProviderAddressFuture>();
	 
	private ProviderAddressCache(){}
	 
	public static ProviderAddressCache getInstance(){
		if(cache == null){
			cache = new ProviderAddressCache();
		}
		
		return cache;
	}
	
	public void put(String key, ProviderAddressFuture value){
		successMap.put(key, value);
	}
	
	public ProviderAddressFuture get(String key){
		return successMap.get(key);
	}
}
