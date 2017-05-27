package com.system;

import java.util.ArrayList;

import com.rowHandler.Row;

public class FeedbackScheduler extends Scheduler {
	
	private ArrayList <Row> userQueue = new ArrayList<Row>(3);
	
	public FeedbackScheduler () {
		super();
		userQueue.add(new Row());
		userQueue.add(new Row());
		userQueue.add(new Row());
	}
	
	public Row getUserQueue (int id){
		return userQueue.get(id - 1);
	}
	
	public Process request(){
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
	
}