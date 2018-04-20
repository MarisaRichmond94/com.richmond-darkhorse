package com.richmond.darkhorse.ProjectSB.gui.component;
import java.util.Arrays;
import java.util.List;
import com.richmond.darkhorse.ProjectSB.Director;
import com.richmond.darkhorse.ProjectSB.Student;
import com.richmond.darkhorse.ProjectSB.middleman.CreateNewContact;
import javafx.beans.binding.Bindings;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class AddContact implements DirectorLayout {
	
	private Student student;
	private Director director;
	
	public AddContact(Student student,Director director) {
		this.student = student;
		this.director = director;
	}
	
	public void display() {
		Stage stage = new Stage();
		GridPane createContactLayout = buildGridPane(stage);
		buildPopUp(stage,createContactLayout,"Create New \nContact");
	}
	
	/**
	 * Builds the pop up GridPane for AddContact
	 * @param stage - the current stage
	 * @return GridPane
	 */
	private GridPane buildGridPane(Stage stage) {
		GridPane gridpane = new GridPane();
		setConstraints(gridpane,3,0,15,15,"modulargridpane");
	    gridpane.getStylesheets().add("css/director.css");
	    ImageView newContactViewer = createImageWithFitHeight("images/addcontact.png",250);
		Label title = createLabel("Create\nNew Contact","popup-title");
		List<Label> labels = populateLabels(Arrays.asList("first name:","last name:","relationship:","work #:","cell #:","e-mail:"),"label");
		List<TextField> textfields = populateTextFields(Arrays.asList("first name","last name","relationship to student","(XXX)-XXX-XXXX","(XXX)-XXX-XXXX","emailexample@gmail.com"),"textfield",650);
		Button createContactButton = createButton("create contact",null,0,0,0);
		createContactButton.disableProperty().bind(
	    	    Bindings.isEmpty(textfields.get(0).textProperty())
	    	    .or(Bindings.isEmpty(textfields.get(1).textProperty()))
	    	    .or(Bindings.isEmpty(textfields.get(2).textProperty()))
	    	    .or(Bindings.isEmpty(textfields.get(3).textProperty()))
	    	    .or(Bindings.isEmpty(textfields.get(4).textProperty()))
	    	    .or(Bindings.isEmpty(textfields.get(5).textProperty()))
	    	);
		createContactButton.setOnAction(e -> createContact(stage,textfields));
		Button cancelButton = createButton("cancel",null,0,0,0);
		cancelButton.setOnAction(e -> stage.close());
		List<Node> nodes = Arrays.asList(newContactViewer,title,labels.get(0),textfields.get(0),labels.get(1),textfields.get(1),labels.get(2),textfields.get(2),labels.get(3),textfields.get(3),labels.get(4),textfields.get(4),labels.get(5),textfields.get(5),createContactButton,cancelButton);
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
		placeNode(gridpane,nodes.get(15),2,7,"center",null);
	}
	
	/**
	 * Takes in all of the information and uses it to create a new {@link Contact} for the {@link Student}
	 * @param stage - the current stage
	 * @param textfields - a list of text fields containing information about the {@link Student}'s {@link Contact}
	 */
	private void createContact(Stage stage,List<TextField> textfields) {
		String firstNameFinal = textfields.get(0).getText(), lastNameFinal = textfields.get(1).getText(), relationshipToStudent = textfields.get(2).getText(), workNumber = textfields.get(3).getText(), cellNumber = textfields.get(4).getText(), emailFinal = textfields.get(5).getText();
		Thread newCreateNewContact = new Thread(new CreateNewContact(director,student,firstNameFinal,lastNameFinal,relationshipToStudent,workNumber,cellNumber,emailFinal));
		newCreateNewContact.start();
		stage.close();
	}

}
