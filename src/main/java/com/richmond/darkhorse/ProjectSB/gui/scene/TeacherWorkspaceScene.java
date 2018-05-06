package com.richmond.darkhorse.ProjectSB.gui.scene;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import com.richmond.darkhorse.ProjectSB.Admin;
import com.richmond.darkhorse.ProjectSB.Center;
import com.richmond.darkhorse.ProjectSB.StaffMember;
import com.richmond.darkhorse.ProjectSB.Teacher;
import com.richmond.darkhorse.ProjectSB.gui.component.AddTeacher;
import com.richmond.darkhorse.ProjectSB.gui.component.AdminLayout;
import com.richmond.darkhorse.ProjectSB.gui.component.ModifyTeacher;
import com.richmond.darkhorse.ProjectSB.middleman.ChangeScene;
import javafx.scene.Node;
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

public class TeacherWorkspaceScene extends Scene implements AdminLayout{
	
	private AddTeacher newAddTeacher;
	private double rowIndex = 1.0;
	private int column = 0;
	
	public TeacherWorkspaceScene(Stage stage,Scene nextScene,Admin admin) {
		this(stage,new BorderPane(),nextScene,admin);
		this.newAddTeacher = new AddTeacher(admin);
	}
	
	public TeacherWorkspaceScene(Stage stage,BorderPane layout,Scene nextScene,Admin admin) {
		super(layout);
		HBox topPane = buildTopPane(stage,admin);
		HBox bottomPane = buildBottomPane();
		VBox leftPane = buildLeftPane(stage,admin);
		ScrollPane scrollPane = new ScrollPane(buildCenterPane(stage,admin));
		scrollPane.setFitToWidth(true);
		BorderPane adminHomeLayout = layout;
		setBorderPaneCenterScroll(adminHomeLayout,scrollPane,null,leftPane,topPane,bottomPane);
		adminHomeLayout.getStylesheets().add("css/admin.css");
	}
	
	/**
	 * Builds the central pane for the overall layout
	 * @param stage - the current stage
	 * @param admin - the current user (admin)
	 * @return a fully-constructed GridPane layout 
	 */
	private GridPane buildCenterPane(Stage stage,Admin admin) {
		GridPane gridpane = new GridPane();
		setConstraints(gridpane,4,0,15,15,"gridpane");
		ImageView workspaceViewer = createImageWithFitHeight("images/workspace.png",150);
		Button addTeacherButton = createButton(null,"images/teacher.png",200,300,500);
		addTeacherButton.setOnAction(e -> {
			newAddTeacher.display();
			Platform.runLater(new ChangeScene(stage,new TeacherWorkspaceScene(stage,null,admin)));
		});
		populateTeachers(gridpane,stage,admin);
		List<Node> nodes = Arrays.asList(workspaceViewer,addTeacherButton);
		placeNodes(gridpane,nodes);
		return gridpane;
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
	 * Populates the given GridPane layout with all of the {@link Teacher}s in the system
	 * @param gridpane - the given GridPane layout
	 * @param stage - the current stage
	 * @param admin - the current user (admin)
	 */
	private void populateTeachers(GridPane gridpane,Stage stage,Admin admin) {
		Map<String,StaffMember> staffMembers = admin.getStaffMembers();
		List<Teacher> teachers = new ArrayList<Teacher>();
		for(StaffMember staffMember: staffMembers.values()) {
			String title = staffMember.getTitle();
			if(title.equals("Teacher")) {teachers.add((Teacher) staffMember);}
		}
		for(Teacher theTeacher : teachers) {
			String firstName = theTeacher.getFirstName(), lastName = theTeacher.getLastName();
			Center theCenter = theTeacher.getCenter(theTeacher.getCenterID());
			String centerString = theCenter.getAbbreviatedName();
			String buttonText = "" + firstName + " " + lastName + "\n" + "Center: " + centerString + "";
			Button newButton = createButton(buttonText,null,0,300,500);
			newButton.setOnAction(e -> {
				ModifyTeacher modifyTeacher = new ModifyTeacher(admin,(Teacher)theTeacher);
				modifyTeacher.display();
				Platform.runLater(new ChangeScene(stage,new TeacherWorkspaceScene(stage,null,admin)));
			});
			int row = 0;
			boolean doesNumberEndInZero = doesNumberEndInZero(rowIndex);
			if(doesNumberEndInZero == false) {row = (int) Math.round(rowIndex);}
			else {row = (int)rowIndex;}
			determinePosition(gridpane,newButton,row,column);
			rowIndex = rowIndex+0.5;
			column++;	
		}
	}
	
	/**
	 * Checks to see if a {@link StaffMember} is a {@link Teacher}
	 * @param staffMember 
	 * @return the staffMember if that {@link StaffMember} is a {@link Teacher} or null if the not
	 */
	public Teacher getTeacher(StaffMember staffMember) {
		String title = staffMember.getTitle();
		if(title.equals("Teacher")) {
			Teacher teacher = (Teacher)staffMember;
			return teacher;
		}
		return null;
	}
	
}
