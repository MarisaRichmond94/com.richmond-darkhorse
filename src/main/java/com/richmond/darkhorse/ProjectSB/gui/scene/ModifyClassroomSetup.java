package com.richmond.darkhorse.ProjectSB.gui.scene;
import com.richmond.darkhorse.ProjectSB.Center;
import com.richmond.darkhorse.ProjectSB.Classroom;
import com.richmond.darkhorse.ProjectSB.Director;
import com.richmond.darkhorse.ProjectSB.Student;
import com.richmond.darkhorse.ProjectSB.Teacher;
import com.richmond.darkhorse.ProjectSB.gui.component.AddRemovePane;
import com.richmond.darkhorse.ProjectSB.gui.component.DirectorLayout;
import com.richmond.darkhorse.ProjectSB.gui.component.ImageButton;
import com.richmond.darkhorse.ProjectSB.gui.component.ModifyClassSizePane;
import com.richmond.darkhorse.ProjectSB.gui.component.TeacherInfoPane;
import com.richmond.darkhorse.ProjectSB.middleman.ChangeScene;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.MenuItem;

public class ModifyClassroomSetup extends Scene implements DirectorLayout{

	private Director director;
	private BorderPane modifyClassroomSetupLayout;
	private List<Label> labels = new ArrayList<Label>();
	private List<Student> currentlyDisplayedList = new ArrayList<Student>();
	private String currentTitle;
	private List<String> myStudentList = new ArrayList<String>();
	
	public ModifyClassroomSetup(Stage stage,Scene nextScene,Classroom classroom,Director director) {
		this(stage,new BorderPane(),nextScene,classroom,director);
	}
	
	public ModifyClassroomSetup(Stage stage,BorderPane layout,Scene nextScene,Classroom classroom,Director director) {
		super(layout);
		this.director = director;
		populateStudentList(classroom);
		this.currentTitle = "Class List";
		GridPane topPane = buildTopPane(stage,classroom);
		HBox bottomPane = buildBottomPane();
		VBox leftPane = buildLeftPane(director,stage,classroom);
		List<Student> studentsEnrolled = extractStudents(classroom);
		ScrollPane scrollPane = buildRightPane(classroom,studentsEnrolled);
		VBox centerPane = buildDefaultPane(classroom);
	    modifyClassroomSetupLayout = layout;
	    setBorderPaneRightScroll(modifyClassroomSetupLayout,centerPane,scrollPane,leftPane,topPane,bottomPane);
	    modifyClassroomSetupLayout.getStylesheets().add("css/director.css");
	}
	
	/**
	 * Builds the top pane of the overall BorderPane layout for {@link ModifyClassroomSetup}
	 * @param stage - the current stage
	 * @param classroom - the selected {@link Classroom} in the {@link Center}
	 * @return GridPane
	 */
	private GridPane buildTopPane(Stage stage,Classroom classroom) {
		GridPane toppane = new GridPane();
		setConstraints(toppane,4,0,10,10,"toppane");
		ImageButton classroomButton = new ImageButton(createImageWithFitHeight("images/classroom.png",125));
		classroomButton.setOnAction(e -> Platform.runLater(new ChangeScene(stage,new DirectorClassroomWorkspace(stage,null,director))));
		VBox returnButton = buildButtonWithLabel(classroomButton,createLabel("return","label"));
		Label classnameTitle = createLabel(classroom.getClassroomType(),"med-title");
		ImageButton logoutButton = new ImageButton(createImageWithFitHeight("images/logout.png",125));
		logoutButton.setOnAction(e -> saveInit(stage));
		VBox logout = buildButtonWithLabel(logoutButton,createLabel("logout","label"));
		placeNode(toppane,returnButton,0,0,"center",null);
		placeNodeSpan(toppane,classnameTitle,1,0,2,1,"center",null);
		placeNode(toppane,logout,3,0,"center",null);
		return toppane;
	}
	
