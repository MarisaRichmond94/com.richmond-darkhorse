package com.richmond.darkhorse.ProjectSB.gui.component;
import javafx.scene.paint.Color;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import com.richmond.darkhorse.ProjectSB.Admin;
import com.richmond.darkhorse.ProjectSB.Center;
import com.richmond.darkhorse.ProjectSB.middleman.CreateNewDirector;
import javafx.beans.binding.Bindings;
import javafx.scene.control.ChoiceBox;

public class AddDirector implements AdminLayout{
	
	private Admin admin;
	
	public AddDirector(Admin admin) { 
		this.admin = admin;
	}
	
	public void display() {
		Stage stage = new Stage();
		GridPane addDirectorLayout = buildGridPane(stage);
		buildPopUp(stage,addDirectorLayout,"Create New Director");
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
	    Label title = createLabel("Create New \nDirector","title");
	    Label directorFirstName = createLabel("First name:","label");
	    TextField firstNameField = createTextField("first name","textfield",650);
	    Label directorLastName = createLabel("Last name:","label");
	    TextField lastNameField = createTextField("last name","textfield",650);
	    Label errorMessage = createLabel("The center you have selected already has an active director","label");
	    errorMessage.setTextFill(Color.RED);
	    Button createDirectorButton = new Button("Create Director");
	    createDirectorButton.getStyleClass().add("button");
	    createDirectorButton.disableProperty().bind(
	    	    Bindings.isEmpty(firstNameField.textProperty())
	    	    .or(Bindings.isEmpty(lastNameField.textProperty()))
	    	);
	    Label center = createLabel("Center:","label");
	    ChoiceBox<Center> centerBox = new ChoiceBox<Center>();
	    if(admin.getCenters().isEmpty()) {centerBox.setDisable(true);
	    }else {
	    		Map<String,Center> centers = admin.getCenters();
	    		for(Center activeCenter : centers.values()) {centerBox.getItems().add(activeCenter);}
	    		centerBox.setValue(centerBox.getItems().get(0));
	    }
	    centerBox.getStyleClass().add("choicebox");
	    createDirectorButton.setOnAction(e -> {
	    		String directorsFirstName = firstNameField.getText(), directorsLastName = lastNameField.getText();
	    		Center directorsCenter = centerBox.getValue();
	    		boolean isNull = isNull(directorsCenter);
	    		if(isNull == false) {placeNodeSpan(gridpane,errorMessage,0,5,3,1,"center",null);}
	    		else {
	    			Thread createNewDirector = new Thread(new CreateNewDirector(admin,directorsFirstName,directorsLastName,directorsCenter));
	    	    		createNewDirector.start();
	    	    		stage.close();
	    		}
	    	});
	    Button cancelButton = new Button("Cancel");
	    cancelButton.getStyleClass().add("button");
	    cancelButton.setOnAction(e -> stage.close());
	    List<Node> nodes = new ArrayList<>();
	    nodes.addAll(Arrays.asList(directorViewer,title,directorFirstName,firstNameField,directorLastName,lastNameField,center,centerBox,createDirectorButton,cancelButton));
	    placeNodes(gridpane,nodes);
	    gridpane.getStylesheets().add("css/admin.css");
	    return gridpane;
	}
	
	/**
	 * Places all of the nodes in the list in the given GridPane
	 * @param gridpane - GridPane layout
	 * @param nodes - a list of nodes to be added to the GridPane
	 */
	public void placeNodes(GridPane gridpane,List<Node> nodes) {
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
	 * Checks to see if the @{Center} is null
	 * @param directorsCenter - the @{Center} being checked
	 * @return true if the @{Center} is null and false if it is NOT null
	 */
	private boolean isNull(Center directorsCenter) {
		String directorID = directorsCenter.getDirectorID();
		if(directorID == null) {return true;}
		return false;
	}
	
}