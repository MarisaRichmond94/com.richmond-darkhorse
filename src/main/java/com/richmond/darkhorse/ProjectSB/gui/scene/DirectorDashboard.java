package com.richmond.darkhorse.ProjectSB.gui.scene;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import com.richmond.darkhorse.ProjectSB.Center;
import com.richmond.darkhorse.ProjectSB.Classroom;
import com.richmond.darkhorse.ProjectSB.Director;
import com.richmond.darkhorse.ProjectSB.Event;
import com.richmond.darkhorse.ProjectSB.EventCalendar;
import com.richmond.darkhorse.ProjectSB.Schedule;
import com.richmond.darkhorse.ProjectSB.Teacher;
import com.richmond.darkhorse.ProjectSB.gui.component.AddEvent;
import com.richmond.darkhorse.ProjectSB.gui.component.ClassroomStatus;
import com.richmond.darkhorse.ProjectSB.gui.component.Description;
import com.richmond.darkhorse.ProjectSB.gui.component.DirectorLayout;
import com.richmond.darkhorse.ProjectSB.gui.component.ImageButton;
import com.richmond.darkhorse.ProjectSB.gui.component.ModifySchedules;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ChoiceBox;
import java.util.Calendar;

public class DirectorDashboard extends Scene implements DirectorLayout{
	
	private BorderPane directorDashboardLayout;
	private ScrollPane scrollPane;
	private List<ImageButton> buttons = new ArrayList<ImageButton>();
	private GridPane innerPane;
	@SuppressWarnings("unused")
	private boolean isEventPane = true;
	private EventCalendar eventCalendar;
	private List<Schedule> staffSchedules = new ArrayList<Schedule>();
	private Center center;
	private Map<String,Schedule> staffScheduleMap;
	
	public DirectorDashboard(Stage stage,Scene nextScene,Director director) {
		this(stage,new BorderPane(),nextScene,director);
	}
	
	public DirectorDashboard(Stage stage,BorderPane layout,Scene nextScene,Director director) {
		super(layout);
		this.center = director.getCenter(director.getCenterID());
		this.eventCalendar = center.getEventCalendar();
		this.staffScheduleMap = director.getSchedules();
		for(Schedule schedule : staffScheduleMap.values()) {staffSchedules.add(schedule);}
		HBox topPane = buildTopPane(stage,director);
		HBox bottomPane = buildBottomPane();
		VBox leftPane = buildLeftPane(stage,director);
		GridPane centerPane = buildGridPane(director);
		ScrollPane rightPane = buildRightPane(director);
		directorDashboardLayout = layout;
		setBorderPaneRightScroll(directorDashboardLayout,centerPane,rightPane,leftPane,topPane,bottomPane);
		directorDashboardLayout.getStylesheets().add("css/director.css");
	}
	
	/**
	 * Builds a GridPane and then places it inside of a ScrollPane to be returned for the left pane of the DirectorDashboard layout
	 * @param director - the current user
	 * @return a GridPane inside of a ScrollPane
	 */
	private ScrollPane buildRightPane(Director director) {
		this.innerPane = new GridPane();
		setConstraints(innerPane,4,0,10,10,"rightpane");
		Label title = createLabel("Ratios","subtitle");
		populateClassrooms(director);
		List<Node> nodes = new ArrayList<>();
		nodes.addAll(Arrays.asList(title));
		placeNodeSpan(innerPane,nodes.get(0),1,0,2,1,"center",null);
		ScrollPane rightPane = new ScrollPane(innerPane);
		rightPane.setFitToHeight(true);
		rightPane.setFitToWidth(true);
		return rightPane;
	}
	
