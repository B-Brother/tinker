package com.alibaba.tinker.rc.container;

import com.alibaba.tinker.rc.handler.ConsumeHandler;
import com.alibaba.tinker.rc.handler.NettyHandler;
import com.alibaba.tinker.rc.handler.PublishHandler;
import com.alibaba.tinker.rc.util.NettyHandlerConstants;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by yingchao.zyc on 2016/3/25.
 */
public class NettyHandlerFactory {

    @Resource
    private ConsumeHandler consumeHandler;

    @Resource
    private PublishHandler publishHandler;

    private Map<String, NettyHandler> handlerMap = new HashMap<>();

    private NettyHandlerFactory(){
        handlerMap.put(NettyHandlerConstants.HANDLER_CONSUMER, consumeHandler);
        handlerMap.put(NettyHandlerConstants.HANDLER_PUBLISH, publishHandler);
    }

    public NettyHandler getHandler(String type){
        return handlerMap.get(type);
    }
}
