package com.alibaba.tinker.service.response.impl;

import java.util.Date;

import com.alibaba.tinker.service.response.HelloIntReturnService;

public class DefaultHelloIntReturnService implements HelloIntReturnService{

	@SuppressWarnings("deprecation")
	@Override
	public int call(Date date) {
		return date.getYear();
	}

}
