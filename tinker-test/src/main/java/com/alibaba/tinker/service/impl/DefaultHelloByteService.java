package com.alibaba.tinker.service.impl;

import com.alibaba.tinker.service.HelloByteService;

public class DefaultHelloByteService implements HelloByteService{

	@Override
	public void call(byte num) {
		System.out.println("I am byte service. num = " + num);
	}

}
