package com.alibaba.tinker.service.impl;

import com.alibaba.tinker.service.HelloCharacterService;

public class DefaultHelloCharacterService implements HelloCharacterService{

	@Override
	public void call(Character param) {
		System.out.println("I am boxing character service, param=" + param);
	}

}
