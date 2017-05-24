package com.system;

import java.util.ArrayList;

import com.rowHandler.RealTimeRow;
import com.rowHandler.Row;
import com.rowHandler.SubmissionRow;
import com.rowHandler.UserRow;
import com.util.MemoryList;

public class Scheduler implements Runnable{
		private int lastId = 0;
		private int quantum = 2;
		private RealTimeRow rtr = new RealTimeRow();
		private UserRow ur = new UserRow();
		private SubmissionRow sr = new SubmissionRow(this);
		private MemoryList memory = new MemoryList ();
		private int admitDelay = 4;
		
		private ArrayList <Row> feedbackQueue = new ArrayList<Row>(3);
		private Row fcfsQueue = new Row();
		
		public Scheduler (){
			feedbackQueue.add(new Row()); // feedback row 1
			feedbackQueue.add(new Row()); // feedback row 2
			feedbackQueue.add(new Row()); // feedback row 3
		}
		
		public Scheduler (int quantum){
			this.quantum = quantum;	
		}	

		public void submit (Process process){
			process.setId(++lastId);
			sr.submit(process);
		}
		
		private void admitAll(){
			while (rtr.getList().getFirst() != null)
				fcfsQueue.submit(rtr.getList().pop());
			while (ur.getList().getFirst() != null){
				feedbackQueue.get(0).submit(rtr.getList().pop()); // admits process to first row of the feedback queue
			}		
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
		
		public Process request(){
			// TODO: Make it a feedback politic with FCFS for priority 0 processes
			if ()
		}

		public int getQuantum() {
			// TODO Auto-generated method stub
			return this.quantum;
		}

		@Override
		public void run() {
			sr.defineRows(); // this will allocate memory
			this.admitAll(); // admits processes
		}

		
}
