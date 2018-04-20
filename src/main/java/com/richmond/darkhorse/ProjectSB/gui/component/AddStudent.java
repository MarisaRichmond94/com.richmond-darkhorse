package com.richmond.darkhorse.ProjectSB.gui.component;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.richmond.darkhorse.ProjectSB.Center;
import com.richmond.darkhorse.ProjectSB.Classroom;
import com.richmond.darkhorse.ProjectSB.Director;
import com.richmond.darkhorse.ProjectSB.middleman.CreateNewStudent;
import javafx.beans.binding.Bindings;
import javafx.scene.control.CheckBox;

public class AddStudent implements DirectorLayout{

	private Director director;
	
	public AddStudent(Director director) {
		this.director = director;
	}

	public void display() {
		Stage stage = new Stage();
		GridPane addStudentLayout = buildGridPane(stage);
		buildPopUp(stage,addStudentLayout,"Create New Student");
	}
	
	/**
	 * Builds the main GridPane for the AddStudent layout
	 * @param stage - the current stage
	 * @return GridPane
	 */
	private GridPane buildGridPane(Stage stage) {
		GridPane gridpane = new GridPane();
		setConstraints(gridpane,3,0,10,10,"modulargridpane");
		ImageView studentViewer = createImageWithFitHeight("images/students.png",150);
		Label title = createLabel("Create New \n Student","popup-title");
		List<String> labelStrings = Arrays.asList("first name:","last name:","birth date:","classroom:","attendance plan:");
		List<String> textFieldPrompts = Arrays.asList("first name","last name","MM/DD/YYYY");
		List<Label> labels = populateLabels(labelStrings,"label");
		List<TextField> textFields = populateTextFields(textFieldPrompts,"textfield",650);
		ChoiceBox<ClassroomHolder> classroomBox = buildClassroomBox();
		CheckBox mondayCheckBox = buildCheckBox("Monday","checkbox");
		CheckBox tuesdayCheckBox = buildCheckBox("Tuesday","checkbox");
		CheckBox wednesdayCheckBox = buildCheckBox("Wednesday","checkbox");
		CheckBox thursdayCheckBox = buildCheckBox("Thursday","checkbox");
		CheckBox fridayCheckBox = buildCheckBox("Friday","checkbox");
		List<CheckBox> checkBoxes = Arrays.asList(mondayCheckBox,tuesdayCheckBox,wednesdayCheckBox,thursdayCheckBox,fridayCheckBox);
		Button createStudentButton = createButton("Create Student",null,0,0,0);
		createStudentButton.disableProperty().bind(
			Bindings.isEmpty(textFields.get(0).textProperty())
			.or(Bindings.isEmpty(textFields.get(1).textProperty())
			.or(Bindings.isEmpty(textFields.get(2).textProperty())))
		);
		createStudentButton.setOnAction(e -> createStudent(stage,textFields,checkBoxes,classroomBox));
		Button cancelButton = createButton("Cancel",null,0,0,0);	    
		cancelButton.setOnAction(e -> stage.close());
		List<Node> nodes = Arrays.asList(studentViewer,title,labels.get(0),textFields.get(0),labels.get(1),textFields.get(1),labels.get(2),textFields.get(2),labels.get(3),classroomBox,labels.get(4),mondayCheckBox,tuesdayCheckBox,wednesdayCheckBox,thursdayCheckBox,fridayCheckBox,createStudentButton,cancelButton);
		placeNodes(gridpane,nodes);
		gridpane.getStylesheets().add("css/director.css");
		return gridpane;
	}
	
