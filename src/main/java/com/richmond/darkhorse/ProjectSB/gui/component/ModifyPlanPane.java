package com.richmond.darkhorse.ProjectSB.gui.component;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.richmond.darkhorse.ProjectSB.Classroom;
import com.richmond.darkhorse.ProjectSB.Director;
import com.richmond.darkhorse.ProjectSB.Student;
import com.richmond.darkhorse.ProjectSB.middleman.ModifyExistingStudent;
import javafx.scene.paint.Color;
import javafx.geometry.HPos;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class ModifyPlanPane extends GridPane implements DirectorLayout{

	private Map<String,Boolean> attendancePlan;
	
	public ModifyPlanPane(Student student,Director director) {
		this.attendancePlan = student.getRecord().getAttendance().getAttendancePlan();
		buildGridPane(student,director);
	}
	
	/**
	 * Assembles the GridPane
	 * @param student - the current {@link Student}
	 */
	private void buildGridPane(Student student,Director director) {
		setConstraints(this,3,0,10,10,"gridpane");
	    this.getStylesheets().add("css/director.css");
	    Label studentAttendancePlan = createLabel("Attendance Plan","super-subtitle");
	    List<Label> labels = populateLabels(Arrays.asList("monday plan:","tuesday plan","wednesday plan:","thursday plan:","friday plan:"),"centrallabel");
	    ChoiceBox<String> mondayBox = buildDayBox("Monday");
	    ChoiceBox<String> tuesdayBox = buildDayBox("Tuesday");
	    ChoiceBox<String> wednesdayBox = buildDayBox("Wednesday");
	    ChoiceBox<String> thursdayBox = buildDayBox("Thursday");
	    ChoiceBox<String> fridayBox = buildDayBox("Friday");
	    List<ChoiceBox<String>> dayBoxes = Arrays.asList(mondayBox,tuesdayBox,wednesdayBox,thursdayBox,fridayBox);
	    ImageButton editButton = new ImageButton(createImageWithFitHeight("images/edit.png",125));
		Label editLabel = createLabel("edit","label");
		ImageButton writeButton = new ImageButton(createImageWithFitHeight("images/write.png",125));
		writeButton.setVisible(false);
	    Label writeLabel = createLabel("write","label");
	    writeLabel.setVisible(false);
	    writeButton.setOnAction(e -> write(director,student,dayBoxes));
	    editButton.setOnAction(e -> edit(writeButton,writeLabel,editButton,editLabel,dayBoxes));
		List<Node> nodes = Arrays.asList(studentAttendancePlan,labels.get(0),dayBoxes.get(0),labels.get(1),dayBoxes.get(1),labels.get(2),dayBoxes.get(2),labels.get(3),dayBoxes.get(3),labels.get(4),dayBoxes.get(4),editButton,editLabel,writeButton,writeLabel);
		placeNodes(this,nodes);
	}

	@Override
	public void placeNodes(GridPane gridpane, List<Node> nodes) {
		placeNodeSpan(this,nodes.get(0),0,0,3,1,"center",null);
		placeNode(this,nodes.get(1),0,1,"right",null);
		placeNodeSpan(this,nodes.get(2),1,1,2,1,"left",null);
		placeNode(this,nodes.get(3),0,2,"right",null);
		placeNodeSpan(this,nodes.get(4),1,2,2,1,"left",null);
		placeNode(this,nodes.get(5),0,3,"right",null);
		placeNodeSpan(this,nodes.get(6),1,3,2,1,"left",null);
		placeNode(this,nodes.get(7),0,4,"right",null);
		placeNodeSpan(this,nodes.get(8),1,4,2,1,"left",null);
		placeNode(this,nodes.get(9),0,5,"right",null);
		placeNodeSpan(this,nodes.get(10),1,5,2,1,"left",null);
		placeNodeSpan(this,nodes.get(11),1,6,2,1,"center",null);
		placeNodeSpan(this,nodes.get(12),1,7,2,1,"center",null);
		placeNodeSpan(this,nodes.get(13),1,6,2,1,"center",null);
		placeNodeSpan(this,nodes.get(14),1,7,2,1,"center",null);
	}
	
	/**
	 * Creates a new ChoiceBox<String> and populates it with "present" or "not present"
	 * @param day - String representing the day of the week, which is used to determine which option to set the value to
	 * @return ChoiceBox<String>
	 */
	private ChoiceBox<String> buildDayBox(String day){
		ChoiceBox<String> dayBox = new ChoiceBox<String>();
		dayBox.getItems().addAll("present","not present");
	    if(attendancePlan.get(day) == true) {dayBox.setValue(dayBox.getItems().get(0));}
	    else {dayBox.setValue(dayBox.getItems().get(1));}
	    dayBox.setDisable(true);
	    dayBox.getStyleClass().add("choice-box");
	    return dayBox;
	}
	
	/**
	 * Takes all of the information from the Choice Boxes and uses them to modify the {@link Student}'s current {@link AttendancePlan}
	 * @param director - the current user
	 * @param student - the current {@link Student}
	 * @param dayBoxes - a list of Choice Boxes containing necessary data
	 */
	private void write(Director director,Student student,List<ChoiceBox<String>> dayBoxes) {
		Classroom selectedClassroom = student.getClassroom(student.getClassroomID());
		Map<String,Boolean> studentsAttendancePlan = new HashMap<String,Boolean>();
		if(dayBoxes.get(0).getValue().equals("present")) {studentsAttendancePlan.put("Monday",true);}
		else {studentsAttendancePlan.put("Monday",false);}
		if(dayBoxes.get(1).getValue().equals("present")) {studentsAttendancePlan.put("Tuesday",true);}
		else {studentsAttendancePlan.put("Tuesday",false);}
		if(dayBoxes.get(2).getValue().equals("present")) {studentsAttendancePlan.put("Wednesday",true);}
		else {studentsAttendancePlan.put("Wednesday",false);}
		if(dayBoxes.get(3).getValue().equals("present")) {studentsAttendancePlan.put("Thursday",true);}
		else {studentsAttendancePlan.put("Thursday",false);}
		if(dayBoxes.get(4).getValue().equals("present")) {studentsAttendancePlan.put("Friday",true);}
		else {studentsAttendancePlan.put("Friday",false);}
		Thread modifyExistingStudent = new Thread(new ModifyExistingStudent(director,student,selectedClassroom,studentsAttendancePlan));
		modifyExistingStudent.start();
		Label saveSuccessful = new Label("save successful!");
		saveSuccessful.setTextFill(Color.RED);
		saveSuccessful.getStyleClass().add("label");
		this.add(saveSuccessful,1,9);
		GridPane.setConstraints(saveSuccessful,1,9,2,1);
		GridPane.setHalignment(saveSuccessful,HPos.CENTER);
		dayBoxes.get(0).setDisable(true);
		dayBoxes.get(1).setDisable(true);
		dayBoxes.get(2).setDisable(true);
		dayBoxes.get(3).setDisable(true);
		dayBoxes.get(4).setDisable(true);
	}
	
	/**
	 * Allows the user to enter edit mode by enabling all Choice Boxes
	 * @param writeButton
	 * @param writeLabel
	 * @param editButton
	 * @param editLabel
	 * @param dayBoxes
	 */
	private void edit(ImageButton writeButton,Label writeLabel,ImageButton editButton,Label editLabel,List<ChoiceBox<String>> dayBoxes) {
		writeButton.setVisible(true);
		writeLabel.setVisible(true);
		editButton.setVisible(false);
		editLabel.setVisible(false);
		for(ChoiceBox<String> dayBox : dayBoxes) {dayBox.setDisable(false);}
	}
	
}
