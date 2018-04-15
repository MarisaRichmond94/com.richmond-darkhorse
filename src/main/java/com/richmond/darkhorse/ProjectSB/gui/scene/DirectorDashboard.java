package com.richmond.darkhorse.ProjectSB.gui.scene;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.richmond.darkhorse.ProjectSB.AccountManager;
import com.richmond.darkhorse.ProjectSB.Center;
import com.richmond.darkhorse.ProjectSB.Classroom;
import com.richmond.darkhorse.ProjectSB.Director;
import com.richmond.darkhorse.ProjectSB.Event;
import com.richmond.darkhorse.ProjectSB.EventCalendar;
import com.richmond.darkhorse.ProjectSB.Schedule;
import com.richmond.darkhorse.ProjectSB.SpecialBeginnings;
import com.richmond.darkhorse.ProjectSB.Teacher;
import com.richmond.darkhorse.ProjectSB.gui.component.AddEvent;
import com.richmond.darkhorse.ProjectSB.gui.component.ClassroomStatus;
import com.richmond.darkhorse.ProjectSB.gui.component.Description;
import com.richmond.darkhorse.ProjectSB.gui.component.ImageButton;
import com.richmond.darkhorse.ProjectSB.gui.component.ModifySchedules;
import com.richmond.darkhorse.ProjectSB.middleman.ChangeScene;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.HPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ChoiceBox;
import java.util.Calendar;

public class DirectorDashboard extends Scene {
	
	private BorderPane directorDashboardLayout;
	private ScrollPane scrollPane;
	private List<ImageButton> buttons = new ArrayList<ImageButton>();
	private GridPane innerPane;
	@SuppressWarnings("unused")
	private boolean isEventPane = true;
	private EventCalendar eventCalendar;
	private List<Schedule> staffSchedules;
	
	public DirectorDashboard(Stage stage,Scene nextScene,Director director) {
		this(stage,new BorderPane(),nextScene,director);
	}
	
	public DirectorDashboard(Stage stage,BorderPane layout,Scene nextScene,Director director) {
		super(layout);
		Center center = director.getCenter(director.getCenterID());
		this.eventCalendar = center.getEventCalendar();
		this.staffSchedules = new ArrayList<Schedule>();
		Map<String,Schedule> staffScheduleMap = director.getSchedules();
		for(Schedule schedule : staffScheduleMap.values()) {staffSchedules.add(schedule);}
		
		HBox topBar = buildTopBar(stage,director);
		HBox bottomBar = buildBottomBar();
		VBox leftSideBar = buildSideBar(stage,director);
		GridPane centerPane = buildCenterPane(director);
		ScrollPane rightSideBar = buildRightSideBar(director);
		
		directorDashboardLayout = layout;
		directorDashboardLayout.setTop(topBar);
		directorDashboardLayout.setBottom(bottomBar);
		directorDashboardLayout.setLeft(leftSideBar);
		directorDashboardLayout.setRight(rightSideBar);
		directorDashboardLayout.setCenter(centerPane);
		directorDashboardLayout.getStylesheets().add("directorhome.css");
		
	}
	
