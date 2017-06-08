package com.rowHandler;

import com.system.FCFSScheduler;
import com.system.FeedbackScheduler;
import com.system.Logger;
import com.system.Processor;

public class SubmissionRow extends Row {
	private FeedbackScheduler feedbackScheduler;
	private FCFSScheduler fcfsScheduler;

	public SubmissionRow(FeedbackScheduler feedbackScheduler, FCFSScheduler fcfsScheduler) {
		super();
		this.fcfsScheduler = fcfsScheduler;
		this.feedbackScheduler = feedbackScheduler;
	}

	public void admitAll() {
		while (super.getList().getFirst() != null) {
			if (super.getList().getFirstProcess() != null) {
				this.feedbackScheduler.getProcessor();
				// checks if process already arrived (simulated)
				if (super.getList().getFirstProcess().getArrivalTime() <= Processor.getClock()) {
					Logger.addLogLine(this.getList().getFirstProcess() + " admited!");
					if (super.getList().getFirstProcess().getPriority() > 0)
						this.feedbackScheduler.submit(super.getList().pop());
					else
						this.fcfsScheduler.submit(super.getList().pop());
				} else {
					break;
				}
			}
		}
	}

}