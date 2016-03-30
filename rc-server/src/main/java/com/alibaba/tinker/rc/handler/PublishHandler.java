package com.alibaba.tinker.rc.handler;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.tinker.rc.client.RequestContext;
import com.alibaba.tinker.rc.mapper.ConfigMapper;
import com.alibaba.tinker.rc.object.ConfigDo;
import com.alibaba.tinker.rc.util.RCConstants;
import io.netty.channel.Channel;

import javax.annotation.Resource;
import java.nio.charset.Charset;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * 针对"发布"场景的注册处理
 */
public class PublishHandler implements NettyHandler {

    @Resource
    private ConfigMapper interfaceConfigMapper;

    @Override
    public void handler(RequestContext context) {

        ConfigDo config = new ConfigDo();
        config.setDataId(context.getDataId());
        config.setDataGroup(context.getDataGroup());
        config.setClientIp(context.getClientIp());
        config.setAppName(context.getAppName());
        config.setAttributes(JSONObject.toJSONString(context.getAttributeMap()));
        config.setDataValue(context.getDataValue());
        config.setGmtCreate(new Date());
        config.setGmtModified(new Date());
        config.setMiddleware(RCConstants.MIDDLEWARE_TINKER);
        config.setStatus(RCConstants.STATUS_NORMAL);

        ConfigDo configDo = interfaceConfigMapper.insert(config);

        // configDo不为空代表数据已经正确插入了,需要回通知客户端
        Map<String, String> response = new HashMap<>();
        if(configDo != null){
            response.put("result", "true");
        }else{
            response.put("result", "false");

            // TODO 需要判断这里的errorCode, 后续再考虑判断
        }

        // 将结果回写到客户端
        Channel channel = context.getChannel();
        channel.writeAndFlush(Base64.getEncoder().encode(
                JSONObject.toJSONString(response).getBytes(Charset.forName("UTF-8"))));
    }
}
