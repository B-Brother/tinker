package com.alibaba.tinker.service.impl;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import com.alibaba.tinker.service.HelloObjectService;
import com.alibaba.tinker.service.dto.TransferDTO;

public class DefaultHelloObjectService implements HelloObjectService{

	@Override
	public void call(TransferDTO transferDTO) {
		System.out.println("I am object service, transferDTO=" + ReflectionToStringBuilder.toString(transferDTO));
	}
 
}
