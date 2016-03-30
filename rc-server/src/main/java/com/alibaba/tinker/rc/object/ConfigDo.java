package com.alibaba.tinker.rc.object;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 接口持久化对象。
 */
public class ConfigDo {

	// 自增主键id
	private Long id;

	// 记录创建时间
	private Date gmtCreate;

	// 记录修改时间
	private Date gmtModified;

	// app名称
	private String appName;

	// 数据所属组别(一般会按照应用进行区分)
	private String dataGroup;

	// 在同一个组下唯一的数据id
	private String dataId;

	// 数据值
	private String dataValue;

	// 客户端ip
	private String clientIp;

	// 当前发布记录的状态
	private int status;

	// 中间件类型。暂时这里默认为Tinker
 	private String middleware;

	// 额外属性值集合。这个值为多个值的map集合以json存储
	// 例如在Tinker中有timeout,序列化方式等等额外字段需要持久化到该字段。
	private String attributes;

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

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getDataGroup() {
		return dataGroup;
	}

	public void setDataGroup(String dataGroup) {
		this.dataGroup = dataGroup;
	}

	public String getDataId() {
		return dataId;
	}

	public void setDataId(String dataId) {
		this.dataId = dataId;
	}

	public String getDataValue() {
		return dataValue;
	}

	public void setDataValue(String dataValue) {
		this.dataValue = dataValue;
	}

	public String getClientIp() {
		return clientIp;
	}

	public void setClientIp(String clientIp) {
		this.clientIp = clientIp;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMiddleware() {
		return middleware;
	}

	public void setMiddleware(String middleware) {
		this.middleware = middleware;
	}

	public String getAttributes() {
		return attributes;
	}

	public void setAttributes(String attributes) {
		this.attributes = attributes;
	}
}