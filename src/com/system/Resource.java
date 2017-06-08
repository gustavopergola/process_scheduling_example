package com.system;

public class Resource {
	private String name;
	public boolean isWorking = false;
	
	public Resource (String name){
		this.name = name;	
	}

	public String getName() {
		return name;
	}

	public void setName(String string) {
		this.name = string;
		
	}
	
}
