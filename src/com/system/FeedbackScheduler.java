package com.system;

import java.util.ArrayList;

import com.rowHandler.Row;

public class FeedbackScheduler extends Scheduler implements Runnable {
	
	private ArrayList <Row> userQueue = new ArrayList<Row>(3);
	private Processor processor;
	private CPU freeCPU = null;
	
	public FeedbackScheduler (Processor processor) {
		super();
		userQueue.add(new Row());
		userQueue.add(new Row());
		userQueue.add(new Row());
		this.processor = processor;
		
	}
	
	public Row getUserQueue (int id){
		return userQueue.get(id - 1);
	}
	
	private Process request(){
		Process process = null;
		for (int i= 0; i < userQueue.size(); i++ ){
			process = userQueue.get(i).getList().pop();
			
			// troca de fila
			if (i + 1 >= userQueue.size()) userQueue.get(0).submit(process); // caso seja a ultima fila
			else userQueue.get(i + 1).submit(process); // caso seja a primeira ou a segunda ou alguma no meio
				
			if (process != null) return process;
		}
		return null;
	}
	
	public boolean submit (Process process, int processId){
		if (process != null){
			// TODO: CHECK MEMORY SPACE
			process.setId(processId);
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

	@Override
	public void run (){
		Process process = null;
		
		freeCPU = this.processor.getFreeCPU();
		
		// checa se tem cpu livre
		if (freeCPU != null){
			// pega novo processo da fila
			process = this.request();
			// executa um ciclo
			freeCPU.execute(process);
			
			// TODO checa interrup��o do fcfs
			
			// se n tiver interrup��o, checa se o processo j� acabou, pega outro processo caso contr�rio
			if (process != null){
				if (process.getTimeLeft() > 0){
					freeCPU.execute(process);
				}else {
					this.withdraw(process);
					process = this.request();
				}
			}
			
		}
		

		// checa se o processo terminou e imprime se for o caso
		if (process != null && freeCPU != null){
			if (process.getTimeLeft() == 0){
				this.withdraw (process);
			}
			System.out.println(process.toString() + " " + process.getTimeLeft());
		}
		
		// tenta pular o clock (é protegido pelo processor para que esteja sincronizado)
		this.processor.feedbackDone = true;
		this.processor.incrementClock();
		
		try {
			Thread.sleep(49);
			run ();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}