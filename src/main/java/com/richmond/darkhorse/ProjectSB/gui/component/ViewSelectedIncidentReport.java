package com.richmond.darkhorse.ProjectSB.gui.component;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import com.richmond.darkhorse.ProjectSB.Director;
import com.richmond.darkhorse.ProjectSB.IncidentReport;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class ViewSelectedIncidentReport extends GridPane implements DirectorLayout{

	private List<TextField> enabledTextFields = new ArrayList<TextField>();
	private IncidentReport incidentReport;
	private TextField descriptionText,actionText,appearanceText,commentsText; 
	
	public ViewSelectedIncidentReport(Director director,IncidentReport incidentReport) {
		this.incidentReport = incidentReport;
		buildGridPane(director);
	}
	
	private void buildGridPane(Director director) {
		setConstraints(this,6,0,10,10,"modulargridpane");
	    this.setStyle("-fx-background-color: #CCCCCC;");
	    this.getStylesheets().add("css/director.css");
	    Label title = createLabel("Incident Report Form","subtitle");
	    List<Label> labels = populateLabels(Arrays.asList("Student:","Date:","Time:","Description:","Action taken:","Appearance:","Medical:","Comments:","Teachers:"),"label");
	    TextField nameText = createTextField(incidentReport.getStudentName(),"textfield",650), dateText = createTextField(incidentReport.getStringDate(),"textfield",650);
	    TextField timeText = createTextField(incidentReport.getStringTime(),"textfield",650), descriptionText = createTextField(incidentReport.getIncidentDescription(),"textfield",650);
	    TextField actionText = createTextField(incidentReport.getActionTaken(),"textfield",650), appearanceText = createTextField(incidentReport.getStudentCondition(),"textfield",650);
	    TextField medicalText = medicalResponse(), commentsText = createTextField(incidentReport.getComments(),"textfield",650), teacherText = createTextField(incidentReport.getTeachersPresent(),"textfield",650);
	    this.descriptionText = descriptionText;
	    this.actionText = actionText;
	    this.appearanceText = appearanceText;
	    this.commentsText = commentsText;
	    disableAll(nameText,dateText,timeText,teacherText,descriptionText,actionText,appearanceText,commentsText,medicalText);
	    enable();
	    List<Node> nodes = Arrays.asList(title,labels.get(0),nameText,labels.get(1),dateText,labels.get(2),timeText,labels.get(3),descriptionText,labels.get(4),actionText,labels.get(5),appearanceText,labels.get(6),medicalText,labels.get(7),commentsText,labels.get(8),teacherText);
	    placeNodes(this,nodes);
	}
	
	@Override
	public void placeNodes(GridPane gridpane, List<Node> nodes) {
		placeNodeSpan(gridpane,nodes.get(0),0,0,6,2,"center",null);
		placeNode(gridpane,nodes.get(1),0,2,"right",null);
		placeNode(gridpane,nodes.get(2),1,2,"left",null);
		placeNode(gridpane,nodes.get(3),2,2,"right",null);
		placeNode(gridpane,nodes.get(4),3,2,"left",null);
		placeNode(gridpane,nodes.get(5),4,2,"right",null);
		placeNode(gridpane,nodes.get(6),5,2,"left",null);
		placeNode(gridpane,nodes.get(7),0,3,"right",null);
		placeNodeSpan(gridpane,nodes.get(8),1,3,5,1,"left",null);
		placeNode(gridpane,nodes.get(9),0,4,"right",null);
		placeNodeSpan(gridpane,nodes.get(10),1,4,5,1,"left",null);
		placeNode(gridpane,nodes.get(11),0,5,"right",null);
		placeNodeSpan(gridpane,nodes.get(12),1,5,5,1,"left",null);
		placeNode(gridpane,nodes.get(13),0,6,"right",null);
		placeNodeSpan(gridpane,nodes.get(14),1,6,5,1,"left",null);
		placeNode(gridpane,nodes.get(15),0,7,"right",null);
		placeNodeSpan(gridpane,nodes.get(16),1,7,5,1,"left",null);
		placeNode(gridpane,nodes.get(17),0,8,"right",null);
		placeNodeSpan(gridpane,nodes.get(18),1,8,2,1,"left",null);
	}
	
	/**
	 * Creates a TextField based on the information stored in a {@link Student}'s {@link IncidentReport}
	 * @return TextField
	 */
	private TextField medicalResponse() {
		String medicalResponse;
	    if(incidentReport.isMedicalAttention() == true && incidentReport.isOnSite() == false) {medicalResponse = incidentReport.getStudentName() + " required off-site medical attention";}
	    else if(incidentReport.isMedicalAttention() == true && incidentReport.isOnSite() == true){ medicalResponse = incidentReport.getStudentName() + " required on-site medical attention";}
	    else { medicalResponse = incidentReport.getStudentName() + " did not require any immediate medical attention";}
	    TextField medicalText = new TextField(medicalResponse);
	    medicalText.setMaxWidth(650);
	    medicalText.getStyleClass().add("textfield");
	    return medicalText;
	}
	
	/**
	 * Enables the given TextFields
	 */
	private void enable() {
		enabledTextFields.add(descriptionText);
	    enabledTextFields.add(actionText);
	    enabledTextFields.add(appearanceText);
	    enabledTextFields.add(commentsText);
	}
	
	/**
	 * Enables all TextFields
	 */
	public void enableAll() {
		for(TextField textField : enabledTextFields) {
			textField.setDisable(false);
		}
	}
	
	public IncidentReport getIncidentReport() {
		return incidentReport;
	}
	
	/**
	 * Disables all TextFields
	 */
	public void disableAll(TextField nameText,TextField dateText,TextField timeText,TextField teacherText,TextField descriptionText,TextField actionText,TextField appearanceText,TextField commentsText,TextField medicalText) {
		nameText.setDisable(true);
		dateText.setDisable(true);
		timeText.setDisable(true);
		teacherText.setDisable(true);
		descriptionText.setDisable(true);
		actionText.setDisable(true);
		appearanceText.setDisable(true);
		commentsText.setDisable(true);
		medicalText.setDisable(true);
	}
	
	public List<TextField> getEnabledTextFields() {
		return enabledTextFields;
	}

	public String getDescriptionText() {
		return descriptionText.getText();
	}

	public String getActionText() {
		return actionText.getText();
	}

	public String getAppearanceText() {
		return appearanceText.getText();
	}

	public String getCommentsText() {
		return commentsText.getText();
	}
	
}
