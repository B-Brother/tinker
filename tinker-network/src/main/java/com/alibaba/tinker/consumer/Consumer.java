package com.alibaba.tinker.consumer;
 
import java.lang.reflect.Proxy; 
 
public final class Consumer {  
     
    private String serviceName;
    
    // 被代理的远程对象
    private Object proxy;
 
	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	} 

	@SuppressWarnings("rawtypes")
	public void init(){
		// 生成代理对象
    	Class clazz;
		try {
			clazz = Class.forName(serviceName.substring(0, serviceName.lastIndexOf(":")));
			
			Class[] interfaceArr = new Class[1];
			interfaceArr[0] = clazz;
			 
			Object proxyObj = Proxy.newProxyInstance(
	                this.getClass().getClassLoader(),  
	                interfaceArr,   
	                new TinkerInvokeHandler(this)    
	                );
			
			proxy = proxyObj;
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
    }
    
    public Object getObject(){
    	return proxy;
    }  
}









