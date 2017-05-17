package com.system;

public class Core {
	private Process executing;
	private int coreId;
	
	public Core () {
	}

	public boolean execute (Process process){
		if 	(executing != null) return false;
		else{
			this.executing = process;
			return true;
		}
	}
	
	public boolean empty (){
		return executing == null;
	}
}
