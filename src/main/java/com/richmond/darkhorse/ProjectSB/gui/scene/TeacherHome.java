package com.richmond.darkhorse.ProjectSB.gui.scene;
import java.util.Arrays;
import java.util.List;
import com.richmond.darkhorse.ProjectSB.Teacher;
import com.richmond.darkhorse.ProjectSB.gui.component.TeacherLayout;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class TeacherHome extends Scene implements TeacherLayout{
	
	public TeacherHome(Stage stage,Scene nextScene,Teacher teacher) {
		this(stage,new BorderPane(),nextScene,teacher);
	}
	
	public TeacherHome(Stage stage,BorderPane layout,Scene nextScene,Teacher teacher) {
		super(layout);
		HBox topPane = buildTopPane(stage,teacher);
		HBox bottomPane = buildBottomPane();
		VBox leftPane = buildLeftPane(stage,teacher);
		GridPane centerPane = buildGridPane(teacher);
		BorderPane teacherHomeLayout = layout;
		setBorderPane(teacherHomeLayout,centerPane,null,leftPane,topPane,bottomPane);
		teacherHomeLayout.getStylesheets().add("css/teacher.css");
	}
	
	/**
	 * Builds the central GridPane for the overall BorderPane layout of {@link TeacherHome}
	 * @param teacher - a {@link Teacher}
	 * @return GridPane
	 */
	public GridPane buildGridPane(Teacher teacher) {
		GridPane gridpane = new GridPane();
		setConstraints(gridpane,1,0,10,10,"gridpane");
		ImageView welcomeViewer = createImageWithFitHeight("images/welcome.png",125);
		ImageView iconViewer = createImageWithFitHeight("images/teachericon.png",300);
		String teacherName = teacher.getFirstName() + " " + teacher.getLastName();
		Label nameLabel = createLabel(teacherName,"title");
		List<Node> nodes = Arrays.asList(welcomeViewer,iconViewer,nameLabel);		
		placeNodes(gridpane,nodes);
		return gridpane;
	}

	@Override
	public void placeNodes(GridPane gridpane,List<Node> nodes) {
		placeNode(gridpane,nodes.get(0),0,0,"center",null);
		placeNode(gridpane,nodes.get(1),0,1,"center",null);
		placeNode(gridpane,nodes.get(2),0,2,"center",null);
	}
	
}