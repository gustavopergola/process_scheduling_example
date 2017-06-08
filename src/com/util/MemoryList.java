package com.util;

import com.system.Process;

public class MemoryList {
	public MemoryNode first = new MemoryNode(1024);
	
	public MemoryList (){}
	
	public boolean insertProcess(Process process){
		MemoryNode avaiableSpace = checkSizeAvailability(process.getSize());
		if (avaiableSpace != null){
			
			avaiableSpace.size -= process.getSize();
			
			MemoryNode processMemoryNode = new MemoryNode(process.getSize());
			processMemoryNode.process = process;
			
			processMemoryNode.previous = avaiableSpace.previous;
			processMemoryNode.next = avaiableSpace;
			
			if (avaiableSpace.previous != null){
				avaiableSpace.previous.next = processMemoryNode;
			}else {
				this.first = processMemoryNode;
			}
			
			avaiableSpace.previous = processMemoryNode;
			
			return true;
		}
		
		return false;
		
	}

	public void fixFragmentation(){
		boolean flag = true;
		while (flag){
			flag = false;
			MemoryNode aux = this.first;
			while (aux != null){
				if (aux.process == null){
					if (aux.next != null){
						if (aux.next.process == null){
							flag = true;
							aux.size += aux.next.size;
							// remove next manually without incrementing size
							if (aux.next.next != null) aux.next.next.previous = aux;
							aux.next = aux.next.next;
						}
					}
				}
				aux = aux.next;
			}
		}
	}
	
	public void removeProcess (MemoryNode node){
		if (node == null)
			return;
		
		if (node.process == null)
			return;
		
		node.process = null;
		
		if (node.next != null){
			if (node.next.process == null){
				node.next.size += node.size;
				node.next.previous = node.previous;
				if(node.previous != null)
					node.previous.next = node.next;
				else
					this.first = node.next;
				node = node.next;
			}	
		}
		
		if (node.previous != null){
			if (node.previous.process == null){
				node.previous.size += node.size;
				node.previous.next = node.next;
				if(node.next != null)
					node.next.previous = node.previous;
			}
		}	
		
	}
	
	public void removeProcess (Process process){
		if (process != null){
			removeProcess(search(process));
		}
	}
	
	private MemoryNode search(Process process) {
		if (first.process != null){
			if (first.process.equals(process)){
				return first;
			}
		}
		return search(process, first);
	}

	private MemoryNode search(Process process, MemoryNode node) {
		if (node == null){
			return null;
		}else {
			if (node.process != null){
				if (node.process.equals(process)) return node;
			}
			return search(process, node.next);
		}
	}

	public MemoryNode checkSizeAvailability(int size){
		if (first.size >= size && first.process == null)
			return first;
		else 
			return checkSizeAvailability(size, first.next);
	}
	
	private MemoryNode checkSizeAvailability(int size, MemoryNode node){
		
		if (node == null)
			return null;
		
		if (node.size >= size && node.process == null)
			return node;
		
		return checkSizeAvailability(size, node.next);
		
		
	}
	
	public String toString(){
		String aux = "";
		MemoryNode node = this.first;
		while (node != null){
			if (node.process != null){
				aux += node.size + "(" + node.process.toString() + ")-> ";
			}else {
				aux += node.size + "-> ";
			}
			node = node.next;
		}
		aux += "\n";
		node = this.first;
		while (node.next != null) node = node.next;
		while (node != null){
			if (node.process != null){
				aux += node.size + "(" + node.process.toString() + ")-> ";
			}else {
				aux += node.size + "-> ";
			}
			node = node.previous;
		}
		return aux;
	}
	
}
