package com.alibaba.tinker.service.impl;

import com.alibaba.tinker.service.HelloBooleanBoxingService;

public class DefaultHelloBooleanBoxingService implements HelloBooleanBoxingService{

	@Override
	public void call(Boolean param) {
		System.out.println("I am boxing boolean service, param=" + param);
	}

}
