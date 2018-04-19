package com.richmond.darkhorse.ProjectSB.gui.scene;
import java.util.List;

import com.richmond.darkhorse.ProjectSB.Admin;
import com.richmond.darkhorse.ProjectSB.gui.component.AdminLayout;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.scene.image.ImageView;
import javafx.scene.Node;

public class AdminHome extends Scene implements AdminLayout{
		
	public AdminHome(Stage stage,Scene nextScene,Admin admin) {
		this(stage,new BorderPane(),nextScene,admin);
	}
	
	public AdminHome(Stage stage,BorderPane layout,Scene nextScene,Admin admin) {
		super(layout);
		HBox topPane = buildTopPane(stage,admin);
		HBox bottomPane = buildBottomPane();
		VBox leftPane = buildLeftPane(stage,admin);
		GridPane centerPane = buildCenterPane();
		BorderPane adminHomeLayout = layout;
		setBorderPane(adminHomeLayout,centerPane,null,leftPane,topPane,bottomPane);
		adminHomeLayout.getStylesheets().add("css/admin.css");
	}
	
	/**
	 * Builds the central GridPane for the BorderPane layout 
	 * @return a GridPane
	 */
	public GridPane buildCenterPane() {
		GridPane centerPane = new GridPane();
		setConstraints(centerPane,1,0,10,10,"gridpane");
		ImageView welcomeViewer = createImageWithFitHeight("images/welcome.png",125);
		ImageView iconViewer = createImageWithFitHeight("images/admin.png",450);
		placeNode(centerPane,welcomeViewer,0,0,"center",null);
		placeNode(centerPane,iconViewer,0,1,"center",null);
		return centerPane;
	}
	
	/**
	 * Places all of the nodes in the list in the given GridPane
	 * @param gridpane - GridPane layout
	 * @param nodes - a list of nodes to be added to the GridPane
	 */
	public void placeNodes(GridPane gridpane,List<Node> nodes) {
	}
	
}