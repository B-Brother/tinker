package com.alibaba.tinker.constants;

import java.util.HashMap;
import java.util.Map;

/**
 * 该常量负责tinker中所有的类型
 * 
 * @author beckham
 *
 */
public class TypeConstants {
	
	@SuppressWarnings("rawtypes")
	// 简单类型映射
	public static Map<String, Class> easyTypeMapping = new HashMap<String, Class>(); 
	
	// 装箱类型映射
	@SuppressWarnings("rawtypes")
	public static Map<String, Class> boxingTypeMapping = new HashMap<String, Class>(); 
	
	static{
		easyTypeMapping.put("int", int.class);
		easyTypeMapping.put("short", short.class);
		easyTypeMapping.put("byte", byte.class);
		easyTypeMapping.put("char", char.class);
		easyTypeMapping.put("long", long.class);
		easyTypeMapping.put("boolean", boolean.class); 
		
		boxingTypeMapping.put("int", Integer.class);
		boxingTypeMapping.put("long", Long.class);
		boxingTypeMapping.put("short", Short.class);
		boxingTypeMapping.put("char", Character.class);
		boxingTypeMapping.put("boolean", Boolean.class);
		boxingTypeMapping.put("byte", Byte.class);
	} 
}




