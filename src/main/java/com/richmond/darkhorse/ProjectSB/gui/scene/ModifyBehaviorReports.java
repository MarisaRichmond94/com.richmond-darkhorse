package com.richmond.darkhorse.ProjectSB.gui.scene;
import javafx.scene.control.ScrollPane;
import java.util.Arrays;
import java.util.List;
import com.richmond.darkhorse.ProjectSB.BehaviorReport;
import com.richmond.darkhorse.ProjectSB.Director;
import com.richmond.darkhorse.ProjectSB.gui.component.DirectorLayout;
import com.richmond.darkhorse.ProjectSB.gui.component.ImageButton;
import com.richmond.darkhorse.ProjectSB.gui.component.ViewSelectedBehaviorReport;
import com.richmond.darkhorse.ProjectSB.middleman.ChangeScene;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.control.Button;

public class ModifyBehaviorReports extends Scene implements DirectorLayout{

	private Director director;
	private List<BehaviorReport> behaviorReports;
	private BorderPane modifyBehaviorReportLayout;
	private ViewSelectedBehaviorReport viewSelectedReport;
	private int index = 0, total;
	
	public ModifyBehaviorReports(Stage stage,Scene nextScene,Director director,List<BehaviorReport> behaviorReports) {
		this(stage,new BorderPane(),nextScene,behaviorReports,director);
	}
	
	public ModifyBehaviorReports(Stage stage,BorderPane layout,Scene nextScene,List<BehaviorReport> behaviorReports,Director director) {
		super(layout);
		this.director = director;
		this.behaviorReports = behaviorReports;
		this.total = behaviorReports.size();
		modifyBehaviorReportLayout = layout;
		HBox topPane = buildTopPane(stage,director);
		HBox bottomPane = buildBottomPane();
		GridPane centerPane = buildGridPane(stage);
		setBorderPane(modifyBehaviorReportLayout,centerPane,null,null,topPane,bottomPane);
		modifyBehaviorReportLayout.getStylesheets().add("css/director.css");
	}
	
	/**
	 * Builds the central GridPane for {@link ModifyBehaviorReports}
	 * @param stage - the current stage
	 * @return GridPane
	 */
	private GridPane buildGridPane(Stage stage) {
		GridPane gridpane = new GridPane();
		setConstraints(gridpane,6,0,10,10,"gridpane");
		gridpane.getStylesheets().add("css/director.css");
		String count = (index+1) + "/" + total;
		Label pageCount = createLabel(count,"label");
		viewSelectedReport = new ViewSelectedBehaviorReport(director,behaviorReports.get(index));
		ScrollPane scrollPane = new ScrollPane(viewSelectedReport);
		fixScrollPane(scrollPane);
		ImageButton returnButton = new ImageButton(createImageWithFitHeight("images/back.png",75));
		returnButton.setOnAction(e -> {Platform.runLater(new ChangeScene(stage,new DirectorMailbox(stage,null,director)));});
		ImageButton backArrowButton = new ImageButton(createImageWithFitHeight("images/backarrow.png",100));
		ImageButton nextArrowButton = new ImageButton(createImageWithFitHeight("images/arrow.png",100));
		backArrowButton.setVisible(false);
		backArrowButton.setOnAction(e -> goBack(scrollPane,pageCount,nextArrowButton,backArrowButton));
		@SuppressWarnings("unused")
		Label previous = createLabel("previous","label"), next = createLabel("next","label");
		if((index+1) == total) {nextArrowButton.setVisible(false);}
		nextArrowButton.setOnAction(e -> goForward(scrollPane,pageCount,nextArrowButton,backArrowButton));
		Button editButton = createButton("edit",null,0,0,0), writeButton = createButton("write",null,0,0,0);
		writeButton.setOnAction(e -> write(stage));
		editButton.setOnAction(e -> edit(writeButton,editButton));
		writeButton.setVisible(false);
		Button signButton = createButton("sign",null,0,0,0);
		signButton.setOnAction(e -> sign());
		List<Node> nodes = Arrays.asList(returnButton,scrollPane,backArrowButton,nextArrowButton,editButton,writeButton,signButton,pageCount);
		placeNodes(gridpane,nodes);
		return gridpane;
	}

