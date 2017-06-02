package com.rowHandler;

import com.system.Process;
import com.util.ProcessList;
import com.util.ProcessNode;

public class Row {
	protected ProcessList list;
	
	public Row (){
		this.list = new ProcessList();
	}

	public ProcessList getList() {
		return list;
	}

	public void setList(ProcessList list) {
		this.list = list;
	}
	
	public void submit (Process process){
		if (process == null) return;
		list.insert(process);
	}
	
	public String toString (){
		 String answer = "\t ";
		 ProcessNode node = list.getFirst();
		 while (node != null){
			 if (node.process != null) answer += " P" + node.process.getId();
			 node = node.next;
		 }
		 return answer;
	}
	
	public boolean remove (Process process){
		return list.remove(process);
	}
	
	public Process getNextProcess(){
		return list.getNextProcess();
	}
	
	public int size(){
		return list.size();
	}
	
}
