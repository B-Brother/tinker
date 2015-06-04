package com.alibaba.tinker.invoke.noreturn.singleparam;
 
import java.util.Date;

import com.alibaba.tinker.client.Client;
import com.alibaba.tinker.publisher.Publisher;  
import com.alibaba.tinker.service.HelloObjectService;
import com.alibaba.tinker.service.dto.TransferDTO;

/**
 * 单个参数，无返回值时候的调用情况。
 * 
 * @author beckham
 *
 */
public class VoidReturnSingleObjectArgumentInvoke {
	public static void main(String[] args) {
		// 启动Provider
		Publisher publisher = new Publisher("com.alibaba.tinker.service.HelloObjectService:1.0.0.dev");
		publisher.forRegisterCenter();
		publisher.forRpc();
		 
		// 启动Consumer
		Client consumer = new Client();
		consumer.setServiceName("com.alibaba.tinker.service.HelloObjectService:1.0.0.dev"); 
		consumer.init();
		
		HelloObjectService helloBooleanService = (HelloObjectService) consumer.getObject(); 
		
		TransferDTO transferDTO = new TransferDTO();
		transferDTO.setD(new Date());
		transferDTO.setId(424);
		transferDTO.setName("david.beckham");
		transferDTO.put2Map("1", "BUFFON");
		transferDTO.put2Map("2", "CAFU");
		transferDTO.put2Map("7", "David.Beckham");
		
		helloBooleanService.call(transferDTO);	
	}
}







