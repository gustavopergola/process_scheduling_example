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
			
			processMemoryNode.previous = avaiableSpace.previous;
			processMemoryNode.next = avaiableSpace;
			
			if (avaiableSpace.previous != null){
				avaiableSpace.previous.next = processMemoryNode;
			}
			
			avaiableSpace.previous = processMemoryNode;
			
			return true;
		}
		
		return false;
		
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
				return;
			}
		}
		if (node.previous != null){
			if (node.previous.process == null){
				node.previous.size += node.size;
				node.previous.next = node.next;
				if(node.next != null)
					node.next.previous = node.previous;
				return;
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
	
	
}
