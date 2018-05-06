package com.richmond.darkhorse.ProjectSB.gui.scene;
import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.richmond.darkhorse.ProjectSB.Center;
import com.richmond.darkhorse.ProjectSB.Classroom;
import com.richmond.darkhorse.ProjectSB.Director;
import com.richmond.darkhorse.ProjectSB.Schedule;
import com.richmond.darkhorse.ProjectSB.StaffMember;
import com.richmond.darkhorse.ProjectSB.Student;
import com.richmond.darkhorse.ProjectSB.Teacher;
import com.richmond.darkhorse.ProjectSB.gui.component.DirectorLayout;
import com.richmond.darkhorse.ProjectSB.gui.component.ImageButton;
import com.richmond.darkhorse.ProjectSB.gui.component.ScheduleHelper;
import com.richmond.darkhorse.ProjectSB.middleman.ChangeScene;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

//TODO Set up functionality for the student list so that you can select multiple students to push up or down by turning them into toggle buttons and creating a method
//in director that adds students to another classroom's attendance list for the passed day and removes them from the current classroom's attendance list for the day
//TODO modify the schedule class to have some way of showing a teacher where they are for different periods of the day (for floats) and create Float class that extends
//from StaffMember class 
public class ClassroomSchedule extends Scene implements DirectorLayout,ScheduleHelper{

	private Director director;
	private Classroom classroom;
	private String day;
	private BorderPane classroomScheduleLayout;
	private List<Label> labels = new ArrayList<Label>();
	private Map<LocalTime,Integer> columnSelector = new HashMap<>();
	
	public ClassroomSchedule(Stage stage,Scene nextScene,Director director,Classroom classroom,String day) {
		this(stage,new BorderPane(),nextScene,director,classroom,day);
	}
	
	public ClassroomSchedule(Stage stage,BorderPane layout,Scene nextScene,Director director,Classroom classroom,String day) {
		super(layout);
		this.director = director;
		this.classroom = classroom;
		this.day = day;
		populateColumnSelector();
		GridPane topPane = buildNewTopPane(stage,director);
		HBox bottomPane = buildBottomPane();
		VBox rightPane = buildRightScrollPane(day);
		VBox centerPane = buildCenterPane(stage);
		classroomScheduleLayout = layout;
		setBorderPane(classroomScheduleLayout,centerPane,rightPane,null,topPane,bottomPane);
		classroomScheduleLayout.getStylesheets().add("css/director.css");
	}
	
	/**
	 * Builds the top section of the {@link ClassroomSchedule} BorderPane to display a button that returns the user to the {@link Schedule} pane of 
	 * {@link DirectorDashboard}, displays the {@link Classroom} name, and provides a logout button 
	 * @return GridPane
	 */
	private GridPane buildNewTopPane(Stage stage,Director director) {
		GridPane toppane = new GridPane();
		setConstraints(toppane,4,0,10,10,"toppane");
		ImageButton scheduleButton = new ImageButton(createImageWithFitHeight("images/schedule.png",125));
		scheduleButton.setOnAction(e -> Platform.runLater(new ChangeScene(stage,new DirectorDashboard(stage,null,director))));
		VBox returnButton = buildButtonWithLabel(scheduleButton,createLabel("return","label"));
		Label classnameTitle = createLabel(classroom.getClassroomType(),"med-title");
		ImageButton logoutButton = new ImageButton(createImageWithFitHeight("images/logout.png",125));
		logoutButton.setOnAction(e -> saveInit(stage));
		VBox logout = buildButtonWithLabel(logoutButton,createLabel("logout","label"));
		placeNode(toppane,returnButton,0,0,"center",null);
		placeNodeSpan(toppane,classnameTitle,1,0,2,1,"center",null);
		placeNode(toppane,logout,3,0,"center",null);
		return toppane;
	}
	
