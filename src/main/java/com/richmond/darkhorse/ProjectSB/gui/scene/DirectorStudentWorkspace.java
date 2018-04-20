package com.richmond.darkhorse.ProjectSB.gui.scene;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import javafx.scene.control.ScrollPane;
import com.richmond.darkhorse.ProjectSB.Classroom;
import com.richmond.darkhorse.ProjectSB.Director;
import com.richmond.darkhorse.ProjectSB.Student;
import com.richmond.darkhorse.ProjectSB.gui.component.AddStudent;
import com.richmond.darkhorse.ProjectSB.gui.component.DirectorLayout;
import com.richmond.darkhorse.ProjectSB.middleman.ChangeScene;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class DirectorStudentWorkspace extends Scene implements DirectorLayout {
	
	private AddStudent addStudent;
	private double rowIndex = 1.0;
	private int column = 0;
	
	public DirectorStudentWorkspace(Stage stage,Scene nextScene,Director director) {
		this(stage,new BorderPane(),nextScene,director);
		this.addStudent = new AddStudent(director);
	}
	
	public DirectorStudentWorkspace(Stage stage,BorderPane layout,Scene nextScene,Director director) {
		super(layout);
		HBox topPane = buildTopPane(stage,director);
		HBox bottomPane = buildBottomPane();
		VBox leftPane = buildLeftPane(stage,director);
		ScrollPane scrollPane = buildCenterPane(stage,director);
		BorderPane directorStudentWorkspaceLayout = layout;
		setBorderPaneCenterScroll(directorStudentWorkspaceLayout,scrollPane,null,leftPane,topPane,bottomPane);
		directorStudentWorkspaceLayout.getStylesheets().add("css/director.css");
	}
	
	/**
	 * Builds a fully populated GridPane and places it inside of a ScrollPane
	 * @param stage - the current stage
	 * @param director - the current user
	 * @return ScrollPane
	 */
	private ScrollPane buildCenterPane(Stage stage,Director director) {
		GridPane gridpane = new GridPane();
		setConstraints(gridpane,4,0,10,10,"gridpane");
		ImageView workspaceViewer = createImageWithFitHeight("images/studentsworkspace.png",150);
		Button addStudentButton = createButton(null,"images/addstudent.png",200,300,500);
		addStudentButton.setOnAction(e -> {
			addStudent.display();
			Platform.runLater(new ChangeScene(stage,new DirectorStudentWorkspace(stage,null,director)));
		});
		populateStudents(gridpane,stage,director);
		List<Node> nodes = Arrays.asList(workspaceViewer,addStudentButton);
		placeNodes(gridpane,nodes);
		ScrollPane scrollPane = new ScrollPane(gridpane);
		scrollPane.setFitToWidth(true);
		return scrollPane;
	}
	
	@Override
	public void placeNodes(GridPane gridpane, List<Node> nodes) {
		placeNodeSpan(gridpane,nodes.get(0),1,0,2,1,"center",null);
		placeNodeSpan(gridpane,nodes.get(1),0,1,2,1,"center",null);
	}
	
	/**
	 * Populates the given GridPane with all of the {@link Student}s in the {@link Director}'s {@link Center}
	 * @param gridpane - GridPane layout
	 * @param stage - the current stage
	 * @param director - the current user
	 */
	private void populateStudents(GridPane gridpane,Stage stage,Director director) {
		Map<String,Student> students = director.getStudents();
		for(Student student : students.values()) {
			String firstName = student.getFirstName(), lastName = student.getLastName(), birthDate = student.getBirthDate(), classroomType = "N/A";
			Classroom classroom = null;
			if(student.getClassroom(student.getClassroomID()) != null) {
				classroom = student.getClassroom(student.getClassroomID());
				classroomType = classroom.getClassroomType();
			}
			String buttonText = firstName + " " + lastName + "\n" + "Birth Date: " + birthDate + "\n" + "Classroom: " + classroomType;
			Button newButton = createButton(buttonText,null,0,300,500);
			newButton.setOnAction(e -> Platform.runLater(new ChangeScene(stage,new ModifyStudentInfo(stage,null,student,director))));
			int row = 0;
			boolean doesNumberEndInZero = doesNumberEndInZero(rowIndex);
			if(doesNumberEndInZero == false) { row = (int) Math.round(rowIndex); }
			else {row = (int)rowIndex;}
			determinePosition(gridpane,newButton,row,column);
			rowIndex = rowIndex+0.5;
			column++;
		}
	}
	
}

