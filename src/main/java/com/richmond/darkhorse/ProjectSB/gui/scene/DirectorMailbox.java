package com.richmond.darkhorse.ProjectSB.gui.scene;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToggleButton;
import com.richmond.darkhorse.ProjectSB.AccountManager;
import com.richmond.darkhorse.ProjectSB.Center;
import com.richmond.darkhorse.ProjectSB.Director;
import com.richmond.darkhorse.ProjectSB.Document;
import com.richmond.darkhorse.ProjectSB.SpecialBeginnings;
import com.richmond.darkhorse.ProjectSB.StaffMember;
import com.richmond.darkhorse.ProjectSB.Teacher;
import com.richmond.darkhorse.ProjectSB.BehaviorReport;
import com.richmond.darkhorse.ProjectSB.IncidentReport;
import com.richmond.darkhorse.ProjectSB.gui.component.ImageButton;
import com.richmond.darkhorse.ProjectSB.gui.component.UnsignedBehaviorReportPane;
import com.richmond.darkhorse.ProjectSB.gui.component.UnsignedIncidentReportPane;
import com.richmond.darkhorse.ProjectSB.middleman.ChangeScene;
import javafx.application.Platform;
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
import javafx.geometry.HPos;
import javafx.geometry.VPos;

public class DirectorMailbox extends Scene {
	
	private boolean isBehaviorReportPane = true;
	private GridPane innerPane;
	private UnsignedBehaviorReportPane unsignedBehaviorReportPane;
	private UnsignedIncidentReportPane unsignedIncidentReportPane;
	private BorderPane directorMailboxLayout;
	
	public DirectorMailbox(Stage stage,Scene nextScene,Director director) {
		this(stage,new BorderPane(),nextScene,director);
	}
	
