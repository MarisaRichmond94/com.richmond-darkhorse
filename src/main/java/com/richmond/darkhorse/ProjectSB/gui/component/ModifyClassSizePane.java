package com.richmond.darkhorse.ProjectSB.gui.component;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.richmond.darkhorse.ProjectSB.Center;
import com.richmond.darkhorse.ProjectSB.Classroom;
import com.richmond.darkhorse.ProjectSB.Director;
import com.richmond.darkhorse.ProjectSB.StaffMember;
import com.richmond.darkhorse.ProjectSB.Teacher;
import com.richmond.darkhorse.ProjectSB.middleman.ModifyExistingClassSize;
import javafx.scene.paint.Color;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;

public class ModifyClassSizePane extends GridPane {

	private String previousAgeGroup;
	private Center center;
	private Teacher teacher,assistantTeacher;
	private int maxCapacity;
	private List<Integer> roomSizes = Arrays.asList(7,9,12,14,18);
	private Map<String,String> classroomTypes;
	private Director director;
	
	public ModifyClassSizePane(Classroom classroom,Director director) {
		this.classroomTypes = new HashMap<String,String>();
		this.classroomTypes = populateClassroomTypes();
		this.center = classroom.getCenter(classroom.getCenterID());
		this.teacher = classroom.getTeacher(classroom.getTeacherID());
		this.assistantTeacher = classroom.getAssistantTeacher(classroom.getAssistantTeacherID());
		this.maxCapacity = classroom.getMaxSize();
		this.previousAgeGroup = classroom.getAgeGroup();	
		this.director = director;
		
		//Grid Pane
		this.setVgap(10);
		this.setHgap(10);
		GridPane.setHalignment(this,HPos.CENTER);
		GridPane.setValignment(this,VPos.CENTER);
		ColumnConstraints columnOne = new ColumnConstraints();
		columnOne.setPercentWidth(33.3);
	    ColumnConstraints columnTwo = new ColumnConstraints();
	    columnTwo.setPercentWidth(33.3);
	    ColumnConstraints columnThree = new ColumnConstraints();
	    columnThree.setPercentWidth(33.3);
	    this.getColumnConstraints().addAll(columnOne,columnTwo,columnThree);
	    this.getStyleClass().add("gridpane");
	    
	    //Grid Pane - Classroom Type Box
	    Label classType = new Label("Classroom type:");
	    classType.getStyleClass().add("centrallabel");
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
	    centerName.getStyleClass().add("centrallabel");
	    ChoiceBox<Center> centerBox = new ChoiceBox<Center>();
	    if(director.getCenters().isEmpty()) {centerBox.setDisable(true);}else {
	    		Map<String,Center> centers = director.getCenters();
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
	    teacherName.getStyleClass().add("centrallabel");
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
	    Label assistantName = new Label("Assistant Teacher:");
	    assistantName.getStyleClass().add("centrallabel");
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
	    capacity.getStyleClass().add("centrallabel");
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
	    
		//Grid Pane - Write Button
	    ImageView writeViewer = new ImageView();
	    Image write = new Image("write.png");
	    writeViewer.setImage(write);
	    writeViewer.setPreserveRatio(true);
	    writeViewer.setFitHeight(150);
	    ImageButton writeButton = new ImageButton(writeViewer);
	    writeButton.setOnAction(e -> {
	    		int newMaxSize = capacityBox.getValue();
		    	Thread modifyExistingClassSize = new Thread(new ModifyExistingClassSize(classroom,newMaxSize,director));
    	    		modifyExistingClassSize.start();	
    	    		Label saveSuccessful = new Label("save successful!");
    	    		saveSuccessful.getStyleClass().add("centrallabel");
    	    		saveSuccessful.setTextFill(Color.RED);
    	    		this.add(saveSuccessful,1,7);
    	    		GridPane.setHalignment(saveSuccessful,HPos.CENTER);
	    });
	    writeButton.setVisible(false);
	    Label writeLabel = new Label("write");
	    writeLabel.setVisible(false);
	    writeLabel.getStyleClass().add("centrallabel");
	    
		//Grid Pane - Edit Button
	    Label editLabel = new Label("edit");
	    editLabel.getStyleClass().add("centrallabel");
	    ImageView editViewer = new ImageView();
	    Image edit = new Image("edit.png");
	    editViewer.setImage(edit);
	    editViewer.setPreserveRatio(true);
	    editViewer.setFitHeight(150);
	    ImageButton editButton = new ImageButton(editViewer);
	    editButton.setOnAction(e -> {
	    		capacityBox.setDisable(false);
	    		writeButton.setVisible(true);
	    		writeLabel.setVisible(true);
	    		editButton.setVisible(false);
	    		editLabel.setVisible(false);
	    });
	    
		//Grid Pane - Layout
	    this.add(classType,0,0);
	    GridPane.setHalignment(classType,HPos.RIGHT);
	    this.add(typeBox,1,0);
	    GridPane.setConstraints(typeBox,1,0,2,1);
	    this.add(centerName,0,1);
	    GridPane.setHalignment(centerName,HPos.RIGHT);
	    this.add(centerBox,1,1);
	    GridPane.setConstraints(centerBox,1,1,2,1);
	    this.add(teacherName,0,2);
	    GridPane.setHalignment(teacherName,HPos.RIGHT);
	    this.add(teacherBox,1,2);
	    GridPane.setConstraints(teacherBox,1,2,2,1);
	    this.add(assistantName,0,3);
	    GridPane.setHalignment(assistantName,HPos.RIGHT);
	    this.add(assistantTeacherBox,1,3);
	    GridPane.setConstraints(assistantTeacherBox,1,3,2,1);
	    this.add(capacity,0,4);
	    GridPane.setHalignment(capacity,HPos.RIGHT);
	    this.add(capacityBox,1,4);
	    GridPane.setConstraints(capacityBox,1,4,2,1);
	    this.add(editButton,1,5);
	    GridPane.setHalignment(editButton,HPos.CENTER);
	    this.add(writeButton,1,5);
	    GridPane.setHalignment(writeButton,HPos.CENTER);
	    this.add(editLabel,1,6);
	    GridPane.setHalignment(editLabel,HPos.CENTER);
	    this.add(writeLabel,1,6);
	    GridPane.setHalignment(writeLabel,HPos.CENTER);
	    this.getStylesheets().add("modifyclassroomsetup.css");
	    
	}
	
	public List<Teacher> populateTeachers(){
		Map<String,StaffMember> staffMembers = director.getStaffMembers();
		List<Teacher> teachers = new ArrayList<Teacher>();
		for(StaffMember staffMember: staffMembers.values()) {
			String title = staffMember.getTitle();
			if(title.equals("Teacher")) {teachers.add((Teacher) staffMember);}
		}
		return teachers;
	}
	
	private class TeacherHolder{
		private Teacher teacher;
		public TeacherHolder(Teacher teacher) {this.teacher = teacher;}
		@SuppressWarnings("unused")
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
