package com.system;
import java.io.File;
import java.util.Scanner;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class Main extends Application  {
	
	Scene scene1, scene2;
	
	public static void main(String[] args) {

		
		launch(args);
		
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		Scheduler scheduler = new Scheduler ();
		
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
		primaryStage.show();
		
	}

	private static boolean readFile(File file, Scheduler scheduler){
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
					}
					
				}
				scheduler.submit(newProcess);
				
			}
			sc.close();
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	// TODO	Alocação de memória apropriada
	// TODO Manipulação de processos em paralelo usando threads
	// TODO Planejamento geral do escalonador de feedback FCFS
	// TODO Tratamento de recursos
	
	
}

