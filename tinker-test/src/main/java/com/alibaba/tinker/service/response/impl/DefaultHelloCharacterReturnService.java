package com.alibaba.tinker.service.response.impl;

import java.util.Date;

import com.alibaba.tinker.service.response.HelloCharacterReturnService;

public class DefaultHelloCharacterReturnService implements HelloCharacterReturnService {

	@SuppressWarnings("deprecation")
	@Override
	public Character call(Date date) {
		char v = date.toLocaleString().charAt(3);
		return new Character(v);
	}

}
