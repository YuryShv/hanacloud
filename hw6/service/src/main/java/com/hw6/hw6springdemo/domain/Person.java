package com.hw6.hw6springdemo.domain;

import java.sql.Timestamp;
import java.util.List;

public class Person {
	private String id;    
	private String name;
	private Timestamp ts_update;
	private Timestamp ts_create;
	public List<Cars> carList;
	public List<Cars> getCarList() {
		return carList;
	}
	public void setCarList(List<Cars> carList) {
		this.carList = carList;
	}
	public Timestamp getTs_update() {
		return ts_update;
	}
	public void setTs_update(Timestamp ts_update) {
		this.ts_update = ts_update;
	}
	public Timestamp getTs_create() {
		return ts_create;
	}
	public void setTs_create(Timestamp ts_create) {
		this.ts_create = ts_create;
	}
	public String getId() {   
		return id;  
	}  
	public void setId(String id) {   this.id = id;  }
	public String getName() {   return name;  } 
	public void setName(String name) {   this.name = name;  }  
	 
}
