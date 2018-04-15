package com.richmond.darkhorse.ProjectSB.gui.component;
import com.richmond.darkhorse.ProjectSB.IncidentReport;
import com.richmond.darkhorse.ProjectSB.Student;
import javafx.geometry.HPos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ViewIncidentReport {
	
	private Student student;
	private IncidentReport incidentReport;

	public ViewIncidentReport(Student student,IncidentReport incidentReport) {
		
		this.student = student;
		this.incidentReport = incidentReport;
	}
	
	public void display() {
		
		Stage stage = new Stage();
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.setTitle("Incident Report - " + student.getFirstName() + " " + student.getLastName() + ", " + incidentReport.getStringDate());
		
		GridPane viewIncidentReportLayout = new GridPane();
		viewIncidentReportLayout.setVgap(10);
		viewIncidentReportLayout.setHgap(10);
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
	    viewIncidentReportLayout.getColumnConstraints().addAll(columnOne,columnTwo,columnThree,columnFour,columnFive,columnSix);
	    viewIncidentReportLayout.getStyleClass().add("gridpane");
	    viewIncidentReportLayout.getStylesheets().add("modifystudentinfo.css");
	    
	    Label title = new Label("Incident Report Form");
	    title.getStyleClass().add("subtitle");
	    
	    viewIncidentReportLayout.add(title,0,0);
	    GridPane.setHalignment(title,HPos.CENTER);
	    GridPane.setConstraints(title,0,0,6,2);
	    
	    Label childsName = new Label("Student:");
	    childsName.getStyleClass().add("label");
	    Label nameText = new Label();
	    nameText.setText(student.getFirstName() + " " + student.getLastName());
	    nameText.getStyleClass().add("response-label");
	    
	    viewIncidentReportLayout.add(childsName,0,2);
	    GridPane.setHalignment(childsName,HPos.RIGHT);
	    viewIncidentReportLayout.add(nameText,1,2);
	    
	    Label date = new Label("Date:");
	    date.getStyleClass().add("label");
	    Label dateText = new Label(incidentReport.getStringDate());
	    dateText.getStyleClass().add("response-label");
	    Label time = new Label("Time:");
	    time.getStyleClass().add("label");
	    Label timeText = new Label(incidentReport.getStringTime());
	    timeText.getStyleClass().add("response-label");
	    
	    viewIncidentReportLayout.add(date,2,2);
	    GridPane.setHalignment(date,HPos.RIGHT);
	    viewIncidentReportLayout.add(dateText,3,2);
	    GridPane.setHalignment(dateText,HPos.LEFT);
	    viewIncidentReportLayout.add(time,4,2);
	    GridPane.setHalignment(time,HPos.RIGHT);
	    viewIncidentReportLayout.add(timeText,5,2);
	    GridPane.setHalignment(timeText,HPos.LEFT);
	    
	    Label description = new Label("Description:");
	    description.getStyleClass().add("label");
	    Label descriptionText = new Label(incidentReport.getIncidentDescription());
	    descriptionText.getStyleClass().add("response-label");
	    
	    viewIncidentReportLayout.add(description,0,3);
	    GridPane.setHalignment(description,HPos.RIGHT);
	    viewIncidentReportLayout.add(descriptionText,1,3);
	    GridPane.setHalignment(descriptionText,HPos.LEFT);
	    GridPane.setConstraints(descriptionText,1,3,5,1);
	    
	    Label action = new Label("Action taken:");
	    action.getStyleClass().add("label");
	    Label actionText = new Label(incidentReport.getActionTaken());
	    actionText.getStyleClass().add("response-label");
	    
	    viewIncidentReportLayout.add(action,0,4);
	    GridPane.setHalignment(action,HPos.RIGHT);
	    viewIncidentReportLayout.add(actionText,1,4);
	    GridPane.setHalignment(actionText,HPos.LEFT);
	    GridPane.setConstraints(actionText,1,4,5,1);
	    
	    Label appearance = new Label("Appearance:");
	    appearance.getStyleClass().add("label");
	    Label appearanceText = new Label(incidentReport.getStudentCondition());
	    appearanceText.getStyleClass().add("response-label");
	    
	    viewIncidentReportLayout.add(appearance,0,5);
	    GridPane.setHalignment(appearance,HPos.RIGHT);
	    viewIncidentReportLayout.add(appearanceText,1,5);
	    GridPane.setHalignment(appearanceText,HPos.LEFT);
	    GridPane.setConstraints(appearanceText,1,5,5,1);
	    
	    Label medical = new Label("Medical:");
	    medical.getStyleClass().add("label");
	    String medicalResponse;
	    if(incidentReport.isMedicalAttention() == true && incidentReport.isOnSite() == false) {medicalResponse = student.getFirstName() + "required off-site medical attention";}
	    else if(incidentReport.isMedicalAttention() == true && incidentReport.isOnSite() == true){ medicalResponse = student.getFirstName() + "required on-site medical attention";}
	    else { medicalResponse = student.getFirstName() + "did not require any immediate medical attention";}
	    Label medicalText = new Label(medicalResponse);
	    medicalText.getStyleClass().add("response-label");

	    viewIncidentReportLayout.add(medical,0,6);
	    GridPane.setHalignment(medical,HPos.RIGHT);
	    viewIncidentReportLayout.add(medicalText,1,6);
	    GridPane.setHalignment(medicalText,HPos.LEFT);
	    GridPane.setConstraints(medicalText,1,6,5,1);
	    
	    Label comments = new Label("Comments:");
	    comments.getStyleClass().add("label");
	    Label commentsText = new Label(incidentReport.getComments());
	    commentsText.getStyleClass().add("response-label");
	    
	    viewIncidentReportLayout.add(comments,0,7);
	    GridPane.setHalignment(comments,HPos.RIGHT);
	    viewIncidentReportLayout.add(commentsText,1,7);
	    GridPane.setHalignment(commentsText,HPos.LEFT);
	    GridPane.setConstraints(commentsText,1,7,5,1);
	    
	    Label teacher = new Label("Teacher:");
	    teacher.getStyleClass().add("label");
	    Label teacherText = new Label(incidentReport.getTeachersPresent());
	    teacherText.getStyleClass().add("response-label");
	    
	    viewIncidentReportLayout.add(teacher,0,8);
	    GridPane.setHalignment(teacher,HPos.RIGHT);
	    viewIncidentReportLayout.add(teacherText,1,8);
	    GridPane.setHalignment(teacherText,HPos.LEFT);
	    GridPane.setConstraints(teacherText,1,8,2,1);
	    
		Scene viewIncidentReportScene = new Scene(viewIncidentReportLayout);
		stage.setScene(viewIncidentReportScene);
		stage.showAndWait();
	}
	
}
