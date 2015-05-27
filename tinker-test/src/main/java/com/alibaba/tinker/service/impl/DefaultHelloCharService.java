package com.alibaba.tinker.service.impl;

import com.alibaba.tinker.service.HelloCharService;

public class DefaultHelloCharService implements HelloCharService{

	@Override
	public void call(char param) {
		System.out.println("I am char service, param=" + param);
	}

}
