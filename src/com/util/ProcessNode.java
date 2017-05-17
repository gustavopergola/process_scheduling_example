package com.util;
import com.system.Process;
public class ProcessNode {
	public ProcessNode next = null;
	public Process process = null;
	

	public ProcessNode(Process process){
		this.next = null;
		this.process = process;
		
	}	

	public ProcessNode (ProcessNode next, Process process){
		this.next = next;
		this.process = process;
	}
	
	

}
