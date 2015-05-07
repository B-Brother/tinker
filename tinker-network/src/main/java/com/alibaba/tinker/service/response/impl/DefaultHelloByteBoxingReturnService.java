package com.alibaba.tinker.service.response.impl;

import java.util.Date;

import com.alibaba.tinker.service.response.HelloByteBoxingReturnService;

public class DefaultHelloByteBoxingReturnService implements HelloByteBoxingReturnService{

	@SuppressWarnings("deprecation")
	@Override
	public Byte call(Date date) {
		int day = date.getDay();
		byte dayByte = (byte) day;
		return new Byte(dayByte);
	}

}
