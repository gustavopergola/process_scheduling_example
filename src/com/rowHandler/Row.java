package com.rowHandler;

import com.util.ProcessList;

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
	
	
	
}
