package com.richmond.darkhorse.ProjectSB.gui.scene;
import com.richmond.darkhorse.ProjectSB.AccountManager;
import com.richmond.darkhorse.ProjectSB.Center;
import com.richmond.darkhorse.ProjectSB.Classroom;
import com.richmond.darkhorse.ProjectSB.Director;
import com.richmond.darkhorse.ProjectSB.SpecialBeginnings;
import com.richmond.darkhorse.ProjectSB.Student;
import com.richmond.darkhorse.ProjectSB.Teacher;
import com.richmond.darkhorse.ProjectSB.gui.component.AddRemovePane;
import com.richmond.darkhorse.ProjectSB.gui.component.ImageButton;
import com.richmond.darkhorse.ProjectSB.gui.component.ModifyClassSizePane;
import com.richmond.darkhorse.ProjectSB.gui.component.TeacherInfoPane;
import com.richmond.darkhorse.ProjectSB.middleman.ChangeScene;
import javafx.application.Platform;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.MenuItem;

public class ModifyClassroomSetup extends Scene{

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
		
		GridPane topBar = buildTopBar(stage,classroom.getClassroomType());
		HBox bottomBar = buildBottomBar();
		VBox leftSideBar = buildLeftSideBar(director,stage,classroom);
		List<Student> studentsEnrolled = extractStudents(classroom);
		ScrollPane scrollPane = buildRightSideBar(classroom,studentsEnrolled);
		GridPane centerPane = buildDefaultPane(classroom);
	    
