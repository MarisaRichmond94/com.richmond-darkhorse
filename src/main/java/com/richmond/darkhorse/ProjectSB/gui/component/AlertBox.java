package com.richmond.darkhorse.ProjectSB.gui.component;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.Scene;

public class AlertBox {

	public static void display(String title,String message) {
		Stage stage = new Stage();
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.setTitle(title);
		stage.setMinWidth(250);
		
		Label label = new Label(message);
		label.getStyleClass().add("message");
		Button closeButton = new Button("Close");
		closeButton.getStyleClass().add("button");
		closeButton.setOnAction(e -> stage.close());
		
		VBox alertBoxLayout = new VBox(10);
		alertBoxLayout.getStyleClass().add("alertbox");
		alertBoxLayout.getChildren().addAll(label,closeButton);
		alertBoxLayout.getStylesheets().add("alertbox.css");
		
		Scene alertScene = new Scene(alertBoxLayout);
		stage.setScene(alertScene);
		stage.showAndWait();
	}
	
}
