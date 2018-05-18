package com.richmond.darkhorse.ProjectSB.gui.component;
import java.util.Arrays;
import java.util.List;
import com.richmond.darkhorse.ProjectSB.Classroom;
import com.richmond.darkhorse.ProjectSB.Director;
import com.richmond.darkhorse.ProjectSB.Student;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.control.ScrollPane;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.control.ToggleButton;

public class AddRemovePane extends GridPane implements DirectorLayout{

	private int count;
	private boolean isAddPane = true;
	private GridPane innerPane;
	private AddStudentPane addStudentPane;
	private RemoveStudentPane removeStudentPane;
	
	public AddRemovePane(Classroom classroom,Director director) {
		buildGridPane(director,classroom);
	}
	
	/**
	 * Builds the necessary GridPanes for the {@link AddRemovePane}
	 * @param director - a {@link Director}
	 * @param classroom - a {@link Classroom}
	 */
	private void buildGridPane(Director director,Classroom classroom) {
		setConstraints(this,6,0,10,10,"gridpane");
		this.getStylesheets().add("css/director.css");
		count = 0;
		List<String> studentsEnrolled = classroom.getStudentsEnrolled();
		for(@SuppressWarnings("unused") String student : studentsEnrolled) {count++;}
		List<Label> trackingLabels = populateLabels(Arrays.asList("Total:","Monday:","Tuesday:","Wednesday:","Thursday:","Friday:"),"label");
		Label total = createLabel("" + count + "","label");
		Label monday = createLabel(buildRatioString(classroom,"Monday"),"label"), tuesday = createLabel(buildRatioString(classroom,"Tuesday"),"label"), wednesday = createLabel(buildRatioString(classroom,"Wednesday"),"label"), thursday = createLabel(buildRatioString(classroom,"Thursday"),"label"), friday = createLabel(buildRatioString(classroom,"Friday"),"label");
		List<Label> dayLabels = Arrays.asList(monday,tuesday,wednesday,thursday,friday);
		overflowCheck(dayLabels,classroom);
		//inner pane
		addStudentPane = new AddStudentPane(classroom,director);
		innerPane = addStudentPane;
		ScrollPane scrollPane = new ScrollPane(innerPane);
		scrollPane.setPrefHeight(500);
		scrollPane.setPrefWidth(500);
		ToggleButton addButton = createToggleButton("add student(s)",true,50,300,"toggle-button");
		ToggleButton removeButton = createToggleButton("remove student(s)",false,50,300,"toggle-button");
		Button addEnterButton = createButton("add",null,0,40,150);
		List<Label> labels = Arrays.asList(total,monday,tuesday,wednesday,thursday,friday);
		addEnterButton.setOnAction(e -> addEnterButtonSelect(scrollPane,director,classroom,labels));
		Button removeEnterButton = createButton("remove",null,0,40,150);
		removeEnterButton.setVisible(false);
		removeEnterButton.setOnAction(e -> removeEnterButtonSelected(scrollPane,director,classroom,labels));
		addButton.setOnAction(e -> add(scrollPane,director,classroom,removeButton,addButton,addEnterButton,removeEnterButton));
		removeButton.setOnAction(e -> remove(scrollPane,director,classroom,removeButton,addButton,addEnterButton,removeEnterButton));
		List<Node> nodes = Arrays.asList(trackingLabels.get(0),total,trackingLabels.get(1),monday,trackingLabels.get(2),tuesday,trackingLabels.get(3),wednesday,trackingLabels.get(4),thursday,trackingLabels.get(5),friday,addButton,removeButton,scrollPane,addEnterButton,removeEnterButton);
		placeNodes(this,nodes);
	}
	
	@Override
	public void placeNodes(GridPane gridpane, List<Node> nodes) {
		placeNode(gridpane,nodes.get(0),0,0,"right",null);
		placeNode(gridpane,nodes.get(1),1,0,"left",null);
		placeNode(gridpane,nodes.get(2),2,0,"right",null);
		placeNode(gridpane,nodes.get(3),3,0,"left",null);
		placeNode(gridpane,nodes.get(4),4,0,"right",null);
		placeNode(gridpane,nodes.get(5),5,0,"left",null);
		placeNode(gridpane,nodes.get(6),0,1,"right",null);
		placeNode(gridpane,nodes.get(7),1,1,"left",null);
		placeNode(gridpane,nodes.get(8),2,1,"right",null);
		placeNode(gridpane,nodes.get(9),3,1,"left",null);
		placeNode(gridpane,nodes.get(10),4,1,"right",null);
		placeNode(gridpane,nodes.get(11),5,1,"left",null);
		placeNodeSpan(gridpane,nodes.get(12),0,2,2,1,"center",null);
		placeNodeSpan(gridpane,nodes.get(13),2,2,2,1,"center",null);
		placeNodeSpan(gridpane,nodes.get(14),0,3,6,6,null,null);
		placeNodeSpan(gridpane,nodes.get(15),2,10,2,1,"center",null);
		placeNodeSpan(gridpane,nodes.get(16),2,10,2,1,"center",null);
	}
	
