package com.system;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class CPULogWindow {
	public static void display() {
		Stage window = new Stage ();
		window.setTitle("CPU Usage window");
		window.setMinWidth(250);
		window.setMinHeight(200);
		
		Label label = new Label("CPU Usage");
		label.setFont(new Font("Roboto", 30));
		label.setPadding(new Insets(0,0,30,0));
		
		VBox layout = new VBox (0);
		layout.getChildren().addAll(label);
		layout.setAlignment(Pos.CENTER);
		
		Scene scene = new Scene (layout, 300, 300);
		window.setScene(scene);
		window.show();
	}
	private static void updateUI() {
		Platform.runLater(new Runnable() {
            @Override
            public void run() {
            
            }
		});
	}
}
