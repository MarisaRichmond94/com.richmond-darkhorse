package com.richmond.darkhorse.ProjectSB.gui.component;
import java.util.List;
import com.richmond.darkhorse.ProjectSB.Classroom;
import com.richmond.darkhorse.ProjectSB.Director;
import com.richmond.darkhorse.ProjectSB.Student;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.control.ToggleButton;

public class AddRemovePane extends GridPane{

	private int count;
	private boolean isAddPane = true;
	private GridPane innerPane;
	private AddStudentPane addStudentPane;
	private RemoveStudentPane removeStudentPane;
	
	@SuppressWarnings("unused")
	public AddRemovePane(Classroom classroom,Director director) {
		
		//Grid Pane
		this.setVgap(10);
		this.setHgap(10);
		GridPane.setHalignment(this,HPos.CENTER);
		GridPane.setValignment(this,VPos.CENTER);
		ColumnConstraints columnOne = new ColumnConstraints();
		columnOne.setPercentWidth(16.6);
	    ColumnConstraints columnTwo = new ColumnConstraints();
	    columnTwo.setPercentWidth(16.6);
		ColumnConstraints columnThree = new ColumnConstraints();
		columnThree.setPercentWidth(16.6);
		ColumnConstraints columnFour = new ColumnConstraints();
		columnFour.setPercentWidth(16.6);
	    ColumnConstraints columnFive = new ColumnConstraints();
	    columnFive.setPercentWidth(16.6);
		ColumnConstraints columnSix = new ColumnConstraints();
		columnSix.setPercentWidth(16.6);
		this.getColumnConstraints().addAll(columnOne,columnTwo,columnThree,columnFour,columnFive,columnSix);
		this.getStyleClass().add("gridpane");
		this.getStylesheets().add("addremovepane.css");
		
		count = 0;
		List<String> studentsEnrolled = classroom.getStudentsEnrolled();
		for(String student : studentsEnrolled) {
			count++;
		}
		
		Label totalEnrolled = new Label("Total:");
		totalEnrolled.getStyleClass().add("label");
		Label totalEnrolledStat = new Label("" + count + "");
		totalEnrolledStat.getStyleClass().add("label");
		
		Label mondayRatio = new Label("Monday:");
		mondayRatio.getStyleClass().add("label");
		Label mondayRatioStat = new Label(classroom.getCount("Monday") + "/" + classroom.getMaxSize());
		if(classroom.getCount("Monday") > classroom.getMaxSize()) {mondayRatioStat.setTextFill(Color.RED);}
		else {mondayRatioStat.setTextFill(Color.BLACK);}
		mondayRatioStat.getStyleClass().add("label");
		
		Label tuesdayRatio = new Label("Tuesday:");
		tuesdayRatio.getStyleClass().add("label");
		Label tuesdayRatioStat = new Label(classroom.getCount("Tuesday") + "/" + classroom.getMaxSize());
		if(classroom.getCount("Tuesday") > classroom.getMaxSize()) {tuesdayRatioStat.setTextFill(Color.RED);}
		else {tuesdayRatioStat.setTextFill(Color.BLACK);}
		tuesdayRatioStat.getStyleClass().add("label");
		
		Label wednesdayRatio = new Label("Wednesday:");
		wednesdayRatio.getStyleClass().add("label");
		Label wednesdayRatioStat = new Label(classroom.getCount("Wednesday") + "/" + classroom.getMaxSize());
		if(classroom.getCount("Wednesday") > classroom.getMaxSize()) {wednesdayRatioStat.setTextFill(Color.RED);}
		else {wednesdayRatioStat.setTextFill(Color.BLACK);}
		wednesdayRatioStat.getStyleClass().add("label");
		
		Label thursdayRatio = new Label("Thursday:");
		thursdayRatio.getStyleClass().add("label");
		Label thursdayRatioStat = new Label(classroom.getCount("Thursday") + "/" + classroom.getMaxSize());
		if(classroom.getCount("Thursday") > classroom.getMaxSize()) {thursdayRatioStat.setTextFill(Color.RED);}
		else {thursdayRatioStat.setTextFill(Color.BLACK);}
		thursdayRatioStat.getStyleClass().add("label");
		
		Label fridayRatio = new Label("Friday:");
		fridayRatio.getStyleClass().add("label");
		Label fridayRatioStat = new Label(classroom.getCount("Friday") + "/" + classroom.getMaxSize());
		if(classroom.getCount("Friday") > classroom.getMaxSize()) {fridayRatioStat.setTextFill(Color.RED);}
		else {fridayRatioStat.setTextFill(Color.BLACK);}
		fridayRatioStat.getStyleClass().add("label");
		
		//Inner GridPane
		addStudentPane = new AddStudentPane(classroom,director);
		innerPane = addStudentPane;
		ScrollPane scrollPane = new ScrollPane(innerPane);
		scrollPane.setPrefHeight(500);
		scrollPane.setPrefWidth(500);
		
		ToggleButton addButton = new ToggleButton("add student(s)");
		addButton.setSelected(true);
		addButton.setMaxHeight(50);
		addButton.setMaxWidth(300);
		addButton.getStyleClass().add("toggle-button");
		
		ToggleButton removeButton = new ToggleButton("remove student(s)");
		removeButton.setMaxHeight(50);
		removeButton.setMaxWidth(300);
		removeButton.getStyleClass().add("toggle-button");
		
		Label add = new Label("add");
		add.getStyleClass().add("label");
		Button addEnterButton = new Button("",add);
		addEnterButton.setOnAction(e -> {
			if(isAddPane == true) {
				List<Student> students = addStudentPane.getStudentsAdded();
				for(Student student : students) {director.addStudentToClassroom(student,classroom);}
				addStudentPane = (AddStudentPane) buildAddGridPane(classroom,director);
				scrollPane.setContent(addStudentPane);
				updateStats(classroom,totalEnrolledStat,mondayRatioStat,tuesdayRatioStat,wednesdayRatioStat,thursdayRatioStat,fridayRatioStat);
			}
		});
		addEnterButton.setMaxHeight(40);
		addEnterButton.setMaxWidth(150);
		addEnterButton.getStyleClass().add("button");
		
		Label remove = new Label("remove");
		add.getStyleClass().add("label");
		Button removeEnterButton = new Button("",remove);
		removeEnterButton.setMaxHeight(40);
		removeEnterButton.setMaxWidth(150);
		removeEnterButton.setVisible(false);
		removeEnterButton.getStyleClass().add("button");
		removeEnterButton.setOnAction(e -> {
			if(isAddPane == false) {
				List<Student> students = removeStudentPane.getStudentsRemoved();
				for(Student student : students) {
					director.removeStudentFromClassroom(student,classroom);
				}
				removeStudentPane = (RemoveStudentPane) buildRemoveGridPane(classroom,director);
				scrollPane.setContent(removeStudentPane);
				updateStats(classroom,totalEnrolledStat,mondayRatioStat,tuesdayRatioStat,wednesdayRatioStat,thursdayRatioStat,fridayRatioStat);
			}
		});
		
		addButton.setOnAction(e -> {
			removeButton.setSelected(false);
			addButton.setSelected(true);
			addStudentPane = (AddStudentPane) buildAddGridPane(classroom,director);
			scrollPane.setContent(addStudentPane);
			addEnterButton.setVisible(true);
			removeEnterButton.setVisible(false);
			isAddPane = true;
		});
		
		removeButton.setOnAction(e -> {
			removeButton.setSelected(true);
			addButton.setSelected(false);
			removeStudentPane = (RemoveStudentPane) buildRemoveGridPane(classroom,director);
			scrollPane.setContent(removeStudentPane);
			addEnterButton.setVisible(false);
			removeEnterButton.setVisible(true);
			isAddPane = false;
		});
		
		this.add(totalEnrolled,0,0);
		GridPane.setHalignment(totalEnrolled,HPos.RIGHT);
		this.add(totalEnrolledStat,1,0);
		this.add(mondayRatio,2,0);
		GridPane.setHalignment(mondayRatio,HPos.RIGHT);
		this.add(mondayRatioStat,3,0);
		this.add(tuesdayRatio,4,0);
		GridPane.setHalignment(tuesdayRatio,HPos.RIGHT);
		this.add(tuesdayRatioStat,5,0);
		this.add(wednesdayRatio,0,1);
		GridPane.setHalignment(wednesdayRatio,HPos.RIGHT);
		this.add(wednesdayRatioStat,1,1);
		this.add(thursdayRatio,2,1);
		GridPane.setHalignment(thursdayRatio,HPos.RIGHT);
		this.add(thursdayRatioStat,3,1);
		this.add(fridayRatio,4,1);
		GridPane.setHalignment(fridayRatio,HPos.RIGHT);
		this.add(fridayRatioStat,5,1);
		this.add(addButton,0,2);
		GridPane.setConstraints(addButton,0,2,2,1);
		GridPane.setHalignment(addButton,HPos.CENTER);
		this.add(removeButton,2,2);
		GridPane.setConstraints(removeButton,2,2,2,1);
		GridPane.setHalignment(removeButton,HPos.CENTER);
		this.add(scrollPane,0,3);
		GridPane.setConstraints(scrollPane,0,3,6,6);
		this.add(addEnterButton,2,10);
		GridPane.setConstraints(addEnterButton,2,10,2,1);
		GridPane.setHalignment(addEnterButton,HPos.CENTER);
		this.add(removeEnterButton,2,10);
		GridPane.setConstraints(removeEnterButton,2,10,2,1);
		GridPane.setHalignment(removeEnterButton,HPos.CENTER);
		
	}
	
