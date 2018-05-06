package com.richmond.darkhorse.ProjectSB.gui.scene;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.richmond.darkhorse.ProjectSB.Center;
import com.richmond.darkhorse.ProjectSB.Classroom;
import com.richmond.darkhorse.ProjectSB.Director;
import com.richmond.darkhorse.ProjectSB.Teacher;
import com.richmond.darkhorse.ProjectSB.gui.component.DirectorLayout;
import com.richmond.darkhorse.ProjectSB.middleman.ChangeScene;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class DirectorClassroomWorkspace extends Scene implements DirectorLayout {
	
	private double rowIndex = 0.5;
	private int column = 1;
	
	public DirectorClassroomWorkspace(Stage stage,Scene nextScene,Director director) {
		this(stage,new BorderPane(),nextScene,director);
	}
	
	public DirectorClassroomWorkspace(Stage stage,BorderPane layout,Scene nextScene,Director director) {
		super(layout);
		HBox topPane = buildTopPane(stage,director);
		HBox bottomPane = buildBottomPane();
		VBox leftPane = buildLeftPane(stage,director);
		ScrollPane scrollPane = buildGridPane(stage,director);
		BorderPane directorClassroomWorkspaceLayout = layout;
		setBorderPaneCenterScroll(directorClassroomWorkspaceLayout,scrollPane,null,leftPane,topPane,bottomPane);
		directorClassroomWorkspaceLayout.getStylesheets().add("css/director.css");
	}
	
	/**
	 * Builds the central GridPane for the over BorderPane layout of {@link DirectorClassroomWorkspace}
	 * @param stage - the current stage
	 * @param director - a {@link Director}
	 * @return GridPane
	 */
	private ScrollPane buildGridPane(Stage stage,Director director) {
		GridPane gridpane = new GridPane();
		setConstraints(gridpane,4,0,10,10,"gridpane");
		ImageView workspaceViewer = createImageWithFitHeight("images/classroomsworkspace.png",150); 
		populateClassrooms(stage,director,gridpane);
		List<Node> nodes = Arrays.asList(workspaceViewer);
		placeNodes(gridpane,nodes);
		ScrollPane scrollPane = new ScrollPane(gridpane);
		scrollPane.setFitToWidth(true);
		return scrollPane;
	}
	
	@Override
	public void placeNodes(GridPane gridpane, List<Node> nodes) {
		placeNodeSpan(gridpane,nodes.get(0),1,0,2,1,"center",null);
	}
	
	/**
	 * Populates the passed GridPane with all of the {@link Classroom}s in the {@link Director}'s {@link Center}
	 * @param stage - the current stage
	 * @param director - a {@link Director}
	 * @param gridpane - GridPane
	 */
	private void populateClassrooms(Stage stage,Director director,GridPane gridpane) {
		Map<String,Classroom> allClassrooms = new HashMap<String,Classroom>();
		Center center = director.getCenter(director.getCenterID()); 
		Map<String,Classroom> centerClassrooms = center.getClassrooms();
		for(Classroom theClassroom : centerClassrooms.values()) {allClassrooms.put(theClassroom.getClassroomID(), theClassroom);}
		for(Classroom classroomValue : allClassrooms.values()) {
			String classroomType = classroomValue.getClassroomType(), teachers = "N/A";
			if(classroomValue.getTeacherID() != null && classroomValue.getAssistantTeacherID() != null) {
				Teacher leadTeacher = classroomValue.getTeacher(classroomValue.getTeacherID());
				Teacher assistantTeacher = classroomValue.getAssistantTeacher(classroomValue.getAssistantTeacherID());
				teachers = leadTeacher.getFirstName() + " " + leadTeacher.getLastName() + ", \n" + assistantTeacher.getFirstName() + " " + assistantTeacher.getLastName();
			}else if(classroomValue.getTeacher(classroomValue.getTeacherID()) != null) {
				Teacher leadTeacher = classroomValue.getTeacher(classroomValue.getTeacherID());
				teachers = leadTeacher.getFirstName() + " " + leadTeacher.getLastName(); 
			}
			String mondayRatio = classroomValue.getCount("Monday") + "/" + classroomValue.getMaxSize(), tuesdayRatio = classroomValue.getCount("Tuesday") + "/" + classroomValue.getMaxSize(), wednesdayRatio = classroomValue.getCount("Wednesday") + "/" + classroomValue.getMaxSize(), thursdayRatio = classroomValue.getCount("Thursday") + "/" + classroomValue.getMaxSize(), fridayRatio = classroomValue.getCount("Friday") + "/" + classroomValue.getMaxSize();
			String buttonText = classroomType + "\n" + "Teachers: " + teachers + "\n" + "Monday Ratio: " + mondayRatio + "\n" + "Tuesday Ratio: " + tuesdayRatio + "\n" + "Wednesday Ratio: " + wednesdayRatio + "\n" + "Thursday Ratio: " + thursdayRatio + "\n" + "Friday Ratio: " + fridayRatio;
			Button newButton = createButton(buttonText,null,0,300,500);
			newButton.setOnAction(e -> Platform.runLater(new ChangeScene(stage,new ModifyClassroomSetup(stage,null,classroomValue,director))));
			int row = 0;
			boolean doesNumberEndInZero = doesNumberEndInZero(rowIndex);
			if(doesNumberEndInZero == false) {row = (int) Math.round(rowIndex);}
			else {row = (int)rowIndex;}
			determinePosition(gridpane,newButton,row,column);
			rowIndex = rowIndex+0.5;
			column++;
		}
	}
	
}

