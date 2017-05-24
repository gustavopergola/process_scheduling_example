package com.system;

public class Core implements Runnable {
	private Process executing;
	private int coreId;
	private Scheduler scheduler;
	
	public Core (Scheduler scheduler) {
		this.scheduler = scheduler;
	}

	public boolean execute (Process process)  {
		if 	(executing != null) return false;
		else{
			this.executing = process;
			return true;
		}
	}
	
	public boolean empty (){
		return executing == null;
	}
	
	public Process getExecuting (){
		return executing;
	}

	@Override
	public void run() {
		if (executing.getTimeLeft() == 0){
			// request another process
			executing = this.scheduler.request();
			if (executing != null){
				executing.setTimeLeft(executing.getTimeLeft() - 1);
			}
		}else {
			executing.setTimeLeft(executing.getTimeLeft() - 1);
		}
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
}