	//TODO figure out how to make the ScrollPane fill empty space correctly because having a static value is bad
	/**
	 * Builds a {@link ScrollPane} that contains a list of the {@link Student}s in the {@link Classroom} on the given day and allows the {@link Director} to move one or
	 * more {@link Student}s up or down depending on need and availability
	 * @return ScrollPane
	 */
	private VBox buildRightScrollPane(String day) {
		GridPane gridpane = new GridPane();
		gridpane.getStyleClass().add("rightpane");
	    buildStudentList(gridpane,classroom,day);
	    Label sideTitle = createLabel("Class List","subtitle");
	    placeNode(gridpane,sideTitle,1,0,"center","center");
	    ScrollPane scrollpane = new ScrollPane(gridpane);
	    scrollpane.setMaxHeight(637);
	    scrollpane.setMinHeight(637);
	    scrollpane.setFitToHeight(true);
	    scrollpane.setFitToWidth(true);
		GridPane menupane = new GridPane();
		ChoiceBox<ClassroomHolder> classroomBox = buildClassroomBox(day);
		ImageButton arrowButton = new ImageButton(createImageWithFitHeight("images/arrow.png",35));
		placeNode(menupane,classroomBox,0,0,"center",null);
		placeNode(menupane,arrowButton,1,0,"center",null);
		VBox rightPane = new VBox();
		rightPane.getChildren().addAll(scrollpane,menupane);
		return rightPane;
	}
	
	/**
	 * Constructs a {@link Classroom} list of {@link Student}s for the day and populates the GridPane with them
	 * @param rightPane - GridPane
	 * @param classroom - a {@link Classroom}
	 */
	private void buildStudentList(GridPane rightPane,Classroom classroom,String day) {
		List<String> studentStrings = classroom.generateClassList(day);
		List<Student> studentList = new ArrayList<Student>();
		for(String studentID : studentStrings) {
			Student student = classroom.getStudent(studentID);
			studentList.add(student);
		}
	    if(!studentList.isEmpty()) {
		    int columnIndex = 1, rowIndex = 1;
		    for(Student student : studentList) {
		    		Label studentLabel = createLabel(student.getFirstName() + " " + student.getLastName(),"label");
		    		labels.add(studentLabel);
		    		placeNode(rightPane,studentLabel,columnIndex,rowIndex,"center",null);
		    		rowIndex++;
		    }
	    }else {
	    		Label noStudents = createLabel("No students expected","label");
	    		labels.add(noStudents);
	    		placeNode(rightPane,noStudents,1,2,"center",null);
	    }
	}
	
	/**
	 * Builds a VBox filled with GridPanes to serve as the central layout for {@link Classroom Schedule}
	 * @return VBox
	 */
	private VBox buildCenterPane(Stage stage) {
		VBox centerPane = new VBox();
		Teacher leadTeacher = classroom.getTeacher(classroom.getTeacherID()), assistantTeacher = classroom.getAssistantTeacher(classroom.getAssistantTeacherID());
		GridPane teacherPane = new GridPane(); 
		if(leadTeacher != null && assistantTeacher != null) {teacherPane = buildTeacherSchedulePane(leadTeacher,assistantTeacher);}
		else if(leadTeacher != null & assistantTeacher == null) {teacherPane = buildTeacherSchedulePane(leadTeacher);}
		else {teacherPane = buildTeacherSchedulePane();}
		GridPane classroomStatusPane = buildClassroomStatusPane(stage);
		GridPane holePane = buildFillHolePane();
		centerPane.getChildren().addAll(teacherPane,classroomStatusPane,holePane);
		return centerPane;
	}

	/**
	 * Builds a GridPane that displays the {@link Classroom}'s {@link Teacher}s' {@link Schedule}s
	 * @return GridPane
	 */
	private GridPane buildTeacherSchedulePane(Teacher leadTeacher,Teacher assistantTeacher) {
		GridPane gridpane = new GridPane();
		setConstraints(gridpane,22,0,10,10,"gridpane");
		populateTimes(gridpane);
		int dayNum = determineDayNum(day);
		Button leadSchedule = buildScheduleButton(leadTeacher,dayNum), assistantSchedule = buildScheduleButton(assistantTeacher,dayNum);
		placeScheduleButton(gridpane,leadTeacher,leadSchedule,dayNum,1);
		placeScheduleButton(gridpane,assistantTeacher,assistantSchedule,dayNum,2);
		if(isClassroomInRatio(classroom,leadTeacher,assistantTeacher,dayNum,day) == false) {
			fillCriticalHoles(gridpane,leadTeacher,dayNum,day,1);
			fillCriticalHoles(gridpane,assistantTeacher,dayNum,day,2);
		}
		return gridpane;
	}
	
