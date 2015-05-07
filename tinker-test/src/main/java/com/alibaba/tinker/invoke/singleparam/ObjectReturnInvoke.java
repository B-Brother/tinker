package com.alibaba.tinker.invoke.singleparam;
 
import java.util.ArrayList;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.alibaba.tinker.consumer.Consumer;
import com.alibaba.tinker.publisher.Publisher;  
import com.alibaba.tinker.service.dto.TransferDTO;
import com.alibaba.tinker.service.response.HelloObjectReturnService;

public class ObjectReturnInvoke {
	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		// 启动Provider
		Publisher publisher = new Publisher("com.alibaba.tinker.service.response.HelloObjectReturnService:1.0.0.dev");
		publisher.forRegisterCenter();
		publisher.forRpc();
		 
		// 启动Consumer
		Consumer consumer = new Consumer();
		consumer.setServiceName("com.alibaba.tinker.service.response.HelloObjectReturnService:1.0.0.dev"); 
		consumer.init();
		
		HelloObjectReturnService helloObjectReturnService = (HelloObjectReturnService) consumer.getObject();
		Object result = helloObjectReturnService.call();
		
		ArrayList<TransferDTO> transferList = (ArrayList<TransferDTO>) result;
		System.out.println("调用远程方法收到返回值."); 
		for(TransferDTO transfer : transferList){
			System.out.println("content:" + ToStringBuilder.reflectionToString(transfer));
		}
	}
}




