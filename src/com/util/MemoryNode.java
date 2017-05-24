package com.util;

import com.system.Process;

public class MemoryNode {
	public MemoryNode next;
	public MemoryNode previous;
	public Process process;
	public int size;
	
	public MemoryNode(int size){
		this.size = size;
		next = null;
	}
	
	public MemoryNode(int size, Process process){
		this.size = size;
		this.process = process;
		next = null;
	}
}
