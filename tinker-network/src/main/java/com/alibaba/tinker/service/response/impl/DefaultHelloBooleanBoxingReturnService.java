package com.alibaba.tinker.service.response.impl;

import java.util.Date;

import com.alibaba.tinker.service.response.HelloBooleanBoxingReturnService;

public class DefaultHelloBooleanBoxingReturnService implements HelloBooleanBoxingReturnService{

	@Override
	public Boolean call(Date date) {
		return date.getTime() > 1L;
	}

}
