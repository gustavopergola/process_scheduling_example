package com.system;

import com.rowHandler.RealTimeRow;
import com.rowHandler.Row;
import com.rowHandler.SubmissionRow;
import com.rowHandler.UserRow;
import com.util.MemoryList;

public class Scheduler {
		private int lastId = 0;
		private int quantum = 2;
		private RealTimeRow rtr = new RealTimeRow();
		private UserRow ur = new UserRow();
		private SubmissionRow sr = new SubmissionRow(this);
		private MemoryList memory = new MemoryList ();
		
		public Scheduler (){}
		
		public Scheduler (int quantum){
			this.quantum = quantum;	
		}	

		public void submit (Process process){
			process.setId(++lastId);
			sr.submit(process);
		}
		
		public String toString(){
			String answer = " \n Submission Row: \n";
			answer += sr.toString() + "\n";
			answer += "Real Time Row: \n";
			answer += rtr.toString() + "\n";
			answer += "User Row: \n";
			answer += ur.toString() + "\n";
			answer += "Scheduler END \n";
			return answer;
		}
		
		public Row getSubmissionRow (){
			return this.sr;
		}
		
		public Row getRealTimeRow (){
			return this.rtr;
		}
		
		public Row getUserRow (){
			return this.ur;
		}
		
		public void checkMemorySpace (Process process){
			
			// if there's no memory space, we should swap out some processes
			if (memory.checkSizeAvailability(process.getSize()) == null){
				swapOut();
			}
				
		}

		private void swapOut() {
			memory.removeProcess(ur.LRU());
		}


		
}
