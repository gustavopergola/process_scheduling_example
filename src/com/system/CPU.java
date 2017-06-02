package com.system;

public class CPU{
	private Process executing;
	private int coreId;
	int quantumCounter;
	
	public CPU (int coreId) {
		this.coreId = coreId;
	}

	public void execute(Process process)  {
		// TODO checar se é preemptivo para FCFS
		// TODO deixar de deixar sempre vazio
		if (process != null){
			if (process.getTimeLeft() > 0) process.setTimeLeft(process.getTimeLeft() - 1);
		}
			
	}
	
	public boolean empty (){
		return executing == null;
	}
	
	public Process getExecuting (){
		return executing;
	}

	public String toString (){
		return "CPU" + this.coreId;
	}
	
}
