package com.richmond.darkhorse.ProjectSB.gui.component;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.richmond.darkhorse.ProjectSB.BehaviorReport;
import com.richmond.darkhorse.ProjectSB.Director;
import com.richmond.darkhorse.ProjectSB.StaffMember;
import com.richmond.darkhorse.ProjectSB.Teacher;
import javafx.scene.control.TextField;
import javafx.geometry.HPos;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;

public class ViewSelectedBehaviorReport extends GridPane{
	
	private List<TextField> enabledTextFields = new ArrayList<TextField>();
	private BehaviorReport behaviorReport;
	private TextField classroomActivityText,childsBehaviorText,consequenceText,childsReactionText,timeLastedText; 
	
	public ViewSelectedBehaviorReport(Director director,BehaviorReport behaviorReport) {
		this.behaviorReport = behaviorReport;
		
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
		
		Label title = new Label("Behavior Incident Form");
	    title.getStyleClass().add("subtitle");
	    
	    this.add(title,0,0);
	    GridPane.setHalignment(title,HPos.CENTER);
	    GridPane.setConstraints(title,0,0,6,2);
	    
	    Label childsName = new Label("Student:");
	    childsName.getStyleClass().add("label");
	    TextField nameText = new TextField(behaviorReport.getStudentName());
	    nameText.getStyleClass().add("textfield");
	    
	    this.add(childsName,0,2);
	    GridPane.setHalignment(childsName,HPos.RIGHT);
	    this.add(nameText,1,2);
	    
	    Label date = new Label("Date:");
	    date.getStyleClass().add("label");
	    TextField dateText = new TextField(behaviorReport.getStringDate());
	    dateText.getStyleClass().add("textfield");
	    Label time = new Label("Time:");
	    time.getStyleClass().add("label");
	    TextField timeText = new TextField(behaviorReport.getStringTime());
	    timeText.getStyleClass().add("textfield");
	    
	    this.add(date,2,2);
	    GridPane.setHalignment(date,HPos.RIGHT);
	    this.add(dateText,3,2);
	    GridPane.setHalignment(dateText,HPos.LEFT);
	    this.add(time,4,2);
	    GridPane.setHalignment(time,HPos.RIGHT);
	    this.add(timeText,5,2);
	    GridPane.setHalignment(timeText,HPos.LEFT);
	    
	    Label teacher = new Label("Teacher:");
	    teacher.getStyleClass().add("label");
	    TextField teacherText = new TextField("" + behaviorReport.getTeacher(behaviorReport.getTeacherID()) + "");
	    teacherText.getStyleClass().add("textfield");
	    
	    this.add(teacher,0,3);
	    GridPane.setHalignment(teacher,HPos.RIGHT);
	    this.add(teacherText,1,3);
	    GridPane.setHalignment(teacherText,HPos.LEFT);
	    
	    Label classroomActivity = new Label("Activity:");
	    classroomActivity.getStyleClass().add("label");
	    TextField classroomActivityText = new TextField(behaviorReport.getActivityDuringBehavior());
	    classroomActivityText.getStyleClass().add("textfield");
	    this.classroomActivityText = classroomActivityText;
	    
	    this.add(classroomActivity,2,3);
	    GridPane.setHalignment(classroomActivity,HPos.RIGHT);
	    this.add(classroomActivityText,3,3);
	    GridPane.setHalignment(classroomActivityText,HPos.LEFT);
	    GridPane.setConstraints(classroomActivityText,3,3,3,1);
	    
	    Label childsBehavior = new Label("Behavior:");
	    childsBehavior.getStyleClass().add("label");
	    TextField childsBehaviorText = new TextField(behaviorReport.getChildsBehavior());
	    childsBehaviorText.getStyleClass().add("textfield");
	    childsBehaviorText.setMaxWidth(10000);
	    this.childsBehaviorText = childsBehaviorText;
	    
	    this.add(childsBehavior,0,4);
	    GridPane.setHalignment(childsBehavior,HPos.RIGHT);
	    GridPane.setConstraints(childsBehavior,0,4,1,1);
	    this.add(childsBehaviorText,1,4);
	    GridPane.setHalignment(childsBehaviorText,HPos.LEFT);
	    GridPane.setConstraints(childsBehaviorText,1,4,5,1);
	    
	    Label consequence = new Label("Consequence:");
	    consequence.getStyleClass().add("label");
	    TextField consequenceText = new TextField(behaviorReport.getConsequence());
	    consequenceText.getStyleClass().add("textfield");
	    consequenceText.setMaxWidth(10000);
	    this.consequenceText = consequenceText;

	    this.add(consequence,0,5);
	    GridPane.setHalignment(consequence,HPos.RIGHT);
	    this.add(consequenceText,1,5);
	    GridPane.setHalignment(consequenceText,HPos.LEFT);
	    GridPane.setConstraints(consequenceText,1,5,4,1);
	    
	    Label childsReaction = new Label("Reaction:");
	    childsReaction.getStyleClass().add("label");
	    TextField childsReactionText = new TextField(behaviorReport.getStudentResponse());
	    childsReactionText.getStyleClass().add("textfield");
	    childsReactionText.setMaxWidth(10000);
	    this.childsReactionText = childsReactionText;
	    
	    this.add(childsReaction,0,6);
	    GridPane.setHalignment(childsReaction,HPos.RIGHT);
	    this.add(childsReactionText,1,6);
	    GridPane.setHalignment(childsReactionText,HPos.LEFT);
	    GridPane.setConstraints(childsReactionText,1,6,4,1);
	    
	    Label timeLasted = new Label("Duration:");
	    timeLasted.getStyleClass().add("label");
	    TextField timeLastedText = new TextField(behaviorReport.getDurationBehaviorOccurred());
	    timeLastedText.getStyleClass().add("textfield");
	    timeLastedText.setMaxWidth(10000);
	    this.timeLastedText = timeLastedText;
	    
	    this.add(timeLasted,0,7);
	    GridPane.setHalignment(timeLasted,HPos.RIGHT);
	    this.add(timeLastedText,1,7);
	    GridPane.setHalignment(timeLastedText,HPos.LEFT);
	    GridPane.setConstraints(timeLastedText,1,7,4,1);
	    
	    Label observedBy = new Label("Observed by:");
	    observedBy.getStyleClass().add("label");
	    TextField observedByText = new TextField(behaviorReport.getTeacherObserved());
	    observedByText.getStyleClass().add("textfield");
	    observedByText.setMaxWidth(10000);
	    
	    this.add(observedBy,0,8);
	    GridPane.setHalignment(observedBy,HPos.RIGHT);
	    this.add(observedByText,1,8);
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
	    TextField teacherSignatureText = new TextField(teachSignature);
	    teacherSignatureText.getStyleClass().add("textfield");
	    
	    Label directorSignature = new Label("Director:");
	    directorSignature.getStyleClass().add("label");
	    boolean directorsSignature = behaviorReport.getDirectorSignature();
	    String signature;
	    if(directorsSignature == true) { signature = director.getFirstName() + " " + director.getLastName();}
	    else {signature = " ";}
	    TextField directorSignatureText = new TextField(signature);
	    directorSignatureText.getStyleClass().add("textfield");
	    
	    disableAll(nameText,dateText,timeText,teacherText,classroomActivityText,childsBehaviorText,consequenceText,childsReactionText,timeLastedText,observedByText,teacherSignatureText,directorSignatureText);
	    enabledTextFields.add(classroomActivityText);
	    enabledTextFields.add(childsBehaviorText);
	    enabledTextFields.add(consequenceText);
	    enabledTextFields.add(childsReactionText);
	    enabledTextFields.add(timeLastedText);
	    enabledTextFields.add(directorSignatureText);
	    this.add(teacherSignature,0,9);
	    GridPane.setHalignment(teacherSignature,HPos.RIGHT);
	    this.add(teacherSignatureText,1,9);
	    GridPane.setHalignment(teacherSignatureText,HPos.LEFT);
	    GridPane.setConstraints(teacherSignatureText,1,9,2,1);
	    this.add(directorSignature,3,9);
	    GridPane.setHalignment(directorSignature,HPos.RIGHT);
	    this.add(directorSignatureText,4,9);
	    GridPane.setHalignment(directorSignatureText,HPos.LEFT);
	    GridPane.setConstraints(directorSignatureText,4,9,2,1);
		
	}
	
