package com.richmond.darkhorse.ProjectSB.gui.scene;
import java.util.Arrays;
import java.util.List;
import com.richmond.darkhorse.ProjectSB.BehaviorReport;
import com.richmond.darkhorse.ProjectSB.Director;
import com.richmond.darkhorse.ProjectSB.IncidentReport;
import com.richmond.darkhorse.ProjectSB.gui.component.DirectorLayout;
import com.richmond.darkhorse.ProjectSB.gui.component.ImageButton;
import com.richmond.darkhorse.ProjectSB.gui.component.ViewSelectedIncidentReport;
import com.richmond.darkhorse.ProjectSB.middleman.ChangeScene;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class ModifyIncidentReports extends Scene implements DirectorLayout{

	private Director director;
	private List<IncidentReport> incidentReports;
	private BorderPane modifyIncidentReportLayout;
	private ViewSelectedIncidentReport viewSelectedReport;
	private int index = 0, total;
	
	public ModifyIncidentReports(Stage stage,Scene nextScene,Director director,List<IncidentReport> incidentReports) {
		this(stage,new BorderPane(),nextScene,incidentReports,director);
	}
	
	public ModifyIncidentReports(Stage stage,BorderPane layout,Scene nextScene,List<IncidentReport> incidentReports,Director director) {
		super(layout);
		this.director = director;
		this.incidentReports = incidentReports;
		this.total = incidentReports.size();
		modifyIncidentReportLayout = layout;
		HBox topPane = buildTopPane(stage,director);
		HBox bottomPane = buildBottomPane();
		GridPane centerPane = buildGridPane(stage);
		setBorderPane(modifyIncidentReportLayout,centerPane,null,null,topPane,bottomPane);
		modifyIncidentReportLayout.getStylesheets().add("css/director.css");
	}
	
	/**
	 * Builds the central GridPane for the overall BorderPane layout of {@link ModifyIncidentReports}
	 * @param stage - the current stage
	 * @return GridPane
	 */
	private GridPane buildGridPane(Stage stage) {
		GridPane gridpane = new GridPane();
		setConstraints(gridpane,6,0,10,10,"gridpane");
		String count = (index+1) + "/" + total;
		Label pageCount = createLabel(count,"label");
		viewSelectedReport = new ViewSelectedIncidentReport(director,incidentReports.get(index));
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
			viewSelectedReport = new ViewSelectedIncidentReport(director,incidentReports.get(index));
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
			viewSelectedReport = new ViewSelectedIncidentReport(director,incidentReports.get(index));
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
		IncidentReport incidentReport = viewSelectedReport.getIncidentReport();
		String description = null;
		if(viewSelectedReport.getDescriptionText().equals(null)) {description = incidentReport.getIncidentDescription();}
		else {description = viewSelectedReport.getDescriptionText();}
		String action = null;
		if(viewSelectedReport.getActionText().equals(null)) {action = incidentReport.getActionTaken();}
		else {action = viewSelectedReport.getActionText();}
		String appearance = null;
		if(viewSelectedReport.getAppearanceText().equals(null)) {appearance = incidentReport.getStudentCondition();}
		else {appearance = viewSelectedReport.getAppearanceText();}
		String comments = null;
		if(viewSelectedReport.getCommentsText().equals(null)) {comments = incidentReport.getComments();}
		else {comments = viewSelectedReport.getCommentsText();}
		director.modifyIncidentReport(incidentReport, description, action, appearance, comments);
		Platform.runLater(new ChangeScene(stage,new ModifyIncidentReports(stage,null,director,incidentReports)));
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
		IncidentReport incidentReport = viewSelectedReport.getIncidentReport();
		incidentReport.setDirectorSignature(true);
		director.getMailbox().addSignedIncidentReport(incidentReport);
	}
	
}
