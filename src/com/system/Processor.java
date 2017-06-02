package com.system;

import java.util.ArrayList;

import com.rowHandler.SubmissionRow;

public class Processor {
	private ArrayList <CPU> cpuList = new ArrayList <CPU> (4);
	private int clock = 0;
	public boolean fcfsDone = true;
	public boolean feedbackDone = true;
	private SubmissionRow sr = null;
	
	public Processor (int cores){
		if (cores <= 0) 
			cores = 1;
		for (int i = 0; i < cores; i++) 
			cpuList.add(new CPU(i + 1));
		
	}
	
	public void setSubmissionRow (SubmissionRow sr){
		this.sr = sr;
	}
	
	public SubmissionRow getSubmissionRow(){
		return this.sr;
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
	
	public int getClock (){
		return clock;
	}
	
	public void incrementClock(){
		if (this.fcfsDone && this.feedbackDone){
			// TODO fazer a sincronização no run() do fcfs
			this.fcfsDone = true;
			this.feedbackDone = false;
			this.clock++;
			if (this.clock % 2 == 0){
				if(sr != null){
					sr.admitAll();
				}
			}
		}
	}
}

