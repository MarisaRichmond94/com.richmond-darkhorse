package com.richmond.darkhorse.ProjectSB.gui.scene;
import java.util.Map;
import javafx.scene.control.ScrollPane;
import com.richmond.darkhorse.ProjectSB.AccountManager;
import com.richmond.darkhorse.ProjectSB.Center;
import com.richmond.darkhorse.ProjectSB.Classroom;
import com.richmond.darkhorse.ProjectSB.Director;
import com.richmond.darkhorse.ProjectSB.SpecialBeginnings;
import com.richmond.darkhorse.ProjectSB.Student;
import com.richmond.darkhorse.ProjectSB.gui.component.AddStudent;
import com.richmond.darkhorse.ProjectSB.gui.component.ImageButton;
import com.richmond.darkhorse.ProjectSB.middleman.ChangeScene;
import javafx.application.Platform;
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

public class DirectorStudentWorkspace extends Scene {
	
	private AddStudent addStudent;
	private double rowIndex = 1.0;
	private int column = 0;
	
	public DirectorStudentWorkspace(Stage stage,Scene nextScene,Director director) {
		this(stage,new BorderPane(),nextScene,director);
		this.addStudent = new AddStudent(director);
	}
	
	public DirectorStudentWorkspace(Stage stage,BorderPane layout,Scene nextScene,Director director) {
		super(layout);
		
		HBox topBar = buildTopBar(stage,director);
		HBox bottomBar = buildBottomBar();
		VBox leftSideBar = buildSideBar(stage,director);
		ScrollPane scrollPane = buildCenterPane(stage,director);
		
		BorderPane directorStudentWorkspaceLayout = layout;
		directorStudentWorkspaceLayout.setTop(topBar);
		directorStudentWorkspaceLayout.setBottom(bottomBar);
		directorStudentWorkspaceLayout.setLeft(leftSideBar);
		directorStudentWorkspaceLayout.setCenter(scrollPane);
		directorStudentWorkspaceLayout.getStylesheets().add("studentworkspace.css");
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
		
		ImageView workspaceViewer = new ImageView();
		Image studentsWorkspace = new Image("studentsworkspace.png");
		workspaceViewer.setImage(studentsWorkspace);
		workspaceViewer.setPreserveRatio(true);
		workspaceViewer.setFitHeight(150);
		
		//Add Student Button
		ImageView iconViewer = new ImageView();
		Image studentIcon = new Image("addstudent.png");
		iconViewer.setImage(studentIcon);
		iconViewer.setPreserveRatio(true);
		iconViewer.setFitHeight(200);
		Button addStudentButton = new Button("",iconViewer);
		addStudentButton.setOnAction(e -> {
			addStudent.display();
			Platform.runLater(new ChangeScene(stage,new DirectorStudentWorkspace(stage,null,director)));
		});
		addStudentButton.getStyleClass().add("button");
		addStudentButton.setPrefHeight(300);
		addStudentButton.setPrefWidth(500);
		
		Map<String,Student> students = director.getStudents();
		for(Student student : students.values()) {
			String firstName = student.getFirstName();
			String lastName = student.getLastName();
			String birthDate = student.getBirthDate();
			Classroom classroom = null;
			String classroomType = "N/A";
			if(student.getClassroom(student.getClassroomID()) != null) {
				classroom = student.getClassroom(student.getClassroomID());
				classroomType = classroom.getClassroomType();
			}
			Button newButton = new Button();
			newButton.setText(firstName + " " + lastName + "\n" + "Birth Date: " + birthDate + "\n" + "Classroom: " + classroomType);
			newButton.setOnAction(e -> {
				Platform.runLater(new ChangeScene(stage,new ModifyStudentInfo(stage,null,student,director)));
			});
			newButton.getStyleClass().add("button");
			newButton.setPrefHeight(300);
			newButton.setPrefWidth(500);
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
		centerPane.add(addStudentButton,0,1);
		GridPane.setConstraints(addStudentButton,0,1,2,1);
		GridPane.setHalignment(workspaceViewer,HPos.CENTER);
		GridPane.setHalignment(addStudentButton,HPos.CENTER);
		ScrollPane scrollPane = new ScrollPane(centerPane);
		scrollPane.setFitToWidth(true);
		return scrollPane;
	}
	
	public void determinePosition(GridPane gridpane,Button button,int row,int column) {
		int columnIndex;
		if(column == 0) {
			gridpane.add(button, 2, 1);
			GridPane.setConstraints(button, 2, 1, 2, 1);
			GridPane.setHalignment(button, HPos.CENTER);
		}else {
			boolean isIndexEven = isIntEven(column);
			if(isIndexEven == true) {columnIndex = 2;
			}else {columnIndex = 0;}
			gridpane.add(button, columnIndex, row);
			GridPane.setConstraints(button, columnIndex, row, 2, 1);
			GridPane.setHalignment(button, HPos.CENTER);
			
		}
	}
	
	public boolean isIntEven(int n) {
		if( n % 2 == 0) {return true;}
		return false;
	}
	
	public boolean doesNumberEndInZero(double rowIndex) {
		if (Math.abs(rowIndex - Math.rint(rowIndex)) == 0.5) {return false;}
		return true;
	}
	
}

