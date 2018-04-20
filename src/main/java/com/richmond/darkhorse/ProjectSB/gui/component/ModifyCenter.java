package com.richmond.darkhorse.ProjectSB.gui.component;
import java.util.Arrays;
import java.util.List;
import com.richmond.darkhorse.ProjectSB.Admin;
import com.richmond.darkhorse.ProjectSB.Center;
import com.richmond.darkhorse.ProjectSB.middleman.ModifyExistingCenter;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class ModifyCenter implements AdminLayout{

	private Admin admin;
	private Center center;
	private String theCenterName, abbreviatedName = "", theLicenseName, theAddress, theCity, theCounty;
	
	public ModifyCenter(Admin admin,Center center) { 
		this.admin = admin;
		this.center = center;
		this.theCenterName = center.getCenterName();
		if(center.getAbbreviatedName() != null) {this.abbreviatedName = center.getAbbreviatedName();}
		this.theLicenseName = center.getLicenseName();
		this.theAddress = center.getAddress();
		this.theCity = center.getCity();
		this.theCounty = center.getCounty();
	}
	
	public void display() {
		Stage stage = new Stage();
		GridPane modifyCenterLayout = buildGridPane(stage);
		buildPopUp(stage,modifyCenterLayout,center.getCenterName());
	}
	
	/**
	 * Builds the GridPane layout
	 * @param stage - the current stage
	 * @return a GridPane layout
	 */
	private GridPane buildGridPane(Stage stage) {
		GridPane gridpane = new GridPane();
		setConstraints(gridpane,3,0,10,10,"modulargridpane");
		ImageView centerViewer = createImageWithFitHeight("images/center.png",200);
		Label title = createLabel("Modify Center","title");
		List<String> labelNames = Arrays.asList("Center name:","Abbreviated Name:","Name on license:","License number:","Address:","City:","County:");
		List<Label> labels = populateLabels(labelNames,"label");
		List<String> textFieldPrompts = Arrays.asList(center.getCenterName(),center.getAbbreviatedName(),center.getLicenseName(),center.getLicenseNumber(),center.getAddress(),center.getCity(),center.getCounty());
		List<TextField> textFields = populateTextFields(textFieldPrompts,"textfield",0);
		ImageButton writeButton = new ImageButton(createImageWithFitHeight("images/write.png",100));
	    writeButton.setOnAction(e -> write(stage,textFields));
	    writeButton.setVisible(false);
	    Label writeLabel = createLabel("write","label");
	    writeLabel.setVisible(false);
	    Label editLabel = createLabel("edit","label");
	    ImageButton editButton = new ImageButton(createImageWithFitHeight("images/edit.png",100));
	    editButton.setOnAction(e -> edit(textFields,writeButton,editButton,writeLabel,editLabel));
	    ImageButton trashButton = new ImageButton(createImageWithFitHeight("images/trash.png",100));
	    trashButton.setOnAction(e -> {
	    		admin.deleteCenter(center);
	    		stage.close();
	    	});
	    Label trashLabel = createLabel("delete","label");
	    Button cancel = new Button("cancel");
	    cancel.setOnAction(e -> stage.close());
	    cancel.getStyleClass().add("button");
	    List<Node> nodes = Arrays.asList(centerViewer,title,editButton,writeButton,trashButton,editLabel,writeLabel,trashLabel,cancel);
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
	    placeNode(gridpane,nodes.get(3),1,8,"center",null);
	    placeNode(gridpane,nodes.get(4),2,8,"center",null);
	    placeNode(gridpane,nodes.get(5),1,9,"center",null);
	    placeNode(gridpane,nodes.get(6),1,9,"center",null);
	    placeNode(gridpane,nodes.get(7),2,9,"center",null);
	    placeNodeSpan(gridpane,nodes.get(8),1,10,2,1,"center",null);
	}
	
	/**
	 * Gets the text from each text field in the List. If the text field is empty, gets the current value stored in the center and submits all necessary fields to 
	 * the thread in order to modify the existing center. Once completed, the stage closes
	 * @param stage - the current stage
	 * @param textFields - a list of text fields with valuable input 
	 */
	private void write(Stage stage,List<TextField> textFields) {
		String centerNameText = textFields.get(0).getText(), abbreviatedNameText = textFields.get(1).getText(), licenseNameText = textFields.get(2).getText(), addressText = textFields.get(3).getText(), cityText = textFields.get(4).getText(), countyText = textFields.get(5).getText();
		if(textFields.get(0).getText().trim().isEmpty()) {centerNameText = theCenterName;}
		if(textFields.get(1).getText().trim().isEmpty()) {abbreviatedNameText = abbreviatedName;}
		if(textFields.get(2).getText().trim().isEmpty()) {licenseNameText = theLicenseName;}
		if(textFields.get(3).getText().trim().isEmpty()) {addressText = theAddress;}
		if(textFields.get(4).getText().trim().isEmpty()) {cityText = theCity;}
		if(textFields.get(5).getText().trim().isEmpty()) {countyText = theCounty;}
		Thread modifyExistingCenter = new Thread(new ModifyExistingCenter(center,centerNameText,abbreviatedNameText,licenseNameText,addressText,cityText,countyText,admin));
	    	modifyExistingCenter.start();	
		stage.close();
	}
	
	/**
	 * Enters the program into edit-mode by revealing the write button and enabling all text fields for typing
	 * @param textFields - the list of text fields to be enabled
	 * @param writeButton - the button necessary for writing changes
	 * @param editButton - the button that allows the user to enter into edit mode
	 * @param writeLabel 
	 * @param editLabel
	 */
	private void edit(List<TextField> textFields,Button writeButton,Button editButton,Label writeLabel,Label editLabel) {
		textFields.get(0).setDisable(false);
		textFields.get(1).setDisable(false);
		textFields.get(2).setDisable(false);
		textFields.get(3).setDisable(false);
		textFields.get(4).setDisable(false);
		textFields.get(5).setDisable(false);
		writeButton.setVisible(true);
		writeLabel.setVisible(true);
		editButton.setVisible(false);
		editLabel.setVisible(false);
	}
	
}
