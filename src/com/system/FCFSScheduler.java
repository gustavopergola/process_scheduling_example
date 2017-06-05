package com.system;

import java.util.ArrayList;

import com.rowHandler.Row;

public class FCFSScheduler extends Scheduler implements Runnable {
	private Row fcfsQueue = new Row();
	private Process process = null;
	private Processor processor;
	private CPU freeCPU = null;
	public boolean stopFlag = false;
	
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
		// case 1: free cpu available with 1 process OK!
		// case 2: free cpu available with multiple processes OK!
		// case 3: no cpu available (feedback processes) with 1 process OK!
		// case 4: no cpu available (fcfs processes) with 1 process OK!
		// case 5: no cpu available (fcfs processes) with multiple processes OK!
		// case 6: no cpu available (feedback processes) with multiple processes OK!
		
		process = fcfsQueue.getList().isEmpty();
		
		while(process != null){ // there's at least one process that needs to be executed at real time
			freeCPU = this.processor.getFreeCPU();
			if (freeCPU != null){
				// there's a free CPU available so we don't need to interrupt any other one
				fcfsQueue.getList().getNextProcess(); // withdraw process from list, there's no need to keep him at the row
				freeCPU.setExecuting(process);
				
			}else {
				// there's no CPU available so we need to interrupt a user priority process
				freeCPU = this.processor.getUserCPU();
				if ( freeCPU != null){
					// there is at least one user process blocking the real time execution
					freeCPU.interrupt();
					fcfsQueue.getList().getNextProcess(); // withdraw process from list, there's no need to keep him at the row
					freeCPU.setExecuting(process);
				}else {
					// all CPU's are being used by real time requests, just wait a little longer
					break;
				}
			}
			
			process = fcfsQueue.getList().isEmpty();
		}
		this.processor.fcfsDone = true;
		this.processor.incrementClock();
		
		try {
			Thread.sleep(5);
			if (this.processor.getSubmissionRow().size() <= 0){
				if (this.fcfsQueue.size() <= 0){
					if (this.processor.isEmpty())
						stopFlag = true;
				}
			}
			
			if (!this.stopFlag)
				run ();
			else{
				System.out.println("Real Time requests ended");
				Thread.currentThread().interrupt();
			}
			
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		
		}
		
		
		// check free CPU
		// interrupts if needed
		// execute process until it ends
		
	}
}
