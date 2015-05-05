package com.alibaba.tinker.hessian.test;

import java.io.Serializable;
import java.util.Date;

public class People implements Serializable{
	private String name = "abc";
	
	private String college = "sust";
			
	private int age =232;
	
	private Date now = new Date();
}
