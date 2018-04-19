package com.richmond.darkhorse.ProjectSB.gui.component;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import com.richmond.darkhorse.ProjectSB.Admin;
import com.richmond.darkhorse.ProjectSB.Center;
import com.richmond.darkhorse.ProjectSB.Classroom;
import com.richmond.darkhorse.ProjectSB.middleman.CreateNewTeacher;
import javafx.beans.binding.Bindings;

public class AddTeacher implements AdminLayout{
	
	private Admin admin;
	
	public AddTeacher(Admin admin) { 
		this.admin = admin;
	}
	
	public void display() {
		Stage stage = new Stage();
		GridPane addTeacherLayout = buildGridPane(stage);
		buildPopUp(stage,addTeacherLayout,"Create New Teacher");
	}
	
	private GridPane buildGridPane(Stage stage) {
		GridPane gridpane = new GridPane();
		setConstraints(gridpane,3,0,10,10,"modulargridpane");
		ImageView teacherViewer = createImageWithFitHeight("images/teacher.png",200);
		Label title = createLabel("Create New \nTeacher","title");
		Label teacherFirstName = createLabel("First name:","label");
		TextField firstNameField = createTextField("first name","textfield",650);
		Label teacherLastName = createLabel("Last name:","label");
		TextField lastNameField = createTextField("last name","textfield",650);
		Label centerSelection = createLabel("Center","label");
		ChoiceBox<Center> centerBox = buildCenterBox();
		Label classroomSelection = createLabel("Classroom:","label");
		ChoiceBox<ClassroomHolder> classroomBox = buildClassroomBox();
		Label activeTeacherWarning = createLabel("The classroom you have selected already has an active teacher","label");
		activeTeacherWarning.setTextFill(Color.RED);
		activeTeacherWarning.setVisible(false);
		Button createTeacherButton = new Button("Create Teacher");
		createTeacherButton.getStyleClass().add("button");
		createTeacherButton.disableProperty().bind(
			Bindings.isEmpty(firstNameField.textProperty())
			.or(Bindings.isEmpty(lastNameField.textProperty()))
		);
		List<TextField> textFields = new ArrayList<>();
		textFields.addAll(Arrays.asList(firstNameField,lastNameField));
		createTeacherButton.setOnAction(e -> createTeacher(stage,classroomBox,centerBox,textFields,activeTeacherWarning));
		Button cancelButton = new Button("Cancel");
		cancelButton.getStyleClass().add("button");
		cancelButton.setOnAction(e -> stage.close());
		List<Node> nodes = new ArrayList<>();
		nodes.addAll(Arrays.asList(activeTeacherWarning,teacherViewer,title,teacherFirstName,firstNameField,teacherLastName,lastNameField,centerSelection,centerBox,classroomSelection,classroomBox,createTeacherButton,cancelButton));
		placeNodes(gridpane,nodes);
		gridpane.getStylesheets().add("css/admin.css");
		return gridpane;
	}
	
	/**
	 * Places all of the nodes in the GridPane
	 */
	public void placeNodes(GridPane gridpane, List<Node> nodes) {
		placeNodeSpan(gridpane,nodes.get(0),0,6,3,1,"center",null);
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
		placeNode(gridpane,nodes.get(11),1,5,"center",null);
		placeNode(gridpane,nodes.get(12),2,5,"center",null);
	}
	
	/**
	 * Builds a ChoiceBox that holds all of the {@link Center}s
	 * @return a ChoiceBox<Center>
	 */
	private ChoiceBox<Center> buildCenterBox() {
		ChoiceBox<Center> centerBox = new ChoiceBox<Center>();
		if(admin.getCenters().isEmpty()) {centerBox.setDisable(true);}
		else {
			Map<String,Center> centers = admin.getCenters();
			for(Center activeCenter : centers.values()) {	centerBox.getItems().add(activeCenter);}
			centerBox.setValue(centerBox.getItems().get(0));
		}
		centerBox.setMaxWidth(650);
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
				}
			}
		}
		classroomBox.setValue(emptyClassroom);
		classroomBox.setMaxWidth(650);
		classroomBox.getStyleClass().add("choicebox");
		return classroomBox;
	}
	
	/**
	 * Creates a new {@link Teacher} object
	 * @param stage
	 * @param classroomBox
	 * @param centerBox
	 * @param textFields
	 * @param activeTeacherWarning
	 */
	private void createTeacher(Stage stage,ChoiceBox<ClassroomHolder> classroomBox,ChoiceBox<Center> centerBox,List<TextField> textFields,Label activeTeacherWarning) {
		String teachersFirstName = textFields.get(0).getText();
		String teachersLastName = textFields.get(1).getText();
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

