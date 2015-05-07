package com.alibaba.tinker.service.response.impl;

import java.util.Date;

import com.alibaba.tinker.service.response.HelloByteReturnService;

public class DefaultHelloByteReturnService implements HelloByteReturnService{

	@SuppressWarnings("deprecation")
	@Override
	public byte call(Date date) {
		int minute = date.getMinutes();
		byte b = (byte) minute;
		return b;
	}

}
