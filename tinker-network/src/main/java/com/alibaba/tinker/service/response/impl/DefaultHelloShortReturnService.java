package com.alibaba.tinker.service.response.impl;

import java.util.Date;

import com.alibaba.tinker.service.response.HelloShortReturnService;

public class DefaultHelloShortReturnService implements HelloShortReturnService{

	@SuppressWarnings("deprecation")
	@Override
	public short call(Date date) {
		int month = date.getMonth();
		short monthShort = (short) month;
		return monthShort;
	}

}
