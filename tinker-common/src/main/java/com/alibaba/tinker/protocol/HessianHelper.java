package com.alibaba.tinker.protocol;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import com.caucho.hessian.io.HessianInput;
import com.caucho.hessian.io.HessianOutput;

/**
 * HESSIAN的序列&反序列化工具类。
 * 
 * @author yingchao.zyc
 *
 */
public class HessianHelper {
	/**
	 * 将对象序列化成数组
	 * 
	 * @param obj
	 * @return
	 */
	public static byte[] serialize(Object obj) {
		if (obj == null)
			throw new NullPointerException();

		ByteArrayOutputStream os = new ByteArrayOutputStream();
		HessianOutput ho = new HessianOutput(os);
		try {
			ho.writeObject(obj);
		} catch (IOException e) { 
			e.printStackTrace();
		}
		return os.toByteArray();
	}

	/**
	 * 将数组解序列化成对象
	 * 
	 * @param obj
	 * @return
	 */
	public static Object deserialize(byte[] by) {
		if (by == null)
			throw new NullPointerException();

		ByteArrayInputStream is = new ByteArrayInputStream(by);
		
		HessianInput hi = new HessianInput(is);
		try {
			return hi.readObject();
		} catch (IOException e) { 
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static void main(String[] args) {
		Short s = 24;
		byte bb[] = serialize(s);
		for(byte b : bb){
			System.out.println(b);
		}
		
		System.out.println(deserialize(bb).getClass());;
	}
}







