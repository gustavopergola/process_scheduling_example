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
				while (auxNode.next != null){
					auxNode = auxNode.next;
				}
					
				auxNode.next = node;
				return node;
			}else {
				first.next = node;
				return node;
			}
		}
		
	}
	
	public boolean remove(Process process){
		ProcessNode aux = first;
		ProcessNode ant = null;
		while (aux != null){
			if (aux.process.equals(process)){
				// found the process
				if (ant != null){
					ant.next = aux.next;
				}else {
					first = aux.next;
				}
				return true;
			}
			ant = aux;
			aux = aux.next;
		}
		return false;
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
	
	public Process get (int index){
		ProcessNode aux = first;
		while (aux != null){
			if (index == 1)
				return aux.process;
			else 
				aux = aux.next;
		}
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
	
	public Process isEmpty(){
		if (this.first == null) return null;
		return this.first.process;
	}

	public Process getNextProcess() {
		if (this.first == null) return null;
		ProcessNode aux = this.first;
		Process first = this.first.process;
		while (aux != null){
			if (aux.process.getArrivalTime() < first.getArrivalTime()){
				first = aux.process;
			}
			aux = aux.next;
		}
		if (first != null){
			remove(first);
			return first;
		}
		return null;
	}

	public int size() {
		int n = 0;
		ProcessNode aux = first;
		while (aux != null){
			n++;
			aux = aux.next;
		}
		return n;
	}
}
