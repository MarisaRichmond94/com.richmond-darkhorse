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
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class ModifyClassSizePane extends GridPane implements DirectorLayout{

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
		buildGridPane(classroom);
	}
	
	/**
	 * Builds the GridPane for {@link ModifyClassSizePane}
	 * @param classroom - a {@link Classroom}
	 */
	private void buildGridPane(Classroom classroom) {
		setConstraints(this,3,0,10,10,"gridpane");
		this.getStylesheets().add("css/director.css");
		Label title = createLabel("Classroom Info","super-subtitle");
	    List<Label> labels = populateLabels(Arrays.asList("Classroom type:","Center:","Lead teacher:","Assistant teacher:","Max capacity:"),"centerallabel");
	    ChoiceBox<String> typeBox = buildTypeBox();
	    ChoiceBox<Center> centerBox = buildCenterBox();
	    ChoiceBox<TeacherHolder> teacherBox = buildLeadBox();
	    ChoiceBox<TeacherHolder> assistantTeacherBox = buildAssistantBox();
	    ChoiceBox<Integer> capacityBox = buildCapacityBox();
	    ImageButton writeButton = new ImageButton(createImageWithFitHeight("images/write.png",125));
	    writeButton.setOnAction(e -> write(classroom,capacityBox));
	    writeButton.setVisible(false);
	    Label writeLabel = createLabel("write","centrallabel");
	    writeLabel.setVisible(false);
	    Label editLabel = createLabel("edit","centrallabel");
	    ImageButton editButton = new ImageButton(createImageWithFitHeight("images/edit.png",125));
	    editButton.setOnAction(e -> edit(capacityBox,writeButton,writeLabel,editButton,editLabel));
	    List<Node> nodes = Arrays.asList(labels.get(0),typeBox,labels.get(1),centerBox,labels.get(2),teacherBox,labels.get(3),assistantTeacherBox,labels.get(4),capacityBox,editButton,writeButton,editLabel,writeLabel,title);
		placeNodes(this,nodes);
	}
	
	@Override
	public void placeNodes(GridPane gridpane, List<Node> nodes) {
		placeNode(gridpane,nodes.get(0),0,1,"right",null);
		placeNodeSpan(gridpane,nodes.get(1),1,1,2,1,"left",null);
		placeNode(gridpane,nodes.get(2),0,2,"right",null);
		placeNodeSpan(gridpane,nodes.get(3),1,2,2,1,"left",null);
		placeNode(gridpane,nodes.get(4),0,3,"right",null);
		placeNodeSpan(gridpane,nodes.get(5),1,3,2,1,"left",null);
		placeNode(gridpane,nodes.get(6),0,4,"right",null);
		placeNodeSpan(gridpane,nodes.get(7),1,4,2,1,"left",null);
		placeNode(gridpane,nodes.get(8),0,5,"right",null);
		placeNodeSpan(gridpane,nodes.get(9),1,5,2,1,"left",null);
		placeNode(gridpane,nodes.get(10),1,6,"center",null);
		placeNode(gridpane,nodes.get(11),1,6,"center",null);
		placeNode(gridpane,nodes.get(12),1,7,"center",null);
		placeNode(gridpane,nodes.get(13),1,7,"center",null);	
		placeNodeSpan(gridpane,nodes.get(14),0,0,3,1,"center",null);
	}
	
	/**
	 * Builds a ChoiceBox populated with Strings 
	 * @return ChoiceBox
	 */
	private ChoiceBox<String> buildTypeBox() {
		ChoiceBox<String> typeBox = new ChoiceBox<String>();
	    Map<String,String> classroomTypes = this.getClassroomTypes();
	    	for(String ageGroup : classroomTypes.values()) {
	    		if(ageGroup.equals(previousAgeGroup)) {
	    			typeBox.getItems().add(ageGroup);
	    			typeBox.setValue(ageGroup);
	    		}
	    	}
	    typeBox.setDisable(true);
	    typeBox.getStyleClass().add("choice-box");
	    return typeBox;
	}
	
	/**
	 * Builds a ChoiceBox populated with Centers 
	 * @return ChoiceBox
	 */
	private ChoiceBox<Center> buildCenterBox(){
		ChoiceBox<Center> centerBox = new ChoiceBox<Center>();
	    if(director.getCenters().isEmpty()) {centerBox.setDisable(true);}else {
	    		Map<String,Center> centers = director.getCenters();
	    		for(Center activeCenter : centers.values()) {
	    			centerBox.getItems().add(activeCenter);
	    			if(activeCenter == center) {centerBox.setValue(activeCenter);}
	    		}
	    }
	    centerBox.setDisable(true);
	    centerBox.getStyleClass().add("choice-box");
	    return centerBox;
	}
	
	/**
	 * Builds a ChoiceBox populated with Teachers 
	 * @return ChoiceBox
	 */
	private ChoiceBox<TeacherHolder> buildLeadBox(){
		ChoiceBox<TeacherHolder> teacherBox = new ChoiceBox<TeacherHolder>();
	    TeacherHolder emptyTeacherHolder = new TeacherHolder(null);
	    teacherBox.getItems().add(emptyTeacherHolder);
	    List<Teacher> availableTeachers = populateTeachers();
	    for(Teacher theTeacher : availableTeachers) {
	    		TeacherHolder teacherHolder = new TeacherHolder(theTeacher);
	    		if(theTeacher.equals(teacher)) {
	    			teacherBox.getItems().add(teacherHolder);
	    			teacherBox.setValue(teacherHolder);
	    		}
	    		else {teacherBox.getItems().add(teacherHolder);}
	    }
	    if(teacher == null) {teacherBox.setValue(emptyTeacherHolder);}
	    teacherBox.setDisable(true);
	    teacherBox.getStyleClass().add("choice-box");
	    return teacherBox;
	}
	
	/**
	 * Builds a ChoiceBox populated with Teachers 
	 * @return ChoiceBox
	 */
	private ChoiceBox<TeacherHolder> buildAssistantBox(){
		ChoiceBox<TeacherHolder> assistantTeacherBox = new ChoiceBox<TeacherHolder>();
	    TeacherHolder emptyAssistantTeacherHolder = new TeacherHolder(null);
	    assistantTeacherBox.getItems().add(emptyAssistantTeacherHolder);
	    List<Teacher> availableAssistantTeachers = populateTeachers();
	    for(Teacher theAssistantTeacher : availableAssistantTeachers) {
	    		TeacherHolder assistantTeacherHolder = new TeacherHolder(theAssistantTeacher);
	    		if(theAssistantTeacher.equals(assistantTeacher)) {
	    			assistantTeacherBox.getItems().add(assistantTeacherHolder);
	    			assistantTeacherBox.setValue(assistantTeacherHolder);
	    		}
	    		else {assistantTeacherBox.getItems().add(assistantTeacherHolder);}
	    }
	    if(assistantTeacher == null) {assistantTeacherBox.setValue(emptyAssistantTeacherHolder);}
	    assistantTeacherBox.setDisable(true);
	    assistantTeacherBox.getStyleClass().add("choice-box");
	    return assistantTeacherBox;
	}
	
	/**
	 * Builds a ChoiceBox populated with Integers 
	 * @return ChoiceBox
	 */
	private ChoiceBox<Integer> buildCapacityBox(){
		ChoiceBox<Integer> capacityBox = new ChoiceBox<Integer>();
	    for(int theMaxCapacity : roomSizes) {
	    		capacityBox.getItems().add(theMaxCapacity);
	    		if(theMaxCapacity == maxCapacity) {capacityBox.setValue(theMaxCapacity);}
	    }
	    capacityBox.setDisable(true);
	    capacityBox.getStyleClass().add("choice-box");
	    return capacityBox;
	}
	
	/**
	 * Writes changes to the {@link Classroom}'s capacity
	 * @param classroom - a {@link Classroom}
	 * @param capacityBox - ChoiceBox
	 */
	private void write(Classroom classroom,ChoiceBox<Integer> capacityBox) {
		int newMaxSize = capacityBox.getValue();
    		Thread modifyExistingClassSize = new Thread(new ModifyExistingClassSize(classroom,newMaxSize,director));
    		modifyExistingClassSize.start();	
    		Label saveSuccessful = createLabel("save successful!","centrallabel");
    		saveSuccessful.setTextFill(Color.RED);
    		placeNode(this,saveSuccessful,1,7,"center",null);
	}
	
	/**
	 * Enters the {@link Director} into edit mode
	 */
	private void edit(ChoiceBox<Integer> capacityBox,ImageButton writeButton,Label writeLabel,ImageButton editButton,Label editLabel) {
		capacityBox.setDisable(false);
		writeButton.setVisible(true);
		writeLabel.setVisible(true);
		editButton.setVisible(false);
		editLabel.setVisible(false);
	}
	
	/**
	 * Populates a List with {@link Teacher}s and returns it
	 * @return List of {@link Teacher}s
	 */
	private List<Teacher> populateTeachers(){
		Map<String,StaffMember> staffMembers = director.getStaffMembers();
		List<Teacher> teachers = new ArrayList<Teacher>();
		for(StaffMember staffMember: staffMembers.values()) {
			String title = staffMember.getTitle();
			if(title.equals("Teacher")) {teachers.add((Teacher) staffMember);}
		}
		return teachers;
	}
	
	/**
	 * Populates a List with the various {@link Classroom} types
	 * @return List of {@link Classroom} types
	 */
	private Map<String,String> populateClassroomTypes() {
		classroomTypes.put("PreK2", "4+ years");
		classroomTypes.put("PreK1", "3-4 years");
		classroomTypes.put("Pre3", "2-3 years");
		return classroomTypes;
	}

	public Map<String, String> getClassroomTypes() {
		return classroomTypes;
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

}
