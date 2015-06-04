package com.alibaba.tinker.invoke.singleparam;

import java.util.Date;

import com.alibaba.tinker.client.Client;
import com.alibaba.tinker.publisher.Publisher; 
import com.alibaba.tinker.service.response.HelloShortBoxingReturnService; 

public class ShortBoxingReturnInvoke {
	public static void main(String[] args) {
		// 启动Provider
		Publisher publisher = new Publisher("com.alibaba.tinker.service.response.HelloShortBoxingReturnService:1.0.0.dev");
		publisher.forRegisterCenter();
		publisher.forRpc();
		 
		// 启动Consumer
		Client consumer = new Client();
		consumer.setServiceName("com.alibaba.tinker.service.response.HelloShortBoxingReturnService:1.0.0.dev"); 
		consumer.init();
		
		HelloShortBoxingReturnService helloShortBoxingReturnService = (HelloShortBoxingReturnService) consumer.getObject();
		Short result = helloShortBoxingReturnService.call(new Date());
		System.out.println("调用远程方法收到返回值-->" + result); 
	}
}