package com.alibaba.tinker.service.response.impl;

import java.util.Date;

import com.alibaba.tinker.service.response.HelloShortBoxingReturnService;

public class DefaultHelloShortBoxingReturnService implements HelloShortBoxingReturnService{

	@SuppressWarnings("deprecation")
	@Override
	public Short call(Date date) {
		return new Short((short)date.getMinutes());
	}
}
