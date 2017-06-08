package com.system;

import javafx.scene.text.Font;

import com.rowHandler.Row;
import com.rowHandler.SubmissionRow;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class RowWindow {
	
	public static void display(SubmissionRow sr, FeedbackScheduler feedbackScheduler, FCFSScheduler fcfsScheduler) {
		Stage window = new Stage ();
		window.setTitle("Row window");
		window.setMinWidth(250);
		window.setMinHeight(200);
		
		Label label = new Label("Queues");
		label.setFont(new Font("Roboto", 30 ));
		
		Line line = new Line (-300 , 0, 300, 0);
		line.setScaleY(2);
		line.setOpacity(0.5);
		line.setStroke(Color.DARKMAGENTA);
		
		Line line2 = new Line (-300 , 0, 300, 0);
		line2.setScaleY(2);
		line2.setOpacity(0.5);
		line2.setStroke(Color.DARKMAGENTA);
		
		Label submissionRowContent = new Label("");
		Label user1RowContent = new Label("");
		Label user2RowContent = new Label("");
		Label user3RowContent = new Label("");
		Label suspendedRowContent = new Label("");
		Label rtRowContent = new Label("");
		
		HBox submission = addRow(sr, "Submission", submissionRowContent);
		HBox user1 = addRow(feedbackScheduler.getUserQueue(1), "User 1        ", user1RowContent);
		HBox user2 = addRow(feedbackScheduler.getUserQueue(2), "User 2        ", user2RowContent);
		HBox user3 = addRow(feedbackScheduler.getUserQueue(3), "User 3        ", user3RowContent);
		HBox susp = addRow(feedbackScheduler.getSuspended(), "Suspended ", suspendedRowContent);
		HBox rt = addRow(fcfsScheduler.getFcfsQueue(),      "RealTime    ", rtRowContent);
		
		final Thread countThread = new Thread(new Runnable() {
            @Override
            public void run() {
                updateUI(sr, feedbackScheduler, fcfsScheduler, submissionRowContent, user1RowContent, user2RowContent, user3RowContent, suspendedRowContent, rtRowContent);
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
		
		VBox layout = new VBox (10);
		layout.getChildren().addAll(label, submission, line, user1, user2, user3, susp, line2, rt);
		layout.setAlignment(Pos.CENTER);
		
		Scene scene = new Scene (layout, 1000, 300);
		window.setScene(scene);
		window.show();
		
	}

	private static HBox addRow(Row row, String name, Label rowContent) {
		
		HBox hb = new HBox(5);
		
		Label rowName = new Label(name);
		
		rowName.setFont(new Font("Roboto", 15));
		rowContent.setFont(new Font("Roboto", 15));
		
		StackPane sp = new StackPane ();
		Rectangle bg = new Rectangle (800, 15);
		bg.setStroke(Color.BLACK);
		bg.setFill(Color.WHITE);
		if (name.equals("Suspended ")){
			bg.setFill(Color.DODGERBLUE);
			bg.setOpacity(0.3);
		}
		sp.getChildren().addAll(bg, rowContent);
		sp.setAlignment(Pos.CENTER_LEFT);
		
		hb.getChildren().addAll(rowName, sp);
		hb.setAlignment(Pos.BASELINE_LEFT);
		
		return hb;
	}
	
	private static void updateUI(SubmissionRow sr, FeedbackScheduler feedbackScheduler, FCFSScheduler fcfsScheduler, Label submission, Label user1, Label user2, Label user3, Label susp, Label rt) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
            	submission.setText(sr.toString());
            	user1.setText(feedbackScheduler.getUserQueue(1).toString());
            	user2.setText(feedbackScheduler.getUserQueue(2).toString());
            	user3.setText(feedbackScheduler.getUserQueue(3).toString());
            	susp.setText(feedbackScheduler.getSuspended().toString());
            	rt.setText(fcfsScheduler.getFcfsQueue().toString());
            }
        });
    }
}