package com.rowHandler;

import com.system.Process;

public class SubmissionRow extends Row {

	private RealTimeRow rtr;
	private UserRow ur;
	
	public SubmissionRow() {
		super();
		rtr = new RealTimeRow();
		ur = new UserRow();
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
			if (process.getPriority() == 0){
				rtr.submit(process);
			}else
				ur.submit(process);
			return true;
		}
	}
	

}
