package com.richmond.darkhorse.ProjectSB.gui.component;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import com.richmond.darkhorse.ProjectSB.Director;
import com.richmond.darkhorse.ProjectSB.Schedule;
import com.richmond.darkhorse.ProjectSB.StaffMember;
import com.richmond.darkhorse.ProjectSB.Teacher;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.geometry.HPos;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ModifySchedules {

	private Director director;
	private Map<Integer,String> startTimes,stopTimes,newStartTimes,newStopTimes;
	private List<Teacher> teachers;
	private int staffIndex;
	private Teacher teacher;
	private Schedule schedule;
	
	public ModifySchedules(Director director) {
		this.director = director;
		this.teachers = new ArrayList<Teacher>();
		Map<String,StaffMember> staffMembers = director.getStaffMembers();
		for(StaffMember staffmember : staffMembers.values()) {
			if(staffmember.getTitle().equals("Teacher")) {teachers.add((Teacher) staffmember);}
		}
		this.staffIndex = 0;
		this.teacher = teachers.get(staffIndex); 
		this.schedule = teacher.getSchedule();
		this.startTimes = schedule.getStartTimes();
		this.stopTimes = schedule.getStopTimes();
		this.newStartTimes = new HashMap<Integer,String>();
		this.newStopTimes = new HashMap<Integer,String>();
	}
	
	public void display() {
		Stage stage = new Stage();
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.setTitle("Modify");
		
		GridPane modifySchedulesLayout = new GridPane();
		modifySchedulesLayout.setVgap(0);
		modifySchedulesLayout.setHgap(0);
		ColumnConstraints columnOne = new ColumnConstraints();
		columnOne.setPercentWidth(25);
	    ColumnConstraints columnTwo = new ColumnConstraints();
	    columnTwo.setPercentWidth(25);
	    ColumnConstraints columnThree = new ColumnConstraints();
	    columnThree.setPercentWidth(25);
	    ColumnConstraints columnFour = new ColumnConstraints();
	    columnFour.setPercentWidth(25);
	    modifySchedulesLayout.getColumnConstraints().addAll(columnOne,columnTwo,columnThree,columnFour);
	    modifySchedulesLayout.getStyleClass().add("gridpane");
	    modifySchedulesLayout.getStylesheets().add("directorhome.css");
	    loadDisplayPane(modifySchedulesLayout,stage);
	    
	    ChoiceBox<Teacher> teacherBox = new ChoiceBox<Teacher>();
	    for(Teacher teacher : teachers) {teacherBox.getItems().add(teacher);}
	    teacherBox.setValue(teacherBox.getItems().get(staffIndex));

        teacherBox.getSelectionModel().selectedItemProperty().addListener((ov,oldTeacher,newTeacher) -> {
        		teacher = newTeacher;
        		boolean match = false;
			int count = 0;
			while(match == false && count < teachers.size()) {
				Teacher teacherCheck = teachers.get(count);
				if(teacherCheck.equals(newTeacher)) {
					match = true;
				}
				count++;
			}
			staffIndex = count-1;
			schedule = teacher.getSchedule();
			startTimes = schedule.getStartTimes();
			stopTimes = schedule.getStopTimes();
			modifySchedulesLayout.getChildren().clear();
			buildViewMenu(modifySchedulesLayout,stage);
			loadDisplayPane(modifySchedulesLayout,stage);
        });
		
        modifySchedulesLayout.add(teacherBox,0,0);
        GridPane.setHalignment(teacherBox,HPos.CENTER);
        GridPane.setConstraints(teacherBox,0,0,4,1);
        
		Scene modifySchedulesScene = new Scene(modifySchedulesLayout);
		stage.setScene(modifySchedulesScene);
		stage.showAndWait();
	}
	
	private void buildViewMenu(GridPane modifySchedulesLayout,Stage stage) {
		Label selectTeacher = new Label("view:");
	    selectTeacher.getStyleClass().add("label");
	    ChoiceBox<Teacher> teacherBox = new ChoiceBox<Teacher>();
	    for(Teacher teacher : teachers) {teacherBox.getItems().add(teacher);}
	    teacherBox.setValue(teacherBox.getItems().get(staffIndex));
	    
	    teacherBox.getSelectionModel().selectedItemProperty().addListener((ov,oldTeacher,newTeacher) -> {
    		teacher = newTeacher;
    		boolean match = false;
		int count = 0;
		while(match == false && count < teachers.size()) {
			Teacher teacherCheck = teachers.get(count);
			if(teacherCheck.equals(newTeacher)) {
				match = true;
			}
			count++;
		}
		staffIndex = count-1;
		schedule = teacher.getSchedule();
		startTimes = schedule.getStartTimes();
		stopTimes = schedule.getStopTimes();
		modifySchedulesLayout.getChildren().clear();
		buildViewMenu(modifySchedulesLayout,stage);
		loadDisplayPane(modifySchedulesLayout,stage);
    });
		
        modifySchedulesLayout.add(teacherBox,0,0);
        GridPane.setHalignment(teacherBox,HPos.CENTER);
        GridPane.setConstraints(teacherBox,0,0,4,1);
	}
	
	private void loadDisplayPane(GridPane modifySchedulesLayout,Stage stage) {
		
	    Label teacherTitle = new Label(teacher.getFirstName() + " " + teacher.getLastName());
	    teacherTitle.getStyleClass().add("subtitle");
	    modifySchedulesLayout.add(teacherTitle,0,1);
	    GridPane.setHalignment(teacherTitle,HPos.CENTER);
	    GridPane.setConstraints(teacherTitle,0,1,4,1);
	    
	    Label monday = new Label("monday:");
	    monday.getStyleClass().add("label");
	    Label mondayTo = new Label("to");
	    mondayTo.getStyleClass().add("label");
	    TextField mondayStart;
	    if(startTimes.size() > 0) {mondayStart = new TextField(startTimes.get(1));}
	    else {mondayStart = new TextField();}
	    mondayStart.getStyleClass().add("textfield");
	    TextField mondayEnd;
	    if(stopTimes.size() > 0) {mondayEnd = new TextField(stopTimes.get(1));}
	    else {mondayEnd = new TextField();}
	    mondayEnd.getStyleClass().add("textfield");
	    placeDay(modifySchedulesLayout,monday,mondayTo,mondayStart,mondayEnd,2);
	    
	    Label tuesday = new Label("tuesday:");
	    tuesday.getStyleClass().add("label");
	    Label tuesdayTo = new Label("to");
	    tuesdayTo.getStyleClass().add("label");
	    TextField tuesdayStart;
	    if(startTimes.size() > 1) {tuesdayStart = new TextField(startTimes.get(2));}
	    else {tuesdayStart = new TextField();}
	    tuesdayStart.getStyleClass().add("textfield");
	    TextField tuesdayEnd;
	    if(stopTimes.size() > 1) {tuesdayEnd = new TextField(stopTimes.get(2));}
	    else {tuesdayEnd = new TextField();}
	    tuesdayEnd.getStyleClass().add("textfield");
	    placeDay(modifySchedulesLayout,tuesday,tuesdayTo,tuesdayStart,tuesdayEnd,3);
	    
	    Label wednesday = new Label("wednesday:");
	    wednesday.getStyleClass().add("label");
	    Label wednesdayTo = new Label("to");
	    wednesdayTo.getStyleClass().add("label");
	    TextField wednesdayStart;
	    if(startTimes.size() > 2) {wednesdayStart = new TextField(startTimes.get(3));}
	    else {wednesdayStart = new TextField();}
	    wednesdayStart.getStyleClass().add("textfield");
	    TextField wednesdayEnd;
	    if(stopTimes.size() > 2) {wednesdayEnd = new TextField(stopTimes.get(3));}
	    else {wednesdayEnd = new TextField();}
	    wednesdayEnd.getStyleClass().add("textfield");
	    placeDay(modifySchedulesLayout,wednesday,wednesdayTo,wednesdayStart,wednesdayEnd,4);
	    
	    Label thursday = new Label("thursday:");
	    thursday.getStyleClass().add("label");
	    Label thursdayTo = new Label("to");
	    thursdayTo.getStyleClass().add("label");
	    TextField thursdayStart;
	    if(startTimes.size() > 3) {thursdayStart = new TextField(startTimes.get(4));}
	    else {thursdayStart = new TextField();}
	    thursdayStart.getStyleClass().add("textfield");
	    TextField thursdayEnd;
	    if(stopTimes.size() > 3) {thursdayEnd = new TextField(stopTimes.get(4));}
	    else {thursdayEnd = new TextField();}
	    thursdayEnd.getStyleClass().add("textfield");
	    placeDay(modifySchedulesLayout,thursday,thursdayTo,thursdayStart,thursdayEnd,5);
	    
	    Label friday = new Label("friday:");
	    friday.getStyleClass().add("label");
	    Label fridayTo = new Label("to");
	    fridayTo.getStyleClass().add("label");
	    TextField fridayStart;
	    if(startTimes.size() > 4) {fridayStart = new TextField(startTimes.get(5));}
	    else {fridayStart = new TextField();}
	    fridayStart.getStyleClass().add("textfield");
	    TextField fridayEnd;
	    if(stopTimes.size() > 4) {fridayEnd = new TextField(stopTimes.get(5));}
	    else {fridayEnd = new TextField();}
	    fridayEnd.getStyleClass().add("textfield");
	    placeDay(modifySchedulesLayout,friday,fridayTo,fridayStart,fridayEnd,6);
		
		Button editButton = new Button("edit");
		editButton.getStyleClass().add("button");
		Button writeButton = new Button("write");
		writeButton.getStyleClass().add("button");
		writeButton.setOnAction(e -> {
			String startMonday = nullStartCheck(mondayStart);
			String startTuesday = nullStartCheck(tuesdayStart);
			String startWednesday = nullStartCheck(wednesdayStart); 
			String startThursday = nullStartCheck(thursdayStart); 
			String startFriday = nullStartCheck(fridayStart); 
			newStartTimes.put(1,startMonday);
			newStartTimes.put(2,startTuesday);
			newStartTimes.put(3,startWednesday);
			newStartTimes.put(4,startThursday);
			newStartTimes.put(5,startFriday);
			String endMonday = nullEndCheck(mondayEnd); 
			String endTuesday = nullEndCheck(tuesdayEnd); 
			String endWednesday = nullEndCheck(wednesdayEnd); 
			String endThursday = nullEndCheck(thursdayEnd); 
			String endFriday = nullEndCheck(fridayEnd); 
			newStopTimes.put(1,endMonday);
			newStopTimes.put(2,endTuesday);
			newStopTimes.put(3,endWednesday);
			newStopTimes.put(4,endThursday);
			newStopTimes.put(5,endFriday);
			director.modifyStaffSchedule(teacher,newStartTimes,newStopTimes);
			stage.close();
		});
		editButton.setOnAction(e -> {
			editButton.setVisible(false);
			writeButton.setVisible(true);
			mondayStart.setDisable(false);
			mondayEnd.setDisable(false);
			tuesdayStart.setDisable(false);
			tuesdayEnd.setDisable(false);
			wednesdayStart.setDisable(false);
			wednesdayEnd.setDisable(false);
			thursdayStart.setDisable(false);
			thursdayEnd.setDisable(false);
			fridayStart.setDisable(false);
			fridayEnd.setDisable(false);
		});
		writeButton.setVisible(false);
		Button cancelButton = new Button("cancel");
		cancelButton.getStyleClass().add("button");
		cancelButton.setOnAction(e -> {
			stage.close();
		});
		modifySchedulesLayout.add(editButton,1,7);
		GridPane.setHalignment(editButton,HPos.CENTER);
		modifySchedulesLayout.add(writeButton,1,7);
		GridPane.setHalignment(writeButton,HPos.CENTER);
		modifySchedulesLayout.add(cancelButton,2,7);
		GridPane.setHalignment(cancelButton,HPos.CENTER);
	}
	
	private String nullStartCheck(TextField startField) {
		String value = null;
		if(startField.getText().isEmpty()) {value = null;}
		else { value = startField.getText();}
		return value;
	}
	
	private String nullEndCheck(TextField endField) {
		String value = null;
		if(endField.getText().isEmpty()) {value = null;}
		else { value = endField.getText();}
		return value;
	}
	
	private void placeDay(GridPane gridpane,Label day,Label dayTo,TextField dayStart,TextField dayEnd,int rowIndex) {
		dayStart.setDisable(true);
		dayEnd.setDisable(true);
		dayStart.setMaxWidth(100);
		dayEnd.setMaxWidth(100);
		gridpane.add(day,0,rowIndex);
		GridPane.setHalignment(day,HPos.RIGHT);
		gridpane.add(dayStart,1,rowIndex);
		GridPane.setHalignment(dayStart,HPos.CENTER);
		gridpane.add(dayTo,2,rowIndex);
		GridPane.setHalignment(dayTo,HPos.CENTER);
		gridpane.add(dayEnd,3,rowIndex);
		GridPane.setHalignment(dayEnd,HPos.CENTER);
	}
	
}
