package com.richmond.darkhorse.ProjectSB.gui.component;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import com.richmond.darkhorse.ProjectSB.Admin;
import com.richmond.darkhorse.ProjectSB.Center;
import com.richmond.darkhorse.ProjectSB.Director;
import com.richmond.darkhorse.ProjectSB.middleman.ModifyExistingDirector;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class ModifyDirector implements AdminLayout{

	private Admin admin;
	private Director director;
	private Center previousCenter;
	private String theFirstName,theLastName;
		
	public ModifyDirector(Admin admin,Director director) { 
		this.admin = admin;
		this.director = director;
		this.previousCenter = director.getCenter(director.getCenterID());
		this.theFirstName = director.getFirstName();
		this.theLastName = director.getLastName();
	}
		
	public void display() {
		Stage stage = new Stage();
		GridPane modifyDirectorLayout = buildGridPane(stage);
		String directorName = director.getFirstName() + "\n" + director.getLastName();
		buildPopUp(stage,modifyDirectorLayout,directorName);
	}
	
	/**
	 * Builds the GridPane to be used for the layout of the scene
	 * @param stage - the current stage
	 * @return a GridPane
	 */
	private GridPane buildGridPane(Stage stage) {
		GridPane gridpane = new GridPane();
		setConstraints(gridpane,3,0,10,10,"modulargridpane");
		ImageView directorViewer = createImageWithFitHeight("images/director.png",200);
		Label title = createLabel("Modify Director","title");
		Label firstName = createLabel("First name:","label");
		TextField firstNameField = createTextField(director.getFirstName(),"textfield",650);
		Label lastName = createLabel("Last name:","label");
		TextField lastNameField = createTextField(director.getLastName(),"textfield",650);
		Label titleChange = createLabel("Title (if applicable)","label");
		ChoiceBox<String> titleBox = new ChoiceBox<String>();
		titleBox.getItems().addAll("Director","Teacher");
		titleBox.setValue(titleBox.getItems().get(0));
		titleBox.setDisable(true);
		titleBox.getStyleClass().add("choice-box");
		Label errorMessage = createLabel("The center you have selected already has an active director","label");
		errorMessage.setTextFill(Color.RED);
		Label theCenter = createLabel("Center:","label");
		ChoiceBox<Center> centerBox = new ChoiceBox<Center>();
		if(admin.getCenters().isEmpty()) {centerBox.setDisable(true);
		}else {
			Map<String,Center> centers = admin.getCenters();
			for(Center activeCenter : centers.values()) {
			centerBox.getItems().add(activeCenter);
			    	if(activeCenter == previousCenter) {centerBox.setValue(activeCenter);	}
			}
		}
		centerBox.setDisable(true);
		centerBox.getStyleClass().add("choice-box");
		ImageButton writeButton = new ImageButton(createImageWithFitHeight("images/write.png",100));
		List<TextField> textFields = Arrays.asList(firstNameField,lastNameField);
		writeButton.setOnAction(e -> write(stage,gridpane,writeButton,errorMessage,titleBox,centerBox,textFields));
		writeButton.setVisible(false);
		Label writeLabel = createLabel("write","label");
		writeLabel.setVisible(false);
		Label editLabel = createLabel("edit","label");
		ImageButton editButton = new ImageButton(createImageWithFitHeight("images/edit.png",100));
		List<Node> nodes = Arrays.asList(firstNameField,lastNameField,centerBox,titleBox,writeButton,writeLabel,editButton,editLabel);
		editButton.setOnAction(e -> edit(nodes));
		ImageButton trashButton = new ImageButton(createImageWithFitHeight("images/trash.png",100));
		trashButton.setOnAction(e -> {
			admin.deleteDirector(director);
			stage.close();
		});
		Label trashLabel = createLabel("delete","label");
		Button cancel = createButton("cancel",null,0,0,0);
		cancel.setOnAction(e -> stage.close());
		List<Node> allNodes = Arrays.asList(directorViewer,title,firstName,firstNameField,lastName,lastNameField,titleChange,titleBox,theCenter,centerBox,editButton,writeButton,trashButton,editLabel,writeLabel,trashLabel,cancel);
		placeNodes(gridpane,allNodes);
		gridpane.getStylesheets().add("css/admin.css");
		return gridpane;
	}
	
	/**
	 * Writes changes
	 * @param stage - the current stage
	 * @param gridpane - the current GridPane layout
	 * @param writeButton - the button
	 * @param errorMessage - message to be displayed if an error occurs
	 * @param titleBox
	 * @param centerBox
	 * @param textFields - a list of text fields 
	 */
	private void write(Stage stage,GridPane gridpane,Button writeButton,Label errorMessage,ChoiceBox<String> titleBox,ChoiceBox<Center> centerBox,List<TextField> textFields) {
		String firstNameText = textFields.get(0).getText(), lastNameText = textFields.get(1).getText();;
		if(textFields.get(0).getText().trim().isEmpty()) {firstNameText = theFirstName;}
		if(textFields.get(1).getText().trim().isEmpty()) {lastNameText = theLastName;}
		String newTitle = titleBox.getValue();
		Center selectedCenter = centerBox.getValue();
		boolean isNull = isNull(selectedCenter);
		if(isNull == false) {
			writeButton.setDisable(true);
			placeNodeSpan(gridpane,errorMessage,0,9,3,1,"center",null);
		}
		Thread modifyExistingCenter = new Thread(new ModifyExistingDirector(director,firstNameText,lastNameText,newTitle,selectedCenter,admin));
		modifyExistingCenter.start();	
		stage.close();
	}
	
	/**
	 * Disables and enables all necessary fields
	 * @param nodes - the nodes to be enabled and disabled
	 */
	private void edit(List<Node> nodes) {
		nodes.get(0).setDisable(false);
		nodes.get(1).setDisable(false);
		nodes.get(2).setDisable(false);
		nodes.get(3).setDisable(false);
		nodes.get(4).setVisible(true);
		nodes.get(5).setVisible(true);
		nodes.get(6).setVisible(false);
		nodes.get(7).setVisible(false);
	}
	
	/**
	 * Places all of the nodes in the list in the given GridPane
	 * @param gridpane - GridPane layout
	 * @param nodes - a list of nodes to be added to the GridPane
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
		placeNode(gridpane,nodes.get(8),0,4,"right",null);	
		placeNodeSpan(gridpane,nodes.get(9),1,4,2,1,"left",null);
		placeNode(gridpane,nodes.get(10),1,5,"center",null);	
		placeNode(gridpane,nodes.get(11),1,5,"center",null);	
		placeNode(gridpane,nodes.get(12),2,5,"center",null);	
		placeNode(gridpane,nodes.get(13),1,6,"center",null);	
		placeNode(gridpane,nodes.get(14),1,6,"center",null);	
		placeNode(gridpane,nodes.get(15),2,6,"center",null);	
		placeNodeSpan(gridpane,nodes.get(16),1,8,2,1,"center",null);	
	}
	
	/**
	 * Checks to see if the given object is null
	 * @param directorsCenter - given object
	 * @return true if the object is null and false if it is NOT null
	 */
	private boolean isNull(Center directorsCenter) {
		String directorID = directorsCenter.getDirectorID();
		if(directorID == null) {return true;}
		return false;
	}
		
}
