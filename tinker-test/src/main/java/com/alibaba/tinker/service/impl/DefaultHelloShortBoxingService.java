package com.alibaba.tinker.service.impl;

import com.alibaba.tinker.service.HelloShortBoxingService;
 
public class DefaultHelloShortBoxingService implements HelloShortBoxingService{

	@Override
	public void call(Short num) {
		System.out.println("装箱类型Short被调用，num=" + num);
	}

}
