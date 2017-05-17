package com.system;

import java.util.ArrayList;

public class Process {
	private int priority;
	private ArrayList<Resource> resources;
	public int id; 
		
	public Process(int priority){
		this.priority = priority;
		this.resources = new ArrayList<Resource>();	
	}
	
	public int getPriority (){
		return this.priority;
	}
	
}
