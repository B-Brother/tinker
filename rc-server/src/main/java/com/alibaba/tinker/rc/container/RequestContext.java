package com.alibaba.tinker.rc.container;

/**
 * 上下文对象。
 */
public class RequestContext {

    // 应用名称
    private String appName;

    // 客户端IP
    private String clientIp;

    // 数据分组
    private String dataGroup;

    // 数据的id, 同一个分组下唯一
    private String dataId;

    // 数据值
    private String dataValue;

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getClientIp() {
        return clientIp;
    }

    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
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
}