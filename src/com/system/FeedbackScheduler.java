package com.system;

import java.util.ArrayList;

import com.rowHandler.Row;

public class FeedbackScheduler extends Scheduler implements Runnable {
	
	private ArrayList <Row> userQueue = new ArrayList<Row>(3);
	private Processor processor;
	private Process process = null;
	private CPU freeCPU = null;
	private boolean firstQuantum = true;
	private boolean stopFlag = false;
	
	public FeedbackScheduler (Processor processor) {
		super();
		userQueue.add(new Row());
		userQueue.add(new Row());
		userQueue.add(new Row());
		this.processor = processor;
	}
	
	public Processor getProcessor (){
		return this.processor;
	}
	
	public Row getUserQueue (int id){
		return userQueue.get(id - 1);
	}
	
	private Process request(int n){
		int sum = 0;
		Process aux = null;
		
		/** checks if n is out of bounds **/
		for (int i = 0; i < userQueue.size(); i++){
			sum += userQueue.get(i).size();
		}
		
		if (n > sum) return aux;
		/** ends checking if n is out of bounds **/
		
		/** find the nth process **/
		for (int i = 0; i < userQueue.size(); i++){
			if (userQueue.get(i).size() < n) n -= userQueue.get(i).size(); //means that the process is at the next row or after
			else{
				// means that the process is at this row
				aux = userQueue.get(i).get(n);
				
				break;
			}
		}
		/** ends finding nth process **/
		
		/** returns process **/
		return aux;
	
		// backup
		/**Process process = null;

		for (int i= 0; i < userQueue.size(); i++ ){
			
			process = userQueue.get(i).getList().pop();
			
			// troca de fila
			if (jump){
				
			}
			if (process != null) return process;
		}
		return null;**/
	}
	
	private void skipRow(Process process){
		for (int i = 0; i< userQueue.size(); i++){
			if (userQueue.get(i).remove(process)) // checks in which row the process is before removing it
				if (i + 1 >= userQueue.size()) userQueue.get(0).submit(process); // in case it's the last row
				else userQueue.get(i + 1).submit(process);
		}
	}
	
	public boolean submit (Process process){
		if (process != null){
			// TODO: CHECK MEMORY SPACE
			getUserQueue(1).submit(process);
			return true;
		}
		return false;
	}
	
	public boolean withdraw (Process process){
		for (int i = 0; i < userQueue.size(); i ++){
			if (userQueue.get(i).remove(process)){
				return true;
			}
		}
		return false;
	}
	
	private boolean clean() {
		Process aux;
		for (int i = 0; i < userQueue.size(); i++){
			for (int j = 0; j < userQueue.get(i).size(); j++){
				aux = userQueue.get(i).get(j+1);
				if (aux != null){
					if (aux.getTimeLeft() <= 0){
						this.withdraw(aux);
						return true;
					}
				}
			}
		}
		return false;
	}

	@Override
	public void run (){
		
		// checks if there is a free CPU avaiable
		freeCPU = this.processor.getFreeCPU();
		
		if (freeCPU != null){
			// if tehere's a free CPU available check if there's still a process to be executed
			int n = 1;
			
			while (true){
				process = this.request(n);
				if (process != null){
					// if there's a process at user queue, check if the same process isn't being executed already at a CPU
					if (this.processor.searchProcess(process) == true){
						// if it is, get another one
						n++;
					}else {
						// if it isn't, we found the process which has priority in being executed, finally
						freeCPU.setExecuting(process);
						// skip the process row at the user queue
						skipRow(process);
						break;
					}
				}else {
					// there is no process at user queue, break the iteration
					break;
				}
			}
		}else {
			// there's no CPU avaiable so we do nothing because FCFS has priority
		}
		
		// tenta pular o clock (É protegido pelo processor para que esteja sincronizado)
		this.processor.feedbackDone = true;
		this.processor.incrementClock();
		
		while (this.clean()){}
		
		try {
			Thread.sleep(900);
			if (this.processor != null){
				if (this.processor.getSubmissionRow() != null){
					// checks if submission row is empty
					// checks if all queues are empty
					// TODO break execution
					if (this.processor.getSubmissionRow().size() <= 0){
						if (this.userQueue.get(0).size() <= 0 && this.userQueue.get(1).size() <= 0 && this.userQueue.get(2).size() <= 0){
								stopFlag = true;
						}
					}
				}
			}
			
			// recursive call
			if (!stopFlag)
				run ();
			else 
				System.out.println("User requests ended");
				
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
}