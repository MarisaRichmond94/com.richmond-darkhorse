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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.richmond.darkhorse.ProjectSB.Admin;
import com.richmond.darkhorse.ProjectSB.Center;
import com.richmond.darkhorse.ProjectSB.StaffMember;
import com.richmond.darkhorse.ProjectSB.Teacher;
import com.richmond.darkhorse.ProjectSB.middleman.CreateNewClassroom;
import javafx.geometry.HPos;

public class AddClassroom {
	
	private Admin admin;
	private List<Integer> roomSizes = Arrays.asList(7,9,12,14,18);;
	private Map<String,String> classroomTypes;
	
	public AddClassroom(Admin admin) { 
		this.admin = admin;
		this.classroomTypes = new HashMap<String,String>();
		this.classroomTypes = populateClassroomTypes();
	}
	
	public void display() {
		Stage stage = new Stage();
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.setTitle("Create New \nClassroom");
		
		//Grid Pane
		GridPane addClassroomLayout = new GridPane();
		addClassroomLayout.setVgap(10);
		addClassroomLayout.setHgap(10);
		ColumnConstraints columnOne = new ColumnConstraints();
		columnOne.setPercentWidth(33.3);
	    ColumnConstraints columnTwo = new ColumnConstraints();
	    columnTwo.setPercentWidth(33.3);
	    ColumnConstraints columnThree = new ColumnConstraints();
	    columnThree.setPercentWidth(33.3);
	    addClassroomLayout.getColumnConstraints().addAll(columnOne,columnTwo,columnThree);
	    addClassroomLayout.getStyleClass().add("modulargridpane");
		
		//Grid Pane - Icon
	    ImageView classroomViewer = new ImageView();
	    Image classroomIcon = new Image("images/classroom.png");
	    classroomViewer.setImage(classroomIcon);
	    classroomViewer.setPreserveRatio(true);
	    classroomViewer.setFitHeight(200);
	    
		//Grid Pane - Title
	    Label title = new Label("Create New \nClassroom");
	    title.getStyleClass().add("title");
	    
		//Grid Pane - Classroom Type Box
	    Label classType = new Label("Classroom type:");
	    classType.getStyleClass().add("label");
	    ChoiceBox<String> typeBox = new ChoiceBox<String>();
	    Map<String,String> classroomTypes = this.getClassroomTypes();
	    	String ageGroup1 = classroomTypes.get("PreK2");
	    	typeBox.getItems().add(ageGroup1);
	    	String ageGroup2 = classroomTypes.get("PreK1");
	    	typeBox.getItems().add(ageGroup2);
	    	String ageGroup3 = classroomTypes.get("Pre3");
	    	typeBox.getItems().add(ageGroup3);
	    typeBox.setValue(typeBox.getItems().get(0));
	    typeBox.setMaxHeight(500);
	    typeBox.getStyleClass().add("choicebox");
	    
		//Grid Pane - Center Box
	    Label centerName = new Label("Center:");
	    centerName.getStyleClass().add("label");
	    ChoiceBox<Center> centerBox = new ChoiceBox<Center>();
	    if(admin.getCenters().isEmpty()) {
	    		centerBox.setDisable(true);
	    }else {
	    		Map<String,Center> centers = admin.getCenters();
	    		for(Center activeCenter : centers.values()) {
	    			centerBox.getItems().add(activeCenter);
	    		}
	    		centerBox.setValue(centerBox.getItems().get(0));
	    }
	    centerBox.setMaxWidth(600);
	    centerBox.getStyleClass().add("choicebox");
	    
		//Grid Pane - Lead Teacher Box
	    Label teacherName = new Label("Lead Teacher:");
	    teacherName.getStyleClass().add("label");
	    ChoiceBox<TeacherHolder> teacherBox = new ChoiceBox<TeacherHolder>();
	    TeacherHolder emptyTeacherHolder = new TeacherHolder(null);
	    teacherBox.getItems().add(emptyTeacherHolder);
	    List<Teacher> availableTeachers = populateTeachers(centerBox.getValue());
	    for(Teacher teacher : availableTeachers) {
	    		TeacherHolder teacherHolder = new TeacherHolder(teacher);
	    		teacherBox.getItems().add(teacherHolder);
	    }
	    teacherBox.setValue(emptyTeacherHolder);
	    teacherBox.setMaxWidth(600);
	    teacherBox.getStyleClass().add("choicebox");
	    
		//Grid Pane - Assistant Teacher Box
	    Label assistantName = new Label("Assistant Teacher (optional):");
	    assistantName.getStyleClass().add("label");
	    ChoiceBox<TeacherHolder> assistantTeacherBox = new ChoiceBox<TeacherHolder>();
	    TeacherHolder emptyAssistantTeacherHolder = new TeacherHolder(null);
	    assistantTeacherBox.getItems().add(emptyAssistantTeacherHolder);
	    List<Teacher> availableAssistantTeachers = populateTeachers(centerBox.getValue());
	    for(Teacher assistantTeacher : availableAssistantTeachers) {
	    		TeacherHolder teacherHolder = new TeacherHolder(assistantTeacher);
	    		if(teacherBox.getValue() != teacherHolder) {
	    			assistantTeacherBox.getItems().add(teacherHolder);	
	    		}
	    }
	    assistantTeacherBox.setValue(emptyAssistantTeacherHolder);
	    assistantTeacherBox.setMaxWidth(600);
	    assistantTeacherBox.getStyleClass().add("choicebox");
	    
		//Grid Pane - Capacity Box
	    Label capacity = new Label("Max capacity:");
	    capacity.getStyleClass().add("label");
	    ChoiceBox<Integer> capacityBox = new ChoiceBox<Integer>();
	    for(int maxCapacity : roomSizes) {
	    		capacityBox.getItems().add(maxCapacity);
	    }
	    capacityBox.setValue(capacityBox.getItems().get(0));
	    capacityBox.getStyleClass().add("choicebox");
	    
	    //Grid Pane - Duplicate Teacher Warning
	    Label duplicateTeacherWarning = new Label("Cannot assign lead teacher and assistant teacher to the same person");
	    duplicateTeacherWarning.getStyleClass().add("label");
	    duplicateTeacherWarning.setTextFill(Color.RED);
	    addClassroomLayout.add(duplicateTeacherWarning, 0, 7);
	    GridPane.setConstraints(duplicateTeacherWarning,0,7,3,1);
	    GridPane.setHalignment(duplicateTeacherWarning, HPos.CENTER);
	    duplicateTeacherWarning.setVisible(false);
	    
	    //Grid Pane - Teacher Already Active Warning
	    Label teacherAlreadyActiveWarning = new Label("Teacher is already active in another classroom");
	    teacherAlreadyActiveWarning.getStyleClass().add("label");
	    teacherAlreadyActiveWarning.setTextFill(Color.RED);
	    addClassroomLayout.add(teacherAlreadyActiveWarning, 0, 7);
	    GridPane.setConstraints(teacherAlreadyActiveWarning,0,7,3,1);
	    GridPane.setHalignment(teacherAlreadyActiveWarning, HPos.CENTER);
	    teacherAlreadyActiveWarning.setVisible(false);
	    
		//Grid Pane - Create Button
	    Button createClassroomButton = new Button("Create Classroom");
	    createClassroomButton.getStyleClass().add("button");
	    createClassroomButton.setOnAction(e -> {
		    	String ageGroup = typeBox.getValue();
		    	String classroomType = null;
		    	if(ageGroup.equals("4+ years")) {classroomType = "PreK2";	}
		    	else if(ageGroup.equals("3-4 years")) {classroomType = "PreK1";}
		    	else {classroomType = "Pre3";}
		    	Center selectedCenter = centerBox.getValue();
		    	Teacher selectedTeacher = teacherBox.getValue().getTeacher();
		    	Teacher selectedAssistantTeacher = assistantTeacherBox.getValue().getTeacher();
		    	if(teacherBox.getValue() == null) {selectedTeacher.setTeacherID(null);}
		    	if(assistantTeacherBox.getValue() == null) {selectedAssistantTeacher.setTeacherID(null);}
		    	int maxCapacity = capacityBox.getValue();
		    	if(selectedTeacher != null && selectedTeacher.equals(selectedAssistantTeacher)) {
		    		if(teacherAlreadyActiveWarning.isVisible()) {teacherAlreadyActiveWarning.setVisible(false);}
		    		duplicateTeacherWarning.setVisible(true);
		    	}else if(selectedTeacher != null && selectedTeacher.getClassroomID() != null) {
		    		if(duplicateTeacherWarning.isVisible()) {duplicateTeacherWarning.setVisible(false);}
		    		teacherAlreadyActiveWarning.setVisible(true);
		    	}else if(selectedAssistantTeacher != null && selectedAssistantTeacher.getClassroomID() != null){
		    		if(duplicateTeacherWarning.isVisible()) {duplicateTeacherWarning.setVisible(false);}
		    		teacherAlreadyActiveWarning.setVisible(true);
		    	}else {
			    	Thread createNewClassroom = new Thread(new CreateNewClassroom(classroomType,selectedCenter,selectedTeacher,selectedAssistantTeacher,maxCapacity,ageGroup,admin));
			    	createNewClassroom.start();	
			    	stage.close();
		    	}
	    });
	    
		//Grid Pane - Cancel Button
	    Button cancelButton = new Button("Cancel");
	    cancelButton.getStyleClass().add("button");
	    cancelButton.setOnAction(e -> stage.close());
	    
		//Grid Pane - Layout
	    addClassroomLayout.add(classroomViewer, 0, 0);
	    GridPane.setHalignment(classroomViewer, HPos.CENTER);
	    addClassroomLayout.add(title, 1, 0);
	    GridPane.setConstraints(title,1,0,2,1);
	    GridPane.setHalignment(title, HPos.CENTER);
	    addClassroomLayout.add(classType, 0, 1);
	    GridPane.setHalignment(classType, HPos.RIGHT);
	    addClassroomLayout.add(typeBox, 1, 1);
	    GridPane.setConstraints(typeBox,1,1,2,1);
	    addClassroomLayout.add(centerName, 0, 2);
	    GridPane.setHalignment(centerName, HPos.RIGHT);
	    addClassroomLayout.add(centerBox, 1, 2);
	    GridPane.setConstraints(centerBox,1,2,2,1);
	    addClassroomLayout.add(teacherName, 0, 3);
	    GridPane.setHalignment(teacherName, HPos.RIGHT);
	    addClassroomLayout.add(teacherBox, 1, 3);
	    GridPane.setConstraints(teacherBox,1,3,2,1);
	    addClassroomLayout.add(assistantName, 0, 4);
	    GridPane.setHalignment(assistantName, HPos.RIGHT);
	    addClassroomLayout.add(assistantTeacherBox, 1, 4);
	    GridPane.setConstraints(assistantTeacherBox,1,4,2,1);
	    addClassroomLayout.add(capacity, 0, 5);
	    GridPane.setHalignment(capacity, HPos.RIGHT);
	    addClassroomLayout.add(capacityBox, 1, 5);
	    GridPane.setConstraints(capacityBox,1,5,2,1);
	    addClassroomLayout.add(createClassroomButton, 1, 6);
	    GridPane.setHalignment(createClassroomButton, HPos.CENTER);
	    addClassroomLayout.add(cancelButton, 2, 6);
	    GridPane.setHalignment(cancelButton, HPos.LEFT);
	    addClassroomLayout.getStylesheets().add("css/admin.css");
		
		Scene createClassroomScene = new Scene(addClassroomLayout);
		stage.setScene(createClassroomScene);
		stage.showAndWait();
	}
	
	public List<Teacher> populateTeachers(Center center){
		Map<String,StaffMember> staffMembers = admin.getStaffMembers();
		List<Teacher> availableTeacher = new ArrayList<Teacher>();
		for(StaffMember staffMember: staffMembers.values()) {
			String title = staffMember.getTitle();
			if(title.equals("Teacher")) {
				Teacher correctTeacher = (Teacher) staffMember;
				availableTeacher.add(correctTeacher);
			}
		}
		return availableTeacher;
	}
	
	private class TeacherHolder{
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
