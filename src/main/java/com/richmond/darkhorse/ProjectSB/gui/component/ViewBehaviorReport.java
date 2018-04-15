package com.richmond.darkhorse.ProjectSB.gui.component;
import com.richmond.darkhorse.ProjectSB.BehaviorReport;
import com.richmond.darkhorse.ProjectSB.Director;
import com.richmond.darkhorse.ProjectSB.Student;
import com.richmond.darkhorse.ProjectSB.Teacher;
import com.richmond.darkhorse.ProjectSB.StaffMember;
import javafx.geometry.HPos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.util.Map;

public class ViewBehaviorReport {
	
	private Director director;
	private Student student;
	private BehaviorReport behaviorReport;

	public ViewBehaviorReport(Director director,Student student,BehaviorReport behaviorReport) {
		this.director = director;
		this.student = student;
		this.behaviorReport = behaviorReport;
	}
	
	public void display() {
		
		Stage stage = new Stage();
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.setTitle("Behavior Report - " + student.getFirstName() + " " + student.getLastName() + ", " + behaviorReport.getStringDate());
		
		GridPane viewBehaviorReportLayout = new GridPane();
		viewBehaviorReportLayout.setVgap(10);
		viewBehaviorReportLayout.setHgap(10);
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
	    viewBehaviorReportLayout.getColumnConstraints().addAll(columnOne,columnTwo,columnThree,columnFour,columnFive,columnSix);
	    viewBehaviorReportLayout.getStyleClass().add("gridpane");
	    viewBehaviorReportLayout.getStylesheets().add("modifystudentinfo.css");
		
	    Label title = new Label("Behavior Incident Form");
	    title.getStyleClass().add("subtitle");
	    
	    viewBehaviorReportLayout.add(title,0,0);
	    GridPane.setHalignment(title,HPos.CENTER);
	    GridPane.setConstraints(title,0,0,6,2);
	    
	    Label childsName = new Label("Student:");
	    childsName.getStyleClass().add("label");
	    Label nameText = new Label();
	    nameText.setText(student.getFirstName() + " " + student.getLastName());
	    nameText.getStyleClass().add("response-label");
	    
	    viewBehaviorReportLayout.add(childsName,0,2);
	    GridPane.setHalignment(childsName,HPos.RIGHT);
	    viewBehaviorReportLayout.add(nameText,1,2);
	    
	    Label date = new Label("Date:");
	    date.getStyleClass().add("label");
	    Label dateText = new Label(behaviorReport.getStringDate());
	    dateText.getStyleClass().add("response-label");
	    Label time = new Label("Time:");
	    time.getStyleClass().add("label");
	    Label timeText = new Label(behaviorReport.getStringTime());
	    timeText.getStyleClass().add("response-label");
	    
	    viewBehaviorReportLayout.add(date,2,2);
	    GridPane.setHalignment(date,HPos.RIGHT);
	    viewBehaviorReportLayout.add(dateText,3,2);
	    GridPane.setHalignment(dateText,HPos.LEFT);
	    viewBehaviorReportLayout.add(time,4,2);
	    GridPane.setHalignment(time,HPos.RIGHT);
	    viewBehaviorReportLayout.add(timeText,5,2);
	    GridPane.setHalignment(timeText,HPos.LEFT);
	    
	    Label teacher = new Label("Teacher:");
	    teacher.getStyleClass().add("label");
	    Label teacherText = new Label("" + behaviorReport.getTeacher(behaviorReport.getTeacherID()) + "");
	    teacherText.getStyleClass().add("response-label");
	    
	    viewBehaviorReportLayout.add(teacher,0,3);
	    GridPane.setHalignment(teacher,HPos.RIGHT);
	    viewBehaviorReportLayout.add(teacherText,1,3);
	    GridPane.setHalignment(teacherText,HPos.LEFT);
	    
	    Label classroomActivity = new Label("Activity:");
	    classroomActivity.getStyleClass().add("label");
	    Label classroomActivityText = new Label(behaviorReport.getActivityDuringBehavior());
	    classroomActivityText.getStyleClass().add("response-label");
	    
	    viewBehaviorReportLayout.add(classroomActivity,2,3);
	    GridPane.setHalignment(classroomActivity,HPos.RIGHT);
	    viewBehaviorReportLayout.add(classroomActivityText,3,3);
	    GridPane.setHalignment(classroomActivityText,HPos.LEFT);
	    GridPane.setConstraints(classroomActivityText,3,3,3,1);
	    
	    Label childsBehavior = new Label("Behavior:");
	    childsBehavior.getStyleClass().add("label");
	    Label childsBehaviorText = new Label(behaviorReport.getChildsBehavior());
	    childsBehaviorText.getStyleClass().add("response-label");
	    
	    viewBehaviorReportLayout.add(childsBehavior,0,4);
	    GridPane.setHalignment(childsBehavior,HPos.RIGHT);
	    GridPane.setConstraints(childsBehavior,0,4,1,1);
	    viewBehaviorReportLayout.add(childsBehaviorText,1,4);
	    GridPane.setHalignment(childsBehaviorText,HPos.LEFT);
	    GridPane.setConstraints(childsBehaviorText,1,4,5,1);
	    
	    Label consequence = new Label("Consequence:");
	    consequence.getStyleClass().add("label");
	    Label consequenceText = new Label(behaviorReport.getConsequence());
	    consequenceText.getStyleClass().add("response-label");

	    viewBehaviorReportLayout.add(consequence,0,5);
	    GridPane.setHalignment(consequence,HPos.RIGHT);
	    viewBehaviorReportLayout.add(consequenceText,1,5);
	    GridPane.setHalignment(consequenceText,HPos.LEFT);
	    GridPane.setConstraints(consequenceText,1,5,4,1);
	    
	    Label childsReaction = new Label("Reaction:");
	    childsReaction.getStyleClass().add("label");
	    Label childsReactionText = new Label(behaviorReport.getStudentResponse());
	    childsReactionText.getStyleClass().add("response-label");
	    
	    viewBehaviorReportLayout.add(childsReaction,0,6);
	    GridPane.setHalignment(childsReaction,HPos.RIGHT);
	    viewBehaviorReportLayout.add(childsReactionText,1,6);
	    GridPane.setHalignment(childsReactionText,HPos.LEFT);
	    GridPane.setConstraints(childsReactionText,1,6,4,1);
	    
	    Label timeLasted = new Label("Duration:");
	    timeLasted.getStyleClass().add("label");
	    Label timeLastedText = new Label(behaviorReport.getDurationBehaviorOccurred());
	    timeLastedText.getStyleClass().add("response-label");
	    
	    viewBehaviorReportLayout.add(timeLasted,0,7);
	    GridPane.setHalignment(timeLasted,HPos.RIGHT);
	    viewBehaviorReportLayout.add(timeLastedText,1,7);
	    GridPane.setHalignment(timeLastedText,HPos.LEFT);
	    GridPane.setConstraints(timeLastedText,1,7,4,1);
	    
	    Label observedBy = new Label("Observed by:");
	    observedBy.getStyleClass().add("label");
	    Label observedByText = new Label(behaviorReport.getTeacherObserved());
	    observedByText.getStyleClass().add("response-label");
	    
	    viewBehaviorReportLayout.add(observedBy,0,8);
	    GridPane.setHalignment(observedBy,HPos.RIGHT);
	    viewBehaviorReportLayout.add(observedByText,1,8);
	    GridPane.setHalignment(observedByText,HPos.LEFT);
	    GridPane.setConstraints(observedByText,1,8,4,1);
	    
	    Label teacherSignature = new Label("Teacher:");
	    teacherSignature.getStyleClass().add("label");
	    boolean teachersSignature = behaviorReport.getTeacherSignature();
	    Teacher myTeacher = null;
	    Map<String,StaffMember> staff = director.getStaffMembers();
	    if(staff.containsKey(behaviorReport.getTeacherID())) {
	    		myTeacher = (Teacher)staff.get(behaviorReport.getTeacherID());
	    }
	    String teachSignature;
	    if(teachersSignature == true && myTeacher != null) { teachSignature = myTeacher.getFirstName() + " " + myTeacher.getLastName();}
	    else {teachSignature = " ";}
	    Label teacherSignatureText = new Label(teachSignature);
	    teacherSignatureText.getStyleClass().add("response-label");
	    
	    Label directorSignature = new Label("Director:");
	    directorSignature.getStyleClass().add("label");
	    boolean directorsSignature = behaviorReport.getDirectorSignature();
	    String signature;
	    if(directorsSignature == true) { signature = director.getFirstName() + " " + director.getLastName();}
	    else {signature = " ";}
	    Label directorSignatureText = new Label(signature);
	    directorSignatureText.getStyleClass().add("response-label");
	    
	    viewBehaviorReportLayout.add(teacherSignature,0,9);
	    GridPane.setHalignment(teacherSignature,HPos.RIGHT);
	    viewBehaviorReportLayout.add(teacherSignatureText,1,9);
	    GridPane.setHalignment(teacherSignatureText,HPos.LEFT);
	    GridPane.setConstraints(teacherSignatureText,1,9,2,1);
	    viewBehaviorReportLayout.add(directorSignature,3,9);
	    GridPane.setHalignment(directorSignature,HPos.RIGHT);
	    viewBehaviorReportLayout.add(directorSignatureText,4,9);
	    GridPane.setHalignment(directorSignatureText,HPos.LEFT);
	    GridPane.setConstraints(directorSignatureText,4,9,2,1);
	    
	    Scene viewBehaviorReportScene = new Scene(viewBehaviorReportLayout);
		stage.setScene(viewBehaviorReportScene);
		stage.showAndWait();
		
	}
	
}
