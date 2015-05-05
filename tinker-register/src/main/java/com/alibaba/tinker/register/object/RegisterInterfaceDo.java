package com.alibaba.tinker.register.object;

import java.util.Date;

public class RegisterInterfaceDo {
	private Long id;
	
	private Date gmtCreate;
	
	private Date gmtModified;
	
	private String creator;
	
	private String modified;
	
	private String isDeleted;
	
	private String appName;
	
	private String tinkerVersion;
	
	private String interfaceName;
	
	private String interfaceVersion;
	
	private String providerAddress;
	
	private Date publishTime;
	
	private String status;
	
	private Long timeout;
	
	private String serializableType;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getGmtCreate() {
		return gmtCreate;
	}

	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	public Date getGmtModified() {
		return gmtModified;
	}

	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getModified() {
		return modified;
	}

	public void setModified(String modified) {
		this.modified = modified;
	}

	public String getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(String isDeleted) {
		this.isDeleted = isDeleted;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getTinkerVersion() {
		return tinkerVersion;
	}

	public void setTinkerVersion(String tinkerVersion) {
		this.tinkerVersion = tinkerVersion;
	}

	public String getInterfaceName() {
		return interfaceName;
	}

	public void setInterfaceName(String interfaceName) {
		this.interfaceName = interfaceName;
	}

	public String getInterfaceVersion() {
		return interfaceVersion;
	}

	public void setInterfaceVersion(String interfaceVersion) {
		this.interfaceVersion = interfaceVersion;
	}

	public String getProviderAddress() {
		return providerAddress;
	}

	public void setProviderAddress(String providerAddress) {
		this.providerAddress = providerAddress;
	}

	public Date getPublishTime() {
		return publishTime;
	}

	public void setPublishTime(Date publishTime) {
		this.publishTime = publishTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	} 

	public Long getTimeout() {
		return timeout;
	}

	public void setTimeout(Long timeout) {
		this.timeout = timeout;
	}

	public String getSerializableType() {
		return serializableType;
	}

	public void setSerializableType(String serializableType) {
		this.serializableType = serializableType;
	} 
}  