package com.system;

import com.rowHandler.Row;

public class FCFSScheduler extends Scheduler implements Runnable {
	private Row fcfsQueue = new Row();
	private Process process = null;
	private Processor processor;
	private CPU freeCPU;
	public FCFSScheduler (Processor processor){
		super();
		this.processor = processor;
	}
	
	public boolean submit (Process process, int processId){
		if (process != null){
			// TODO CHECK MEMORY SPACE
			process.setId(processId);
			fcfsQueue.submit(process);
			return true;
		}
		return false;
	}
	
	public Row getFcfsQueue (){
		return this.fcfsQueue;
	}
	
	@Override
	public void run (){
		// check queue 
		if (this.fcfsQueue.size() > 0) process = this.fcfsQueue.getList().pop();
		if (process != null){
			
		}
		// check free CPU
		// interrupts if needed
		// execute process until it ends
		
	}
}
