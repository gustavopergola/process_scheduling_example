package com.system;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import com.rowHandler.SubmissionRow;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import com.util.ProcessList;

public class Main extends Application implements EventHandler<ActionEvent> {
	
	Button button;
	
	public static void main(String[] args) {

		launch(args);
		
		System.out.println("Bem-vindo!");
		
		// Initialize quantum 2 process scheduler (manages rows)
		Scheduler scheduler = new Scheduler();
		
		if (readFile(scheduler)){
			// Declare quad-core processor
			Processor processor = new Processor(4);
			System.out.println(scheduler.toString());
			
			System.out.println("Até logo!");
			
		}else {
			System.out.println("File could not be found! Aborting!");
		}
		
		
		
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("Titulo");
		
		Button button = new Button();
		button.setText("Click me");
		
		StackPane layout = new StackPane();
		layout.getChildren().add(button);
		
		Scene scene = new Scene(layout, 300, 250);
		
		primaryStage.setScene(scene);
		primaryStage.show();
		
	}

	@Override
	public void handle(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	
	private static boolean readFile(Scheduler scheduler){
		// Lets consider a perfect file: if it exists, there's no syntax error or whatsoever
		
		File file = new File("file.txt");
		try {
			Scanner sc = new Scanner(file);
			while (sc.hasNextLine()){
				Process newProcess = new Process();
				String line = sc.nextLine();
				String [] infos = new String [8];
				infos = line.split(" ");
				
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
						newProcess.setTime(Integer.parseInt(infos[i]));
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
			
			return true;
		} catch (FileNotFoundException e) {
			return false;
		}
	}
	
	// TODO	Alocação de memória apropriada
	// TODO Leitura do arquivo <arrival time>, <priority>, <processor time>, <Mbytes>, <# printers>, <# scanners>, <# modems>, <# CDs>
	// TODO User Interface
	// TODO Manipulação de processos em paralelo usando threads
	// TODO Planejamento geral do escalonador de feedback FCFS
	// TODO Tratamento de recursos
	
	
}

