package com.system;

import java.util.ArrayList;

import com.rowHandler.Row;

public class FeedbackScheduler extends Scheduler implements Runnable {
	
	private ArrayList <Row> userQueue = new ArrayList<Row>(3);
	private Processor processor;
	private Process process = null;
	private CPU freeCPU = null;
	public ArrayList <Process> suspended = new ArrayList <Process> ();
	private Memory memory;
	private ArrayList <Resource> resources;
	
	public FeedbackScheduler (Processor processor, Memory memory) {
		
		super();
		userQueue.add(new Row());
		userQueue.add(new Row());
		userQueue.add(new Row());
		this.processor = processor;
		this.memory = memory;
		
		resources = new ArrayList<Resource>(5);	
		
		resources.add(new Resource("Printer")); //0 first printer
		resources.add(new Resource("Printer")); //1 second printer
		resources.add(new Resource("Scanner")); //2 first scanner
		resources.add(new Resource("Modem")); //3 first modem
		resources.add(new Resource("CD")); //4 first CD
		resources.add(new Resource("CD")); //5 second CD	
	}
	
	public ArrayList <Resource> getResources(){
		return this.resources;
	}
	
	public Processor getProcessor (){
		return this.processor;
	}
	
	public Row getUserQueue (int id){
		return userQueue.get(id - 1);
	}
	
	public Row getSuspended (){
		Row aux = new Row ();
		for (int i = suspended.size()-1; i >= 0; i--){
			aux.submit(suspended.get(i));
		}
		return aux;
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
	}
	
	private void skipRow(Process process){
		for (int i = 0; i< userQueue.size(); i++){
			if (userQueue.get(i).remove(process)){
				// checks in which row the process is before removing it
				if (i + 1 >= userQueue.size()) userQueue.get(0).submit(process); // in case it's the last row
				else userQueue.get(i + 1).submit(process);
				//Logger.addLogLine(process.toString() + "changed feedback row.");
			}
				
		}
	}
	
	public boolean submit (Process process){
		if (process != null){
			if (checkResourcesAvailability(process)){
				if (memory.insertUserRequest(process)){
					getUserQueue(1).submit(process);
					process.state = "ready";
					setResourcesState(process.getResources(), true);
					
				}else {
					boolean found = false;
					for (int i = 0; i < suspended.size(); i++){
						if(suspended.get(i).equals(process)){
							found = true;
							break;
						}
					}
					if (!found){
						this.suspended.add(process);
						process.state = "suspended";
						Logger.addLogLine(process.toString() + "suspended due to lack of memory space!");
					}
					return false;
				}
			}else {
				boolean found = false;
				for (int i = 0; i < suspended.size(); i++){
					if(suspended.get(i).equals(process)){
						found = true;
						break;
					}
				}
				if (!found){
					this.suspended.add(process);
					process.state = "suspended";
					Logger.addLogLine(process.toString() + "suspended due to lack of resource availability!");
				}
				return false;
			}
			return true;
		}
		return false;
	}
	
	private void setResourcesState(ArrayList<Resource> list, boolean state) {
		String lastName = "random";
		for (int i = 0; i < list.size(); i++){
			for (int j = 0; j < resources.size(); j++){
				if (list.get(i).getName().equals(resources.get(j).getName())){
					if (!lastName.equals(resources.get(j).getName())){
						if (resources.get(j).isWorking == !state) resources.get(j).isWorking = state;
						lastName = resources.get(j).getName();
					}
				}
				
				boolean two = false;
				// check if it has more than one request of this resource type
				if (i+1 < list.size()){
					//next index is not out of bound
					if (list.get(i + 1).getName().equals(list.get(i))){
						// next resource is the same as the actual one, so we need two resources of the type
						two = true;
					}
				}
				
				if (two){
					// checks if next resource is also available
					if(j + 1 < resources.size()){
						//it's not out of bounds so lets check availability
						if (resources.get(j + 1).isWorking == !state && resources.get(j + 1).getName().equals(resources.get(j).getName())){
							resources.get(j + 1).isWorking = state;
						}
						i++; // jump process resource bc we already checked
					}
				}
			}
		}
		
	}