	public void enableAll() {
		for(TextField textField : enabledTextFields) {
			textField.setDisable(false);
		}
	}
	
	public BehaviorReport getBehaviorReport() {
		return behaviorReport;
	}
	
	public void disableAll(TextField nameText,TextField dateText,TextField timeText,TextField teacherText,TextField classroomActivityText,TextField childsBehaviorText,TextField consequenceText,TextField childsReactionText,TextField timeLastedText,TextField observedByText,TextField teacherSignatureText,TextField directorSignatureText) {
		nameText.setDisable(true);
		dateText.setDisable(true);
		timeText.setDisable(true);
		teacherText.setDisable(true);
		classroomActivityText.setDisable(true);
		childsBehaviorText.setDisable(true);
		consequenceText.setDisable(true);
		childsReactionText.setDisable(true);
		timeLastedText.setDisable(true);
		observedByText.setDisable(true);
		teacherSignatureText.setDisable(true);
		directorSignatureText.setDisable(true);
	}
	
	public List<TextField> getEnabledTextFields() {
		return enabledTextFields;
	}

	public String getClassroomActivityText() {
		return classroomActivityText.getText();
	}

	public String getChildsBehaviorText() {
		return childsBehaviorText.getText();
	}

	public String getConsequenceText() {
		return consequenceText.getText();
	}

	public String getChildsReactionText() {
		return childsReactionText.getText();
	}

	public String getTimeLastedText() {
		return timeLastedText.getText();
	}
	
}
