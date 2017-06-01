package com.system;

import java.util.ArrayList;

public class Processor {
	private ArrayList <CPU> cpuList = new ArrayList <CPU> (4);
	
	public Processor (int cores){
		if (cores <= 0) 
			cores = 1;
		for (int i = 0; i < cores; i++) 
			cpuList.add(new CPU(i + 1));
		
	}
	
	public int getNumberOfCores (){
		return cpuList.size();
	}
	
	public CPU getFreeCPU (){
		for (int i= 0; i < cpuList.size(); i++){
			if (cpuList.get(i).empty()) return cpuList.get(i);
		}
		return null;
	}
}
