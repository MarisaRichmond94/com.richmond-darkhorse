package com.richmond.darkhorse.ProjectSB.gui.component;
import com.richmond.darkhorse.ProjectSB.Director;
import com.richmond.darkhorse.ProjectSB.Student;
import javafx.geometry.HPos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;

public class ViewRecordPane extends GridPane{
	
	private GridPane innerPane;
	private DailySheetPane dailySheetPane;
	private BehaviorReportPane behaviorReportPane;
	private IncidentReportPane incidentReportPane;
	
	public ViewRecordPane(Director director,Student student) {
	
		this.setVgap(10);
		this.setHgap(10);
		ColumnConstraints columnOne = new ColumnConstraints();
		columnOne.setPercentWidth(33.3);
		ColumnConstraints columnTwo = new ColumnConstraints();
		columnTwo.setPercentWidth(33.3);
		ColumnConstraints columnThree = new ColumnConstraints();
		columnThree.setPercentWidth(33.3);
		this.getColumnConstraints().addAll(columnOne,columnTwo,columnThree);
		this.getStyleClass().add("grid-pane");
		this.getStylesheets().add("modifystudentinfo.css");	
			
		ImageView recordViewer = new ImageView();
		Image record = new Image("record.png");
		recordViewer.setImage(record);
		recordViewer.setPreserveRatio(true);
		recordViewer.setFitHeight(150);
		
		Label title = new Label(student.getFirstName() + "'s Record");
		title.getStyleClass().add("subtitle");
		
		ToggleButton dailySheetButton = new ToggleButton("daily sheets");
		dailySheetButton.setSelected(true);
		dailySheetButton.setMaxHeight(50);
		dailySheetButton.setMaxWidth(300);
		dailySheetButton.getStyleClass().add("toggle-button");
		
		ToggleButton behaviorReportButton = new ToggleButton("behavior reports");
		behaviorReportButton.setMaxHeight(50);
		behaviorReportButton.setMaxWidth(300);
		behaviorReportButton.getStyleClass().add("toggle-button");
		
		ToggleButton incidentReportButton = new ToggleButton("incident reports");
		incidentReportButton.setMaxHeight(50);
		incidentReportButton.setMaxWidth(300);
		incidentReportButton.getStyleClass().add("toggle-button");
		
		//Inner GridPane
		dailySheetPane = new DailySheetPane(director,student);
		innerPane = dailySheetPane;
		ScrollPane scrollPane = new ScrollPane(innerPane);
		scrollPane.setPrefHeight(500);
		scrollPane.setPrefWidth(500);
		
		dailySheetButton.setOnAction(e -> {
			if(behaviorReportButton.isSelected() == true) {behaviorReportButton.setSelected(false);}
			else if(incidentReportButton.isSelected() == true) {incidentReportButton.setSelected(false);}
			dailySheetPane = (DailySheetPane) buildDailySheetPane(student,director);
			scrollPane.setContent(dailySheetPane); 
		});
		behaviorReportButton.setOnAction(e -> {
			if(dailySheetButton.isSelected() == true) {dailySheetButton.setSelected(false);}
			else if(incidentReportButton.isSelected() == true) {incidentReportButton.setSelected(false);}
			behaviorReportPane = (BehaviorReportPane) buildBehaviorReportPane(student,director);
			scrollPane.setContent(behaviorReportPane);
		});
		incidentReportButton.setOnAction(e -> {
			if(dailySheetButton.isSelected() == true) {dailySheetButton.setSelected(false);}
			else if(behaviorReportButton.isSelected() == true) {behaviorReportButton.setSelected(false);}
			incidentReportPane = (IncidentReportPane) buildIncidentReportPane(student,director);
			scrollPane.setContent(incidentReportPane);
		});
		
		this.add(recordViewer,0,0);
		GridPane.setHalignment(recordViewer,HPos.CENTER);
		this.add(title,1,0);
		GridPane.setConstraints(title,1,0,2,1);
		GridPane.setHalignment(title,HPos.LEFT);
		this.add(dailySheetButton,0,1);
		GridPane.setHalignment(dailySheetButton,HPos.CENTER);
		this.add(behaviorReportButton,1,1);
		GridPane.setHalignment(behaviorReportButton,HPos.CENTER);
		this.add(incidentReportButton,2,1);
		GridPane.setHalignment(incidentReportButton,HPos.CENTER);
		this.add(scrollPane,0,2);
		GridPane.setConstraints(scrollPane,0,2,3,3);
		GridPane.setHalignment(scrollPane,HPos.CENTER);
		
	}
	
	public GridPane buildDailySheetPane(Student student,Director director) {
		DailySheetPane newDailySheetPane = new DailySheetPane(director,student);
		return newDailySheetPane;
	}
	
	public GridPane buildBehaviorReportPane(Student student,Director director) {
		BehaviorReportPane newBehaviorReportPane = new BehaviorReportPane(director,student);
		return newBehaviorReportPane;
	}
	
	public GridPane buildIncidentReportPane(Student student,Director director) {
		IncidentReportPane newIncidentReportPane = new IncidentReportPane(director,student);
		return newIncidentReportPane;
	}

}
