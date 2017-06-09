package com.system;

import java.util.ArrayList;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class ResourcesWindow {
	public static void display(ArrayList <Resource> resources) {
		
		Stage window = new Stage ();
		window.setTitle("Resources window");
		window.setMinWidth(250);
		window.setMinHeight(200);
		
		Label label = new Label("Resources");
		label.setFont(new Font("Roboto", 30));
		label.setPadding(new Insets(0,0,30,0));
		
		/** ============== Explanations Starts ============== **/
		
		HBox explanationsLayout = new HBox (15);
		
		/** ============== Green rectangle explanation starts ============== **/
		
		Rectangle greenBg = new Rectangle (15, 15);
		greenBg.setStroke(Color.BLACK);
		greenBg.setFill(Color.DARKSEAGREEN);
		
		Label greenExplanation = new Label ("= available.");
		
		HBox greenExplanationLayout = new HBox(2);
		greenExplanationLayout.getChildren().addAll(greenBg, greenExplanation);
		
		/** ============== Green rectangle explanation ends ============== **/
		
		/** ============== Red rectangle explanation starts =============== **/
		Rectangle redBg = new Rectangle (15, 15);
		redBg.setStroke(Color.BLACK);
		redBg.setFill(Color.INDIANRED);
		
		Label redExplanation = new Label ("= not available.");
		
		HBox redExplanationLayout = new HBox(2);
		redExplanationLayout.getChildren().addAll(redBg, redExplanation);
		/** ============== Red rectangle explanation ends ============== **/
		
		explanationsLayout.getChildren().addAll(greenExplanationLayout, redExplanationLayout);
		explanationsLayout.setAlignment(Pos.TOP_CENTER);
		
		/** ============== Explanations ends ============== **/
		
		/** ============== Resources Starts ============== **/
		
		HBox resourcesLayout = new HBox (15);
		
		/** ============== Printers Starts ============== **/
		
		VBox printersLayout = new VBox (5);
		
		Label printersLabel = new Label ("Printers");
		printersLabel.setFont(new Font("Roboto", 12));
		
		Rectangle printer1Bg = new Rectangle (25, 25);
		printer1Bg.setStroke(Color.BLACK);
		printer1Bg.setFill(Color.INDIANRED);
		
		Rectangle printer2Bg = new Rectangle (25, 25);
		printer2Bg.setStroke(Color.BLACK);
		printer2Bg.setFill(Color.INDIANRED);
		
		printersLayout.getChildren().addAll(printersLabel, printer1Bg, printer2Bg);
		printersLayout.setAlignment(Pos.TOP_CENTER);
		
		/** ============== Printers ends ============== **/
		
		/** ============== Scanners starts ============== **/
		
		VBox scannerLayout = new VBox (5);
		
		Label scannerLabel = new Label ("Scanner");
		scannerLabel.setFont(new Font("Roboto", 12));
		
		Rectangle scannerBg = new Rectangle (25, 25);
		scannerBg.setStroke(Color.BLACK);
		scannerBg.setFill(Color.INDIANRED);
		
		scannerLayout.getChildren().addAll(scannerLabel, scannerBg);
		scannerLayout.setAlignment(Pos.TOP_CENTER);
		
		/** ============== Scanners ends ============== **/
		
		/** ============== Modem starts ============== **/
		
		VBox modemLayout = new VBox (5);
		
		Label modemLabel = new Label ("Modem");
		modemLabel.setFont(new Font("Roboto", 12));
		
		Rectangle modemBg = new Rectangle (25, 25);
		modemBg.setStroke(Color.BLACK);
		modemBg.setFill(Color.INDIANRED);
		
		modemLayout.getChildren().addAll(modemLabel, modemBg);
		modemLayout.setAlignment(Pos.TOP_CENTER);
		
		/** ============== Modem ends ============== **/
		
		/** ============== CDs Starts ============== **/
		
		VBox cdsLayout = new VBox (5);
		
		Label cdsLabel = new Label ("CDs");
		cdsLabel.setFont(new Font("Roboto", 12));
		
		Rectangle cd1Bg = new Rectangle (25, 25);
		cd1Bg.setStroke(Color.BLACK);
		cd1Bg.setFill(Color.INDIANRED);
		
		Rectangle cd2Bg = new Rectangle (25, 25);
		cd2Bg.setStroke(Color.BLACK);
		cd2Bg.setFill(Color.INDIANRED);
		
		cdsLayout.getChildren().addAll(cdsLabel, cd1Bg, cd2Bg);
		cdsLayout.setAlignment(Pos.TOP_CENTER);
		
		/** ============== CDs ends ============== **/
		
		resourcesLayout.getChildren().addAll(printersLayout, scannerLayout, modemLayout, cdsLayout);
		resourcesLayout.setAlignment(Pos.CENTER);
		
		/** ============== Resources ends ============== **/
		
		VBox main = new VBox (0);
		main.getChildren().addAll(label, explanationsLayout, resourcesLayout);
		main.setAlignment(Pos.CENTER);
		
		final Thread countThread = new Thread(new Runnable() {
            @Override
            public void run() {
                updateUI(resources, printer1Bg, printer2Bg, scannerBg, modemBg, cd1Bg, cd2Bg);
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
		
		Scene scene = new Scene (main, 320, 220);
		window.setScene(scene);
		window.show();
		
	}
	
	static private void updateUI(ArrayList<Resource> resources, Rectangle printer1Bg, Rectangle printer2Bg,
			Rectangle scannerBg, Rectangle modemBg, Rectangle cd1Bg, Rectangle cd2Bg) {
		Platform.runLater(new Runnable() {
            @Override
            public void run() {
            	for (int i = 0; i < resources.size(); i++){
            		if (i == 0){
            			if (resources.get(i).isWorking)
            				printer1Bg.setFill(Color.INDIANRED);
            			else
            				printer1Bg.setFill(Color.DARKSEAGREEN);
            		}else if (i == 1){
            			if (resources.get(i).isWorking)
            				printer2Bg.setFill(Color.INDIANRED);
            			else
            				printer2Bg.setFill(Color.DARKSEAGREEN);
            		}else if (i == 2){
            			if (resources.get(i).isWorking)
            				scannerBg.setFill(Color.INDIANRED);
            			else
            				scannerBg.setFill(Color.DARKSEAGREEN);
            		}else if (i == 3){
            			if (resources.get(i).isWorking)
            				modemBg.setFill(Color.INDIANRED);
            			else
            				modemBg.setFill(Color.DARKSEAGREEN);
            		}else if (i == 4){
            			if (resources.get(i).isWorking)
            				cd1Bg.setFill(Color.INDIANRED);
            			else
            				cd1Bg.setFill(Color.DARKSEAGREEN);
            		}else if (i == 5){
            			if (resources.get(i).isWorking)
            				cd2Bg.setFill(Color.INDIANRED);
            			else
            				cd2Bg.setFill(Color.DARKSEAGREEN);
            		}
            	}
            }
		});
	}
	
}


