package com.alibaba.tinker.main;
 
import com.alibaba.tinker.consumer.Consumer;
import com.alibaba.tinker.publisher.Publisher;
import com.alibaba.tinker.service.HelloWorldService;

public class Main {
	public static void main(String[] args) {
		// 启动Provider
		Publisher publisher = new Publisher("com.alibaba.tinker.service.HelloWorldService:1.0.0.dev");
		publisher.forRegisterCenter();
		publisher.forRpc();
		 
		// 启动Consumer
		Consumer consumer = new Consumer();
		consumer.setServiceName("com.alibaba.tinker.service.HelloWorldService:1.0.0.dev"); 
		consumer.init();
		
		HelloWorldService helloWorldService = (HelloWorldService) consumer.getObject(); 
		helloWorldService.call();	
	}
}