	//Top bar 
	public HBox buildTopBar(Stage stage,Director director) {
		HBox topBar = new HBox();
		ImageView logoViewer = new ImageView();
		Image logo = new Image("logo.png");
		logoViewer.setImage(logo);
		logoViewer.setPreserveRatio(true);
		logoViewer.setFitHeight(250);
					
		ImageView homeButtonViewer = new ImageView();
		Image hB = new Image("home.png");
		homeButtonViewer.setImage(hB);
		homeButtonViewer.setPreserveRatio(true);
		homeButtonViewer.setFitHeight(100);
		ImageButton homeButton = new ImageButton(homeButtonViewer);
		homeButton.setOnAction(e -> Platform.runLater(new ChangeScene(stage,new DirectorHome(stage,null,director))));
					
		ImageView logoutViewer = new ImageView();
		Image lB = new Image("logout.png");
		logoutViewer.setImage(lB);
		logoutViewer.setPreserveRatio(true);
		logoutViewer.setFitHeight(100);
		ImageButton logoutButton = new ImageButton(logoutViewer);
		logoutButton.setOnAction(e -> {
			SpecialBeginnings sB = SpecialBeginnings.getInstance();
			sB.saveCenters();
			sB.saveStaffMembers();
			Map<String,Center> centers = sB.getCenters();
			for(Center center : centers.values()) {
				center.saveClassrooms();
				center.saveStudents();
			}
			AccountManager.getInstance().saveUserIDToAccount();
			Platform.runLater(new ChangeScene(stage,new LoginScene(stage,null)));
		});
		topBar.getChildren().addAll(homeButton,logoViewer,logoutButton);
		topBar.getStyleClass().add("topbar");
		return topBar;
	}
	
	//Bottom bar
	public HBox buildBottomBar() {
		HBox bottomBar = new HBox();
		Label signature = new Label("Created by Marisa Richmond");
		signature.getStyleClass().add("text");
		bottomBar.getChildren().add(signature);
		bottomBar.getStyleClass().add("bottombar");
		return bottomBar;
	}
	
	//Side bar (left)
	public VBox buildSideBar(Stage stage,Director director) {
		VBox leftSideBar = new VBox();
		
		ImageView dashboardViewer = new ImageView();
		Image dashboard = new Image("dashboard.png");
		dashboardViewer.setImage(dashboard);
		dashboardViewer.setPreserveRatio(true);
		dashboardViewer.setFitHeight(100);
		ImageButton dashboardButton = new ImageButton(dashboardViewer);
		dashboardButton.setOnAction(e -> Platform.runLater(new ChangeScene(stage,new DirectorDashboard(stage,null,director))));
		Label viewDashboard = new Label("Dashboard");
		viewDashboard.getStyleClass().add("label");
		
		ImageView mailboxViewer = new ImageView();
		Image mailbox = new Image("mailbox.png");
		mailboxViewer.setImage(mailbox);
		mailboxViewer.setPreserveRatio(true);
		mailboxViewer.setFitHeight(80);
		ImageButton mailboxButton = new ImageButton(mailboxViewer);
		mailboxButton.setOnAction(e -> Platform.runLater(new ChangeScene(stage,new DirectorMailbox(stage,null,director))));
		Label checkMailbox = new Label("Mailbox");
		checkMailbox.getStyleClass().add("label");
		
		ImageView classroomViewer = new ImageView();
		Image classroom = new Image("classroom.png");
		classroomViewer.setImage(classroom);
		classroomViewer.setPreserveRatio(true);
		classroomViewer.setFitHeight(100);
		ImageButton classroomButton = new ImageButton(classroomViewer);
		classroomButton.setOnAction(e -> Platform.runLater(new ChangeScene(stage,new DirectorClassroomWorkspace(stage,null,director))));
		Label viewClassroom = new Label("Classrooms");
		viewClassroom.getStyleClass().add("label");
		
		ImageView studentViewer = new ImageView();
		Image students = new Image("students.png");
		studentViewer.setImage(students);
		studentViewer.setPreserveRatio(true);
		studentViewer.setFitHeight(95);
		ImageButton studentButton = new ImageButton(studentViewer);
		studentButton.setOnAction(e -> Platform.runLater(new ChangeScene(stage,new DirectorStudentWorkspace(stage,null,director))));
		Label createStudent = new Label("Students");
		createStudent.getStyleClass().add("label");
		
		leftSideBar.getChildren().addAll(dashboardButton,viewDashboard,mailboxButton,checkMailbox,classroomButton,viewClassroom,studentButton,createStudent);
		leftSideBar.getStyleClass().add("sidebar");
		return leftSideBar;
	}
	
