package com.system;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class Logger {
	
	static public void generateLogFile (){
		PrintWriter writer;
		PrintWriter writer2;
		try {
			writer = new PrintWriter("history.log", "UTF-8");
			writer.println("Program initialized");
			writer.close();
			writer2 = new PrintWriter("CPUhistory.log", "UTF-8");
			writer2.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
	}
	
	static public void addLogLine(String line){
		try {
			line += " at Clock: " + Processor.getClock() +  " \n";
		    Files.write(Paths.get("history.log"), line.getBytes(), StandardOpenOption.APPEND);
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	static public void addCPULogLine(String line){
		try {
		    Files.write(Paths.get("CPUhistory.log"), line.getBytes(), StandardOpenOption.APPEND);
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
