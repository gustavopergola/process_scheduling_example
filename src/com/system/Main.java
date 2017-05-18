package com.system;
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
		
		// Declare quad-core processor
		Processor processor = new Processor(4);
		
		// Declare a process priority 1
		Process process1 = new Process(1);
		Process process2 = new Process(1);
		
		// Initialize quantum 2 process scheduler (manages rows)
		Scheduling scheduler = new Scheduling();
		
		scheduler.submit(process1);
		scheduler.submit(process2);
		
		System.out.println(scheduler.toString());
		
		
		System.out.println("Até logo!");
		
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
	
	
	
	
	
}

