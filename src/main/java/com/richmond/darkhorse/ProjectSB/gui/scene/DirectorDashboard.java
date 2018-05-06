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
import com.richmond.darkhorse.ProjectSB.gui.component.ScheduleHelper;
import com.richmond.darkhorse.ProjectSB.middleman.ChangeScene;
import javafx.application.Platform;
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
import java.time.*;

public class DirectorDashboard extends Scene implements DirectorLayout, ScheduleHelper{
	
	private BorderPane directorDashboardLayout;
	private ScrollPane scrollPane;
	private List<ImageButton> buttons = new ArrayList<ImageButton>();
	private List<Button> defaultButtons = new ArrayList<Button>(), editButtons = new ArrayList<Button>(), scheduleButtons = new ArrayList<Button>();
	private GridPane innerPane;
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
		GridPane centerPane = buildGridPane(stage,director);
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
		List<Node> nodes = Arrays.asList(title);
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
	public GridPane buildGridPane(Stage stage,Director director) {
		GridPane gridpane = new GridPane();
		setConstraints(gridpane,4,0,10,10,"gridpane");
		Button modSchedule = createButton("modify schedule",null,0,0,0);
	    modSchedule.setOnAction(e -> modifySchedule(stage,director));
	    Button clearSchedule = createButton("clear schedule",null,0,0,0);
	    clearSchedule.setOnAction(e -> {
	    		director.clearSchedules();
	    		refreshSchedulePane(stage,director);
	    });
	    Button addButton = createButton("add event",null,0,0,0), removeButton = createButton("remove event",null,0,0,0), clearButton = createButton("clear all",null,0,0,0), cancelButton = createButton("cancel",null,0,0,0);
	    defaultButtons.add(addButton);
	    defaultButtons.add(removeButton);
	    editButtons.add(clearButton);
	    editButtons.add(cancelButton);
	    scheduleButtons.add(modSchedule);
	    scheduleButtons.add(clearSchedule);
	    addButton.setOnAction(e -> addEvent(director));
	    clearButton.setOnAction(e -> clear(director));
	    removeButton.setOnAction(e -> remove());
	    cancelButton.setOnAction(e -> cancel());
	    clearButton.setVisible(false);
	    cancelButton.setVisible(false);
	    addButton.setVisible(false);
	    removeButton.setVisible(false);
	    ChoiceBox<String> viewBox = new ChoiceBox<String>();
	    viewBox.getItems().addAll("schedule view","event view");
	    viewBox.setValue(viewBox.getItems().get(0));
	    viewBox.getStyleClass().add("choice-box-mini");
	    ChangeListener<String> changeListener = new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if(newValue == "event view") {
					innerPane = buildEventPane(director);
					scrollPane.setContent(innerPane);
					for(Button editButton : editButtons) {editButton.setVisible(false);}
					for(Button defaultButton : defaultButtons) {defaultButton.setVisible(true);}
					for(Button scheduleButton : scheduleButtons) {scheduleButton.setVisible(false);}
				}else if(newValue == "schedule view") {
					innerPane = buildSchedulePane(stage,director);
					scrollPane.setContent(innerPane);
					for(Button editButton : editButtons) {editButton.setVisible(false);}
					for(Button defaultButton : defaultButtons) {defaultButton.setVisible(false);}
					for(Button scheduleButton : scheduleButtons) {scheduleButton.setVisible(true);}
				}
			}
        };
        viewBox.getSelectionModel().selectedItemProperty().addListener(changeListener);
        LocalDate date = LocalDate.now();
        String month = date.getMonth().toString(), finalMonth = month.substring(0,1) + month.substring(1).toLowerCase();
	    Label monthLabel = createLabel(finalMonth,"med-title");
	    ImageButton scheduleButton = new ImageButton(createImageWithFitHeight("images/schedule.png",100));
	    List<Node> nodes = Arrays.asList(viewBox,monthLabel,scheduleButton);
	    placeNodes(gridpane,nodes);
	    innerPane = buildSchedulePane(stage,director);
	    scrollPane = new ScrollPane(innerPane);
	    scrollPane.setFitToHeight(true);
	    scrollPane.setFitToWidth(true);
	    scrollPane.setPrefSize(1000, 1000);
	    placeNodeSpan(gridpane,scrollPane,0,1,4,4,null,null);
	    placeNode(gridpane,addButton,1,5,"center",null);
	    placeNode(gridpane,removeButton,2,5,"center",null);
	    placeNode(gridpane,clearButton,1,5,"center",null);
	    placeNode(gridpane,cancelButton,2,5,"center",null);
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
	private void modifySchedule(Stage stage,Director director) {
		ModifySchedules modifySchedules = new ModifySchedules(director);
		modifySchedules.display();
		refreshSchedulePane(stage,director);
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
	private void clear(Director director) {
		eventCalendar.clear();
		for(Button defaultButton : defaultButtons) {defaultButton.setVisible(true);}
		for(Button editButton : editButtons) {editButton.setVisible(false);}
		refreshEventPane(director);
	}
	
	/**
	 * Removes the selected {@link Event}
	 * @param removeButton
	 * @param clearButton
	 */
	private void remove() {
		revealButtons(buttons);
		for(Button defaultButton : defaultButtons) {defaultButton.setVisible(false);}
		for(Button editButton : editButtons) {editButton.setVisible(true);}
	}
	
	private void cancel() {
		hideButtons(buttons);
		for(Button defaultButton : defaultButtons) {defaultButton.setVisible(true);}
		for(Button editButton : editButtons) {editButton.setVisible(false);}
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
	private GridPane buildSchedulePane(Stage stage,Director director) {
		GridPane schedulePane = new GridPane();
		buildCalendarView(schedulePane);
		populateClassroomButtons(stage,director,schedulePane);
		return schedulePane;
	}
	
	 /**
	 * Populates the {@link Schedule} with the {@link Classroom} buttons as well as the {@link Teacher}s and each of their {@link Schedule}s
	 * @param director - the current user
	 * @param gridpane - the layout
	 */
	public void populateClassroomButtons(Stage stage,Director director,GridPane gridpane) {
		int rowIndex = 1, columnCount = 1, dayCount = 0;
		Center center = director.getCenter(director.getCenterID());
		Map<String,Classroom> classrooms = center.getClassrooms();
		List<String> days = Arrays.asList("Monday","Tuesday","Wednesday","Thursday","Friday");
		while(columnCount < 6) {
			for(Classroom classroom : classrooms.values()) {
				Teacher leadTeacher = classroom.getTeacher(classroom.getTeacherID());
				Teacher assistantTeacher = classroom.getAssistantTeacher(classroom.getAssistantTeacherID());
				Button newButton = new Button();
				if(leadTeacher == null && assistantTeacher == null) {
					newButton.setText(classroom.toString());
					newButton.setPrefSize(300,200);
					newButton.getStyleClass().add("badbutton");
				}else if(assistantTeacher == null ^ leadTeacher == null) {
					if(assistantTeacher == null) {newButton = isInRatio(classroom,leadTeacher,columnCount,days.get(dayCount));}
					else if(leadTeacher == null) {newButton = isInRatio(classroom,assistantTeacher,columnCount,days.get(dayCount));}
				}else if(leadTeacher != null && assistantTeacher != null) {newButton = isInRatio(classroom,leadTeacher,assistantTeacher,columnCount,days.get(dayCount));}
				int columnIndex = getColumnIndex(columnCount);
				String day = days.get(dayCount);
				newButton.setOnAction(e -> Platform.runLater(new ChangeScene(stage,new ClassroomSchedule(stage,null,director,classroom,day))));
				placeNodeSpan(gridpane,newButton,columnIndex,rowIndex,2,3,"center",null);
				rowIndex = rowIndex+3;
			}
			dayCount++;
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
		trashButton.setOnAction(e -> {
			director.getCenter(director.getCenterID()).getEventCalendar().removeEvent(event);
			buttons.remove(trashButton);
			refreshEventPane(director);
			cancel();
		});
		placeNodeSpan(gridPane,trashButton,columnIndex,rowIndex+2,2,1,"center",null);
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
		LocalDate date = LocalDate.now();
	    int dayNum = date.getDayOfWeek().getValue();
	    List<Integer> dates = getDates(dayNum);
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
	 * Gets a list of unordered dates starting with the current day of the month and then adding dates until the list reaches a size of 5
	 * @param dayNum - an integer representing the day of the week (e.g. 1 for Monday, etc.)
	 * @return a list of unordered dates
	 */
	private List<Integer> getDates(int dayNum){
		List<Integer> dates = new ArrayList<Integer>();
		LocalDate now = LocalDate.now();
		int startDate = now.getDayOfMonth();
		dates.add(startDate);
		int date = startDate+1;
		OUTER_LOOP: while(dayNum < 5) { 
			boolean isValid = dateValidator(now.getMonth(),date);
			if(isValid == true) {
				dates.add(date);
				dayNum++;
				date++;
			}else {
				date = 1;
				while(dayNum < 5) {
					dates.add(date);
					dayNum++;
					date++;
				}
				break OUTER_LOOP;
			}
		}
		if(dates.size() < 5) { 
			int difference = 5 - dates.size();  
			int newDateStart = startDate - difference; 
			while(newDateStart < startDate) { 
				dates.add(newDateStart); 
				newDateStart = newDateStart+1; 
			}
		}
		List<Integer> sortedList = sort(dates);
		return sortedList;
	}
	
	/**
	 * Determines whether or not a given date is valid based on the current month
	 * @param month - Month
	 * @param date - integer (ideally representing the day of the month)
	 * @return true if the date is valid and false if it is not (e.g. April 31st)
	 */
	private boolean dateValidator(Month month,int date) {
		try {
			@SuppressWarnings("unused")
			LocalDate dateCheck = LocalDate.of(0,month,date);
			return true;
		}catch(Exception e) {
			return false;
		}
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
	public void refreshSchedulePane(Stage stage,Director director) {
		innerPane = buildSchedulePane(stage,director);
		scrollPane.setContent(innerPane);
	}
	
}

