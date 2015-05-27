package com.alibaba.tinker.service.impl;

import com.alibaba.tinker.service.HelloLongBoxingService;

public class DefaultHelloLongBoxingService implements HelloLongBoxingService{

	@Override
	public void call(Long param) {
		System.out.println("I am long boxing service, param=" + param);
	}

}
