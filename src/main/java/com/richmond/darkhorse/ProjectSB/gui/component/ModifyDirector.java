package com.richmond.darkhorse.ProjectSB.gui.component;
import java.util.Map;
import com.richmond.darkhorse.ProjectSB.Admin;
import com.richmond.darkhorse.ProjectSB.Center;
import com.richmond.darkhorse.ProjectSB.Director;
import com.richmond.darkhorse.ProjectSB.middleman.ModifyExistingDirector;
import javafx.geometry.HPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ModifyDirector {

	private Admin admin;
	private Director director;
	private Center previousCenter;
	private String theFirstName, theLastName;
		
	public ModifyDirector(Admin admin,Director director) { 
		this.admin = admin;
		this.director = director;
		this.previousCenter = director.getCenter(director.getCenterID());
		this.theFirstName = director.getFirstName();
		this.theLastName = director.getLastName();
	}
		
	public void display() {
		Stage stage = new Stage();
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.setTitle(director.getFirstName() + "\n" + director.getLastName());
		
		//Grid Pane
		GridPane modifyDirectorLayout = new GridPane();
		modifyDirectorLayout.setVgap(10);
		modifyDirectorLayout.setHgap(10);
		ColumnConstraints columnOne = new ColumnConstraints();
		columnOne.setPercentWidth(33.3);
		ColumnConstraints columnTwo = new ColumnConstraints();
		columnTwo.setPercentWidth(33.3);
		ColumnConstraints columnThree = new ColumnConstraints();
		columnThree.setPercentWidth(33.3);
		modifyDirectorLayout.getColumnConstraints().addAll(columnOne,columnTwo,columnThree);
	    modifyDirectorLayout.getStyleClass().add("gridpane");
		
		//Grid Pane - Icon
		ImageView directorViewer = new ImageView();
		Image directorIcon = new Image("director.png");
		directorViewer.setImage(directorIcon);
		directorViewer.setPreserveRatio(true);
		directorViewer.setFitHeight(150);
		
		//Grid Pane - Title
		Label title = new Label("Modify\n Director");
		title.getStyleClass().add("title");
		
		//Grid Pane - First Name
		Label firstName = new Label("First name:");
		firstName.getStyleClass().add("label");
		TextField firstNameField = new TextField();
		firstNameField.setPromptText(director.getFirstName());
		firstNameField.setDisable(true);
		firstNameField.getStyleClass().add("textfield");
		
		//Grid Pane - Last Name
		Label lastName = new Label("Last name:");
		lastName.getStyleClass().add("label");
		TextField lastNameField = new TextField();
		lastNameField.setPromptText(director.getLastName());
		lastNameField.setDisable(true);
		lastNameField.getStyleClass().add("textfield");
		
		//GridPane - Title Change
		Label titleChange = new Label("Title (if applicable):");
		titleChange.getStyleClass().add("label");
		ChoiceBox<String> titleBox = new ChoiceBox<String>();
		titleBox.getItems().add("Director");
		titleBox.getItems().add("Teacher");
		titleBox.setValue(titleBox.getItems().get(0));
		titleBox.setMaxWidth(500);
		titleBox.setDisable(true);
		titleBox.getStyleClass().add("choicebox");
		
		//Grid Pane - Error Message (Hidden)
	    Label errorMessage = new Label("The center you have selected already has an active director");
	    errorMessage.getStyleClass().add("label");
	    errorMessage.setTextFill(Color.RED);
		
		//Grid Pane - Center Box
		Label theCenter = new Label("Center:");
		theCenter.getStyleClass().add("label");
		ChoiceBox<Center> centerBox = new ChoiceBox<Center>();
	    if(admin.getCenters().isEmpty()) {
	    		centerBox.setDisable(true);
	    }else {
	    		Map<String,Center> centers = admin.getCenters();
	    		for(Center activeCenter : centers.values()) {
	    			centerBox.getItems().add(activeCenter);
	    			if(activeCenter == previousCenter) {
	    				centerBox.setValue(activeCenter);
	    			}
	    		}
	    }
	    centerBox.setMaxWidth(500);
	    centerBox.setDisable(true);
	    centerBox.getStyleClass().add("choicebox");
		
		//Grid Pane - Write Button
		ImageView writeViewer = new ImageView();
		Image write = new Image("write.png");
		writeViewer.setImage(write);
		writeViewer.setPreserveRatio(true);
		writeViewer.setFitHeight(150);
		ImageButton writeButton = new ImageButton(writeViewer);
		writeButton.setOnAction(e -> {
		    	String firstNameText = firstNameField.getText();
		    	if(firstNameField.getText().trim().isEmpty()) {firstNameText = theFirstName;}
			String lastNameText = lastNameField.getText();
			if(lastNameField.getText().trim().isEmpty()) {lastNameText = theLastName;}
			String newTitle = titleBox.getValue();
			Center selectedCenter = centerBox.getValue();
			boolean isNull = isNull(selectedCenter);
	    		if(isNull == false) {
	    			writeButton.setDisable(true);
	    			modifyDirectorLayout.add(errorMessage, 0, 9);
	    			GridPane.setHalignment(errorMessage, HPos.CENTER);
	    			GridPane.setConstraints(errorMessage, 0, 9, 3, 1);
	    		}
		    Thread modifyExistingCenter = new Thread(new ModifyExistingDirector(director,firstNameText,lastNameText,newTitle,selectedCenter,admin));
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
		    	firstNameField.setDisable(false);
		    	lastNameField.setDisable(false);
		    	centerBox.setDisable(false);
		    	titleBox.setDisable(false);
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
			admin.deleteDirector(director);
			stage.close();
		});
		Label trashLabel = new Label("delete");
		trashLabel.getStyleClass().add("label");
		
		//Grid Pane - Cancel Button
		Button cancel = new Button("cancel");
		cancel.setOnAction(e -> stage.close());
		cancel.getStyleClass().add("button");
		
		//Grid Pane - Layout
		modifyDirectorLayout.add(directorViewer, 0, 0);
		GridPane.setHalignment(directorViewer, HPos.CENTER);
		modifyDirectorLayout.add(title, 1, 0);
		GridPane.setConstraints(title,1,0,2,1);
		GridPane.setHalignment(title, HPos.CENTER);
		modifyDirectorLayout.add(firstName, 0, 1);
		GridPane.setHalignment(firstName, HPos.RIGHT);
		modifyDirectorLayout.add(firstNameField, 1, 1);
		GridPane.setConstraints(firstNameField,1,1,2,1);
		modifyDirectorLayout.add(lastName, 0, 2);
		GridPane.setHalignment(lastName, HPos.RIGHT);
		modifyDirectorLayout.add(lastNameField, 1, 2);
		GridPane.setConstraints(lastNameField,1,2,2,1);
		modifyDirectorLayout.add(titleChange, 0, 3);
		GridPane.setHalignment(titleChange, HPos.RIGHT);
		modifyDirectorLayout.add(titleBox, 1, 3);
		GridPane.setConstraints(titleBox, 1, 3, 2, 1);
		modifyDirectorLayout.add(theCenter, 0, 4);
		GridPane.setHalignment(theCenter, HPos.RIGHT);
		modifyDirectorLayout.add(centerBox, 1, 4);
		GridPane.setConstraints(centerBox,1,4,2,1);
		modifyDirectorLayout.add(editButton, 1, 5);
		GridPane.setHalignment(editButton, HPos.CENTER);
		modifyDirectorLayout.add(writeButton, 1, 5);
		GridPane.setHalignment(writeButton, HPos.CENTER);
		modifyDirectorLayout.add(trashButton, 2, 5);
		GridPane.setHalignment(trashButton, HPos.CENTER);
		modifyDirectorLayout.add(editLabel, 1, 6);
		GridPane.setHalignment(editLabel, HPos.CENTER);
		modifyDirectorLayout.add(writeLabel, 1, 6);
		GridPane.setHalignment(writeLabel, HPos.CENTER);
		modifyDirectorLayout.add(trashLabel, 2, 6);
		GridPane.setHalignment(trashLabel, HPos.CENTER);
		modifyDirectorLayout.add(cancel, 1, 8);
		GridPane.setHalignment(cancel, HPos.CENTER);
		GridPane.setConstraints(cancel, 1, 8, 2, 1);
		modifyDirectorLayout.getStylesheets().add("modifycenter.css");
			
		Scene modifyDirectorScene = new Scene(modifyDirectorLayout);
		stage.setScene(modifyDirectorScene);
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