	/**
	 * Builds a GridPane that displays the {@link Classroom}'s {@link Teacher}s' {@link Schedule}s
	 * @return GridPane
	 */
	private GridPane buildTeacherSchedulePane(Teacher leadTeacher) {
		GridPane gridpane = new GridPane();
		setConstraints(gridpane,22,0,10,10,"gridpane");
		populateTimes(gridpane);
		int dayNum = determineDayNum(day);
		Button leadSchedule = buildScheduleButton(leadTeacher,dayNum);
		placeScheduleButton(gridpane,leadTeacher,leadSchedule,dayNum,1);
		placeScheduleButton(gridpane,null,null,dayNum,2);
		if(isClassroomInRatio(classroom,leadTeacher,dayNum,day) == false) {fillCriticalHoles(gridpane,leadTeacher,dayNum,day,1);}
		return gridpane;
	}
	
	/**
	 * Builds a GridPane that displays the {@link Classroom}'s {@link Teacher}s' {@link Schedule}s
	 * @return GridPane
	 */
	private GridPane buildTeacherSchedulePane() {
		GridPane gridpane = new GridPane();
		setConstraints(gridpane,22,0,10,10,"gridpane");
		populateTimes(gridpane);
		int dayNum = determineDayNum(day);
		placeScheduleButton(gridpane,null,null,dayNum,1);
		placeScheduleButton(gridpane,null,null,dayNum,2);
		return gridpane;
	}
	
	private GridPane buildClassroomStatusPane(Stage stage) {
		GridPane gridpane = new GridPane();
		int dayNum = determineDayNum(day);
		Map<String,Classroom> classrooms = director.getClassrooms();
		setConstraints(gridpane,classrooms.size(),0,10,10,"gridpane");
		Label title = createLabel("Center Classroom Ratios","subtitle");
		placeNodeSpan(gridpane,title,0,0,4,1,"center",null);
		populateClassrooms(stage,gridpane,classrooms,dayNum);
		return gridpane;
	}
	
	private GridPane buildFillHolePane() {
		GridPane gridpane = new GridPane();
		setConstraints(gridpane,4,0,10,10,"gridpane");
		Label title = createLabel("Available Staff Members","subtitle"), teacher = createLabel("Teacher:","label");
		ChoiceBox<StaffMember> staffBox = buildStaffBox();
		Label from = createLabel("From:","label"), to = createLabel("To:","label");
		TextField startField = createTextField("start time","textfield",650), endField = createTextField("stop time","textfield",650);
		Button fillHole = createButton("fill hole",null,0,0,0);
		List<Node> nodes = Arrays.asList(title,teacher,staffBox,from,startField,to,endField,fillHole);
		placeNodes(gridpane,nodes);
		return gridpane;
	}
	
	@Override
	public void placeNodes(GridPane gridpane, List<Node> nodes) {
		placeNodeSpan(gridpane,nodes.get(0),0,0,4,1,"center",null);
		placeNode(gridpane,nodes.get(1),0,1,"right",null);
		placeNodeSpan(gridpane,nodes.get(2),1,1,3,1,"left",null);
		placeNode(gridpane,nodes.get(3),0,2,"right",null);
		placeNodeSpan(gridpane,nodes.get(4),1,2,3,1,"left",null);
		placeNode(gridpane,nodes.get(5),0,3,"right",null);
		placeNodeSpan(gridpane,nodes.get(6),1,3,3,1,"left",null);
		placeNodeSpan(gridpane,nodes.get(7),0,4,4,1,"center",null);
	}
	
