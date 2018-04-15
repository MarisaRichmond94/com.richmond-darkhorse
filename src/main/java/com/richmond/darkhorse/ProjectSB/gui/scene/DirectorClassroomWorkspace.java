package com.richmond.darkhorse.ProjectSB.gui.scene;
import java.util.HashMap;
import java.util.Map;
import com.richmond.darkhorse.ProjectSB.AccountManager;
import com.richmond.darkhorse.ProjectSB.Center;
import com.richmond.darkhorse.ProjectSB.Classroom;
import com.richmond.darkhorse.ProjectSB.Director;
import com.richmond.darkhorse.ProjectSB.SpecialBeginnings;
import com.richmond.darkhorse.ProjectSB.Teacher;
import com.richmond.darkhorse.ProjectSB.gui.component.ImageButton;
import com.richmond.darkhorse.ProjectSB.middleman.ChangeScene;
import javafx.application.Platform;
import javafx.geometry.HPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class DirectorClassroomWorkspace extends Scene {
	
	private double rowIndex = 0.5;
	private int column = 1;
	
	public DirectorClassroomWorkspace(Stage stage,Scene nextScene,Director director) {
		this(stage,new BorderPane(),nextScene,director);
	}
	
	public DirectorClassroomWorkspace(Stage stage,BorderPane layout,Scene nextScene,Director director) {
		super(layout);
		HBox topBar = buildTopBar(stage,director);
		HBox bottomBar = buildBottomBar();
		VBox leftSideBar = buildSideBar(stage,director);
		ScrollPane scrollPane = buildCenterPane(stage,director);
		
		BorderPane directorClassroomWorkspaceLayout = layout;
		directorClassroomWorkspaceLayout.setTop(topBar);
		directorClassroomWorkspaceLayout.setBottom(bottomBar);
		directorClassroomWorkspaceLayout.setLeft(leftSideBar);
		directorClassroomWorkspaceLayout.setCenter(scrollPane);
		directorClassroomWorkspaceLayout.getStylesheets().add("classroomworkspace.css");
	}
	
	public ScrollPane buildCenterPane(Stage stage,Director director) {
		GridPane centerPane = new GridPane();
		centerPane.setVgap(10);
		centerPane.setHgap(10);
		ColumnConstraints columnOne = new ColumnConstraints();
		columnOne.setPercentWidth(24.5);
		ColumnConstraints columnTwo = new ColumnConstraints();
	    columnTwo.setPercentWidth(24.5);
	    ColumnConstraints columnThree = new ColumnConstraints();
		columnThree.setPercentWidth(24.5);
		ColumnConstraints columnFour = new ColumnConstraints();
		columnFour.setPercentWidth(24.5);
		ColumnConstraints columnFive = new ColumnConstraints();
		columnFive.setPercentWidth(2);
		centerPane.getColumnConstraints().addAll(columnOne,columnTwo,columnThree,columnFour,columnFive);
		centerPane.getStyleClass().add("gridpane");
				
		//Center Pane - Title
		ImageView workspaceViewer = new ImageView(); 
		Image classroomWorkspace = new Image("classroomsworkspace.png");
		workspaceViewer.setImage(classroomWorkspace);
		workspaceViewer.setPreserveRatio(true);
		workspaceViewer.setFitHeight(150);
		
		Map<String,Classroom> allClassrooms = new HashMap<String,Classroom>();
		Center center = director.getCenter(director.getCenterID()); 
		Map<String,Classroom> centerClassrooms = center.getClassrooms();
		for(Classroom theClassroom : centerClassrooms.values()) {
			allClassrooms.put(theClassroom.getClassroomID(), theClassroom);
		}
		for(Classroom classroomValue : allClassrooms.values()) {
			String classroomType = classroomValue.getClassroomType();
			String teachers = "N/A";
			if(classroomValue.getTeacherID() != null && classroomValue.getAssistantTeacherID() != null) {
				Teacher leadTeacher = classroomValue.getTeacher(classroomValue.getTeacherID());
				Teacher assistantTeacher = classroomValue.getAssistantTeacher(classroomValue.getAssistantTeacherID());
				teachers = leadTeacher.getFirstName() + " " + leadTeacher.getLastName() + ", \n" + assistantTeacher.getFirstName() + " " + assistantTeacher.getLastName();
			}else if(classroomValue.getTeacher(classroomValue.getTeacherID()) != null) {
				Teacher leadTeacher = classroomValue.getTeacher(classroomValue.getTeacherID());
				teachers = leadTeacher.getFirstName() + " " + leadTeacher.getLastName(); 
			}
			String mondayRatio = classroomValue.getCount("Monday") + "/" + classroomValue.getMaxSize();
			String tuesdayRatio = classroomValue.getCount("Tuesday") + "/" + classroomValue.getMaxSize();
			String wednesdayRatio = classroomValue.getCount("Wednesday") + "/" + classroomValue.getMaxSize();
			String thursdayRatio = classroomValue.getCount("Thursday") + "/" + classroomValue.getMaxSize();
			String fridayRatio = classroomValue.getCount("Friday") + "/" + classroomValue.getMaxSize();
			Button newButton = new Button();
			newButton.setText(classroomType + "\n" + "Teachers: " + teachers + "\n" + "Monday Ratio: " + mondayRatio + "\n" + "Tuesday Ratio: " + tuesdayRatio + "\n" + "Wednesday Ratio: " + wednesdayRatio + "\n" + "Thursday Ratio: " + thursdayRatio + "\n" + "Friday Ratio: " + fridayRatio);
			newButton.getStyleClass().add("button");
			newButton.setPrefHeight(300);
			newButton.setPrefWidth(500);
			newButton.setOnAction(e -> {
				Platform.runLater(new ChangeScene(stage,new ModifyClassroomSetup(stage,null,classroomValue,director)));
			});
			int row = 0;
			boolean doesNumberEndInZero = doesNumberEndInZero(rowIndex);
			if(doesNumberEndInZero == false) {
				row = (int) Math.round(rowIndex);
			}else {
				row = (int)rowIndex;
			}
			determinePosition(centerPane,newButton,row,column);
			rowIndex = rowIndex+0.5;
			column++;
		}
		
		centerPane.add(workspaceViewer,1,0);
		GridPane.setConstraints(workspaceViewer,1,0,2,1);
		GridPane.setHalignment(workspaceViewer,HPos.CENTER);
		ScrollPane scrollPane = new ScrollPane(centerPane);
		scrollPane.setFitToWidth(true);
		return scrollPane;
	}
	
	public void determinePosition(GridPane gridpane,Button button,int row,int column) {
		int columnIndex;
		if(column == 0) {
			gridpane.add(button,2,1);
			GridPane.setConstraints(button,2,1,2,1);
			GridPane.setHalignment(button, HPos.CENTER);
		}else {
			boolean isIndexEven = isIntEven(column);
			if(isIndexEven == true) {
				columnIndex = 2;
			}else {
				columnIndex = 0;
			}
			gridpane.add(button, columnIndex, row);
			GridPane.setConstraints(button, columnIndex, row, 2, 1);
			GridPane.setHalignment(button, HPos.CENTER);
			
		}
	}
	
	public boolean isIntEven(int n) {
		if( n % 2 == 0) {
			return true;
		}
		return false;
	}
	
	public boolean doesNumberEndInZero(double rowIndex) {
		if (Math.abs(rowIndex - Math.rint(rowIndex)) == 0.5) {
			return false;
		}
		return true;
		
	}
	
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
	
	public HBox buildBottomBar() {
		HBox bottomBar = new HBox();
		Label signature = new Label("Created by Marisa Richmond");
		signature.getStyleClass().add("text");
		bottomBar.getChildren().add(signature);
		bottomBar.getStyleClass().add("bottombar");
		return bottomBar;
	}
	
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
	
}

