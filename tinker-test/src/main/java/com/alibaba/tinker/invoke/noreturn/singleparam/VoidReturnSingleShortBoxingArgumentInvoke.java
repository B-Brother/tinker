package com.alibaba.tinker.invoke.noreturn.singleparam;
 
import com.alibaba.tinker.client.Client;
import com.alibaba.tinker.publisher.Publisher; 
import com.alibaba.tinker.service.HelloShortBoxingService; 

/**
 * 单个参数，无返回值时候的调用情况。
 * 
 * @author beckham
 *
 */
public class VoidReturnSingleShortBoxingArgumentInvoke {
	public static void main(String[] args) {
		// 启动Provider
		Publisher publisher = new Publisher("com.alibaba.tinker.service.HelloShortBoxingService:1.0.0.dev");
		publisher.forRegisterCenter();
		publisher.forRpc();
		 
		// 启动Consumer
		Client consumer = new Client();
		consumer.setServiceName("com.alibaba.tinker.service.HelloShortBoxingService:1.0.0.dev"); 
		consumer.init();
		
		HelloShortBoxingService helloShortBoxingService = (HelloShortBoxingService) consumer.getObject(); 
		Short s = 23;
		helloShortBoxingService.call(s);	
	}
}
