package com.alibaba.tinker.cache;

import io.netty.channel.Channel;
 



import java.util.ArrayList;
import java.util.HashMap; 
import java.util.List;
import java.util.Random;

/**
 * 保存和客户端建立起来的连接
 * 
 * @author yingchao.zyc
 *
 */
public class ChannelCache {
	private static ChannelCache cache = null;
	
	// 地址缓存池。 要调用的方法的地址
	private HashMap<String, List<Channel>> channelMap = new HashMap<String, List<Channel>>();
	 
	private ChannelCache(){}
	 
	public static ChannelCache getInstance(){
		if(cache == null){
			cache = new ChannelCache();
		}
		
		return cache;
	}
	
	public void put(String key, List<Channel> value){
		channelMap.put(key, value);
	}
	
	public void put(String key, Channel value){
		List<Channel> channelList = channelMap.get(key);
		
		if(channelList == null){
			channelList = new ArrayList<Channel>();
		}
		
		channelList.add(value);
		channelMap.put(key, channelList);
	}
	
	public List<Channel> get(String key){
		return channelMap.get(key);
	}
	
	/**
	 * 随机选择出来一个Channel。供服务器调用
	 * 
	 * @return
	 */
	public Channel select(String serviceName){
		int seed = (int) (Math.random() * channelMap.size()); 
		return channelMap.get(serviceName).get(seed);
	}
}


