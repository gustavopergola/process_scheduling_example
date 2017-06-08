package com.system;

import java.util.ArrayList;

import com.rowHandler.SubmissionRow;

public class Processor {
	
	private ArrayList <CPU> cpuList = new ArrayList <CPU> (4);
	static private int clock = 0;
	public boolean fcfsDone = false;
	public boolean feedbackDone = false;
	private SubmissionRow sr = null;
	protected Memory memory;
	public boolean feedbackStopFlag = false;
	public boolean fcfsStopFlag = false;
	public ArrayList <Process> toBeremoved = new ArrayList<Process>();
	
	public Processor (int cores){
		if (cores <= 0) 
			cores = 1;
		for (int i = 0; i < cores; i++) 
			cpuList.add(new CPU(i + 1, this));
	}
	
	public void setMemory (Memory memory){
		this.memory = memory;
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
	
	static public int getClock (){
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
		if ((this.fcfsDone && this.feedbackDone)){
			this.fcfsDone = false;
			this.feedbackDone = false;
			
			clock++;
			
			this.memory.fixFragmentation();
			
			if(sr != null){
				sr.admitAll();
			}
			
			System.out.printf("CLOCK: %d", Processor.getClock());
			
			// execute CPU's 
			for (int i = 0; i < cpuList.size(); i++){
				if (cpuList.get(i).getExecuting() != null)
					cpuList.get(i).getExecuting().lastTimeUsed = Processor.clock;
				cpuList.get(i).execute();
			}
			
			// remove suspended processes
			for (int j = 0; j < toBeremoved.size(); j++){
				for (int i = 0; i < cpuList.size(); i++){
					if (cpuList.get(i).getExecuting() != null){
						if (cpuList.get(i).getExecuting().equals(toBeremoved.get(j))){
							cpuList.get(i).setExecuting(null);
						}
					}
					
				}
			}
			
			
			System.out.printf("\n");
			
			//System.out.println(this.memory.toString());
		}
	}

	public void removeProcess(Process process){
		this.toBeremoved.add(process);
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

	public ArrayList<CPU> getCPUList(){
		return this.cpuList;
	}

}

