package com.alibaba.tinker.rc.handler;

import com.alibaba.tinker.rc.container.RequestContext;

/**
 * Created by yingchao.zyc on 2016/3/25.
 */
public interface NettyHandler {

    /**
     * 处理请求。
     *
     * @param context
     */
    public void handler(RequestContext context);
}
