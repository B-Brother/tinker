package com.alibaba.tinker.invoke.noreturn.multiparam;

import java.util.Date;

import com.alibaba.tinker.consumer.Consumer;
import com.alibaba.tinker.publisher.Publisher;
import com.alibaba.tinker.service.HelloMultiArgumentService;
import com.alibaba.tinker.service.dto.TransferDTO;

public class VoidReturnMultiArgumentInvoke {
	public static void main(String[] args) {
		// 启动Provider
		Publisher publisher = new Publisher("com.alibaba.tinker.service.HelloMultiArgumentService:1.0.0.dev");
		publisher.forRegisterCenter();
		publisher.forRpc();
		 
		// 启动Consumer
		Consumer consumer = new Consumer();
		consumer.setServiceName("com.alibaba.tinker.service.HelloMultiArgumentService:1.0.0.dev"); 
		consumer.init();
		
		HelloMultiArgumentService helloMultiArgumentService = (HelloMultiArgumentService) consumer.getObject();  
		
		byte b1 = 1;
		Byte b2 = 2;
		short s1 = 3;
		Short s2 = 4;
		int i1 = 5; 
		Integer i2 = 6; 
		char c1 = 'C';
		Character c2 = 'D';
		long l1 = 7;
		Long l2 = 8L;
		boolean bo1 = true;
		Boolean bo2 = new Boolean(false);
		String name = "fuck";
		Date d1 = new Date();
		TransferDTO transferDTO = new TransferDTO();
		transferDTO.setD(new Date());
		transferDTO.setId(424);
		transferDTO.setName("david.beckham");
		transferDTO.put2Map("1", "BUFFON");
		transferDTO.put2Map("2", "CAFU");
		transferDTO.put2Map("7", "David.Beckham");
		
		helloMultiArgumentService.call(b1, b2, s1, s2, i1, i2, c1, c2, l1, l2, bo1, bo2, name, d1, transferDTO);	
	}
}
