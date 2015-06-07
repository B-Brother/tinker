package com.alibaba.tinker.parse.config;

public class ServiceConfig extends BaseConfig {
	// 服务名称
	private String name;
	
	// 服务版本
	private String version;
	
	// 具体服务对象
	private Object ref;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public Object getRef() {
		return ref;
	}

	public void setRef(Object ref) {
		this.ref = ref;
	} 
}