	private ChoiceBox<StaffMember> buildStaffBox(){
		ChoiceBox<StaffMember> staffBox = new ChoiceBox<>();
		List<StaffMember> staffList = new ArrayList<>();
		List<Classroom> classroomList = new ArrayList<>();
		Map<String,StaffMember> staffMembers = director.getStaffMembers();
		for(StaffMember staffMember : staffMembers.values()) {staffList.add(staffMember);}
		Map<String,Classroom> classrooms = director.getClassrooms();
		for(Classroom classroomCheck : classrooms.values()) { classroomList.add(classroomCheck);}
		for(StaffMember staffmember : staffList) {
			if(staffmember.getTitle().equals("Teacher")) {
				Teacher teacher = (Teacher) staffmember;
				if(!classroom.getTeacherID().equals(teacher.getTeacherID())) {staffBox.getItems().add(staffmember);}
			}else {staffBox.getItems().add(staffmember);}
		}
		staffBox.setValue(staffBox.getItems().get(0));
		return staffBox;
	}
	
	private Button buildScheduleButton(Teacher teacher,int dayNum) {
		if(teacher.getSchedule().getStartTimes().get(dayNum) != null) {
			Button schedule = createButton(teacher.getFirstName(),null,0,0,0);
			return schedule;
		}
		return null;
	}
	
	/**
	 * Populates a GridPane with times ranging from 7:00am to 6:00pm posted in non-military time
	 * @param gridpane - GridPane
	 */
	private void populateTimes(GridPane gridpane) {
		List<LocalTime> times = new ArrayList<>();
		LocalTime time = LocalTime.of(7,0), close = LocalTime.of(18,30), noon = LocalTime.of(13,0);
		while(!time.equals(close)) {
			times.add(time);
			time = time.plusMinutes(30L);
		}
		int index = 0;
		for(LocalTime scheduleTime : times) {
			if(scheduleTime.isBefore(noon)) {
				Label timeLabel = createLabel(scheduleTime.toString(),"time-label");
				placeNode(gridpane,timeLabel,index,0,"center",null);
			}else {
				scheduleTime = scheduleTime.minusHours(12L);
				Label timeLabel = createLabel(scheduleTime.toString(),"time-label");
				placeNode(gridpane,timeLabel,index,0,"center",null);
			}
			index++;
		}
	}
	
	private void placeScheduleButton(GridPane gridpane,Teacher teacher,Button scheduleButton,int dayNum,int rowNum) {
		if(teacher != null && scheduleButton != null) {
			LocalTime startTime = teacher.getSchedule().getStartTimes().get(dayNum), stopTime = teacher.getSchedule().getStopTimes().get(dayNum);
			int start = columnSelector.get(startTime.plusHours(loopCheck(startTime))), end = columnSelector.get(stopTime.plusHours(loopCheck(stopTime))), columnSpan = end-start;
			placeNodeSpan(gridpane,scheduleButton,start,rowNum,columnSpan,1,null,null);
			scheduleButton.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
			scheduleButton.getStyleClass().add("goodbutton");
		}else if(teacher != null) {
			Button holeButton = createButton("Hole",null,0,0,0);
			LocalTime startTime = LocalTime.of(8,0), stopTime = LocalTime.of(17,0);
			int start = columnSelector.get(startTime), end = columnSelector.get(stopTime), columnSpan = end-start;
			placeNodeSpan(gridpane,holeButton,start,rowNum,columnSpan,1,null,null);
			holeButton.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
			holeButton.getStyleClass().add("badbutton");
		}
	}
	
	private long loopCheck(LocalTime time) {
		if(time == null) {
			IllegalArgumentException e = new IllegalArgumentException();
			e.printStackTrace();
		}
		LocalTime start = LocalTime.of(1,00), end = LocalTime.of(6,30);
		if(time.equals(start) || time.isAfter(start) && time.isBefore(end)) {return 12L;}
		return 0L;
	}
	
