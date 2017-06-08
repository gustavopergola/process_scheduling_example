package com.system;

import com.rowHandler.Row;

public abstract class Scheduler{
		protected int lastId = 0;
		protected int quantum = 2;
		protected int admitDelay = 4;
		
		protected Row fcfsQueue = new Row();
		
		public Scheduler (){
		}
		
		public Scheduler (int quantum){
			this.quantum = quantum;	
		}	
		
		public void checkMemorySpace (Process process){
			
			// if there's no memory space, we should swap out some processes
			//if (memory.checkSizeAvailability(process.getSize()) == null){
			//	swapOut();
			//}
				
		}
		

		public int getQuantum() {
			// TODO Auto-generated method stub
			return this.quantum;
		}

		
}
