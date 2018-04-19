package com.richmond.darkhorse.ProjectSB.gui.component;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import com.richmond.darkhorse.ProjectSB.Admin;
import com.richmond.darkhorse.ProjectSB.middleman.CreateNewCenter;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import javafx.beans.binding.*;
import javafx.scene.Node;

public class AddCenter implements AdminLayout {
	 
	private Admin admin;
	
	public AddCenter(Admin admin) { 
		this.admin = admin;
	}
	
	public void display() {
		Stage stage = new Stage();
		GridPane addCenterLayout = buildGridPane(stage);
		buildPopUp(stage,addCenterLayout,"Create New Center");
	}
	
	/**
	 * Builds the layout for AddCenter
	 * @param stage - the current stage
	 * @return a GridPane layout
	 */
	private GridPane buildGridPane(Stage stage) {
		GridPane gridpane = new GridPane();
		setConstraints(gridpane,3,0,10,10,"modulargridpane");
		ImageView centerViewer = createImageWithFitHeight("images/center.png",200);
		Label title = createLabel("Create New \nCenter","title");
		List<String> labelNames = new ArrayList<>();
		labelNames.addAll(Arrays.asList("Center name:","Abbreviated Name:","Name on license:","License number:","Address:","City:","County:"));
		List<Label> labels = populateLabels(labelNames,"label");
		List<String> textFieldPrompts = new ArrayList<>();
		textFieldPrompts.addAll(Arrays.asList("center name","abbreviated name","name on license","license number","address","city","county"));
		List<TextField> textFields = populateTextFields(textFieldPrompts,"textfield",0);
	    Button createCenterButton = new Button("Create Center");
	    createCenterButton.getStyleClass().add("button");
	    createCenterButton.disableProperty().bind(
	    	    Bindings.isEmpty(textFields.get(0).textProperty())
	    	    .or(Bindings.isEmpty(textFields.get(2).textProperty()))
	    	    .or(Bindings.isEmpty(textFields.get(3).textProperty()))
	    	    .or(Bindings.isEmpty(textFields.get(4).textProperty()))
	    	    .or(Bindings.isEmpty(textFields.get(5).textProperty()))
	    	    .or(Bindings.isEmpty(textFields.get(6).textProperty()))
	    	);
	    createCenterButton.setOnAction(e -> {
	    		boolean isAcceptable = isAcceptable(textFields.get(3).getText());
	    		if(isAcceptable == false) {
	    			Label nonIntWarning = new Label("License # must be at least 5 #'s long, and the first 4 characters must be #'s");
	    			nonIntWarning.setTextFill(Color.RED);
	    			placeNodeSpan(gridpane,nonIntWarning,0,9,3,1,"center",null);
	    		}else {
		    		String centerNameText = textFields.get(0).getText(), abbrviatedNameText = textFields.get(1).getText(), licenseNameText = textFields.get(2).getText(), licenseNumberText = textFields.get(3).getText(), addressText = textFields.get(4).getText(), cityText = textFields.get(5).getText(), countyText = textFields.get(6).getText();
		    		Thread createNewCenter = new Thread(new CreateNewCenter(centerNameText,abbrviatedNameText,licenseNameText,licenseNumberText,addressText,cityText,countyText,admin));
		    	    	createNewCenter.start();	
		    		stage.close();
	    		}
	    });
	    Button cancelButton = new Button("Cancel");
	    cancelButton.getStyleClass().add("button");
	    cancelButton.setOnAction(e -> stage.close());
	    List<Node> nodes = new ArrayList<>();
	    nodes.addAll(Arrays.asList(centerViewer,title,createCenterButton,cancelButton));
	    placeNodes(gridpane,nodes);
	    populateGridPane(gridpane,labels,textFields,1);
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
	    placeNode(gridpane,nodes.get(2),1,8,"center",null);
	    placeNode(gridpane,nodes.get(3),2,8,"center",null);
	}
	
	/**
	 * Determines whether or not the provided license number is at least four-digits long and contains only number literals (for the first 4 digits) by parsing the
	 * provided number String and passing the first four digits through a number check method (isNumber)
	 * @param licenseNumber - the license number provided by the user 
	 * @return true if the provided license number is at least 4 digits long and false if the try clause throws an indexOutOfBoundsException 
	 */
	private boolean isAcceptable(String licenseNumber) {
		try{
			int a = Character.digit(licenseNumber.charAt(0), 10);
			String aString = Integer.toString(a);
		    	int b = Character.digit(licenseNumber.charAt(1), 10);
		   	String bString = Integer.toString(b);
		   	int c = Character.digit(licenseNumber.charAt(2), 10);
		    	String cString = Integer.toString(c);
		    	int d = Character.digit(licenseNumber.charAt(3), 10);
		    	String dString = Integer.toString(d);
	    		String number = aString + bString + cString + dString;
	    		boolean status = isNumber(number);
	    		if(status == true) {return true;}
		}catch(IndexOutOfBoundsException e) {return false;}
    		return false;
	}
	
	/**
	 * Checks to see if the passed String contains only numbers
	 * @param number - String potentially containing numbers
	 * @return true if the String only contains numbers and false if the String contains any Characters
	 */
	private boolean isNumber(String number) throws NumberFormatException{
		try{
			@SuppressWarnings("unused")
			int possibleNumber = Integer.parseInt(number);
			return true;
		}catch(NumberFormatException e) {return false;}
	}
	
}
