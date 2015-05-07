package com.alibaba.tinker.invoke.noreturn.singleparam;
 
import com.alibaba.tinker.consumer.Consumer;
import com.alibaba.tinker.publisher.Publisher; 
import com.alibaba.tinker.service.HelloWorld3Service;

/**
 * 单个参数，无返回值时候的调用情况。
 * 
 * @author beckham
 *
 */
public class VoidReturnSingleIntArgumentInvoke {
	public static void main(String[] args) {
		// 启动Provider
		Publisher publisher = new Publisher("com.alibaba.tinker.service.HelloWorld3Service:1.0.0.dev");
		publisher.forRegisterCenter();
		publisher.forRpc();
		 
		// 启动Consumer
		Consumer consumer = new Consumer();
		consumer.setServiceName("com.alibaba.tinker.service.HelloWorld3Service:1.0.0.dev"); 
		consumer.init();
		
		HelloWorld3Service helloWorld3Service = (HelloWorld3Service) consumer.getObject(); 
		helloWorld3Service.call(23);	
	}
}