	    modifyClassroomSetupLayout = layout;
	    modifyClassroomSetupLayout.setTop(topBar);
	    modifyClassroomSetupLayout.setBottom(bottomBar);
	    modifyClassroomSetupLayout.setLeft(leftSideBar);
	    modifyClassroomSetupLayout.setRight(scrollPane);
	    modifyClassroomSetupLayout.setCenter(centerPane);
	    modifyClassroomSetupLayout.getStylesheets().add("modifyclassroomsetup.css");
	}
	
	private GridPane buildTopBar(Stage stage,String classType) {
		GridPane topBar = new GridPane();
		topBar.setVgap(0);
		topBar.setHgap(0);
		ColumnConstraints columnOne = new ColumnConstraints();
		columnOne.setPercentWidth(25);
	    ColumnConstraints columnTwo = new ColumnConstraints();
	    columnTwo.setPercentWidth(25);
	    ColumnConstraints columnThree = new ColumnConstraints();
	    columnThree.setPercentWidth(25);
	    ColumnConstraints columnFour = new ColumnConstraints();
	    columnFour.setPercentWidth(25);
	    topBar.getColumnConstraints().addAll(columnOne,columnTwo,columnThree,columnFour);
	    topBar.getStyleClass().add("topbar");
	    
		ImageView classroomViewer = new ImageView();
		Image classroomIcon = new Image("classroom.png");
		classroomViewer.setImage(classroomIcon);
		classroomViewer.setPreserveRatio(true);
		classroomViewer.setFitHeight(125);
		ImageButton classroomButton = new ImageButton(classroomViewer);
		classroomButton.setOnAction(e -> Platform.runLater(new ChangeScene(stage,new DirectorClassroomWorkspace(stage,null,director))));
		classroomButton.getStyleClass().add("button");
		Label returnToClassrooms = new Label("<--return");
		returnToClassrooms.setTranslateY(-30);
		returnToClassrooms.setTranslateX(35);
		returnToClassrooms.getStyleClass().add("label");
		
		Label classnameTitle = new Label(classType);
		classnameTitle.getStyleClass().add("title");
		
		ImageView logoutViewer = new ImageView();
		Image lB = new Image("logout.png");
		logoutViewer.setImage(lB);
		logoutViewer.setPreserveRatio(true);
		logoutViewer.setFitHeight(125);
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
		logoutButton.getStyleClass().add("button");
		Label logoutLabel = new Label("logout");
		logoutLabel.setTranslateY(-30);
		logoutLabel.setTranslateX(-40);
		logoutLabel.getStyleClass().add("label");
		
		topBar.add(classroomButton,0,0);
		GridPane.setHalignment(classroomButton,HPos.LEFT);
		topBar.add(returnToClassrooms,0,1);
		GridPane.setHalignment(returnToClassrooms,HPos.LEFT);
		topBar.add(classnameTitle,1,0);
		GridPane.setHalignment(classnameTitle,HPos.CENTER);
		GridPane.setConstraints(classnameTitle,1,0,2,1);
		topBar.add(logoutButton,3,0);
		GridPane.setHalignment(logoutButton,HPos.RIGHT);
		topBar.add(logoutLabel,3,1);
		GridPane.setHalignment(logoutLabel,HPos.RIGHT);
		return topBar;
	}
	
	public HBox buildBottomBar() {
		HBox bottomBar = new HBox();
		Label signature = new Label("Created by Marisa Richmond");
		signature.getStyleClass().add("text");
		bottomBar.getChildren().add(signature);
		bottomBar.getStyleClass().add("bottombar");
		return bottomBar;
	}
	
	private VBox buildLeftSideBar(Director director,Stage stage,Classroom classroom) {
		VBox leftSideBar = new VBox();
		leftSideBar.getStyleClass().add("leftbar");
		ImageView addRemoveViewer = new ImageView();
		Image addRemoveIcon = new Image("addremove.png");
		addRemoveViewer.setImage(addRemoveIcon);
		addRemoveViewer.setPreserveRatio(true);
		addRemoveViewer.setFitHeight(100);
		ImageButton addRemoveButton = new ImageButton(addRemoveViewer);
		addRemoveButton.setOnAction(e -> {
			modifyClassroomSetupLayout.setCenter(new AddRemovePane(classroom,director));
		});
		addRemoveButton.getStyleClass().add("button");
		Label addRemove = new Label("add/remove");
		addRemove.getStyleClass().add("label");
		
		ImageView classSizeViewer = new ImageView();
		Image classSizeIcon = new Image("classsize.png");
		classSizeViewer.setImage(classSizeIcon);
		classSizeViewer.setPreserveRatio(true);
		classSizeViewer.setFitHeight(100);
		ImageButton classSizeButton = new ImageButton(classSizeViewer);
		classSizeButton.setOnAction(e -> {
			modifyClassroomSetupLayout.setCenter(new ModifyClassSizePane(classroom,director));
		});
		classSizeButton.getStyleClass().add("button");
		Label classSize = new Label("modify ratio");
		classSize.getStyleClass().add("label");
		
		ImageView teacherViewer = new ImageView();
		Image teacherIcon = new Image("teacher.png");
		teacherViewer.setImage(teacherIcon);
		teacherViewer.setPreserveRatio(true);
		teacherViewer.setFitHeight(110);
		ImageButton teacherButton = new ImageButton(teacherViewer);
		teacherButton.setOnAction(e -> {
			if(classroom.getTeacherID() != null && classroom.getAssistantTeacherID() != null) {
				Teacher teacher = classroom.getTeacher(classroom.getTeacherID());
				Teacher assistantTeacher = classroom.getAssistantTeacher(classroom.getAssistantTeacherID());
				modifyClassroomSetupLayout.setCenter(new TeacherInfoPane(director,teacher,assistantTeacher));
			}else if(classroom.getTeacherID() != null) {
				Teacher teacher = classroom.getTeacher(classroom.getTeacherID());
				modifyClassroomSetupLayout.setCenter(new TeacherInfoPane(director,teacher,null));
			}
		});
		teacherButton.getStyleClass().add("button");
		Label teacherInfo = new Label("teacher info");
		teacherInfo.getStyleClass().add("label");
		
		ImageView returnViewer = new ImageView();
		Image returnIcon = new Image("back.png");
		returnViewer.setImage(returnIcon);
		returnViewer.setPreserveRatio(true);
		returnViewer.setFitHeight(80);
		ImageButton returnButton = new ImageButton(returnViewer);
		returnButton.setOnAction(e -> {
			modifyClassroomSetupLayout.setCenter(buildDefaultPane(classroom));
		});
		returnButton.getStyleClass().add("button");
		Label returnLabel = new Label("return");
		returnLabel.getStyleClass().add("label");
		
		leftSideBar.getChildren().addAll(addRemoveButton,addRemove,classSizeButton,classSize,teacherButton,teacherInfo,returnButton,returnLabel);
		return leftSideBar;
	}
	
	private ScrollPane buildRightSideBar(Classroom classroom,List<Student> studentList) {
		GridPane rightSideBar = new GridPane();
	    rightSideBar.getStyleClass().add("rightbar");
	    
	    buildDefaultList(rightSideBar,classroom);
	    Label sideTitle = new Label("Class List");
	    sideTitle.getStyleClass().add("label");
	    rightSideBar.add(sideTitle,1,0);
	    GridPane.setHalignment(sideTitle,HPos.CENTER);
	    GridPane.setValignment(sideTitle,VPos.CENTER);

	    ImageView sortViewer = new ImageView();
	    Image sortIcon = new Image("sorting.png");
	    sortViewer.setImage(sortIcon);
	    sortViewer.setPreserveRatio(true);
	    sortViewer.setFitHeight(30);
	    MenuButton sortingButton = new MenuButton("");
	    sortingButton.setGraphic(sortViewer);
	    sortingButton.getStyleClass().add("button");
	    MenuItem alphabeticalByFirst = new MenuItem("A-Z (first)");
	    alphabeticalByFirst.setOnAction(e -> {sortAlphabeticalByFirst(currentlyDisplayedList,rightSideBar,sideTitle,currentTitle);});
	    MenuItem alphabeticalByLast = new MenuItem("A-Z (last)");
	    alphabeticalByLast.setOnAction(e -> {sortAlphabeticalByLast(currentlyDisplayedList,rightSideBar,sideTitle,currentTitle);});
	    MenuItem reverseAlphabeticalByFirst = new MenuItem("Z-A (first)");
	    reverseAlphabeticalByFirst.setOnAction(e -> {sortReverseAlphabeticalByFirst(currentlyDisplayedList,rightSideBar,sideTitle,currentTitle);});
	    MenuItem reverseAlphabeticalByLast = new MenuItem("Z-A (last)"); 
	    reverseAlphabeticalByLast.setOnAction(e -> {sortReverseAlphabeticalByLast(currentlyDisplayedList,rightSideBar,sideTitle,currentTitle);});
	    sortingButton.getItems().addAll(alphabeticalByFirst,alphabeticalByLast,reverseAlphabeticalByFirst,reverseAlphabeticalByLast);
	    
	    ImageView optionsViewer = new ImageView();
	    Image optionsIcon = new Image("options.png");
	    optionsViewer.setImage(optionsIcon);
	    optionsViewer.setPreserveRatio(true);
	    optionsViewer.setFitHeight(20);
	    MenuButton optionsButton = new MenuButton("");
	    optionsButton.setGraphic(optionsViewer);
	    MenuItem totalList = new MenuItem("All");
	    totalList.setOnAction(e -> {
	    		List<String> studentStrings = classroom.getStudentsEnrolled();
	    		List<Student> newStudentList = new ArrayList<Student>();
	    		for(String studentID : studentStrings) {
	    			Student student = classroom.getStudent(studentID);
	    			newStudentList.add(student);
	    		}
	    		this.currentTitle = "All";
	    		this.currentlyDisplayedList = newStudentList;
	    		buildStudentList(rightSideBar,sideTitle,"All",newStudentList);
	    	});
	    MenuItem mondayList = new MenuItem("Monday");
	    mondayList.setOnAction(e -> {
	    		List<String> mondayStringList = classroom.generateClassList("Monday",classroom.getStudentsEnrolled()); 
	    		List<Student> mondayStudentList = new ArrayList<Student>();
	    		for(String studentID : mondayStringList) {
	    			Student student = classroom.getStudent(studentID);
	    			mondayStudentList.add(student);
	    		}
	    		this.currentTitle = "Monday";
	    		this.currentlyDisplayedList = mondayStudentList;
	    		buildStudentList(rightSideBar,sideTitle,"Monday",mondayStudentList);
	    	});
	    MenuItem tuesdayList = new MenuItem("Tuesday"); 
	    tuesdayList.setOnAction(e -> {
		    	List<String> tuesdayStringList = classroom.generateClassList("Tuesday",classroom.getStudentsEnrolled()); 
	    		List<Student> tuesdayStudentList = new ArrayList<Student>();
	    		for(String studentID : tuesdayStringList) {
	    			Student student = classroom.getStudent(studentID);
	    			tuesdayStudentList.add(student);
	    		}
	    		this.currentTitle = "Tuesday";
	    		this.currentlyDisplayedList = tuesdayStudentList;
	    		buildStudentList(rightSideBar,sideTitle,"Tuesday",tuesdayStudentList);
	    });
	    MenuItem wednesdayList = new MenuItem("Wednesday");
	    wednesdayList.setOnAction(e -> {
		    	List<String> wednesdayStringList = classroom.generateClassList("Wednesday",classroom.getStudentsEnrolled()); 
	    		List<Student> wednesdayStudentList = new ArrayList<Student>();
	    		for(String studentID : wednesdayStringList) {
	    			Student student = classroom.getStudent(studentID);
	    			wednesdayStudentList.add(student);
	    		}
	    		this.currentTitle = "Wednesday";
	    		this.currentlyDisplayedList = wednesdayStudentList;
	    		buildStudentList(rightSideBar,sideTitle,"Wednesday",wednesdayStudentList);
	    });
	    MenuItem thursdayList = new MenuItem("Thursday"); 
	    thursdayList.setOnAction(e -> {
		    	List<String> thursdayStringList = classroom.generateClassList("Thursday",classroom.getStudentsEnrolled()); 
	    		List<Student> thursdayStudentList = new ArrayList<Student>();
	    		for(String studentID : thursdayStringList) {
	    			Student student = classroom.getStudent(studentID);
	    			thursdayStudentList.add(student);
	    		}
	    		this.currentTitle = "Thursday";
	    		this.currentlyDisplayedList = thursdayStudentList;
	    		buildStudentList(rightSideBar,sideTitle,"Thursday",thursdayStudentList);
	    });
	    MenuItem fridayList = new MenuItem("Friday"); 
	    fridayList.setOnAction(e -> {
		    	List<String> fridayStringList = classroom.generateClassList("Friday",classroom.getStudentsEnrolled()); 
	    		List<Student> fridayStudentList = new ArrayList<Student>();
	    		for(String studentID : fridayStringList) {
	    			Student student = classroom.getStudent(studentID);
	    			fridayStudentList.add(student);
	    		}
	    		this.currentTitle = "Friday";
	    		this.currentlyDisplayedList = fridayStudentList;
	    		buildStudentList(rightSideBar,sideTitle,"Friday",fridayStudentList);
	    });
	    optionsButton.getItems().addAll(totalList,mondayList,tuesdayList,wednesdayList,thursdayList,fridayList);
	    optionsButton.getStyleClass().add("button");
	    
	    rightSideBar.add(sortingButton,0,0);
	    rightSideBar.add(optionsButton,2,0);
	    ScrollPane scrollPane = new ScrollPane(rightSideBar);

		scrollPane.setFitToWidth(true);
		scrollPane.setFitToHeight(true);
		return scrollPane;
	}
	
	private GridPane buildDefaultPane(Classroom classroom) {
		GridPane centerPane = new GridPane();
	    centerPane.setVgap(10);
	    centerPane.setHgap(10);
		ColumnConstraints columnOne = new ColumnConstraints();
		columnOne.setPercentWidth(33.3);
	    ColumnConstraints columnTwo = new ColumnConstraints();
	    columnTwo.setPercentWidth(33.3);
	    ColumnConstraints columnThree = new ColumnConstraints();
	    columnThree.setPercentWidth(33.3);
	    centerPane.getColumnConstraints().addAll(columnOne,columnTwo,columnThree);
	    centerPane.getStyleClass().add("gridpane");
	    
	    Teacher leadTeacher = classroom.getTeacher(classroom.getTeacherID());
	    Label lead = new Label("lead teacher: ");
	    lead.getStyleClass().add("centrallabel");
	    TextField leadTeacherField = new TextField();
	    if(leadTeacher != null) {leadTeacherField.setPromptText(leadTeacher.getFirstName() + " " + leadTeacher.getLastName());
	    }else {leadTeacherField.setPromptText("N/A");}
	    leadTeacherField.setDisable(true);
	    leadTeacherField.getStyleClass().add("textfield");
	    leadTeacherField.setMaxWidth(600);
	    
	    Teacher assistantTeacher = classroom.getAssistantTeacher(classroom.getAssistantTeacherID());
	    Label assistant = new Label("assistant teacher: ");
	    assistant.getStyleClass().add("centrallabel");
	    TextField assistantTeacherField = new TextField();
	    if(assistantTeacher != null) {assistantTeacherField.setPromptText(assistantTeacher.getFirstName() + " " + assistantTeacher.getLastName());
	    }else {assistantTeacherField.setPromptText("N/A");}
	    assistantTeacherField.setDisable(true);
	    assistantTeacherField.getStyleClass().add("textfield");
	    assistantTeacherField.setMaxWidth(600);
	    
	    Label monday = new Label("monday ratio: ");
	    monday.getStyleClass().add("centrallabel");
	    TextField mondayField = new TextField();
	    mondayField.setPromptText(classroom.getCount("Monday") + "/" + classroom.getMaxSize());
	    mondayField.setDisable(true);
	    mondayField.getStyleClass().add("textfield");
	    mondayField.setMaxWidth(600);
	    
	    Label tuesday = new Label("tuesday ratio: ");
	    tuesday.getStyleClass().add("centrallabel");
	    TextField tuesdayField = new TextField();
	    tuesdayField.setPromptText(classroom.getCount("Tuesday") + "/" + classroom.getMaxSize());
	    tuesdayField.setDisable(true);
	    tuesdayField.getStyleClass().add("textfield");
	    tuesdayField.setMaxWidth(600);
	    
	    Label wednesday = new Label("wednesday ratio: ");
	    wednesday.getStyleClass().add("centrallabel");
	    TextField wednesdayField = new TextField();
	    wednesdayField.setPromptText(classroom.getCount("Wednesday") + "/" + classroom.getMaxSize());
	    wednesdayField.setDisable(true);
	    wednesdayField.getStyleClass().add("textfield");
	    wednesdayField.setMaxWidth(600);
	    
	    Label thursday = new Label("thursday ratio: ");
	    thursday.getStyleClass().add("centrallabel");
	    TextField thursdayField = new TextField();
	    thursdayField.setPromptText(classroom.getCount("Thursday") + "/" + classroom.getMaxSize());
	    thursdayField.setDisable(true);
	    thursdayField.getStyleClass().add("textfield");
	    thursdayField.setMaxWidth(600);
	    
	    Label friday = new Label("friday ratio: ");
	    friday.getStyleClass().add("centrallabel");
	    TextField fridayField = new TextField();
	    fridayField.setPromptText(classroom.getCount("Friday") + "/" + classroom.getMaxSize());
	    fridayField.setDisable(true);
	    fridayField.getStyleClass().add("textfield");
	    fridayField.setMaxWidth(600);
	    
	    Label capacity = new Label("Max capacity:");
	    capacity.getStyleClass().add("centrallabel");
	    TextField capacityField = new TextField();
	    capacityField.setPromptText("" + classroom.getMaxSize() + "");
	    capacityField.setDisable(true);
	    capacityField.setMaxWidth(600);
	    capacityField.getStyleClass().add("textfield");
	    
	    centerPane.add(lead,0,0);
	    GridPane.setHalignment(lead,HPos.RIGHT);
	    centerPane.add(leadTeacherField,1,0);
	    GridPane.setConstraints(leadTeacherField,1,0,2,1);
	    centerPane.add(assistant,0,1);
	    GridPane.setHalignment(assistant,HPos.RIGHT);
	    centerPane.add(assistantTeacherField,1,1);
	    GridPane.setConstraints(assistantTeacherField,1,1,2,1);
	    centerPane.add(monday,0,2);
	    GridPane.setHalignment(monday,HPos.RIGHT);
	    centerPane.add(mondayField,1,2);
	    GridPane.setConstraints(mondayField,1,2,2,1);
	    centerPane.add(tuesday,0,3);
	    GridPane.setHalignment(tuesday,HPos.RIGHT);
	    centerPane.add(tuesdayField,1,3);
	    GridPane.setConstraints(tuesdayField,1,3,2,1);
	    centerPane.add(wednesday,0,4);
	    GridPane.setHalignment(wednesday,HPos.RIGHT);
	    centerPane.add(wednesdayField,1,4);
	    GridPane.setConstraints(wednesdayField,1,4,2,1);
	    centerPane.add(thursday,0,5);
	    GridPane.setHalignment(thursday,HPos.RIGHT);
	    centerPane.add(thursdayField,1,5);
	    GridPane.setConstraints(thursdayField,1,5,2,1);
	    centerPane.add(friday,0,6);
	    GridPane.setHalignment(friday,HPos.RIGHT);
	    centerPane.add(fridayField,1,6);
	    GridPane.setConstraints(fridayField,1,6,2,1);
	    centerPane.add(capacity,0,7);
	    GridPane.setHalignment(capacity,HPos.RIGHT);
	    centerPane.add(capacityField,1,7);
	    GridPane.setConstraints(capacityField,1,7,2,1);
	    return centerPane;
	}
	
	private List<Student> extractStudents(Classroom classroom){
		List<Student> studentsEnrolled = new ArrayList<Student>();
		List<String> studentIDs = classroom.getStudentsEnrolled();
		for(String studentID : studentIDs) {
			Student student = classroom.getStudent(studentID);
			studentsEnrolled.add(student);
		}
		return studentsEnrolled;
	}
	
	private void buildDefaultList(GridPane rightSideBar,Classroom classroom) {
		List<String> studentStrings = classroom.getStudentsEnrolled();
		List<Student> studentList = new ArrayList<Student>();
		for(String studentID : studentStrings) {
			Student student = classroom.getStudent(studentID);
			studentList.add(student);
		}
		Label noStudentsEnrolled = new Label("No students enrolled");
	    noStudentsEnrolled.getStyleClass().add("label");
	    if(!studentList.isEmpty()) {
		    int columnIndex = 1;
		    	int rowIndex = 1;
		    for(Student student : studentList) {
		    		Label studentLabel = new Label(student.getFirstName() + " " + student.getLastName());
		    		labels.add(studentLabel);
		    		rightSideBar.add(studentLabel,columnIndex,rowIndex);
		    		GridPane.setHalignment(studentLabel,HPos.CENTER);
		    		rowIndex++;
		    }
	    }else {
	    		labels.add(noStudentsEnrolled);
	    		rightSideBar.add(noStudentsEnrolled,1,2);
	    }
	}
	
	private void buildStudentList(GridPane rightSideBar,Label sideTitle,String title,List<Student> newList){
		sideTitle.setText(title);
		sideTitle.getStyleClass().add("side-title");
		for(Label label : labels) {label.setText("");}
		Label noStudentsEnrolled = new Label("No students enrolled");
	    noStudentsEnrolled.getStyleClass().add("label");
	    if(!newList.isEmpty()) {
		    int columnIndex = 1;
		    	int rowIndex = 1;
		    for(Student student : newList) {
		    		Label studentLabel = new Label(student.getFirstName() + " " + student.getLastName());
		    		labels.add(studentLabel);
		    		rightSideBar.add(studentLabel,columnIndex,rowIndex);
		    		GridPane.setHalignment(studentLabel,HPos.CENTER);
		    		rowIndex++;
		    }
	    }else {
	    		labels.add(noStudentsEnrolled);
	    		rightSideBar.add(noStudentsEnrolled,1,2);
	    }
	}
	
	private void sortAlphabeticalByFirst(List<Student> unsortedList,GridPane rightSideBar,Label sideTitle,String title){
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
		buildStudentList(rightSideBar,sideTitle,title,sortedStudentList);
		myStudentList.clear();
	}
	
	private void sortAlphabeticalByLast(List<Student> unsortedList,GridPane rightSideBar,Label sideTitle,String title) {
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
		buildStudentList(rightSideBar,sideTitle,title,sortedStudentList); 
		myStudentList.clear();
	}
	
	private void sortReverseAlphabeticalByFirst(List<Student> unsortedList,GridPane rightSideBar,Label sideTitle,String title){
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
		buildStudentList(rightSideBar,sideTitle,title,sortedStudentList);
		myStudentList.clear();
	}
	
	private void sortReverseAlphabeticalByLast(List<Student> unsortedList,GridPane rightSideBar,Label sideTitle,String title) {
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
		buildStudentList(rightSideBar,sideTitle,title,sortedStudentList); 
		myStudentList.clear();
	}
	
	public List<String> sort(List<String> unsortedList,boolean isAtoZ) {
		this.myStudentList = unsortedList;
		if(myStudentList.size() <= 1) {
			return myStudentList;
		}
		int start = 0;
		int end = myStudentList.size()-1;
		quickSort(myStudentList,start,end,isAtoZ);
		return myStudentList;
	}
	
	public void quickSort(List<String> myStudentList,int start,int end,boolean isAtoZ){
		if(start < end) {
			int pIndex = partition(myStudentList,start,end,isAtoZ);
			quickSort(myStudentList,start,pIndex-1,isAtoZ);
			quickSort(myStudentList,pIndex+1,end,isAtoZ);
		}
	}
	
	public int partition(List<String> myStudentList,int start,int end,boolean isAtoZ) {
		int pivot = end;
		String pivotValue = myStudentList.get(pivot);
		int startIndex = start;
		int switchIndex = start;
		int wall = start;
		while(startIndex < end) {
			if(isAtoZ == true) {
				while(myStudentList.get(startIndex).compareTo(myStudentList.get(pivot)) > 0) {
					startIndex++;
				}
			}else {
				while(myStudentList.get(startIndex).compareTo(myStudentList.get(pivot)) < 0) {
					startIndex++;
				}	
			}
			if(startIndex < end) {
				String valueCheck = myStudentList.get(startIndex);	
				String switchValue = myStudentList.get(switchIndex);
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
	
	public void populateStudentList(Classroom classroom) {
		List<String> students = classroom.getStudentsEnrolled();
		for(String studentID : students) {
			Student student = classroom.getStudent(studentID);
			currentlyDisplayedList.add(student);
		}
	}
	
}
