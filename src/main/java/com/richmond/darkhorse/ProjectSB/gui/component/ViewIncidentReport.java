package com.richmond.darkhorse.ProjectSB.gui.component;
import java.util.Arrays;
import java.util.List;
import com.richmond.darkhorse.ProjectSB.IncidentReport;
import com.richmond.darkhorse.ProjectSB.Student;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class ViewIncidentReport implements DirectorLayout{
	
	private Student student;
	private IncidentReport incidentReport;

	public ViewIncidentReport(Student student,IncidentReport incidentReport) {
		this.student = student;
		this.incidentReport = incidentReport;
	}
	
	public void display() {
		Stage stage = new Stage();
		GridPane viewIncidentReportLayout = buildGridPane();
		String title = "Incident Report - " + student.getFirstName() + " " + student.getLastName() + ", " + incidentReport.getStringDate();
		buildPopUp(stage,viewIncidentReportLayout,title);
	}
	
	/**
	 * Builds the central GridPane for {@link ViewIncidentReport}
	 * @return GridPane
	 */
	private GridPane buildGridPane() {
		GridPane gridpane = new GridPane();
		setConstraints(gridpane,6,0,10,10,"modulargridpane");
	    gridpane.getStylesheets().add("css/director.css");
	    Label title = createLabel("Incident Report Form","subtitle");
	    String nameText = student.getFirstName() + " " + student.getLastName();
	    List<Label> labels = populateLabels(Arrays.asList("Student:","Date:","Time:","Description:","Action taken:","Appearance:","Medical:","Comments:","Teacher:"),"label");
	    Label name = createLabel(nameText,"response-label"), date = createLabel(incidentReport.getStringDate(),"response-label"), time = createLabel(incidentReport.getStringTime(),"response-label");
	    Label description = createLabel(incidentReport.getIncidentDescription(),"response-label"), action = createLabel(incidentReport.getActionTaken(),"response-label");
	    Label appearance = createLabel(incidentReport.getStudentCondition(),"response-label"), medical = getMedical(), comments = createLabel(incidentReport.getComments(),"response-label"), teacher = createLabel(incidentReport.getTeachersPresent(),"response-label");
	    List<Node> nodes = Arrays.asList(title,labels.get(0),name,labels.get(1),date,labels.get(2),time,labels.get(3),description,labels.get(4),action,labels.get(5),appearance,labels.get(6), medical,labels.get(7),comments,labels.get(8),teacher);
	    placeNodes(gridpane,nodes);
	    return gridpane;
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
	 * Creates a Label based on information contained in the {@link Student}'s {@link DailySheet}
	 * @return Label
	 */
	private Label getMedical() {
		String medicalResponse;
	    if(incidentReport.isMedicalAttention() == true && incidentReport.isOnSite() == false) {medicalResponse = student.getFirstName() + "required off-site medical attention";}
	    else if(incidentReport.isMedicalAttention() == true && incidentReport.isOnSite() == true){ medicalResponse = student.getFirstName() + "required on-site medical attention";}
	    else { medicalResponse = student.getFirstName() + "did not require any immediate medical attention";}
	    Label medicalText = new Label(medicalResponse);
	    medicalText.getStyleClass().add("response-label");
	    return medicalText;
	}
	
}