	//Side bar (right)
	public ScrollPane buildRightSideBar(Director director) {
		GridPane innerPane = new GridPane();
		innerPane.setVgap(10);
		innerPane.setHgap(10);
		ColumnConstraints columnOne = new ColumnConstraints();
		columnOne.setPercentWidth(25);
	    ColumnConstraints columnTwo = new ColumnConstraints();
	    columnTwo.setPercentWidth(25);
	    ColumnConstraints columnThree = new ColumnConstraints();
	    columnThree.setPercentWidth(25);
	    ColumnConstraints columnFour = new ColumnConstraints();
	    columnFour.setPercentWidth(25);
	    innerPane.getColumnConstraints().addAll(columnOne,columnTwo,columnThree,columnFour);
	    innerPane.getStyleClass().add("rightsidebar");
		
		Label title = new Label("Ratios");
		title.getStyleClass().add("subtitle");
		innerPane.add(title,1,0);
		GridPane.setConstraints(title,1,0,2,1);
		GridPane.setHalignment(title,HPos.CENTER);
		
		Map<String,Classroom> classrooms = director.getClassrooms();
		List<Classroom> rooms = new ArrayList<Classroom>();
		for(Classroom classroom : classrooms.values()) {rooms.add(classroom);}
		
		int row = 1;
		for(Classroom room : rooms) {
			Button newButton = new Button();
			newButton.setPrefSize(250,150);
			String roomName = room.toString();
			String totalTeachers = "" + room.getTeachersPresent().size() + "";
			String totalStudents = "" + room.getStudentsPresent().size() + "";
			String roomRatio = null;
			if(room.getTeachersPresent().size() > 0) {
				roomRatio = "1:" + (room.getStudentsPresent().size())/(room.getTeachersPresent().size());
			}else {roomRatio = "0:0";}
			newButton.setText(roomName + "\nTeachers (total): " + totalTeachers + "\nStudents (total): " + totalStudents + "\nRatio (current): " + roomRatio);
			innerPane.add(newButton,0,row);
			if(room.getTeachersPresent().size() == 1 && room.getStudentsPresent().size() >= 13) {newButton.getStyleClass().add("badbutton");
			}else if(room.getTeachersPresent().size() == 2 && room.getStudentsPresent().size() >= 19) {newButton.getStyleClass().add("badbutton");
			}else if(room.getStudentsPresent().size() >= 19) {newButton.getStyleClass().add("badbutton");
			}else {newButton.getStyleClass().add("goodbutton");}
			GridPane.setHalignment(newButton,HPos.CENTER);
			GridPane.setConstraints(newButton,0,row,4,4);
			newButton.setOnAction(e -> {
				ClassroomStatus classroomStatus = new ClassroomStatus(director,room);
				classroomStatus.display();
			});
			row = row+4;
		}
		
		ScrollPane rightSideBar = new ScrollPane(innerPane);
		rightSideBar.setFitToHeight(true);
		rightSideBar.setFitToWidth(true);
		return rightSideBar;
	}
	