	/**
	 * Builds the left pane (VBox)
	 * @param stage - the current stage 
	 * @return VBox
	 */
	private VBox buildLeftPane(Director director,Stage stage,Classroom classroom) {
		VBox leftPane = new VBox();
		leftPane.getStyleClass().add("leftpane");
		ImageButton addRemoveButton = new ImageButton(createImageWithFitHeight("images/addremove.png",100));
		addRemoveButton.setOnAction(e -> modifyClassroomSetupLayout.setCenter(new AddRemovePane(classroom,director)));
		Label addRemove = createLabel("add/remove","label");
		ImageButton classSizeButton = new ImageButton(createImageWithFitHeight("images/classsize.png",100));
		classSizeButton.setOnAction(e -> modifyClassroomSetupLayout.setCenter(new ModifyClassSizePane(classroom,director)));
		Label classSize = createLabel("modify ratio","label");
		ImageButton teacherButton = new ImageButton(createImageWithFitHeight("images/teacher.png",110));
		teacherButton.setOnAction(e -> teacherButton(classroom));
		Label teacherInfo = createLabel("teacher info","label");
		ImageButton returnButton = new ImageButton(createImageWithFitHeight("images/back.png",80));
		returnButton.setOnAction(e -> modifyClassroomSetupLayout.setCenter(buildDefaultPane(classroom)));
		Label returnLabel = createLabel("return","label");
		leftPane.getChildren().addAll(addRemoveButton,addRemove,classSizeButton,classSize,teacherButton,teacherInfo,returnButton,returnLabel);
		return leftPane;
	}
	
	/**
	 * Creates a right ScrollPane 
	 * @return ScrollPane
	 */
	private ScrollPane buildRightPane(Classroom classroom,List<Student> studentList) {
		GridPane rightpane = new GridPane();
		rightpane.getStyleClass().add("rightpane");
	    buildDefaultList(rightpane,classroom);
	    Label sideTitle = createLabel("Class List","label");
	    placeNode(rightpane,sideTitle,1,0,"center","center");
	    ImageView sortViewer = createImageWithFitHeight("images/sorting.png",30);
	    MenuButton sortingButton = buildSortingButton(rightpane,sideTitle,sortViewer);
	    ImageView optionsViewer = createImageWithFitHeight("images/options.png",20);
	    MenuButton optionsButton = buildOptionsButton(rightpane,classroom,sideTitle,optionsViewer);
	    placeNode(rightpane,sortingButton,0,0,null,null);
	    placeNode(rightpane,optionsButton,2,0,null,null);
	    ScrollPane scrollPane = new ScrollPane(rightpane);
		scrollPane.setFitToWidth(true);
		scrollPane.setFitToHeight(true);
		return scrollPane;
	}
	
	/**
	 * Builds the central pane for the overall BorderPane layout
	 * @param classroom - a {@link Classroom}
	 * @return GridPane
	 */
	private VBox buildDefaultPane(Classroom classroom) {
		GridPane gridpane = new GridPane();
		setConstraints(gridpane,3,0,10,10,"gridpane");
		Teacher leadTeacher = classroom.getTeacher(classroom.getTeacherID()), assistantTeacher = classroom.getAssistantTeacher(classroom.getAssistantTeacherID());
		List<Label> labels = populateLabels(Arrays.asList("lead teacher:","assistant teacher:","monday ratio:","tuesday ratio:","wednesday ratio:","thursday ratio:","friday ratio:","max capacity"),"centrallabel");
		String leadTeacherName = null, assistantTeacherName = null, mondayRatio = classroom.getCount("Monday") + "/" + classroom.getMaxSize(), tuesdayRatio = classroom.getCount("Tuesday") + "/" + classroom.getMaxSize(), wednesdayRatio = classroom.getCount("Wednesday") + "/" + classroom.getMaxSize(), thursdayRatio = classroom.getCount("Thursday") + "/" + classroom.getMaxSize(), fridayRatio = classroom.getCount("Friday") + "/" + classroom.getMaxSize(), capacityText = "" + classroom.getMaxSize() + "";
	    if(leadTeacher != null) {leadTeacherName = leadTeacher.getFirstName() + " " + leadTeacher.getLastName();}
	    else {leadTeacherName = "N/A";}
	    if(assistantTeacher != null) {assistantTeacherName = assistantTeacher.getFirstName() + " " + assistantTeacher.getLastName();}
	    else {assistantTeacherName = "N/A";}
		List<TextField> textfields = populateTextFields(Arrays.asList(leadTeacherName,assistantTeacherName,mondayRatio,tuesdayRatio,wednesdayRatio,thursdayRatio,fridayRatio,capacityText),"textfield",650);
		disableClassInfo(textfields);
		List<Node> nodes = Arrays.asList(labels.get(0),textfields.get(0),labels.get(1),textfields.get(1),labels.get(2),textfields.get(2),labels.get(3),textfields.get(3),labels.get(4),textfields.get(4),labels.get(5),textfields.get(5),labels.get(6),textfields.get(6),labels.get(7),textfields.get(7));
	    placeNodes(gridpane,nodes);
	    VBox wrapper = new VBox();
		wrapper.getChildren().add(gridpane);
		wrapper.setAlignment(Pos.CENTER);
	    return wrapper;
	}
	
