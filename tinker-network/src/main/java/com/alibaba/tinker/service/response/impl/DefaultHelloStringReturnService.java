package com.alibaba.tinker.service.response.impl;

import java.util.Date;

import com.alibaba.tinker.service.response.HelloStringReturnService;

public class DefaultHelloStringReturnService implements HelloStringReturnService{

	@SuppressWarnings("deprecation")
	@Override
	public String call(Date date) {
		return "现在是北京时间:" + date.toLocaleString(); 
	}

}
