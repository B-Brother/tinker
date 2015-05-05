package com.alibaba.tinker.register.connect.get;

import java.util.ArrayList; 
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.tinker.register.mapper.RegisterInterfaceMapper;
import com.alibaba.tinker.register.object.RegisterInterfaceDo; 

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext; 
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Handler implementation for the echo server.
 */
@Sharable
public class RemoteServerHandler extends SimpleChannelInboundHandler<String> {

    @Override
    public void channelRead0(ChannelHandlerContext ctx, String msg) { 
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:beans.xml");
		
		RegisterInterfaceMapper registerCenterMapper = (RegisterInterfaceMapper) context.getBean("registerInterfaceMapper");
		 
		JSONObject jsonObject = JSON.parseObject(msg.toString());
		
		List<RegisterInterfaceDo> registerInterfaceList = registerCenterMapper
				.getInterfaceListByFullName(jsonObject.getString("interfaceName")+":"+jsonObject.getString("interfaceVersion"));
		
		List<String> addressList = new ArrayList<String>();
		if(!CollectionUtils.isEmpty(registerInterfaceList)){
			for(RegisterInterfaceDo registerInterface : registerInterfaceList){
				addressList.add(registerInterface.getProviderAddress());
			}
		}
    	
		ctx.writeAndFlush(addressList);
    } 
}
