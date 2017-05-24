package com.system;

public class CPU implements Runnable {
	private Process executing;
	private int coreId;
	private Scheduler scheduler;
	int quantumCounter;
	
	public CPU (Scheduler scheduler) {
		this.scheduler = scheduler;
		quantumCounter = this.scheduler.getQuantum();
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
		
		if (executing == null){
			// request another process
			executing = this.scheduler.request();
		}
		
		if (executing != null){
			System.out.println(executing.toString());
		}else {
			System.out.println("Sem processo!");
		}

		if (executing != null){
			executing.setTimeLeft(executing.getTimeLeft() - 1);
			if (executing.getTimeLeft() <= 0){
				// request another process
				executing = this.scheduler.request();
				// plus one because we are subtracting at the end of the method and the process hasn't started executing yet
				quantumCounter = this.scheduler.getQuantum() + 1; 
			}
		}
		
		quantumCounter--;
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		if (quantumCounter <= 0){
			executing = this.scheduler.request();
			quantumCounter = this.scheduler.getQuantum();
		}
		
		this.run ();
	}
}