	//Center Pane
	public GridPane buildCenterPane(Director director) {
		GridPane centerPane = new GridPane();
		centerPane.setVgap(10);
		centerPane.setHgap(10);
		ColumnConstraints columnOne = new ColumnConstraints();
		columnOne.setPercentWidth(25);
	    ColumnConstraints columnTwo = new ColumnConstraints();
	    columnTwo.setPercentWidth(25);
	    ColumnConstraints columnThree = new ColumnConstraints();
	    columnThree.setPercentWidth(25);
	    ColumnConstraints columnFour = new ColumnConstraints();
	    columnFour.setPercentWidth(25);
	    centerPane.getColumnConstraints().addAll(columnOne,columnTwo,columnThree,columnFour);
	    centerPane.getStyleClass().add("gridpane");
	    
	    Button modSchedule = new Button("modify schedule");
	    modSchedule.getStyleClass().add("button");
	    modSchedule.setVisible(false);
	    modSchedule.setOnAction(e -> {
	    		ModifySchedules modifySchedules = new ModifySchedules(director);
	    		modifySchedules.display();
	    		refreshSchedulePane(director);
	    });
	    Button clearSchedule = new Button("clear schedule");
	    clearSchedule.setOnAction(e -> director.clearSchedules());
	    clearSchedule.getStyleClass().add("button");
	    clearSchedule.setVisible(false);
	    
	    Button addButton = new Button("add event");
	    addButton.setOnAction(e -> {
	    		AddEvent addEvent = new AddEvent(director);
	    		addEvent.display();
	    		refreshEventPane(director);
	    });
	    addButton.getStyleClass().add("button");
	    Button removeButton = new Button("remove event");
	    removeButton.getStyleClass().add("button");
	    
	    Button clearButton = new Button("clear all");
	    clearButton.setVisible(false);
	    clearButton.getStyleClass().add("button");
	    clearButton.setOnAction(e -> {
	    		eventCalendar.clear();
	    		clearButton.setVisible(false);
	    		removeButton.setVisible(true);
			refreshEventPane(director);	
	    });
	    removeButton.setOnAction(e -> {
	    		revealButtons(buttons);
	    		removeButton.setVisible(false);
	    		clearButton.setVisible(true);
	    });
	    
	    ChoiceBox<String> viewBox = new ChoiceBox<String>();
	    String eventView = "event view";
	    String scheduleView = "schedule view";
	    viewBox.getItems().addAll(eventView,scheduleView);
	    viewBox.setValue(viewBox.getItems().get(0));
	    viewBox.getStyleClass().add("choicebox");
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
	    String monthString = getMonthNumToString(monthNum);
	    Label month = new Label(monthString);
	    month.getStyleClass().add("med-title");
	    
	    ImageView scheduleViewer = new ImageView();
	    Image schedule = new Image("schedule.png");
	    scheduleViewer.setImage(schedule);
	    scheduleViewer.setPreserveRatio(true);
	    scheduleViewer.setFitHeight(100);
	    ImageButton scheduleButton = new ImageButton(scheduleViewer);
	    
	    centerPane.add(viewBox,3,0);
	    GridPane.setHalignment(viewBox,HPos.CENTER);
	    centerPane.add(month,1,0);
	    GridPane.setConstraints(month,1,0,2,1);
	    GridPane.setHalignment(month,HPos.CENTER);
	    centerPane.add(scheduleButton,0,0);
	    GridPane.setHalignment(scheduleButton,HPos.CENTER);
	    
	    innerPane = buildEventPane(director);
	    scrollPane = new ScrollPane(innerPane);
	    scrollPane.setFitToHeight(true);
	    scrollPane.setFitToWidth(true);
	    scrollPane.setPrefSize(1000, 1000);
	    centerPane.add(scrollPane,0,1);
	    GridPane.setConstraints(scrollPane,0,1,4,4);
	    
	    centerPane.add(addButton,1,5);
	    GridPane.setHalignment(addButton,HPos.CENTER);
	    centerPane.add(removeButton,2,5);
	    GridPane.setHalignment(removeButton,HPos.CENTER);
	    centerPane.add(clearButton,2,5);
	    GridPane.setHalignment(clearButton,HPos.CENTER);
	    centerPane.add(modSchedule,1,5);
	    GridPane.setHalignment(modSchedule,HPos.CENTER);
	    centerPane.add(clearSchedule,2,5);
	    GridPane.setHalignment(clearSchedule,HPos.CENTER);
	    
		return centerPane;
	}
	
	//Event pane (build)
	public GridPane buildEventPane(Director director) {
	    GridPane eventPane = new GridPane();
	    buildCalendarView(eventPane);
	    
	    List<Event> events = eventCalendar.getEvents();
	    for(Event event : events) {
	    		addEvent(event,eventPane,director);
	    }
	    
	    return eventPane;
	}
	
