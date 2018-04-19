package com.richmond.darkhorse.ProjectSB.gui.scene;
import java.util.List;
import com.richmond.darkhorse.ProjectSB.Director;
import com.richmond.darkhorse.ProjectSB.gui.component.DirectorLayout;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class DirectorHome extends Scene implements DirectorLayout{
	
	public DirectorHome(Stage stage,Scene nextScene,Director director) {
		this(stage,new BorderPane(),nextScene,director);
	}
	
	public DirectorHome(Stage stage,BorderPane layout,Scene nextScene,Director director) {
		super(layout);
		HBox topPane = buildTopPane(stage,director);
		HBox bottomPane = buildBottomPane();
		VBox leftPane = buildLeftPane(stage,director);
		GridPane centerPane = buildGridPane(director);
		BorderPane directorHomeLayout = layout;
		setBorderPane(directorHomeLayout,centerPane,null,leftPane,topPane,bottomPane);
		directorHomeLayout.getStylesheets().add("css/director.css");
	}
	
	public GridPane buildGridPane(Director director) {
		GridPane gridpane = new GridPane();
		setConstraints(gridpane,1,0,10,10,"gridpane");
		ImageView welcomeViewer = createImageWithFitHeight("images/welcome.png",150);
		ImageView iconViewer = createImageWithFitHeight("images/director.png",350);
		String directorName = director.getFirstName() + " " + director.getLastName();
		Label nameLabel = createLabel(directorName,"title");
		placeNode(gridpane,welcomeViewer,0,0,"center",null);
		placeNode(gridpane,iconViewer,0,1,"center",null);
		placeNode(gridpane,nameLabel,0,2,"center",null);
		return gridpane;
	}

	@Override
	public void placeNodes(GridPane gridpane, List<Node> nodes) {}
	
}