package com.alibaba.tinker.baseinvoke;
 
import com.alibaba.tinker.consumer.Consumer;
import com.alibaba.tinker.publisher.Publisher; 
import com.alibaba.tinker.service.HelloBooleanService; 

/**
 * 单个参数，无返回值时候的调用情况。
 * 
 * @author beckham
 *
 */
public class VoidReturnSingleObjectArgumentInvoke {
	public static void main(String[] args) {
		// 启动Provider
		Publisher publisher = new Publisher("com.alibaba.tinker.service.HelloBooleanService:1.0.0.dev");
		publisher.forRegisterCenter();
		publisher.forRpc();
		 
		// 启动Consumer
		Consumer consumer = new Consumer();
		consumer.setServiceName("com.alibaba.tinker.service.HelloBooleanService:1.0.0.dev"); 
		consumer.init();
		
		HelloBooleanService helloBooleanService = (HelloBooleanService) consumer.getObject(); 
		helloBooleanService.call(true);	
	}
}
