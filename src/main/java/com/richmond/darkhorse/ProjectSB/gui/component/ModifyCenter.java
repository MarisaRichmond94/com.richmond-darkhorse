package com.richmond.darkhorse.ProjectSB.gui.component;
import com.richmond.darkhorse.ProjectSB.Admin;
import com.richmond.darkhorse.ProjectSB.Center;
import com.richmond.darkhorse.ProjectSB.middleman.ModifyExistingCenter;
import javafx.geometry.HPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ModifyCenter {

	private Admin admin;
	private Center center;
	private String theCenterName, theLicenseName, theAddress, theCity, theCounty;
	
	public ModifyCenter(Admin admin,Center center) { 
		this.admin = admin;
		this.center = center;
		this.theCenterName = center.getCenterName();
		this.theLicenseName = center.getLicenseName();
		this.theAddress = center.getAddress();
		this.theCity = center.getCity();
		this.theCounty = center.getCounty();
	}
	
	public void display() {
		Stage stage = new Stage();
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.setTitle(center.getCenterName());
		
		//Grid Pane
		GridPane modifyCenterLayout = new GridPane();
		modifyCenterLayout.setVgap(10);
		modifyCenterLayout.setHgap(10);
		ColumnConstraints columnOne = new ColumnConstraints();
		columnOne.setPercentWidth(33.3);
	    ColumnConstraints columnTwo = new ColumnConstraints();
	    columnTwo.setPercentWidth(33.3);
	    ColumnConstraints columnThree = new ColumnConstraints();
	    columnThree.setPercentWidth(33.3);
	    modifyCenterLayout.getColumnConstraints().addAll(columnOne,columnTwo,columnThree);
	    modifyCenterLayout.getStyleClass().add("gridpane");
		
	    //Grid Pane - Icon
	    ImageView centerViewer = new ImageView();
	    Image centerIcon = new Image("center.png");
	    centerViewer.setImage(centerIcon);
	    centerViewer.setPreserveRatio(true);
	    centerViewer.setFitHeight(150);
	    
	    //Grid Pane - Title
	    Label title = new Label("Modify Center");
	    title.getStyleClass().add("title");
	    
	    //Grid Pane - Center Name
	    Label centerName = new Label("Center name:");
	    centerName.getStyleClass().add("label");
	    TextField nameField = new TextField();
	    nameField.setPromptText(center.getCenterName());
	    nameField.setDisable(true);
	    nameField.getStyleClass().add("textfield");
	    
	    //Grid Pane - Abbreviated Name
	    Label abbreviatedName = new Label("Abbreviated Name:");
	    abbreviatedName.getStyleClass().add("label");
	    TextField abbreviatedNameField = new TextField();
	    abbreviatedNameField.setPromptText(center.getAbbreviatedName());
	    abbreviatedNameField.setDisable(true);
	    abbreviatedNameField.getStyleClass().add("textfield");
	    
	    //Grid Pane - License Name
	    Label licenseName = new Label("Name on license:");
	    licenseName.getStyleClass().add("label");
	    TextField licenseNameField = new TextField();
	    licenseNameField.setPromptText(center.getLicenseName());
	    licenseNameField.setDisable(true);
	    licenseNameField.getStyleClass().add("textfield");
	    
	    //Grid Pane - License Number
	    Label licenseNumber = new Label("License number:");
	    licenseNumber.getStyleClass().add("label");
	    TextField licenseNumberField = new TextField();
	    licenseNumberField.setPromptText(center.getLicenseNumber());
	    licenseNumberField.setDisable(true);
	    licenseNumberField.getStyleClass().add("textfield");
	    
	    //Grid Pane - Address 
	    Label address = new Label("Address:");
	    address.getStyleClass().add("label");
	    TextField addressField = new TextField();
	    addressField.setPromptText(center.getAddress());
	    addressField.setDisable(true);
	    addressField.getStyleClass().add("textfield");
	    
	    //Grid Pane - City
	    Label city = new Label("City:");
	    city.getStyleClass().add("label");
	    TextField cityField = new TextField();
	    cityField.setPromptText(center.getCity());
	    cityField.setDisable(true);
	    cityField.getStyleClass().add("textfield");
	    
	    //Grid Pane - County
	    Label county = new Label("County:");
	    county.getStyleClass().add("label");
	    TextField countyField = new TextField();
	    countyField.setPromptText(center.getCounty());
	    countyField.setDisable(true);
	    countyField.getStyleClass().add("textfield");
	    
	    //Grid Pane - Write Button
	    ImageView writeViewer = new ImageView();
	    Image write = new Image("write.png");
	    writeViewer.setImage(write);
	    writeViewer.setPreserveRatio(true);
	    writeViewer.setFitHeight(150);
	    ImageButton writeButton = new ImageButton(writeViewer);
	    writeButton.setOnAction(e -> {
		    	String centerNameText = nameField.getText();	
		    	String abbreviatedNameText = abbreviatedNameField.getText();
		    	String licenseNameText = licenseNameField.getText();
		    	String addressText = addressField.getText();
		    	String cityText = cityField.getText();
		    	String countyText = countyField.getText();
	    		if(nameField.getText().trim().isEmpty()) {centerNameText = theCenterName;}
	    		if(licenseNameField.getText().trim().isEmpty()) {licenseNameText = theLicenseName;}
	    		if(addressField.getText().trim().isEmpty()) {addressText = theAddress;}
	    		if(cityField.getText().trim().isEmpty()) {cityText = theCity;}
	    		if(countyField.getText().trim().isEmpty()) {countyText = theCounty;}
	    		Thread modifyExistingCenter = new Thread(new ModifyExistingCenter(center,centerNameText,abbreviatedNameText,licenseNameText,addressText,cityText,countyText,admin));
	    	    	modifyExistingCenter.start();	
	    		stage.close();
	    });
	    writeButton.setVisible(false);
	    Label writeLabel = new Label("write");
	    writeLabel.setVisible(false);
	    writeLabel.getStyleClass().add("label");
	    
	    //Grid Pane - Edit Button
	    Label editLabel = new Label("edit");
	    editLabel.getStyleClass().add("label");
	    ImageView editViewer = new ImageView();
	    Image edit = new Image("edit.png");
	    editViewer.setImage(edit);
	    editViewer.setPreserveRatio(true);
	    editViewer.setFitHeight(150);
	    ImageButton editButton = new ImageButton(editViewer);
	    editButton.setOnAction(e -> {
	    		nameField.setDisable(false);
	    		abbreviatedNameField.setDisable(false);
	    		licenseNameField.setDisable(false);
	    		addressField.setDisable(false);
	    		cityField.setDisable(false);
	    		countyField.setDisable(false);
	    		writeButton.setVisible(true);
	    		writeLabel.setVisible(true);
	    		editButton.setVisible(false);
	    		editLabel.setVisible(false);
	    });
	    
	    //Grid Pane - Delete Button
	    ImageView trashViewer = new ImageView();
	    Image trash = new Image("trash.png");
	    trashViewer.setImage(trash);
	    trashViewer.setPreserveRatio(true);
	    trashViewer.setFitHeight(150);
	    ImageButton trashButton = new ImageButton(trashViewer);
	    trashButton.setOnAction(e -> {
	    		admin.deleteCenter(center);
	    		stage.close();
	    	});
	    Label trashLabel = new Label("delete");
	    trashLabel.getStyleClass().add("label");
	    
	    //Grid Pane - Cancel Button
	    Button cancel = new Button("cancel");
	    cancel.setOnAction(e -> stage.close());
	    cancel.getStyleClass().add("button");
	    
	    //Grid Pane - Layout
	    modifyCenterLayout.add(centerViewer, 0, 0);
	    GridPane.setHalignment(centerViewer, HPos.CENTER);
	    modifyCenterLayout.add(title, 1, 0);
	    GridPane.setConstraints(title,1,0,2,1);
	    GridPane.setHalignment(title, HPos.CENTER);
	    modifyCenterLayout.add(centerName, 0, 1);
	    GridPane.setHalignment(centerName, HPos.RIGHT);
	    modifyCenterLayout.add(nameField, 1, 1);
	    GridPane.setConstraints(nameField,1,1,2,1);
	    modifyCenterLayout.add(abbreviatedName, 0, 2);
	    GridPane.setHalignment(abbreviatedName, HPos.RIGHT);
	    modifyCenterLayout.add(abbreviatedNameField, 1, 2);
	    GridPane.setConstraints(abbreviatedNameField,1,2,2,1);
	    modifyCenterLayout.add(licenseName, 0, 3);
	    GridPane.setHalignment(licenseName, HPos.RIGHT);
	    modifyCenterLayout.add(licenseNameField, 1, 3);
	    GridPane.setConstraints(licenseNameField,1,3,2,1);
	    modifyCenterLayout.add(licenseNumber, 0, 4);
	    GridPane.setHalignment(licenseNumber, HPos.RIGHT);
	    modifyCenterLayout.add(licenseNumberField, 1, 4);
	    GridPane.setConstraints(licenseNumberField,1,4,2,1);
	    modifyCenterLayout.add(address, 0, 5);
	    GridPane.setHalignment(address, HPos.RIGHT);
	    modifyCenterLayout.add(addressField, 1, 5);
	    GridPane.setConstraints(addressField,1,5,2,1);
	    modifyCenterLayout.add(city, 0, 6);
	    GridPane.setHalignment(city, HPos.RIGHT);
	    modifyCenterLayout.add(cityField, 1, 6);
	    GridPane.setConstraints(cityField,1,6,2,1);
	    modifyCenterLayout.add(county, 0, 7);
	    GridPane.setHalignment(county, HPos.RIGHT);
	    modifyCenterLayout.add(countyField, 1, 7);
	    GridPane.setConstraints(countyField,1,7,2,1);
	    modifyCenterLayout.add(editButton, 1, 8);
	    GridPane.setHalignment(editButton, HPos.CENTER);
	    modifyCenterLayout.add(writeButton, 1, 8);
	    GridPane.setHalignment(writeButton, HPos.CENTER);
	    modifyCenterLayout.add(trashButton, 2, 8);
	    GridPane.setHalignment(trashButton, HPos.CENTER);
	    modifyCenterLayout.add(editLabel, 1, 9);
	    GridPane.setHalignment(editLabel, HPos.CENTER);
	    modifyCenterLayout.add(writeLabel, 1, 9);
	    GridPane.setHalignment(writeLabel, HPos.CENTER);
	    modifyCenterLayout.add(trashLabel, 2, 9);
	    GridPane.setHalignment(trashLabel, HPos.CENTER);
	    modifyCenterLayout.add(cancel, 1, 10);
	    GridPane.setHalignment(cancel, HPos.CENTER);
	    GridPane.setConstraints(cancel, 1, 10, 2, 1);
	    modifyCenterLayout.getStylesheets().add("modifycenter.css");
		
		Scene modifyCenterScene = new Scene(modifyCenterLayout);
		stage.setScene(modifyCenterScene);
		stage.showAndWait();
	}
	
}
