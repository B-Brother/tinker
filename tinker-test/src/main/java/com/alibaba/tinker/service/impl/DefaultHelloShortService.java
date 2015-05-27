package com.alibaba.tinker.service.impl;

import com.alibaba.tinker.service.HelloShortService;

public class DefaultHelloShortService implements HelloShortService{

	@Override
	public void call(short num) {
		 System.out.println("short " + num + " short");
	}

}
