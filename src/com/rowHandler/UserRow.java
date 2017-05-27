package com.rowHandler;

import com.system.Process;
import com.util.*;
public class UserRow extends Row {

	public UserRow() {
		super();
	}
	
	/**public Process LRU (){
		// iterates to find least recently used process with priority 3
		// in case it it doesn't find any priority 3 process, do the same with priority 2 and 1
		
		Process process = null;
		ProcessNode aux;
		aux = super.list.getFirst();
		
		// check only for priority 3 processes
		while (aux != null){
			if (aux.process.getPriority() == 2){
				if (process == null){
					process = aux.process;
				}else{
					if(process.lastTimeUsed > aux.process.lastTimeUsed) {
						process = aux.process;
					}
				}
			}
			aux = aux.next;
		}
		
		
		// check only for priority 2 processes
		while (aux != null){
			if (aux.process.getPriority() == 2){
				if (process == null){
					process = aux.process;
				}else{
					if(process.lastTimeUsed > aux.process.lastTimeUsed) {
						process = aux.process;
					}
				}
			}
			aux = aux.next;
		}
		
		// check only for priority 1 processes if we didn't find any priority 2 process
		if (process == null){
			while (aux != null){
				if (aux.process.getPriority() == 1){
					if (process == null){
						process = aux.process;
					}else{
						if(process.lastTimeUsed > aux.process.lastTimeUsed) {
							process = aux.process;
						}
					}
				}
				aux = aux.next;
			}
		}
		
		return process;
		
	}**/

}
