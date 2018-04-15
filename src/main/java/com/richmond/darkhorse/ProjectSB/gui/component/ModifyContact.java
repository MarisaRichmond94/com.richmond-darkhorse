package com.richmond.darkhorse.ProjectSB.gui.component;
import com.richmond.darkhorse.ProjectSB.Contact;
import com.richmond.darkhorse.ProjectSB.Director;
import com.richmond.darkhorse.ProjectSB.Student;
import com.richmond.darkhorse.ProjectSB.middleman.ModifyExistingContact;
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

public class ModifyContact {
	
	private Director director;
	private Student student;
	private Contact contact;
	
	public ModifyContact(Director director,Student student,Contact contact) {
		this.director = director;
		this.student = student;
		this.contact = contact;
	}
	
	public void display() {
		Stage stage = new Stage();
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.setTitle(contact.getFirstName() + " " + contact.getLastName());
		
		GridPane modifyContactLayout = new GridPane();
		modifyContactLayout.setVgap(15);
		modifyContactLayout.setHgap(15);
		ColumnConstraints columnOne = new ColumnConstraints();
		columnOne.setPercentWidth(33.3);
	    ColumnConstraints columnTwo = new ColumnConstraints();
	    columnTwo.setPercentWidth(33.3);
	    ColumnConstraints columnThree = new ColumnConstraints();
	    columnThree.setPercentWidth(33.3);
	    modifyContactLayout.getColumnConstraints().addAll(columnOne,columnTwo,columnThree);
	    modifyContactLayout.getStyleClass().add("gridpane");
	    modifyContactLayout.getStylesheets().add("modifystudentinfo.css");
		
	    ImageView modifyContactViewer = new ImageView();
		Image modifyContact = new Image("newicon.png");
		modifyContactViewer.setImage(modifyContact);
		modifyContactViewer.setPreserveRatio(true);
		modifyContactViewer.setFitHeight(250);
		Label title = new Label("Modify Contact");
		title.getStyleClass().add("subtitle");
		
		Label firstName = new Label("first name:");
		firstName.getStyleClass().add("label");
		TextField firstNameField = new TextField();
		firstNameField.setPromptText("" + contact.getFirstName() + "");
		firstNameField.setMaxWidth(500);
		firstNameField.setDisable(true);
		firstNameField.getStyleClass().add("textfield");
		
		Label lastName = new Label("last name:");
		lastName.getStyleClass().add("label");
		TextField lastNameField = new TextField();
		lastNameField.setPromptText("" + contact.getLastName() + "");
		lastNameField.setMaxWidth(500);
		lastNameField.setDisable(true);
		lastNameField.getStyleClass().add("textfield");
		
		Label relationship = new Label("relationship:");
		relationship.getStyleClass().add("label");
		TextField relationshipField = new TextField();
		relationshipField.setPromptText("" + contact.getRelationshipToStudent() + "");
		relationshipField.setMaxWidth(500);
		relationshipField.setDisable(true);
		relationshipField.getStyleClass().add("textfield");
		
		Label workPhone = new Label("work #:");
		workPhone.getStyleClass().add("label");
		TextField workPhoneField = new TextField();
		workPhoneField.setPromptText("" + contact.getWorkNumber() + "");
		workPhoneField.setMaxWidth(500);
		workPhoneField.setDisable(true);
		workPhoneField.getStyleClass().add("textfield");
		
		Label cellPhone = new Label("cell #:");
		cellPhone.getStyleClass().add("label");
		TextField cellPhoneField = new TextField();
		cellPhoneField.setPromptText("" + contact.getCellNumber() + "");
		cellPhoneField.setMaxWidth(500);
		cellPhoneField.setDisable(true);
		cellPhoneField.getStyleClass().add("textfield");
		
		Label email = new Label("e-mail:");
		email.getStyleClass().add("label");
		TextField emailField = new TextField();
		emailField.setPromptText("" + contact.getEmail() + "");
		emailField.setMaxWidth(500);
		emailField.setDisable(true);
		emailField.getStyleClass().add("textfield");
		
		//Grid Pane - Edit Button
	    Label editLabel = new Label("edit");
	    editLabel.getStyleClass().add("label");
	    ImageView editViewer = new ImageView();
	    Image edit = new Image("edit.png");
	    editViewer.setImage(edit);
	    editViewer.setPreserveRatio(true);
	    editViewer.setFitHeight(150);
	    ImageButton editButton = new ImageButton(editViewer);
		
		//Grid Pane - Write Button
	    ImageView writeViewer = new ImageView();
	    Image write = new Image("write.png");
	    writeViewer.setImage(write);
	    writeViewer.setPreserveRatio(true);
	    writeViewer.setFitHeight(150);
	    ImageButton writeButton = new ImageButton(writeViewer);
	    writeButton.setOnAction(e -> {
	    		String firstNameFinal = firstNameField.getText();
	    		String lastNameFinal = lastNameField.getText();
	    		String relationshipToStudent = relationshipField.getText();
			String workNumber = workPhoneField.getText();
			String cellNumber = cellPhoneField.getText();
			String emailFinal = emailField.getText();
	    		if(firstNameField.getText().trim().isEmpty()){firstNameFinal = contact.getFirstName();}  
	    		if(lastNameField.getText().trim().isEmpty()){lastNameFinal = contact.getLastName();} 
	    		if(relationshipField.getText().trim().isEmpty()){relationshipToStudent = contact.getRelationshipToStudent();} 
	    		if(workPhoneField.getText().trim().isEmpty()){workNumber = contact.getWorkNumber();} 
	    		if(cellPhoneField.getText().trim().isEmpty()){cellNumber = contact.getCellNumber();} 
	    		if(emailField.getText().trim().isEmpty()){emailFinal = contact.getEmail();}  
	    		Thread modifyExistingContact = new Thread(new ModifyExistingContact(director,contact,student,firstNameFinal,lastNameFinal,relationshipToStudent,workNumber,cellNumber,emailFinal));
	    		modifyExistingContact.start();
	    		stage.close();
	    });
	    writeButton.setVisible(false);
	    Label writeLabel = new Label("write");
	    writeLabel.setVisible(false);
	    writeLabel.getStyleClass().add("label");
	    
	    editButton.setOnAction(e -> {
	    		writeButton.setVisible(true);
	    		writeLabel.setVisible(true);
	    		editButton.setVisible(false);
	    		editLabel.setVisible(false);
	    		firstNameField.setDisable(false);
	    		lastNameField.setDisable(false);
	    		relationshipField.setDisable(false);
	    		workPhoneField.setDisable(false);
	    		cellPhoneField.setDisable(false);
	    		emailField.setDisable(false);
	    });
	    
		//Grid Pane - Delete Button
	    ImageView trashViewer = new ImageView();
	    Image trash = new Image("trash.png");
	    trashViewer.setImage(trash);
	    trashViewer.setPreserveRatio(true);
	    trashViewer.setFitHeight(150);
	    ImageButton trashButton = new ImageButton(trashViewer);
	    trashButton.setOnAction(e -> {
	    		director.deleteStudentContact(student,contact);
	    		stage.close();
	    });
	    Label trashLabel = new Label("delete");
	    trashLabel.getStyleClass().add("label");
	    
		//Grid Pane - Cancel Button
	    Button cancel = new Button("cancel");
	    cancel.setOnAction(e -> stage.close());
	    cancel.getStyleClass().add("button");
		
		modifyContactLayout.add(modifyContactViewer,0,0);
		GridPane.setHalignment(modifyContactViewer,HPos.CENTER);
		modifyContactLayout.add(title,1,0);
		GridPane.setConstraints(title,1,0,2,1);
		GridPane.setHalignment(title,HPos.CENTER);
		modifyContactLayout.add(firstName,0,1);
		GridPane.setHalignment(firstName,HPos.RIGHT);
		modifyContactLayout.add(firstNameField,1,1);
		GridPane.setConstraints(firstNameField,1,1,2,1);
		modifyContactLayout.add(lastName,0,2);
		GridPane.setHalignment(lastName,HPos.RIGHT);
		modifyContactLayout.add(lastNameField,1,2);
		GridPane.setConstraints(lastNameField,1,2,2,1);
		modifyContactLayout.add(relationship,0,3);
		GridPane.setHalignment(relationship,HPos.RIGHT);
		modifyContactLayout.add(relationshipField,1,3);
		GridPane.setConstraints(relationshipField,1,3,2,1);
		modifyContactLayout.add(workPhone,0,4);
		GridPane.setHalignment(workPhone,HPos.RIGHT);
		modifyContactLayout.add(workPhoneField,1,4);
		GridPane.setConstraints(workPhoneField,1,4,2,1);
		modifyContactLayout.add(cellPhone,0,5);
		GridPane.setHalignment(cellPhone,HPos.RIGHT);
		modifyContactLayout.add(cellPhoneField,1,5);
		GridPane.setConstraints(cellPhoneField,1,5,2,1);
		modifyContactLayout.add(email,0,6);
		GridPane.setHalignment(email,HPos.RIGHT);
		modifyContactLayout.add(emailField,1,6);
		GridPane.setConstraints(emailField,1,6,2,1);
		modifyContactLayout.add(editButton,1,7);
		GridPane.setHalignment(editButton,HPos.CENTER);
		modifyContactLayout.add(writeButton,1,7);
		GridPane.setHalignment(writeButton,HPos.CENTER);
		modifyContactLayout.add(trashButton,2,7);
		GridPane.setHalignment(trashButton,HPos.LEFT);
		modifyContactLayout.add(editLabel,1,8);
		GridPane.setHalignment(editLabel,HPos.CENTER);
		modifyContactLayout.add(writeLabel,1,8);
		GridPane.setHalignment(writeLabel,HPos.CENTER);
		modifyContactLayout.add(trashLabel,2,8);
		GridPane.setHalignment(trashLabel,HPos.LEFT);
		modifyContactLayout.add(cancel,1,9);
		GridPane.setHalignment(cancel,HPos.CENTER);
		GridPane.setConstraints(cancel,1,9,2,1);
		
		Scene modifyContactScene = new Scene(modifyContactLayout);
		stage.setScene(modifyContactScene);
		stage.showAndWait();
	}

}
