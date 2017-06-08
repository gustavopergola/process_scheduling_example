package com.system;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;

import com.rowHandler.Row;
import com.rowHandler.SubmissionRow;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class Main extends Application {
	
	Scene scene1, scene2;
	boolean fileSelected = false;
	public static void main(String[] args) {
		launch(args);
	}
		
	private static File orderFile(File file)  {
		PrintWriter writer;
		Process process = null;
		Row auxRow = new Row();
		
		try {
			readFile(file, auxRow);
			
			writer = new PrintWriter("orderedProcesses.txt", "UTF-8");
			while (true){
				process = auxRow.getNextProcess();
				if (process == null) break;
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
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		Logger.generateLogFile();
		
		Processor processor = new Processor (4);
		Memory memory = new Memory ();
		FeedbackScheduler feedbackScheduler = new FeedbackScheduler(processor, memory);
		FCFSScheduler fcfsScheduler = new FCFSScheduler(processor, memory);
		SubmissionRow sr = new SubmissionRow(feedbackScheduler, fcfsScheduler);
		
		processor.setSubmissionRow(sr);
		processor.setMemory(memory);
		
		memory.setFeedbackScheduler(feedbackScheduler);
		
		primaryStage.setTitle("Scheduler");
		
		// First Scene
		Label label1 = new Label("Select your process file");
		Button button1 = new Button(); 
		button1.setText("Select TXT File");
		
		Button button2 = new Button();
		button2.setText("Start Simulation");
		button2.setTextFill(Color.INDIANRED);
		
		Button button3 = new Button();
		button3.setText("Open Log Window");
		
		Button button4 = new Button();
		button4.setText("Open Processor Window");
		
		Button button5 = new Button();
		button5.setText("Open Queues Window");
		
		
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open TXT File");
		
		VBox layout1 = new VBox(10);
		layout1.getChildren().addAll(label1, button1, button2, button3, button4, button5);
		layout1.setAlignment(Pos.CENTER);
		
		button1.setOnAction(e -> {
			if (!fileSelected){
				if (readFile(orderFile(fileChooser.showOpenDialog(primaryStage)), sr)){
					fileSelected = true;
					// Display rows
					
				}else {
					// Show error message in case there is smth wrong with the file
					Label labelError = new Label ("Impossible to read from this file!");	
					layout1.getChildren().add(labelError);
					
				}
			}
			
		});
		
		button2.setOnAction(e -> {
			if (fileSelected){
				sr.admitAll();
				
				Thread fcfsThread = new Thread (fcfsScheduler);
				fcfsThread.start();
				
				Thread feedbackThread = new Thread (feedbackScheduler);
				feedbackThread.start();
			}
		});
		
		button3.setOnAction(e -> LogWindow.display());
		
		button4.setOnAction(e -> CPUWindow.display(processor));
		
		button5.setOnAction(e -> RowWindow.display(sr, feedbackScheduler, fcfsScheduler));
		
		scene1 = new Scene(layout1, 300, 250);
	
		primaryStage.setScene(scene1);
		primaryStage.show();
	}

	private static boolean readFile(File file, Row sr){
		// Lets consider a perfect file: if it exists, there's no syntax error or whatsoever
		int lastId = 0;
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
				if (newProcess.getId() == 0){
					newProcess.setId(++lastId);
				}
				// ignore zero timed processes
				if (newProcess.getTimeLeft() > 0){
					newProcess.state = "new";
					sr.submit(newProcess);
				}
					
			}
			sc.close();
			return true;
		} catch (FileNotFoundException e) {
			System.err.println("erro: " + e.toString());
			return false;
		}
	}
}

