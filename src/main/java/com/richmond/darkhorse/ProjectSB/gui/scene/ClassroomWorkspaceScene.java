package com.richmond.darkhorse.ProjectSB.gui.scene;
import java.util.HashMap;
import java.util.Map;
import com.richmond.darkhorse.ProjectSB.AccountManager;
import com.richmond.darkhorse.ProjectSB.Admin;
import com.richmond.darkhorse.ProjectSB.Center;
import com.richmond.darkhorse.ProjectSB.Classroom;
import com.richmond.darkhorse.ProjectSB.SpecialBeginnings;
import com.richmond.darkhorse.ProjectSB.Teacher;
import com.richmond.darkhorse.ProjectSB.gui.component.AddClassroom;
import com.richmond.darkhorse.ProjectSB.gui.component.AdminLayout;
import com.richmond.darkhorse.ProjectSB.gui.component.ImageButton;
import com.richmond.darkhorse.ProjectSB.gui.component.ModifyClassroom;
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

public class ClassroomWorkspaceScene extends Scene implements AdminLayout{
	
	private AddClassroom newAddClassroom;
	private double rowIndex = 1.0;
	private int column = 0;
	
	public ClassroomWorkspaceScene(Stage stage,Scene nextScene,Admin admin) {
		this(stage,new BorderPane(),nextScene,admin);
		this.newAddClassroom = new AddClassroom(admin);
	}
	
	public ClassroomWorkspaceScene(Stage stage,BorderPane layout,Scene nextScene,Admin admin) {
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
		
		//Center Pane - Create Classroom Button
		ImageView iconViewer = new ImageView();
		Image classroomIcon = new Image("images/addclassroom.png");
		iconViewer.setImage(classroomIcon);
		iconViewer.setPreserveRatio(true);
		iconViewer.setFitHeight(200);
		Button addClassroomButton = new Button("",iconViewer);
		addClassroomButton.getStyleClass().add("button");
		addClassroomButton.setPrefHeight(300);
		addClassroomButton.setPrefWidth(400);
		addClassroomButton.setOnAction(e -> {
			newAddClassroom.display(); 
			Platform.runLater(new ChangeScene(stage,new ClassroomWorkspaceScene(stage,null,admin)));
		});
		
		Map<String,Classroom> allClassrooms = new HashMap<String,Classroom>();
		Map<String,Center> centers = admin.getCenters();
		for(Center theCenter : centers.values()) {
			Map<String,Classroom> classrooms = theCenter.getClassrooms();
			for(Classroom theClassroom : classrooms.values()) {
				allClassrooms.put(theClassroom.getClassroomID(), theClassroom);
			}
		}	
		for(Classroom value : allClassrooms.values()) {
			String classroomType = value.getClassroomType();
			Center theCenter = value.getCenter(value.getCenterID());
			String leadTeacherName = "N/A";
			if(value.getTeacher(value.getTeacherID()) != null) {
				Teacher leadTeacher = value.getTeacher(value.getTeacherID());
				leadTeacherName = leadTeacher.toString();
			}
			String assistantTeacherName = "N/A";
			if(value.getAssistantTeacher(value.getAssistantTeacherID()) != null) {
				Teacher assistantTeacher = value.getAssistantTeacher(value.getAssistantTeacherID());
				assistantTeacherName = assistantTeacher.toString();
			}
			String maxCapacity = String.valueOf(value.getMaxSize());
			String ageGroup = value.getAgeGroup();
			Button newButton = new Button();
			newButton.setText(classroomType + "\n" + "Center: " + theCenter.getAbbreviatedName() + "\n" + "Lead Teacher: " + leadTeacherName + "\n" + "Assistant Teacher: " + assistantTeacherName + "\n" + "Class size: " + maxCapacity + "\n" + "Age Group: " + ageGroup);
			newButton.getStyleClass().add("button");
			newButton.setPrefHeight(300);
			newButton.setPrefWidth(400);
			newButton.setOnAction(e -> {
				ModifyClassroom modifyClassroom = new ModifyClassroom(value,admin);
				modifyClassroom.display();
				newButton.setText(value.getClassroomType() + "\n" + "Center: " + value.getCenter(value.getCenterID()).getAbbreviatedName() + "\n" + "Lead Teacher: " + value.getTeacher(value.getTeacherID()) + "\n" + "Assistant Teacher: " + value.getAssistantTeacher(value.getAssistantTeacherID()) + "\n" + "Class size: " + value.getMaxSize() + "\n" + "Age Group: " + value.getAgeGroup());
				Platform.runLater(new ChangeScene(stage,new ClassroomWorkspaceScene(stage,null,admin)));
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
		
		centerPane.add(workspaceViewer, 1, 0);
		GridPane.setConstraints(workspaceViewer,1,0,2,1);
		centerPane.add(addClassroomButton, 0, 1);
		GridPane.setConstraints(addClassroomButton,0,1,2,1);
		GridPane.setHalignment(workspaceViewer,HPos.CENTER);
		GridPane.setHalignment(addClassroomButton,HPos.CENTER);
		
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

}
