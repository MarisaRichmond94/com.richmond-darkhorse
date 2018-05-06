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

public class TeacherSchedule extends Scene implements TeacherLayout{
	
	public TeacherSchedule(Stage stage,Scene nextScene,Teacher teacher) {
		this(stage,new BorderPane(),nextScene,teacher);
	}
	
	public TeacherSchedule(Stage stage,BorderPane layout,Scene nextScene,Teacher teacher) {
		super(layout);
		HBox topPane = buildTopPane(stage,teacher);
		HBox bottomPane = buildBottomPane();
		VBox leftPane = buildLeftPane(stage,teacher);
		GridPane centerPane = buildGridPane(teacher);
		BorderPane teacherScheduleLayout = layout;
		setBorderPane(teacherScheduleLayout,centerPane,null,leftPane,topPane,bottomPane);
		teacherScheduleLayout.getStylesheets().add("css/teacher.css");
	}
	
	/**
	 * Builds the central GridPane for the overall BorderPane layout of {@link TeacherSchedule}
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
