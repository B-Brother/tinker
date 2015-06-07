package com.alibaba.tinker.parse.handler;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

import com.alibaba.tinker.parse.config.ApplicationConfig;
import com.alibaba.tinker.parse.config.ProtocolConfig;
import com.alibaba.tinker.parse.config.ServiceConfig; 
import com.alibaba.tinker.parse.factory.xml.TinkerBeanDefinitionParser;

public class PeopleNameSpaceHandler extends NamespaceHandlerSupport {

	@Override
	public void init() {
		registerBeanDefinitionParser("application", new TinkerBeanDefinitionParser(ApplicationConfig.class, true));  
		registerBeanDefinitionParser("protocol", new TinkerBeanDefinitionParser(ProtocolConfig.class, true));  
		registerBeanDefinitionParser("service", new TinkerBeanDefinitionParser(ServiceConfig.class, true));  
	}

}