	//Schedule pane (build)
	private GridPane buildSchedulePane(Director director) {
		GridPane schedulePane = new GridPane();
		buildCalendarView(schedulePane);
		populateClassroomButtons(director,schedulePane);
		return schedulePane;
	}
	
	//TODO If you run into any problems, check here first. This is probably the problem
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
				String lead = "N/A";
				String leadSchedule = "N/A";
				if(classroom.getTeacherID() != null) {
					leadTeacher = classroom.getTeacher(classroom.getTeacherID());
					lead = leadTeacher.getFirstName() + " " + leadTeacher.getLastName();
					leadSchedule = leadTeacher.getSchedule().getStartTimes().get(columnCount) + "-" + leadTeacher.getSchedule().getStopTimes().get(columnCount);
				}
				Teacher assistantTeacher = null;
				String assistant = "N/A";
				String assistantSchedule = "N/A";
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
					gridpane.add(newButton,columnIndex,rowIndex);
					GridPane.setHalignment(newButton,HPos.CENTER);
					GridPane.setConstraints(newButton,columnIndex,rowIndex,2,5);
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
					gridpane.add(newButton,columnIndex,rowIndex);
					GridPane.setHalignment(newButton,HPos.CENTER);
					GridPane.setConstraints(newButton,columnIndex,rowIndex,2,3);
					rowIndex = rowIndex+3;
				}
			}
			rowIndex = 1;
			columnCount++;
		}
	}
	
	//Event creation methods
	private void addEvent(Event event,GridPane gridPane,Director director) {
		int dayInt = event.getDayOfWeek();
		String day = event.getDay();
		int eventPosition = getEventPosition(event,day); 
		int rowIndex = getRowIndex(eventPosition);
		int columnIndex = getColumnIndex(dayInt);
		String summary = event.getSummary();
		String time = null;
		if(event.getTime() != null) {time = event.getTime();}
		Button newButton = new Button();
		newButton.getStyleClass().add("eventbutton");
		if(time != null) {newButton.setText(summary + "\n" + time);}
		else {newButton.setText(summary);}
		newButton.setOnAction(e -> {
			Description description = new Description(event);
			description.display();
		});
		gridPane.add(newButton,columnIndex,rowIndex);
		GridPane.setHalignment(newButton,HPos.CENTER);
		GridPane.setConstraints(newButton,columnIndex,rowIndex,2,2);
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
		gridPane.add(trashButton,columnIndex,rowIndex+2);
		GridPane.setHalignment(trashButton,HPos.CENTER);
		gridPane.add(cancelButton,columnIndex+1,rowIndex+2);
		GridPane.setHalignment(cancelButton,HPos.CENTER);
	}
	
	private ImageButton createTrashButton() {
		ImageView trashView = new ImageView();
		Image trash = new Image("trash.png");
		trashView.setImage(trash);
		trashView.setPreserveRatio(true);
		trashView.setFitHeight(50);
		ImageButton trashButton = new ImageButton(trashView);
		trashButton.setVisible(false);
		return trashButton;
	}
	
	private ImageButton createCancelButton() {
		ImageView cancelView = new ImageView();
		Image cancel = new Image("cancel.png");
		cancelView.setImage(cancel);
		cancelView.setPreserveRatio(true);
		cancelView.setFitHeight(40);
		ImageButton cancelButton = new ImageButton(cancelView);
		cancelButton.setVisible(false);
		return cancelButton;
	}
	
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
	
	private int getRowIndex(int eventPosition) {
		int rowIndex = 0;
		if(eventPosition == 0) {rowIndex = 2;}
		else { rowIndex = (eventPosition * 4) + 1; }
		return rowIndex;
	}
	
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
	private void buildCalendarView(GridPane gridPane) {
		gridPane.setVgap(10);
		gridPane.setHgap(10);
		ColumnConstraints column1 = new ColumnConstraints();
		column1.setPercentWidth(10);
	    ColumnConstraints column2 = new ColumnConstraints();
	    column2.setPercentWidth(10);
	    ColumnConstraints column3 = new ColumnConstraints();
	    column3.setPercentWidth(10);
	    ColumnConstraints column4 = new ColumnConstraints();
	    column4.setPercentWidth(10);
	    ColumnConstraints column5 = new ColumnConstraints();
	    column5.setPercentWidth(10);
	    ColumnConstraints column6 = new ColumnConstraints();
	    column6.setPercentWidth(10);
	    ColumnConstraints column7 = new ColumnConstraints();
	    column7.setPercentWidth(10);
	    ColumnConstraints column8 = new ColumnConstraints();
	    column8.setPercentWidth(10);
	    ColumnConstraints column9 = new ColumnConstraints();
	    column9.setPercentWidth(10);
	    ColumnConstraints column10 = new ColumnConstraints();
	    column10.setPercentWidth(10);
	    gridPane.getColumnConstraints().addAll(column1,column2,column3,column4,column5,column6,column7,column8,column9,column10);
	    gridPane.getStyleClass().add("gridpane");
	    
	    Calendar calendar = Calendar.getInstance();
	    int dayOfWeekInt = calendar.get(Calendar.DAY_OF_WEEK);
	    String dayOfWeek = getDayOfWeek(dayOfWeekInt);
	    List<Integer> dates = getDates(dayOfWeekInt,dayOfWeek);
	    Label monday = new Label("Monday " + dates.get(0));
	    monday.getStyleClass().add("boxlabel");
	    Label tuesday = new Label ("Tuesday " + dates.get(1));
	    tuesday.getStyleClass().add("boxlabel");
	    Label wednesday = new Label("Wednesday " + dates.get(2));
	    wednesday.getStyleClass().add("boxlabel");
	    Label thursday = new Label("Thursday " + dates.get(3));
	    thursday.getStyleClass().add("boxlabel");
	    Label friday = new Label("Friday " + dates.get(4));
	    friday.getStyleClass().add("boxlabel");
	    
	    gridPane.add(monday,0,0);
	    GridPane.setHalignment(monday,HPos.CENTER);
	    GridPane.setConstraints(monday,0,0,2,1);
	    gridPane.add(tuesday,2,0);
	    GridPane.setHalignment(tuesday,HPos.CENTER);
	    GridPane.setConstraints(tuesday,2,0,2,1);
	    gridPane.add(wednesday,4,0);
	    GridPane.setHalignment(wednesday,HPos.CENTER);
	    GridPane.setConstraints(wednesday,4,0,2,1);
	    gridPane.add(thursday,6,0);
	    GridPane.setHalignment(thursday,HPos.CENTER);
	    GridPane.setConstraints(thursday,6,0,2,1);
	    gridPane.add(friday,8,0);
	    GridPane.setHalignment(friday,HPos.CENTER);
	    GridPane.setConstraints(friday,8,0,2,1);
	}
	
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
	
	private String getDayOfWeek(int dayOfWeekInt) {
		String dayOfWeek = null;
		if(dayOfWeekInt == 2) {dayOfWeek = "Monday";}
		else if(dayOfWeekInt == 3) {dayOfWeek = "Tuesday";}
		else if(dayOfWeekInt == 4) {dayOfWeek = "Wednesday";}
		else if(dayOfWeekInt == 5) {dayOfWeek = "Thursday";}
		else if(dayOfWeekInt == 6) {dayOfWeek = "Friday";}
		return dayOfWeek;
	}
	
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
				}else {
					possibleSwitchesPresent = false;
				}
				compareIndex++; 
				compareToIndex++;
			}
		}
		return dates;
	}
	
	//event calls
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
	
	public void refreshEventPane(Director director) {
		innerPane = buildEventPane(director);
		scrollPane.setContent(innerPane);
	}
	
	public void refreshSchedulePane(Director director) {
		innerPane = buildSchedulePane(director);
		scrollPane.setContent(innerPane);
	}
	
}

