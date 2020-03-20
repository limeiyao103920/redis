package com.bawei;

import java.io.Serializable;

public class User implements Serializable{
	
	  
	    
	private static final long serialVersionUID = -510397397699069080L;

	private String id;
	
	private String name;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + "]";
	}

	public User(String id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public User() {
		super();
	}
	

}
