package com.alibaba.tinker.cache;

import java.util.HashMap;
import java.util.List; 

public class ServiceAddressCache {
	private static ServiceAddressCache cache = null;
	
	// 地址缓存池。 要调用的方法的地址
	private static HashMap<String, List<String>> addressMap = new HashMap<String, List<String>>();
	 
	private ServiceAddressCache(){}
	 
	public static ServiceAddressCache getInstance(){
		if(cache == null){
			cache = new ServiceAddressCache();
		}
		
		return cache;
	}
	
	public void put(String key, List<String> value){
		addressMap.put(key, value);
	}
	
	public List<String> get(String key){
		return addressMap.get(key);
	}
}