	@Override
	public void placeNodes(GridPane gridpane, List<Node> nodes) {
		placeNode(gridpane,nodes.get(0),0,0,"right",null);
		placeNodeSpan(gridpane,nodes.get(1),1,0,2,1,"left",null);
		placeNode(gridpane,nodes.get(2),0,1,"right",null);
		placeNodeSpan(gridpane,nodes.get(3),1,1,2,1,"left",null);
		placeNode(gridpane,nodes.get(4),0,2,"right",null);
		placeNodeSpan(gridpane,nodes.get(5),1,2,2,1,"left",null);
		placeNode(gridpane,nodes.get(6),0,3,"right",null);
		placeNodeSpan(gridpane,nodes.get(7),1,3,2,1,"left",null);
		placeNode(gridpane,nodes.get(8),0,4,"right",null);
		placeNodeSpan(gridpane,nodes.get(9),1,4,2,1,"left",null);
		placeNode(gridpane,nodes.get(10),0,5,"right",null);
		placeNodeSpan(gridpane,nodes.get(11),1,5,2,1,"left",null);
		placeNode(gridpane,nodes.get(12),0,6,"right",null);
		placeNodeSpan(gridpane,nodes.get(13),1,6,2,1,"left",null);
		placeNode(gridpane,nodes.get(14),0,7,"right",null);
		placeNodeSpan(gridpane,nodes.get(15),1,7,2,1,"left",null);
	}
	
	/**
	 * Extracts all of the {@link Student}s from the given {@link Classroom}
	 * @param classroom - a {@link Classroom}
	 * @return a List of {@link Student}s
	 */
	private List<Student> extractStudents(Classroom classroom){
		List<Student> studentsEnrolled = new ArrayList<Student>();
		List<String> studentIDs = classroom.getStudentsEnrolled();
		for(String studentID : studentIDs) {
			Student student = classroom.getStudent(studentID);
			studentsEnrolled.add(student);
		}
		return studentsEnrolled;
	}
	
	/**
	 * Builds a MenuButton with various sorting options
	 * @param rightpane - GridPane
	 * @param sideTitle - Label
	 * @param sortViewer - ImageView
	 * @return MenuButton
	 */
	private MenuButton buildSortingButton(GridPane rightpane,Label sideTitle,ImageView sortViewer) {
		MenuButton sortingButton = new MenuButton("");
	    sortingButton.setGraphic(sortViewer);
	    sortingButton.getStyleClass().add("button");
	    MenuItem alphabeticalByFirst = new MenuItem("A-Z (first)");
	    alphabeticalByFirst.setOnAction(e -> {sortAlphabeticalByFirst(currentlyDisplayedList,rightpane,sideTitle,currentTitle);});
	    MenuItem alphabeticalByLast = new MenuItem("A-Z (last)");
	    alphabeticalByLast.setOnAction(e -> {sortAlphabeticalByLast(currentlyDisplayedList,rightpane,sideTitle,currentTitle);});
	    MenuItem reverseAlphabeticalByFirst = new MenuItem("Z-A (first)");
	    reverseAlphabeticalByFirst.setOnAction(e -> {sortReverseAlphabeticalByFirst(currentlyDisplayedList,rightpane,sideTitle,currentTitle);});
	    MenuItem reverseAlphabeticalByLast = new MenuItem("Z-A (last)"); 
	    reverseAlphabeticalByLast.setOnAction(e -> {sortReverseAlphabeticalByLast(currentlyDisplayedList,rightpane,sideTitle,currentTitle);});
	    sortingButton.getItems().addAll(alphabeticalByFirst,alphabeticalByLast,reverseAlphabeticalByFirst,reverseAlphabeticalByLast);
	    return sortingButton;
	}
	
