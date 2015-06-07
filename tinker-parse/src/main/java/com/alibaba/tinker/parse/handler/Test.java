package com.alibaba.tinker.parse.handler;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
 

public class Test {
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("beans.xml");  
        Object value = ctx.getBean("beckham-app");   
        Object d = ctx.getBean("default");   
        Object demoService = ctx.getBean("java.util.Date");   
        System.out.println("DONE.");
	}
}
