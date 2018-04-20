package com.richmond.darkhorse.ProjectSB.gui.component;
import java.util.Arrays;
import java.util.List;
import com.richmond.darkhorse.ProjectSB.Director;
import com.richmond.darkhorse.ProjectSB.Student;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

public class ViewRecordPane extends GridPane implements DirectorLayout{
	
	private GridPane innerPane;
	private DailySheetPane dailySheetPane;
	private BehaviorReportPane behaviorReportPane;
	private IncidentReportPane incidentReportPane;
	
	public ViewRecordPane(Director director,Student student) {
		buildDefaultPane(director,student);
	}
	
	/**
	 * Builds the starting GridPane for ViewRecordPane
	 * @param director - the current user
	 * @param student - the current {@link Student}
	 */
	private void buildDefaultPane(Director director,Student student) {
		setConstraints(this,3,0,10,10,"gridpane");
		this.getStylesheets().add("css/director.css");
		ImageView recordViewer = createImageWithFitHeight("images/record.png",150);
		String titleText = student.getFirstName() + "'s Record";
		Label title = createLabel(titleText,"super-subtitle");
		ToggleButton dailySheetButton = createToggleButton("daily sheets",true,50,300,"toggle-button");
		ToggleButton behaviorReportButton = createToggleButton("behavior reports",false,50,300,"toggle-button");
		ToggleButton incidentReportButton = createToggleButton("incident reports",false,50,300,"toggle-button");
		dailySheetPane = new DailySheetPane(director,student);
		innerPane = dailySheetPane;
		ScrollPane scrollPane = new ScrollPane(innerPane);
		scrollPane.setPrefHeight(500);
		scrollPane.setPrefWidth(500);
		dailySheetButton.setOnAction(e -> {
			if(behaviorReportButton.isSelected() == true) {behaviorReportButton.setSelected(false);}
			else if(incidentReportButton.isSelected() == true) {incidentReportButton.setSelected(false);}
			dailySheetPane = (DailySheetPane) buildDailySheetPane(student,director);
			scrollPane.setContent(dailySheetPane); 
		});
		behaviorReportButton.setOnAction(e -> {
			if(dailySheetButton.isSelected() == true) {dailySheetButton.setSelected(false);}
			else if(incidentReportButton.isSelected() == true) {incidentReportButton.setSelected(false);}
			behaviorReportPane = (BehaviorReportPane) buildBehaviorReportPane(student,director);
			scrollPane.setContent(behaviorReportPane);
		});
		incidentReportButton.setOnAction(e -> {
			if(dailySheetButton.isSelected() == true) {dailySheetButton.setSelected(false);}
			else if(behaviorReportButton.isSelected() == true) {behaviorReportButton.setSelected(false);}
			incidentReportPane = (IncidentReportPane) buildIncidentReportPane(student,director);
			scrollPane.setContent(incidentReportPane);
		});
		List<Node> nodes = Arrays.asList(recordViewer,title,dailySheetButton,behaviorReportButton,incidentReportButton,scrollPane);
		placeNodes(this,nodes);
	}
	
	/**
	 * Sets the internal ScrollPane to a DailySheetPane
	 * @param student - the current {@link Student}
	 * @param director - the current user
	 * @return a fully-assembled DailySheetPane
	 */
	public GridPane buildDailySheetPane(Student student,Director director) {
		DailySheetPane newDailySheetPane = new DailySheetPane(director,student);
		return newDailySheetPane;
	}
	
	/**
	 * Sets the internal ScrollPane to a BehaviorReportPane
	 * @param student - the current {@link Student}
	 * @param director - the current user
	 * @return a fully-assembled BehaviorReportPane
	 */
	public GridPane buildBehaviorReportPane(Student student,Director director) {
		BehaviorReportPane newBehaviorReportPane = new BehaviorReportPane(director,student);
		return newBehaviorReportPane;
	}
	
	/**
	 * Sets the internal ScrollPane to a IncidentReportPane
	 * @param student - the current {@link Student}
	 * @param director - the current user
	 * @return a fully-assembled IncidentReportPane
	 */
	public GridPane buildIncidentReportPane(Student student,Director director) {
		IncidentReportPane newIncidentReportPane = new IncidentReportPane(director,student);
		return newIncidentReportPane;
	}

	@Override
	public void placeNodes(GridPane gridpane,List<Node> nodes) {
		placeNode(gridpane,nodes.get(0),0,0,"center",null);
		placeNodeSpan(gridpane,nodes.get(1),1,0,2,1,"left",null);
		placeNode(gridpane,nodes.get(2),0,1,"center",null);
		placeNode(gridpane,nodes.get(3),1,1,"center",null);
		placeNode(gridpane,nodes.get(4),2,1,"center",null);
		placeNodeSpan(gridpane,nodes.get(5),0,2,3,3,"center",null);
	}

}
