/**package com.system;

import javafx.scene.text.Font;

import com.rowHandler.Row;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class RowWindow {
	
	/**public static void display(FeedbackScheduler feedbackScheduler, FCFSScheduler fcfsScheduler) {
		Stage window = new Stage ();
		window.setTitle("Row window");
		window.setMinWidth(250);
		window.setMinHeight(200);
		
		Label label = new Label("Rows");
		label.setFont(new Font("Roboto", 30 ));
		
		VBox layout = new VBox (10);
		layout.getChildren().addAll(label, addRow(scheduler.getSubmissionRow(), "Submission"), addRow(scheduler.getUserRow(), "User"), addRow( scheduler.getRealTimeRow(), "Real Time"));
		layout.setAlignment(Pos.TOP_CENTER);
		
		Scene scene = new Scene (layout, 800, 200);
		window.setScene(scene);
		window.show();
		
	}

	private static HBox addRow(Row row, String name) {
		
		HBox hb = new HBox(5);
		
		Label rowName = new Label(name + " Row:");
		Label rowContent = new Label(row.toString());
		
		rowName.setFont(new Font("Roboto", 15));
		rowContent.setFont(new Font("Roboto", 15));
		
		StackPane sp = new StackPane ();
		Rectangle bg = new Rectangle (600, 15);
		bg.setStroke(Color.BLACK);
		bg.setFill(Color.WHITE);
		sp.getChildren().addAll(bg, rowContent);
		sp.setAlignment(Pos.CENTER_LEFT);
		
		hb.getChildren().addAll(rowName, sp);
		hb.setAlignment(Pos.BASELINE_LEFT);
		
		return hb;
	}
}
**/