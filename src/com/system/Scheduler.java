package com.system;

import java.util.ArrayList;

import com.rowHandler.Row;
import com.rowHandler.SubmissionRow;
import com.rowHandler.UserRow;
import com.util.MemoryList;

public abstract class Scheduler{
		protected int lastId = 0;
		protected int quantum = 2;
		protected MemoryList memory = new MemoryList ();
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
