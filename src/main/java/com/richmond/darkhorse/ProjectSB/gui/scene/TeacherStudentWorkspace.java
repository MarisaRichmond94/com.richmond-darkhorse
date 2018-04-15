package com.richmond.darkhorse.ProjectSB.gui.scene;
import java.util.Map;
import com.richmond.darkhorse.ProjectSB.AccountManager;
import com.richmond.darkhorse.ProjectSB.Center;
import com.richmond.darkhorse.ProjectSB.SpecialBeginnings;
import com.richmond.darkhorse.ProjectSB.Teacher;
import com.richmond.darkhorse.ProjectSB.gui.component.ImageButton;
import com.richmond.darkhorse.ProjectSB.middleman.ChangeScene;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class TeacherStudentWorkspace extends Scene{

	public TeacherStudentWorkspace(Stage stage,Scene nextScene,Teacher teacher) {
		this(stage,new BorderPane(),nextScene,teacher);
	}
	
	public TeacherStudentWorkspace(Stage stage,BorderPane layout,Scene nextScene,Teacher teacher) {
		
		super(layout);
		HBox topBar = buildTopBar(stage,teacher);
		HBox bottomBar = buildBottomBar();
		VBox leftSideBar = buildSideBar(stage,teacher);
		GridPane centerPane = buildCenterPane(teacher);
		
		BorderPane teacherStudentWorkspaceLayout = layout;
		teacherStudentWorkspaceLayout.setTop(topBar);
		teacherStudentWorkspaceLayout.setBottom(bottomBar);
		teacherStudentWorkspaceLayout.setLeft(leftSideBar);
		teacherStudentWorkspaceLayout.setCenter(centerPane);
		teacherStudentWorkspaceLayout.getStylesheets().add("teacherhome.css");
		
	}
	
public HBox buildTopBar(Stage stage,Teacher teacher) {
		
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
		homeButton.setOnAction(e -> Platform.runLater(new ChangeScene(stage,new TeacherHome(stage,null,teacher))));
				
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
	
	public VBox buildSideBar(Stage stage,Teacher teacher) {
		VBox leftSideBar = new VBox();
		
		ImageView dashboardViewer = new ImageView();
		Image dashboard = new Image("dashboard.png");
		dashboardViewer.setImage(dashboard);
		dashboardViewer.setPreserveRatio(true);
		dashboardViewer.setFitHeight(100);
		ImageButton dashboardButton = new ImageButton(dashboardViewer);
		dashboardButton.setOnAction(e -> Platform.runLater(new ChangeScene(stage,new TeacherDashboard(stage,null,teacher))));
		Label dashboardLabel = new Label("Dashboard");
		dashboardLabel.getStyleClass().add("label");
		
		ImageView studentViewer = new ImageView();
		Image students = new Image("students.png");
		studentViewer.setImage(students);
		studentViewer.setPreserveRatio(true);
		studentViewer.setFitHeight(95);
		ImageButton studentButton = new ImageButton(studentViewer);
		studentButton.setOnAction(e -> Platform.runLater(new ChangeScene(stage,new TeacherStudentWorkspace(stage,null,teacher))));
		Label viewStudent = new Label("Students");
		viewStudent.getStyleClass().add("label");

		ImageView reportViewer = new ImageView();
		Image report = new Image("reports.png");
		reportViewer.setImage(report);
		reportViewer.setPreserveRatio(true);
		reportViewer.setFitHeight(125);
		ImageButton reportButton = new ImageButton(reportViewer);
		reportButton.setOnAction(e -> Platform.runLater(new ChangeScene(stage,new TeacherReportWorkspace(stage,null,teacher))));
		Label reportLabel = new Label("Reports");
		reportLabel.getStyleClass().add("label");
		
		ImageView scheduleViewer = new ImageView();
		Image schedule = new Image("schedule.png");
		scheduleViewer.setImage(schedule);
		scheduleViewer.setPreserveRatio(true);
		scheduleViewer.setFitHeight(100);
		ImageButton scheduleButton = new ImageButton(scheduleViewer);
		scheduleButton.setOnAction(e -> Platform.runLater(new ChangeScene(stage,new TeacherSchedule(stage,null,teacher))));
		Label viewSchedule = new Label("Schedule");
		viewSchedule.getStyleClass().add("label");
		
		leftSideBar.getChildren().addAll(dashboardButton,dashboardLabel,studentButton,viewStudent,reportButton,reportLabel,scheduleButton,viewSchedule);
		leftSideBar.getStyleClass().add("sidebar");
		return leftSideBar;
	}
	
	public GridPane buildCenterPane(Teacher teacher) {
		GridPane centerPane = new GridPane();
		return centerPane;
	}
	
}
