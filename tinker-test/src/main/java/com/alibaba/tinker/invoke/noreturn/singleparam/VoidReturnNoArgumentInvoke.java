package com.alibaba.tinker.invoke.noreturn.singleparam;
 
import com.alibaba.tinker.client.Client;
import com.alibaba.tinker.publisher.Publisher;
import com.alibaba.tinker.service.HelloWorldService;

/**
 * 无参数，无返回值时候的调用情况
 * 
 * @author beckham
 *
 */
public class VoidReturnNoArgumentInvoke {
	public static void main(String[] args) {
		// 启动Provider
		Publisher publisher = new Publisher("com.alibaba.tinker.service.HelloWorldService:1.0.0.dev");
		publisher.forRegisterCenter();
		publisher.forRpc();
		 
		// 启动Consumer
		Client consumer = new Client();
		consumer.setServiceName("com.alibaba.tinker.service.HelloWorldService:1.0.0.dev"); 
		consumer.init();
		
		long start = System.currentTimeMillis();
		HelloWorldService helloWorldService = (HelloWorldService) consumer.getObject(); 
		helloWorldService.call();	
		long end = System.currentTimeMillis();
		System.out.println("本次调用耗时:" + (end - start) + "ms.");
	}
}
