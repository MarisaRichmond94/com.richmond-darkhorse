package com.richmond.darkhorse.ProjectSB.gui.scene;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.richmond.darkhorse.ProjectSB.AccountManager;
import com.richmond.darkhorse.ProjectSB.Admin;
import com.richmond.darkhorse.ProjectSB.Center;
import com.richmond.darkhorse.ProjectSB.Classroom;
import com.richmond.darkhorse.ProjectSB.SpecialBeginnings;
import com.richmond.darkhorse.ProjectSB.StaffMember;
import com.richmond.darkhorse.ProjectSB.Teacher;
import com.richmond.darkhorse.ProjectSB.gui.component.AddTeacher;
import com.richmond.darkhorse.ProjectSB.gui.component.AdminLayout;
import com.richmond.darkhorse.ProjectSB.gui.component.ImageButton;
import com.richmond.darkhorse.ProjectSB.gui.component.ModifyTeacher;
import com.richmond.darkhorse.ProjectSB.middleman.ChangeScene;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.application.Platform;
import javafx.geometry.HPos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.ColumnConstraints;

public class TeacherWorkspaceScene extends Scene implements AdminLayout{
	
	private AddTeacher newAddTeacher;
	private double rowIndex = 1.0;
	private int column = 0;
	
	public TeacherWorkspaceScene(Stage stage,Scene nextScene,Admin admin) {
		this(stage,new BorderPane(),nextScene,admin);
		this.newAddTeacher = new AddTeacher(admin);
	}
	
