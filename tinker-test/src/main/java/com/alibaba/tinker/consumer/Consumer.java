package com.alibaba.tinker.consumer;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.alibaba.tinker.service.HelloWorldService;

public class Consumer {
	public static void main(String[] args) {
		// 初始化spring bean容器。让每个FactoryBean去做自己的初始化init方法
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:consumer/consumer.xml");
		
		HelloWorldService helloWorldService = (HelloWorldService) context.getBean("helloWorldService");
		
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		helloWorldService.call();
	}
}
