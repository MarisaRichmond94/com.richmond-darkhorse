package com.richmond.darkhorse.ProjectSB.gui.component;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import com.richmond.darkhorse.ProjectSB.Admin;
import com.richmond.darkhorse.ProjectSB.Center;
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
		
	public ModifyTeacher(Admin admin,Teacher teacher) { 
		this.admin = admin;
		this.teacher = teacher;
		this.theFirstName = teacher.getFirstName();
		this.theLastName = teacher.getLastName();
		this.previousCenter = teacher.getCenter(teacher.getCenterID());
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
		Label activeDirectorWarning = createLabel("The center you have selected already has an active director","label");
		activeDirectorWarning.setTextFill(Color.RED);
		activeDirectorWarning.setVisible(false);
		List<Node> nodes = Arrays.asList(firstNameField,lastNameField,titleBox,centerBox);
		ImageButton writeButton = new ImageButton(createImageWithFitHeight("images/write.png",100));
		List<TextField> textFields = Arrays.asList(firstNameField,lastNameField);
	    writeButton.setOnAction(e -> write(stage,textFields,centerBox,titleBox,activeDirectorWarning));
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
		List<Node> allNodes = Arrays.asList(activeDirectorWarning,teacherViewer,title,teacherFirstName,firstNameField,teacherLastName,lastNameField,titleChange,titleBox,centerSelection,centerBox,editButton,writeButton,trashButton,editLabel,writeLabel,trashLabel,cancel);
		placeNodes(gridpane,allNodes);
		gridpane.getStylesheets().add("css/admin.css");
		return gridpane;
	}
	
	/**
	 * Places all of the nodes in the GridPane
	 */
	public void placeNodes(GridPane gridpane, List<Node> nodes) {
		placeNodeSpan(gridpane,nodes.get(0),0,10,3,1,"center",null);
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
		placeNode(gridpane,nodes.get(11),1,6,"center",null);
		placeNode(gridpane,nodes.get(12),1,6,"center",null);
		placeNode(gridpane,nodes.get(13),2,6,"center",null);
		placeNode(gridpane,nodes.get(14),1,7,"center",null);
		placeNode(gridpane,nodes.get(15),1,7,"center",null);
		placeNode(gridpane,nodes.get(16),2,7,"center",null);
		placeNodeSpan(gridpane,nodes.get(17),1,9,2,1,"center",null);
	}
	
	/**
	 * Writes changes
	 * @param stage
	 * @param textFields
	 * @param centerBox
	 * @param titleBox
	 * @param activeDirectorWarning
	 */
	private void write(Stage stage,List<TextField> textFields,ChoiceBox<Center> centerBox,ChoiceBox<String> titleBox,Label activeDirectorWarning) {
		String teachersFirstName = textFields.get(0).getText(), teachersLastName = textFields.get(1).getText();
		if(textFields.get(0).getText().trim().isEmpty()) {teachersFirstName = theFirstName;}
		if(textFields.get(1).getText().trim().isEmpty()) {teachersLastName = theLastName;}
		Center teachersCenter = centerBox.getValue();
		String newTitle = titleBox.getValue();
		if(newTitle.equals("Teacher")) {
			Thread modifyExistingTeacher = new Thread(new ModifyExistingTeacher(admin,teacher,teachersFirstName,teachersLastName,newTitle,teachersCenter));
    			modifyExistingTeacher.start();
    			stage.close();
    		}else{
	    		if(newTitle.equals("Director") && teachersCenter.getDirectorID() != null) {activeDirectorWarning.setVisible(true);}
	    		else {
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
		
}
