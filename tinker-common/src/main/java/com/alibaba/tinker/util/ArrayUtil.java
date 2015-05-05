package com.alibaba.tinker.util;

public class ArrayUtil {
	public static byte[] concat(byte[] a, byte[] b) {  
	   byte[] c= new byte[a.length+b.length];  
	   System.arraycopy(a, 0, c, 0, a.length);  
	   System.arraycopy(b, 0, c, a.length, b.length);  
	   return c;  
	}  
	
	public static byte[] concat(byte[] a, byte b){
		byte bArr[] = new byte[]{b};
		return concat(a, bArr);
	}
	
	public static byte[] concat(byte[] a, int b){
		byte bArr[] = new byte[]{(byte)b};
		return concat(a, bArr);
	}
}
