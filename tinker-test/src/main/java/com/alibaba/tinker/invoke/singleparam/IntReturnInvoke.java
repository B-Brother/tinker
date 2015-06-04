package com.alibaba.tinker.invoke.singleparam;

import java.util.Date;

import com.alibaba.tinker.client.Client;
import com.alibaba.tinker.publisher.Publisher;
import com.alibaba.tinker.service.response.HelloIntReturnService;

public class IntReturnInvoke {
	public static void main(String[] args) {
		// 启动Provider
		Publisher publisher = new Publisher("com.alibaba.tinker.service.response.HelloIntReturnService:1.0.0.dev");
		publisher.forRegisterCenter();
		publisher.forRpc();
		 
		// 启动Consumer
		Client consumer = new Client();
		consumer.setServiceName("com.alibaba.tinker.service.response.HelloIntReturnService:1.0.0.dev"); 
		consumer.init();
		
		HelloIntReturnService helloIntReturnService = (HelloIntReturnService) consumer.getObject();
		int result = helloIntReturnService.call(new Date());
		System.out.println("调用远程方法收到返回值-->" + result); 
	}
}
