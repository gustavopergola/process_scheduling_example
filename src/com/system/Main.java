package com.system;
import com.util.ProcessList;
public class Main {
	public static void main(String[] args) {

		System.out.println("Bem-vindo!");
		
		// Declare quad-core processor
		Processor processor = new Processor(4);
		
		// Declare a process priority 1
		Process process1 = new Process(1);
		
		// Generate and add process to Process List
		ProcessList processList = new ProcessList();
		
		processList.insert(process1);
		
		System.out.println("Até logo!");
		
	}

}
