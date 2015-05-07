package com.alibaba.tinker.service.response.impl;

import java.util.Date;

import com.alibaba.tinker.service.response.HelloBooleanReturnService;

public class DefaultHelloBooleanReturnService implements HelloBooleanReturnService{
 
	@Override
	public boolean call(Date date) {
		return new Boolean(date.getTime() > 1);
	}

}
