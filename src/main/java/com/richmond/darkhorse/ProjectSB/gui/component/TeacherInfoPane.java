package com.richmond.darkhorse.ProjectSB.gui.component;
import java.util.Arrays;
import java.util.List;import com.richmond.darkhorse.ProjectSB.Director;
import com.richmond.darkhorse.ProjectSB.Teacher;
import javafx.scene.layout.GridPane;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class TeacherInfoPane extends GridPane implements DirectorLayout{
	
	boolean isLead;
	
	public TeacherInfoPane(Director director,Teacher teacher,Teacher assistantTeacher) {
		isLead = true;
		buildGridPane(teacher,assistantTeacher);
	}
	
	/**
	 * Builds the {@link TeacherInfoPane}
	 * @param teacher - a {@link Teacher}
	 * @param assistantTeacher - a {@link Teacher}
	 */
	private void buildGridPane(Teacher teacher,Teacher assistantTeacher) {
		setConstraints(this,4,0,10,10,"gridpane");
		Label leadTeacherLabel = createLabel("Lead Teacher","super-subtitle");
	    Label assistantTeacherLabel = createLabel("Assistant","super-subtitle");
	    assistantTeacherLabel.setVisible(false);
	    List<Label> labels = populateLabels(Arrays.asList("full name:","employee id:","classroom:","e-mail:","cell number:"),"label");
	    String name = teacher.getFirstName() + " " + teacher.getLastName() + "";
	    TextField nameField = createTextField(name,"textfield",650);
	    nameField.setDisable(true);
	    TextField identificationField = createTextField(teacher.getTeacherID(),"textfield",650);
	    identificationField.setDisable(true);
	    TextField classroomField = createTextField("Marisa was too lazy to fix me","textfield",650);
	    classroomField.setDisable(true);
	    TextField emailField = getEmailField(teacher);
	    TextField phoneNumberField = getPhoneNumberField(teacher);
	    Label isLeadTeacher = createLabel("lead","label");
	    Label isAssistantTeacher = createLabel("assistant","label");
	    isLeadTeacher.setVisible(false);
	    ImageButton arrowButton = new ImageButton(createImageWithFitHeight("images/arrow.png",50));
	    List<TextField> textfields = Arrays.asList(nameField,identificationField,classroomField,emailField,phoneNumberField);
	    List<Label> arrowLabels = Arrays.asList(assistantTeacherLabel,leadTeacherLabel,isAssistantTeacher,isLeadTeacher);
	    arrowButton.setOnAction(e -> arrowButtonSelected(teacher,assistantTeacher,textfields,arrowLabels));
	    Label writeLabel = createLabel("write","label");
	    ImageButton writeButton = new ImageButton(createImageWithFitHeight("images/write.png",125));
	    writeButton.setVisible(false);
	    writeLabel.setVisible(false);
	    Label editLabel = createLabel("edit","label");
	    ImageButton editButton = new ImageButton(createImageWithFitHeight("images/edit.png",125));
	    editButton.setOnAction(e -> edit(editButton,editLabel,writeButton,writeLabel,emailField,phoneNumberField));
	    writeButton.setOnAction(e -> write(teacher,assistantTeacher,editButton,editLabel,writeButton,writeLabel,emailField,phoneNumberField));
	    List<Node> nodes = Arrays.asList(leadTeacherLabel,assistantTeacherLabel,labels.get(0),nameField,labels.get(1),identificationField,labels.get(2),classroomField,labels.get(3),emailField,labels.get(4),phoneNumberField,arrowButton,isLeadTeacher,isAssistantTeacher,editButton,editLabel,writeButton,writeLabel);
	    placeNodes(this,nodes);
	    this.getStylesheets().add("css/director.css");
	
	}

	@Override
	public void placeNodes(GridPane gridpane, List<Node> nodes) {
		placeNodeSpan(this,nodes.get(0),1,0,2,1,"center",null);
		placeNodeSpan(this,nodes.get(1),1,0,2,1,"center",null);
		placeNode(this,nodes.get(2),0,1,"right",null);
		placeNodeSpan(this,nodes.get(3),1,1,2,1,"left",null);
		placeNode(this,nodes.get(4),0,2,"right",null);
		placeNodeSpan(this,nodes.get(5),1,2,2,1,"left",null);
		placeNode(this,nodes.get(6),0,3,"right",null);
//		placeNodeSpan(this,nodes.get(7),1,3,2,1,"left",null);
		placeNode(this,nodes.get(8),0,4,"right",null);
		placeNodeSpan(this,nodes.get(9),1,4,2,1,"left",null);
		placeNode(this,nodes.get(10),0,5,"right",null);
		placeNodeSpan(this,nodes.get(11),1,5,2,1,"left",null);
		placeNode(this,nodes.get(12),3,3,"center",null);
		placeNode(this,nodes.get(13),3,4,"center",null);
		placeNode(this,nodes.get(14),3,4,"center",null);
		placeNodeSpan(this,nodes.get(15),1,6,2,1,"center",null);
		placeNodeSpan(this,nodes.get(16),1,7,2,1,"center",null);
		placeNodeSpan(this,nodes.get(17),1,6,2,1,"center",null);
		placeNodeSpan(this,nodes.get(18),1,7,2,1,"center",null);
	}
	
	/**
	 * Creates a TextField with the email of the {@link Teacher}
	 * @param teacher - a {@link Teacher}
	 * @return TextField
	 */
	private TextField getEmailField(Teacher teacher) {
		TextField emailField = new TextField();
	    if(teacher.getContact() == null || teacher.getContact().getEmail() == null) {emailField.setPromptText("e-mail");}
	    else {emailField.setPromptText(teacher.getContact().getEmail());}
	    emailField.getStyleClass().add("textfield"); 
	    emailField.setDisable(true);
	    emailField.setMaxWidth(650);
	    return emailField;
	}
	
	/**
	 * Creates a TextField with the phone number of the {@link Teacher}
	 * @param teacher - a {@link Teacher}
	 * @return TextField
	 */
	private TextField getPhoneNumberField(Teacher teacher) {
		 TextField phoneNumberField = new TextField();
		 if(teacher.getContact() != null && teacher.getContact().getCellNumber() != null) {phoneNumberField.setPromptText(teacher.getContact().getCellNumber());}
		 else {phoneNumberField.setPromptText("(xxx)-xxx-xxxx");}
		 phoneNumberField.getStyleClass().add("textfield"); 
		 phoneNumberField.setDisable(true);
		 phoneNumberField.setMaxWidth(650);
		 return phoneNumberField;
	}
	
	/**
	 * Switches {@link Teacher}s
	 */
	private void arrowButtonSelected(Teacher teacher,Teacher assistantTeacher,List<TextField> textfields,List<Label> labels) {
		if(isLead == true && assistantTeacher != null) {
			labels.get(0).setVisible(true);
			labels.get(1).setVisible(false);
			labels.get(2).setVisible(false);
			labels.get(3).setVisible(true);
			textfields.get(0).setPromptText(assistantTeacher.getFirstName() + " " + assistantTeacher.getLastName());
			textfields.get(1).setPromptText(assistantTeacher.getTeacherID());
			if(assistantTeacher.getContact() == null || assistantTeacher.getContact().getEmail() == null) {textfields.get(3).setPromptText("e-mail");}
			else{textfields.get(3).setPromptText(assistantTeacher.getContact().getEmail());};
			if(assistantTeacher.getContact() == null || assistantTeacher.getContact().getCellNumber() == null) {textfields.get(4).setPromptText("(xxx)-xxx-xxxx");}
			else {textfields.get(4).setPromptText("" + assistantTeacher.getContact().getCellNumber() + "");}
			isLead = false;
		}else if(isLead == false) {
			labels.get(0).setVisible(false);
			labels.get(1).setVisible(true);
			labels.get(2).setVisible(true);
			labels.get(3).setVisible(false);
			textfields.get(0).setPromptText(teacher.getFirstName() + " " + teacher.getLastName());
			textfields.get(1).setPromptText(teacher.getTeacherID());
			if(teacher.getContact() == null || teacher.getContact().getEmail() == null) {textfields.get(3).setPromptText("e-mail");}
			else{textfields.get(3).setPromptText(teacher.getContact().getEmail());};
			if(teacher.getContact() == null || teacher.getContact().getCellNumber() == null) {textfields.get(4).setPromptText("(xxx)-xxx-xxxx");}
			else {textfields.get(4).setPromptText("" + teacher.getContact().getCellNumber() + "");}
			isLead = true;
		}
	}
	
	/**
	 * Enters edit mode
	 */
	private void edit(ImageButton editButton,Label editLabel,ImageButton writeButton,Label writeLabel,TextField emailField,TextField phoneNumberField) {
		editButton.setVisible(false);
		editLabel.setVisible(false);
		writeButton.setVisible(true);
		writeLabel.setVisible(true);
		emailField.setDisable(false);
		phoneNumberField.setDisable(false);
	}
	
	/**
	 * Writes changes to the {@link Teacher}
	 */
	private void write(Teacher teacher,Teacher assistantTeacher,ImageButton editButton,Label editLabel,ImageButton writeButton,Label writeLabel,TextField emailField,TextField phoneNumberField) {
		if(isLead == true) {
			String teacherEmail = null;
    		if(emailField.getText().isEmpty() && teacher.getContact().getEmail() != null) {teacherEmail = teacher.getContact().getEmail();}
    		else {teacherEmail = emailField.getText();}
    		teacher.getContact().setEmail(teacherEmail);
	    	String teachersPhoneNumber = null;
	    	if(phoneNumberField.getText().isEmpty() && teacher.getContact().getCellNumber() != null) {teachersPhoneNumber = teacher.getContact().getCellNumber();}
	    	else {teachersPhoneNumber = phoneNumberField.getText();}
    		teacher.getContact().setCellNumber(teachersPhoneNumber);
		}else {
			String teacherEmail = null;
    		if(emailField.getText().isEmpty() && assistantTeacher.getContact().getEmail() != null) {teacherEmail = assistantTeacher.getContact().getEmail();}
    		else {teacherEmail = emailField.getText();}
			assistantTeacher.getContact().setEmail(teacherEmail);
			String teachersPhoneNumber = null;
	    	if(phoneNumberField.getText().isEmpty() && assistantTeacher.getContact().getCellNumber() != null) {teachersPhoneNumber = assistantTeacher.getContact().getCellNumber();}
	    	else {teachersPhoneNumber = phoneNumberField.getText();}
			assistantTeacher.getContact().setCellNumber(teachersPhoneNumber);
		}
		writeButton.setVisible(false);
		writeLabel.setVisible(false);
		editButton.setVisible(true);
		editLabel.setVisible(true);
		emailField.setDisable(true);
		phoneNumberField.setDisable(true);
	}
	
}
