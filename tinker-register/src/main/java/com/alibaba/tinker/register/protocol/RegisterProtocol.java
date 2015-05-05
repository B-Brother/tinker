package com.alibaba.tinker.register.protocol;
 
import com.alibaba.fastjson.JSONObject; 

/**
 * 负责注册时候传入的协议解析
 * 
 * @author yingchao.zyc
 *
 */
public class RegisterProtocol {
	// 接口名称
	private String interfaceName;
	
	// 版本
	private String version;
	
	// 目标地址
	private String targetAddress;
	
	// 服务端发布的超时时间
	private long timeout;

	// 调用连接端口
	private String connectPort;
	
	public RegisterProtocol(JSONObject json){  
    	String serviceName = json.getString("serviceName");
    	 
		interfaceName = serviceName.substring(0, serviceName.indexOf(":"));
		version = serviceName.substring(serviceName.indexOf(":") + 1);
		targetAddress = json.getString("address");
		connectPort = json.getString("connectPort");
		timeout = json.getLong("timeout");
	}

	public String getInterfaceName() {
		return interfaceName;
	}

	public String getVersion() {
		return version;
	}

	public String getTargetAddress() {
		return targetAddress;
	}

	public long getTimeout() {
		return timeout;
	}

	public String getConnectPort() {
		return connectPort;
	} 
}
