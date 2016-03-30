package com.alibaba.tinker.rc.bootstrap;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 在spring没有集成到web容器，该类用来简单启动spring容器
 */
public class Bootstrap {

    public static void main(String[] args) {

        new ClassPathXmlApplicationContext("beans.xml");
    }
}
