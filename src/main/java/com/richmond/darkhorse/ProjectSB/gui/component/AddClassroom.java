package com.richmond.darkhorse.ProjectSB.gui.component;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
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

public class AddClassroom implements AdminLayout{
	
	private Admin admin;
	private List<Integer> roomSizes = Arrays.asList(7,9,12,14,18);
	private Map<String,String> classroomTypes;
	
	public AddClassroom(Admin admin) { 
		this.admin = admin;
		this.classroomTypes = new HashMap<String,String>();
		this.classroomTypes = populateClassroomTypes();
	}
	
	public void display() {
		Stage stage = new Stage();
		GridPane addClassroomLayout = buildGridPane(stage,admin);
		buildPopUp(stage,addClassroomLayout,"Create New \nClassroom");
	}
	
	/**
	 * Builds the layout for AddClassroom
	 * @param stage - the current stage
	 * @return a GridPane layout
	 */
	private GridPane buildGridPane(Stage stage,Admin admin) {
		GridPane gridpane = new GridPane();
		setConstraints(gridpane,3,0,10,10,"modulargridpane");
		ImageView classroomViewer = createImageWithFitHeight("images/classroom.png",200);
		Label title = createLabel("Create New \nClassroom","title");
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
		Label teacherAlreadyActiveWarning = createLabel("Teacher is already active in another classroom","label");
	    teacherAlreadyActiveWarning.setTextFill(Color.RED);
		teacherAlreadyActiveWarning.setVisible(false);
		Button createClassroomButton = createButton("Create Classroom",null,0,0,0);
		createClassroomButton.setOnAction(e -> createClassroom(typeBox,centerBox,teacherBox,assistantTeacherBox,capacityBox,teacherAlreadyActiveWarning,duplicateTeacherWarning,stage));
		Button cancelButton = new Button("Cancel");
	    cancelButton.getStyleClass().add("button");
	    cancelButton.setOnAction(e -> stage.close());	   
	    List<Node> nodes = Arrays.asList(teacherAlreadyActiveWarning,duplicateTeacherWarning,classroomViewer,title,classType,typeBox,centerName,centerBox,teacherName,teacherBox,assistantName,assistantTeacherBox,capacity,capacityBox,createClassroomButton,cancelButton);
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
		placeNodeSpan(gridpane,nodes.get(0),0,7,3,1,"center",null);
		placeNodeSpan(gridpane,nodes.get(1),0,7,3,1,"center",null);
		placeNode(gridpane,nodes.get(2),0,0,"center",null);
		placeNodeSpan(gridpane,nodes.get(3),1,0,2,1,"center",null);
		placeNode(gridpane,nodes.get(4),0,1,"right",null);
		placeNodeSpan(gridpane,nodes.get(5),1,1,2,1,"left",null);
		placeNode(gridpane,nodes.get(6),0,2,"right",null);
		placeNodeSpan(gridpane,nodes.get(7),1,2,2,1,"left",null);
		placeNode(gridpane,nodes.get(8),0,3,"right",null);
		placeNodeSpan(gridpane,nodes.get(9),1,3,2,1,"left",null);
		placeNode(gridpane,nodes.get(10),0,4,"right",null);
		placeNodeSpan(gridpane,nodes.get(11),1,4,2,1,"left",null);
		placeNode(gridpane,nodes.get(12),0,5,"right",null);
		placeNodeSpan(gridpane,nodes.get(13),1,5,2,1,"left",null);
		placeNode(gridpane,nodes.get(14),1,6,"center",null);
		placeNode(gridpane,nodes.get(15),2,6,"left",null);
	}
	
	/**
	 * Builds a ChoiceBox of type <String> populated with all of the classroom types
	 * @return a ChoiceBox<String>
	 */
	private ChoiceBox<String> buildTypeBox() {
		ChoiceBox<String> typeBox = new ChoiceBox<String>();
	    Map<String,String> classroomTypes = this.getClassroomTypes();
	    	typeBox.getItems().addAll(classroomTypes.get("PreK2"),classroomTypes.get("PreK1"),classroomTypes.get("Pre3"));
	    typeBox.setValue(typeBox.getItems().get(0));
	    typeBox.getStyleClass().add("choice-box");
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
	    		for(Center activeCenter : centers.values()) {centerBox.getItems().add(activeCenter);}
	    		centerBox.setValue(centerBox.getItems().get(0));
	    }
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
	    for(Teacher teacher : availableTeachers) {
	    		TeacherHolder teacherHolder = new TeacherHolder(teacher);
	    		teacherBox.getItems().add(teacherHolder);
	    }
	    teacherBox.setValue(emptyTeacherHolder);
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
	    for(Teacher assistantTeacher : availableAssistantTeachers) {
	    		TeacherHolder teacherHolder = new TeacherHolder(assistantTeacher);
	    		if(teacherBox.getValue() != teacherHolder) {assistantTeacherBox.getItems().add(teacherHolder);}
	    }
	    assistantTeacherBox.setValue(emptyAssistantTeacherHolder);
	    assistantTeacherBox.getStyleClass().add("choice-box");
	    return assistantTeacherBox;
	}
	
	/**
	 * Builds a ChoiceBox of type <Integer> populated with the different room capacities
	 * @return ChoiceBox<Integer>
	 */
	private ChoiceBox<Integer> buildCapacityBox(){
		ChoiceBox<Integer> capacityBox = new ChoiceBox<Integer>();
	    for(int maxCapacity : roomSizes) {capacityBox.getItems().add(maxCapacity);}
	    capacityBox.setValue(capacityBox.getItems().get(0));
	    capacityBox.getStyleClass().add("choice-box");
	    return capacityBox;
	}
	
	/**
	 * Creates a new {@link Classroom} Object
	 * @param typeBox
	 * @param centerBox
	 * @param teacherBox
	 * @param assistantTeacherBox
	 * @param capacityBox
	 * @param teacherAlreadyActiveWarning
	 * @param duplicateTeacherWarning
	 * @param stage - the current stage
	 */
	private void createClassroom(ChoiceBox<String> typeBox,ChoiceBox<Center> centerBox,ChoiceBox<TeacherHolder> teacherBox,ChoiceBox<TeacherHolder> assistantTeacherBox,ChoiceBox<Integer> capacityBox,Label teacherAlreadyActiveWarning,Label duplicateTeacherWarning,Stage stage) {
		String ageGroup = typeBox.getValue(),classroomType = null;
		Center selectedCenter = centerBox.getValue();
		Teacher selectedTeacher = teacherBox.getValue().getTeacher(), selectedAssistantTeacher = assistantTeacherBox.getValue().getTeacher();
		int maxCapacity = capacityBox.getValue();
	    	if(ageGroup.equals("4+ years")) {classroomType = "PreK2";	}
	    	else if(ageGroup.equals("3-4 years")) {classroomType = "PreK1";}
	    	else {classroomType = "Pre3";}
	    	if(teacherBox.getValue() == null) {selectedTeacher.setTeacherID(null);}
	    	if(assistantTeacherBox.getValue() == null) {selectedAssistantTeacher.setTeacherID(null);}
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
