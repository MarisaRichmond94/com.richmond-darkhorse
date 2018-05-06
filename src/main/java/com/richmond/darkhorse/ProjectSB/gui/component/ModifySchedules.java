package com.richmond.darkhorse.ProjectSB.gui.component;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import com.richmond.darkhorse.ProjectSB.Director;
import com.richmond.darkhorse.ProjectSB.Schedule;
import com.richmond.darkhorse.ProjectSB.StaffMember;
import com.richmond.darkhorse.ProjectSB.Teacher;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ModifySchedules implements DirectorLayout{

	private Director director;
	private Map<Integer,LocalTime> startTimes,stopTimes,newStartTimes,newStopTimes;
	private List<Teacher> teachers;
	private int staffIndex;
	private Teacher teacher;
	private Schedule schedule;
	
	public ModifySchedules(Director director) {
		this.director = director;
		this.teachers = new ArrayList<Teacher>();
		Map<String,StaffMember> staffMembers = director.getStaffMembers();
		for(StaffMember staffmember : staffMembers.values()) {
			if(staffmember.getTitle().equals("Teacher")) {teachers.add((Teacher) staffmember);}
		}
		this.staffIndex = 0;
		this.teacher = teachers.get(staffIndex); 
		this.schedule = teacher.getSchedule();
		this.startTimes = schedule.getStartTimes();
		this.stopTimes = schedule.getStopTimes();
		this.newStartTimes = new HashMap<Integer,LocalTime>();
		this.newStopTimes = new HashMap<Integer,LocalTime>();
	}
	
	public void display() {
		Stage stage = new Stage();
		GridPane modifySchedulesLayout = buildGridPane(stage);
		buildPopUp(stage,modifySchedulesLayout,"Modify");
	}
	
	/**
	 * Builds the default GridPane
	 * @param stage - the current stage
	 * @return GridPane
	 */
	private GridPane buildGridPane(Stage stage) {
		GridPane gridpane = new GridPane();
		setConstraints(gridpane,4,0,0,0,"modulargridpane");
	    gridpane.getStylesheets().add("css/director.css");
	    loadDisplayPane(gridpane,stage);
	    ChoiceBox<Teacher> teacherBox = buildTeacherBox(gridpane,stage);
	    Label selectTeacher = createLabel("view:","label");
	    placeNode(gridpane,selectTeacher,2,0,"right",null);
	    placeNode(gridpane,teacherBox,3,0,"left",null);
	    return gridpane;
	}
	
	private ChoiceBox<Teacher> buildTeacherBox(GridPane gridpane,Stage stage){
		ChoiceBox<Teacher> teacherBox = new ChoiceBox<Teacher>();
	    for(Teacher teacher : teachers) {teacherBox.getItems().add(teacher);}
	    teacherBox.setValue(teacherBox.getItems().get(staffIndex));
	    teacherBox.getSelectionModel().selectedItemProperty().addListener((ov,oldTeacher,newTeacher) -> {
	    		teacher = newTeacher;
        		boolean match = false;
			int count = 0;
			while(match == false && count < teachers.size()) {
				Teacher teacherCheck = teachers.get(count);
				if(teacherCheck.equals(newTeacher)) {match = true;}
				count++;
			}
			staffIndex = count-1;
			schedule = teacher.getSchedule();
			startTimes = schedule.getStartTimes();
			stopTimes = schedule.getStopTimes();
			gridpane.getChildren().clear();
			buildViewMenu(gridpane,stage);
			loadDisplayPane(gridpane,stage);
        });
	    teacherBox.getStyleClass().add("choice-box-mini");
	    return teacherBox;
	}
	
	/**
	 * Builds the view menu
	 * @param gridpane - GridPane
	 * @param stage - the current stage
	 */
	private void buildViewMenu(GridPane gridpane,Stage stage) {
	    ChoiceBox<Teacher> teacherBox = buildTeacherBox(gridpane,stage);
	    placeNode(gridpane,teacherBox,3,0,"left",null);
	}
	
	/**
	 * Makes an end time TextField
	 * @param index - index representing the day
	 * @return TextField
	 */
	private TextField getStart(int index) {
		TextField dayStart;
		String start = " ";
		if(startTimes.get(index) != null & startTimes.size() > 0) {
			start = "" + startTimes.get(index) + "";
			dayStart = new TextField(start);
		}else {dayStart = new TextField();}
	    dayStart.getStyleClass().add("textfield");
	    return dayStart;
	}
	
	/**
	 * Makes a start time TextField
	 * @param index - index representing the day
	 * @return TextField
	 */
	private TextField getEnd(int index) {
		TextField dayEnd;
		String end = "";
		if(stopTimes.get(index) != null & stopTimes.size() > 0) {
			end = "" + stopTimes.get(index) + "";
			dayEnd = new TextField(end);
		}else {dayEnd = new TextField();}
		dayEnd.getStyleClass().add("textfield");
		return dayEnd;
	}
	
	/**
	 * Writes any changes made to the {@link StaffMember}'s {@link Schedule}
	 * @param stage - the current stage
	 * @param starts - start times
	 * @param ends - end times
	 */
	private void write(Stage stage,GridPane gridpane,List<TextField> starts,List<TextField> ends) {
		List<LocalTime> startTimes = populateStartTimes(starts);
		List<LocalTime> endTimes = populateEndTimes(ends);
		newStartTimes.put(1,startTimes.get(0));
		newStartTimes.put(2,startTimes.get(1));
		newStartTimes.put(3,startTimes.get(2));
		newStartTimes.put(4,startTimes.get(3));
		newStartTimes.put(5,startTimes.get(4));
		newStopTimes.put(1,endTimes.get(0));
		newStopTimes.put(2,endTimes.get(1));
		newStopTimes.put(3,endTimes.get(2));
		newStopTimes.put(4,endTimes.get(3));
		newStopTimes.put(5,endTimes.get(4));
		director.modifyStaffSchedule(teacher,newStartTimes,newStopTimes);
		Label saveSuccessful = createLabel("save successful!","label");
		saveSuccessful.setTextFill(Color.GREENYELLOW);
		placeNodeSpan(gridpane,saveSuccessful,0,7,4,1,"center",null);
	}
	
	private List<LocalTime> populateStartTimes(List<TextField> starts){
		List<LocalTime> startTimes = new ArrayList<>();
		for(TextField start : starts) {
			char[] characters = start.getText().toCharArray();
			if(characters.length == 4) {
				int hour = Integer.parseInt(String.valueOf(characters[0])), minutes = Integer.parseInt(String.valueOf(characters[2]))*10 + Integer.parseInt(String.valueOf(characters[3]));
				LocalTime startTime = LocalTime.of(hour,minutes);
				startTimes.add(startTime);
			}else if(characters.length == 5){
				int hour = Integer.parseInt(String.valueOf(characters[0]))*10 + Integer.parseInt(String.valueOf(characters[1])), minutes = Integer.parseInt(String.valueOf(characters[3]))*10 + Integer.parseInt(String.valueOf(characters[4]));
				LocalTime startTime = LocalTime.of(hour,minutes);
				startTimes.add(startTime);
			}else if(characters.length == 0) {startTimes.add(null);}
		}
		return startTimes;
	}
	
	private List<LocalTime> populateEndTimes(List<TextField> ends){
		List<LocalTime> endTimes = new ArrayList<>();
		for(TextField end : ends) {
			char[] characters = end.getText().toCharArray();
			if(characters.length == 4) {
				int hour = Integer.parseInt(String.valueOf(characters[0])), minutes = Integer.parseInt(String.valueOf(characters[2]))*10 + Integer.parseInt(String.valueOf(characters[3]));
				LocalTime startTime = LocalTime.of(hour,minutes);
				endTimes.add(startTime);
			}else if(characters.length == 5){
				int hour = Integer.parseInt(String.valueOf(characters[0]))*10 + Integer.parseInt(String.valueOf(characters[1])), minutes = Integer.parseInt(String.valueOf(characters[3]))*10 + Integer.parseInt(String.valueOf(characters[4]));
				LocalTime startTime = LocalTime.of(hour,minutes);
				endTimes.add(startTime);
			}else if(characters.length == 0) {endTimes.add(null);}
		}
		return endTimes;
	}
	
	/**
	 * Enables edit mode
	 */
	private void edit(Button editButton,Button writeButton,List<TextField> starts,List<TextField> ends) {
		editButton.setVisible(false);
		writeButton.setVisible(true);
		starts.get(0).setDisable(false);
		ends.get(0).setDisable(false);
		starts.get(1).setDisable(false);
		ends.get(1).setDisable(false);
		starts.get(2).setDisable(false);
		ends.get(2).setDisable(false);
		starts.get(3).setDisable(false);
		ends.get(3).setDisable(false);
		starts.get(4).setDisable(false);
		ends.get(4).setDisable(false);
	}
	
	/**
	 * Loads the GridPane to be displayed
	 * @param gridpane - GridPane
	 * @param stage - the current stage
	 */
	private void loadDisplayPane(GridPane gridpane,Stage stage) {
	    Label teacherTitle = new Label(teacher.getFirstName() + " " + teacher.getLastName());
	    teacherTitle.getStyleClass().add("super-subtitle");
	    placeNodeSpan(gridpane,teacherTitle,0,1,4,1,"center",null);
	    Label selectTeacher = createLabel("view:","label");
	    List<Label> labels = populateLabels(Arrays.asList("monday:","tuesday:","wednesday:","thursday:","friday:"),"label");
	    TextField mondayStart = getStart(1), mondayEnd = getEnd(1), tuesdayStart = getStart(2), tuesdayEnd = getEnd(2), wednesdayStart = getStart(3), wednesdayEnd = getEnd(3);
	    TextField thursdayStart = getStart(4), thursdayEnd = getEnd(4), fridayStart = getStart(5), fridayEnd = getEnd(5);
	    placeDay(gridpane,labels.get(0),mondayStart,mondayEnd,2);
	    placeDay(gridpane,labels.get(1),tuesdayStart,tuesdayEnd,3);
	    placeDay(gridpane,labels.get(2),wednesdayStart,wednesdayEnd,4);
	    placeDay(gridpane,labels.get(3),thursdayStart,thursdayEnd,5);
	    placeDay(gridpane,labels.get(4),fridayStart,fridayEnd,6);
		Button editButton = createButton("edit",null,0,0,0);
		Button writeButton = createButton("write",null,0,0,0);
		List<TextField> starts = Arrays.asList(mondayStart,tuesdayStart,wednesdayStart,thursdayStart,fridayStart);
		List<TextField> ends = Arrays.asList(mondayEnd,tuesdayEnd,wednesdayEnd,thursdayEnd,fridayEnd);
		writeButton.setOnAction(e -> write(stage,gridpane,starts,ends));
		editButton.setOnAction(e -> edit(editButton,writeButton,starts,ends));
		writeButton.setVisible(false);
		Button cancelButton = createButton("cancel",null,0,0,0);
		cancelButton.setOnAction(e -> stage.close());
		placeNode(gridpane,selectTeacher,2,0,"right",null);
		placeNode(gridpane,editButton,1,8,"center",null);
		placeNode(gridpane,writeButton,1,8,"center",null);
		placeNode(gridpane,cancelButton,2,8,"center",null);
	}
	
	/**
	 * Places each day's nodes
	 */
	private void placeDay(GridPane gridpane,Label day,TextField dayStart,TextField dayEnd,int rowIndex) {
		dayStart.setDisable(true);
		dayEnd.setDisable(true);
		dayStart.setMaxWidth(300);
		dayEnd.setMaxWidth(300);
		placeNode(gridpane,day,0,rowIndex,"right",null);
		placeNode(gridpane,dayStart,1,rowIndex,"center",null);
		placeNode(gridpane,dayEnd,2,rowIndex,"center",null);
	}

	@Override
	public void placeNodes(GridPane gridpane, List<Node> nodes) {}
	
}
