package com.alibaba.tinker.rc.mapper;

import javax.annotation.Resource;

import com.alibaba.tinker.rc.object.ConfigDo;

import java.util.List;

@Resource
public interface ConfigMapper {

	/**
	 * 新增一条配置信息
	 *
	 * @param config
	 * @return
     */
	ConfigDo insert(ConfigDo config);

	/**
	 * 获取对应的配置列表
	 *
	 * @param appName
	 * @param dataGroup
	 * @param dataId
     * @return
     */
	List<ConfigDo> queryConfigList(String appName, String dataGroup, String dataId);
}
