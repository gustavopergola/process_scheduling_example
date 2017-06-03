package com.system;

import java.util.ArrayList;

import com.rowHandler.Row;

public class FCFSScheduler extends Scheduler implements Runnable {
	private Row fcfsQueue = new Row();
	private Process process = null;
	private Processor processor;
	private ArrayList <CPU> executingCPUs = new ArrayList <CPU> (4);
	private CPU freeCPU = null;
	public FCFSScheduler (Processor processor){
		super();
		this.processor = processor;
	}
	
	public boolean submit (Process process){
		if (process != null){
			// TODO CHECK MEMORY SPACE
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
		
		
		this.processor.fcfsDone = true;
		this.processor.incrementClock();
		
		try {
			Thread.sleep(90);
			run ();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		
		// check free CPU
		// interrupts if needed
		// execute process until it ends
		
	}
}
