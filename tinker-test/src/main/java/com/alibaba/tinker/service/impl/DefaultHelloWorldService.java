package com.alibaba.tinker.service.impl;

import com.alibaba.tinker.service.HelloWorldService;

public class DefaultHelloWorldService implements HelloWorldService{

	@Override
	public void call() {
		System.out.println("Hello, 欢迎你使用Tinker。");
	}

}
