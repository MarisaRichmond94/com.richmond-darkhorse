package com.richmond.darkhorse.ProjectSB.gui.component;
import com.richmond.darkhorse.ProjectSB.Director;
import com.richmond.darkhorse.ProjectSB.Student;
import com.richmond.darkhorse.ProjectSB.middleman.CreateNewContact;

import javafx.beans.binding.Bindings;
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

public class AddContact {
	
	private Student student;
	private Director director;
	
	public AddContact(Student student,Director director) {
		this.student = student;
		this.director = director;
	}
	
	public void display() {
		Stage stage = new Stage();
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.setTitle("Create New \nContact");
		
		GridPane createContactLayout = new GridPane();
		createContactLayout.setVgap(15);
		createContactLayout.setHgap(15);
		ColumnConstraints columnOne = new ColumnConstraints();
		columnOne.setPercentWidth(33.3);
	    ColumnConstraints columnTwo = new ColumnConstraints();
	    columnTwo.setPercentWidth(33.3);
	    ColumnConstraints columnThree = new ColumnConstraints();
	    columnThree.setPercentWidth(33.3);
	    createContactLayout.getColumnConstraints().addAll(columnOne,columnTwo,columnThree);
	    createContactLayout.getStyleClass().add("gridpane");
	    createContactLayout.getStylesheets().add("modifystudentinfo.css");
		
	    ImageView newContactViewer = new ImageView();
		Image createNewContact = new Image("addcontact.png");
		newContactViewer.setImage(createNewContact);
		newContactViewer.setPreserveRatio(true);
		newContactViewer.setFitHeight(250);
		Label title = new Label("    Create\nNew Contact");
		title.getStyleClass().add("subtitle");
		
		Label firstName = new Label("first name:");
		firstName.getStyleClass().add("label");
		TextField firstNameField = new TextField();
		firstNameField.setPromptText("first name");
		firstNameField.setMaxWidth(500);
		firstNameField.getStyleClass().add("textfield");
		
		Label lastName = new Label("last name:");
		lastName.getStyleClass().add("label");
		TextField lastNameField = new TextField();
		lastNameField.setPromptText("last name");
		lastNameField.setMaxWidth(500);
		lastNameField.getStyleClass().add("textfield");
		
		Label relationship = new Label("relationship:");
		relationship.getStyleClass().add("label");
		TextField relationshipField = new TextField();
		relationshipField.setPromptText("relationship to student");
		relationshipField.setMaxWidth(500);
		relationshipField.getStyleClass().add("textfield");
		
		Label workPhone = new Label("work #:");
		workPhone.getStyleClass().add("label");
		TextField workPhoneField = new TextField();
		workPhoneField.setPromptText("(XXX)-XXX-XXXX");
		workPhoneField.setMaxWidth(500);
		workPhoneField.getStyleClass().add("textfield");
		
		Label cellPhone = new Label("cell #:");
		cellPhone.getStyleClass().add("label");
		TextField cellPhoneField = new TextField();
		cellPhoneField.setPromptText("(XXX)-XXX-XXXX");
		cellPhoneField.setMaxWidth(500);
		cellPhoneField.getStyleClass().add("textfield");
		
		Label email = new Label("e-mail:");
		email.getStyleClass().add("label");
		TextField emailField = new TextField();
		emailField.setPromptText("e-mail");
		emailField.setMaxWidth(500);
		emailField.getStyleClass().add("textfield");
		
		Button createContactButton = new Button("create contact");
		createContactButton.disableProperty().bind(
	    	    Bindings.isEmpty(firstNameField.textProperty())
	    	    .or(Bindings.isEmpty(lastNameField.textProperty()))
	    	    .or(Bindings.isEmpty(relationshipField.textProperty()))
	    	    .or(Bindings.isEmpty(workPhoneField.textProperty()))
	    	    .or(Bindings.isEmpty(cellPhoneField.textProperty()))
	    	    .or(Bindings.isEmpty(emailField.textProperty()))
	    	);
		createContactButton.setOnAction(e -> {
			String firstNameFinal = firstNameField.getText();
			String lastNameFinal = lastNameField.getText();
			String relationshipToStudent = relationshipField.getText();
			String workNumber = workPhoneField.getText();
			String cellNumber = cellPhoneField.getText();
			String emailFinal = emailField.getText();
			Thread newCreateNewContact = new Thread(new CreateNewContact(director,student,firstNameFinal,lastNameFinal,relationshipToStudent,workNumber,cellNumber,emailFinal));
			newCreateNewContact.start();
			stage.close();
		});
		createContactButton.getStyleClass().add("button");
		
		Button cancelButton = new Button("cancel");
		cancelButton.setOnAction(e -> stage.close());
		cancelButton.getStyleClass().add("button");
		
		createContactLayout.add(newContactViewer,0,0);
		GridPane.setHalignment(newContactViewer,HPos.CENTER);
		createContactLayout.add(title,1,0);
		GridPane.setConstraints(title,1,0,2,1);
		GridPane.setHalignment(title,HPos.CENTER);
		createContactLayout.add(firstName,0,1);
		GridPane.setHalignment(firstName,HPos.RIGHT);
		createContactLayout.add(firstNameField,1,1);
		GridPane.setConstraints(firstNameField,1,1,2,1);
		createContactLayout.add(lastName,0,2);
		GridPane.setHalignment(lastName,HPos.RIGHT);
		createContactLayout.add(lastNameField,1,2);
		GridPane.setConstraints(lastNameField,1,2,2,1);
		createContactLayout.add(relationship,0,3);
		GridPane.setHalignment(relationship,HPos.RIGHT);
		createContactLayout.add(relationshipField,1,3);
		GridPane.setConstraints(relationshipField,1,3,2,1);
		createContactLayout.add(workPhone,0,4);
		GridPane.setHalignment(workPhone,HPos.RIGHT);
		createContactLayout.add(workPhoneField,1,4);
		GridPane.setConstraints(workPhoneField,1,4,2,1);
		createContactLayout.add(cellPhone,0,5);
		GridPane.setHalignment(cellPhone,HPos.RIGHT);
		createContactLayout.add(cellPhoneField,1,5);
		GridPane.setConstraints(cellPhoneField,1,5,2,1);
		createContactLayout.add(email,0,6);
		GridPane.setHalignment(email,HPos.RIGHT);
		createContactLayout.add(emailField,1,6);
		GridPane.setConstraints(emailField,1,6,2,1);
		createContactLayout.add(createContactButton,1,7);
		GridPane.setHalignment(createContactButton,HPos.CENTER);
		createContactLayout.add(cancelButton,2,7);
		GridPane.setHalignment(cancelButton,HPos.CENTER);
		
		Scene createContactScene = new Scene(createContactLayout);
		stage.setScene(createContactScene);
		stage.showAndWait();
	}

}
