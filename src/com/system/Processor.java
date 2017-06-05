package com.system;

import java.util.ArrayList;

import com.rowHandler.SubmissionRow;

public class Processor {
	private ArrayList <CPU> cpuList = new ArrayList <CPU> (4);
	private int clock = 0;
	public boolean fcfsDone = true;
	public boolean feedbackDone = true;
	private SubmissionRow sr = null;
	private FeedbackScheduler userScheduler;
	private FCFSScheduler realTimeScheduler;
	
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
	
	public boolean isEmpty(){
		for (int i = 0; i < cpuList.size(); i++)
			if (!cpuList.get(i).empty())
				return false;
		return true;
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
	
	public boolean searchProcess (Process process){
		for (int i = 0; i < cpuList.size(); i++)
			if (cpuList.get(i).getExecuting() == process)
				return true;
		return false;
	}
	
	public void incrementClock(){
		// check if all the schedulers are done
		if (this.fcfsDone && this.feedbackDone || (this.realTimeScheduler.stopFlag || this.userScheduler.stopFlag)){
			this.fcfsDone = this.realTimeScheduler.stopFlag;
			this.feedbackDone = this.userScheduler.stopFlag;
			this.clock++;
			
				if(sr != null){
					sr.admitAll();
				}
			
			System.out.printf("CLOCK: %d", this.getClock());
			
			// execute CPU's 
			for (int i = 0; i < cpuList.size(); i++){
				cpuList.get(i).execute();
			}
			
			System.out.printf("\n");
				
		}

	}

	public CPU getUserCPU() {
		int leastPriority = 0;
		for (int i = 0; i < cpuList.size(); i++){
			if (!cpuList.get(i).empty()){
				if (cpuList.get(i).getExecuting().getPriority() > leastPriority){
					return cpuList.get(i);
				}
			}
		}
			
		return null;
	}

	public void setFeedbackScheduler(FeedbackScheduler feedbackScheduler) {
		this.userScheduler = feedbackScheduler;
	}
	
	public void setFCFSScheduler(FCFSScheduler fcfsScheduler) {
		this.realTimeScheduler = fcfsScheduler;
	}
}