	public TeacherWorkspaceScene(Stage stage,BorderPane layout,Scene nextScene,Admin admin) {
		super(layout);
		
		//Top Bar - Logo
		HBox topBar = new HBox();
		ImageView logoViewer = new ImageView();
		Image logo = new Image("images/logo.png");
		logoViewer.setImage(logo);
		logoViewer.setPreserveRatio(true);
		logoViewer.setFitHeight(250);
		
		//Top Bar - Home Button
		ImageView homeButtonViewer = new ImageView();
		Image hB = new Image("images/home.png");
		homeButtonViewer.setImage(hB);
		homeButtonViewer.setPreserveRatio(true);
		homeButtonViewer.setFitHeight(100);
		ImageButton homeButton = new ImageButton(homeButtonViewer);
		homeButton.setOnAction(e -> Platform.runLater(new ChangeScene(stage,new AdminHome(stage,null,admin))));
		
		//Top Bar - Logout Button
		ImageView logoutViewer = new ImageView();
		Image lB = new Image("images/logout.png");
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
		
		//Bottom Bar 
		HBox bottomBar = new HBox();
		Label signature = new Label("Created by Marisa Richmond");
		signature.getStyleClass().add("text");
		bottomBar.getChildren().add(signature);
		bottomBar.getStyleClass().add("bottombar");
		
		//Side Bar - Center Workspace
		VBox leftSideBar = new VBox();
		ImageView centerViewer = new ImageView();
		Image center = new Image("images/center.png");
		centerViewer.setImage(center);
		centerViewer.setPreserveRatio(true);
		centerViewer.setFitHeight(100);
		ImageButton centerButton = new ImageButton(centerViewer);
		centerButton.setOnAction(e -> Platform.runLater(new ChangeScene(stage,new CenterWorkspaceScene(stage,null,admin))));
		Label addCenter = new Label("Centers");
		addCenter.getStyleClass().add("label");
		
		//Side Bar - Director Workspace
		ImageView directorViewer = new ImageView();
		Image director = new Image("images/director.png");
		directorViewer.setImage(director);
		directorViewer.setPreserveRatio(true);
		directorViewer.setFitHeight(100);
		ImageButton directorButton = new ImageButton(directorViewer);
		directorButton.setOnAction(e -> Platform.runLater(new ChangeScene(stage,new DirectorWorkspaceScene(stage,null,admin))));
		Label addDirector = new Label("Directors");
		addDirector.getStyleClass().add("label");
		
		//Side Bar - Teacher Workspace
		ImageView teacherViewer = new ImageView();
		Image teacher = new Image("images/teacher.png");
		teacherViewer.setImage(teacher);
		teacherViewer.setPreserveRatio(true);
		teacherViewer.setFitHeight(100);
		ImageButton teacherButton = new ImageButton(teacherViewer);
		teacherButton.setOnAction(e -> Platform.runLater(new ChangeScene(stage,new TeacherWorkspaceScene(stage,null,admin))));
		Label addTeacher = new Label("Teachers");
		addTeacher.getStyleClass().add("label");
		
		//Side Bar - Classroom Workspace
		ImageView classroomViewer = new ImageView();
		Image classroom = new Image("images/classroom.png");
		classroomViewer.setImage(classroom);
		classroomViewer.setPreserveRatio(true);
		classroomViewer.setFitHeight(100);
		ImageButton classroomButton = new ImageButton(classroomViewer);
		classroomButton.setOnAction(e -> Platform.runLater(new ChangeScene(stage,new ClassroomWorkspaceScene(stage,null,admin))));
		Label addClassroom = new Label("Classrooms");
		addClassroom.getStyleClass().add("label");
		
		leftSideBar.getChildren().addAll(centerButton,addCenter,directorButton,addDirector,teacherButton,addTeacher,classroomButton,addClassroom);
		leftSideBar.getStyleClass().add("sidebar");
		
		//Center Pane
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
		Image workspace = new Image("images/workspace.png");
		workspaceViewer.setImage(workspace);
		workspaceViewer.setPreserveRatio(true);
		workspaceViewer.setFitHeight(150);
		
		//Center Pane - Create Teacher Button
		ImageView iconViewer = new ImageView();
		Image teacherIcon = new Image("images/addteacher.png");
		iconViewer.setImage(teacherIcon);
		iconViewer.setPreserveRatio(true);
		iconViewer.setFitHeight(200);
		Button addTeacherButton = new Button("",iconViewer);
		addTeacherButton.getStyleClass().add("button");
		addTeacherButton.setPrefHeight(300);
		addTeacherButton.setPrefWidth(400);
		addTeacherButton.setOnAction(e -> {
			newAddTeacher.display();
			Platform.runLater(new ChangeScene(stage,new TeacherWorkspaceScene(stage,null,admin)));
		});
		
		Map<String,StaffMember> staffMembers = admin.getStaffMembers();
		List<Teacher> teachers = new ArrayList<Teacher>();
		for(StaffMember staffMember: staffMembers.values()) {
			String title = staffMember.getTitle();
			if(title.equals("Teacher")) {teachers.add((Teacher) staffMember);}
		}
		for(Teacher theTeacher : teachers) {
			String firstName = theTeacher.getFirstName();
			String lastName = theTeacher.getLastName();
			Center theCenter = theTeacher.getCenter(theTeacher.getCenterID());
			String centerString = theCenter.getAbbreviatedName();
			Classroom theClassroom = null;
			if(theTeacher.getClassroomID() != null) {theClassroom = theTeacher.getClassroom(theTeacher.getClassroomID());}
			Button newButton = new Button();
			newButton.setText(firstName + " " + lastName + "\n" + "Center: " + centerString + "\n" + "Classroom: " + theClassroom);
			newButton.getStyleClass().add("button");
			newButton.setPrefHeight(300);
			newButton.setPrefWidth(400);
			newButton.setOnAction(e -> {
				ModifyTeacher modifyTeacher = new ModifyTeacher(admin,(Teacher)theTeacher);
				modifyTeacher.display();
				Platform.runLater(new ChangeScene(stage,new TeacherWorkspaceScene(stage,null,admin)));
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
		centerPane.add(addTeacherButton,0,1);
		GridPane.setConstraints(addTeacherButton,0,1,2,1);
		GridPane.setHalignment(workspaceViewer,HPos.CENTER);
		GridPane.setHalignment(addTeacherButton,HPos.CENTER);
		
		//Overall border pane layout
		ScrollPane scrollPane = new ScrollPane(centerPane);
		scrollPane.setFitToWidth(true);
		BorderPane adminHomeLayout = layout;
		adminHomeLayout.setTop(topBar);
		adminHomeLayout.setBottom(bottomBar);
		adminHomeLayout.setLeft(leftSideBar);
		adminHomeLayout.setCenter(scrollPane);
		adminHomeLayout.getStylesheets().add("css/admin.css");
	}
	
	public void determinePosition(GridPane gridpane,Button button,int row,int column) {
		int columnIndex;
		if(column == 0) {
			gridpane.add(button, 2, 1);
			GridPane.setConstraints(button, 2, 1, 2, 1);
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
	
	public Teacher getTeacher(StaffMember staffMember) {
		String title = staffMember.getTitle();
		if(title.equals("Teacher")) {
			Teacher teacher = (Teacher)staffMember;
			return teacher;
		}
		return null;
	}
	
	public Classroom getClassroom(Teacher teacher) {
		try{
			Classroom classroom = teacher.getClassroom(teacher.getClassroomID());
			if(classroom != null) {
				return classroom;
			}
		}catch(NullPointerException e) {
		}
		return null;
	}
	
}
