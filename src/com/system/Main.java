package com.system;
import com.rowHandler.SubmissionRow;
import com.util.ProcessList;
public class Main {
	public static void main(String[] args) {

		System.out.println("Bem-vindo!");
		
		// Declare quad-core processor
		Processor processor = new Processor(4);
		
		// Declare a process priority 1
		Process process1 = new Process(1);
		Process process2 = new Process(2);
		
		// Initialize quantum 2 process scheduler (manages rows)
		Scheduling scheduler = new Scheduling();
		
		scheduler.submit(process1);
		scheduler.submit(process2);
		
		System.out.println(scheduler.toString());
		
		System.out.println("Até logo!");
		
	}

}