	/**
	 * Populates the left portion of the BorderPane with buttons containing details about each {@link Classroom}, including: the name, total {@link Teacher}s, total
	 * {@link Student}s and the overall ratio of teacher to student 
	 * @param director - the current user
	 */
	private void populateClassrooms(Director director) {
		Map<String,Classroom> classrooms = director.getClassrooms();
		List<Classroom> rooms = new ArrayList<Classroom>();
		for(Classroom classroom : classrooms.values()) {rooms.add(classroom);}
		int row = 1;
		for(Classroom room : rooms) {
			String roomName = room.toString(), totalTeachers = "" + room.getTeachersPresent().size() + "", totalStudents = "" + room.getStudentsPresent().size() + "", roomRatio = null;
			if(room.getTeachersPresent().size() > 0) {roomRatio = "1:" + (room.getStudentsPresent().size())/(room.getTeachersPresent().size());
			}else {roomRatio = "0:0";}
			String buttonText = roomName + "\nTeachers (total): " + totalTeachers + "\nStudents (total): " + totalStudents + "\nRatio (current): " + roomRatio;
			Button newButton = createButton(buttonText,null,0,150,250);
			placeNodeSpan(innerPane,newButton,0,row,4,4,"center",null);
			if(room.getTeachersPresent().size() == 1 && room.getStudentsPresent().size() >= 13) {newButton.getStyleClass().add("badbutton");
			}else if(room.getTeachersPresent().size() == 2 && room.getStudentsPresent().size() >= 19) {newButton.getStyleClass().add("badbutton");
			}else if(room.getStudentsPresent().size() >= 19) {newButton.getStyleClass().add("badbutton");
			}else {newButton.getStyleClass().add("goodbutton");}
			newButton.setOnAction(e -> {
				ClassroomStatus classroomStatus = new ClassroomStatus(director,room);
				classroomStatus.display();
			});
			row = row+4;
		}
	}
	
