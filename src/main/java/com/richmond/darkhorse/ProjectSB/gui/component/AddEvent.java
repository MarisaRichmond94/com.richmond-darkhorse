package com.richmond.darkhorse.ProjectSB.gui.component;
import java.util.Arrays;
import java.util.List;
import com.richmond.darkhorse.ProjectSB.Center;
import com.richmond.darkhorse.ProjectSB.Director;
import com.richmond.darkhorse.ProjectSB.Event;
import javafx.beans.binding.Bindings;
import javafx.scene.Node;
import javafx.stage.Stage;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class AddEvent implements DirectorLayout {

	private Director director;
	
	public AddEvent(Director director) {
		this.director = director;
	}
	
	public void display() {
		Stage stage = new Stage();
		GridPane addEventLayout = buildGridPane(stage);
		buildPopUp(stage,addEventLayout,"Create New Event");
	}

	/**
	 * Builds a GridPane to be used for the {@link AddEvent} layout
	 * @param stage - the current stage
	 * @return GridPane
	 */
	private GridPane buildGridPane(Stage stage) {
		GridPane gridpane = new GridPane();
		setConstraints(gridpane,4,0,10,10,"modulargridpane");
	    Label newEvent = createLabel("Create New Event","med-title");
	    Label summary = createLabel("Summary:","label");
	    TextField summaryText = createTextField("summary","textfield",650);
	    Label day = createLabel("Day:","label");
	    ChoiceBox<String> dayBox = buildDayBox();
	    Label time = createLabel("Time:","label");
	    TextField timeText = createTextField("time (optional)","textfield",650);
	    Label details = createLabel("Details:","label");
	    TextField detailsText = createTextField("additional details (optional)","textfield",650);
	    Button create = createButton("create",null,0,0,0);
	    create.disableProperty().bind(Bindings.isEmpty(summaryText.textProperty()));
	    List<TextField> dataNodes = Arrays.asList(summaryText,timeText,detailsText); 
	    create.setOnAction(e -> createEvent(stage,dataNodes,dayBox));
	    Button cancel = createButton("cancel",null,0,0,0);
	    cancel.setOnAction(e -> stage.close());
	    List<Node> nodes = Arrays.asList(newEvent,summary,summaryText,day,dayBox,time,timeText,details,detailsText,create,cancel);
	    placeNodes(gridpane,nodes);
	    gridpane.getStylesheets().add("css/director.css");
	    return gridpane;
	}
	
	@Override
	public void placeNodes(GridPane gridpane, List<Node> nodes) {
		placeNodeSpan(gridpane,nodes.get(0),0,0,4,1,"center",null);
		placeNode(gridpane,nodes.get(1),0,1,"right",null);
		placeNodeSpan(gridpane,nodes.get(2),1,1,3,1,"left",null);
		placeNode(gridpane,nodes.get(3),0,2,"right",null);
		placeNodeSpan(gridpane,nodes.get(4),1,2,3,1,"left",null);
		placeNode(gridpane,nodes.get(5),0,3,"right",null);
		placeNodeSpan(gridpane,nodes.get(6),1,3,3,1,"left",null);
		placeNode(gridpane,nodes.get(7),0,4,"right",null);
		placeNodeSpan(gridpane,nodes.get(8),1,4,3,1,"left",null);
	    placeNode(gridpane,nodes.get(9),1,5,"center",null);
	    placeNode(gridpane,nodes.get(10),2,5,"center",null);
	}
	
	/**
	 * Builds a ChoiceBox<String> populated with the days of the week (M-F)
	 * @return ChoiceBox<String>
	 */
	private ChoiceBox<String> buildDayBox(){
		ChoiceBox<String> dayBox = new ChoiceBox<String>();
	    dayBox.getItems().addAll("Monday","Tuesday","Wednesday","Thursday","Friday");
	    dayBox.setValue(dayBox.getItems().get(0));
	    dayBox.getStyleClass().add("choice-box");
	    return dayBox;
	}
	
	/**
	 * Creates a new {@link Event} to be added to the {@link EventCalendar} for the current {@link Director}'s {@link Center}
	 * @param stage - the current stage
	 * @param textfields - List<TextField>
	 * @param dayBox - ChoiceBox<String>
	 */
	private void createEvent(Stage stage,List<TextField> textfields,ChoiceBox<String> dayBox) {
		String eventSummary = textfields.get(0).getText();
		String eventDay = dayBox.getValue();
		String eventTime = null;
		if(!textfields.get(1).getText().isEmpty()) {eventTime = textfields.get(1).getText();}
		String eventDetails = null;
		if(!textfields.get(2).getText().isEmpty()) {eventDetails = textfields.get(2).getText();}
		Event event = director.createNewEvent(eventSummary,eventDay,eventTime,eventDetails);
		Center center = director.getCenter(director.getCenterID());
		center.getEventCalendar().addEvent(event);
		stage.close();
	}
	
}
