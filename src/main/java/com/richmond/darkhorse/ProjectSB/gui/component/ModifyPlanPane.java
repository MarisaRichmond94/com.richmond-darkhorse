package com.richmond.darkhorse.ProjectSB.gui.component;
import java.util.HashMap;
import java.util.Map;
import com.richmond.darkhorse.ProjectSB.Classroom;
import com.richmond.darkhorse.ProjectSB.Director;
import com.richmond.darkhorse.ProjectSB.Student;
import com.richmond.darkhorse.ProjectSB.middleman.ModifyExistingStudent;
import javafx.scene.paint.Color;
import javafx.geometry.HPos;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;

public class ModifyPlanPane extends GridPane{

	private Map<String,Boolean> attendancePlan;
	
	public ModifyPlanPane(Student student,Director director) {
		this.attendancePlan = student.getRecord().getAttendance().getAttendancePlan();
		
	    this.setVgap(10);
	    this.setHgap(10);
		ColumnConstraints columnOne = new ColumnConstraints();
		columnOne.setPercentWidth(33.3);
	    ColumnConstraints columnTwo = new ColumnConstraints();
	    columnTwo.setPercentWidth(33.3);
	    ColumnConstraints columnThree = new ColumnConstraints();
	    columnThree.setPercentWidth(33.3);
	    this.getColumnConstraints().addAll(columnOne,columnTwo,columnThree);
	    this.getStyleClass().add("gridpane");
	    this.getStylesheets().add("modifystudentinfo.css");
	    
	    Label studentAttendancePlan = new Label("Attendance Plan");
	    studentAttendancePlan.getStyleClass().add("subtitle");
	    
	    Label monday = new Label("monday plan: ");
	    monday.getStyleClass().add("centrallabel");
	    ChoiceBox<String> mondayBox = new ChoiceBox<String>();
	    mondayBox.getItems().addAll("present","not present");
	    if(attendancePlan.get("Monday") == true) {mondayBox.setValue(mondayBox.getItems().get(0));}
	    else {mondayBox.setValue(mondayBox.getItems().get(1));}
	    mondayBox.setDisable(true);
	    mondayBox.getStyleClass().add("choicebox");
	    mondayBox.setMaxWidth(600);
	    
	    Label tuesday = new Label("tuesday plan: ");
	    tuesday.getStyleClass().add("centrallabel");
	    ChoiceBox<String> tuesdayBox = new ChoiceBox<String>();
	    tuesdayBox.getItems().addAll("present","not present");
	    if(attendancePlan.get("Tuesday") == true) {tuesdayBox.setValue(tuesdayBox.getItems().get(0));}
	    else {tuesdayBox.setValue(tuesdayBox.getItems().get(1));}
	    tuesdayBox.setDisable(true);
	    tuesdayBox.getStyleClass().add("choicebox");
	    tuesdayBox.setMaxWidth(600);
	    
	    Label wednesday = new Label("wednesday plan: ");
	    wednesday.getStyleClass().add("centrallabel");
	    ChoiceBox<String> wednesdayBox = new ChoiceBox<String>();
	    wednesdayBox.getItems().addAll("present","not present");
	    if(attendancePlan.get("Wednesday") == true) {wednesdayBox.setValue(wednesdayBox.getItems().get(0));}
	    else {wednesdayBox.setValue(wednesdayBox.getItems().get(1));}
	    wednesdayBox.setDisable(true);
	    wednesdayBox.getStyleClass().add("choicebox");
	    wednesdayBox.setMaxWidth(600);
	    
	    Label thursday = new Label("thursday plan: ");
	    thursday.getStyleClass().add("centrallabel");
	    ChoiceBox<String> thursdayBox = new ChoiceBox<String>();
	    thursdayBox.getItems().addAll("present","not present");
	    if(attendancePlan.get("Thursday") == true) {thursdayBox.setValue(thursdayBox.getItems().get(0));}
	    else {thursdayBox.setValue(thursdayBox.getItems().get(1));}
	    thursdayBox.setDisable(true);
	    thursdayBox.getStyleClass().add("choicebox");
	    thursdayBox.setMaxWidth(600);
	    
	    Label friday = new Label("friday plan: ");
	    friday.getStyleClass().add("centrallabel");
	    ChoiceBox<String> fridayBox = new ChoiceBox<String>();
	    fridayBox.getItems().addAll("present","not present");
	    if(attendancePlan.get("Friday") == true) {fridayBox.setValue(fridayBox.getItems().get(0));}
	    else {fridayBox.setValue(fridayBox.getItems().get(1));}
	    fridayBox.setDisable(true);
	    fridayBox.getStyleClass().add("choicebox");
	    fridayBox.setMaxWidth(600);
	    
	    ImageView writeViewer = new ImageView();
		Image write = new Image("write.png");
		writeViewer.setImage(write);
		writeViewer.setPreserveRatio(true);
		writeViewer.setFitHeight(150);
		ImageButton writeButton = new ImageButton(writeViewer);
		writeButton.setOnAction(e -> {
			Classroom selectedClassroom = student.getClassroom(student.getClassroomID());
			Map<String,Boolean> studentsAttendancePlan = new HashMap<String,Boolean>();
			if(mondayBox.getValue().equals("present")) {studentsAttendancePlan.put("Monday",true);}
			else {studentsAttendancePlan.put("Monday",false);}
			if(tuesdayBox.getValue().equals("present")) {studentsAttendancePlan.put("Tuesday",true);}
			else {studentsAttendancePlan.put("Tuesday",false);}
			if(wednesdayBox.getValue().equals("present")) {studentsAttendancePlan.put("Wednesday",true);}
			else {studentsAttendancePlan.put("Wednesday",false);}
			if(thursdayBox.getValue().equals("present")) {studentsAttendancePlan.put("Thursday",true);}
			else {studentsAttendancePlan.put("Thursday",false);}
			if(fridayBox.getValue().equals("present")) {studentsAttendancePlan.put("Friday",true);}
			else {studentsAttendancePlan.put("Friday",false);}
			Thread modifyExistingStudent = new Thread(new ModifyExistingStudent(director,student,selectedClassroom,studentsAttendancePlan));
			modifyExistingStudent.start();
			Label saveSuccessful = new Label("save successful!");
			saveSuccessful.setTextFill(Color.RED);
			saveSuccessful.getStyleClass().add("label");
			this.add(saveSuccessful,1,9);
			GridPane.setConstraints(saveSuccessful,1,9,2,1);
			GridPane.setHalignment(saveSuccessful,HPos.CENTER);
			mondayBox.setDisable(true);
			tuesdayBox.setDisable(true);
			wednesdayBox.setDisable(true);
			thursdayBox.setDisable(true);
			fridayBox.setDisable(true);
		});
		writeButton.setVisible(false);
		Label writeLabel = new Label("write");
		writeLabel.setVisible(false);
		writeLabel.getStyleClass().add("label");
		
		Label editLabel = new Label("edit");
		editLabel.getStyleClass().add("label");
		ImageView editViewer = new ImageView();
		Image edit = new Image("edit.png");
		editViewer.setImage(edit);
		editViewer.setPreserveRatio(true);
		editViewer.setFitHeight(150);
		ImageButton editButton = new ImageButton(editViewer);
		editButton.setOnAction(e -> {
			writeButton.setVisible(true);
			writeLabel.setVisible(true);
			editButton.setVisible(false);
			editLabel.setVisible(false);
			mondayBox.setDisable(false);
			tuesdayBox.setDisable(false);
			wednesdayBox.setDisable(false);
			thursdayBox.setDisable(false);
			fridayBox.setDisable(false);
		});
		
		
		this.add(studentAttendancePlan,1,0);
	    GridPane.setHalignment(studentAttendancePlan,HPos.LEFT);
	    GridPane.setConstraints(studentAttendancePlan,1,0,2,1);
	    this.add(monday,0,1);
	    GridPane.setHalignment(monday,HPos.RIGHT);
	    this.add(mondayBox,1,1);
	    GridPane.setConstraints(mondayBox,1,1,2,1);
	    this.add(tuesday,0,2);
	    GridPane.setHalignment(tuesday,HPos.RIGHT);
	    this.add(tuesdayBox,1,2);
	    GridPane.setConstraints(tuesdayBox,1,2,2,1);
	    this.add(wednesday,0,3);
	    GridPane.setHalignment(wednesday,HPos.RIGHT);
	    this.add(wednesdayBox,1,3);
	    GridPane.setConstraints(wednesdayBox,1,3,2,1);
	    this.add(thursday,0,4);
	    GridPane.setHalignment(thursday,HPos.RIGHT);
	    this.add(thursdayBox,1,4);
	    GridPane.setConstraints(thursdayBox,1,4,2,1);
	    this.add(friday,0,5);
	    GridPane.setHalignment(friday,HPos.RIGHT);
	    this.add(fridayBox,1,5);
	    GridPane.setConstraints(fridayBox,1,5,2,1);
	    this.add(editButton,1,6);
	    GridPane.setHalignment(editButton,HPos.CENTER);
	    this.add(editLabel,1,7);
	    GridPane.setHalignment(editLabel,HPos.CENTER);
	    this.add(writeButton,1,6);
	    GridPane.setHalignment(writeButton,HPos.CENTER);
	    this.add(writeLabel,1,7);
		GridPane.setHalignment(writeLabel,HPos.CENTER);
	}
	
}
