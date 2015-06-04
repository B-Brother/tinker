package com.alibaba.tinker.parse.handler;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

import com.alibaba.tinker.parse.factory.xml.PeopleBeanDefinitionParser;

public class PeopleNameSpaceHandler extends NamespaceHandlerSupport {

	@Override
	public void init() {
		registerBeanDefinitionParser("people", new PeopleBeanDefinitionParser());  
	}

}
