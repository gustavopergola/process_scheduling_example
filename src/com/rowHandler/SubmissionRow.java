 package com.rowHandler;

import com.system.Process;
import com.system.Scheduler;

public class SubmissionRow extends Row {

	private RealTimeRow rtr;
	private UserRow ur;
	private Scheduler scheduler;
	
	public SubmissionRow(Scheduler scheduler) {
		super();
		rtr = new RealTimeRow();
		ur = new UserRow();
		this.scheduler = scheduler; 
	}
	
	// admits n processes on the submission row
	public void admit (int n){
		for (int i = 0; i < n; i ++)
			if (admit())
				break;
	}
	
	// admits next process on the submission row (FCFS)
	public boolean admit (){
		Process process = super.list.pop();
		if (process == null) return false;
		else {
			// checks if there's enough memory space for the new process, swap out if needed
			this.scheduler.checkMemorySpace(process);
			
			if (process.getPriority() == 0){
				rtr.submit(process);
			}else
				ur.submit(process);
			return true;
		}
	}
	
}
