package com.system;

import com.rowHandler.Row;

public class FCFSScheduler extends Scheduler implements Runnable {
	private Row fcfsQueue = new Row();
	private Process process = null;
	private Processor processor;
	private CPU freeCPU = null;
	public Memory memory;
	
	public FCFSScheduler (Processor processor, Memory memory){
		super();
		this.processor = processor;
		this.memory = memory;
	}
	
	public boolean submit (Process process){
		if (process != null){
			memory.insertRealTimeRequest(process);
			process.state = "ready";
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
			Thread.sleep(1002);
			if (this.processor.getSubmissionRow().size() <= 0){
				if (this.fcfsQueue.size() <= 0){
					if (this.processor.isEmpty())
						this.processor.fcfsStopFlag = true;
				}
			}
			
			if ((!this.processor.feedbackStopFlag) || (!this.processor.fcfsStopFlag))
				run ();
			else{
				System.out.println("Real Time requests ended");
				Thread.currentThread().interrupt();
			}
			
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		
		}
		
	}
}
