package com.richmond.darkhorse.ProjectSB.gui.component;
import java.util.Arrays;
import java.util.List;
import com.richmond.darkhorse.ProjectSB.Contact;
import com.richmond.darkhorse.ProjectSB.Director;
import com.richmond.darkhorse.ProjectSB.Student;
import com.richmond.darkhorse.ProjectSB.middleman.ModifyExistingContact;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class ModifyContact implements DirectorLayout{
	
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
		GridPane modifyContactLayout = buildGridPane(stage);
		String stageTitle = contact.getFirstName() + " " + contact.getLastName();
		buildPopUp(stage,modifyContactLayout,stageTitle);
	}

	/**
	 * Builds the GridPane to be used as the layout for ModifyContact
	 * @param stage - the current stage
	 * @return GridPane
	 */
	private GridPane buildGridPane(Stage stage) {
		GridPane gridpane = new GridPane();
		setConstraints(gridpane,3,0,15,15,"modulargridpane");
	    gridpane.getStylesheets().add("css/director.css");
	    ImageView modifyContactViewer = createImageWithFitHeight("images/newicon.png",250);
		Label title = createLabel("Modify Contact","popup-title");
		List<Label> labels = populateLabels(Arrays.asList("first name:","last name:","relationship:","work #:","cell #:","e-mail:"),"label");
		List<TextField> textfields = populateTextFields(Arrays.asList(contact.getFirstName(),contact.getLastName(),contact.getRelationshipToStudent(),contact.getWorkNumber(),contact.getCellNumber(),contact.getEmail()),"textfield",650);
		ImageButton editButton = new ImageButton(createImageWithFitHeight("images/edit.png",100));
		Label editLabel = createLabel("edit","label");
		ImageButton writeButton = new ImageButton(createImageWithFitHeight("images/write.png",100));
		writeButton.setVisible(false);
	    Label writeLabel = createLabel("write","label");
	    writeLabel.setVisible(false);
	    ImageButton trashButton = new ImageButton(createImageWithFitHeight("images/trash.png",100));
	    Label trashLabel = createLabel("delete","label");
	    Button cancel = new Button("cancel");
	    cancel.setOnAction(e -> stage.close());
	    writeButton.setOnAction(e -> write(stage,textfields));
	    editButton.setOnAction(e -> edit(writeButton,writeLabel,editButton,editLabel,textfields));
	    trashButton.setOnAction(e -> {
	    		director.deleteStudentContact(student,contact);
	    		stage.close();
	    });
	    List<Node> nodes = Arrays.asList(modifyContactViewer,title,labels.get(0),textfields.get(0),labels.get(1),textfields.get(1),labels.get(2),textfields.get(2),labels.get(3),textfields.get(3),labels.get(4),textfields.get(4),labels.get(5),textfields.get(5),editButton,writeButton,trashButton,editLabel,writeLabel,trashLabel,cancel);
		placeNodes(gridpane,nodes);
		return gridpane;
	}
	
	@Override
	public void placeNodes(GridPane gridpane, List<Node> nodes) {
		placeNode(gridpane,nodes.get(0),0,0,"center",null);
		placeNodeSpan(gridpane,nodes.get(1),1,0,2,1,"center",null);
		placeNode(gridpane,nodes.get(2),0,1,"right",null);
		placeNodeSpan(gridpane,nodes.get(3),1,1,2,1,"left",null);
		placeNode(gridpane,nodes.get(4),0,2,"right",null);
		placeNodeSpan(gridpane,nodes.get(5),1,2,2,1,"left",null);
		placeNode(gridpane,nodes.get(6),0,3,"right",null);
		placeNodeSpan(gridpane,nodes.get(7),1,3,2,1,"left",null);
		placeNode(gridpane,nodes.get(8),0,4,"right",null);
		placeNodeSpan(gridpane,nodes.get(9),1,4,2,1,"left",null);
		placeNode(gridpane,nodes.get(10),0,5,"right",null);
		placeNodeSpan(gridpane,nodes.get(11),1,5,2,1,"left",null);
		placeNode(gridpane,nodes.get(12),0,6,"right",null);
		placeNodeSpan(gridpane,nodes.get(13),1,6,2,1,"left",null);
		placeNode(gridpane,nodes.get(14),1,7,"center",null);
		placeNode(gridpane,nodes.get(15),1,7,"center",null);
		placeNode(gridpane,nodes.get(16),2,7,"left",null);
		placeNode(gridpane,nodes.get(17),1,8,"center",null);
		placeNode(gridpane,nodes.get(18),1,8,"center",null);
		placeNode(gridpane,nodes.get(19),2,8,"left",null);
		placeNodeSpan(gridpane,nodes.get(20),1,9,2,1,"center",null);
	}
	
	/**
	 * Pulls the information from the passed in text fields and creates a new modified {@link Student} {@link Contact}
	 * @param stage - the current stage
	 * @param textfields
	 */
	private void write(Stage stage,List<TextField> textfields) {
		String firstNameFinal = textfields.get(0).getText(), lastNameFinal = textfields.get(1).getText();
		String relationshipToStudent = textfields.get(2).getText(), workNumber = textfields.get(3).getText(), cellNumber = textfields.get(4).getText(), emailFinal = textfields.get(5).getText();
		if(textfields.get(0).getText().trim().isEmpty()){firstNameFinal = contact.getFirstName();}  
		if(textfields.get(1).getText().trim().isEmpty()){lastNameFinal = contact.getLastName();} 
		if(textfields.get(2).getText().trim().isEmpty()){relationshipToStudent = contact.getRelationshipToStudent();} 
		if(textfields.get(3).getText().trim().isEmpty()){workNumber = contact.getWorkNumber();} 
		if(textfields.get(4).getText().trim().isEmpty()){cellNumber = contact.getCellNumber();} 
		if(textfields.get(5).getText().trim().isEmpty()){emailFinal = contact.getEmail();}  
		Thread modifyExistingContact = new Thread(new ModifyExistingContact(director,contact,student,firstNameFinal,lastNameFinal,relationshipToStudent,workNumber,cellNumber,emailFinal));
		modifyExistingContact.start();
		stage.close();
	}
	
	/**
	 * Enables the passed text fields to be edited
	 * @param writeButton
	 * @param writeLabel
	 * @param editButton
	 * @param editLabel
	 * @param textfields
	 */
	private void edit(ImageButton writeButton,Label writeLabel,ImageButton editButton,Label editLabel,List<TextField> textfields) {
		writeButton.setVisible(true);
		writeLabel.setVisible(true);
		editButton.setVisible(false);
		editLabel.setVisible(false);
		for(TextField textfield : textfields) {textfield.setDisable(false);}
	}

}
