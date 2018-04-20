package com.richmond.darkhorse.ProjectSB.gui.scene;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.richmond.darkhorse.ProjectSB.Admin;
import com.richmond.darkhorse.ProjectSB.Center;
import com.richmond.darkhorse.ProjectSB.Classroom;
import com.richmond.darkhorse.ProjectSB.Teacher;
import com.richmond.darkhorse.ProjectSB.gui.component.AddClassroom;
import com.richmond.darkhorse.ProjectSB.gui.component.AdminLayout;
import com.richmond.darkhorse.ProjectSB.gui.component.ModifyClassroom;
import com.richmond.darkhorse.ProjectSB.middleman.ChangeScene;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.control.ScrollPane;
import javafx.scene.Node;

public class ClassroomWorkspaceScene extends Scene implements AdminLayout{
	
	private AddClassroom newAddClassroom;
	private double rowIndex = 1.0;
	private int column = 0;
	
	public ClassroomWorkspaceScene(Stage stage,Scene nextScene,Admin admin) {
		this(stage,new BorderPane(),nextScene,admin);
		this.newAddClassroom = new AddClassroom(admin);
	}
	
	public ClassroomWorkspaceScene(Stage stage,BorderPane layout,Scene nextScene,Admin admin) {
		super(layout);
		HBox topPane = buildTopPane(stage,admin);
		HBox bottomPane = buildBottomPane();
		VBox leftPane = buildLeftPane(stage,admin);
		ScrollPane scrollPane = new ScrollPane(buildGridPane(stage,admin));
		scrollPane.setFitToWidth(true);
		BorderPane adminHomeLayout = layout;
		setBorderPaneCenterScroll(adminHomeLayout,scrollPane,null,leftPane,topPane,bottomPane);
		adminHomeLayout.getStylesheets().add("css/admin.css");
	}
	
	/**
	 * Builds a GridPane
	 * @param stage - the current stage
	 * @param admin - the current user (administrator)
	 * @return a fully assembled GridPane
	 */
	private GridPane buildGridPane(Stage stage,Admin admin) {
		GridPane centerPane = new GridPane();
		setConstraints(centerPane,4,0,15,15,"gridpane");
		ImageView workspaceViewer = createImageWithFitHeight("images/workspace.png",150);
		Button addClassroomButton = createButton(null,"images/classroom.png",200,300,500);
		addClassroomButton.setOnAction(e -> {
			newAddClassroom.display(); 
			Platform.runLater(new ChangeScene(stage,new ClassroomWorkspaceScene(stage,null,admin)));
		});
		populateClassrooms(stage,admin,centerPane);
		List<Node> nodes = Arrays.asList(workspaceViewer,addClassroomButton);
		placeNodes(centerPane,nodes);
		return centerPane;
	}
	
	/**
	 * Places all of the nodes in the list in the given GridPane
	 * @param gridpane - GridPane layout
	 * @param nodes - a list of nodes to be added to the GridPane
	 */
	public void placeNodes(GridPane gridpane,List<Node> nodes) {
		placeNodeSpan(gridpane,nodes.get(0),1,0,2,1,"center",null);
		placeNodeSpan(gridpane,nodes.get(1),0,1,2,1,"center",null);
	}
	
	/**
	 * Populates the given GridPane with all of the {@link Classroom}s that exist in the system
	 * @param stage - the current stage
	 * @param admin - the current user (administrator)
	 * @param centerPane - GridPane 
	 */
	private void populateClassrooms(Stage stage,Admin admin,GridPane centerPane) {
		Map<String,Classroom> allClassrooms = new HashMap<String,Classroom>();
		Map<String,Center> centers = admin.getCenters();
		for(Center theCenter : centers.values()) {
			Map<String,Classroom> classrooms = theCenter.getClassrooms();
			for(Classroom theClassroom : classrooms.values()) {allClassrooms.put(theClassroom.getClassroomID(), theClassroom);}
		}	
		for(Classroom value : allClassrooms.values()) {
			String classroomType = value.getClassroomType(), leadTeacherName = "N/A", assistantTeacherName = "N/A", maxCapacity = String.valueOf(value.getMaxSize()), ageGroup = value.getAgeGroup();
			Center theCenter = value.getCenter(value.getCenterID());
			if(value.getTeacher(value.getTeacherID()) != null) {
				Teacher leadTeacher = value.getTeacher(value.getTeacherID());
				leadTeacherName = leadTeacher.toString();
			}
			if(value.getAssistantTeacher(value.getAssistantTeacherID()) != null) {
				Teacher assistantTeacher = value.getAssistantTeacher(value.getAssistantTeacherID());
				assistantTeacherName = assistantTeacher.toString();
			}
			String buttonText = classroomType + "\n" + "Center: " + theCenter.getAbbreviatedName() + "\n" + "Lead Teacher: " + leadTeacherName + "\n" + "Assistant Teacher: " + assistantTeacherName + "\n" + "Class size: " + maxCapacity + "\n" + "Age Group: " + ageGroup;
			Button newButton = createButton(buttonText,null,0,300,500);
			newButton.setOnAction(e -> {
				ModifyClassroom modifyClassroom = new ModifyClassroom(value,admin);
				modifyClassroom.display();
				newButton.setText(value.getClassroomType() + "\n" + "Center: " + value.getCenter(value.getCenterID()).getAbbreviatedName() + "\n" + "Lead Teacher: " + value.getTeacher(value.getTeacherID()) + "\n" + "Assistant Teacher: " + value.getAssistantTeacher(value.getAssistantTeacherID()) + "\n" + "Class size: " + value.getMaxSize() + "\n" + "Age Group: " + value.getAgeGroup());
				Platform.runLater(new ChangeScene(stage,new ClassroomWorkspaceScene(stage,null,admin)));
			});
			int row = 0;
			boolean doesNumberEndInZero = doesNumberEndInZero(rowIndex);
			if(doesNumberEndInZero == false) {row = (int) Math.round(rowIndex);}
			else {row = (int)rowIndex;}
			determinePosition(centerPane,newButton,row,column);
			rowIndex = rowIndex+0.5;
			column++;
		}
	}
	
}
