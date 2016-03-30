package com.alibaba.tinker.rc.handler;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.tinker.rc.client.RequestContext;
import com.alibaba.tinker.rc.mapper.ConfigMapper;
import com.alibaba.tinker.rc.object.ConfigDo;
import io.netty.channel.Channel;

import javax.annotation.Resource;
import java.nio.charset.Charset;
import java.util.Base64;
import java.util.List;

/**
 *
 * 获取配置项数据的handler
 */
public class ConsumeHandler implements NettyHandler{

    @Resource
    private ConfigMapper configMapper;

    @Override
    public void handler(RequestContext context) {

        // 当前的连接
        Channel channel = context.getChannel();

        // 组别
        String dataGroup = context.getDataGroup();

        // 组别下唯一id
        String dataId = context.getDataId();

        // 应用名称
        String appName = context.getAppName();

        List<ConfigDo> configList = configMapper.queryConfigList(appName, dataGroup, dataId);

        // 向客户端回写目前根据dataId和group查到的合法状态列表数据
        byte[] response = Base64.getDecoder().decode(JSONObject.
                toJSONString(configList).getBytes(Charset.forName("UTF-8")));
        channel.writeAndFlush(response);
    }
}
