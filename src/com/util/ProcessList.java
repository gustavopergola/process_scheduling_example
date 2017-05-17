package com.util;
import com.system.Process;
public class ProcessList {
	private ProcessNode first;
	
	public ProcessList(){}

	public ProcessNode insert (Process process){
		ProcessNode node = new ProcessNode(process);
		
		if (first == null){	
			first = node;
			return node;
		}else {
			ProcessNode auxNode = first.next;
			if (auxNode != null){ 
				while (auxNode.next != null)
					auxNode = auxNode.next;
				auxNode.next = node;
				return node;
			}else {
				first.next = node;
				return node;
			}
		}
	}
	
	public void show (){
		ProcessNode node = first;
		while (node != null){
			System.out.println("Process: " + node.process.getPriority());
			node = node.next;
		}
	}
	
	public ProcessNode getFirst (){
		return first;
	}
	
	public Process getFirstProcess (){
		if (first != null) return first.process;
		return null;
	}
	
	public Process pop(){
		if (first == null) return null;
		
		Process process = first.process;
		if (first.next == null)
			first = null;
		else 
			first = first.next;
		
		return process;
		
		
	}
}
