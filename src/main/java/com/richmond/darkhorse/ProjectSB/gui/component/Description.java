package com.richmond.darkhorse.ProjectSB.gui.component;
import java.util.Arrays;
import java.util.List;
import com.richmond.darkhorse.ProjectSB.Event;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.scene.layout.GridPane;

public class Description implements DirectorLayout{

	private Event event;
	
	public Description(Event event) {
		this.event = event;
	}
	
	public void display() {
		Stage stage = new Stage();
		GridPane eventDescriptionLayout = buildGridPane();
		buildPopUp(stage,eventDescriptionLayout,event.getSummary());
	}
	
	/**
	 * Builds the GridPane for the {@link Description}
	 * @return GridPane
	 */
	private GridPane buildGridPane() {
		GridPane gridpane = new GridPane();
		setConstraints(gridpane,4,0,10,10,"modulargridpane");
	    gridpane.getStylesheets().add("css/director.css");
		Label eventTitle = createLabel(event.getSummary(),"subtitle");
	    Label detailsTitle = createLabel("Details:","label");
	    Label details = new Label();
	    if(event.getDetails() != null) {details.setText(event.getDetails());}
	    else {details.setText("N/A");}
	    details.getStyleClass().add("label");
		List<Node> nodes = Arrays.asList(eventTitle,detailsTitle,details);
		placeNodes(gridpane,nodes);
	    return gridpane;
	}

	@Override
	public void placeNodes(GridPane gridpane, List<Node> nodes) {
		placeNodeSpan(gridpane,nodes.get(0),0,0,4,1,"center",null);
		placeNode(gridpane,nodes.get(1),0,1,"right",null);
		placeNodeSpan(gridpane,nodes.get(2),1,1,3,1,"left",null);
	}
	
}
