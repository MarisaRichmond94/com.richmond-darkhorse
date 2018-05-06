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
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

//TODO figure out why you can modify the class size but you canNOT modify teachers
public class ModifyClassroom implements AdminLayout {

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
		GridPane modifyClassroomLayout = buildGridPane(stage);
		String stageTitle = theClassroom.getCenter(theClassroom.getCenterID()) + ": " + theClassroom.getClassroomType();
		buildPopUp(stage,modifyClassroomLayout,stageTitle);
	}
	
	/**
	 * Builds the layout for ModifyClassroom
	 * @param stage - the current stage
	 * @return a GridPane layout
	 */
	private GridPane buildGridPane(Stage stage) {
		GridPane gridpane = new GridPane();
		setConstraints(gridpane,3,0,10,10,"modulargridpane");
		ImageView classroomViewer = createImageWithFitHeight("images/classroom.png",200);
		Label title = createLabel("Modify Classroom","title");
		Label classType = createLabel("Classroom type:","label");
		ChoiceBox<String> typeBox = buildTypeBox();
	    Label centerName = createLabel("Center:","label");
	    ChoiceBox<Center> centerBox = buildCenterBox();
	    Label teacherName = createLabel("Lead teacher:","label");
	    ChoiceBox<TeacherHolder> teacherBox = buildTeacherBox(centerBox);
	    Label assistantName = createLabel("Assistant teacher (optional):","label");
	    ChoiceBox<TeacherHolder> assistantTeacherBox = buildAssistantTeacherBox(centerBox,teacherBox);
	    Label capacity = createLabel("Max capacity:","label");
	    ChoiceBox<Integer> capacityBox = buildCapacityBox();
	    Label duplicateTeacherWarning = createLabel("Cannot assign lead teacher and assistant teacher to the same person","label");
	    duplicateTeacherWarning.setTextFill(Color.RED);
		duplicateTeacherWarning.setVisible(false);
		ImageButton writeButton = new ImageButton(createImageWithFitHeight("images/write.png",100));
	    writeButton.setOnAction(e -> write(stage,teacherBox,assistantTeacherBox,capacityBox,duplicateTeacherWarning));
	    writeButton.setVisible(false);
	    Label writeLabel = createLabel("write","label");
	    writeLabel.setVisible(false);
	    Label editLabel = createLabel("edit","label");
	    ImageButton editButton = new ImageButton(createImageWithFitHeight("images/edit.png",100));
	    editButton.setOnAction(e -> edit(teacherBox,assistantTeacherBox,capacityBox,writeButton,editButton,writeLabel,editLabel));
	    ImageButton trashButton = new ImageButton(createImageWithFitHeight("images/trash.png",100));
	    trashButton.setOnAction(e -> {
	    		admin.deleteClassroom(theClassroom);
	    		stage.close();
	    	});
	    Label trashLabel = createLabel("delete","label");
	    Button cancelButton = new Button("Cancel");
	    cancelButton.getStyleClass().add("button");
	    cancelButton.setOnAction(e -> stage.close());	   
	    List<Node> nodes = Arrays.asList(duplicateTeacherWarning,classroomViewer,title,classType,typeBox,centerName,centerBox,teacherName,teacherBox,assistantName,assistantTeacherBox,capacity,capacityBox,editButton,writeButton,trashButton,editLabel,writeLabel,trashLabel,cancelButton);
		placeNodes(gridpane,nodes);
		gridpane.getStylesheets().add("css/admin.css");
		return gridpane;
	}
	
	/**
	 * Populates a list of {@link Teacher}s
	 * @param center - the selected {@link Center}
	 * @return a list of {@link Teacher}s at the selected {@link Center}
	 */
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
	
	/**
	 * Places all of the nodes in the list in the given GridPane
	 * @param gridpane - GridPane layout
	 * @param nodes - a list of nodes to be added to the GridPane
	 */
	public void placeNodes(GridPane gridpane,List<Node> nodes) {
		placeNodeSpan(gridpane,nodes.get(0),1,9,2,1,"center",null);
		placeNode(gridpane,nodes.get(1),0,0,"center",null);
		placeNodeSpan(gridpane,nodes.get(2),1,0,2,1,"center",null);
		placeNode(gridpane,nodes.get(3),0,1,"right",null);
		placeNodeSpan(gridpane,nodes.get(4),1,1,2,1,"left",null);
		placeNode(gridpane,nodes.get(5),0,2,"right",null);
		placeNodeSpan(gridpane,nodes.get(6),1,2,2,1,"left",null);
		placeNode(gridpane,nodes.get(7),0,3,"right",null);
		placeNodeSpan(gridpane,nodes.get(8),1,3,2,1,"left",null);
		placeNode(gridpane,nodes.get(9),0,4,"right",null);
		placeNodeSpan(gridpane,nodes.get(10),1,4,2,1,"left",null);
		placeNode(gridpane,nodes.get(11),0,5,"right",null);
		placeNodeSpan(gridpane,nodes.get(12),1,5,2,1,"left",null);
		placeNode(gridpane,nodes.get(13),1,6,"center",null);
		placeNode(gridpane,nodes.get(14),1,6,"center",null);
		placeNode(gridpane,nodes.get(15),2,6,"center",null);
		placeNode(gridpane,nodes.get(16),1,7,"center",null);
		placeNode(gridpane,nodes.get(17),1,7,"center",null);
		placeNode(gridpane,nodes.get(18),2,7,"center",null);
		placeNodeSpan(gridpane,nodes.get(19),1,8,2,1,"center",null);
	}
	
	/**
	 * Builds a ChoiceBox of type <String> populated with all of the classroom types
	 * @return a ChoiceBox<String>
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
	    typeBox.getStyleClass().add("choice-box");
	    typeBox.setDisable(true);
	    return typeBox;
	}
	
	/**
	 * Builds a ChoiceBox of type <Center> populated with all of the Centers in the system
	 * @return ChoiceBox<Center>
	 */
	private ChoiceBox<Center> buildCenterBox(){
		ChoiceBox<Center> centerBox = new ChoiceBox<Center>();
		if(admin.getCenters().isEmpty()) {centerBox.setDisable(true);}
		else {
	    		Map<String,Center> centers = admin.getCenters();
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
	 * Builds a ChoiceBox of type <TeacherHolder> populated with all of the Teachers in the system
	 * @param centerBox - a ChoiceBox<Center> that holds all of the Centers in the system
	 * @return ChoiceBox<TeacherHolder>
	 */
	private ChoiceBox<TeacherHolder> buildTeacherBox(ChoiceBox<Center> centerBox){
		ChoiceBox<TeacherHolder> teacherBox = new ChoiceBox<TeacherHolder>();
		TeacherHolder emptyTeacherHolder = new TeacherHolder(null);
	    teacherBox.getItems().add(emptyTeacherHolder);
	    List<Teacher> availableTeachers = populateTeachers(centerBox.getValue());
	    for(Teacher theTeacher : availableTeachers) {
	    		TeacherHolder teacherHolder = new TeacherHolder(theTeacher);
	    		if(theTeacher.equals(teacher)) {
	    			teacherBox.getItems().add(teacherHolder);
	    			teacherBox.setValue(teacherHolder);
	    		}else {teacherBox.getItems().add(teacherHolder);}
	    }
	    if(teacher == null) {teacherBox.setValue(emptyTeacherHolder);}
	    teacherBox.setDisable(true);
	    teacherBox.getStyleClass().add("choice-box");
	    return teacherBox;
	}
	
	/**
	 * Builds a ChoiceBox of type <TeacherHolder> populated with all of the Teachers in the system
	 * @param centerBox - a ChoiceBox<Center> that holds all of the Centers in the system
	 * @return ChoiceBox<TeacherHolder>
	 */
	private ChoiceBox<TeacherHolder> buildAssistantTeacherBox(ChoiceBox<Center> centerBox,ChoiceBox<TeacherHolder> teacherBox){
		ChoiceBox<TeacherHolder> assistantTeacherBox = new ChoiceBox<TeacherHolder>();
	    TeacherHolder emptyAssistantTeacherHolder = new TeacherHolder(null);
	    assistantTeacherBox.getItems().add(emptyAssistantTeacherHolder);
	    List<Teacher> availableAssistantTeachers = populateTeachers(centerBox.getValue());
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
	    assistantTeacherBox.setDisable(true);
	    assistantTeacherBox.getStyleClass().add("choice-box");
	    return assistantTeacherBox;
	}
	
	/**
	 * Builds a ChoiceBox of type <Integer> populated with the different room capacities
	 * @return ChoiceBox<Integer>
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
	 * Gets the text from each text field in the List. If the text field is empty, gets the current value stored in the center and submits all necessary fields to 
	 * the thread in order to modify the existing center. Once completed, the stage closes
	 * @param stage - the current stage 
	 */
	private void write(Stage stage,ChoiceBox<TeacherHolder> teacherBox,ChoiceBox<TeacherHolder> assistantTeacherBox,ChoiceBox<Integer> capacityBox,Label duplicateTeacherWarning) {
		Teacher newLeadTeacher = teacherBox.getValue().getTeacher(), newAssistantTeacher = assistantTeacherBox.getValue().getTeacher();
		int newMaxSize = capacityBox.getValue();
		if(newLeadTeacher != null && newAssistantTeacher != null && newLeadTeacher.equals(newAssistantTeacher)) {duplicateTeacherWarning.setVisible(true);}
		else {
	    		Thread modifyExistingClassroom = new Thread(new ModifyExistingClassroom(theClassroom,newLeadTeacher,newAssistantTeacher,newMaxSize,admin));
	    		modifyExistingClassroom.start();	
	    		stage.close();
	    	}
	}
	
	/**
	 * Enters the program into edit-mode by revealing the write button and enabling all text fields for typing
	 * @param teacherBox
	 * @param assistantTeacherBox
	 * @param capacityBox
	 * @param writeButton
	 * @param editButton
	 * @param writeLabel
	 * @param editLabel
	 */
	private void edit(ChoiceBox<TeacherHolder> teacherBox,ChoiceBox<TeacherHolder> assistantTeacherBox,ChoiceBox<Integer> capacityBox,Button writeButton,Button editButton,Label writeLabel,Label editLabel) {
		teacherBox.setDisable(false);
		assistantTeacherBox.setDisable(false);
		capacityBox.setDisable(false);
		writeButton.setVisible(true);
		writeLabel.setVisible(true);
		editButton.setVisible(false);
		editLabel.setVisible(false);
	}
	
	/**
	 * populates the list of classroom types
	 * @return a list of classroom types
	 */
	public Map<String,String> populateClassroomTypes() {
		classroomTypes.put("PreK2", "4+ years");
		classroomTypes.put("PreK1", "3-4 years");
		classroomTypes.put("Pre3", "2-3 years");
		return classroomTypes;
	}

	/**
	 * Grabs a list of classroom types
	 * @return a list of classroom types
	 */
	public Map<String, String> getClassroomTypes() {
		return classroomTypes;
	}
	
	private class TeacherHolder{
		private Teacher teacher;
		public TeacherHolder(Teacher teacher) {this.teacher = teacher;}
		public Teacher getTeacher() {return teacher;}
		@Override
		public String toString() {
			if(teacher == null) {return "N/A";}
			else {
				Center center = teacher.getCenter(teacher.getCenterID());
				return center.getAbbreviatedName() +  ": " + teacher.getFirstName() + " " + teacher.getLastName();
			}
		}
	}
	
}
