package com.alibaba.tinker.parse.factory.xml;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser;
import org.springframework.util.StringUtils;
import org.w3c.dom.Element;

import com.alibaba.tinker.parse.entity.People;

public class PeopleBeanDefinitionParser extends AbstractSingleBeanDefinitionParser {
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected Class getBeanClass(Element element) { 
		return People.class;

	}

	protected void doParse(Element element, BeanDefinitionBuilder bean) { 
		String name = element.getAttribute("name");
		String age = element.getAttribute("age");
		String id = element.getAttribute("id");
		if (StringUtils.hasText(id)) {
			bean.addPropertyValue("id", id);
		}

		if (StringUtils.hasText(name)) {
			bean.addPropertyValue("name", name);
		}

		if (StringUtils.hasText(age)) {
			bean.addPropertyValue("age", Integer.valueOf(age));
		}
	}
}
