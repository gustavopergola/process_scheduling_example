package com.system;

import java.util.ArrayList;

public class Processor {	
	/**private ArrayList<CPU> cores;

	public Processor (int cores){
		this.cores = new ArrayList <CPU>();
		for (int i = 0; i < cores; i++){
			this.cores.add(new CPU());
		}
	}

	public CPU execute (Process process){
		CPU emptyCore = checkEmpty();
		if (emptyCore != null){
			emptyCore.execute(process);
			return emptyCore; 	
		}	
		return null;
	}
	
	/// return first empty core pointer (some cores may be 
	/// executing a high priority task
	/// returns null in case none were found
	private CPU checkEmpty (){
		for (int i = 0; i < this.cores.size(); i++)
			if(this.cores.get(i).empty())
				return this.cores.get(i);
		return null;
	}**/
	
}