	public GridPane buildAddGridPane(Classroom classroom,Director director) {
		AddStudentPane newAddStudentPane = new AddStudentPane(classroom,director);
		return newAddStudentPane;
	}
	
	public GridPane buildRemoveGridPane(Classroom classroom,Director director) {
		RemoveStudentPane newRemoveStudentPane = new RemoveStudentPane(classroom,director);
		return newRemoveStudentPane;
	}
	
	@SuppressWarnings("unused")
	public void updateStats(Classroom classroom,Label totalEnrolledStat,Label mondayRatioStat,Label tuesdayRatioStat,Label wednesdayRatioStat,Label thursdayRatioStat,Label fridayRatioStat) {
		count = 0;
		List<String> studentsEnrolled = classroom.getStudentsEnrolled();
		for(String student : studentsEnrolled) {
			count++;
		}
		totalEnrolledStat.setText("" + count + "");
		mondayRatioStat.setText(classroom.getCount("Monday") + "/" + classroom.getMaxSize());
		tuesdayRatioStat.setText(classroom.getCount("Tuesday") + "/" + classroom.getMaxSize());
		wednesdayRatioStat.setText(classroom.getCount("Wednesday") + "/" + classroom.getMaxSize());
		thursdayRatioStat.setText(classroom.getCount("Thursday") + "/" + classroom.getMaxSize());
		fridayRatioStat.setText(classroom.getCount("Friday") + "/" + classroom.getMaxSize());
		
		if(classroom.getCount("Monday") > classroom.getMaxSize()) {mondayRatioStat.setTextFill(Color.RED);}
		else {mondayRatioStat.setTextFill(Color.BLACK);}
		if(classroom.getCount("Tuesday") > classroom.getMaxSize()) {tuesdayRatioStat.setTextFill(Color.RED);}
		else {tuesdayRatioStat.setTextFill(Color.BLACK);}
		if(classroom.getCount("Wednesday") > classroom.getMaxSize()) {wednesdayRatioStat.setTextFill(Color.RED);}
		else {wednesdayRatioStat.setTextFill(Color.BLACK);}
		if(classroom.getCount("Thursday") > classroom.getMaxSize()) {thursdayRatioStat.setTextFill(Color.RED);}
		else {thursdayRatioStat.setTextFill(Color.BLACK);}
		if(classroom.getCount("Friday") > classroom.getMaxSize()) {fridayRatioStat.setTextFill(Color.RED);}
		else {fridayRatioStat.setTextFill(Color.BLACK);}
	}
	
}
