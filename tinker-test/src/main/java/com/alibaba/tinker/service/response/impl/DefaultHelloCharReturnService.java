package com.alibaba.tinker.service.response.impl;

import java.util.Date;

import com.alibaba.tinker.service.response.HelloCharReturnService;

public class DefaultHelloCharReturnService implements HelloCharReturnService{

	@SuppressWarnings("deprecation")
	@Override
	public char call(Date date) {
		return date.toLocaleString().charAt(3);
	}

}
