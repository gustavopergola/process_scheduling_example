package com.system;

import java.util.ArrayList;

import com.rowHandler.RealTimeRow;
import com.rowHandler.SubmissionRow;
import com.rowHandler.UserRow;
import com.util.ProcessList;

public class Scheduler {
		private int lastId = 0;
		private int quantum = 2;
		private RealTimeRow rtr = new RealTimeRow();
		private UserRow ur = new UserRow();
		private SubmissionRow sr = new SubmissionRow();
		
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

		
}
