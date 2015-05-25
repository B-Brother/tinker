package com.alibaba.tinker.xml;

import java.io.File;
import java.io.IOException;

import org.apache.commons.digester.Digester;
import org.xml.sax.SAXException;

/**
 * 
 * <beans>
	<bean service="com.alibaba.tinker.service.HelloWorldService">
		<property name="target" value="com.alibaba.tinker.service.impl.DefaultHelloWorldService"/>
		<property name="version" value="1.0.0.dev"/>
		<property name="timeout" value="3000"/> 
		<property name="threadPoolCoreSize" value="5"/>
		<property name="threadPoolMaxiumSize" value="10"/>
	</bean>
	 
	<bean service="com.alibaba.tinker.service.HelloWorld2Service">
		<property name="target" value="com.alibaba.tinker.service.impl.DefaultHelloWorld2Service"/>
		<property name="version" value="1.0.0.dev"/>
		<property name="timeout" value="3000"/> 
		<property name="threadPoolCoreSize" value="5"/>
		<property name="threadPoolMaxiumSize" value="10"/>
	</bean>
   </beans>	
	
 * @author beckham
 *
 */
public class Test {
	public static void main(String[] args) throws IOException, SAXException {
		 Digester digester = new Digester();
	     digester.setValidating(false);
	     digester.addObjectCreate("beans", XmlServiceBeans.class);
	     digester.addObjectCreate("beans/bean", "bean", XmlServiceBean.class);
	     digester.addSetProperties("beans/bean", "service", "service");
	     
	     digester.addBeanPropertySetter("beans/bean/target");
		 digester.addBeanPropertySetter("beans/bean/version");
		 digester.addBeanPropertySetter("beans/bean/timeout");
		 digester.addBeanPropertySetter("beans/bean/threadPoolCoreSize");
		 digester.addBeanPropertySetter("beans/bean/threadPoolMaxiumSize"); 
//	     digester.addSetProperties("beans/bean", "target", "target");
//	     digester.addSetProperties("beans/bean", "version", "version");
//	     digester.addSetProperties("beans/bean", "timeout", "timeout");
//	     digester.addSetProperties("beans/bean", "threadPoolCoreSize", "threadPoolCoreSize");
//	     digester.addSetProperties("beans/bean", "threadPoolMaxiumSize", "threadPoolMaxiumSize");
	     digester.addSetNext("beans/bean", "addBean"); 
	     XmlServiceBeans beans = (XmlServiceBeans) digester.parse(
	    		 new File("/Users/beckham/Desktop/opensource/tinker/tinker/tinker-network/src/main/java/providers.xml")); 
		
	     System.out.println(beans);
		
		
		
		// Digester digester = new Digester();
	    // digester.setValidating(false);
	    // digester.addObjectCreate("Finance", XmlApplyReq.class);
	    // digester.addObjectCreate("Finance/Message", "Message", XmlApplyReqMsg.class);
	    // digester.addSetProperties("Finance/Message", "id", "id");
	    // digester.addSetNext("Finance/Message", "addMessage");
	    // digester.addObjectCreate("Finance/Message/FPSReq", "FPSReq", XmlApplyReqData.class);
	    // digester.addBeanPropertySetter("Finance/Message/FPSReq/peerToken");
	    // digester.addBeanPropertySetter("Finance/Message/FPSReq/content");
	    // digester.addSetProperties("Finance/Message/FPSReq", "id", "id");
	    // digester.addSetNext("Finance/Message/FPSReq", "addFPSReq");
	    // XmlApplyReq apply = (XmlApplyReq) digester.parse(url); 
	}
}
