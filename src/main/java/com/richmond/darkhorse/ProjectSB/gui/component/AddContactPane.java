package com.richmond.darkhorse.ProjectSB.gui.component;
import java.util.Arrays;
import java.util.List;
import com.richmond.darkhorse.ProjectSB.Contact;
import com.richmond.darkhorse.ProjectSB.Director;
import com.richmond.darkhorse.ProjectSB.Student;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.Node;
import javafx.scene.control.Button;

public class AddContactPane extends GridPane implements DirectorLayout{
	
	public AddContactPane(Director director,Student student) {
		double rowIndex = 1.0;
		int column = 0;
		AddContact addContact = new AddContact(student,director);
		setConstraints(this,4,0,10,10,"gridpane");
		this.getStylesheets().add("css/director.css");
		buildPane(director,student,addContact,rowIndex,column);
	}
	
	/**
	 * Populates the GridPane
	 */
	private void buildPane(Director director,Student student,AddContact addContact,double rowIndex,int column) {
		this.getChildren().clear();
		Label title = createLabel("Student Contacts","super-subtitle");
		Button createNewContactButton = createButton(null,"images/addcontact.png",250,300,500);
		createNewContactButton.setOnAction(e -> createNewContact(addContact,director,student));
		populateContacts(student,addContact,director,rowIndex,column);
		List<Node> nodes = Arrays.asList(title,createNewContactButton);
		placeNodes(this,nodes);
	}
	
	@Override
	public void placeNodes(GridPane gridpane, List<Node> nodes) {
		placeNodeSpan(this,nodes.get(0),0,0,4,1,"center",null);
		placeNodeSpan(this,nodes.get(1),0,1,2,1,"center",null);
	}
	
	/**
	 * Opens a new scene that will allow the user to create a new {@link Contact}
	 * @param addContact - AddContact component
	 * @param director - the current user
	 * @param student - the current {@link Student}
	 */
	private void createNewContact(AddContact addContact,Director director,Student student) {
		addContact.display();
		buildPane(director,student,addContact,1.0,0);
	}
	
	/**
	 * Populates the GridPane with a list of {@link Contact}s for the {@link Student}
	 * @param student - the current {@link Student}
	 * @param addContact 
	 * @param director - the current user
	 * @param rowIndex
	 * @param column
	 */
	private void populateContacts(Student student,AddContact addContact,Director director,double rowIndex,int column) {
		List<Contact> studentContacts = student.getRecord().getContacts();
		if(studentContacts.size() > 0) {
			for(Contact contact : studentContacts) {
				String contactName = contact.getFirstName() + " " + contact.getLastName(), contactRelationship = contact.getRelationshipToStudent(), contactWorkPhone = contact.getWorkNumber(), contactCellPhone = contact.getCellNumber(), contactEmail = contact.getEmail();
				String buttonText = contactName + "\nrelationship: " + contactRelationship + "\nwork #: " + contactWorkPhone + "\ncell #: " + contactCellPhone + "\ne-mail: " + contactEmail;
				Button newButton = createButton(buttonText,null,0,300,500);
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
	}
	
}
