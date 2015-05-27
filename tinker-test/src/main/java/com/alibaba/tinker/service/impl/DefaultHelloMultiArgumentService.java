package com.alibaba.tinker.service.impl;

import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.alibaba.tinker.service.HelloMultiArgumentService;
import com.alibaba.tinker.service.dto.TransferDTO;

public class DefaultHelloMultiArgumentService implements HelloMultiArgumentService{

	@Override
	public void call(byte b1, Byte b2, short s1, Short s2, int i1, Integer i2,
			char c1, Character c2, long l1, Long l2, boolean bo1, Boolean bo2,
			String name, Date d1, TransferDTO transferDTO) {
		System.out.println("==========多参数调用输出开始=========");
		System.out.println("b1=" + b1);
		System.out.println("b2=" + b2);
		System.out.println("s1=" + s1);
		System.out.println("s2=" + s2);
		System.out.println("i1=" + i1);
		System.out.println("i2=" + i2);
		System.out.println("c1=" + c1);
		System.out.println("c2=" + c2);
		System.out.println("l1=" + l1);
		System.out.println("l2=" + l2);
		System.out.println("bo1=" + bo1);
		System.out.println("bo2=" + bo2);
		System.out.println("name=" + name);
		System.out.println("d1=" + d1);
		System.out.println("transferDto=" + ToStringBuilder.reflectionToString(transferDTO));
		System.out.println("==========多参数调用输出结束===========");
	}

}
