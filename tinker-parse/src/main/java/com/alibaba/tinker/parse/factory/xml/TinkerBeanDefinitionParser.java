package com.alibaba.tinker.parse.factory.xml;
  
import org.springframework.beans.factory.config.BeanDefinition; 
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.RootBeanDefinition; 
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;  

import com.alibaba.tinker.parse.config.ApplicationConfig;
import com.alibaba.tinker.parse.config.ProtocolConfig;
import com.alibaba.tinker.parse.config.ServiceConfig;

/**
 * 感谢dubbo的作者william.liangf。这里用了你的代码和思想。
 * 
 * 负责解析xml中的schema。
 * <tinker:application>,<tinker:service>,<tinker:protocol> 
 * 
 * @author beckham
 *
 */
public class TinkerBeanDefinitionParser implements BeanDefinitionParser{

	private Class<?> beanClass;
	
	private boolean required;
	
	public TinkerBeanDefinitionParser(Class<?> beanClass, boolean required){
		this.beanClass = beanClass;
		this.required = required;
	}
	
	@Override
	public BeanDefinition parse(Element element, ParserContext parserContext) {
		// 初始化当前class对应的bean
		RootBeanDefinition beanDefinition = new RootBeanDefinition();
        beanDefinition.setBeanClass(beanClass);
        beanDefinition.setLazyInit(false);
        
        // 尝试获取该spring-schema的id
        String id = acquireBeanId(element);
        
        // 最后做一遍bean的检查，如果该bean存在，则提示这个bean存在了，抛出异常，启动失败
        if (id != null && id.length() > 0) {
            if (parserContext.getRegistry().containsBeanDefinition(id))  {
        		throw new IllegalStateException("Duplicate spring bean id : " + id);
        	}
            
            // 注册bean，设置spring的beanId。
            parserContext.getRegistry().registerBeanDefinition(id, beanDefinition); 
        } 
        
        if(ApplicationConfig.class == beanClass){
        	String appName = element.getAttribute("name");
        	beanDefinition.getPropertyValues().addPropertyValue("name", appName); 
        }else if(ProtocolConfig.class == beanClass){
        	String name = element.getAttribute("name");
        	int port = Integer.parseInt(element.getAttribute("port"));
        	beanDefinition.getPropertyValues().addPropertyValue("name", name); 
        	beanDefinition.getPropertyValues().addPropertyValue("port", port); 
        }else if(ServiceConfig.class == beanClass){
        	String name = element.getAttribute("name");
        	String version = element.getAttribute("version");
        	String ref = element.getAttribute("ref");
        	beanDefinition.getPropertyValues().addPropertyValue("name", name); 
        	beanDefinition.getPropertyValues().addPropertyValue("version", version); 
        	 
        	beanDefinition.getPropertyValues().addPropertyValue("ref", new RuntimeBeanReference(ref)); 
        }
        
        return beanDefinition;
	}
	
	
	
	
	private String acquireBeanId(Element element){ 
        String id = element.getAttribute("id");
        // 当然，我们自定义的schema很有可能是没有id的。比如tinker:application
        if ((id == null || id.length() == 0) && required) {
        	// 如果找不到id，就去找name属性。name也是常见的选项
        	String generatedBeanName = element.getAttribute("name");
        	if (generatedBeanName == null || generatedBeanName.length() == 0) {
        		// 日了狗了。如果id和name都没有的情况下，默认给值为tinker-common。 
        		// 否则去取interface这个属性(其实也就是去找tinker:service了)。
        		generatedBeanName = element.getAttribute("interface");
        		if(generatedBeanName == null || generatedBeanName.length() == 0){
        			generatedBeanName = "tinker-common";
        		}
        	}
        	
        	// 如果还是取不到。卧槽。那就用当前类名来做区分吧。
        	if (generatedBeanName == null || generatedBeanName.length() == 0) {
        		generatedBeanName = beanClass.getName();
        	}
        	
        	// OK，最终的id生成。
            id = generatedBeanName;  
        }
        
        return id;
	} 
}












