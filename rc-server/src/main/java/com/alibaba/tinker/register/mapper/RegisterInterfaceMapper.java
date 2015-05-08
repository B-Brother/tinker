package com.alibaba.tinker.register.mapper;

import java.util.List;

import javax.annotation.Resource;

import com.alibaba.tinker.register.object.RegisterInterfaceDo;

@Resource
public interface RegisterInterfaceMapper {
	public Integer insertRegisterInterface(RegisterInterfaceDo registerInterfaceDo);
	
	public List<RegisterInterfaceDo> getInterfaceListByInterfaceName(String interfaceName);
	
	public List<RegisterInterfaceDo> getInterfaceListByFullName(String fullName);
}
