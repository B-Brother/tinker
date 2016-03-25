package com.alibaba.tinker.rc.container;

import com.alibaba.tinker.rc.handler.ConsumeHandler;
import com.alibaba.tinker.rc.handler.NettyHandler;
import com.alibaba.tinker.rc.handler.PublishHandler;
import com.alibaba.tinker.rc.util.NettyHandlerConstants;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by yingchao.zyc on 2016/3/25.
 */
public class NettyHandlerFactory {

    private static NettyHandlerFactory instance = null;

    private Map<String, NettyHandler> handlerMap = new HashMap<>();

    private NettyHandlerFactory(){
        handlerMap.put(NettyHandlerConstants.HANDLER_CONSUMER, new ConsumeHandler());
        handlerMap.put(NettyHandlerConstants.HANDLER_PUBLISH, new PublishHandler());
    }

    public static NettyHandlerFactory getInstance(){
        if(instance == null){
            instance = new NettyHandlerFactory();
        }

        return instance;
    }

    public NettyHandler getHandler(String type){
        return handlerMap.get(type);
    }
}
