package com.system;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.Format;
import java.util.Locale;
import java.util.Scanner;

import com.rowHandler.Row;
import com.rowHandler.SubmissionRow;

/**import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;**/

public class Main {
	
	//Scene scene1, scene2;
	
	
	public static void main(String[] args) {

		//launch(args);
		Processor processor = new Processor (4);
		FeedbackScheduler feedbackScheduler = new FeedbackScheduler(processor);
		FCFSScheduler fcfsScheduler = new FCFSScheduler(processor);
		SubmissionRow sr = new SubmissionRow(feedbackScheduler, fcfsScheduler);
		processor.setSubmissionRow(sr);
		
		readFile(orderFile(new File ("file.txt")), sr);
		
		sr.admitAll();
		feedbackScheduler.run();
		
		
		/**Thread feedbackThread = new Thread (feedbackScheduler);
		feedbackThread.start();
		
		Thread fcfsThread = new Thread (fcfsScheduler);
		fcfsThread.start();**/
	
		
	}

	//public void start(Stage primaryStage) throws Exception {
		
	private static File orderFile(File file)  {
		int lastId = 0;
		PrintWriter writer;
		Scanner sc;
		Process process = null;
		Row auxRow = new Row();
		
		try {
			readFile(file, auxRow);
			
			writer = new PrintWriter("orderedProcesses.txt", "UTF-8");
			while (true){
				process = auxRow.getNextProcess();
				if (process == null) break;
				process.setId(++lastId);
				writer.println(process.decode());
				process = null;
			}
			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		return new File ("orderedProcesses.txt");
		
	}

		/**Scheduler scheduler = new Scheduler ();
		
		primaryStage.setTitle("Scheduler");
		
		
		// First Scene
		Label label1 = new Label("Select your process file");
		Button button1 = new Button();
		button1.setText("Select TXT File");
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open TXT File");
		
		VBox layout1 = new VBox(20);
		layout1.getChildren().addAll(label1, button1);
		layout1.setAlignment(Pos.CENTER);
		
		button1.setOnAction(e -> {
			if (readFile(fileChooser.showOpenDialog(primaryStage), scheduler)){
				// Display rows
				RowWindow.display(scheduler);
				
			}else {
				// Show error message in case there is smth wrong with the file
				Label labelError = new Label ("Impossible to read from this file!");	
				layout1.getChildren().add(labelError);
				
			}
			System.out.println(scheduler.toString());
		});	
		
		scene1 = new Scene(layout1, 300, 250);
	
		primaryStage.setScene(scene1);
		primaryStage.show();**/
		
	//}

	private static boolean readFile(File file, Row sr){
		// Lets consider a perfect file: if it exists, there's no syntax error or whatsoever
		try {
			Scanner sc = new Scanner(file);
			while (sc.hasNextLine()){
				Process newProcess = new Process();
				
				String line = sc.nextLine();
				String [] infos = new String [8];
				infos = line.split(", ");
				
				
				for (int i = 0; i < infos.length; i++){
					if (i==0){
						// check for arrival time
						newProcess.setArrivalTime(Integer.parseInt(infos[i]));
					}
					else if(i==1){
						// check for priority
						newProcess.setPriority(Integer.parseInt(infos[i]));
					}
					else if(i==2){
						// check for processor time
						newProcess.setTimeLeft(Integer.parseInt(infos[i]));
					}
					else if(i==3){
						// check for size in MBytes
						newProcess.setSize(Integer.parseInt(infos[i]));
					}
					else if(i==4){
						// check for numbers of printers
						for (int j = 0; j < Integer.parseInt(infos[i]); j++)
							newProcess.setResources(new Resource("Printer"));
					}
					else if(i==5){
						// check for numbers of scanners
						for (int j = 0; j < Integer.parseInt(infos[i]); j++)
							newProcess.setResources(new Resource("Scanner"));
					}
					else if(i==6){
						// check for numbers of modems
						for (int j = 0; j < Integer.parseInt(infos[i]); j++)
							newProcess.setResources(new Resource("Modem"));
					}
					else if(i==7){
						// check for numbers of cd drivers
						for (int j = 0; j < Integer.parseInt(infos[i]); j++)
							newProcess.setResources(new Resource("CD"));
					}else if(i==8){
						newProcess.setId(Integer.parseInt(infos[i]));
					}
					
				}
				// ignore zero timed processes
				if (newProcess.getTimeLeft() > 0)
					sr.submit(newProcess);
			}
			sc.close();
			return true;
		} catch (FileNotFoundException e) {
			System.err.println("erro: " + e.toString());
			return false;
		}
	}
	
	// TODO Process Arrival time
	// TODO We need to have 2 schedulers instead of 1 implementing 2 policies
	// TODO Schedulers need to be multiprogrammed, not sequential
	// TODO CPU's can be sequential, there's no need for multiprogrammed CPU's
	// TODO	Aloca��o de mem�ria apropriada (finished process isn't being seen on LRU)
	// TODO suspended processes
	// TODO Manipula��o de processos em paralelo usando threads
	// TODO Planejamento geral do escalonador de feedback FCFS
	// TODO Tratamento de recursos
	
	
}

