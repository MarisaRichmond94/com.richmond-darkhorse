package com.richmond.darkhorse.ProjectSB.gui.scene;
import java.util.Map;
import com.richmond.darkhorse.ProjectSB.AccountManager;
import com.richmond.darkhorse.ProjectSB.Center;
import com.richmond.darkhorse.ProjectSB.Director;
import com.richmond.darkhorse.ProjectSB.SpecialBeginnings;
import com.richmond.darkhorse.ProjectSB.gui.component.ImageButton;
import com.richmond.darkhorse.ProjectSB.middleman.ChangeScene;
import javafx.application.Platform;
import javafx.geometry.HPos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class DirectorHome extends Scene {
	
	public DirectorHome(Stage stage,Scene nextScene,Director director) {
		this(stage,new BorderPane(),nextScene,director);
	}
	
	public DirectorHome(Stage stage,BorderPane layout,Scene nextScene,Director director) {
		super(layout);
		
		HBox topBar = buildTopBar(stage,director);
		HBox bottomBar = buildBottomBar();
		VBox leftSideBar = buildSideBar(stage,director);
		GridPane centerPane = buildCenterPane(director);
		
		BorderPane directorHomeLayout = layout;
		directorHomeLayout.setTop(topBar);
		directorHomeLayout.setBottom(bottomBar);
		directorHomeLayout.setLeft(leftSideBar);
		directorHomeLayout.setCenter(centerPane);
		directorHomeLayout.getStylesheets().add("directorhome.css");
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
	
	public GridPane buildCenterPane(Director director) {
		GridPane centerPane = new GridPane();
		centerPane.setVgap(10);
		centerPane.setHgap(10);
		ColumnConstraints columnOne = new ColumnConstraints();
		columnOne.setPercentWidth(100);
		centerPane.getColumnConstraints().addAll(columnOne);
		centerPane.getStyleClass().add("gridpane");
				
		ImageView welcomeViewer = new ImageView();
		Image welcomeBack = new Image("welcome.png");
		welcomeViewer.setImage(welcomeBack);
		welcomeViewer.setPreserveRatio(true);
		welcomeViewer.setFitHeight(125);
				
		ImageView iconViewer = new ImageView();
		Image directorIcon = new Image("director.png");
		iconViewer.setImage(directorIcon);
		iconViewer.setPreserveRatio(true);
		iconViewer.setFitHeight(300);
		
		Label nameLabel = new Label(director.getFirstName() + " " + director.getLastName());
		nameLabel.getStyleClass().add("title");
				
		centerPane.add(welcomeViewer, 0, 0);
		GridPane.setHalignment(welcomeViewer, HPos.CENTER);
		centerPane.add(iconViewer, 0, 1);
		GridPane.setHalignment(iconViewer, HPos.CENTER);
		centerPane.add(nameLabel, 0, 2);
		GridPane.setHalignment(nameLabel, HPos.CENTER);
		return centerPane;
	}
	
}