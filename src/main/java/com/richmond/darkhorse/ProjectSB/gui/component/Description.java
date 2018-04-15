package com.richmond.darkhorse.ProjectSB.gui.component;
import com.richmond.darkhorse.ProjectSB.Event;
import javafx.geometry.HPos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;

public class Description{

	private Event event;
	
	public Description(Event event) {
		this.event = event;
	}
	
	public void display() {
		Stage stage = new Stage();
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.setTitle("Create New Event");
		
		GridPane eventDescriptionLayout = new GridPane();
		eventDescriptionLayout.setVgap(10);
		eventDescriptionLayout.setHgap(10);
		ColumnConstraints columnOne = new ColumnConstraints();
		columnOne.setPercentWidth(25);
	    ColumnConstraints columnTwo = new ColumnConstraints();
	    columnTwo.setPercentWidth(25);
	    ColumnConstraints columnThree = new ColumnConstraints();
	    columnThree.setPercentWidth(25);
	    ColumnConstraints columnFour = new ColumnConstraints();
	    columnFour.setPercentWidth(25);
	    eventDescriptionLayout.getColumnConstraints().addAll(columnOne,columnTwo,columnThree,columnFour);
	    eventDescriptionLayout.getStyleClass().add("gridpane");
	    eventDescriptionLayout.getStylesheets().add("directorhome.css");
		
	    Label eventTitle = new Label(event.getSummary());
	    eventTitle.getStyleClass().add("subtitle");
	    Label detailsTitle = new Label("Details:");
	    detailsTitle.getStyleClass().add("label");
	    Label details = new Label();
	    if(event.getDetails() != null) {details.setText(event.getDetails());}
	    else {details.setText("N/A");}
	    details.getStyleClass().add("label");
	    
	    eventDescriptionLayout.add(eventTitle,0,0);
	    GridPane.setConstraints(eventTitle,0,0,4,1);
	    GridPane.setHalignment(eventTitle,HPos.CENTER);
	    eventDescriptionLayout.add(detailsTitle,0,1);
	    GridPane.setHalignment(detailsTitle,HPos.RIGHT);
	    eventDescriptionLayout.add(details,1,1);
	    GridPane.setConstraints(details,1,1,3,1);
	    GridPane.setHalignment(details,HPos.LEFT);
	    
		Scene eventDescriptionScene = new Scene(eventDescriptionLayout);
		stage.setScene(eventDescriptionScene);
		stage.showAndWait();
	}
	
}
