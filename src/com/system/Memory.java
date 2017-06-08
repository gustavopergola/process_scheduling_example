package com.system;

import com.util.MemoryList;
import com.util.MemoryNode;

public class Memory {
	private MemoryList list = new MemoryList();
	private FeedbackScheduler feedbackScheduler;
	
	public void insertRealTimeRequest (Process process){
		if (this.list.insertProcess(process)){
			return;
		}else {
			// suspend a user process
			this.suspendLRU();
			this.list.insertProcess(process);
			return;
		}
	}

	public boolean insertUserRequest (Process process){
		if (this.list.checkSizeAvailability(process.getSize()) != null){
			this.list.insertProcess(process);
			return true;
		}
		return false;
		
	}
	
	private boolean suspendLRU() {
		System.out.println("LRU called");
		// suspend least recently used user process
		MemoryNode aux  = list.first;
		Process LRUProcess = null;
		while (aux != null){
			if (aux.process != null){
				if (aux.process.getPriority() > 0){
					if (LRUProcess == null )
						LRUProcess = aux.process;
					else{
						if (LRUProcess.lastTimeUsed > aux.process.lastTimeUsed)
							LRUProcess = aux.process;
						if (LRUProcess.lastTimeUsed == aux.process.lastTimeUsed)
							if (LRUProcess.getPriority() < aux.process.getPriority())
								LRUProcess = aux.process;
					}
				}
			}
			aux = aux.next;
		}
		
		if (LRUProcess == null) return false; // all the processes are real time requests or there's no need to suspend
		this.feedbackScheduler.suspend(LRUProcess);
		list.removeProcess(LRUProcess);
		
		return true;
		
	}
	
	public void remove (Process process){
		this.list.removeProcess(process);
		
	}
	
	public void fixFragmentation (){
		list.fixFragmentation();
	}
	public String toString(){
		return this.list.toString();
	}

	public void setFeedbackScheduler(FeedbackScheduler feedbackScheduler) {
		this.feedbackScheduler = feedbackScheduler;
	}
}
