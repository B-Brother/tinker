package com.alibaba.tinker.service.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class TransferDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private int id;
	
	private String name;
	
	private Date d = new Date();
	
	private Map<String, String> map = new HashMap<String, String>();

	public void put2Map(String key, String value){
		map.put(key, value);
	}
	
	public String get(String key){
		return map.get(key);
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getD() {
		return d;
	}

	public void setD(Date d) {
		this.d = d;
	} 
}
