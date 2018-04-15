package com.richmond.darkhorse.ProjectSB.gui.component;
import com.richmond.darkhorse.ProjectSB.Admin;
import com.richmond.darkhorse.ProjectSB.middleman.CreateNewCenter;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import javafx.geometry.HPos;
import javafx.beans.binding.*;

public class AddCenter {
	 
	private Admin admin;
	
	public AddCenter(Admin admin) { 
		this.admin = admin;
	}
	
	public void display() {
		Stage stage = new Stage();
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.setTitle("Create New Center");
		
		//Grid Pane
		GridPane addCenterLayout = new GridPane();
		addCenterLayout.setVgap(10);
		addCenterLayout.setHgap(10);
		ColumnConstraints columnOne = new ColumnConstraints();
		columnOne.setPercentWidth(33.3);
	    ColumnConstraints columnTwo = new ColumnConstraints();
	    columnTwo.setPercentWidth(33.3);
	    ColumnConstraints columnThree = new ColumnConstraints();
	    columnThree.setPercentWidth(33.3);
	    addCenterLayout.getColumnConstraints().addAll(columnOne,columnTwo,columnThree);
	    addCenterLayout.getStyleClass().add("gridpane");
		
	    //Grid Pane - Icon
	    ImageView centerViewer = new ImageView();
	    Image centerIcon = new Image("center.png");
	    centerViewer.setImage(centerIcon);
	    centerViewer.setPreserveRatio(true);
	    centerViewer.setFitHeight(200);
	    
		//Grid Pane - Title
	    Label title = new Label("Create New \nCenter");
	    title.getStyleClass().add("title");
	    
	    //Grid Pane - Center Name
	    Label centerName = new Label("Center name:");
	    centerName.getStyleClass().add("label");
	    TextField nameField = new TextField();
	    nameField.setPromptText("center name");
	    nameField.getStyleClass().add("textfield");
	    
	    //Grid Pane - Abbreviated Name
	    Label abbreviatedName = new Label("Abbreviated Name:");
	    abbreviatedName.getStyleClass().add("label");
	    TextField abbreviatedNameField = new TextField();
	    abbreviatedNameField.setPromptText("abbreviated name");
	    abbreviatedNameField.getStyleClass().add("textfield");
	    
	    //Grid Pane - License Name
	    Label licenseName = new Label("Name on license:");
	    licenseName.getStyleClass().add("label");
	    TextField licenseNameField = new TextField();
	    licenseNameField.setPromptText("name on license");
	    licenseNameField.getStyleClass().add("textfield");
	    
	    //Grid Pane - License Number
	    Label licenseNumber = new Label("License number:");
	    licenseNumber.getStyleClass().add("label");
	    TextField licenseNumberField = new TextField();
	    licenseNumberField.setPromptText("license number");
	    licenseNumberField.getStyleClass().add("textfield");
	    
	    //Grid Pane - Address
	    Label address = new Label("Address:");
	    address.getStyleClass().add("label");
	    TextField addressField = new TextField();
	    addressField.setPromptText("address");
	    addressField.getStyleClass().add("textfield");
	    
	    //Grid Pane - City
	    Label city = new Label("City:");
	    city.getStyleClass().add("label");
	    TextField cityField = new TextField();
	    cityField.setPromptText("city ");
	    cityField.getStyleClass().add("textfield");
	    
	    //Grid Pane - County
	    Label county = new Label("County:");
	    county.getStyleClass().add("label");
	    TextField countyField = new TextField();
	    countyField.setPromptText("county");
	    countyField.getStyleClass().add("textfield");
	    
	    //Grid Pane - Create Button
	    Button createCenterButton = new Button("Create Center");
	    createCenterButton.getStyleClass().add("button");
	    createCenterButton.disableProperty().bind(
	    	    Bindings.isEmpty(nameField.textProperty())
	    	    .or(Bindings.isEmpty(licenseNameField.textProperty()))
	    	    .or(Bindings.isEmpty(licenseNumberField.textProperty()))
	    	    .or(Bindings.isEmpty(addressField.textProperty()))
	    	    .or(Bindings.isEmpty(cityField.textProperty()))
	    	    .or(Bindings.isEmpty(countyField.textProperty()))
	    	);
	    createCenterButton.setOnAction(e -> {
	    		boolean isAcceptable = isAcceptable(licenseNumberField,licenseNumberField.getText());
	    		if(isAcceptable == false) {
	    			Label nonIntWarning = new Label("License # must be at least 5 #'s long, and the first 4 characters must be #'s");
	    			nonIntWarning.setTextFill(Color.RED);
	    			addCenterLayout.add(nonIntWarning, 1, 9);
	    			GridPane.setHalignment(nonIntWarning, HPos.CENTER);
	    			GridPane.setConstraints(nonIntWarning, 0, 9, 3, 1);
	    		}else {
		    		String centerNameText = nameField.getText();
		    		String abbrviatedNameText = abbreviatedNameField.getText();
		    		String licenseNameText = licenseNameField.getText();
		    		String licenseNumberText = licenseNumberField.getText();
		    		String addressText = addressField.getText();
		    		String cityText = cityField.getText();
		    		String countyText = countyField.getText();
		    		Thread createNewCenter = new Thread(new CreateNewCenter(centerNameText,abbrviatedNameText,licenseNameText,licenseNumberText,addressText,cityText,countyText,admin));
		    	    	createNewCenter.start();	
		    		stage.close();
	    		}
	    });
	    Button cancelButton = new Button("Cancel");
	    cancelButton.getStyleClass().add("button");
	    cancelButton.setOnAction(e -> {
	    		stage.close();
	    });
	    
	    //Grid Pane - Layout
	    addCenterLayout.add(centerViewer, 0, 0);
	    GridPane.setHalignment(centerViewer, HPos.CENTER);
	    addCenterLayout.add(title, 1, 0);
	    GridPane.setConstraints(title,1,0,2,1);
	    GridPane.setHalignment(title, HPos.CENTER);
	    addCenterLayout.add(centerName, 0, 1);
	    GridPane.setHalignment(centerName, HPos.RIGHT);
	    addCenterLayout.add(nameField, 1, 1);
	    GridPane.setConstraints(nameField,1,1,2,1);
	    addCenterLayout.add(abbreviatedName, 0, 2);
	    GridPane.setHalignment(abbreviatedName, HPos.RIGHT);
	    addCenterLayout.add(abbreviatedNameField, 1, 2);
	    GridPane.setConstraints(abbreviatedNameField, 1, 2, 2, 1);
	    addCenterLayout.add(licenseName, 0, 3);
	    GridPane.setHalignment(licenseName, HPos.RIGHT);
	    addCenterLayout.add(licenseNameField, 1, 3);
	    GridPane.setConstraints(licenseNameField,1,3,2,1);
	    addCenterLayout.add(licenseNumber, 0, 4);
	    GridPane.setHalignment(licenseNumber, HPos.RIGHT);
	    addCenterLayout.add(licenseNumberField, 1, 4);
	    GridPane.setConstraints(licenseNumberField,1,4,2,1);
	    addCenterLayout.add(address, 0, 5);
	    GridPane.setHalignment(address, HPos.RIGHT);
	    addCenterLayout.add(addressField, 1, 5);
	    GridPane.setConstraints(addressField,1,5,2,1);
	    addCenterLayout.add(city, 0, 6);
	    GridPane.setHalignment(city, HPos.RIGHT);
	    addCenterLayout.add(cityField, 1, 6);
	    GridPane.setConstraints(cityField,1,6,2,1);
	    addCenterLayout.add(county, 0, 7);
	    GridPane.setHalignment(county, HPos.RIGHT);
	    addCenterLayout.add(countyField, 1, 7);
	    GridPane.setConstraints(countyField,1,7,2,1);
	    addCenterLayout.add(createCenterButton, 1, 8);
	    GridPane.setHalignment(createCenterButton, HPos.CENTER);
	    addCenterLayout.add(cancelButton, 2, 8);
	    GridPane.setHalignment(cancelButton, HPos.CENTER);
		addCenterLayout.getStylesheets().add("addcenter.css");
		
		Scene createCenterScene = new Scene(addCenterLayout);
		stage.setScene(createCenterScene);
		stage.showAndWait();
	}
	
	private boolean isAcceptable(TextField licenseNumberField,String licenseNumber) {
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
	    		if(status == true) {
	    			return true;
	    		}
		}catch(IndexOutOfBoundsException e) {
			return false;
		}
    		return false;
	}
	
	private boolean isNumber(String number) {
		try{
			@SuppressWarnings("unused")
			int possibleNumber = Integer.parseInt(number);
			return true;
		}catch(NumberFormatException e) {
		}
		return false;
	}
	
}
