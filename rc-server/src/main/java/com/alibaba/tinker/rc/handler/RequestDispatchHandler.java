package com.alibaba.tinker.rc.handler;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.tinker.rc.container.NettyHandlerFactory;
import com.alibaba.tinker.rc.client.RequestContext;
import com.alibaba.tinker.rc.ex.BlankParameterException;
import com.alibaba.tinker.rc.util.RCConstants;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Resource;
import java.util.Base64;
import java.util.Map;

/**
 * 负责处理各个客户端过来的请求
 */
public class RequestDispatchHandler extends SimpleChannelInboundHandler<String> {

    @Resource
    private NettyHandlerFactory nettyHandlerFactory;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {

        // 解析msg
        byte[] originByte = Base64.getDecoder().decode(msg);

        if(originByte == null){
            return;
        }

        Map<String, String> dataMap = (Map<String, String>) JSONObject.
                parse(new String(originByte, "UTF-8"));

        // 分派到不同的handler
        RequestContext context = fillContext(dataMap);
        context.setChannel(ctx.channel());

        NettyHandler handler = nettyHandlerFactory.getHandler(context.getType());
        if (handler == null){
            return;
        }


        handler.handler(context);
    }

    private RequestContext fillContext(Map<String, String> dataMap){

        String type = dataMap.get(RCConstants.DATA_TYPE);
        if(StringUtils.isBlank(type)){
            throw new BlankParameterException("配置类型为空");
        }

        String appName = dataMap.get(RCConstants.DATA_APPNAME);
        if(StringUtils.isBlank(appName)){
            throw new BlankParameterException("配置应用名称为空");
        }

        String group = dataMap.get(RCConstants.DATA_GROUP);
        if(StringUtils.isBlank(type)){
            throw new BlankParameterException("配置组别为空");
        }

        String dataId = dataMap.get(RCConstants.DATA_ID);
        if(StringUtils.isBlank(dataId)){
            throw new BlankParameterException("配置数据唯一key为空");
        }

        String clientIp = dataMap.get(RCConstants.DATA_CLIENT_IP);
        if(StringUtils.isBlank(clientIp)){
            throw new BlankParameterException("配置客户端IP为空");
        }

        RequestContext context = new RequestContext();
        context.setAppName(appName);
        context.setClientIp(clientIp);
        context.setDataGroup(group);
        context.setDataId(dataId);
        context.setType(type);
        return context;
    }
}















