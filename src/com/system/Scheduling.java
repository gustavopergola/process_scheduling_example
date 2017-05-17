package com.system;

import java.util.ArrayList;
import com.util.ProcessList;

public class Scheduling {
		private int lastId = 0;
		private int quantum = 2;
		ArrayList <ProcessList> lists;
		
		public Scheduling (){}
		
		public Scheduling (int quantum){
			this.quantum = quantum;	
		}	

		

}
