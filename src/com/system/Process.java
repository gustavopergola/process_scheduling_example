package com.system;

import java.util.ArrayList;

public class Process {
	private int priority;
	private ArrayList<Resource> resources;
	public int id = 0; 
		
	public Process(int priority){
		this.priority = priority;
		this.resources = new ArrayList<Resource>();	
	}
	
	public int getPriority (){
		return this.priority;
	}
	
	public boolean setId(int id){
		if(this.id == 0){
			this.id = id;
			return true;
		}
		return false;
	}
	
	public int getId(){
		return this.id;
	}
	
}