	/**
	 * Builds a MenuButton with the different view options 
	 * @param rightpane - GridPane
	 * @param classroom - an {@link Classroom}
	 * @param sideTitle - Label
	 * @param optionsViewer - ImageView
	 * @return MenuButton
	 */
	private MenuButton buildOptionsButton(GridPane rightpane,Classroom classroom,Label sideTitle,ImageView optionsViewer) {
		MenuButton optionsButton = new MenuButton("");
	    optionsButton.setGraphic(optionsViewer);
	    MenuItem totalList = new MenuItem("All");
	    totalList.setOnAction(e -> displayWholeList(classroom,rightpane,sideTitle));
	    MenuItem mondayList = new MenuItem("Monday");
	    mondayList.setOnAction(e -> displayDayList(classroom,rightpane,"Monday",sideTitle));
	    MenuItem tuesdayList = new MenuItem("Tuesday"); 
	    tuesdayList.setOnAction(e -> displayDayList(classroom,rightpane,"Tuesday",sideTitle));
	    MenuItem wednesdayList = new MenuItem("Wednesday");
	    wednesdayList.setOnAction(e -> displayDayList(classroom,rightpane,"Wednesday",sideTitle));
	    MenuItem thursdayList = new MenuItem("Thursday"); 
	    thursdayList.setOnAction(e -> displayDayList(classroom,rightpane,"Thursday",sideTitle));
	    MenuItem fridayList = new MenuItem("Friday"); 
	    fridayList.setOnAction(e -> displayDayList(classroom,rightpane,"Friday",sideTitle));
	    optionsButton.getItems().addAll(totalList,mondayList,tuesdayList,wednesdayList,thursdayList,fridayList);
	    optionsButton.getStyleClass().add("button");
	    return optionsButton;
	}
	
	/**
	 * Displays the entire {@link Classroom}'s {@link Student} list
	 * @param classroom - a {@link Classroom}
	 * @param rightpane - GridPane
	 * @param sideTitle - Label
	 */
	private void displayWholeList(Classroom classroom,GridPane rightpane,Label sideTitle) {
		List<String> studentStrings = classroom.getStudentsEnrolled();
		List<Student> newStudentList = new ArrayList<Student>();
		for(String studentID : studentStrings) {
			Student student = classroom.getStudent(studentID);
			newStudentList.add(student);
		}
		this.currentTitle = "All";
		this.currentlyDisplayedList = newStudentList;
		buildStudentList(rightpane,sideTitle,"All",newStudentList);
	}
	
	/**
	 * Displays the {@link Classroom}'s {@link Student} list for a particular day
	 * @param classroom - a {@link Classroom}
	 * @param rightpane - GridPane
	 * @param day - String representing the day of the week
	 * @param sideTitle - Label
	 */
	private void displayDayList(Classroom classroom,GridPane rightpane,String day,Label sideTitle) {
		List<String> dayStringList = classroom.generateClassList(day); 
		List<Student> dayStudentList = new ArrayList<Student>();
		for(String studentID : dayStringList) {
			Student student = classroom.getStudent(studentID);
			dayStudentList.add(student);
		}
		this.currentTitle = day;
		this.currentlyDisplayedList = dayStudentList;
		buildStudentList(rightpane,sideTitle,day,dayStudentList);
	}
	
	private void teacherButton(Classroom classroom) {
		if(classroom.getTeacherID() != null && classroom.getAssistantTeacherID() != null) {
			Teacher teacher = classroom.getTeacher(classroom.getTeacherID());
			Teacher assistantTeacher = classroom.getAssistantTeacher(classroom.getAssistantTeacherID());
			modifyClassroomSetupLayout.setCenter(new TeacherInfoPane(director,teacher,assistantTeacher));
		}else if(classroom.getTeacherID() != null) {
			Teacher teacher = classroom.getTeacher(classroom.getTeacherID());
			modifyClassroomSetupLayout.setCenter(new TeacherInfoPane(director,teacher,null));
		}
	}
	
