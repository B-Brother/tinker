package com.alibaba.tinker.service.response.impl;

import java.util.Date;

import com.alibaba.tinker.service.response.HelloIntegerReturnService;

public class DefaultHelloIntegerReturnService implements HelloIntegerReturnService{

	@SuppressWarnings("deprecation")
	@Override
	public Integer call(Date date) {
		return new Integer(date.getDay());
	}

}
