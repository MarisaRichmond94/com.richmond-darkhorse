package com.richmond.darkhorse.ProjectSB.gui.component;
import com.richmond.darkhorse.ProjectSB.Director;
import com.richmond.darkhorse.ProjectSB.Teacher;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.TextField;

//TODO figure out why changing info on one teacher carries over to the next teacher upon hitting the next arrow (even though the information in the actual
//teacher does NOT change

public class TeacherInfoPane extends GridPane {
	
	boolean isLead;
	
	public TeacherInfoPane(Director director,Teacher teacher,Teacher assistantTeacher) {
		
		isLead = true;
		
		//GridPane
		this.setVgap(10);
		this.setHgap(10);
		GridPane.setHalignment(this,HPos.CENTER);
		GridPane.setValignment(this,VPos.CENTER);
		ColumnConstraints columnOne = new ColumnConstraints();
		columnOne.setPercentWidth(29);
	    ColumnConstraints columnTwo = new ColumnConstraints();
	    columnTwo.setPercentWidth(29);
	    ColumnConstraints columnThree = new ColumnConstraints();
	    columnThree.setPercentWidth(29);
	    ColumnConstraints columnFour = new ColumnConstraints();
	    columnFour.setPercentWidth(13);
	    this.getColumnConstraints().addAll(columnOne,columnTwo,columnThree,columnFour);
	    this.getStyleClass().add("gridpane");
	    
	    Label leadTeacherLabel = new Label("Lead Teacher");
	    Label assistantTeacherLabel = new Label("Assistant Teacher");
	    leadTeacherLabel.getStyleClass().add("subtitle");
	    assistantTeacherLabel.getStyleClass().add("subtitle");
	    assistantTeacherLabel.setVisible(false);
	
	    Label teacherName = new Label("full name:");
	    teacherName.getStyleClass().add("label");
	    TextField nameField = new TextField();
	    nameField.setPromptText(teacher.getFirstName() + " " + teacher.getLastName());
	    nameField.getStyleClass().add("textfield");
	    nameField.setDisable(true);
	    nameField.setMaxWidth(500);
	    
	    Label employeeID = new Label("employee id:");
	    employeeID.getStyleClass().add("label");
	    TextField identificationField = new TextField();
	    identificationField.setPromptText(teacher.getTeacherID());
	    identificationField.getStyleClass().add("textfield");
	    identificationField.setDisable(true);
	    identificationField.setMaxWidth(500);
	    
	    Label classroom = new Label("classroom:");
	    classroom.getStyleClass().add("label");
	    TextField classroomField = new TextField();
	    classroomField.setPromptText("" + teacher.getClassroom(teacher.getClassroomID()) + "");
	    classroomField.getStyleClass().add("textfield"); 
	    classroomField.setDisable(true);
	    classroomField.setMaxWidth(500);
	    
	    Label email = new Label("e-mail:");
	    email.getStyleClass().add("label");
	    TextField emailField = new TextField();
	    if(teacher.getContact() == null || teacher.getContact().getEmail() == null) {emailField.setPromptText("e-mail");}
	    else {emailField.setPromptText(teacher.getContact().getEmail());}
	    emailField.getStyleClass().add("textfield"); 
	    emailField.setDisable(true);
	    emailField.setMaxWidth(500);
	    
	    Label phoneNumber = new Label("cell number:");
	    phoneNumber.getStyleClass().add("label");
	    TextField phoneNumberField = new TextField();
	    if(teacher.getContact() != null && teacher.getContact().getCellNumber() != null) {phoneNumberField.setPromptText(teacher.getContact().getCellNumber());}
	    else {phoneNumberField.setPromptText("(xxx)-xxx-xxxx");}
	    phoneNumberField.getStyleClass().add("textfield"); 
	    phoneNumberField.setDisable(true);
	    phoneNumberField.setMaxWidth(500);
	    
	    Label isLeadTeacher = new Label("lead");
	    isLeadTeacher.getStyleClass().add("label");
	    Label isAssistantTeacher = new Label("assistant");
	    isAssistantTeacher.getStyleClass().add("label");
	    isLeadTeacher.setVisible(false);
	    ImageView nextArrowViewer = new ImageView();
	    Image nextArrow = new Image("arrow.png");
	    nextArrowViewer.setImage(nextArrow);
	    nextArrowViewer.setPreserveRatio(true);
	    nextArrowViewer.setFitHeight(50);
	    ImageButton arrowButton = new ImageButton(nextArrowViewer);
	    arrowButton.setOnAction(e -> {
	    		if(isLead == true && assistantTeacher != null) {
	    			assistantTeacherLabel.setVisible(true);
	    			leadTeacherLabel.setVisible(false);
	    			isAssistantTeacher.setVisible(false);
	    			isLeadTeacher.setVisible(true);
	    			nameField.setPromptText(assistantTeacher.getFirstName() + " " + assistantTeacher.getLastName());
	    			identificationField.setPromptText(assistantTeacher.getTeacherID());
	    			classroomField.setPromptText("" + assistantTeacher.getClassroom(assistantTeacher.getClassroomID()));
	    			if(assistantTeacher.getContact() == null || assistantTeacher.getContact().getEmail() == null) {emailField.setPromptText("e-mail");}
	    			else{emailField.setPromptText(assistantTeacher.getContact().getEmail());};
	    			if(assistantTeacher.getContact() == null || assistantTeacher.getContact().getCellNumber() == null) {phoneNumberField.setPromptText("(xxx)-xxx-xxxx");}
	    			else {phoneNumberField.setPromptText("" + assistantTeacher.getContact().getCellNumber() + "");}
	    			isLead = false;
	    		}else if(isLead == false) {
	    			assistantTeacherLabel.setVisible(false);
	    			leadTeacherLabel.setVisible(true);
	    			isAssistantTeacher.setVisible(true);
	    			isLeadTeacher.setVisible(false);
	    			nameField.setPromptText(teacher.getFirstName() + " " + teacher.getLastName());
	    			identificationField.setPromptText(teacher.getTeacherID());
	    			classroomField.setPromptText("" + teacher.getClassroom(teacher.getClassroomID()));
	    			if(teacher.getContact() == null || teacher.getContact().getEmail() == null) {emailField.setPromptText("e-mail");}
	    			else{emailField.setPromptText(teacher.getContact().getEmail());};
	    			if(teacher.getContact() == null || teacher.getContact().getCellNumber() == null) {phoneNumberField.setPromptText("(xxx)-xxx-xxxx");}
	    			else {phoneNumberField.setPromptText("" + teacher.getContact().getCellNumber() + "");}
	    			isLead = true;
	    		}
	    });
	    arrowButton.getStyleClass().add("button");
	    
	    Label writeLabel = new Label("write");
	    ImageView writeViewer = new ImageView();
	    Image write = new Image("write.png");
	    writeViewer.setImage(write);
	    writeViewer.setPreserveRatio(true);
	    writeViewer.setFitHeight(150);
	    ImageButton writeButton = new ImageButton(writeViewer);
	    writeButton.getStyleClass().add("button");
	    writeLabel.getStyleClass().add("label");
	    writeButton.setVisible(false);
	    writeLabel.setVisible(false);
	    
	    Label editLabel = new Label("edit");
	    editLabel.getStyleClass().add("label");
	    ImageView editViewer = new ImageView();
	    Image edit = new Image("edit.png");
	    editViewer.setImage(edit);
	    editViewer.setPreserveRatio(true);
	    editViewer.setFitHeight(150);
	    ImageButton editButton = new ImageButton(editViewer);
	    editButton.setOnAction(e -> {
	    		editButton.setVisible(false);
	    		editLabel.setVisible(false);
	    		writeButton.setVisible(true);
	    		writeLabel.setVisible(true);
	    		emailField.setDisable(false);
	    		phoneNumberField.setDisable(false);
	    });
	    editButton.getStyleClass().add("button");
	    
	    writeButton.setOnAction(e -> {
	    		if(isLead == true) {
	    			String teacherEmail = null;
		    		if(emailField.getText().isEmpty() && teacher.getContact().getEmail() != null) {teacherEmail = teacher.getContact().getEmail();}
		    		else {teacherEmail = emailField.getText();}
		    		teacher.getContact().setEmail(teacherEmail);
			    	String teachersPhoneNumber = null;
			    	if(phoneNumberField.getText().isEmpty() && teacher.getContact().getCellNumber() != null) {teachersPhoneNumber = teacher.getContact().getCellNumber();}
			    	else {teachersPhoneNumber = phoneNumberField.getText();}
		    		teacher.getContact().setCellNumber(teachersPhoneNumber);
	    		}else {
	    			String teacherEmail = null;
		    		if(emailField.getText().isEmpty() && assistantTeacher.getContact().getEmail() != null) {teacherEmail = assistantTeacher.getContact().getEmail();}
		    		else {teacherEmail = emailField.getText();}
	    			assistantTeacher.getContact().setEmail(teacherEmail);
	    			String teachersPhoneNumber = null;
			    	if(phoneNumberField.getText().isEmpty() && assistantTeacher.getContact().getCellNumber() != null) {teachersPhoneNumber = assistantTeacher.getContact().getCellNumber();}
			    	else {teachersPhoneNumber = phoneNumberField.getText();}
	    			assistantTeacher.getContact().setCellNumber(teachersPhoneNumber);
	    		}
	    		writeButton.setVisible(false);
	    		writeLabel.setVisible(false);
	    		editButton.setVisible(true);
	    		editLabel.setVisible(true);
	    		emailField.setDisable(true);
	    		phoneNumberField.setDisable(true);
	    });
	    
	    this.add(leadTeacherLabel,1,0);
	    GridPane.setConstraints(leadTeacherLabel,1,0,2,1);
	    GridPane.setHalignment(leadTeacherLabel,HPos.CENTER);
	    this.add(assistantTeacherLabel,1,0);
	    GridPane.setConstraints(assistantTeacherLabel,1,0,2,1);
	    GridPane.setHalignment(assistantTeacherLabel,HPos.CENTER);
	    this.add(teacherName,0,1);
	    GridPane.setHalignment(teacherName,HPos.RIGHT);
	    this.add(nameField,1,1);
	    GridPane.setConstraints(nameField,1,1,2,1);
	    this.add(employeeID,0,2);
	    GridPane.setHalignment(employeeID,HPos.RIGHT);
	    this.add(identificationField,1,2);
	    GridPane.setConstraints(identificationField,1,2,2,1);
	    this.add(classroom,0,3);
	    GridPane.setHalignment(classroom,HPos.RIGHT);
	    this.add(classroomField,1,3);
	    GridPane.setConstraints(classroomField,1,3,2,1);
	    this.add(email,0,4);
	    GridPane.setHalignment(email,HPos.RIGHT);
	    this.add(emailField,1,4);
	    GridPane.setConstraints(emailField,1,4,2,1);
	    this.add(phoneNumber,0,5);
	    GridPane.setHalignment(phoneNumber,HPos.RIGHT);
	    this.add(phoneNumberField,1,5);
	    GridPane.setConstraints(phoneNumberField,1,5,2,1);
	    this.add(arrowButton,3,3);
	    this.add(isLeadTeacher,3,4);
	    GridPane.setHalignment(isLeadTeacher,HPos.LEFT);
	    this.add(isAssistantTeacher,3,4);
	    GridPane.setHalignment(isAssistantTeacher,HPos.LEFT);
	    this.add(editButton,1,6);
	    GridPane.setHalignment(editButton,HPos.CENTER);
	    GridPane.setConstraints(editButton,1,6,2,1);
	    this.add(editLabel,1,7);
	    GridPane.setHalignment(editLabel,HPos.CENTER);
	    GridPane.setConstraints(editLabel,1,7,2,1);
	    this.add(writeButton,1,6);
	    GridPane.setHalignment(writeButton,HPos.CENTER);
	    GridPane.setConstraints(writeButton,1,6,2,1);
	    this.add(writeLabel,1,7);
	    GridPane.setHalignment(writeLabel,HPos.CENTER);
	    GridPane.setConstraints(writeLabel,1,7,2,1);
	    this.getStylesheets().add("modifyclassroomsetup.css");
	
	}
}
