package com.richmond.darkhorse.ProjectSB.gui.component;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import com.richmond.darkhorse.ProjectSB.Admin;
import com.richmond.darkhorse.ProjectSB.Center;
import com.richmond.darkhorse.ProjectSB.Classroom;
import com.richmond.darkhorse.ProjectSB.Teacher;
import com.richmond.darkhorse.ProjectSB.middleman.ModifyExistingTeacher;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class ModifyTeacher implements AdminLayout{

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
		GridPane modifyTeacherLayout = buildGridPane(stage);
		String stageTitle = teacher.getFirstName() + "\n" + teacher.getLastName();
		buildPopUp(stage,modifyTeacherLayout,stageTitle);
	}
	
	/**
	 * Builds GridPane layout
	 * @param stage - the current stage
	 * @return GridPane
	 */
	private GridPane buildGridPane(Stage stage) {
		GridPane gridpane = new GridPane();
		setConstraints(gridpane,3,0,10,10,"modulargridpane");
		ImageView teacherViewer = createImageWithFitHeight("images/teacher.png",200);
		Label title = createLabel("Modify Teacher","title");
		Label teacherFirstName = createLabel("First name:","label");
		TextField firstNameField = createTextField(teacher.getFirstName(),"textfield",650);
		Label teacherLastName = createLabel("Last name:","label");
		TextField lastNameField = createTextField(teacher.getLastName(),"textfield",650);
	  	Label titleChange = createLabel("Title (if applicable):","label");
	  	ChoiceBox<String> titleBox = new ChoiceBox<String>();
	  	titleBox.getItems().addAll("Director","Teacher");
	  	titleBox.setValue(titleBox.getItems().get(1));
	  	titleBox.setDisable(true);
	  	titleBox.getStyleClass().add("choice-box");
		Label centerSelection = createLabel("Center","label");
		ChoiceBox<Center> centerBox = buildCenterBox();
		Label classroomSelection = createLabel("Classroom:","label");
		ChoiceBox<ClassroomHolder> classroomBox = buildClassroomBox();
		Label activeTeacherWarning = createLabel("The classroom you have selected already has an active teacher","label");
		activeTeacherWarning.setTextFill(Color.RED);
		Label activeDirectorWarning = createLabel("The center you have selected already has an active director","label");
		activeDirectorWarning.setTextFill(Color.RED);
		activeTeacherWarning.setVisible(false);
		activeDirectorWarning.setVisible(false);
		List<Node> nodes = new ArrayList<>();
		nodes.addAll(Arrays.asList(firstNameField,lastNameField,titleBox,centerBox,classroomBox));
		ImageButton writeButton = new ImageButton(createImageWithFitHeight("images/write.png",100));
		List<TextField> textFields = new ArrayList<>();
		textFields.addAll(Arrays.asList(firstNameField,lastNameField));
	    writeButton.setOnAction(e -> write(stage,textFields,centerBox,classroomBox,titleBox,activeDirectorWarning,activeTeacherWarning));
	    writeButton.setVisible(false);
	    Label writeLabel = createLabel("write","label");
	    writeLabel.setVisible(false);
	    Label editLabel = createLabel("edit","label");
	    ImageButton editButton = new ImageButton(createImageWithFitHeight("images/edit.png",100));
	    editButton.setOnAction(e -> edit(nodes,writeButton,editButton,writeLabel,editLabel));
	    ImageButton trashButton = new ImageButton(createImageWithFitHeight("images/trash.png",100));
	    trashButton.setOnAction(e -> {
	    		admin.deleteTeacher(teacher);
			stage.close();
	    	});
	    Label trashLabel = createLabel("delete","label");
	    Button cancel = new Button("cancel");
	    cancel.setOnAction(e -> stage.close());
	    cancel.getStyleClass().add("button");
		List<Node> allNodes = new ArrayList<>();
		allNodes.addAll(Arrays.asList(activeTeacherWarning,activeDirectorWarning,teacherViewer,title,teacherFirstName,firstNameField,teacherLastName,lastNameField,titleChange,titleBox,centerSelection,centerBox,classroomSelection,classroomBox,editButton,writeButton,trashButton,editLabel,writeLabel,trashLabel,cancel));
		placeNodes(gridpane,allNodes);
		gridpane.getStylesheets().add("css/admin.css");
		return gridpane;
	}
	
	/**
	 * Places all of the nodes in the GridPane
	 */
	public void placeNodes(GridPane gridpane, List<Node> nodes) {
		placeNodeSpan(gridpane,nodes.get(0),0,10,3,1,"center",null);
		placeNodeSpan(gridpane,nodes.get(1),0,10,3,1,"center",null);
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
		placeNode(gridpane,nodes.get(15),1,6,"center",null);
		placeNode(gridpane,nodes.get(16),2,6,"center",null);
		placeNode(gridpane,nodes.get(17),1,7,"center",null);
		placeNode(gridpane,nodes.get(18),1,7,"center",null);
		placeNode(gridpane,nodes.get(19),2,7,"center",null);
		placeNodeSpan(gridpane,nodes.get(20),1,9,2,1,"center",null);
	}
	
	/**
	 * Writes changes
	 * @param stage
	 * @param textFields
	 * @param centerBox
	 * @param classroomBox
	 * @param titleBox
	 * @param activeDirectorWarning
	 * @param activeTeacherWarning
	 */
	private void write(Stage stage,List<TextField> textFields,ChoiceBox<Center> centerBox,ChoiceBox<ClassroomHolder> classroomBox,ChoiceBox<String> titleBox,Label activeDirectorWarning,Label activeTeacherWarning) {
		String teachersFirstName = textFields.get(0).getText(), teachersLastName = textFields.get(1).getText();
		if(textFields.get(0).getText().trim().isEmpty()) {teachersFirstName = theFirstName;}
		if(textFields.get(1).getText().trim().isEmpty()) {teachersLastName = theLastName;}
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
	}

	/**
	 * Enters edit mode
	 * @param nodes
	 * @param writeButton
	 * @param editButton
	 * @param writeLabel
	 * @param editLabel
	 */
	private void edit(List<Node> nodes,Button writeButton,Button editButton,Label writeLabel,Label editLabel) {
		nodes.get(0).setDisable(false);
		nodes.get(1).setDisable(false);
		nodes.get(2).setDisable(false);
		nodes.get(3).setDisable(false);
		nodes.get(4).setDisable(false);
		writeButton.setVisible(true);
		writeLabel.setVisible(true);
		editButton.setVisible(false);
		editLabel.setVisible(false);
	}
	
	/**
	 * Builds a ChoiceBox that holds all of the {@link Center}s
	 * @return a ChoiceBox<Center>
	 */
	private ChoiceBox<Center> buildCenterBox() {
		ChoiceBox<Center> centerBox = new ChoiceBox<Center>();
	    if(admin.getCenters().isEmpty()) {centerBox.setDisable(true);
	    }else {
	    		Map<String,Center> centers = admin.getCenters();
	    		for(Center activeCenter : centers.values()) {
	    			centerBox.getItems().add(activeCenter);
	    			if(activeCenter == previousCenter) {centerBox.setValue(activeCenter);}
	    		} 
	    }
	    centerBox.setMaxWidth(650);
	    centerBox.setDisable(true);
	    centerBox.getStyleClass().add("choice-box");
		return centerBox;
	}
	
	/**
	 * Builds a ChoiceBox that holds all of the {@link Classroom}s
	 * @return a ChoiceBox<ClassroomHolder>
	 */
	private ChoiceBox<ClassroomHolder> buildClassroomBox() {
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
	    classroomBox.setMaxWidth(650);
	    classroomBox.setDisable(true);
	    classroomBox.getStyleClass().add("choice-box");
		return classroomBox;
	}
	
	class ClassroomHolder{
		
		private Classroom classroom;
		
		public ClassroomHolder(Classroom classroom) {
			this.classroom = classroom;
		}
		
		public Classroom getClassroom() {
			return classroom;
		}
		
		@Override
		public String toString() {
			if(classroom == null) {return "N/A";}
			else {return classroom.getCenter(classroom.getCenterID()) + ": " + classroom.getClassroomType();}
		}
	}
		
}
