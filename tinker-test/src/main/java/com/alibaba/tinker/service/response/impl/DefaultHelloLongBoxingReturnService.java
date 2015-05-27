package com.alibaba.tinker.service.response.impl;

import java.util.Date;

import com.alibaba.tinker.service.response.HelloLongBoxingReturnService;

public class DefaultHelloLongBoxingReturnService implements HelloLongBoxingReturnService{

	@Override
	public Long call(Date date) {
		return new Long(date.getTime());
	}

}
