package com.system;

public class CPU{
	private Process executing;
	private int coreId;
	int quantumCounter;
	
	public CPU (int coreId) {
		this.coreId = coreId;
	}
	
	public int getCoreId(){
		return this.coreId;
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
	
	public void execute()  {
		if (executing != null){
			
			executing.setTimeLeft(executing.getTimeLeft() - 1);
			
			System.out.printf("\tCPU%d: %s %d", this.coreId, this.executing.toString(), this.executing.getTimeLeft());
			
			if (executing.getTimeLeft() <= 0){
				executing = null;
			}
		}else {
			System.out.printf("\tCPU%d: XX", this.coreId);
		}
	}
	
	public void setExecuting (Process process){
		this.executing = process;
	}
}