	@Override
	public void placeNodes(GridPane gridpane, List<Node> nodes) {
		placeNode(gridpane,nodes.get(0),0,0,null,"top");
		placeNodeSpan(gridpane,nodes.get(1),1,0,4,4,"center",null);
		placeNode(gridpane,nodes.get(2),0,2,"center",null);
		placeNode(gridpane,nodes.get(3),5,2,"center",null);
		placeNode(gridpane,nodes.get(4),2,4,"center",null);
		placeNode(gridpane,nodes.get(5),2,4,"center",null);
		placeNode(gridpane,nodes.get(6),3,4,"center",null);
		placeNodeSpan(gridpane,nodes.get(7),2,5,2,1,"center",null);	
	}
	
	/**
	 * Applies stuff to my ScrollPane because I want it out of the other method
	 * @param scrollPane - ScrollPane
	 */
	private void fixScrollPane(ScrollPane scrollPane) {
		scrollPane.setPrefHeight(500);
		scrollPane.setPrefWidth(500);
		scrollPane.setFitToHeight(true);
		scrollPane.setFitToWidth(true);
	}
	
	/**
	 * Takes the user back to the previous pane
	 * @param scrollPane - ScrollPane
	 * @param pageCount - Label
	 * @param nextArrowButton - Button
	 * @param backArrowButton - Button
	 */
	private void goBack(ScrollPane scrollPane,Label pageCount,ImageButton nextArrowButton,ImageButton backArrowButton) {
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
	}
	
	/**
	 * Takes the user back to the next pane
	 * @param scrollPane - ScrollPane
	 * @param pageCount - Label
	 * @param nextArrowButton - Button
	 * @param backArrowButton - Button
	 */
	private void goForward(ScrollPane scrollPane,Label pageCount,ImageButton nextArrowButton,ImageButton backArrowButton) {
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
	}
	
	/**
	 * Writes the changes made to the {@link BehaviorReport}
	 * @param stage - the current stage
	 */
	private void write(Stage stage) {
		BehaviorReport behaviorReport = viewSelectedReport.getBehaviorReport();
		String activity = null;
		if(viewSelectedReport.getClassroomActivityText().equals(null)) {activity = behaviorReport.getActivityDuringBehavior();}
		else {activity = viewSelectedReport.getClassroomActivityText();}
		String behavior = null;
		if(viewSelectedReport.getChildsBehaviorText().equals(null)) {behavior = behaviorReport.getChildsBehavior();}
		else {behavior = viewSelectedReport.getChildsBehaviorText();}
		String consequence = null;
		if(viewSelectedReport.getConsequenceText().equals(null)) {consequence = behaviorReport.getConsequence();}
		else {consequence = viewSelectedReport.getConsequenceText();}
		String reaction = null;
		if(viewSelectedReport.getChildsReactionText().equals(null)) {reaction = behaviorReport.getStudentResponse();}
		else {reaction = viewSelectedReport.getChildsReactionText();}
		String duration = null;
		if(viewSelectedReport.getTimeLastedText().equals(null)) {duration = behaviorReport.getDurationBehaviorOccurred();}
		else {duration = viewSelectedReport.getTimeLastedText();}
		director.modifyBehaviorReport(behaviorReport,activity,behavior,consequence,reaction,duration);
		Platform.runLater(new ChangeScene(stage,new ModifyBehaviorReports(stage,null,director,behaviorReports)));
	}
	
	/**
	 * Enters the user into edit mode
	 * @param writeButton - Button
	 * @param editButton - Button
	 */
	private void edit(Button writeButton,Button editButton) {
		editButton.setVisible(false);
		writeButton.setVisible(true);
		viewSelectedReport.enableAll();
	}
	
	/**
	 * Signs the {@link BehaviorReport}
	 */
	private void sign() {
		BehaviorReport behaviorReport = viewSelectedReport.getBehaviorReport();
		behaviorReport.setDirectorSignature(true);
		director.getMailbox().addSignedBehaviorReport(behaviorReport);
	}
	
}
