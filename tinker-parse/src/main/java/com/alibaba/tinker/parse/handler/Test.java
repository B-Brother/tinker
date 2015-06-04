package com.alibaba.tinker.parse.handler;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.alibaba.tinker.parse.entity.People;

public class Test {
	public static void main(String[] args) {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("beans.xml");  
        People f = (People) ctx.getBean("people");  
        System.out.println(f);  
	}
}
