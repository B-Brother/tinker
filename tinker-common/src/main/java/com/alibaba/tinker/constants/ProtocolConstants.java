package com.alibaba.tinker.constants;

import java.util.ArrayList;
import java.util.HashMap;

public class ProtocolConstants {
	
	@SuppressWarnings("rawtypes")
	public static final HashMap<String, Class> jdkTypeMap = new HashMap<String, Class>();
	 
	
	public static final HashMap<Integer, String> serializableTypeMap = new HashMap<Integer, String>();
	
	public static final ArrayList<Integer> serializableCodeList = new ArrayList<Integer>();
	
	
	public static final int ONE_BYTE_LENGTH = 127;
	
	public static final int TWO_BYTE_LENGTH = 65535;
	
	public static final int FOUR_BYTE_LENGTH = Integer.MAX_VALUE;
	
	public static final long EIGHT_BYTE_LENGTH = Long.MAX_VALUE; 
	
	// 魔数
	public static final String MAGIC_NUMBER = "B-TINKER";
	
	public static final String TINKER_VERSION = "0.1-BT";
	
	
	public static final int PROTOCOL_TYPE_REQUEST_CODE = 0x01;
	
	public static final int PROTOCOL_TYPE_RESPONSE_CODE = 0x02;
	
	public static final int PROTOCOL_TYPE_HEARTBEAT_CODE = 0x03;
	
	public static final String PROTOCOL_TYPE_REQUEST = "REQUEST";
	
	public static final String PROTOCOL_TYPE_RESPONSE = "RESPONSE";
	
	public static final String PROTOCOL_TYPE_HEARTBEAT = "HEARTBEAT";
	
	public static final int PROTOCOL_TYPE_RESPONSE_SUCCESS_CODE = 0x01;
	
	public static final int PROTOCOL_TYPE_RESPONSE_ERROR_CODE = 0x02; 
	
	public static final String PROTOCOL_TYPE_RESPONSE_SUCCESS = "SUCCESS";
	
	public static final String PROTOCOL_TYPE_RESPONSE_ERROR = "ERROR"; 
	
	
	public static final int SERIALIZABLE_TYPE_JDK_CODE = 0x01;
	
	public static final int SERIALIZABLE_TYPE_HESSIAN2_CODE = 0x02;
	 
	public static final int SERIALIZABLE_TYPE_HESSIAN3_CODE = 0x03;
	
	public static final int SERIALIZABLE_TYPE_HESSIAN4_CODE = 0x04;
	
	public static final int SERIALIZABLE_TYPE_PROTOBUF_CODE = 0x05;
	
	public static final int SERIALIZABLE_TYPE_KYO_CODE = 0x06;
	
	public static final String SERIALIZABLE_TYPE_JDK = "JDK";
	
	public static final String SERIALIZABLE_TYPE_HESSIAN2 = "HESSIAN2";
	 
	public static final String SERIALIZABLE_TYPE_HESSIAN3 = "HESSIAN3";
	
	public static final String SERIALIZABLE_TYPE_HESSIAN4 = "HESSIAN4";
	
	public static final String SERIALIZABLE_TYPE_PROTOBUF = "PROTOBUF";
	
	public static final String SERIALIZABLE_TYPE_KYO = "KYO";

	static{
		jdkTypeMap.put("string", String.class);
		jdkTypeMap.put("byte", Byte.class);
		jdkTypeMap.put("int", Integer.class);
		jdkTypeMap.put("long", Long.class);
		jdkTypeMap.put("short", Short.class);
		jdkTypeMap.put("char", Character.class);
		jdkTypeMap.put("boolean", Boolean.class);
		  
		serializableCodeList.add(SERIALIZABLE_TYPE_JDK_CODE);
		serializableCodeList.add(SERIALIZABLE_TYPE_HESSIAN2_CODE);
		serializableCodeList.add(SERIALIZABLE_TYPE_HESSIAN3_CODE);
		serializableCodeList.add(SERIALIZABLE_TYPE_HESSIAN4_CODE);
		serializableCodeList.add(SERIALIZABLE_TYPE_PROTOBUF_CODE);
		serializableCodeList.add(SERIALIZABLE_TYPE_KYO_CODE);
		
		serializableTypeMap.put(SERIALIZABLE_TYPE_JDK_CODE, SERIALIZABLE_TYPE_JDK);
		serializableTypeMap.put(SERIALIZABLE_TYPE_HESSIAN2_CODE, SERIALIZABLE_TYPE_HESSIAN2);
		serializableTypeMap.put(SERIALIZABLE_TYPE_HESSIAN3_CODE, SERIALIZABLE_TYPE_HESSIAN3);
		serializableTypeMap.put(SERIALIZABLE_TYPE_HESSIAN4_CODE, SERIALIZABLE_TYPE_HESSIAN4);
		serializableTypeMap.put(SERIALIZABLE_TYPE_KYO_CODE, SERIALIZABLE_TYPE_KYO);
		serializableTypeMap.put(SERIALIZABLE_TYPE_PROTOBUF_CODE, SERIALIZABLE_TYPE_PROTOBUF);
	} 
}
