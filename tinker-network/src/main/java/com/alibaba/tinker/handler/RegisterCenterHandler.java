package com. alibaba.tinker.handler;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.tinker.cache.ProviderAddressCache; 
import com.alibaba.tinker.future.ProviderAddressFuture;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * 注册中心连接所用的Handler
 * 
 * @author yingchao.zyc
 *
 */
public class RegisterCenterHandler extends SimpleChannelInboundHandler<String> {

	private String serviceName;
	
	public RegisterCenterHandler(String serviceName){
		this.serviceName = serviceName;
	}
	
    @Override
    public void channelActive(ChannelHandlerContext ctx) { 
    	ProviderAddressFuture future = new ProviderAddressFuture();
		
		ProviderAddressCache.getInstance().put(serviceName, future); 
    	
    	
    	// 通道激活时候向RC请求该服务提供者IP列表  
    	JSONObject json = new JSONObject();
    	json.put("serviceName", serviceName); 
    	
    	// 标注是consumer类型。是获取服务提供者的
    	json.put("type", "consumer");
    	 
        ctx.writeAndFlush(json.toJSONString()); 
        System.out.println("客户端:请求服务提供者列表地址信息消息发送完成, json=" + json.toJSONString());
    }
    
    @SuppressWarnings("unchecked")
	@Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg)
    		throws Exception {
    	JSONObject json = JSONObject.parseObject(msg);
    	
    	List<String> ipList = (List<String>) json.get("addressList");
    	if(ipList != null && ipList.size() != 0){
        	System.out.println("客户端:收到服务提供列表。" + ipList); 
    		
    		ProviderAddressFuture future = ProviderAddressCache.getInstance().get(serviceName);
    		future.putResponseData(ipList);
    		
    	}else{
    		// 未能获取到服务地址，这里需要优雅的提示
    		System.out.println(serviceName + "未能在RC找到服务提供者");
    	}
    }
}
