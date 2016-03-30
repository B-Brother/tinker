package com.alibaba.tinker.rc.client;

import com.alibaba.tinker.rc.object.ConfigDo;

import java.util.List;

/**
 *
 * 配置中心返回的结果值。
 */
public class RCResponse {

    private boolean isSucces;

    private String errorCode;

    private long requestId;

    private List<ConfigDo> configList;

    public boolean isSucces() {
        return isSucces;
    }

    public void setSucces(boolean succes) {
        isSucces = succes;
    }

    public long getRequestId() {
        return requestId;
    }

    public void setRequestId(long requestId) {
        this.requestId = requestId;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public List<ConfigDo> getConfigList() {
        return configList;
    }

    public void setConfigList(List<ConfigDo> configList) {
        this.configList = configList;
    }
}
