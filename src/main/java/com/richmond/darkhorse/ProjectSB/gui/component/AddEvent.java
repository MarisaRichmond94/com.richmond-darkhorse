package com.richmond.darkhorse.ProjectSB.gui.component;
import com.richmond.darkhorse.ProjectSB.Center;
import com.richmond.darkhorse.ProjectSB.Director;
import com.richmond.darkhorse.ProjectSB.Event;
import javafx.beans.binding.Bindings;
import javafx.geometry.HPos;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class AddEvent {

	private Director director;
	
	public AddEvent(Director director) {
		this.director = director;
	}
	
	public void display() {
		Stage stage = new Stage();
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.setTitle("Create New Event");
		
		GridPane addEventLayout = new GridPane();
		addEventLayout.setVgap(10);
		addEventLayout.setHgap(10);
		ColumnConstraints columnOne = new ColumnConstraints();
		columnOne.setPercentWidth(25);
	    ColumnConstraints columnTwo = new ColumnConstraints();
	    columnTwo.setPercentWidth(25);
	    ColumnConstraints columnThree = new ColumnConstraints();
	    columnThree.setPercentWidth(25);
	    ColumnConstraints columnFour = new ColumnConstraints();
	    columnFour.setPercentWidth(25);
	    addEventLayout.getColumnConstraints().addAll(columnOne,columnTwo,columnThree,columnFour);
	    addEventLayout.getStyleClass().add("gridpane");
	    addEventLayout.getStylesheets().add("directorhome.css");
	    
	    Label newEvent = new Label("Create New Event");
	    newEvent.getStyleClass().add("med-title");
	    Label summary = new Label("Summary:");
	    summary.getStyleClass().add("label");
	    TextField summaryText = new TextField();
	    summaryText.getStyleClass().add("textfield");
	    summaryText.setMaxWidth(600);
	    summaryText.setPrefHeight(50);
	    summaryText.setPromptText("summary");
	    Label day = new Label("Day:");
	    day.getStyleClass().add("label");
	    ChoiceBox<String> dayBox = new ChoiceBox<String>();
	    String monday = "Monday";
	    String tuesday = "Tuesday";
	    String wednesday = "Wednesday";
	    String thursday = "Thursday";
	    String friday = "Friday";
	    dayBox.getItems().addAll(monday,tuesday,wednesday,thursday,friday);
	    dayBox.setValue(dayBox.getItems().get(0));
	    dayBox.setMaxWidth(600);
	    Label time = new Label("Time:");
	    time.getStyleClass().add("label");
	    TextField timeText = new TextField();
	    timeText.setPromptText("time (optional)");
	    timeText.getStyleClass().add("textfield");
	    timeText.setMaxWidth(600);
	    timeText.setPrefHeight(50);
	    Label details = new Label("Details:");
	    details.getStyleClass().add("label");
	    TextField detailsText = new TextField();
	    detailsText.setPromptText("additional details (optional)");
	    detailsText.getStyleClass().add("textfield");
	    detailsText.setMaxWidth(600);
	    detailsText.setPrefHeight(50);
	    Button create = new Button("create");
	    create.getStyleClass().add("button");
	    create.disableProperty().bind(Bindings.isEmpty(summaryText.textProperty()));
	    create.setOnAction(e -> {
	    		String eventSummary = summaryText.getText();
	    		String eventDay = dayBox.getValue();
	    		String eventTime = null;
	    		if(!timeText.getText().isEmpty()) {eventTime = timeText.getText();}
	    		String eventDetails = null;
	    		if(!detailsText.getText().isEmpty()) {eventDetails = detailsText.getText();}
	    		Event event = director.createNewEvent(eventSummary,eventDay,eventTime,eventDetails);
	    		Center center = director.getCenter(director.getCenterID());
	    		center.getEventCalendar().addEvent(event);
	    		stage.close();
	    });
	    Button cancel = new Button("cancel");
	    cancel.setOnAction(e -> stage.close());
	    cancel.getStyleClass().add("button");
	    
	    addEventLayout.add(newEvent,0,0);
	    GridPane.setHalignment(newEvent,HPos.CENTER);
	    GridPane.setConstraints(newEvent,0,0,4,1);
	    addEventLayout.add(summary,0,1);
	    GridPane.setHalignment(summary,HPos.RIGHT);
	    addEventLayout.add(summaryText,1,1);
	    GridPane.setHalignment(summaryText,HPos.LEFT);
	    GridPane.setConstraints(summaryText,1,1,3,1);
	    addEventLayout.add(day,0,2);
	    GridPane.setHalignment(day,HPos.RIGHT);
	    addEventLayout.add(dayBox,1,2);
	    GridPane.setHalignment(dayBox,HPos.LEFT);
	    GridPane.setConstraints(dayBox,1,2,3,1);
	    addEventLayout.add(time,0,3);
	    GridPane.setHalignment(time,HPos.RIGHT);
	    addEventLayout.add(timeText,1,3);
	    GridPane.setHalignment(timeText,HPos.LEFT);
	    GridPane.setConstraints(timeText,1,3,3,1);
	    addEventLayout.add(details,0,4);
	    GridPane.setHalignment(details,HPos.RIGHT);
	    addEventLayout.add(detailsText,1,4);
	    GridPane.setHalignment(detailsText,HPos.LEFT);
	    GridPane.setConstraints(detailsText,1,4,3,1);
	    addEventLayout.add(create,1,5);
	    GridPane.setHalignment(create,HPos.CENTER);
	    addEventLayout.add(cancel,2,5);
	    GridPane.setHalignment(cancel,HPos.CENTER);
		
		Scene addEventScene = new Scene(addEventLayout);
		stage.setScene(addEventScene);
		stage.showAndWait();
	}
	
}
