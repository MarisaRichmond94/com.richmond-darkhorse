package com.richmond.darkhorse.ProjectSB.gui.scene;
import java.util.List;
import com.richmond.darkhorse.ProjectSB.Teacher;
import com.richmond.darkhorse.ProjectSB.gui.component.TeacherLayout;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class TeacherReportWorkspace extends Scene implements TeacherLayout{
	
	public TeacherReportWorkspace(Stage stage,Scene nextScene,Teacher teacher) {
		this(stage,new BorderPane(),nextScene,teacher);
	}
	
	public TeacherReportWorkspace(Stage stage,BorderPane layout,Scene nextScene,Teacher teacher) {
		super(layout);
		HBox topPane = buildTopPane(stage,teacher);
		HBox bottomPane = buildBottomPane();
		VBox leftPane = buildLeftPane(stage,teacher);
		GridPane centerPane = buildGridPane(teacher);
		BorderPane teacherReportWorkspaceLayout = layout;
		setBorderPane(teacherReportWorkspaceLayout,centerPane,null,leftPane,topPane,bottomPane);
		teacherReportWorkspaceLayout.getStylesheets().add("css/teacher.css");
	}
	
	/**
	 * Builds the central GridPane for the overall BorderPane layout of {@link TeacherReportWorkspace}
	 * @param teacher - a {@link Teacher}
	 * @return GridPane
	 */
	private GridPane buildGridPane(Teacher teacher) {
		GridPane gridpane = new GridPane();
		return gridpane;
	}

	@Override
	public void placeNodes(GridPane gridpane, List<Node> nodes) {
	}

}