	public DirectorMailbox(Stage stage,BorderPane layout,Scene nextScene,Director director) {
		super(layout);
		
		HBox topBar = buildTopBar(stage,director);
		HBox bottomBar = buildBottomBar();
		VBox leftSideBar = buildLeftSideBar(stage,director);
		VBox rightSideBar = buildRightSideBar(director);
		GridPane centerPane = buildCenterPane(director,stage);
		
		directorMailboxLayout = layout;
		directorMailboxLayout.setTop(topBar);
		directorMailboxLayout.setBottom(bottomBar);
		directorMailboxLayout.setLeft(leftSideBar);
		directorMailboxLayout.setRight(rightSideBar);
		directorMailboxLayout.setCenter(centerPane);
		directorMailboxLayout.getStylesheets().add("directormailbox.css");
		
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
	
	public VBox buildLeftSideBar(Stage stage,Director director) {
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
	
	public VBox buildRightSideBar(Director director) {
		GridPane rightSideBar = new GridPane();
		rightSideBar.getStyleClass().add("sidebar");
		rightSideBar.getStylesheets().add("directormailbox.css");
		
		Label title = new Label("Signed Reports");
		title.getStyleClass().add("subtitle");
		ImageView sendView = new ImageView();
		Image send = new Image("send.png");
		sendView.setImage(send);
		sendView.setPreserveRatio(true);
		sendView.setFitHeight(50);
		ImageButton sendButton = new ImageButton(sendView);
		sendButton.getStyleClass().add("button");
		sendButton.setOnAction(e -> {
			director.returnAllReports();
		});
		
		int row = 1;
		Map<String,Document> signedReports = director.getMailbox().getAllSignedReports();
		for(Document document : signedReports.values()) {
			String documentTitle = document.getTitle();
			String student = document.getStudentName();
			String date = document.getStringDate();
			ToggleButton newButton = new ToggleButton();
			newButton.setText("" + documentTitle + "\nStudent: " + student + "\nDate: " + date);
			newButton.setPrefSize(250,150);
			newButton.getStyleClass().add("toggle-button");
			rightSideBar.add(newButton,0,row);
			GridPane.setConstraints(newButton,0,row,2,1);
			GridPane.setHalignment(newButton,HPos.CENTER);
			row++;
		}
		
		rightSideBar.add(title,0,0);
		GridPane.setHalignment(title,HPos.CENTER);
		rightSideBar.add(sendButton,1,0);
		
		ScrollPane scrollPane = new ScrollPane(rightSideBar);
		scrollPane.setFitToWidth(true);
		VBox wrapper = new VBox();
		wrapper.getStyleClass().add("wrapper");
		wrapper.getChildren().add(scrollPane);
		return wrapper;
	}
	
	public GridPane buildCenterPane(Director director,Stage stage) {
		
		GridPane gridPane = new GridPane();
		gridPane.setVgap(10);
		gridPane.setHgap(10);
		GridPane.setHalignment(gridPane,HPos.CENTER);
		GridPane.setValignment(gridPane,VPos.CENTER);
		ColumnConstraints columnOne = new ColumnConstraints();
		columnOne.setPercentWidth(16.6);
	    ColumnConstraints columnTwo = new ColumnConstraints();
	    columnTwo.setPercentWidth(16.6);
		ColumnConstraints columnThree = new ColumnConstraints();
		columnThree.setPercentWidth(16.6);
		ColumnConstraints columnFour = new ColumnConstraints();
		columnFour.setPercentWidth(16.6);
	    ColumnConstraints columnFive = new ColumnConstraints();
	    columnFive.setPercentWidth(16.6);
		ColumnConstraints columnSix = new ColumnConstraints();
		columnSix.setPercentWidth(16.6);
		gridPane.getColumnConstraints().addAll(columnOne,columnTwo,columnThree,columnFour,columnFive,columnSix);
		gridPane.getStyleClass().add("gridpane");
		gridPane.getStylesheets().add("directormailbox.css");
		
		kirbyAllBehaviorReports(director);
		unsignedBehaviorReportPane = new UnsignedBehaviorReportPane(director,director.getMailbox().getUnsignedBehaviorReports());
		innerPane = unsignedBehaviorReportPane;
		ScrollPane scrollPane = new ScrollPane(innerPane);
		scrollPane.setPrefHeight(500);
		scrollPane.setPrefWidth(500);
		
		Label title = new Label("Unsigned Reports");
		title.getStyleClass().add("title");
		ImageView refreshView = new ImageView();
		Image refresh = new Image("refresh.png");
		refreshView.setImage(refresh);
		refreshView.setPreserveRatio(true);
		refreshView.setFitHeight(75);
		ImageButton refreshButton = new ImageButton(refreshView);
		refreshButton.getStyleClass().add("button");
		
		ToggleButton behaviorReportButton = new ToggleButton("behavior report(s)");
		behaviorReportButton.setSelected(true);
		behaviorReportButton.setMaxHeight(50);
		behaviorReportButton.setMaxWidth(300);
		behaviorReportButton.getStyleClass().add("toggle-button");
		
		ToggleButton incidentReportButton = new ToggleButton("incident report(s)");
		incidentReportButton.setMaxHeight(50);
		incidentReportButton.setMaxWidth(300);
		incidentReportButton.getStyleClass().add("toggle-button");
		
		Label behaviorView = new Label("view");
		behaviorView.getStyleClass().add("label");
		Button behaviorViewButton = new Button("",behaviorView);
		behaviorViewButton.setMaxHeight(40);
		behaviorViewButton.setMaxWidth(150);
		behaviorViewButton.getStyleClass().add("button");
		
		Label incidentView = new Label("view");
		incidentView.getStyleClass().add("label");
		Button incidentViewButton = new Button("",incidentView);
		incidentViewButton.setMaxHeight(40);
		incidentViewButton.setMaxWidth(150);
		incidentViewButton.setVisible(false);
		incidentViewButton.getStyleClass().add("button");
		
		Label behaviorSign = new Label("sign");
		behaviorSign.getStyleClass().add("label");
		Button behaviorSignButton = new Button("",behaviorSign);
		behaviorSignButton.setMaxHeight(40);
		behaviorSignButton.setMaxWidth(150);
		behaviorSignButton.getStyleClass().add("button");
		
		Label incidentSign = new Label("sign");
		incidentSign.getStyleClass().add("label");
		Button incidentSignButton = new Button("",incidentSign);
		incidentSignButton.setMaxHeight(40);
		incidentSignButton.setMaxWidth(150);
		incidentSignButton.setVisible(false);
		incidentSignButton.getStyleClass().add("button");
		
		behaviorReportButton.setOnAction(e -> {
			incidentReportButton.setSelected(false);
			behaviorReportButton.setSelected(true);
			unsignedBehaviorReportPane = (UnsignedBehaviorReportPane) buildBehaviorReport(director);
			scrollPane.setContent(unsignedBehaviorReportPane);
			behaviorViewButton.setVisible(true);
			incidentViewButton.setVisible(false);
			behaviorSignButton.setVisible(true);
			incidentSignButton.setVisible(false);
			isBehaviorReportPane = true;
		});
		
		incidentReportButton.setOnAction(e -> {
			incidentReportButton.setSelected(true);
			behaviorReportButton.setSelected(false);
			unsignedIncidentReportPane = (UnsignedIncidentReportPane) buildIncidentReport(director);
			scrollPane.setContent(unsignedIncidentReportPane);
			behaviorViewButton.setVisible(false);
			incidentViewButton.setVisible(true);
			behaviorSignButton.setVisible(false);
			incidentSignButton.setVisible(true);
			isBehaviorReportPane = false;
		});
		
		behaviorViewButton.setOnAction(e -> {
			List<BehaviorReport> selectedReports = new ArrayList<BehaviorReport>();
			if(isBehaviorReportPane == true) {
				Map<String,BehaviorReport> selectedBRs = unsignedBehaviorReportPane.getSelectedBehaviorReports();
				for(BehaviorReport behaviorReport : selectedBRs.values()) {selectedReports.add(behaviorReport);}
				Platform.runLater(new ChangeScene(stage,new ModifyBehaviorReports(stage,null,director,selectedReports)));
			}
		});
		
		behaviorSignButton.setOnAction(e -> {
			if(isBehaviorReportPane == true) {
				Map<String,BehaviorReport> selectedBRs = unsignedBehaviorReportPane.getSelectedBehaviorReports();
				for(BehaviorReport behaviorReport : selectedBRs.values()) {
					behaviorReport.setDirectorSignature(true);
					director.getMailbox().addSignedBehaviorReport(behaviorReport);
				}
				VBox rightSideBar = buildRightSideBar(director);
				directorMailboxLayout.setRight(rightSideBar);
				unsignedBehaviorReportPane = (UnsignedBehaviorReportPane) buildBehaviorReport(director);
				scrollPane.setContent(unsignedBehaviorReportPane);
			}
		});
		
		incidentViewButton.setOnAction(e -> {
			List<IncidentReport> selectedReports = new ArrayList<IncidentReport>();
			if(isBehaviorReportPane == false) {
				Map<String,IncidentReport> selectedIRs = unsignedIncidentReportPane.getSelectedIncidentReports();
				for(IncidentReport incidentReport : selectedIRs.values()) {selectedReports.add(incidentReport);}
				Platform.runLater(new ChangeScene(stage,new ModifyIncidentReports(stage,null,director,selectedReports)));
			}
		});
		
		incidentSignButton.setOnAction(e -> {
			if(isBehaviorReportPane == false) {
				Map<String,IncidentReport> selectedIRs = (Map<String, IncidentReport>) unsignedIncidentReportPane.getSelectedIncidentReports();
				for(IncidentReport incidentReport : selectedIRs.values()) {
					incidentReport.setDirectorSignature(true);
					director.getMailbox().addSignedIncidentReport(incidentReport);
				}
				VBox rightSideBar = buildRightSideBar(director);
				directorMailboxLayout.setRight(rightSideBar);
				unsignedIncidentReportPane = (UnsignedIncidentReportPane) buildIncidentReport(director);
				scrollPane.setContent(unsignedIncidentReportPane);
			}
		});
		
		refreshButton.setOnAction(e -> {
			if(isBehaviorReportPane == true) {
				kirbyAllBehaviorReports(director);
				UnsignedBehaviorReportPane newBRPane = (UnsignedBehaviorReportPane) buildBehaviorReport(director);
				unsignedBehaviorReportPane = newBRPane;
				scrollPane.setContent(unsignedBehaviorReportPane);
			}else {
				kirbyAllIncidentReports(director);
				UnsignedIncidentReportPane newIRPane = (UnsignedIncidentReportPane) buildIncidentReport(director);
				unsignedIncidentReportPane = newIRPane;
				scrollPane.setContent(unsignedIncidentReportPane);
			}
		});
		
		gridPane.add(title,0,0);
		GridPane.setHalignment(title,HPos.RIGHT);
		GridPane.setConstraints(title,0,0,4,1);
		gridPane.add(refreshButton,4,0);
		GridPane.setHalignment(refreshButton,HPos.LEFT);
		gridPane.add(behaviorReportButton,0,1);
		GridPane.setConstraints(behaviorReportButton,0,1,2,1);
		GridPane.setHalignment(behaviorReportButton,HPos.LEFT);
		gridPane.add(incidentReportButton,2,1);
		GridPane.setConstraints(incidentReportButton,2,1,2,1);
		GridPane.setHalignment(incidentReportButton,HPos.LEFT);
		gridPane.add(scrollPane,0,3);
		GridPane.setConstraints(scrollPane,0,3,6,6);
		gridPane.add(behaviorViewButton,4,9);
		GridPane.setHalignment(behaviorViewButton,HPos.CENTER);
		gridPane.add(incidentViewButton,4,9);
		GridPane.setHalignment(incidentViewButton,HPos.CENTER);
		gridPane.add(behaviorSignButton,5,9);
		GridPane.setHalignment(behaviorSignButton,HPos.CENTER);
		gridPane.add(incidentSignButton,5,9);
		GridPane.setHalignment(incidentSignButton,HPos.CENTER);
		
		return gridPane;
	}
	
	public GridPane buildBehaviorReport(Director director) {
		kirbyAllBehaviorReports(director);
		UnsignedBehaviorReportPane unsignedBRPane = new UnsignedBehaviorReportPane(director,director.getMailbox().getUnsignedBehaviorReports());
		unsignedBehaviorReportPane = unsignedBRPane;
		return unsignedBehaviorReportPane;
	}
	
	public GridPane buildIncidentReport(Director director) {
		kirbyAllIncidentReports(director);
		UnsignedIncidentReportPane unsignedIRPane = new UnsignedIncidentReportPane(director,director.getMailbox().getUnsignedIncidentReports());
		unsignedIncidentReportPane = unsignedIRPane;
		return unsignedIncidentReportPane;
	}
	
	public List<Teacher> getTeachers(Director director){
		Map<String,StaffMember> staffMembers = director.getStaffMembers();
		List<Teacher> teachers = new ArrayList<Teacher>();
		for(StaffMember staffMember : staffMembers.values()) {
			if(staffMember.getCenterID() == director.getCenterID() && staffMember.getTitle().equals("Teacher")) {
				teachers.add((Teacher)staffMember);
			}
		}
		return teachers;
	}
	
	public void kirbyAllBehaviorReports(Director director) {
		List<Teacher> teachers = getTeachers(director);
		for(Teacher teacher : teachers) {
			Map<String,BehaviorReport> unsignedBehaviorReports = teacher.getMailbox().getUnsignedBehaviorReports();
			if(unsignedBehaviorReports.size() > 0) {
				for(BehaviorReport behaviorReport : unsignedBehaviorReports.values()) {
					director.getMailbox().addUnsignedBehaviorReport(behaviorReport);
				}
			}
		}
	}
	
	public void kirbyAllIncidentReports(Director director){
		List<Teacher> teachers = getTeachers(director);
		for(Teacher teacher : teachers) {
			Map<String,IncidentReport> unsignedIncidentReports = teacher.getMailbox().getUnsignedIncidentReports();
			if(unsignedIncidentReports.size() > 0) {
				for(IncidentReport incidentReport : unsignedIncidentReports.values()) {
					director.getMailbox().addUnsignedIncidentReport(incidentReport);
				}
			}
		}
	}
	
}

