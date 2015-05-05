package com.alibaba.tinker.generator;

import java.util.concurrent.atomic.AtomicLong;

/**
 * tinker的伪UUID生成方案。使用自增的方式。
 * 
 * PS:另外一个framework里边给的方案是getAndDecrement。暂时没明白其中缘由。
 * 
 * @author yingchao.zyc
 *
 */
public class UUIDGenerator {
    private static AtomicLong tinkerKey = new AtomicLong();

    public static final synchronized long getNextKey() {
        return tinkerKey.getAndIncrement();
    } 
}