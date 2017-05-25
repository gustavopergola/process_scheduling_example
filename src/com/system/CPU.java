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
		boolean requested = false;
		
		if (executing == null) executing = this.scheduler.request(); // request another process
		
		if (executing != null){
			executing.executing = true;
			System.out.println(executing.toString());
		}else {
			System.out.println("Sem processo!");
		}

		if (executing != null){
			executing.setTimeLeft(executing.getTimeLeft() - 1);
			if (executing.getTimeLeft() <= 0){
				scheduler.endProcess(executing);
				executing = this.scheduler.request(); // request another process
				requested = true;
				quantumCounter = this.scheduler.getQuantum() + 1;  // plus one because we are subtracting at the end of the method and the process hasn't started executing yet
			}
		}
		if (executing != null){
			if (executing.getPriority() != 0)
				quantumCounter--;
			else
				quantumCounter = this.scheduler.getQuantum();
		}
		
		
		try {
			Thread.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		if (quantumCounter <= 0){
			if (!requested){
				executing.executing = false;
				executing = this.scheduler.request();
			}
			quantumCounter = this.scheduler.getQuantum();
		}
		
		this.run ();
	}
}
