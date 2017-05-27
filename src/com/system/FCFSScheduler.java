package com.system;

import com.rowHandler.Row;

public class FCFSScheduler extends Scheduler implements Runnable {
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
	
	@Override
	public void run (){
		for (int i =0; i < 100; i++){
			System.out.println(i + "FCFS");
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
