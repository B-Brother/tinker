package com.alibaba.tinker.parse.config;

public class ProtocolConfig extends BaseConfig {
	
	// 协议名称
	private String name;
	
	// 端口
	private int port;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	} 
}
