package com.richmond.darkhorse.ProjectSB.gui.component;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import java.util.Map;
import com.richmond.darkhorse.ProjectSB.Admin;
import com.richmond.darkhorse.ProjectSB.Center;
import com.richmond.darkhorse.ProjectSB.Classroom;
import com.richmond.darkhorse.ProjectSB.middleman.CreateNewTeacher;
import javafx.beans.binding.Bindings;
import javafx.geometry.HPos;

public class AddTeacher {
	
	private Admin admin;
	
	public AddTeacher(Admin admin) { 
		this.admin = admin;
	}
	
	public void display() {
		Stage stage = new Stage();
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.setTitle("Create New Teacher");
		
		//GridPane
		GridPane addTeacherLayout = new GridPane();
		addTeacherLayout.setVgap(10);
		addTeacherLayout.setHgap(10);
		ColumnConstraints columnOne = new ColumnConstraints();
		columnOne.setPercentWidth(33.3);
	    ColumnConstraints columnTwo = new ColumnConstraints();
	    columnTwo.setPercentWidth(33.3);
	    ColumnConstraints columnThree = new ColumnConstraints();
	    columnThree.setPercentWidth(33.3);
	    addTeacherLayout.getColumnConstraints().addAll(columnOne,columnTwo,columnThree);
	    addTeacherLayout.getStyleClass().add("gridpane");
		
		//GridPane - Icon
	    ImageView teacherViewer = new ImageView();
	    Image teacherIcon = new Image("teacher.png");
	    teacherViewer.setImage(teacherIcon);
	    teacherViewer.setPreserveRatio(true);
	    teacherViewer.setFitHeight(200);
	    
		//GridPane - Title
	    Label title = new Label("Create New \nTeacher");
	    title.getStyleClass().add("title");
	    
		//GridPane - First Name
	    Label teacherFirstName = new Label("First name:");
	    teacherFirstName.getStyleClass().add("label");
	    TextField firstNameField = new TextField();
	    firstNameField.setPromptText("first name");
	    firstNameField.setMaxWidth(600);
	    firstNameField.getStyleClass().add("textfield");
	    
		//GridPane - Last Name
	    Label teacherLastName = new Label("Last name:");
	    teacherLastName.getStyleClass().add("label");
	    TextField lastNameField = new TextField();
	    lastNameField.setPromptText("last name");
	    lastNameField.setMaxWidth(600);
	    lastNameField.getStyleClass().add("textfield");
	    
		//GridPane - Center Box
	    Label centerSelection = new Label("Center:");
	    centerSelection.getStyleClass().add("label");
	    ChoiceBox<Center> centerBox = new ChoiceBox<Center>();
	    if(admin.getCenters().isEmpty()) {centerBox.setDisable(true);}
	    else {
	    		Map<String,Center> centers = admin.getCenters();
	    		for(Center activeCenter : centers.values()) {
	    			centerBox.getItems().add(activeCenter);
	    		}
	    		centerBox.setValue(centerBox.getItems().get(0));
	    }
	    centerBox.setMaxWidth(600);
	    centerBox.getStyleClass().add("choicebox");
	    
		//GridPane - Classroom Box
	    Label classroomSelection = new Label("Classroom:");
	    classroomSelection.getStyleClass().add("label");
	    ChoiceBox<ClassroomHolder> classroomBox = new ChoiceBox<ClassroomHolder>();
	    ClassroomHolder emptyClassroom = new ClassroomHolder(null);
	    classroomBox.getItems().add(emptyClassroom);
	    Map<String,Center> centers = admin.getCenters();
	    for(Center center : centers.values()) {
	    		Map<String,Classroom> openClassrooms = center.getClassrooms();
	    		for(Classroom classroom : openClassrooms.values()) {
	    			if(classroom.getTeacherID() == null || classroom.getAssistantTeacherID() == null) {
	    				ClassroomHolder newClassroomHolder = new ClassroomHolder(classroom);
	    				classroomBox.getItems().add(newClassroomHolder);
	    			}
	    		}
	    }
	    	classroomBox.setValue(emptyClassroom);
	    classroomBox.setMaxWidth(600);
	    classroomBox.getStyleClass().add("choicebox");
	    
		//GridPane - Active Teacher Warning
	    Label activeTeacherWarning = new Label("The classroom you have selected already has an active teacher");
	    activeTeacherWarning.getStyleClass().add("label");
	    activeTeacherWarning.setTextFill(Color.RED);
	    addTeacherLayout.add(activeTeacherWarning, 0, 6);
	    GridPane.setHalignment(activeTeacherWarning, HPos.CENTER);
	    GridPane.setConstraints(activeTeacherWarning, 0, 6, 3, 1);
	    activeTeacherWarning.setVisible(false);
	    
		//GridPane - Create Button
	    Button createTeacherButton = new Button("Create Teacher");
	    createTeacherButton.getStyleClass().add("button");
	    createTeacherButton.disableProperty().bind(
	    	    Bindings.isEmpty(firstNameField.textProperty())
	    	    .or(Bindings.isEmpty(lastNameField.textProperty()))
	    	);
	    createTeacherButton.setOnAction(e -> {
	    		String teachersFirstName = firstNameField.getText();
	    		String teachersLastName = lastNameField.getText();
	    		Center teachersCenter = centerBox.getValue();
	    		Classroom teachersClassroom = null;
	    		if(classroomBox.getValue().getClassroom() != null) {teachersClassroom = classroomBox.getValue().getClassroom();}
	    		if(teachersClassroom == null) {
	    			Thread createNewTeacher = new Thread(new CreateNewTeacher(admin,teachersFirstName,teachersLastName,teachersCenter));
	    			createNewTeacher.start();
	    			stage.close();
	    		}else if(teachersClassroom.getTeacherID() != null && teachersClassroom.getAssistantTeacherID() != null) {
	    			activeTeacherWarning.setVisible(true);
	    		}else{
	    			Thread createNewTeacher = new Thread(new CreateNewTeacher(admin,teachersFirstName,teachersLastName,teachersCenter,teachersClassroom));
	    			createNewTeacher.start();
	    			stage.close();
	    		}
	    });
	    
		//GridPane - Cancel Button
	    Button cancelButton = new Button("Cancel");
	    cancelButton.getStyleClass().add("button");
	    cancelButton.setOnAction(e -> stage.close());
	    
		//GridPane - Layout
	    addTeacherLayout.add(teacherViewer, 0, 0);
	    GridPane.setHalignment(teacherViewer, HPos.CENTER);
	    addTeacherLayout.add(title, 1, 0);
	    GridPane.setConstraints(title,1,0,2,1);
	    GridPane.setHalignment(title, HPos.CENTER);
	    addTeacherLayout.add(teacherFirstName, 0, 1);
	    GridPane.setHalignment(teacherFirstName, HPos.RIGHT);
	    addTeacherLayout.add(firstNameField, 1, 1);
	    GridPane.setConstraints(firstNameField,1,1,2,1);
	    addTeacherLayout.add(teacherLastName, 0, 2);
	    GridPane.setHalignment(teacherLastName, HPos.RIGHT);
	    addTeacherLayout.add(lastNameField, 1, 2);
	    GridPane.setConstraints(lastNameField,1,2,2,1);
	    addTeacherLayout.add(centerSelection, 0, 3);
	    GridPane.setHalignment(centerSelection, HPos.RIGHT);
	    addTeacherLayout.add(centerBox, 1, 3);
	    GridPane.setConstraints(centerBox,1,3,2,1);
	    addTeacherLayout.add(classroomSelection, 0, 4);
	    GridPane.setHalignment(classroomSelection, HPos.RIGHT);
	    addTeacherLayout.add(classroomBox, 1, 4);
	    GridPane.setConstraints(classroomBox,1,4,2,1);
	    addTeacherLayout.add(createTeacherButton, 1, 5);
	    GridPane.setHalignment(createTeacherButton, HPos.CENTER);
	    addTeacherLayout.add(cancelButton, 2, 5);
	    GridPane.setHalignment(cancelButton, HPos.CENTER);
	    addTeacherLayout.getStylesheets().add("addteacher.css");
		
		Scene createTeacherScene = new Scene(addTeacherLayout);
		stage.setScene(createTeacherScene);
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
				return classroom.getCenter(classroom.getCenterID()) + ": " + classroom.getClassroomType();
			}
		}
	}
	
}

