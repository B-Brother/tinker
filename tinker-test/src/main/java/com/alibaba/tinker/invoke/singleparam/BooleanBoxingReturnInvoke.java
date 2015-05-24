package com.alibaba.tinker.invoke.singleparam;

import java.util.Date;

import com.alibaba.tinker.consumer.Consumer;
import com.alibaba.tinker.publisher.Publisher; 
import com.alibaba.tinker.service.response.HelloBooleanBoxingReturnService; 

public class BooleanBoxingReturnInvoke {
	public static void main(String[] args) {
		// 启动Provider
		Publisher publisher = new Publisher("com.alibaba.tinker.service.response.HelloBooleanBoxingReturnService:1.0.0.dev");
		publisher.forRegisterCenter();
		publisher.forRpc();
		 
		// 启动Consumer
		Consumer consumer = new Consumer();
		consumer.setServiceName("com.alibaba.tinker.service.response.HelloBooleanBoxingReturnService:1.0.0.dev"); 
		consumer.init();
		
		long start = System.currentTimeMillis();
		HelloBooleanBoxingReturnService helloBooleanBoxingReturnService = (HelloBooleanBoxingReturnService) consumer.getObject();
		boolean result = helloBooleanBoxingReturnService.call(new Date());
		System.out.println("调用远程方法收到返回值-->" + result); 
		long end = System.currentTimeMillis();
		System.out.println("本次调用耗时:" + (end - start) + "ms.");
	}
}