package com.richmond.darkhorse.ProjectSB.gui.component;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.richmond.darkhorse.ProjectSB.Admin;
import com.richmond.darkhorse.ProjectSB.Center;
import com.richmond.darkhorse.ProjectSB.Classroom;
import com.richmond.darkhorse.ProjectSB.StaffMember;
import com.richmond.darkhorse.ProjectSB.Teacher;
import com.richmond.darkhorse.ProjectSB.middleman.ModifyExistingClassroom;
import javafx.geometry.HPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ModifyClassroom {

	private Classroom theClassroom;
	private String previousAgeGroup;
	private Center center;
	private Teacher teacher,assistantTeacher;
	private int maxCapacity;
	private List<Integer> roomSizes = Arrays.asList(7,9,12,14,18);
	private Map<String,String> classroomTypes;
	private Admin admin;
	
	public ModifyClassroom(Classroom theClassroom,Admin admin) {
		this.classroomTypes = new HashMap<String,String>();
		this.classroomTypes = populateClassroomTypes();
		this.theClassroom = theClassroom;
		this.center = theClassroom.getCenter(theClassroom.getCenterID());
		this.teacher = theClassroom.getTeacher(theClassroom.getTeacherID());
		this.assistantTeacher = theClassroom.getAssistantTeacher(theClassroom.getAssistantTeacherID());
		this.maxCapacity = theClassroom.getMaxSize();
		this.previousAgeGroup = theClassroom.getAgeGroup();
		this.admin = admin;
	}
	
	public void display() {
		Stage stage = new Stage();
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.setTitle(theClassroom.getCenter(theClassroom.getCenterID()) + ": " + theClassroom.getClassroomType());
		
		//Grid Pane
		GridPane modifyClassroomLayout = new GridPane();
		modifyClassroomLayout.setVgap(10);
		modifyClassroomLayout.setHgap(10);
		ColumnConstraints columnOne = new ColumnConstraints();
		columnOne.setPercentWidth(33.3);
	    ColumnConstraints columnTwo = new ColumnConstraints();
	    columnTwo.setPercentWidth(33.3);
	    ColumnConstraints columnThree = new ColumnConstraints();
	    columnThree.setPercentWidth(33.3);
	    modifyClassroomLayout.getColumnConstraints().addAll(columnOne,columnTwo,columnThree);
	    modifyClassroomLayout.getStyleClass().add("gridpane");
		
		//Grid Pane - Icon
	    ImageView classroomViewer = new ImageView();
	    Image classroom = new Image("classroom.png");
	    classroomViewer.setImage(classroom);
	    classroomViewer.setPreserveRatio(true);
	    classroomViewer.setFitHeight(150);
	    
		//Grid Pane - Title
	    Label title = new Label("Modify Classroom");
	    title.getStyleClass().add("title");
	    
	  //Grid Pane - Classroom Type Box
	    Label classType = new Label("Classroom type:");
	    classType.getStyleClass().add("label");
	    ChoiceBox<String> typeBox = new ChoiceBox<String>();
	    Map<String,String> classroomTypes = this.getClassroomTypes();
	    	for(String ageGroup : classroomTypes.values()) {
	    		if(ageGroup.equals(previousAgeGroup)) {
	    			typeBox.getItems().add(ageGroup);
	    			typeBox.setValue(ageGroup);
	    		}
	    	}
	    typeBox.setMaxWidth(600);
	    typeBox.setDisable(true);
	    typeBox.getStyleClass().add("choicebox");
	    
		//Grid Pane - Center Box
	    Label centerName = new Label("Center:");
	    centerName.getStyleClass().add("label");
	    ChoiceBox<Center> centerBox = new ChoiceBox<Center>();
	    if(admin.getCenters().isEmpty()) {centerBox.setDisable(true);}else {
	    		Map<String,Center> centers = admin.getCenters();
	    		for(Center activeCenter : centers.values()) {
	    			centerBox.getItems().add(activeCenter);
	    			if(activeCenter == center) {centerBox.setValue(activeCenter);}
	    		}
	    		centerBox.setDisable(true);
	    }
	    centerBox.setMaxWidth(600);
	    centerBox.getStyleClass().add("choicebox");
	    
		//Grid Pane - Lead Teacher Box
	    Label teacherName = new Label("Lead Teacher:");
	    teacherName.getStyleClass().add("label");
	    ChoiceBox<TeacherHolder> teacherBox = new ChoiceBox<TeacherHolder>();
	    TeacherHolder emptyTeacherHolder = new TeacherHolder(null);
	    teacherBox.getItems().add(emptyTeacherHolder);
	    List<Teacher> availableTeachers = populateTeachers();
	    for(Teacher theTeacher : availableTeachers) {
	    		TeacherHolder teacherHolder = new TeacherHolder(theTeacher);
	    		if(theTeacher.equals(teacher)) {
	    			teacherBox.getItems().add(teacherHolder);
	    			teacherBox.setValue(teacherHolder);
	    		}else {
	    			teacherBox.getItems().add(teacherHolder);
	    		}
	    }
	    if(teacher == null) {teacherBox.setValue(emptyTeacherHolder);}
	    teacherBox.setMaxWidth(600);
	    teacherBox.setDisable(true);
	    teacherBox.getStyleClass().add("choicebox");
	    
		//Grid Pane - Assistant Teacher Box
	    Label assistantName = new Label("Assistant Teacher (optional):");
	    assistantName.getStyleClass().add("label");
	    ChoiceBox<TeacherHolder> assistantTeacherBox = new ChoiceBox<TeacherHolder>();
	    TeacherHolder emptyAssistantTeacherHolder = new TeacherHolder(null);
	    assistantTeacherBox.getItems().add(emptyAssistantTeacherHolder);
	    List<Teacher> availableAssistantTeachers = populateTeachers();
	    for(Teacher theAssistantTeacher : availableAssistantTeachers) {
	    		TeacherHolder assistantTeacherHolder = new TeacherHolder(theAssistantTeacher);
	    		if(theAssistantTeacher.equals(assistantTeacher)) {
	    			assistantTeacherBox.getItems().add(assistantTeacherHolder);
	    			assistantTeacherBox.setValue(assistantTeacherHolder);
	    		}else {
	    			assistantTeacherBox.getItems().add(assistantTeacherHolder);
	    		}
	    }
	    if(assistantTeacher == null) {assistantTeacherBox.setValue(emptyAssistantTeacherHolder);}
	    assistantTeacherBox.setMaxWidth(600);
	    assistantTeacherBox.setDisable(true);
	    assistantTeacherBox.getStyleClass().add("choicebox");
	    
		//Grid Pane - Capacity Box
	    Label capacity = new Label("Max capacity:");
	    capacity.getStyleClass().add("label");
	    ChoiceBox<Integer> capacityBox = new ChoiceBox<Integer>();
	    for(int theMaxCapacity : roomSizes) {
	    		capacityBox.getItems().add(theMaxCapacity);
	    		if(theMaxCapacity == maxCapacity) {
	    			capacityBox.setValue(theMaxCapacity);
	    		}
	    }
	    capacityBox.setDisable(true);
	    capacityBox.setMaxWidth(600);
	    capacityBox.getStyleClass().add("choicebox");

	    //Grid Pane - Duplicate Teacher Warning
	    Label duplicateTeacherWarning = new Label("Cannot assign teacher and assistant teacher to the same person");
	    duplicateTeacherWarning.getStyleClass().add("label");
	    duplicateTeacherWarning.setTextFill(Color.RED);
	    modifyClassroomLayout.add(duplicateTeacherWarning, 0, 9);
	    GridPane.setConstraints(duplicateTeacherWarning,0,9,3,1);
	    GridPane.setHalignment(duplicateTeacherWarning, HPos.CENTER);
	    duplicateTeacherWarning.setVisible(false);
	    
	    //Grid Pane - Teacher Already Active Warning
	    Label teacherAlreadyActiveWarning = new Label("Teacher is already active in another classroom");
	    teacherAlreadyActiveWarning.getStyleClass().add("label");
	    teacherAlreadyActiveWarning.setTextFill(Color.RED);
	    modifyClassroomLayout.add(teacherAlreadyActiveWarning, 0, 9);
	    GridPane.setConstraints(teacherAlreadyActiveWarning,0,9,3,1);
	    GridPane.setHalignment(teacherAlreadyActiveWarning, HPos.CENTER);
	    teacherAlreadyActiveWarning.setVisible(false);
	    
		//Grid Pane - Write Button
	    ImageView writeViewer = new ImageView();
	    Image write = new Image("write.png");
	    writeViewer.setImage(write);
	    writeViewer.setPreserveRatio(true);
	    writeViewer.setFitHeight(150);
	    ImageButton writeButton = new ImageButton(writeViewer);
	    writeButton.setOnAction(e -> {
	    		Teacher newLeadTeacher = teacherBox.getValue().getTeacher();
	    		if(teacherBox.getValue() == null) {newLeadTeacher.setTeacherID(null);}
	    		Teacher newAssistantTeacher = assistantTeacherBox.getValue().getTeacher();
	    		if(assistantTeacherBox.getValue() == null) {newAssistantTeacher.setTeacherID(null);}
	    		int newMaxSize = capacityBox.getValue();
	    		if(newLeadTeacher != null && newAssistantTeacher != null && newLeadTeacher.equals(newAssistantTeacher)) {
	    			if(teacherAlreadyActiveWarning.isVisible()) {teacherAlreadyActiveWarning.setVisible(false);}
		    		duplicateTeacherWarning.setVisible(true);
		    	}else if(newLeadTeacher != null && newLeadTeacher.getClassroomID() != null){
		    		if(duplicateTeacherWarning.isVisible()) {duplicateTeacherWarning.setVisible(false);}
		    		teacherAlreadyActiveWarning.setVisible(true);
	    		}else if(newAssistantTeacher != null && newAssistantTeacher.getClassroomID() != null) {
		    		if(duplicateTeacherWarning.isVisible()) {duplicateTeacherWarning.setVisible(false);}
		    		teacherAlreadyActiveWarning.setVisible(true);
		    	}else {
		    		Thread modifyExistingClassroom = new Thread(new ModifyExistingClassroom(theClassroom,newLeadTeacher,newAssistantTeacher,newMaxSize,admin));
    	    			modifyExistingClassroom.start();	
    	    			stage.close();
		    	}
	    });
	    writeButton.setVisible(false);
	    Label writeLabel = new Label("write");
	    writeLabel.setVisible(false);
	    writeLabel.getStyleClass().add("label");
	    
		//Grid Pane - Edit Button
	    Label editLabel = new Label("edit");
	    editLabel.getStyleClass().add("label");
	    ImageView editViewer = new ImageView();
	    Image edit = new Image("edit.png");
	    editViewer.setImage(edit);
	    editViewer.setPreserveRatio(true);
	    editViewer.setFitHeight(150);
	    ImageButton editButton = new ImageButton(editViewer);
	    editButton.setOnAction(e -> {
	    		teacherBox.setDisable(false);
	    		assistantTeacherBox.setDisable(false);
	    		capacityBox.setDisable(false);
	    		writeButton.setVisible(true);
	    		writeLabel.setVisible(true);
	    		editButton.setVisible(false);
	    		editLabel.setVisible(false);
	    });
	    
		//Grid Pane - Delete Button
	    ImageView trashViewer = new ImageView();
	    Image trash = new Image("trash.png");
	    trashViewer.setImage(trash);
	    trashViewer.setPreserveRatio(true);
	    trashViewer.setFitHeight(150);
	    ImageButton trashButton = new ImageButton(trashViewer);
	    trashButton.setOnAction(e -> {
	    		admin.deleteClassroom(theClassroom);
	    		stage.close();
	    });
	    Label trashLabel = new Label("delete");
	    trashLabel.getStyleClass().add("label");
	    
		//Grid Pane - Cancel Button
	    Button cancel = new Button("cancel");
	    cancel.setOnAction(e -> stage.close());
	    cancel.getStyleClass().add("button");
	    
		//Grid Pane - Layout
	    modifyClassroomLayout.add(classroomViewer,0,0);
	    GridPane.setHalignment(classroomViewer,HPos.CENTER);
	    modifyClassroomLayout.add(title,1,0);
	    GridPane.setConstraints(title,1,0,2,1);
	    GridPane.setHalignment(title,HPos.CENTER);
	    modifyClassroomLayout.add(classType,0,1);
	    GridPane.setHalignment(classType,HPos.RIGHT);
	    modifyClassroomLayout.add(typeBox,1,1);
	    GridPane.setConstraints(typeBox,1,1,2,1);
	    modifyClassroomLayout.add(centerName,0,2);
	    GridPane.setHalignment(centerName,HPos.RIGHT);
	    modifyClassroomLayout.add(centerBox,1,2);
	    GridPane.setConstraints(centerBox,1,2,2,1);
	    modifyClassroomLayout.add(teacherName,0,3);
	    GridPane.setHalignment(teacherName,HPos.RIGHT);
	    modifyClassroomLayout.add(teacherBox,1,3);
	    GridPane.setConstraints(teacherBox,1,3,2,1);
	    modifyClassroomLayout.add(assistantName,0,4);
	    GridPane.setHalignment(assistantName,HPos.RIGHT);
	    modifyClassroomLayout.add(assistantTeacherBox,1,4);
	    GridPane.setConstraints(assistantTeacherBox,1,4,2,1);
	    modifyClassroomLayout.add(capacity,0,5);
	    GridPane.setHalignment(capacity,HPos.RIGHT);
	    modifyClassroomLayout.add(capacityBox,1,5);
	    GridPane.setConstraints(capacityBox,1,5,2,1);
	    modifyClassroomLayout.add(editButton,1,6);
	    GridPane.setHalignment(editButton,HPos.CENTER);
	    modifyClassroomLayout.add(writeButton,1,6);
	    GridPane.setHalignment(writeButton,HPos.CENTER);
	    modifyClassroomLayout.add(trashButton,2,6);
	    GridPane.setHalignment(trashButton,HPos.CENTER);
	    modifyClassroomLayout.add(editLabel,1,7);
	    GridPane.setHalignment(editLabel,HPos.CENTER);
	    modifyClassroomLayout.add(writeLabel,1,7);
	    GridPane.setHalignment(writeLabel,HPos.CENTER);
	    modifyClassroomLayout.add(trashLabel,2,7);
	    GridPane.setHalignment(trashLabel,HPos.CENTER);
	    modifyClassroomLayout.add(cancel,1,8);
	    GridPane.setHalignment(cancel,HPos.CENTER);
	    GridPane.setConstraints(cancel,1,8,2,1);
	    modifyClassroomLayout.getStylesheets().add("modifycenter.css");
		
		Scene modifyClassroomScene = new Scene(modifyClassroomLayout);
		stage.setScene(modifyClassroomScene);
		stage.showAndWait();
	}
	
	public List<Teacher> populateTeachers(){
		Map<String,StaffMember> staffMembers = admin.getStaffMembers();
		List<Teacher> teachers = new ArrayList<Teacher>();
		for(StaffMember staffMember: staffMembers.values()) {
			String title = staffMember.getTitle();
			if(title.equals("Teacher")) {teachers.add((Teacher) staffMember);}
		}
		return teachers;
	}
	
	public class TeacherHolder{
		private Teacher teacher;
		public TeacherHolder(Teacher teacher) {this.teacher = teacher;}
		public Teacher getTeacher() {return teacher;}
		@Override
		public String toString() {
			if(teacher == null) {return "N/A";
			}else {
				Center center = teacher.getCenter(teacher.getCenterID());
				return center.getAbbreviatedName() +  ": " + teacher.getFirstName() + " " + teacher.getLastName();
			}
		}
	}
	
	public Map<String,String> populateClassroomTypes() {
		classroomTypes.put("PreK2", "4+ years");
		classroomTypes.put("PreK1", "3-4 years");
		classroomTypes.put("Pre3", "2-3 years");
		return classroomTypes;
	}

	public Map<String, String> getClassroomTypes() {
		return classroomTypes;
	}
	
}
