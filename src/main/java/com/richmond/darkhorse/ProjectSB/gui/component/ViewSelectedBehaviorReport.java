package com.richmond.darkhorse.ProjectSB.gui.component;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import com.richmond.darkhorse.ProjectSB.BehaviorReport;
import com.richmond.darkhorse.ProjectSB.Director;
import com.richmond.darkhorse.ProjectSB.StaffMember;
import com.richmond.darkhorse.ProjectSB.Teacher;
import javafx.scene.control.TextField;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class ViewSelectedBehaviorReport extends GridPane implements DirectorLayout{
	
	private List<TextField> enabledTextFields = new ArrayList<TextField>();
	private BehaviorReport behaviorReport;
	private TextField classroomActivityText,childsBehaviorText,consequenceText,childsReactionText,timeLastedText; 
	
	public ViewSelectedBehaviorReport(Director director,BehaviorReport behaviorReport) {
		this.behaviorReport = behaviorReport;
		buildGridPane(director);
	}
	
	private void buildGridPane(Director director) {
		setConstraints(this,6,0,10,10,"modulargridpane");
	    this.setStyle("-fx-background-color: #CCCCCC;");
	    this.getStylesheets().add("css/director.css");
		Label title = createLabel("Behavior Incident Form","subtitle");
	    List<Label> labels = populateLabels(Arrays.asList("Student:","Date:","Time:","Teacher:","Activity","Behavior","Consequence","Reaction","Duration","Observed by:","Teacher","Director"),"label");
	   	String name = behaviorReport.getStudentName(), date = behaviorReport.getStringDate(), time = behaviorReport.getStringTime(), teacher = "" + behaviorReport.getTeacher(behaviorReport.getTeacherID()) + "";
	    String activity = behaviorReport.getActivityDuringBehavior(), behavior = behaviorReport.getChildsBehavior(), consequence = behaviorReport.getConsequence();
	    String response = behaviorReport.getStudentResponse(), duration = behaviorReport.getDurationBehaviorOccurred(), observed = behaviorReport.getTeacherObserved();
	    TextField nameText = createTextField(name,"textfield",650), dateText = createTextField(date,"textfield",650), timeText = createTextField(time,"textfield",650); 
	    TextField teacherText = createTextField(teacher,"textfield",650), classroomActivityText = createTextField(activity,"textfield",650), childsBehaviorText = createTextField(behavior,"textfield",650);
	    TextField consequenceText = createTextField(consequence,"textfield",650), childsReactionText = createTextField(response,"textfield",650), timeLastedText = createTextField(duration,"textfield",650);
	    TextField observedText = createTextField(observed,"textfield",650), teacherSig = getTeacherSignature(director), directorSig = getDirectorSignature(director);
	    this.classroomActivityText = classroomActivityText;
	    this.childsBehaviorText = childsBehaviorText;
	    this.consequenceText = consequenceText;
	    this.childsReactionText = childsReactionText;
	    this.timeLastedText = timeLastedText;disableAll(nameText,dateText,timeText,teacherText,classroomActivityText,childsBehaviorText,consequenceText,childsReactionText,timeLastedText,observedText,teacherSig,directorSig);
	    List<TextField> textfields = Arrays.asList(classroomActivityText,childsBehaviorText,consequenceText,childsReactionText,timeLastedText,directorSig);
	    initialEnable(textfields);
	    List<Node> nodes = Arrays.asList(title,labels.get(0),nameText,labels.get(1),dateText,labels.get(2),timeText,labels.get(3),teacherText,labels.get(4),classroomActivityText,labels.get(5),childsBehaviorText,labels.get(6),consequenceText,labels.get(7),childsReactionText,labels.get(8),timeLastedText,labels.get(9),observedText,labels.get(10),teacherSig,labels.get(11),directorSig);
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
		placeNode(gridpane,nodes.get(8),1,3,"left",null);
		placeNode(gridpane,nodes.get(9),2,3,"right",null);
		placeNodeSpan(gridpane,nodes.get(10),3,3,3,1,"left",null);
		placeNodeSpan(gridpane,nodes.get(11),0,4,1,1,"right",null);
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
	
	/**
	 * Enables all of the TextFields in the enabledTextFields List
	 */
	public void enableAll() {
		for(TextField textField : enabledTextFields) {
			textField.setDisable(false);
		}
	}
	
	public BehaviorReport getBehaviorReport() {
		return behaviorReport;
	}
	
	private void disableAll(TextField nameText,TextField dateText,TextField timeText,TextField teacherText,TextField classroomActivityText,TextField childsBehaviorText,TextField consequenceText,TextField childsReactionText,TextField timeLastedText,TextField observedByText,TextField teacherSignatureText,TextField directorSignatureText) {
		nameText.setDisable(true);
		dateText.setDisable(true);
		timeText.setDisable(true);
		teacherText.setDisable(true);
		classroomActivityText.setDisable(true);
		childsBehaviorText.setDisable(true);
		consequenceText.setDisable(true);
		childsReactionText.setDisable(true);
		timeLastedText.setDisable(true);
		observedByText.setDisable(true);
		teacherSignatureText.setDisable(true);
		directorSignatureText.setDisable(true);
	}
	
	private void initialEnable(List<TextField> textfields) {
		enabledTextFields.add(textfields.get(0));
	    enabledTextFields.add(textfields.get(1));
	    enabledTextFields.add(textfields.get(2));
	    enabledTextFields.add(textfields.get(3));
	    enabledTextFields.add(textfields.get(4));
	    enabledTextFields.add(textfields.get(5));
	}
	
	/**
	 * Creates a TextField based on the data stored in the {@link Student}'s {@link BehaviorReport}
	 * @param director - a {@link Director}
	 * @return TextField
	 */
	private TextField getTeacherSignature(Director director) {
		boolean teachersSignature = behaviorReport.getTeacherSignature();
	    Teacher myTeacher = null;
	    Map<String,StaffMember> staff = director.getStaffMembers();
	    if(staff.containsKey(behaviorReport.getTeacherID())) {myTeacher = (Teacher)staff.get(behaviorReport.getTeacherID());}
	    String teachSignature;
	    if(teachersSignature == true && myTeacher != null) { teachSignature = myTeacher.getFirstName() + " " + myTeacher.getLastName();}
	    else {teachSignature = " ";}
	    TextField teacherSignatureText = new TextField(teachSignature);
	    teacherSignatureText.setMaxWidth(650);
	    teacherSignatureText.getStyleClass().add("textfield");
	    return teacherSignatureText;
	}
	
	/**
	 * Creates a TextField based on the data stored in the {@link Student}'s {@link BehaviorReport}
	 * @param director - a {@link Director}
	 * @return TextField
	 */
	private TextField getDirectorSignature(Director director) {
		boolean directorsSignature = behaviorReport.getDirectorSignature();
	    String signature;
	    if(directorsSignature == true) { signature = director.getFirstName() + " " + director.getLastName();}
	    else {signature = " ";}
	    TextField directorSignatureText = new TextField(signature);
	    directorSignatureText.setMaxWidth(650);
	    directorSignatureText.getStyleClass().add("textfield");
	    return directorSignatureText;
	}
	
	public List<TextField> getEnabledTextFields() {
		return enabledTextFields;
	}

	public String getClassroomActivityText() {
		return classroomActivityText.getText();
	}

	public String getChildsBehaviorText() {
		return childsBehaviorText.getText();
	}

	public String getConsequenceText() {
		return consequenceText.getText();
	}

	public String getChildsReactionText() {
		return childsReactionText.getText();
	}

	public String getTimeLastedText() {
		return timeLastedText.getText();
	}
	
}
