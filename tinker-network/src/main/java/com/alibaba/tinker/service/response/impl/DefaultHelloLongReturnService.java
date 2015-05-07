package com.alibaba.tinker.service.response.impl;

import java.util.Date;

import com.alibaba.tinker.service.response.HelloLongReturnService;

public class DefaultHelloLongReturnService implements HelloLongReturnService{

	@Override
	public long call(Date date) {
		return date.getTime();
	}

}
