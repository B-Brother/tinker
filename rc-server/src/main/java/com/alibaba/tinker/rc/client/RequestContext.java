package com.alibaba.tinker.rc.client;

import io.netty.channel.Channel;

import java.util.HashMap;
import java.util.Map;

/**
 * 上下文对象。
 */
public class RequestContext {

    // 应用名称
    private String appName;

    // 客户端IP
    private String clientIp;

    // 发布类型
    private String type;

    // 数据分组
    private String dataGroup;

    // 数据的id, 同一个分组下唯一
    private String dataId;

    // 数据值
    private String dataValue;

    // 额外属性
    private Map<String, String> attributeMap = new HashMap<>();

    // 保持当前的netty连接channel
    private Channel channel;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public Map<String, String> getAttributeMap() {
        return attributeMap;
    }

    public void setAttributeMap(Map<String, String> attributeMap) {
        this.attributeMap = attributeMap;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }
}