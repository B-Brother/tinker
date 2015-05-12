package com.alibaba.tinker.publisher;
 
import com.alibaba.fastjson.JSONObject;
import com.alibaba.tinker.util.IpAddressUtil;

import io.netty.channel.ChannelHandlerContext; 
import io.netty.channel.SimpleChannelInboundHandler;
 
public class PublisherHandler extends SimpleChannelInboundHandler<String> { 
	
	private String serviceName;
    
    public PublisherHandler(String serviceName) { 
		this.serviceName = serviceName;
	}
	
    @Override
    public void channelActive(ChannelHandlerContext ctx) { 
    	JSONObject json = new JSONObject();
    	json.put("serviceName", serviceName);
    	json.put("address", IpAddressUtil.getHostIp());
    	json.put("connectPort", 12200);
    	json.put("timeout", 3000);
    	
    	// 标注是publish类型。是注册服务的。
    	json.put("type", "publish");
    	 
        ctx.writeAndFlush(json.toJSONString()); 
        System.out.println("服务提供者:请求服务提供地址信息完成, json=" + json.toJSONString());
    }

    @Override
    public void channelRead0(ChannelHandlerContext ctx, String msg) {
        System.out.println("client received:" + msg);
    } 
}