	private boolean checkResourcesAvailability(Process process) {
		
		for (int i = 0; i < process.getResources().size(); i++){
			for (int j = 0; j < resources.size(); j++){
				if (process.getResources().get(i).getName().equals(resources.get(j).getName())){
					if (resources.get(j).isWorking){
						// found the resource but it is not idle
						// check next
						if (j + 1 < resources.size()){
							// in bounds
							if (resources.get(j + 1).isWorking && resources.get(j + 1).getName().equals(resources.get(j).getName()) ){
									// it's also not idle
									System.err.println("Next has the same name and it's also working");
									return false;
							}else {
								// check if it's the same name. If it's not, then return false
								if (!resources.get(j + 1).getName().equals(resources.get(j).getName())){
									return false;
								}
							}
						}
						
					}
					
					// found the resource and it's idle
					boolean two = false;
					// check if it has more than one request of this resource type
					if (i+1 < process.getResources().size()){
						//next index is not out of bound
						if (process.getResources().get(i + 1).getName().equals(process.getResources().get(i))){
							// next resource is the same as the actual one, so we need two resources of the type
							two = true;
						}
					}
					
					if (two){
						
						// checks if next resource is also available
						if(j + 1 < resources.size()){
							//it's not out of bounds so lets check availability
							if (resources.get(j + 1).isWorking && resources.get(j + 1).getName().equals(resources.get(j).getName())){
								return false;
							}
							i++; // jump process resource bc we already checked
						}
					}
				}
			}
		}
		return true;
	}

	public boolean withdraw (Process process){
		for (int i = 0; i < userQueue.size(); i ++){
			if (userQueue.get(i).remove(process)){
				setResourcesState(process.getResources(), false);
				return true;
			}
		}
		return false;
	}
	
	public void suspend(Process process) {
		this.withdraw(process);
		this.suspended.add(process);
		process.state = "suspended";
		process.firstQuantum = true;
		this.processor.removeProcess(process);
		Logger.addLogLine(process.toString() + " suspended due to real time priority!");
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

	private void swapInIfPossible(){
		for (int i = 0; i < suspended.size(); i++){
			if (submit (suspended.get(i))){
				suspended.remove(i);
			}
		}
	}
	
	@Override
	public void run (){
			if (this.processor.fcfsDone == true){
				swapInIfPossible();
				
				boolean hasProcess = true;
				
				while (hasProcess){
					// checks if there is a free CPU available
					freeCPU = this.processor.getFreeCPU();
					if (freeCPU == null) break;
					
					if (freeCPU != null){
						// if tehere's a free CPU available check if there's still a process to be executed
						int n = 1;
						
						while (true){
							process = this.request(n);
							
							if (process != null){
								if (process.getTimeLeft() != 0){
									// if there's a process at user queue, check if the same process isn't being executed already at a CPU
									if (this.processor.searchProcess(process) == true){
										// if it is, get another one
										n++;
									}else {
										// if it isn't, we found the process which has priority in being executed, finally
										freeCPU.setExecuting(process);
										Logger.addLogLine(process.toString() + "is now beign executed by CPU" + freeCPU.getCoreId() + ".");
										// skip the process row at the user queue
										skipRow(process);
										break;
									}
								}else {
									n++;
								}
							}else {
								// there is no process at user queue, break the iteration
								hasProcess = false;
								break;
							}
						}
					}else {
						// there's no CPU available so we do nothing because FCFS has priority
					}
				}
				
				// tenta pular o clock (É protegido pelo processor para que esteja sincronizado)
				this.processor.feedbackDone = true;
				this.processor.incrementClock();
				
				while (this.clean()){}
				
		}
		
		try {
			Thread.sleep(1000);
			if (this.processor != null){
				if (this.processor.getSubmissionRow() != null){
					// checks if submission row is empty
					// checks if all queues are empty
					if (this.processor.getSubmissionRow().size() <= 0){
						if (this.userQueue.get(0).size() <= 0 && this.userQueue.get(1).size() <= 0 && this.userQueue.get(2).size() <= 0 && suspended.size() <= 0){
							this.processor.feedbackStopFlag = true;
						}
					}
				}
			}
			
			// recursive call
			if ((!this.processor.feedbackStopFlag) || (!this.processor.fcfsStopFlag))
				run ();
			else {
				System.out.println("User requests ended");
				Thread.currentThread().interrupt();
			}
				
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	

	
}