package com.alibaba.tinker.service.impl;

import com.alibaba.tinker.service.HelloWorld3Service;

public class DefaultHelloWorld3Service implements HelloWorld3Service{

	@Override
	public void call(int num) {
		System.out.println("我赚了" + num + "块钱");
	}

}
