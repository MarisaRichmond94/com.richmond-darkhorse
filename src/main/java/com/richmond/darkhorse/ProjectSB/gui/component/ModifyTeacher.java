package com.richmond.darkhorse.ProjectSB.gui.component;
import java.util.Map;
import com.richmond.darkhorse.ProjectSB.Admin;
import com.richmond.darkhorse.ProjectSB.Center;
import com.richmond.darkhorse.ProjectSB.Classroom;
import com.richmond.darkhorse.ProjectSB.Teacher;
import com.richmond.darkhorse.ProjectSB.middleman.ModifyExistingTeacher;
import javafx.geometry.HPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ModifyTeacher {

	private Admin admin;
	private Teacher teacher;
	private String theFirstName,theLastName;
	private Center previousCenter;
	private Classroom previousClassroom;
		
	public ModifyTeacher(Admin admin,Teacher teacher) { 
		this.admin = admin;
		this.teacher = teacher;
		this.theFirstName = teacher.getFirstName();
		this.theLastName = teacher.getLastName();
		this.previousCenter = teacher.getCenter(teacher.getCenterID());
		this.previousClassroom = teacher.getClassroom(teacher.getClassroomID());
	}
		
	public void display() {
		Stage stage = new Stage();
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.setTitle(teacher.getFirstName() + "\n" + teacher.getLastName());
			
		//GridPane
		GridPane modifyTeacherLayout = new GridPane();
		modifyTeacherLayout.setVgap(10);
		modifyTeacherLayout.setHgap(10);
		ColumnConstraints columnOne = new ColumnConstraints();
		columnOne.setPercentWidth(33.3);
		ColumnConstraints columnTwo = new ColumnConstraints();
		columnTwo.setPercentWidth(33.3);
		ColumnConstraints columnThree = new ColumnConstraints();
		columnThree.setPercentWidth(33.3);
		modifyTeacherLayout.getColumnConstraints().addAll(columnOne,columnTwo,columnThree);
		modifyTeacherLayout.getStyleClass().add("modulargridpane");
		
		//GridPane - Icon
		ImageView teacherViewer = new ImageView();
		Image teacherIcon = new Image("images/teacher.png");
		teacherViewer.setImage(teacherIcon);
		teacherViewer.setPreserveRatio(true);
		teacherViewer.setFitHeight(150);
		 
		//GridPane - Title
		Label title = new Label("Modify\n Teacher");
		title.getStyleClass().add("title");
		 
		//GridPane - First Name
		Label teacherFirstName = new Label("First name:");
	    teacherFirstName.getStyleClass().add("label");
	    TextField firstNameField = new TextField();
	    firstNameField.setPromptText(teacher.getFirstName());
	    firstNameField.setDisable(true);
	    firstNameField.getStyleClass().add("textfield");
	    
		//GridPane - Last Name
	    Label teacherLastName = new Label("Last name:");
	    teacherLastName.getStyleClass().add("label");
	    TextField lastNameField = new TextField();
	    lastNameField.setPromptText(teacher.getLastName());
	    lastNameField.setDisable(true);
	    lastNameField.getStyleClass().add("textfield");
	    
	    //GridPane - Title Change
	  	Label titleChange = new Label("Title (if applicable):");
	  	titleChange.getStyleClass().add("label");
	  	ChoiceBox<String> titleBox = new ChoiceBox<String>();
	  	titleBox.getItems().add("Director");
	  	titleBox.getItems().add("Teacher");
	  	titleBox.setValue(titleBox.getItems().get(1));
	  	titleBox.setMaxWidth(600);
	  	titleBox.setDisable(true);
	  	titleBox.getStyleClass().add("choicebox");
	    
		//GridPane - Center Box
	    Label centerSelection = new Label("Center:");
	    centerSelection.getStyleClass().add("label");
	    ChoiceBox<Center> centerBox = new ChoiceBox<Center>();
	    if(admin.getCenters().isEmpty()) {centerBox.setDisable(true);
	    }else {
	    		Map<String,Center> centers = admin.getCenters();
	    		for(Center activeCenter : centers.values()) {
	    			centerBox.getItems().add(activeCenter);
	    			if(activeCenter == previousCenter) {centerBox.setValue(activeCenter);}
	    		} 
	    }
	    centerBox.setMaxWidth(600);
	    centerBox.setDisable(true);
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
	    			}else if(classroom.getTeacherID().equals(teacher.getTeacherID()) || classroom.getAssistantTeacherID().equals(teacher.getTeacherID())) {
	    				ClassroomHolder newClassroomHolder = new ClassroomHolder(classroom);
	    				classroomBox.getItems().add(newClassroomHolder);
	    				classroomBox.setValue(newClassroomHolder);
	    			}
	    		}
	    }
	    if(previousClassroom == null) {classroomBox.setValue(emptyClassroom);}
	    classroomBox.setMaxWidth(600);
	    classroomBox.setDisable(true);
	    classroomBox.getStyleClass().add("choicebox");
	    
		//GridPane - Active Teacher Warning 
	    Label activeTeacherWarning = new Label("The classroom you have selected already has an active teacher");
	    activeTeacherWarning.getStyleClass().add("label");
	    activeTeacherWarning.setTextFill(Color.RED);
	    modifyTeacherLayout.add(activeTeacherWarning, 0, 10);
	    GridPane.setHalignment(activeTeacherWarning, HPos.CENTER);
	    GridPane.setConstraints(activeTeacherWarning, 0, 10, 3, 1);
	    activeTeacherWarning.setVisible(false);
	    
	    //GridPane - Active Director Warning
	    Label activeDirectorWarning = new Label("The center you have selected already has an active director");
	    activeDirectorWarning.getStyleClass().add("label");
	    activeDirectorWarning.setTextFill(Color.RED);
	    modifyTeacherLayout.add(activeDirectorWarning, 0, 10);
	    GridPane.setHalignment(activeDirectorWarning, HPos.CENTER);
	    GridPane.setConstraints(activeDirectorWarning, 0, 10, 3, 1);
	    activeDirectorWarning.setVisible(false);
		    
		//GridPane - Write Button
		ImageView writeViewer = new ImageView();
		Image write = new Image("images/write.png");
		writeViewer.setImage(write);
		writeViewer.setPreserveRatio(true);
		writeViewer.setFitHeight(150);
		ImageButton writeButton = new ImageButton(writeViewer);
		writeButton.setOnAction(e -> {
	    		String teachersFirstName = firstNameField.getText();
	    		if(firstNameField.getText().trim().isEmpty()) {teachersFirstName = theFirstName;}
	    		String teachersLastName = lastNameField.getText();
	    		if(lastNameField.getText().trim().isEmpty()) {teachersLastName = theLastName;}
	    		Center teachersCenter = centerBox.getValue();
	    		String newTitle = titleBox.getValue();
	    		Classroom teachersClassroom = null;
	    		if(classroomBox.getValue().getClassroom() != null) {
	    			teachersClassroom = classroomBox.getValue().getClassroom();
	    			if(teachersClassroom.getTeacherID() != null && teachersClassroom.getAssistantTeacherID() != null) {
	    				if(activeDirectorWarning.isVisible() == true) { activeDirectorWarning.setVisible(false);}
	    				activeTeacherWarning.setVisible(true);
		    		}else {
		    			Thread modifyExistingTeacher = new Thread(new ModifyExistingTeacher(admin,teacher,teachersFirstName,teachersLastName,newTitle,teachersCenter,teachersClassroom));
		    			modifyExistingTeacher.start();
		    			stage.close();
		    		}
	    		}else{
		    		if(newTitle.equals("Director") && teachersCenter.getDirectorID() != null) {
		    			if(activeTeacherWarning.isVisible() == true) { activeTeacherWarning.setVisible(false);}
		    			activeDirectorWarning.setVisible(true);	
		    		}else {
		    			Thread modifyExistingTeacher = new Thread(new ModifyExistingTeacher(admin,teacher,teachersFirstName,teachersLastName,newTitle,teachersCenter));
		    			modifyExistingTeacher.start();
		    			stage.close();
		    		}
	    		}
		});
		writeButton.setVisible(false);
		Label writeLabel = new Label("write");
		writeLabel.setVisible(false);
		writeLabel.getStyleClass().add("label");
		
		//GridPane - Edit Button
		Label editLabel = new Label("edit");
		editLabel.getStyleClass().add("label");
		ImageView editViewer = new ImageView();
		Image edit = new Image("images/edit.png");
		editViewer.setImage(edit);
		editViewer.setPreserveRatio(true);
		editViewer.setFitHeight(150);
		ImageButton editButton = new ImageButton(editViewer);
		editButton.setOnAction(e -> {
		    	firstNameField.setDisable(false);
		    	lastNameField.setDisable(false);
		    	titleBox.setDisable(false);
		    	centerBox.setDisable(false);
		    	classroomBox.setDisable(false);
		    	writeButton.setVisible(true);
		    	writeLabel.setVisible(true);
		    	editButton.setVisible(false);
		    	editLabel.setVisible(false);
		});
		 
		//GridPane - Delete Button
		ImageView trashViewer = new ImageView();
		Image trash = new Image("images/trash.png");
		trashViewer.setImage(trash);
		trashViewer.setPreserveRatio(true);
		trashViewer.setFitHeight(150);
		ImageButton trashButton = new ImageButton(trashViewer);
		trashButton.setOnAction(e -> {
			admin.deleteTeacher(teacher);
			stage.close();
		});
		Label trashLabel = new Label("delete");
		trashLabel.getStyleClass().add("label");
		
		//GridPane - Cancel Button
		Button cancel = new Button("cancel");
		cancel.setOnAction(e -> stage.close());
		cancel.getStyleClass().add("button");
		
		//GridPane - Layout
		modifyTeacherLayout.add(teacherViewer,0,0);
		GridPane.setHalignment(teacherViewer,HPos.CENTER);
		modifyTeacherLayout.add(title,1,0);
		GridPane.setConstraints(title,1,0,2,1);
		GridPane.setHalignment(title,HPos.CENTER);
		modifyTeacherLayout.add(teacherFirstName,0,1);
		GridPane.setHalignment(teacherFirstName,HPos.RIGHT);
		modifyTeacherLayout.add(firstNameField,1,1);
		GridPane.setConstraints(firstNameField,1,1,2,1);
		modifyTeacherLayout.add(teacherLastName,0,2);
		GridPane.setHalignment(teacherLastName,HPos.RIGHT);
		modifyTeacherLayout.add(lastNameField,1,2);
		GridPane.setConstraints(lastNameField,1,2,2,1);
		modifyTeacherLayout.add(titleChange,0,3);
		GridPane.setHalignment(titleChange,HPos.RIGHT);
		modifyTeacherLayout.add(titleBox,1,3);
		GridPane.setConstraints(titleBox,1,3,2,1);
		modifyTeacherLayout.add(centerSelection,0,4);
		GridPane.setHalignment(centerSelection,HPos.RIGHT);
		modifyTeacherLayout.add(centerBox,1,4);
		GridPane.setConstraints(centerBox,1,4,2,1);
		modifyTeacherLayout.add(classroomSelection,0,5);
		GridPane.setHalignment(classroomSelection,HPos.RIGHT);
		modifyTeacherLayout.add(classroomBox,1,5);
		GridPane.setConstraints(classroomBox,1,5,2,1);
		modifyTeacherLayout.add(editButton,1,6);
		GridPane.setHalignment(editButton,HPos.CENTER);
		modifyTeacherLayout.add(writeButton,1,6);
		GridPane.setHalignment(writeButton,HPos.CENTER);
		modifyTeacherLayout.add(trashButton,2,6);
		GridPane.setHalignment(trashButton,HPos.CENTER);
		modifyTeacherLayout.add(editLabel,1,7);
		GridPane.setHalignment(editLabel,HPos.CENTER);
		modifyTeacherLayout.add(writeLabel,1,7);
		GridPane.setHalignment(writeLabel,HPos.CENTER);
		modifyTeacherLayout.add(trashLabel,2,7);
		GridPane.setHalignment(trashLabel,HPos.CENTER);
		modifyTeacherLayout.add(cancel,1,9);
		GridPane.setHalignment(cancel,HPos.CENTER);
		GridPane.setConstraints(cancel,1,9,2,1);
		modifyTeacherLayout.getStylesheets().add("css/admin.css");
			
		Scene modifyTeacherScene = new Scene(modifyTeacherLayout);
		stage.setScene(modifyTeacherScene);
		stage.showAndWait();
	}
	
	private class ClassroomHolder{
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