	/**
	 * Extracts information and uses it to create a new {@link Student}
	 * @param stage - the current stage
	 * @param textfields - all text fields
	 * @param checkBoxes - all check boxes
	 * @param classroomBox - {@link Classroom} choice box 
	 */
	private void createStudent(Stage stage,List<TextField> textfields,List<CheckBox> checkBoxes,ChoiceBox<ClassroomHolder> classroomBox) {
		String firstName = textfields.get(0).getText(), lastName = textfields.get(1).getText(), birthDate = textfields.get(2).getText();
		Classroom classroom = null;
		if(classroomBox.getValue().getClassroom() != null) { classroom = classroomBox.getValue().getClassroom(); }
		Map<String,Boolean> attendancePlan = new HashMap<String,Boolean>();
		if(checkBoxes.get(0).isSelected() == true) {attendancePlan.put("Monday",true);
		}else {attendancePlan.put("Monday", false);}
		if(checkBoxes.get(1).isSelected() == true) {attendancePlan.put("Tuesday",true);
		}else {attendancePlan.put("Tuesday", false);}
		if(checkBoxes.get(2).isSelected() == true) {attendancePlan.put("Wednesday",true);
		}else {attendancePlan.put("Wednesday", false);}
		if(checkBoxes.get(3).isSelected() == true) {attendancePlan.put("Thursday",true);
		}else {attendancePlan.put("Thursday", false);}
		if(checkBoxes.get(4).isSelected() == true) {attendancePlan.put("Friday",true);
		}else {attendancePlan.put("Friday", false);}
		Thread createNewStudent = new Thread(new CreateNewStudent(director,firstName,lastName,birthDate,classroom,attendancePlan));
		createNewStudent.start();
		stage.close();
	}
	
	@Override
	public void placeNodes(GridPane gridpane, List<Node> nodes) {
		placeNode(gridpane,nodes.get(0),0,0,"center",null);
		placeNodeSpan(gridpane,nodes.get(1),1,0,2,1,"center",null);
		placeNode(gridpane,nodes.get(2),0,1,"right",null);
		placeNodeSpan(gridpane,nodes.get(3),1,1,2,1,"left",null);
		placeNode(gridpane,nodes.get(4),0,2,"right",null);
		placeNodeSpan(gridpane,nodes.get(5),1,2,2,1,"left",null);
		placeNode(gridpane,nodes.get(6),0,3,"right",null);
		placeNodeSpan(gridpane,nodes.get(7),1,3,2,1,"left",null);
		placeNode(gridpane,nodes.get(8),0,4,"right",null);
		placeNodeSpan(gridpane,nodes.get(9),1,4,2,1,"left",null);
		placeNode(gridpane,nodes.get(10),0,5,"right",null);
		placeNode(gridpane,nodes.get(11),1,5,"left",null);
		placeNode(gridpane,nodes.get(12),1,6,"left",null);
		placeNode(gridpane,nodes.get(13),1,7,"left",null);
		placeNode(gridpane,nodes.get(14),1,8,"left",null);
		placeNode(gridpane,nodes.get(15),1,9,"left",null);
		placeNode(gridpane,nodes.get(16),1,10,"center",null);
		placeNode(gridpane,nodes.get(17),2,10,"center",null);
	}
	
	/**
	 * Creates a ChoiceBox containing all of the available {@link Classroom}s in the {@link Center}
	 * @return ChoiceBox<ClassroomHolder>
	 */
	private ChoiceBox<ClassroomHolder> buildClassroomBox(){
		ChoiceBox<ClassroomHolder> classroomBox = new ChoiceBox<>();
		ClassroomHolder emptyClassroom = new ClassroomHolder(null);
	    classroomBox.getItems().add(emptyClassroom);
	    Center center = director.getCenter(director.getCenterID());
	    Map<String,Classroom> classrooms = center.getClassrooms();
	    for(Classroom classroom : classrooms.values()) {
	    		ClassroomHolder newClassroom = new ClassroomHolder(classroom);
	    		classroomBox.getItems().add(newClassroom);
	    }
	    	classroomBox.setValue(classroomBox.getItems().get(0));
	    classroomBox.getStyleClass().add("choice-box");
	    return classroomBox;
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
			if(classroom == null) {return "N/A";}
			else {return classroom.getCenter(classroom.getCenterID()).getAbbreviatedName() + ": " + classroom.getClassroomType();}
		}
	}
	
}
