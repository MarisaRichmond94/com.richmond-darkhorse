package com.richmond.darkhorse.ProjectSB.gui.component;
import javafx.scene.paint.Color;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import java.util.Map;
import com.richmond.darkhorse.ProjectSB.Admin;
import com.richmond.darkhorse.ProjectSB.Center;
import com.richmond.darkhorse.ProjectSB.middleman.CreateNewDirector;
import javafx.beans.binding.Bindings;
import javafx.geometry.HPos;
import javafx.scene.control.ChoiceBox;

public class AddDirector {
	
	private Admin admin;
	
	public AddDirector(Admin admin) { 
		this.admin = admin;
	}
	
	public void display() {
		Stage stage = new Stage();
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.setTitle("Create New Director");
		
		//Grid Pane
		GridPane addDirectorLayout = new GridPane();
		addDirectorLayout.setVgap(10);
		addDirectorLayout.setHgap(10);
		ColumnConstraints columnOne = new ColumnConstraints();
		columnOne.setPercentWidth(33.3);
	    ColumnConstraints columnTwo = new ColumnConstraints();
	    columnTwo.setPercentWidth(33.3);
	    ColumnConstraints columnThree = new ColumnConstraints();
	    columnThree.setPercentWidth(33.3);
	    addDirectorLayout.getColumnConstraints().addAll(columnOne,columnTwo,columnThree);
	    addDirectorLayout.getStyleClass().add("gridpane");
		
		//Grid Pane - Icon
	    ImageView directorViewer = new ImageView();
	    Image directorIcon = new Image("director.png");
	    directorViewer.setImage(directorIcon);
	    directorViewer.setPreserveRatio(true);
	    directorViewer.setFitHeight(200);
	    Label title = new Label("Create New \nDirector");
	    title.getStyleClass().add("title");
	    
		//Grid Pane - First Name
	    Label directorFirstName = new Label("First name:");
	    directorFirstName.getStyleClass().add("label");
	    TextField firstNameField = new TextField();
	    firstNameField.setPromptText("first name");
	    firstNameField.getStyleClass().add("textfield");
	    
		//Grid Pane - Last Name
	    Label directorLastName = new Label("Last name:");
	    directorLastName.getStyleClass().add("label");
	    TextField lastNameField = new TextField();
	    lastNameField.setPromptText("last name");
	    lastNameField.getStyleClass().add("textfield");
	    
		//Grid Pane - Error Message (Hidden)
	    Label errorMessage = new Label("The center you have selected already has an active director");
	    errorMessage.getStyleClass().add("label");
	    errorMessage.setTextFill(Color.RED);

		//Grid Pane - Create Button
	    Button createDirectorButton = new Button("Create Director");
	    createDirectorButton.getStyleClass().add("button");
	    createDirectorButton.disableProperty().bind(
	    	    Bindings.isEmpty(firstNameField.textProperty())
	    	    .or(Bindings.isEmpty(lastNameField.textProperty()))
	    	);
	    
		//Grid Pane - Center Box
	    Label center = new Label("Center:");
	    center.getStyleClass().add("label");
	    ChoiceBox<Center> centerBox = new ChoiceBox<Center>();
	    if(admin.getCenters().isEmpty()) {
	    		centerBox.setDisable(true);
	    }else {
	    		Map<String,Center> centers = admin.getCenters();
	    		for(Center activeCenter : centers.values()) {
	    			centerBox.getItems().add(activeCenter);
	    		}
	    		centerBox.setValue(centerBox.getItems().get(0));
	    }
	    centerBox.setMaxWidth(500);
	    centerBox.getStyleClass().add("choicebox");
	    
		//Grid Pane - Create Button (Action)
	    createDirectorButton.setOnAction(e -> {
	    		String directorsFirstName = firstNameField.getText();
	    		String directorsLastName = lastNameField.getText();
	    		Center directorsCenter = centerBox.getValue();
	    		boolean isNull = isNull(directorsCenter);
	    		if(isNull == false) {
				addDirectorLayout.add(errorMessage, 0, 5);
	    			GridPane.setHalignment(errorMessage, HPos.CENTER);
	    			GridPane.setConstraints(errorMessage, 0, 5, 3, 1);
	    		}else {
	    			Thread createNewDirector = new Thread(new CreateNewDirector(admin,directorsFirstName,directorsLastName,directorsCenter));
	    	    		createNewDirector.start();
	    	    		stage.close();
	    		}
	    	});
	    Button cancelButton = new Button("Cancel");
	    cancelButton.getStyleClass().add("button");
	    cancelButton.setOnAction(e -> stage.close());
	    
	    //Grid Pane - Layout
	    addDirectorLayout.add(directorViewer, 0, 0);
	    GridPane.setHalignment(directorViewer, HPos.CENTER);
	    addDirectorLayout.add(title, 1, 0);
	    GridPane.setConstraints(title,1,0,2,1);
	    GridPane.setHalignment(title, HPos.CENTER);
	    addDirectorLayout.add(directorFirstName, 0, 1);
	    GridPane.setHalignment(directorFirstName, HPos.RIGHT);
	    addDirectorLayout.add(firstNameField, 1, 1);
	    GridPane.setConstraints(firstNameField,1,1,2,1);
	    addDirectorLayout.add(directorLastName, 0, 2);
	    GridPane.setHalignment(directorLastName, HPos.RIGHT);
	    addDirectorLayout.add(lastNameField, 1, 2);
	    GridPane.setConstraints(lastNameField,1,2,2,1);
	    addDirectorLayout.add(center, 0, 3);
	    GridPane.setHalignment(center, HPos.RIGHT);
	    addDirectorLayout.add(centerBox, 1, 3);
	    GridPane.setConstraints(centerBox,1,3,2,1);
	    addDirectorLayout.add(createDirectorButton, 1, 4);
	    GridPane.setHalignment(createDirectorButton, HPos.CENTER);
	    addDirectorLayout.add(cancelButton, 2, 4);
	    GridPane.setHalignment(cancelButton, HPos.CENTER);
	    addDirectorLayout.getStylesheets().add("adddirector.css");
		
		Scene createDirectorScene = new Scene(addDirectorLayout);
		stage.setScene(createDirectorScene);
		stage.showAndWait();
	}
	
	private boolean isNull(Center directorsCenter) {
		String directorID = directorsCenter.getDirectorID();
		if(directorID == null) {
			return true;
		}
		return false;
	}
	
}