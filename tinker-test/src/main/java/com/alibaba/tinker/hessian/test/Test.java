package com.alibaba.tinker.hessian.test;
 
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.caucho.hessian.io.Hessian2Output;

public class Test {
	public static void main(String[] args) throws Exception {
		OutputStream os = new FileOutputStream("D:/test.xml");
		
		Hessian2Output out = new Hessian2Output(os);

		List<String> list = new ArrayList<String>();
		list.add("one");
		list.add("two");
		list.add("three");
 		
		out.writeObject(list);
		 
		out.flush();
		out.close();
		os.close();
		
		
		System.out.println("done.");  
	}
}
