package com.richmond.darkhorse.ProjectSB.gui.scene;
import java.util.List;
import java.util.Map;
import com.richmond.darkhorse.ProjectSB.AccountManager;
import com.richmond.darkhorse.ProjectSB.Center;
import com.richmond.darkhorse.ProjectSB.Director;
import com.richmond.darkhorse.ProjectSB.IncidentReport;
import com.richmond.darkhorse.ProjectSB.SpecialBeginnings;
import com.richmond.darkhorse.ProjectSB.gui.component.ImageButton;
import com.richmond.darkhorse.ProjectSB.gui.component.ViewSelectedIncidentReport;
import com.richmond.darkhorse.ProjectSB.middleman.ChangeScene;
import javafx.application.Platform;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
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
import javafx.stage.Stage;

public class ModifyIncidentReports extends Scene{

	private Director director;
	private List<IncidentReport> incidentReports;
	private BorderPane modifyIncidentReportLayout;
	private ViewSelectedIncidentReport viewSelectedReport;
	private int index = 0;
	private int total;
	
	public ModifyIncidentReports(Stage stage,Scene nextScene,Director director,List<IncidentReport> incidentReports) {
		this(stage,new BorderPane(),nextScene,incidentReports,director);
	}
	
	public ModifyIncidentReports(Stage stage,BorderPane layout,Scene nextScene,List<IncidentReport> incidentReports,Director director) {
		super(layout);
		this.director = director;
		this.incidentReports = incidentReports;
		this.total = incidentReports.size();
		modifyIncidentReportLayout = layout;
		
		HBox topBar = buildTopBar(stage,director);
		HBox bottomBar = buildBottomBar();
		GridPane centerPane = buildCenterPane(stage);
		modifyIncidentReportLayout.setTop(topBar);
		modifyIncidentReportLayout.setBottom(bottomBar);
		modifyIncidentReportLayout.setCenter(centerPane);
		modifyIncidentReportLayout.getStylesheets().add("directormailbox.css");
		
	}
	