	/**
	 * Constructs the default {@link Classroom} list of {@link Student}s
	 * @param rightPane - GridPane
	 * @param classroom - a {@link Classroom}
	 */
	private void buildDefaultList(GridPane rightPane,Classroom classroom) {
		List<String> studentStrings = classroom.getStudentsEnrolled();
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
	    		Label noStudentsEnrolled = createLabel("No students enrolled","label");
	    		labels.add(noStudentsEnrolled);
	    		placeNode(rightPane,noStudentsEnrolled,1,2,"center",null);
	    }
	}
	
	/**
	 * Constructs a {@link Student} list of the {@link Classroom}
	 * @param rightPane - GridPane
	 * @param sideTitle - Label
	 * @param title - String
	 * @param newList - List of {@link Student}s
	 */
	private void buildStudentList(GridPane rightPane,Label sideTitle,String title,List<Student> newList){
		sideTitle = createLabel(title,"side-title");
		for(Label label : labels) {label.setText("");}
	    if(!newList.isEmpty()) {
		    int columnIndex = 1, rowIndex = 1;
		    for(Student student : newList) {
		    		Label studentLabel = createLabel(student.getFirstName() + " " + student.getLastName(),"label");
		    		labels.add(studentLabel);
		    		placeNode(rightPane,studentLabel,columnIndex,rowIndex,"center",null);
		    		rowIndex++;
		    }
	    }else {
	    		Label noStudentsEnrolled = createLabel("No students enrolled","label");
	    		labels.add(noStudentsEnrolled);
	    		placeNode(rightPane,noStudentsEnrolled,1,2,"center",null);
	    }
	}
	
	/**
	 * Sorts the list of {@link Student}s in the {@link Classroom} alphabetically by their first name
	 * @param unsortedList - an unsorted List of {@link Student}s
	 * @param rightPane - GridPane
	 * @param sideTitle - Label
	 * @param title - String
	 */
	private void sortAlphabeticalByFirst(List<Student> unsortedList,GridPane rightPane,Label sideTitle,String title){
		boolean isAtoZ = true;
		for(Student student : unsortedList) {
			String fullName = student.getFirstName() + " " + student.getLastName();
			myStudentList.add(fullName);
		}
		List<String> sortedList = sort(myStudentList,isAtoZ);
		List<Student> sortedStudentList = new ArrayList<Student>();
		for(String name : sortedList) {
			boolean match = false;
			int index = 0;
			while(match == false & index < unsortedList.size()) {
				match = false;
				Student student = unsortedList.get(index);
				String fullName = student.getFirstName() + " " + student.getLastName();
				if(fullName.compareTo(name) == 0) {
					sortedStudentList.add(student);
					match = true;
				}
				index++;
			}
		}
		buildStudentList(rightPane,sideTitle,title,sortedStudentList);
		myStudentList.clear();
	}
	
	/**
	 * Sorts the list of {@link Student}s in the {@link Classroom} alphabetically by their last name
	 * @param unsortedList - an unsorted List of {@link Student}s
	 * @param rightPane - GridPane
	 * @param sideTitle - Label
	 * @param title - String
	 */
	private void sortAlphabeticalByLast(List<Student> unsortedList,GridPane rightPane,Label sideTitle,String title) {
		boolean isAtoZ = true;
		for(Student student : unsortedList) {
			String fullNameReverse = student.getLastName() + " " + student.getFirstName();
			myStudentList.add(fullNameReverse);
		}
		List<String> sortedList = sort(myStudentList,isAtoZ);
		List<Student> sortedStudentList = new ArrayList<Student>();
		for(String reverseName : sortedList) {
			boolean match = false;
			int index = 0;
			while(match == false && index < unsortedList.size()) {
				match = false;
				Student student = unsortedList.get(index);
				String reverseNameCheck = student.getLastName() + " " + student.getFirstName();
				if(reverseName.compareTo(reverseNameCheck) == 0) {
					sortedStudentList.add(student);
					match = true;
				}
				index++;
			}
		}
		buildStudentList(rightPane,sideTitle,title,sortedStudentList); 
		myStudentList.clear();
	}
	
	/**
	 * Sorts the list of {@link Student}s in the {@link Classroom} reverse alphabetically by their first name
	 * @param unsortedList - an unsorted List of {@link Student}s
	 * @param rightPane - GridPane
	 * @param sideTitle - Label
	 * @param title - String
	 */
	private void sortReverseAlphabeticalByFirst(List<Student> unsortedList,GridPane rightPane,Label sideTitle,String title){
		boolean isAtoZ = false;
		for(Student student : unsortedList) {
			String fullName = student.getFirstName() + " " + student.getLastName();
			myStudentList.add(fullName);
		}
		List<String> sortedList = sort(myStudentList,isAtoZ);
		List<Student> sortedStudentList = new ArrayList<Student>();
		for(String name : sortedList) {
			boolean match = false;
			int index = 0;
			while(match == false & index < unsortedList.size()) {
				match = false;
				Student student = unsortedList.get(index);
				String fullName = student.getFirstName() + " " + student.getLastName();
				if(fullName.compareTo(name) == 0) {
					sortedStudentList.add(student);
					match = true;
				}
				index++;
			}
		}
		buildStudentList(rightPane,sideTitle,title,sortedStudentList);
		myStudentList.clear();
	}
	
	/**
	 * Sorts the list of {@link Student}s in the {@link Classroom} reverse alphabetically by their last name
	 * @param unsortedList - an unsorted List of {@link Student}s
	 * @param rightPane - GridPane
	 * @param sideTitle - Label
	 * @param title - String
	 */
	private void sortReverseAlphabeticalByLast(List<Student> unsortedList,GridPane rightPane,Label sideTitle,String title) {
		boolean isAtoZ = false;
		for(Student student : unsortedList) {
			String fullNameReverse = student.getLastName() + " " + student.getFirstName();
			myStudentList.add(fullNameReverse);
		}
		List<String> sortedList = sort(myStudentList,isAtoZ);
		List<Student> sortedStudentList = new ArrayList<Student>();
		for(String reverseName : sortedList) {
			boolean match = false;
			int index = 0;
			while(match == false && index < unsortedList.size()) {
				match = false;
				Student student = unsortedList.get(index);
				String reverseNameCheck = student.getLastName() + " " + student.getFirstName();
				if(reverseName.compareTo(reverseNameCheck) == 0) {
					sortedStudentList.add(student);
					match = true;
				}
				index++;
			}
		}
		buildStudentList(rightPane,sideTitle,title,sortedStudentList); 
		myStudentList.clear();
	}
	
	/**
	 * Sorts a list of {@link Student}s in the {@link Classroom} 
	 * @param unsortedList - an unsorted List of {@link Student}s
	 * @param isAtoZ - boolean representing the sorting option selected
	 */
	public List<String> sort(List<String> unsortedList,boolean isAtoZ) {
		this.myStudentList = unsortedList;
		if(myStudentList.size() <= 1) {return myStudentList;}
		int start = 0, end = myStudentList.size()-1;
		quickSort(myStudentList,start,end,isAtoZ);
		return myStudentList;
	}
	
	/**
	 * Sorting algorithm that works by breaking the given List of {@link Student}s into two Lists recursively until they are length 1 and then puts them
	 * back together in order
	 * @param myStudentList - a List of Strings 
	 * @param start - integer
	 * @param end - integer
	 * @param isAtoZ - boolean 
	 */
	public void quickSort(List<String> myStudentList,int start,int end,boolean isAtoZ){
		if(start < end) {
			int pIndex = partition(myStudentList,start,end,isAtoZ);
			quickSort(myStudentList,start,pIndex-1,isAtoZ);
			quickSort(myStudentList,pIndex+1,end,isAtoZ);
		}
	}
	
	/**
	 * Part of quick sort algorithm that separates the given List in half
	 * @param myStudentList - List of String
	 * @param start - integer
	 * @param end - integer
	 * @param isAtoZ - boolean
	 * @return an integer representing the partition point
	 */
	public int partition(List<String> myStudentList,int start,int end,boolean isAtoZ) {
		int pivot = end, startIndex = start, switchIndex = start, wall = start;
		String pivotValue = myStudentList.get(pivot);
		while(startIndex < end) {
			if(isAtoZ == true) {
				while(myStudentList.get(startIndex).compareTo(myStudentList.get(pivot)) > 0) {startIndex++;}
			}else {
				while(myStudentList.get(startIndex).compareTo(myStudentList.get(pivot)) < 0) {startIndex++;}	
			}
			if(startIndex < end) {
				String valueCheck = myStudentList.get(startIndex), switchValue = myStudentList.get(switchIndex);
				myStudentList.set(switchIndex, valueCheck);
				myStudentList.set(startIndex, switchValue);
				wall++;
				startIndex++;
				switchIndex++;
				if(startIndex == end) {
					String wallValue = myStudentList.get(wall);
					myStudentList.set(wall, pivotValue);
					myStudentList.set(pivot, wallValue);
				}
			}else {
				String wallValue = myStudentList.get(wall);
				myStudentList.set(wall, pivotValue);
				myStudentList.set(pivot, wallValue);
			}
		}
		return wall;
	}
	
	/**
	 * Populates the List of {@link Student}s in the {@link Classroom}
	 * @param classroom - a {@link Classroom}
	 */
	public void populateStudentList(Classroom classroom) {
		List<String> students = classroom.getStudentsEnrolled();
		for(String studentID : students) {
			Student student = classroom.getStudent(studentID);
			currentlyDisplayedList.add(student);
		}
	}
	
	/**
	 * Disables the Passed List of TextFields
	 * @param textfields - List of TextField
	 */
	private void disableClassInfo(List<TextField> textfields) {
		for(TextField textfield : textfields) {textfield.setDisable(true);}
	}
	
}
