package com.system;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class LogWindow {
	public static void display() {
		Stage window = new Stage ();
		window.setTitle("Log window");
		window.setMinWidth(250);
		window.setMinHeight(200);
		
		Label label = new Label("Logs");
		label.setFont(new Font("Roboto", 25));
		label.setPadding(new Insets(0,0,30,0));
		
		ListView<String> list = new ListView<String>();
		ObservableList<String> items = FXCollections.observableArrayList ();
		list.setItems(items);
		
		final Thread countThread = new Thread(new Runnable() {
            @Override
            public void run() {
                updateUI( list);
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
		layout.getChildren().addAll(label, list);
		layout.setAlignment(Pos.TOP_CENTER);
		
		Scene scene = new Scene (layout, 400, 350);
		window.setScene(scene);
		window.show();
	}
	
	static private void updateUI(ListView <String> list) {
		Platform.runLater(new Runnable() {
            @Override
            public void run() {
				ObservableList<String> items = FXCollections.observableArrayList ();
				try {
					Scanner sc = new Scanner(new File("history.log"));
					while(sc.hasNextLine()){
						items.add(sc.nextLine());
					}
					sc.close();
					list.setItems(items);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
            }
		});
	}
}
