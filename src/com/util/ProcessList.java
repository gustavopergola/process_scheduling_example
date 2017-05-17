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
}
