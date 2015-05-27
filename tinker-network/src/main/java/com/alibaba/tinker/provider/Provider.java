package com.alibaba.tinker.provider;
 
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Provider{
	public static void main(String[] args) {
		// 初始化spring bean容器。让每个FactoryBean去做自己的初始化init方法
		new ClassPathXmlApplicationContext("classpath:providers.xml");
	}
}
