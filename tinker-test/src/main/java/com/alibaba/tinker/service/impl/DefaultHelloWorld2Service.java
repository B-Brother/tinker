package com.alibaba.tinker.service.impl;

import com.alibaba.tinker.service.HelloWorld2Service;

public class DefaultHelloWorld2Service implements HelloWorld2Service{

	@Override
	public void call(Integer num) {
		System.out.println("我赚了" + num + "块钱");
	}

}
