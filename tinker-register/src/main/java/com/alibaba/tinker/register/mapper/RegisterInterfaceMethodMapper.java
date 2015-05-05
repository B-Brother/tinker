package com.alibaba.tinker.register.mapper;

import java.util.List;

import javax.annotation.Resource;

import com.alibaba.tinker.register.object.RegisterInterfaceDo;

@Resource
public interface RegisterInterfaceMethodMapper {
	public Integer insertRegisterInterfaceMethod(RegisterInterfaceDo registerInterfaceDo);
	
	public List<RegisterInterfaceDo> getInterfaceListByInterface(long interfaceId); 
}
