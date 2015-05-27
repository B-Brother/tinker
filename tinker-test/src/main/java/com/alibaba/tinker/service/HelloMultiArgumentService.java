package com.alibaba.tinker.service;

import java.util.Date;

import com.alibaba.tinker.service.dto.TransferDTO;

/**
 * 多参数调用服务
 * 
 * @author beckham
 *
 */
public interface HelloMultiArgumentService {

	public void call(
			byte b1,
			Byte b2,
			short s1,
			Short s2,
			int i1,
			Integer i2,
			char c1,
			Character c2,
			long l1,
			Long l2,
			boolean bo1,
			Boolean bo2,
			String name,
			Date d1,
			TransferDTO transferDTO
		);	
}
