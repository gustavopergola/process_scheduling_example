 package com.rowHandler;

import com.system.FCFSScheduler;
import com.system.FeedbackScheduler;
import com.system.Process;
import com.system.Scheduler;

public class SubmissionRow extends Row {
	private FeedbackScheduler feedbackScheduler;
	private FCFSScheduler fcfsScheduler;
	
	public SubmissionRow(FeedbackScheduler feedbackScheduler, FCFSScheduler fcfsScheduler) {
		super();
		this.fcfsScheduler = fcfsScheduler;
		this.feedbackScheduler = feedbackScheduler;
	}
	
	public void admitAll(){
		while (super.getList().getFirst() != null)
			if (super.getList().getFirstProcess() != null)
				// checks if process already arrived (simulated)
				if (super.getList().getFirstProcess().getArrivalTime() <= this.feedbackScheduler.getProcessor().getClock()){
					if (super.getList().getFirstProcess().getPriority() > 0)
						this.feedbackScheduler.submit(super.getList().pop());
					else
						this.fcfsScheduler.submit(super.getList().pop());
				}else {
					break;
				}
				
	}
	
}