	/**
	 * Builds the central GridPane for the overall BorderPane layout of DirectorDashboard
	 * @param director - the current user
	 * @return - a fully assembled GridPane
	 */
	public GridPane buildGridPane(Director director) {
		GridPane gridpane = new GridPane();
		setConstraints(gridpane,4,0,10,10,"gridpane");
		Button modSchedule = createButton("modify schedule",null,0,0,0);
	    modSchedule.setVisible(false);
	    modSchedule.setOnAction(e -> modifySchedule(director));
	    Button clearSchedule = createButton("clear schedule",null,0,0,0);
	    clearSchedule.setOnAction(e -> director.clearSchedules());
	    clearSchedule.setVisible(false);
	    Button addButton = createButton("add event",null,0,0,0);
	    addButton.setOnAction(e -> addEvent(director));
	    Button removeButton = createButton("remove event",null,0,0,0);
	    Button clearButton = createButton("clear all",null,0,0,0);
	    clearButton.setVisible(false);
	    clearButton.setOnAction(e -> clear(director,clearButton,removeButton));
	    removeButton.setOnAction(e -> remove(removeButton,clearButton));
	    ChoiceBox<String> viewBox = new ChoiceBox<String>();
	    viewBox.getItems().addAll("event view","schedule view");
	    viewBox.setValue(viewBox.getItems().get(0));
	    viewBox.getStyleClass().add("choice-box-mini");
	    ChangeListener<String> changeListener = new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if(newValue == "event view") {
					innerPane = buildEventPane(director);
					scrollPane.setContent(innerPane);
					addButton.setVisible(true);
					removeButton.setVisible(true);
					clearSchedule.setVisible(false);
					modSchedule.setVisible(false);
					isEventPane = true;
				}else if(newValue == "schedule view") {
					innerPane = buildSchedulePane(director);
					scrollPane.setContent(innerPane);
					addButton.setVisible(false);
					removeButton.setVisible(false);
					clearButton.setVisible(false);
					modSchedule.setVisible(true);
					clearSchedule.setVisible(true);
					isEventPane = false;
				}
			}
        };
        viewBox.getSelectionModel().selectedItemProperty().addListener(changeListener);
	    Calendar calendar = Calendar.getInstance();
	    int monthNum = calendar.get(Calendar.MONTH);
	    Label month = createLabel(getMonthNumToString(monthNum),"med-title");
	    ImageButton scheduleButton = new ImageButton(createImageWithFitHeight("images/schedule.png",100));
	    List<Node> nodes = new ArrayList<>();
	    nodes.addAll(Arrays.asList(viewBox,month,scheduleButton));
	    placeNodes(gridpane,nodes);
	    innerPane = buildEventPane(director);
	    scrollPane = new ScrollPane(innerPane);
	    scrollPane.setFitToHeight(true);
	    scrollPane.setFitToWidth(true);
	    scrollPane.setPrefSize(1000, 1000);
	    placeNodeSpan(gridpane,scrollPane,0,1,4,4,null,null);
	    placeNode(gridpane,addButton,1,5,"center",null);
	    placeNode(gridpane,removeButton,2,5,"center",null);
	    placeNode(gridpane,clearButton,2,5,"center",null);
	    placeNode(gridpane,modSchedule,1,5,"center",null);
	    placeNode(gridpane,clearSchedule,2,5,"center",null);
		return gridpane;
	}
	
	@Override
	public void placeNodes(GridPane gridpane, List<Node> nodes) {
		placeNode(gridpane,nodes.get(0),3,0,"center",null);
	    placeNodeSpan(gridpane,nodes.get(1),1,0,2,1,"center",null);
	    placeNode(gridpane,nodes.get(2),0,0,"center",null);
	}
	
	/**
	 * Modifies the {@link Schedule}
	 * @param director - the current user
	 */
	private void modifySchedule(Director director) {
		ModifySchedules modifySchedules = new ModifySchedules(director);
		modifySchedules.display();
		refreshSchedulePane(director);
	}
	
	/**
	 * Adds a new {@link Event}
	 * @param director - the current user
	 */
	private void addEvent(Director director) {
		AddEvent addEvent = new AddEvent(director);
		addEvent.display();
		refreshEventPane(director);
	}
	
	/**
	 * Clears the {@link Calendar}
	 * @param director - the current user
	 * @param clearButton
	 * @param removeButton
	 */
	private void clear(Director director,Button clearButton,Button removeButton) {
		eventCalendar.clear();
		clearButton.setVisible(false);
		removeButton.setVisible(true);
		refreshEventPane(director);
	}
	
	/**
	 * Removes the selected {@link Event}
	 * @param removeButton
	 * @param clearButton
	 */
	private void remove(Button removeButton,Button clearButton) {
		revealButtons(buttons);
		removeButton.setVisible(false);
		clearButton.setVisible(true);
	}
	
	/**
	 * Builds an event pane
	 * @param director - the current user
	 * @return GridPane
	 */
	public GridPane buildEventPane(Director director) {
	    GridPane eventPane = new GridPane();
	    buildCalendarView(eventPane);
	    List<Event> events = eventCalendar.getEvents();
	    for(Event event : events) {addEvent(event,eventPane,director);}
	    return eventPane;
	}
	
	/**
	 * Builds an schedule pane
	 * @param director - the current user
	 * @return GridPane
	 */
	private GridPane buildSchedulePane(Director director) {
		GridPane schedulePane = new GridPane();
		buildCalendarView(schedulePane);
		populateClassroomButtons(director,schedulePane);
		return schedulePane;
	}
	
	//TODO If you run into any problems, check here first. This is probably the problem
	/**
	 * Populates the {@link Schedule} with the {@link Classroom} buttons as well as the {@link Teacher}s and each of their {@link Schedule}s
	 * @param director - the current user
	 * @param gridpane - the layout
	 */
	public void populateClassroomButtons(Director director,GridPane gridpane) {
		int rowIndex = 1;
		Center center = director.getCenter(director.getCenterID());
		Map<String,Classroom> classrooms = center.getClassrooms();
		int columnCount = 1;
		while(columnCount < 6) {
			for(Classroom classroom : classrooms.values()) {
				int columnIndex = getColumnIndex(columnCount);
				Button newButton = new Button();
				newButton.getStyleClass().add("button");
				Teacher leadTeacher = null;
				String lead = "N/A", leadSchedule = "N/A";
				if(classroom.getTeacherID() != null) {
					leadTeacher = classroom.getTeacher(classroom.getTeacherID());
					lead = leadTeacher.getFirstName() + " " + leadTeacher.getLastName();
					leadSchedule = leadTeacher.getSchedule().getStartTimes().get(columnCount) + "-" + leadTeacher.getSchedule().getStopTimes().get(columnCount);
				}
				Teacher assistantTeacher = null;
				String assistant = "N/A", assistantSchedule = "N/A";
				if(classroom.getAssistantTeacherID() != null) {
					if(classroom.getAssistantTeacher(classroom.getAssistantTeacherID()).getSchedule().getStartTimes().get(columnCount) != null) {
						assistantTeacher = classroom.getAssistantTeacher(classroom.getAssistantTeacherID());
						assistant = assistantTeacher.getFirstName() + " " + assistantTeacher.getLastName();
						assistantSchedule = assistantTeacher.getSchedule().getStartTimes().get(columnCount) + "-" + assistantTeacher.getSchedule().getStopTimes().get(columnCount);
					}
				}
				if(classroom.getMaxSize() > 12) {
					newButton.setPrefSize(300,300);
					if(leadTeacher != null && leadTeacher.getSchedule().getStartTimes().get(columnCount) != null && assistantTeacher != null && assistantTeacher.getSchedule().getStartTimes().get(columnCount) != null) {
						newButton.setText(classroom.toString() + "\n" + lead + "\n" + leadSchedule + "\n" + assistant + "\n" + assistantSchedule);
						newButton.getStyleClass().add("goodbutton");
					}else if(leadTeacher != null && leadTeacher.getSchedule().getStartTimes().get(columnCount) != null){
						newButton.setText(classroom.toString() + "\n" + lead + "\n" + leadSchedule);
						newButton.getStyleClass().add("badbutton");
					}else{
						newButton.setText(classroom.toString());
						newButton.getStyleClass().add("badbutton");
					}
					placeNodeSpan(gridpane,newButton,columnIndex,rowIndex,2,5,"center",null);
					rowIndex = rowIndex+5;
				}else if(classroom.getMaxSize() <= 12) {
					newButton.setPrefSize(300,200);
					if(leadTeacher.getSchedule().getStartTimes().get(columnCount) != null){
						newButton.setText(classroom.toString() + "\n" + lead + "\n" + leadSchedule);
						newButton.getStyleClass().add("goodbutton");
					}else {
						newButton.setText(classroom.toString());
						newButton.getStyleClass().add("badbutton");
					}
					placeNodeSpan(gridpane,newButton,columnIndex,rowIndex,2,3,"center",null);
					rowIndex = rowIndex+3;
				}
			}
			rowIndex = 1;
			columnCount++;
		}
	}
	
	/**
	 * Adds a new {@link Event} to the {@link Calendar}
	 * @param event - the new {@link Event} to be added
	 * @param gridPane - the layout
	 * @param director - the current user
	 */
	private void addEvent(Event event,GridPane gridPane,Director director) {
		int dayInt = event.getDayOfWeek();
		String day = event.getDay();
		int eventPosition = getEventPosition(event,day), rowIndex = getRowIndex(eventPosition), columnIndex = getColumnIndex(dayInt);
		String summary = event.getSummary(), time = null;
		if(event.getTime() != null) {time = event.getTime();}
		Button newButton = new Button();
		newButton.getStyleClass().add("eventbutton");
		if(time != null) {newButton.setText(summary + "\n" + time);}
		else {newButton.setText(summary);}
		newButton.setOnAction(e -> {
			Description description = new Description(event);
			description.display();
		});
		placeNodeSpan(gridPane,newButton,columnIndex,rowIndex,2,2,"center",null);
		ImageButton trashButton = createTrashButton();
		buttons.add(trashButton);
		ImageButton cancelButton = createCancelButton();
		buttons.add(cancelButton);
		cancelButton.setOnAction(e -> {
			hideButtons(buttons);
			refreshEventPane(director);
		});
		trashButton.setOnAction(e -> {
			director.getCenter(director.getCenterID()).getEventCalendar().removeEvent(event);
			buttons.remove(trashButton);
			buttons.remove(cancelButton);
			refreshEventPane(director);
		});
		placeNode(gridPane,trashButton,columnIndex,rowIndex+2,"center",null);
		placeNode(gridPane,cancelButton,columnIndex+1,rowIndex+2,"center",null);
	}

	/**
	 * Creates a trash button
	 * @return Button
	 */
	private ImageButton createTrashButton() {
		ImageButton trashButton = new ImageButton(createImageWithFitHeight("images/trash.png",50));
		trashButton.setVisible(false);
		return trashButton;
	}
	
	/**
	 * Creates a cancel button
	 * @return Button
	 */
	private ImageButton createCancelButton() {
		ImageButton cancelButton = new ImageButton(createImageWithFitHeight("images/cancel.png",40));
		cancelButton.setVisible(false);
		return cancelButton;
	}
	
	/**
	 * Gets the row number (integer) of an {@link Event} based on the day of the {@link Event}
	 * @param event - the current event
	 * @param day - a String representing the day
	 * @return an integer representing the day
	 */
	private int getEventPosition(Event event,String day) {
		int eventPosition = 0;
		if(day.equals("Monday")) {
			List<Event> mondayEvents = eventCalendar.getMondayEvents();
			int index = 0;
			for(Event mondayEvent : mondayEvents) {
				if(mondayEvent.equals(event)) {eventPosition = index;}
				index++;
			}
		}
		else if(day.equals("Tuesday")) {
			List<Event> tuesdayEvents = eventCalendar.getTuesdayEvents();
			int index = 0;
			for(Event tuesdayEvent : tuesdayEvents) {
				if(tuesdayEvent.equals(event)) {eventPosition = index;}
				index++;
			}
		}
		else if(day.equals("Wednesday")) {
			List<Event> wednesdayEvents = eventCalendar.getWednesdayEvents();
			int index = 0;
			for(Event wednesdayEvent : wednesdayEvents) {
				if(wednesdayEvent.equals(event)) {eventPosition = index;}
				index++;
			}
		}
		else if(day.equals("Thursday")) {
			List<Event> thursdayEvents = eventCalendar.getThursdayEvents();
			int index = 0;
			for(Event thursdayEvent : thursdayEvents) {
				if(thursdayEvent.equals(event)) {eventPosition = index;}
				index++;
			}
		}
		else {
			List<Event> fridayEvents = eventCalendar.getFridayEvents();
			int index = 0;
			for(Event fridayEvent : fridayEvents) {
				if(fridayEvent.equals(event)) {eventPosition = index;}
				index++;
			}
		}
		return eventPosition;
	}
	
	/**
	 * Gets the row index based on an integer position of an {@link Event}
	 * @param eventPosition - the column position of an {@link Event} based on the day of that {@link Event}
	 * @return an integer representing the row position, based on how many {@link Event}s are already on that day
	 */
	private int getRowIndex(int eventPosition) {
		int rowIndex = 0;
		if(eventPosition == 0) {rowIndex = 2;}
		else { rowIndex = (eventPosition * 4) + 1; }
		return rowIndex;
	}
	
	/**
	 * Gets a column index based on the day of the week
	 * @param dayInt - an int between 1 and 5 representing the day of the week
	 * @return a column index
	 */
	private int getColumnIndex(int dayInt) {
		int columnIndex = 0;
		if(dayInt == 1) { columnIndex = 0; }
		else if(dayInt == 2) { columnIndex = 2; }
		else if(dayInt == 3) { columnIndex = 4; }
		else if(dayInt == 4) { columnIndex = 6; }
		else { columnIndex = 8; }
		return columnIndex;
	}
	
	//Calendar creation methods
	private void buildCalendarView(GridPane gridpane) {
		setConstraints(gridpane,10,0,10,10,"gridpane");
	    Calendar calendar = Calendar.getInstance();
	    int dayOfWeekInt = calendar.get(Calendar.DAY_OF_WEEK);
	    String dayOfWeek = getDayOfWeek(dayOfWeekInt);
	    List<Integer> dates = getDates(dayOfWeekInt,dayOfWeek);
	    Label monday = createLabel("Monday " + dates.get(0) + "","boxlabel");
	    Label tuesday = createLabel ("Tuesday " + dates.get(1),"boxlabel");
	    Label wednesday = createLabel("Wednesday " + dates.get(2),"boxlabel");
	    Label thursday = createLabel("Thursday " + dates.get(3),"boxlabel");
	    Label friday = createLabel("Friday " + dates.get(4),"boxlabel");
	    placeNodeSpan(gridpane,monday,0,0,2,1,"center",null);
	    placeNodeSpan(gridpane,tuesday,2,0,2,1,"center",null);
	    placeNodeSpan(gridpane,wednesday,4,0,2,1,"center",null);
	    placeNodeSpan(gridpane,thursday,6,0,2,1,"center",null);
	    placeNodeSpan(gridpane,friday,8,0,2,1,"center",null);
	}
	
	/**
	 * Gets the month of the year
	 * @param monthNum - a number between 0 and 11 representing the month of the year
	 * @return a String "month"
	 */
	private String getMonthNumToString(int monthNum) {
		String month = null;
		if(monthNum == 0) {month = "January";}
		if(monthNum == 1) {month = "February";}
		if(monthNum == 2) {month = "March";}
		if(monthNum == 3) {month = "April";}
		if(monthNum == 4) {month = "May";}
		if(monthNum == 5) {month = "June";}
		if(monthNum == 6) {month = "July";}
		if(monthNum == 7) {month = "August";}
		if(monthNum == 8) {month = "September";}
		if(monthNum == 9) {month = "October";}
		if(monthNum == 10) {month = "November";}
		if(monthNum == 11) {month = "December";}
		return month;
	}
	
	/**
	 * Gets the day of the week
	 * @param dayOfWeekInt - a number between 2 and 6 representing the day of the week
	 * @return the day of the week
	 */
	private String getDayOfWeek(int dayOfWeekInt) {
		String dayOfWeek = null;
		if(dayOfWeekInt == 2) {dayOfWeek = "Monday";}
		else if(dayOfWeekInt == 3) {dayOfWeek = "Tuesday";}
		else if(dayOfWeekInt == 4) {dayOfWeek = "Wednesday";}
		else if(dayOfWeekInt == 5) {dayOfWeek = "Thursday";}
		else if(dayOfWeekInt == 6) {dayOfWeek = "Friday";}
		return dayOfWeek;
	}
	
	/**
	 * Gets a list of unordered dates
	 * @param dayOfWeekInt
	 * @param dayOfWeek
	 * @return a list of unordered dates
	 */
	private List<Integer> getDates(int dayOfWeekInt,String dayOfWeek){
		List<Integer> dates = new ArrayList<Integer>();
		Calendar now = Calendar.getInstance(); 
		int dateStart = now.get(Calendar.DATE);
		if(dayOfWeek == null) {
	    		if(dayOfWeekInt == 1) {
	    			dateStart = dateStart+1;
	    			dayOfWeekInt = 2;
	    		}else if(dayOfWeekInt == 7) {
	    			dateStart = dateStart+2;
	    			dayOfWeekInt = 2;
	    		}
		}
		dates.add(dateStart); 
		while(dayOfWeekInt < 7) { 
			int date = dateStart+1;
			dates.add(date);
			dayOfWeekInt++;
			dateStart++;
		}
		if(dates.size() < 5) { 
			int difference = 5 - dates.size();  
			int newDateStart = dateStart - difference; 
			while(newDateStart < dateStart) { 
				dates.add(newDateStart); 
				newDateStart = newDateStart+1; 
			}
		}
		List<Integer> sortedList = sort(dates);
		return sortedList;
	}
	
	/**
	 * Sorts a list of dates
	 * @param dates - a list of unordered dates
	 * @return a list of ordered dates
	 */
	private List<Integer> sort(List<Integer> dates) {
		boolean possibleSwitchesPresent = true;
		while(possibleSwitchesPresent == true) { 
			int compareIndex = 0;
			int compareToIndex = 1;
			boolean switchStatus = false;
			while(switchStatus == false || compareToIndex <= 4) {
				if(compareToIndex > 4) {
					possibleSwitchesPresent = false;
					break;
				}
				int compare = dates.get(compareIndex);
				int compareTo = dates.get(compareToIndex);
				if(compare > compareTo) {
					int temp = compare; 
					dates.set(compareIndex, compareTo);
					dates.set(compareToIndex,temp);
					switchStatus = true; 
					possibleSwitchesPresent = true;
				}else {possibleSwitchesPresent = false;}
				compareIndex++; 
				compareToIndex++;
			}
		}
		return dates;
	}
	
	public void hideButtons(List<ImageButton> buttons) {
		for(ImageButton button : buttons) {button.setVisible(false);}
	}
	
	public void revealButtons(List<ImageButton> buttons) {
		for(ImageButton button : buttons) {button.setVisible(true);}
	}
	
	public void addButton(ImageButton button) {
		buttons.add(button);
	}
	
	public List<ImageButton> getButtons(){
		return buttons;
	}
	
	/**
	 * Reloads the event pane
	 * @param director - the current user
	 */
	public void refreshEventPane(Director director) {
		innerPane = buildEventPane(director);
		scrollPane.setContent(innerPane);
	}
	
	/**
	 * Reloads the schedule pane
	 * @param director - the current user
	 */
	public void refreshSchedulePane(Director director) {
		innerPane = buildSchedulePane(director);
		scrollPane.setContent(innerPane);
	}
	
}

