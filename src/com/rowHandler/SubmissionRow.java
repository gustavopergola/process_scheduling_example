 package com.rowHandler;

import com.system.Process;
import com.system.Scheduler;

public class SubmissionRow extends Row {

	private RealTimeRow rtr;
	private UserRow ur;
	private Scheduler scheduler;
	
	public SubmissionRow(Scheduler scheduler, RealTimeRow rtr, UserRow ur) {
		super();
		this.scheduler = scheduler; 
		this.ur = ur;
		this.rtr = rtr;
	}
	
	// admits next process on the submission row (FCFS)
	public void defineRows (){
		if (super.list.getFirst() == null){
			return;
		}
		Process process = super.list.pop();
	
		// checks if there's enough memory space for the new process, swap out if needed
		//this.scheduler.checkMemorySpace(process);
		
		if (process.getPriority() == 0){
			rtr.submit(process);
		}else{
			ur.submit(process);
		}
		
		defineRows();
	
	}
	
}
