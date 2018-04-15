package com.richmond.darkhorse.ProjectSB.gui.scene;
import com.richmond.darkhorse.ProjectSB.Admin;
import com.richmond.darkhorse.ProjectSB.gui.component.AdminLayout;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.scene.image.ImageView;

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
		adminHomeLayout.getStylesheets().add("adminhome.css");
	}
	
	/**
	 * Builds the central GridPane for the BorderPane layout 
	 * @return a GridPane
	 */
	public GridPane buildCenterPane() {
		GridPane centerPane = new GridPane();
		setConstraints(centerPane,1,0,10,10);
		ImageView welcomeViewer = createImageWithFitHeight("welcome.png",125);
		ImageView iconViewer = createImageWithFitHeight("admin.png",450);
		placeNode(centerPane,welcomeViewer,0,0,"center",null);
		placeNode(centerPane,iconViewer,0,1,"center",null);
		return centerPane;
	}
	
}