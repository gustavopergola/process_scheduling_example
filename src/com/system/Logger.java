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
		try {
			writer = new PrintWriter("history.log", "UTF-8");
			writer.println("Program started");
			writer.close();
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
	
}
