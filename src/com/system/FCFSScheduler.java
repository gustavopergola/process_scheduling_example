package com.system;

import com.rowHandler.Row;

public class FCFSScheduler extends Scheduler {
	private Row fcfsQueue = new Row();
	public FCFSScheduler (){
		super();
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
	
}
