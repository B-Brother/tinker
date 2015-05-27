package com.alibaba.tinker.service.impl;

import com.alibaba.tinker.service.HelloByteBoxingService;

public class DefaultHelloByteBoxingService implements HelloByteBoxingService{

	@Override
	public void call(Byte num) {
		System.out.println("I am byte boxing service!!! num = " + num);
	}

}
