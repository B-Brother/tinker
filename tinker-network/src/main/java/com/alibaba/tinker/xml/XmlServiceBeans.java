package com.alibaba.tinker.xml;

import java.util.ArrayList;
import java.util.List;
 
import org.apache.commons.lang3.builder.ToStringBuilder;

public class XmlServiceBeans {
	private List<XmlServiceBean> beanList = new ArrayList<XmlServiceBean>();
	
	public void addBean(XmlServiceBean bean){
		beanList.add(bean);
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