	public GridPane buildCenterPane(Stage stage) {
		GridPane centerPane = new GridPane();
		centerPane.setVgap(10);
		centerPane.setHgap(10);
		GridPane.setHalignment(centerPane,HPos.CENTER);
		GridPane.setValignment(centerPane,VPos.TOP);
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
		centerPane.getColumnConstraints().addAll(columnOne,columnTwo,columnThree,columnFour,columnFive,columnSix);
		centerPane.getStyleClass().add("gridpane");
		centerPane.getStylesheets().add("directormailbox.css");
		
		Label pageCount = new Label((index+1) + "/" + total);
		pageCount.getStyleClass().add("label");
		
		viewSelectedReport = new ViewSelectedIncidentReport(director,incidentReports.get(index));
		ScrollPane scrollPane = new ScrollPane(viewSelectedReport);
		scrollPane.setPrefHeight(500);
		scrollPane.setPrefWidth(500);
		scrollPane.setFitToHeight(true);
		scrollPane.setFitToWidth(true);
		
		ImageView returnViewer = new ImageView();
		Image returnImage = new Image("back.png");
		returnViewer.setImage(returnImage);
		returnViewer.setPreserveRatio(true);
		returnViewer.setFitHeight(75);
		ImageButton returnButton = new ImageButton(returnViewer);
		returnButton.getStyleClass().add("button");
		returnButton.setOnAction(e -> {Platform.runLater(new ChangeScene(stage,new DirectorMailbox(stage,null,director)));});
		
		ImageView backViewer = new ImageView();
		Image backArrow = new Image("backarrow.png");
		backViewer.setImage(backArrow);
		backViewer.setPreserveRatio(true);
		backViewer.setFitHeight(100);
		
		ImageView nextViewer = new ImageView();
		Image nextArrow = new Image("arrow.png");
		nextViewer.setImage(nextArrow);
		nextViewer.setPreserveRatio(true);
		nextViewer.setFitHeight(100);

		ImageButton backArrowButton = new ImageButton(backViewer);
		ImageButton nextArrowButton = new ImageButton(nextViewer);
		backArrowButton.getStyleClass().add("button");
		backArrowButton.setVisible(false);
		backArrowButton.setOnAction(e -> {
			if(index > 0) {
				index = index -1;
				pageCount.setText((index+1) + "/" + total);
				viewSelectedReport = new ViewSelectedIncidentReport(director,incidentReports.get(index));
				scrollPane.setContent(viewSelectedReport);
				if(index+1 < total) {nextArrowButton.setVisible(true);
				} else {nextArrowButton.setVisible(false);}
				if(index <= 0) {backArrowButton.setVisible(false);
				} else {backArrowButton.setVisible(true);}
			}
		});
		Label previous = new Label("previous");
		previous.getStyleClass().add("label");
		
		nextArrowButton.getStyleClass().add("button");
		if((index+1) == total) {nextArrowButton.setVisible(false);}
		nextArrowButton.setOnAction(e -> {
			if((index+1) < total) {
				index = index + 1;
				pageCount.setText((index+1) + "/" + total);
				viewSelectedReport = new ViewSelectedIncidentReport(director,incidentReports.get(index));
				scrollPane.setContent(viewSelectedReport);
				if(index+1 < total) {nextArrowButton.setVisible(true);
				} else {nextArrowButton.setVisible(false);}
				if(index <= 0) {backArrowButton.setVisible(false);
				} else {backArrowButton.setVisible(true);}
			}
		});
		Label next = new Label("next");
		next.getStyleClass().add("label");
		
		Button editButton = new Button("edit");
		editButton.getStyleClass().add("button");
		Button writeButton = new Button("write");
		writeButton.getStyleClass().add("button");
		writeButton.setOnAction(e -> {
			IncidentReport incidentReport = viewSelectedReport.getIncidentReport();
			String description = null;
			if(viewSelectedReport.getDescriptionText().equals(null)) {
				description = incidentReport.getIncidentDescription(); 
			}else {description = viewSelectedReport.getDescriptionText();}
			String action = null;
			if(viewSelectedReport.getActionText().equals(null)) {
				action = incidentReport.getActionTaken(); 
			}else {action = viewSelectedReport.getActionText();}
			String appearance = null;
			if(viewSelectedReport.getAppearanceText().equals(null)) {
				appearance = incidentReport.getStudentCondition(); 
			}else {appearance = viewSelectedReport.getAppearanceText();}
			String comments = null;
			if(viewSelectedReport.getCommentsText().equals(null)) {
				comments = incidentReport.getComments(); 
			}else {comments = viewSelectedReport.getCommentsText();}
			director.modifyIncidentReport(incidentReport, description, action, appearance, comments);
			Platform.runLater(new ChangeScene(stage,new ModifyIncidentReports(stage,null,director,incidentReports)));
		});
		editButton.setOnAction(e -> {
			editButton.setVisible(false);
			writeButton.setVisible(true);
			viewSelectedReport.enableAll();
		});
		writeButton.setVisible(false);
		Button signButton = new Button("sign");
		signButton.getStyleClass().add("button");
		signButton.setOnAction(e -> {
			IncidentReport incidentReport = viewSelectedReport.getIncidentReport();
			incidentReport.setDirectorSignature(true);
			director.getMailbox().addSignedIncidentReport(incidentReport);
		});
		
		centerPane.add(returnButton,0,0);
		GridPane.setValignment(returnButton,VPos.TOP);
		centerPane.add(scrollPane,1,0);
		GridPane.setHalignment(scrollPane,HPos.CENTER);
		GridPane.setConstraints(scrollPane,1,0,4,4);
		centerPane.add(backArrowButton,0,2);
		GridPane.setHalignment(backArrowButton,HPos.CENTER);
		centerPane.add(nextArrowButton,5,2);
		GridPane.setHalignment(nextArrowButton,HPos.CENTER);
		centerPane.add(editButton,2,4);
		GridPane.setHalignment(editButton,HPos.CENTER);
		centerPane.add(writeButton,2,4);
		GridPane.setHalignment(writeButton,HPos.CENTER);
		centerPane.add(signButton,3,4);
		GridPane.setHalignment(signButton,HPos.CENTER);
		centerPane.add(pageCount,2,5);
		GridPane.setHalignment(pageCount,HPos.CENTER);
		GridPane.setConstraints(pageCount,2,5,2,1);
		
		return centerPane;
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
	
}
