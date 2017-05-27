 package com.rowHandler;

import com.system.Process;
import com.system.Scheduler;

public class SubmissionRow extends Row {
	private Scheduler scheduler;
	
	public SubmissionRow(Scheduler scheduler) {
		super();
		this.scheduler = scheduler; 
	}
	
	public void admitAll(){
		while (super.getList().getFirst() != null)
			this.scheduler.submit(super.getList().pop());
	}
	
}
