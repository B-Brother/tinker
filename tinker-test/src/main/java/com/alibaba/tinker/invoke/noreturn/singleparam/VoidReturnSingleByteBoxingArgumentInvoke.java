package com.alibaba.tinker.invoke.noreturn.singleparam;
 
import com.alibaba.tinker.consumer.Consumer;
import com.alibaba.tinker.publisher.Publisher; 
import com.alibaba.tinker.service.HelloByteBoxingService; 

/**
 * 单个参数，无返回值时候的调用情况。
 * 
 * @author beckham
 *
 */
public class VoidReturnSingleByteBoxingArgumentInvoke {
	public static void main(String[] args) {
		// 启动Provider
		Publisher publisher = new Publisher("com.alibaba.tinker.service.HelloByteBoxingService:1.0.0.dev");
		publisher.forRegisterCenter();
		publisher.forRpc();
		 
		// 启动Consumer
		Consumer consumer = new Consumer();
		consumer.setServiceName("com.alibaba.tinker.service.HelloByteBoxingService:1.0.0.dev"); 
		consumer.init();
		
		HelloByteBoxingService helloByteBoxingService = (HelloByteBoxingService) consumer.getObject(); 
		Byte s = 23;
		helloByteBoxingService.call(s);	
	}
}
