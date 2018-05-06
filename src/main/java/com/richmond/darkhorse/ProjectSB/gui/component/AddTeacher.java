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
import java.util.List;
import java.util.Map;
import com.richmond.darkhorse.ProjectSB.Admin;
import com.richmond.darkhorse.ProjectSB.Center;
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
		Button createTeacherButton = new Button("Create Teacher");
		createTeacherButton.getStyleClass().add("button");
		createTeacherButton.disableProperty().bind(
			Bindings.isEmpty(firstNameField.textProperty())
			.or(Bindings.isEmpty(lastNameField.textProperty()))
		);
		List<TextField> textFields = Arrays.asList(firstNameField,lastNameField);
		createTeacherButton.setOnAction(e -> createTeacher(stage,centerBox,textFields));
		Button cancelButton = new Button("Cancel");
		cancelButton.getStyleClass().add("button");
		cancelButton.setOnAction(e -> stage.close());
		List<Node> nodes = Arrays.asList(teacherViewer,title,teacherFirstName,firstNameField,teacherLastName,lastNameField,centerSelection,centerBox,createTeacherButton,cancelButton);
		placeNodes(gridpane,nodes);
		gridpane.getStylesheets().add("css/admin.css");
		return gridpane;
	}
	
	/**
	 * Places all of the nodes in the GridPane
	 */
	public void placeNodes(GridPane gridpane, List<Node> nodes) {
		placeNode(gridpane,nodes.get(0),0,0,"center",null);
		placeNodeSpan(gridpane,nodes.get(1),1,0,2,1,"center",null);
		placeNode(gridpane,nodes.get(2),0,1,"right",null);
		placeNodeSpan(gridpane,nodes.get(3),1,1,2,1,"left",null);
		placeNode(gridpane,nodes.get(4),0,2,"right",null);
		placeNodeSpan(gridpane,nodes.get(5),1,2,2,1,"left",null);
		placeNode(gridpane,nodes.get(6),0,3,"right",null);
		placeNodeSpan(gridpane,nodes.get(7),1,3,2,1,"left",null);
		placeNode(gridpane,nodes.get(8),1,4,"center",null);
		placeNode(gridpane,nodes.get(9),2,4,"center",null);
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
	 * Creates a new {@link Teacher} object
	 * @param stage
	 * @param centerBox
	 * @param textFields
	 */
	private void createTeacher(Stage stage,ChoiceBox<Center> centerBox,List<TextField> textFields) {
		String teachersFirstName = textFields.get(0).getText();
		String teachersLastName = textFields.get(1).getText();
		Center teachersCenter = centerBox.getValue();
		Thread createNewTeacher = new Thread(new CreateNewTeacher(admin,teachersFirstName,teachersLastName,teachersCenter));
		createNewTeacher.start();
		stage.close();
	}
	
}

