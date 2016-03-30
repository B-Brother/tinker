/*
 * Copyright 2012 The Netty Project
 *
 * The Netty Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package com.alibaba.tinker.register.connect.save;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.tinker.rc.mapper.ConfigMapper;
import com.alibaba.tinker.rc.object.ConfigDo;
import com.alibaba.tinker.register.protocol.RegisterProtocol;
 
import io.netty.channel.ChannelHandlerContext; 
import io.netty.channel.SimpleChannelInboundHandler;
 
public class RegisterHandler extends SimpleChannelInboundHandler<String> {

	private ConfigMapper registerCenterMapper;
	
	public RegisterHandler(){
    	ApplicationContext context = new ClassPathXmlApplicationContext("classpath:beans.xml");
    	registerCenterMapper = (ConfigMapper) context.getBean("registerInterfaceMapper");
	}
	
    @Override
    public void channelRead0(ChannelHandlerContext ctx, String msg) {
		JSONObject json = JSONObject.parseObject(msg); 
		
    	String type = json.getString("type"); 
    	if(type.equals("consumer")){
        	System.out.println("注册中心:收到了consumer消息"); 
    		doConsumer(ctx, json);
    	}else if(type.equals("publish")){
        	System.out.println("注册中心:收到了publish消息"); 
    		doPublisher(json); 
    	}
    } 
    
    public void doConsumer(ChannelHandlerContext ctx, JSONObject json){  
    	String serviceName = json.getString("serviceName");
    	List<ConfigDo> rcList = registerCenterMapper.getInterfaceListByInterfaceName(serviceName);
    	
    	List<String> addressList = new ArrayList<String>();
    	if(rcList != null && rcList.size() != 0){
    		for(ConfigDo rc : rcList){
    			if(!addressList.contains(rc.getProviderAddress())){
    				addressList.add(rc.getProviderAddress());
    			}
    		}
    	}
    	
    	JSONObject returnJson = new JSONObject();
    	returnJson.put("type", "consumer");
    	returnJson.put("serviceName", serviceName);
    	returnJson.put("addressList", addressList);
    	
    	ctx.writeAndFlush(returnJson.toJSONString());
    }

    public void doPublisher(JSONObject json){   
    	RegisterProtocol registerProtocol = new RegisterProtocol(json);
    	
    	ConfigDo registerInterface = new ConfigDo();
    	registerInterface.setAppName("unname");
    	registerInterface.setCreator("system");
    	registerInterface.setGmtCreate(new Date());
    	registerInterface.setGmtModified(new Date());
    	registerInterface.setInterfaceName(registerProtocol.getInterfaceName());
    	registerInterface.setInterfaceVersion(registerProtocol.getVersion());
    	registerInterface.setIsDeleted("n");
    	registerInterface.setModified("system");
    	registerInterface.setProviderAddress(registerProtocol.getTargetAddress());
    	registerInterface.setPublishTime(new Date());
    	registerInterface.setSerializableType("HESSIAN4");
    	registerInterface.setStatus("NEW");
    	registerInterface.setTimeout(registerProtocol.getTimeout());
    	registerInterface.setTinkerVersion("0.1");
    	
    	registerCenterMapper.insertRegisterInterface(registerInterface);
    	
    	System.out.println("注册中心:服务注册完成。");
    }
}






