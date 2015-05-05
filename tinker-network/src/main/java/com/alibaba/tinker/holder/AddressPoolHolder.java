package com.alibaba.tinker.holder;
 
import java.util.HashMap; 

import com.alibaba.tinker.future.ProviderAddressFuture;

/**
 * 地址缓存池
 * 
 * @author yingchao.zyc
 *
 */
public class AddressPoolHolder {
	
	private final static AddressPoolHolder addressPoolHolder = null;
	
	// 地址缓存池。 要调用的方法的地址
	private static HashMap<String, ProviderAddressFuture> addressMap = new HashMap<String, ProviderAddressFuture>();
	 
	private AddressPoolHolder(){}
	 
	public static AddressPoolHolder getInstance(){
		if(addressPoolHolder == null){
			return new AddressPoolHolder();
		}
		
		return addressPoolHolder;
	}
	
	public ProviderAddressFuture put(String serviceName, ProviderAddressFuture future){
		return addressMap.put(serviceName, future);
	}
	
	public ProviderAddressFuture get(String serviceName){
		return addressMap.get(serviceName);
	}
}
