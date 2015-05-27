package com.alibaba.tinker.service.impl;

import com.alibaba.tinker.service.HelloBooleanService;

public class DefaultHelloBooleanService implements HelloBooleanService{

	@Override
	public void call(boolean param) {
		System.out.println("I am boolean service, param = " + param);
	}

}
