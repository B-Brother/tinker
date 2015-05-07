package com.alibaba.tinker.invoke.singleparam;

import java.util.Date;

import com.alibaba.tinker.consumer.Consumer;
import com.alibaba.tinker.publisher.Publisher; 
import com.alibaba.tinker.service.response.HelloLongBoxingReturnService; 

public class LongBoxingReturnInvoke {
	public static void main(String[] args) {
		// 启动Provider
		Publisher publisher = new Publisher("com.alibaba.tinker.service.response.HelloLongBoxingReturnService:1.0.0.dev");
		publisher.forRegisterCenter();
		publisher.forRpc();
		 
		// 启动Consumer
		Consumer consumer = new Consumer();
		consumer.setServiceName("com.alibaba.tinker.service.response.HelloLongBoxingReturnService:1.0.0.dev"); 
		consumer.init();
		
		HelloLongBoxingReturnService helloLongBoxingReturnService = (HelloLongBoxingReturnService) consumer.getObject();
		long result = helloLongBoxingReturnService.call(new Date());
		System.out.println("调用远程方法收到返回值-->" + result); 
	}
}