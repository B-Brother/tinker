package com.alibaba.tinker.metadata;

/**
 * 接口服务元数据。当前类囊括了所有的服务信息
 * 
 * @author beckham
 *
 */
public class ServiceMetadata {
	private String serviceName;

	private Object target;

	private String version;

	private int timeout;

	private int threadPoolCoreSize;

	private int threadPoolMaxiumSize;

	private String serializableType;
	
	public String getServiceName() {
		return serviceName;
	}

	public String getSerializableType() {
		return serializableType;
	}

	public void setSerializableType(String serializableType) {
		this.serializableType = serializableType;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public Object getTarget() {
		return target;
	}

	public void setTarget(Object target) {
		this.target = target;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public int getTimeout() {
		return timeout;
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	public int getThreadPoolCoreSize() {
		return threadPoolCoreSize;
	}

	public void setThreadPoolCoreSize(int threadPoolCoreSize) {
		this.threadPoolCoreSize = threadPoolCoreSize;
	}

	public int getThreadPoolMaxiumSize() {
		return threadPoolMaxiumSize;
	}

	public void setThreadPoolMaxiumSize(int threadPoolMaxiumSize) {
		this.threadPoolMaxiumSize = threadPoolMaxiumSize;
	} 
}
