 package com.rowHandler;

import com.system.FCFSScheduler;
import com.system.FeedbackScheduler;
import com.system.Process;
import com.system.Scheduler;

public class SubmissionRow extends Row {
	private FeedbackScheduler feedbackScheduler;
	private FCFSScheduler fcfsScheduler;
	private int lastId = 0;
	
	public SubmissionRow(FeedbackScheduler feedbackScheduler, FCFSScheduler fcfsScheduler) {
		super();
		this.fcfsScheduler = fcfsScheduler;
		this.feedbackScheduler = feedbackScheduler;
	}
	
	public void admitAll(){
		while (super.getList().getFirst() != null)
			if (super.getList().getFirstProcess() != null)
				if (super.getList().getFirstProcess().getPriority() > 0)
					this.feedbackScheduler.submit(super.getList().pop(), ++lastId);
				else
					this.fcfsScheduler.submit(super.getList().pop(), ++lastId);
	}
	
}
