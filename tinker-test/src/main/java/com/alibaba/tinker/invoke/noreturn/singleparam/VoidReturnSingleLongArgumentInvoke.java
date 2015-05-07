package com.alibaba.tinker.invoke.noreturn.singleparam;
 
import com.alibaba.tinker.consumer.Consumer;
import com.alibaba.tinker.publisher.Publisher;   
import com.alibaba.tinker.service.HelloLongService;

/**
 * 单个参数，无返回值时候的调用情况。
 * 
 * @author beckham
 *
 */
public class VoidReturnSingleLongArgumentInvoke {
	public static void main(String[] args) {
		// 启动Provider
		Publisher publisher = new Publisher("com.alibaba.tinker.service.HelloLongService:1.0.0.dev");
		publisher.forRegisterCenter();
		publisher.forRpc();
		 
		// 启动Consumer
		Consumer consumer = new Consumer();
		consumer.setServiceName("com.alibaba.tinker.service.HelloLongService:1.0.0.dev"); 
		consumer.init();
		
		HelloLongService helloLongService = (HelloLongService) consumer.getObject(); 
		long a = 45;
		helloLongService.call(a);	
	}
}