	private GridPane buildAddGridPane(Classroom classroom,Director director) {
		AddStudentPane newAddStudentPane = new AddStudentPane(classroom,director);
		return newAddStudentPane;
	}
	
	private GridPane buildRemoveGridPane(Classroom classroom,Director director) {
		RemoveStudentPane newRemoveStudentPane = new RemoveStudentPane(classroom,director);
		return newRemoveStudentPane;
	}
	
	/**
	 * Updates the ratios for each day for the given {@link Classroom} to check for {@link Student} overflow
	 * @param classroom - a {@link Classroom}
	 * @param labels - a List of Labels for each day's ratio
	 */
	private void updateStats(Classroom classroom,List<Label> labels) {
		count = 0;
		List<String> studentsEnrolled = classroom.getStudentsEnrolled();
		for(@SuppressWarnings("unused") String student : studentsEnrolled) {count++;}
		labels.get(0).setText("" + count + "");
		labels.get(1).setText(classroom.getCount("Monday") + "/" + classroom.getMaxSize());
		labels.get(2).setText(classroom.getCount("Tuesday") + "/" + classroom.getMaxSize());
		labels.get(3).setText(classroom.getCount("Wednesday") + "/" + classroom.getMaxSize());
		labels.get(4).setText(classroom.getCount("Thursday") + "/" + classroom.getMaxSize());
		labels.get(5).setText(classroom.getCount("Friday") + "/" + classroom.getMaxSize());
		overflowCheck(labels,classroom);
	}
	
	/**
	 * Puts together a String representing the given day's ratio
	 * @param classroom - a {@link Classroom}
	 * @param day - String representing the day of the week
	 * @return String ratio
	 */
	private String buildRatioString(Classroom classroom,String day) {
		String ratio = classroom.getCount(day) + "/" + classroom.getMaxSize();
		return ratio;
	}
	
	/**
	 * Checks to see if the {@link Classroom} is over ratio for any day. if the {@link Classroom} exceeds its maximum capacity, the ratio label is set to red
	 * @param labels - a list of Labels for each day of the week
	 * @param classroom - a {@link Classroom}
	 */
	private void overflowCheck(List<Label> labels,Classroom classroom) {
		List<String> daysOfTheWeek = Arrays.asList("Monday","Tuesday","Wednesday","Thursday","Friday");
		int dayIndex = 0;
		for(Label label : labels) {
			if(dayIndex == 5) {continue;}
			if(classroom.getCount(daysOfTheWeek.get(dayIndex)) > classroom.getMaxSize()) {label.setTextFill(Color.RED);}
			else {label.setTextFill(Color.BLACK);}
			label.getStyleClass().add("label");
			dayIndex++;
		}
	}
	
	/**
	 * Switches to the add {@link Student}s pane
	 */
	private void addEnterButtonSelect(ScrollPane scrollPane,Director director,Classroom classroom,List<Label> labels) {
		if(isAddPane == true) {
			List<Student> students = addStudentPane.getStudentsAdded();
			for(Student student : students) {director.addStudentToClassroom(student,classroom);}
			addStudentPane = (AddStudentPane) buildAddGridPane(classroom,director);
			scrollPane.setContent(addStudentPane);
			updateStats(classroom,labels);
		}
	}
	
	/**
	 * Switches to the remove {@link Student}s pane
	 */
	private void removeEnterButtonSelected(ScrollPane scrollPane,Director director,Classroom classroom,List<Label> labels) {
		if(isAddPane == false) {
			List<Student> students = removeStudentPane.getStudentsRemoved();
			for(Student student : students) {
				director.removeStudentFromClassroom(student,classroom);
			}
			removeStudentPane = (RemoveStudentPane) buildRemoveGridPane(classroom,director);
			scrollPane.setContent(removeStudentPane);
			updateStats(classroom,labels);
		}
	}
	
	/**
	 * Adds a {@link Student} to the passed {@link Classroom}
	 */
	private void add(ScrollPane scrollPane,Director director,Classroom classroom,ToggleButton removeButton,ToggleButton addButton,Button addEnterButton,Button removeEnterButton) {
		removeButton.setSelected(false);
		addButton.setSelected(true);
		addStudentPane = (AddStudentPane) buildAddGridPane(classroom,director);
		scrollPane.setContent(addStudentPane);
		addEnterButton.setVisible(true);
		removeEnterButton.setVisible(false);
		isAddPane = true;
	}
	
	/**
	 * Removes a {@link Student} from the passed {@link Classroom}
	 */
	private void remove(ScrollPane scrollPane,Director director,Classroom classroom,ToggleButton removeButton,ToggleButton addButton,Button addEnterButton,Button removeEnterButton) {
		removeButton.setSelected(true);
		addButton.setSelected(false);
		removeStudentPane = (RemoveStudentPane) buildRemoveGridPane(classroom,director);
		scrollPane.setContent(removeStudentPane);
		addEnterButton.setVisible(false);
		removeEnterButton.setVisible(true);
		isAddPane = false;
	}
	
}
