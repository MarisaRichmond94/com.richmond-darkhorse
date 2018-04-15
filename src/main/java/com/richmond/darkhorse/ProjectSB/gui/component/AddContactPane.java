package com.richmond.darkhorse.ProjectSB.gui.component;
import java.util.List;
import com.richmond.darkhorse.ProjectSB.Contact;
import com.richmond.darkhorse.ProjectSB.Director;
import com.richmond.darkhorse.ProjectSB.Student;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.geometry.HPos;
import javafx.scene.control.Button;

//TODO burn this to the f**king ground  

public class AddContactPane extends GridPane{
	
	public AddContactPane(Director director,Student student) {
		
		double rowIndex = 1.0;
		int column = 0;
		AddContact addContact = new AddContact(student,director);
		
		this.setVgap(10);
		this.setHgap(10);
		ColumnConstraints columnOne = new ColumnConstraints();
		columnOne.setPercentWidth(25);
		ColumnConstraints columnTwo = new ColumnConstraints();
		columnTwo.setPercentWidth(25);
		ColumnConstraints columnThree = new ColumnConstraints();
		columnThree.setPercentWidth(25);
		ColumnConstraints columnFour = new ColumnConstraints();
		columnFour.setPercentWidth(25);
		this.getColumnConstraints().addAll(columnOne,columnTwo,columnThree,columnFour);
		this.getStyleClass().add("gridpane");
		this.getStylesheets().add("modifystudentinfo.css");
		
		buildPane(director,student,addContact,rowIndex,column);
		
	}
	
	public void buildPane(Director director,Student student,AddContact addContact,double rowIndex,int column) {
		
		this.getChildren().clear();
		
		Label title = new Label("Contacts");
		title.getStyleClass().add("subtitle");
		
		ImageView newContactViewer = new ImageView();
		Image createNewContact = new Image("addcontact.png");
		newContactViewer.setImage(createNewContact);
		newContactViewer.setPreserveRatio(true);
		newContactViewer.setFitHeight(200);
		Button createNewContactButton = new Button("",newContactViewer);
		createNewContactButton.setPrefSize(500,275);
		createNewContactButton.setOnAction(e -> {
			addContact.display();
			buildPane(director,student,addContact,1.0,0);
		});
		createNewContactButton.getStyleClass().add("button");
		
		List<Contact> studentContacts = student.getRecord().getContacts();
		if(studentContacts.size() > 0) {
			for(Contact contact : studentContacts) {
				Button newButton = new Button();
				String contactName = contact.getFirstName() + " " + contact.getLastName();
				String contactRelationship = contact.getRelationshipToStudent();
				String contactWorkPhone = contact.getWorkNumber();
				String contactCellPhone = contact.getCellNumber();
				String contactEmail = contact.getEmail();
				newButton.setText(contactName + "\nrelationship: " + contactRelationship + "\nwork #: " + contactWorkPhone + "\ncell #: " + contactCellPhone + "\ne-mail: " + contactEmail);
				newButton.setPrefSize(500,275);
				newButton.getStyleClass().add("button");
				newButton.setOnAction(e -> {
					ModifyContact modifyContact = new ModifyContact(director,student,contact);
					modifyContact.display();
					newButton.setText(contact.getFirstName() + " " + contact.getLastName() + "\nrelationship: " + contact.getRelationshipToStudent() + "\nwork #: " + contact.getWorkNumber() + "\ncell #: " + contact.getCellNumber() + "\ne-mail: " + contact.getEmail());
					buildPane(director,student,addContact,1.0,0);
				});
				int row = 0;
				boolean doesNumberEndInZero = doesNumberEndInZero(rowIndex);
				if(doesNumberEndInZero == false) {row = (int) Math.round(rowIndex);}
				else {row = (int)rowIndex;}
				determinePosition(this,newButton,row,column);
				rowIndex = rowIndex+0.5;
				column++;
			}
		}
		
		this.add(title,1,0);
		GridPane.setConstraints(title,1,0,2,1);
		GridPane.setHalignment(title,HPos.CENTER);
		this.add(createNewContactButton,0,1);
		GridPane.setConstraints(createNewContactButton,0,1,2,1);
		GridPane.setHalignment(createNewContactButton,HPos.CENTER);
		
	}

	public void determinePosition(GridPane gridpane,Button button,int row,int column) {
		int columnIndex;
		if(column == 0) {
			gridpane.add(button,2,1);
			GridPane.setConstraints(button,2,1,2,1);
			GridPane.setHalignment(button, HPos.CENTER);
		}else {
			boolean isIndexEven = isIntEven(column);
			if(isIndexEven == true) {columnIndex = 2;
			}else {columnIndex = 0;}
			gridpane.add(button, columnIndex, row);
			GridPane.setConstraints(button, columnIndex, row, 2, 1);
			GridPane.setHalignment(button, HPos.CENTER);
		}
	}
	
	public boolean isIntEven(int n) {
		if( n % 2 == 0) {return true;}
		return false;
	}
	
	public boolean doesNumberEndInZero(double rowIndex) {
		if (Math.abs(rowIndex - Math.rint(rowIndex)) == 0.5) {return false;}
		return true;
	}
	
}
