package com.richmond.darkhorse.ProjectSB.gui.component;
import com.richmond.darkhorse.ProjectSB.BehaviorReport;
import com.richmond.darkhorse.ProjectSB.Director;
import com.richmond.darkhorse.ProjectSB.Student;
import com.richmond.darkhorse.ProjectSB.Teacher;
import com.richmond.darkhorse.ProjectSB.StaffMember;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class ViewBehaviorReport implements DirectorLayout {
	
	private Director director;
	private Student student;
	private BehaviorReport behaviorReport;

	public ViewBehaviorReport(Director director,Student student,BehaviorReport behaviorReport) {
		this.director = director;
		this.student = student;
		this.behaviorReport = behaviorReport;
	}
	
	public void display() {
		Stage stage = new Stage();
		GridPane viewBehaviorReportLayout = buildGridPane();
		String title = "Behavior Report - " + student.getFirstName() + " " + student.getLastName() + ", " + behaviorReport.getStringDate();
		buildPopUp(stage,viewBehaviorReportLayout,title);
	}
	
	/**
	 * Builds the central GridPane for {@link ViewBehaviorReport}
	 * @return GridPane
	 */
	private GridPane buildGridPane() {
		GridPane gridpane = new GridPane();
		setConstraints(gridpane,6,0,10,10,"modulargridpane");
	    gridpane.getStylesheets().add("css/director.css");
		Label title = createLabel("Behavior Incident Form","subtitle");
		String name = student.getFirstName() + " " + student.getLastName(), date = behaviorReport.getStringDate(), time = behaviorReport.getStringTime();
		String teacher = "" + behaviorReport.getTeacher(behaviorReport.getTeacherID()) + "", activity = behaviorReport.getActivityDuringBehavior();
		String behavior = behaviorReport.getChildsBehavior(), consequence = behaviorReport.getConsequence(), reaction = behaviorReport.getStudentResponse();
		String duration = behaviorReport.getDurationBehaviorOccurred(), observed = behaviorReport.getTeacherObserved();  
	    List<Label> labels = populateLabels(Arrays.asList("Student:","Date","Time:","Teacher","Activity","Behavior","Consequence","Reaction","Duration","Observed By:","Teacher","Director"),"label");
	    List<Label> textLabels = populateLabels(Arrays.asList(name,date,time,teacher,activity,behavior,consequence,reaction,duration,observed),"response-label");
	    boolean teachersSignature = behaviorReport.getTeacherSignature();
	    Teacher myTeacher = null;
	    Map<String,StaffMember> staff = director.getStaffMembers();
	    if(staff.containsKey(behaviorReport.getTeacherID())) {myTeacher = (Teacher)staff.get(behaviorReport.getTeacherID());}
	    String teachSignature;
	    if(teachersSignature == true && myTeacher != null) { teachSignature = myTeacher.getFirstName() + " " + myTeacher.getLastName();}
	    else {teachSignature = " ";}
	    Label teacherSignatureText = createLabel(teachSignature,"response-label");
	    teacherSignatureText.getStyleClass().add("response-label");
	    boolean directorsSignature = behaviorReport.getDirectorSignature();
	    String signature;
	    if(directorsSignature == true) { signature = director.getFirstName() + " " + director.getLastName();}
	    else {signature = "";}
	    Label directorSignatureText = createLabel(signature,"response-label");
	    List<Node> nodes = Arrays.asList(title,labels.get(0),textLabels.get(0),labels.get(1),textLabels.get(1),labels.get(2),textLabels.get(2),labels.get(3),textLabels.get(3),labels.get(4),textLabels.get(4),labels.get(5),textLabels.get(5),labels.get(6),textLabels.get(6),labels.get(7),textLabels.get(7),labels.get(8),textLabels.get(8),labels.get(9),textLabels.get(9),labels.get(10),teacherSignatureText,labels.get(11),directorSignatureText);
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
		placeNode(gridpane,nodes.get(8),1,3,"left",null);
		placeNode(gridpane,nodes.get(9),2,3,"right",null);
		placeNodeSpan(gridpane,nodes.get(10),3,3,3,1,"left",null);
		placeNode(gridpane,nodes.get(11),0,4,"right",null);
		placeNodeSpan(gridpane,nodes.get(12),1,4,5,1,"left",null);
		placeNode(gridpane,nodes.get(13),0,5,"right",null);
		placeNodeSpan(gridpane,nodes.get(14),1,5,4,1,"left",null);
		placeNode(gridpane,nodes.get(15),0,6,"right",null);
		placeNodeSpan(gridpane,nodes.get(16),1,6,4,1,"left",null);
		placeNode(gridpane,nodes.get(17),0,7,"right",null);
		placeNodeSpan(gridpane,nodes.get(18),1,7,4,1,"left",null);
		placeNode(gridpane,nodes.get(19),0,8,"right",null);
		placeNodeSpan(gridpane,nodes.get(20),1,8,4,1,"left",null);
		placeNode(gridpane,nodes.get(21),0,9,"right",null);
		placeNodeSpan(gridpane,nodes.get(22),1,9,2,1,"left",null);
		placeNode(gridpane,nodes.get(23),3,9,"right",null);
		placeNodeSpan(gridpane,nodes.get(24),4,9,2,1,"left",null);
	}
	
}