	private void fillCriticalHoles(GridPane gridpane,Teacher teacher,int dayNum,String day,int rowNum) {
		if(teacher.getSchedule().getStartTimes().get(dayNum) != null) {
			LocalTime teacherStart = teacher.getSchedule().getStartTimes().get(dayNum), teacherEnd = teacher.getSchedule().getStopTimes().get(dayNum);
			Duration teacherDuration = Duration.between(teacherStart.plusHours(isLoop(teacherStart)),teacherEnd.plusHours(isLoop(teacherEnd)));
			if(teacherDuration.compareTo(CORE_HOURS) < 0) {
				if(teacherStart.isAfter(LocalTime.of(8,00))) {
					int columnSpan = determineStartHoleSpan(teacherStart.plusHours(loopCheck(teacherStart)));
					int start = columnSelector.get(LocalTime.of(8,0));
					Button hole = createButton("Hole",null,0,0,0);
					hole.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
					hole.getStyleClass().add("badbutton");
					placeNodeSpan(gridpane,hole,start,rowNum,columnSpan,1,null,null);
				}
				if(teacherEnd.isBefore(LocalTime.of(17,0))) {
					int columnSpan = determineHoleSpan(teacherEnd.plusHours(loopCheck(teacherEnd)));
					int start = columnSelector.get(teacherEnd.plusHours(isLoop(teacherEnd)));
					Button hole = createButton("Hole",null,0,0,0);
					hole.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
					hole.getStyleClass().add("badbutton");
					placeNodeSpan(gridpane,hole,start,rowNum,columnSpan,1,null,null);
				}
			}
		}
	}
	
	private int determineHoleSpan(LocalTime teacherStop) {
		int span = 0;
		LocalTime stop = LocalTime.of(17,30);
		while(teacherStop.isBefore(stop)) {
			teacherStop = teacherStop.plusMinutes(30L);
			span = span+1;
		}
		return span-1;
	}
	
	private int determineStartHoleSpan(LocalTime teacherStart) {
		int span = 0;
		LocalTime coreStart = LocalTime.of(8,0);
		while(coreStart.isBefore(teacherStart)) {
			coreStart = coreStart.plusMinutes(30L);
			span = span+1;
		}
		return span;
	}
	
	/**
	 * Populates the {@link Schedule} with the {@link Classroom} buttons as well as the {@link Teacher}s and each of their {@link Schedule}s
	 * @param director - the current user
	 * @param gridpane - the layout
	 */
	public void populateClassrooms(Stage stage,GridPane gridpane,Map<String,Classroom> classrooms,int dayNum) {
		List<Classroom> rooms = new ArrayList<Classroom>();
		for(Classroom classroom : classrooms.values()) {rooms.add(classroom);}
		int column = 0;
		for(Classroom room : rooms) {
			Teacher leadTeacher = room.getTeacher(room.getTeacherID()), assistantTeacher = room.getAssistantTeacher(room.getAssistantTeacherID());
			String buttonText = room.getClassroomType() + "\nRatio:" + room.generateClassList(day).size() + "/" + room.getMaxSize();
			Button classroomButton = createButton(buttonText,null,0,200,300);
			boolean status = false;
			if(leadTeacher != null && assistantTeacher != null) {status = isClassroomInRatio(room,leadTeacher,assistantTeacher,dayNum,day);}
			else if(leadTeacher != null && assistantTeacher == null) {status = isClassroomInRatio(room,leadTeacher,dayNum,day);}
			else if(leadTeacher == null && assistantTeacher != null) {status = isClassroomInRatio(room,assistantTeacher,dayNum,day);}
			if(status == true) {classroomButton.getStyleClass().add("goodbutton");}
			else {classroomButton.getStyleClass().add("badbutton");}
			classroomButton.setOnAction(e -> Platform.runLater(new ChangeScene(stage,new ClassroomSchedule(stage,null,director,room,day))));
			placeNode(gridpane,classroomButton,column,1,"center",null);
			column = column+1;
		}
	}
	
