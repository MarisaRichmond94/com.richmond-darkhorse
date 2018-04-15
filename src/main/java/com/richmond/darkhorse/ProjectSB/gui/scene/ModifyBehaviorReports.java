package com.richmond.darkhorse.ProjectSB.gui.scene;
import javafx.scene.control.ScrollPane;
import java.util.List;
import java.util.Map;
import com.richmond.darkhorse.ProjectSB.AccountManager;
import com.richmond.darkhorse.ProjectSB.BehaviorReport;
import com.richmond.darkhorse.ProjectSB.Center;
import com.richmond.darkhorse.ProjectSB.Director;
import com.richmond.darkhorse.ProjectSB.SpecialBeginnings;
import com.richmond.darkhorse.ProjectSB.gui.component.ImageButton;
import com.richmond.darkhorse.ProjectSB.gui.component.ViewSelectedBehaviorReport;
import com.richmond.darkhorse.ProjectSB.middleman.ChangeScene;
import javafx.scene.image.Image;
import javafx.application.Platform;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.stage.Stage;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.control.Button;

public class ModifyBehaviorReports extends Scene{

	private Director director;
	private List<BehaviorReport> behaviorReports;
	private BorderPane modifyBehaviorReportLayout;
	private ViewSelectedBehaviorReport viewSelectedReport;
	private int index = 0;
	private int total;
	
	public ModifyBehaviorReports(Stage stage,Scene nextScene,Director director,List<BehaviorReport> behaviorReports) {
		this(stage,new BorderPane(),nextScene,behaviorReports,director);
	}
	
	public ModifyBehaviorReports(Stage stage,BorderPane layout,Scene nextScene,List<BehaviorReport> behaviorReports,Director director) {
		super(layout);
		this.director = director;
		this.behaviorReports = behaviorReports;
		this.total = behaviorReports.size();
		modifyBehaviorReportLayout = layout;
		
		HBox topBar = buildTopBar(stage,director);
		HBox bottomBar = buildBottomBar();
		GridPane centerPane = buildCenterPane(stage);
		modifyBehaviorReportLayout.setTop(topBar);
		modifyBehaviorReportLayout.setBottom(bottomBar);
		modifyBehaviorReportLayout.setCenter(centerPane);
		modifyBehaviorReportLayout.getStylesheets().add("directormailbox.css");
		
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
		
		viewSelectedReport = new ViewSelectedBehaviorReport(director,behaviorReports.get(index));
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
				viewSelectedReport = new ViewSelectedBehaviorReport(director,behaviorReports.get(index));
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
				viewSelectedReport = new ViewSelectedBehaviorReport(director,behaviorReports.get(index));
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
			BehaviorReport behaviorReport = viewSelectedReport.getBehaviorReport();
			String activity = null;
			if(viewSelectedReport.getClassroomActivityText().equals(null)) {
				activity = behaviorReport.getActivityDuringBehavior(); 
			}else {activity = viewSelectedReport.getClassroomActivityText();}
			String behavior = null;
			if(viewSelectedReport.getChildsBehaviorText().equals(null)) {
				behavior = behaviorReport.getChildsBehavior(); 
			}else {behavior = viewSelectedReport.getChildsBehaviorText();}
			String consequence = null;
			if(viewSelectedReport.getConsequenceText().equals(null)) {
				consequence = behaviorReport.getConsequence(); 
			}else {consequence = viewSelectedReport.getConsequenceText();}
			String reaction = null;
			if(viewSelectedReport.getChildsReactionText().equals(null)) {
				reaction = behaviorReport.getStudentResponse(); 
			}else {reaction = viewSelectedReport.getChildsReactionText();}
			String duration = null;
			if(viewSelectedReport.getTimeLastedText().equals(null)) {
				duration = behaviorReport.getDurationBehaviorOccurred(); 
			}else {duration = viewSelectedReport.getTimeLastedText();}
			director.modifyBehaviorReport(behaviorReport,activity,behavior,consequence,reaction,duration);
			Platform.runLater(new ChangeScene(stage,new ModifyBehaviorReports(stage,null,director,behaviorReports)));
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
			BehaviorReport behaviorReport = viewSelectedReport.getBehaviorReport();
			behaviorReport.setDirectorSignature(true);
			director.getMailbox().addSignedBehaviorReport(behaviorReport);
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
