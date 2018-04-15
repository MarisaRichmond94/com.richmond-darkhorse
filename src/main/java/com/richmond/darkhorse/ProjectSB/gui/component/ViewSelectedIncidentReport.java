package com.richmond.darkhorse.ProjectSB.gui.component;
import java.util.ArrayList;
import java.util.List;
import com.richmond.darkhorse.ProjectSB.Director;
import com.richmond.darkhorse.ProjectSB.IncidentReport;
import javafx.geometry.HPos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;

public class ViewSelectedIncidentReport extends GridPane{

	private List<TextField> enabledTextFields = new ArrayList<TextField>();
	private IncidentReport incidentReport;
	private TextField descriptionText,actionText,appearanceText,commentsText; 
	
	public ViewSelectedIncidentReport(Director director,IncidentReport incidentReport) {
		this.incidentReport = incidentReport;
		
		this.setVgap(10);
		this.setHgap(10);
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
	    this.setStyle("-fx-background-color: #CCCCCC;");
	    this.getStyleClass().add("gridpane");
	    this.getStylesheets().add("directormailbox.css");
		
	    Label title = new Label("Incident Report Form");
	    title.getStyleClass().add("subtitle");
	    
	    this.add(title,0,0);
	    GridPane.setHalignment(title,HPos.CENTER);
	    GridPane.setConstraints(title,0,0,6,2);
	    
	    Label childsName = new Label("Student:");
	    childsName.getStyleClass().add("label");
	    TextField nameText = new TextField(incidentReport.getStudentName());
	    nameText.getStyleClass().add("textfield");
	    
	    this.add(childsName,0,2);
	    GridPane.setHalignment(childsName,HPos.RIGHT);
	    this.add(nameText,1,2);
	    
	    Label date = new Label("Date:");
	    date.getStyleClass().add("label");
	    TextField dateText = new TextField(incidentReport.getStringDate());
	    dateText.getStyleClass().add("textfield");
	    Label time = new Label("Time:");
	    time.getStyleClass().add("label");
	    TextField timeText = new TextField(incidentReport.getStringTime());
	    timeText.getStyleClass().add("textfield");
	    
	    this.add(date,2,2);
	    GridPane.setHalignment(date,HPos.RIGHT);
	    this.add(dateText,3,2);
	    GridPane.setHalignment(dateText,HPos.LEFT);
	    this.add(time,4,2);
	    GridPane.setHalignment(time,HPos.RIGHT);
	    this.add(timeText,5,2);
	    GridPane.setHalignment(timeText,HPos.LEFT);
	    
	    Label description = new Label("Description:");
	    description.getStyleClass().add("label");
	    TextField descriptionText = new TextField(incidentReport.getIncidentDescription());
	    descriptionText.getStyleClass().add("textfield");
	    this.descriptionText = descriptionText;
	    
	    this.add(description,0,3);
	    GridPane.setHalignment(description,HPos.RIGHT);
	    this.add(descriptionText,1,3);
	    GridPane.setHalignment(descriptionText,HPos.LEFT);
	    GridPane.setConstraints(descriptionText,1,3,5,1);
	    
	    Label action = new Label("Action taken:");
	    action.getStyleClass().add("label");
	    TextField actionText = new TextField(incidentReport.getActionTaken());
	    actionText.getStyleClass().add("textfield");
	    this.actionText = actionText;
	    
	    this.add(action,0,4);
	    GridPane.setHalignment(action,HPos.RIGHT);
	    this.add(actionText,1,4);
	    GridPane.setHalignment(actionText,HPos.LEFT);
	    GridPane.setConstraints(actionText,1,4,5,1);
	    
	    Label appearance = new Label("Appearance:");
	    appearance.getStyleClass().add("label");
	    TextField appearanceText = new TextField(incidentReport.getStudentCondition());
	    appearanceText.getStyleClass().add("textfield");
	    this.appearanceText = appearanceText;
	    
	    this.add(appearance,0,5);
	    GridPane.setHalignment(appearance,HPos.RIGHT);
	    this.add(appearanceText,1,5);
	    GridPane.setHalignment(appearanceText,HPos.LEFT);
	    GridPane.setConstraints(appearanceText,1,5,5,1);
	    
	    Label medical = new Label("Medical:");
	    medical.getStyleClass().add("label");
	    String medicalResponse;
	    if(incidentReport.isMedicalAttention() == true && incidentReport.isOnSite() == false) {medicalResponse = incidentReport.getStudentName() + " required off-site medical attention";}
	    else if(incidentReport.isMedicalAttention() == true && incidentReport.isOnSite() == true){ medicalResponse = incidentReport.getStudentName() + " required on-site medical attention";}
	    else { medicalResponse = incidentReport.getStudentName() + " did not require any immediate medical attention";}
	    TextField medicalText = new TextField(medicalResponse);
	    medicalText.getStyleClass().add("textfield");

	    this.add(medical,0,6);
	    GridPane.setHalignment(medical,HPos.RIGHT);
	    this.add(medicalText,1,6);
	    GridPane.setHalignment(medicalText,HPos.LEFT);
	    GridPane.setConstraints(medicalText,1,6,5,1);
	    
	    Label comments = new Label("Comments:");
	    comments.getStyleClass().add("label");
	    TextField commentsText = new TextField(incidentReport.getComments());
	    commentsText.getStyleClass().add("textfield");
	    this.commentsText = commentsText;
	    
	    this.add(comments,0,7);
	    GridPane.setHalignment(comments,HPos.RIGHT);
	    this.add(commentsText,1,7);
	    GridPane.setHalignment(commentsText,HPos.LEFT);
	    GridPane.setConstraints(commentsText,1,7,5,1);
	    
	    Label teacher = new Label("Teacher:");
	    teacher.getStyleClass().add("label");
	    TextField teacherText = new TextField(incidentReport.getTeachersPresent());
	    teacherText.getStyleClass().add("textfield");
	    
	    this.add(teacher,0,8);
	    GridPane.setHalignment(teacher,HPos.RIGHT);
	    this.add(teacherText,1,8);
	    GridPane.setHalignment(teacherText,HPos.LEFT);
	    GridPane.setConstraints(teacherText,1,8,2,1);
	    
	    disableAll(nameText,dateText,timeText,teacherText,descriptionText,actionText,appearanceText,commentsText,medicalText);
	    enabledTextFields.add(descriptionText);
	    enabledTextFields.add(actionText);
	    enabledTextFields.add(appearanceText);
	    enabledTextFields.add(commentsText);
	}
	
	public void enableAll() {
		for(TextField textField : enabledTextFields) {
			textField.setDisable(false);
		}
	}
	
	public IncidentReport getIncidentReport() {
		return incidentReport;
	}
	
	public void disableAll(TextField nameText,TextField dateText,TextField timeText,TextField teacherText,TextField descriptionText,TextField actionText,TextField appearanceText,TextField commentsText,TextField medicalText) {
		nameText.setDisable(true);
		dateText.setDisable(true);
		timeText.setDisable(true);
		teacherText.setDisable(true);
		descriptionText.setDisable(true);
		actionText.setDisable(true);
		appearanceText.setDisable(true);
		commentsText.setDisable(true);
		medicalText.setDisable(true);
	}
	
	public List<TextField> getEnabledTextFields() {
		return enabledTextFields;
	}

	public String getDescriptionText() {
		return descriptionText.getText();
	}

	public String getActionText() {
		return actionText.getText();
	}

	public String getAppearanceText() {
		return appearanceText.getText();
	}

	public String getCommentsText() {
		return commentsText.getText();
	}
	
}
