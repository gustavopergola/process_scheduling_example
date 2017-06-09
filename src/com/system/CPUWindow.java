package com.system;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class CPUWindow {
	public static void display(Processor processor) {
		Stage window = new Stage ();
		window.setTitle("Processor window");
		window.setMinWidth(250);
		window.setMinHeight(200);
		
		Label label = new Label("CPUs");
		label.setFont(new Font("Roboto", 30));
		label.setPadding(new Insets(0,0,30,0));
		
		Line line1 = new Line (-75 , 0, 75, 0);
		Line line2 = new Line (0, -30 , 0, 30);
		Line line3 = new Line (0, -30 , 0, 30);
		
		Label cpu1 = new Label("CPU1");
		cpu1.setFont(new Font("Roboto", 16));
		Label cpu2 = new Label("CPU2");
		cpu2.setFont(new Font("Roboto", 16));
		Label cpu3 = new Label("CPU3");
		cpu3.setFont(new Font("Roboto", 16));
		Label cpu4 = new Label("CPU4");
		cpu4.setFont(new Font("Roboto", 16));
		
		HBox cpu13 = new HBox (10);
		HBox cpu24 = new HBox (10);
		
		cpu13.getChildren().addAll(cpu1, line2, cpu3);
		cpu13.setAlignment(Pos.CENTER);
		
		cpu24.getChildren().addAll(cpu2, line3, cpu4);
		cpu24.setAlignment(Pos.CENTER);
		
		final Thread countThread = new Thread(new Runnable() {
            @Override
            public void run() {
                updateUI(processor, cpu1, cpu2, cpu3, cpu4);
                try {
					Thread.sleep(300);
					run();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
            }
        });
        countThread.setDaemon(true);
        countThread.start();
		
		VBox layout = new VBox (0);
		layout.getChildren().addAll(label, cpu13, line1, cpu24);
		layout.setAlignment(Pos.CENTER);
		
		Scene scene = new Scene (layout, 300, 300);
		window.setScene(scene);
		window.show();
	}
	
	private static void updateUI(Processor processor, Label cpu1, Label cpu2, Label cpu3, Label cpu4) {
		Platform.runLater(new Runnable() {
            @Override
            public void run() {
				if (processor.getCPUList().get(0).getExecuting() != null){
					cpu1.setText("CPU1:" + processor.getCPUList().get(0).getExecuting().toString() + "\t");
				}else {
					cpu1.setText("CPU1:  \t");
				}
				
				if (processor.getCPUList().get(1).getExecuting() != null){
					cpu2.setText("CPU2:" + processor.getCPUList().get(1).getExecuting().toString()  + "\t");
				}else {
					cpu2.setText("CPU2:  \t");
				}
				
				if (processor.getCPUList().get(2).getExecuting() != null){
					cpu3.setText("CPU3:  " + processor.getCPUList().get(2).getExecuting().toString()  + "\t");
				}else {
					cpu3.setText("CPU3:  \t");
				}
				
				if (processor.getCPUList().get(3).getExecuting() != null){
					cpu4.setText("CPU4:" + processor.getCPUList().get(3).getExecuting().toString()  + "\t");
				}else {
					cpu4.setText("CPU4:  \t");
				}
            }
		});
	}
}
