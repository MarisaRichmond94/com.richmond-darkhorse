package com.richmond.darkhorse.ProjectSB.gui.component;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import java.util.HashMap;
import java.util.Map;
import com.richmond.darkhorse.ProjectSB.Center;
import com.richmond.darkhorse.ProjectSB.Classroom;
import com.richmond.darkhorse.ProjectSB.Director;
import com.richmond.darkhorse.ProjectSB.middleman.CreateNewStudent;
import javafx.beans.binding.Bindings;
import javafx.geometry.HPos;
import javafx.scene.control.CheckBox;

public class AddStudent {

	private Director director;
	
	public AddStudent(Director director) {
		this.director = director;
	}

	public void display() {
		Stage stage = new Stage();
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.setTitle("Create New Student");
		
		//GridPane
		GridPane addStudentLayout = new GridPane();
		addStudentLayout.setVgap(10);
		addStudentLayout.setHgap(10);
		ColumnConstraints columnOne = new ColumnConstraints();
		columnOne.setPercentWidth(33.3);
	    ColumnConstraints columnTwo = new ColumnConstraints();
	    columnTwo.setPercentWidth(33.3);
	    ColumnConstraints columnThree = new ColumnConstraints();
	    columnThree.setPercentWidth(33.3);
	    addStudentLayout.getColumnConstraints().addAll(columnOne,columnTwo,columnThree);
	    addStudentLayout.getStyleClass().add("gridpane");
		
		//GridPane - Icon
	    ImageView studentViewer = new ImageView();
	    Image studentIcon = new Image("students.png");
	    studentViewer.setImage(studentIcon);
	    studentViewer.setPreserveRatio(true);
	    studentViewer.setFitHeight(150);
	    
		//GridPane - Title
	    Label title = new Label("Create New \n Student");
	    title.getStyleClass().add("title");
	    
		//GridPane - First Name
	    Label studentFirstName = new Label("first name:");
	    studentFirstName.getStyleClass().add("label");
	    TextField firstNameField = new TextField();
	    firstNameField.setPromptText("first name");
	    firstNameField.setMaxWidth(700);
	    firstNameField.getStyleClass().add("textfield");
	    
		//GridPane - Last Name
	    Label studentLastName = new Label("last name:");
	    studentLastName.getStyleClass().add("label");
	    TextField lastNameField = new TextField();
	    lastNameField.setPromptText("last name");
	    lastNameField.setMaxWidth(700);
	    lastNameField.getStyleClass().add("textfield");
	    
		//GridPane - Birth Date
	    Label studentBirthDate = new Label("birth date:");
	    studentBirthDate.getStyleClass().add("label");
	    TextField birthDateField = new TextField();
	    birthDateField.setPromptText("MM/DD/YYYY");
	    birthDateField.setMaxWidth(700);
	    birthDateField.getStyleClass().add("textfield");
	    
		//GridPane - Classroom
	    Label studentClassroom = new Label("classroom:");
	    studentClassroom.getStyleClass().add("label");
	    ChoiceBox<ClassroomHolder> classroomBox = new ChoiceBox<ClassroomHolder>();
	    ClassroomHolder emptyClassroom = new ClassroomHolder(null);
	    classroomBox.getItems().add(emptyClassroom);
	    Center center = director.getCenter(director.getCenterID());
	    Map<String,Classroom> classrooms = center.getClassrooms();
	    for(Classroom classroom : classrooms.values()) {
	    		ClassroomHolder newClassroom = new ClassroomHolder(classroom);
	    		classroomBox.getItems().add(newClassroom);
	    }
	    	classroomBox.setValue(classroomBox.getItems().get(0));
	    classroomBox.setMaxWidth(700);
	    classroomBox.getStyleClass().add("choicebox");
	    
	    //GridPane - Attendance Plan
	    Label studentAttendancePlan = new Label("attendance plan:");
	    studentAttendancePlan.getStyleClass().add("label");
	    CheckBox mondayCheckBox = new CheckBox("Monday");
	    mondayCheckBox.getStyleClass().add("checkbox");
	    CheckBox tuesdayCheckBox = new CheckBox("Tuesday");
	    tuesdayCheckBox.getStyleClass().add("checkbox");
	    CheckBox wednesdayCheckBox = new CheckBox("Wednesday");
	    wednesdayCheckBox.getStyleClass().add("checkbox");
	    CheckBox thursdayCheckBox = new CheckBox("Thursday");
	    thursdayCheckBox.getStyleClass().add("checkbox");
	    CheckBox fridayCheckBox = new CheckBox("Friday");
	    fridayCheckBox.getStyleClass().add("checkbox");
	    
		//GridPane - Create Button
	    Button createStudentButton = new Button("Create Student");
	    createStudentButton.getStyleClass().add("button");
	    createStudentButton.disableProperty().bind(
	    	    Bindings.isEmpty(firstNameField.textProperty())
	    	    .or(Bindings.isEmpty(lastNameField.textProperty())
	    	    		.or(Bindings.isEmpty(birthDateField.textProperty())))
	    	);
	    createStudentButton.setOnAction(e -> {
	    		String firstName = firstNameField.getText();
	    		String lastName = lastNameField.getText();
	    		String birthDate = birthDateField.getText();
	    		Classroom classroom = null;
	    		if(classroomBox.getValue().getClassroom() != null) {
	    			classroom = classroomBox.getValue().getClassroom();
	    		}
	    		Map<String,Boolean> attendancePlan = new HashMap<String,Boolean>();
	    		if(mondayCheckBox.isSelected() == true) {attendancePlan.put("Monday",true);
	    		}else {attendancePlan.put("Monday", false);}
	    		if(tuesdayCheckBox.isSelected() == true) {attendancePlan.put("Tuesday",true);
	    		}else {attendancePlan.put("Tuesday", false);}
	    		if(wednesdayCheckBox.isSelected() == true) {attendancePlan.put("Wednesday",true);
	    		}else {attendancePlan.put("Wednesday", false);}
	    		if(thursdayCheckBox.isSelected() == true) {attendancePlan.put("Thursday",true);
	    		}else {attendancePlan.put("Thursday", false);}
	    		if(fridayCheckBox.isSelected() == true) {attendancePlan.put("Friday",true);
	    		}else {attendancePlan.put("Friday", false);}
	    		Thread createNewStudent = new Thread(new CreateNewStudent(director,firstName,lastName,birthDate,classroom,attendancePlan));
	    		createNewStudent.start();
    			stage.close();
	    });
	    
		//GridPane - Cancel Button
	    Button cancelButton = new Button("Cancel");
	    cancelButton.getStyleClass().add("button");
	    cancelButton.setOnAction(e -> stage.close());
	    
		//GridPane - Layout
	    addStudentLayout.add(studentViewer,0,0);
	    GridPane.setHalignment(studentViewer, HPos.CENTER);
	    addStudentLayout.add(title,1,0);
	    GridPane.setConstraints(title,1,0,2,1);
	    GridPane.setHalignment(title,HPos.CENTER);
	    addStudentLayout.add(studentFirstName,0,1);
	    GridPane.setHalignment(studentFirstName,HPos.CENTER);
	    addStudentLayout.add(firstNameField,1,1);
	    GridPane.setConstraints(firstNameField,1,1,2,1);
	    addStudentLayout.add(studentLastName,0,2);
	    GridPane.setHalignment(studentLastName,HPos.CENTER);
	    addStudentLayout.add(lastNameField,1,2);
	    GridPane.setConstraints(lastNameField,1,2,2,1);
	    addStudentLayout.add(studentBirthDate,0,3);
	    GridPane.setHalignment(studentBirthDate,HPos.CENTER);
	    addStudentLayout.add(birthDateField,1,3);
	    GridPane.setConstraints(birthDateField,1,3,2,1);
	    addStudentLayout.add(studentClassroom, 0,4);
	    GridPane.setHalignment(studentClassroom,HPos.CENTER);
	    addStudentLayout.add(classroomBox,1,4);
	    GridPane.setConstraints(classroomBox,1,4,2,1);
	    addStudentLayout.add(studentAttendancePlan,0,5);
	    GridPane.setHalignment(studentAttendancePlan,HPos.CENTER);
	    addStudentLayout.add(mondayCheckBox,1,5);
	    addStudentLayout.add(tuesdayCheckBox,1,6);
	    addStudentLayout.add(wednesdayCheckBox,1,7);
	    addStudentLayout.add(thursdayCheckBox,1,8);
	    addStudentLayout.add(fridayCheckBox,1,9);
	    addStudentLayout.add(createStudentButton,1,10);
	    GridPane.setHalignment(createStudentButton,HPos.CENTER);
	    addStudentLayout.add(cancelButton,2,10);
	    GridPane.setHalignment(cancelButton,HPos.CENTER);
	    addStudentLayout.getStylesheets().add("addstudent.css");
		
		Scene createStudentScene = new Scene(addStudentLayout);
		stage.setScene(createStudentScene);
		stage.showAndWait();
	}
	
	public class ClassroomHolder{
		private Classroom classroom;
		public ClassroomHolder(Classroom classroom) {
			this.classroom = classroom;
		}
		public Classroom getClassroom() {
				return classroom;
		}
		@Override
		public String toString() {
			if(classroom == null) {
				return "N/A";
			}else {
				return classroom.getCenter(classroom.getCenterID()).getAbbreviatedName() + ": " + classroom.getClassroomType();
			}
		}
	}
	
}
