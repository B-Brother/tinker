package com.alibaba.tinker.service.impl;

import com.alibaba.tinker.service.HelloLongService;

public class DefaultHelloLongService implements HelloLongService{

	@Override
	public void call(long param) {
		System.out.println("I am long servic, param=" + param);
	}

}
