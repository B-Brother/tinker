package com.alibaba.tinker.service.response.impl;

import java.util.ArrayList;
import java.util.List;
 
import com.alibaba.tinker.service.dto.TransferDTO;
import com.alibaba.tinker.service.response.HelloObjectReturnService;

public class DefaultHelloObjectReturnService implements HelloObjectReturnService{

	@Override
	public List<TransferDTO> call() {
		TransferDTO transferDTO1 = new TransferDTO(); 
		transferDTO1.setId(1);
		transferDTO1.setName("david.beckham");
		transferDTO1.put2Map("1", "BUFFON");
		transferDTO1.put2Map("2", "CAFU");
		transferDTO1.put2Map("7", "David.Beckham");
		
		TransferDTO transferDTO2 = new TransferDTO(); 
		transferDTO2.setId(2);
		transferDTO2.setName("david.beckham");
		transferDTO2.put2Map("1", "BUFFON");
		transferDTO2.put2Map("2", "CAFU");
		transferDTO2.put2Map("7", "David.Beckham");
		
		TransferDTO transferDTO3 = new TransferDTO(); 
		transferDTO3.setId(1);
		transferDTO3.setName("david.beckham");
		transferDTO3.put2Map("1", "BUFFON");
		transferDTO3.put2Map("2", "CAFU");
		transferDTO3.put2Map("7", "David.Beckham");
		
		List<TransferDTO> transferList = new ArrayList<TransferDTO>();
		transferList.add(transferDTO3);
		transferList.add(transferDTO2);
		transferList.add(transferDTO1);
		
		return transferList;
	}
    
}