	/**
	 * Builds a {@link ChoiceBox} populated with {@link Classroom}s whose list of enrolled {@link Student}s for the given day is less than the {@link Classroom}'s
	 * overall maximum capacity
	 * @return {@link ChoiceBox} of {@link Classroom}
	 */
	private ChoiceBox<ClassroomHolder> buildClassroomBox(String day){
		ChoiceBox<ClassroomHolder> classroomBox = new ChoiceBox<ClassroomHolder>();
		int dayNum = determineDayNum(day);
		Map<String,Center> centers = director.getCenters();
		for(Center center : centers.values()) {
			Map<String,Classroom> openClassrooms = center.getClassrooms();
			for(Classroom classroom : openClassrooms.values()) {
				Teacher leadTeacher = classroom.getTeacher(classroom.getTeacherID());
				Teacher assistantTeacher = classroom.getAssistantTeacher(classroom.getAssistantTeacherID());
				if(!classroom.equals(this.classroom)) {
					if(leadTeacher != null && assistantTeacher != null) {
						if(isHolePresent(classroom,leadTeacher,assistantTeacher,dayNum,day) == false) {
							ClassroomHolder newClassroomHolder = new ClassroomHolder(classroom);
				    			classroomBox.getItems().add(newClassroomHolder);
						}
					}else if(leadTeacher != null && assistantTeacher == null) {
						if(isHolePresent(classroom,leadTeacher,dayNum,day) == false) {
							ClassroomHolder newClassroomHolder = new ClassroomHolder(classroom);
			    				classroomBox.getItems().add(newClassroomHolder);
						}
					}else if(leadTeacher == null & assistantTeacher != null) {
						if(isHolePresent(classroom,assistantTeacher,dayNum,day) == false) {
							ClassroomHolder newClassroomHolder = new ClassroomHolder(classroom);
			    				classroomBox.getItems().add(newClassroomHolder);
						}
					}
				}
			}
		}
		if(classroomBox.getItems().size() > 0) {classroomBox.setValue(classroomBox.getItems().get(0));}
		else {
			ClassroomHolder emptyClassroom = new ClassroomHolder(null);
			classroomBox.getItems().add(emptyClassroom);
			classroomBox.setValue(emptyClassroom);
		}
		classroomBox.setMaxWidth(260);
		classroomBox.getStyleClass().add("choice-box-mini");
		return classroomBox;
	}
	
	/**
	 * Determines a number representing the day based on the passed String value of the day (e.g. "Monday")
	 * @param day - String representing the day of the week (M-F)
	 * @return an Integer representing the day of the week (0-4) or -1 if the passed String value does not match M-F
	 */
	private int determineDayNum(String day) {
		if(day.equals("Monday")) {return 1;}
		else if(day.equals("Tuesday")) {return 2;}
		else if(day.equals("Wednesday")) {return 3;}
		else if(day.equals("Thursday")) {return 4;}
		else if(day.equals("Friday")) {return 5;}
		return -1;
	}
	
	class ClassroomHolder{
		private Classroom classroom;
		public ClassroomHolder(Classroom classroom) {
			this.classroom = classroom;
		}
		public Classroom getClassroom() {
			return classroom;
		}
		@Override
		public String toString() {
			if(classroom == null) {return "N/A";}
			else {return classroom.getClassroomType();}
		}
	}

	private Map<LocalTime, Integer> populateColumnSelector() {
		columnSelector.put(LocalTime.of(7,00),0);
		columnSelector.put(LocalTime.of(7,30),1);
		columnSelector.put(LocalTime.of(8,00),2);
		columnSelector.put(LocalTime.of(8,30),3);
		columnSelector.put(LocalTime.of(9,00),4);
		columnSelector.put(LocalTime.of(9,30),5);
		columnSelector.put(LocalTime.of(10,00),6);
		columnSelector.put(LocalTime.of(10,30),7);
		columnSelector.put(LocalTime.of(11,00),8);
		columnSelector.put(LocalTime.of(11,30),9);
		columnSelector.put(LocalTime.of(12,00),10);
		columnSelector.put(LocalTime.of(12,30),11);
		columnSelector.put(LocalTime.of(13,00),12);
		columnSelector.put(LocalTime.of(13,30),13);
		columnSelector.put(LocalTime.of(14,00),14);
		columnSelector.put(LocalTime.of(14,30),15);
		columnSelector.put(LocalTime.of(15,00),16);
		columnSelector.put(LocalTime.of(15,30),17);
		columnSelector.put(LocalTime.of(16,00),18);
		columnSelector.put(LocalTime.of(16,30),19);
		columnSelector.put(LocalTime.of(17,00),20);
		columnSelector.put(LocalTime.of(17,30),21);
		columnSelector.put(LocalTime.of(18,00),22);
		return columnSelector;
	}
	
}
