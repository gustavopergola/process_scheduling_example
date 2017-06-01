package com.system;

import java.util.ArrayList;

public class Process {
	private int size = 0; // Mbytes
	private int timeLeft = 0;
	private int arrivalTime = 0;
	private int priority = 2;
	private ArrayList<Resource> resources; //<# printers >, <# scanners>, <# modems>, <# CDs>
	public int id = 0; 
	public int lastTimeUsed = 0;
	public boolean executing;
	
	
	public Process(){
		resources = new ArrayList<Resource>(5);	
		
		resources.add(null); //0 first printer
		resources.add(null); //1 second printer
		resources.add(null); //2 first scanner
		resources.add(null); //3 first modem
		resources.add(null); //4 first CD
		resources.add(null); //5 second CD
	}
	
	public int getPriority (){
		return this.priority;
	}
	
	public boolean setId(int id){
		if(this.id == 0){
			this.id = id;
			return true;
		}
		return false;
	}
	
	public int getId(){
		return this.id;
	}

	public int getArrivalTime() {
		return arrivalTime;
	}

	public void setArrivalTime(int arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	public ArrayList<Resource> getResources() {
		return resources;
	}

	public boolean setResources(Resource resource) {
		if (resource.getName().equals("Printer")){
			// checks for first printer
			if (resources.get(0) == null){
				resources.add(0, resource);
			//checks for second printer
			}else if (resources.get(1) == null) {
				resources.add(1, resource);
			}else {
				return false;
			}
			return true;
		}else if(resource.getName().equals("Scanner")){
			// checks for first scanner
			if (resources.get(2) == null){
				resources.add(2, resource);
			}else {
				return false;
			}
			return true;
		}else if(resource.getName().equals("Modem")){
			// checks for first modem
			if (resources.get(3) == null){
				resources.add(3, resource);
			}else {
				return false;
			}
			return true;
		}else{
			// else is a CD driver
			
			// checks for first CD
			if (resources.get(4) == null){
				resources.add(0, resource);
			//checks for second CD
			}else if (resources.get(5) == null) {
				resources.add(1, resource);
			}else {
				return false;
			}
			return true;
		}
	}

	public void setPriority(int priority) {
		this.priority = priority;
		
	}
	
	public String decode (){
		String resp = "";
		// <arrival time>, <priority>, <processor time>, <Mbytes>, <# impressoras>, <# scanners>, <# modems>, <# CDs>
		resp += this.arrivalTime + ", ";
		resp += this.priority + ", ";
		resp += this.timeLeft + ", ";
		resp += this.size + ", ";
		
		int printers = 0;
		if (getResources().get(0) != null) printers++;
		if (getResources().get(1) != null) printers++;
		resp += printers + ", ";
		
		int scanners = 0;
		if (getResources().get(2) != null) scanners++;
		resp += scanners + ", ";
		
		int modems = 0;
		if (getResources().get(3) != null) modems++;
		resp += modems + "," ;
		
		int CDs = 0;
		if (getResources().get(4) != null) CDs++;
		if (getResources().get(5) != null) CDs++;
		resp += CDs + "";
		
		return resp;
	}

	public int getTimeLeft() {
		return timeLeft;
	}

	public void setTimeLeft(int timeLeft) {
		this.timeLeft = timeLeft;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}
	
	public String toString(){
		return "P" + this.id;
	}
	
}
