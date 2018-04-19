package com.richmond.darkhorse.ProjectSB.gui.scene;
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
import javafx.geometry.HPos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
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
	
	public ScrollPane buildCenterPane(Stage stage,Director director) {
		GridPane centerPane = new GridPane();
		centerPane.setVgap(10);
		centerPane.setHgap(10);
		ColumnConstraints columnOne = new ColumnConstraints();
		columnOne.setPercentWidth(24.5);
		ColumnConstraints columnTwo = new ColumnConstraints();
		columnTwo.setPercentWidth(24.5);
		ColumnConstraints columnThree = new ColumnConstraints();
		columnThree.setPercentWidth(24.5);
		ColumnConstraints columnFour = new ColumnConstraints();
		columnFour.setPercentWidth(24.5);
		ColumnConstraints columnFive = new ColumnConstraints();
		columnFive.setPercentWidth(2);
		centerPane.getColumnConstraints().addAll(columnOne,columnTwo,columnThree,columnFour,columnFive);
		centerPane.getStyleClass().add("gridpane");
		
		ImageView workspaceViewer = new ImageView();
		Image studentsWorkspace = new Image("studentsworkspace.png");
		workspaceViewer.setImage(studentsWorkspace);
		workspaceViewer.setPreserveRatio(true);
		workspaceViewer.setFitHeight(150);
		
		//Add Student Button
		ImageView iconViewer = new ImageView();
		Image studentIcon = new Image("addstudent.png");
		iconViewer.setImage(studentIcon);
		iconViewer.setPreserveRatio(true);
		iconViewer.setFitHeight(200);
		Button addStudentButton = new Button("",iconViewer);
		addStudentButton.setOnAction(e -> {
			addStudent.display();
			Platform.runLater(new ChangeScene(stage,new DirectorStudentWorkspace(stage,null,director)));
		});
		addStudentButton.getStyleClass().add("button");
		addStudentButton.setPrefHeight(300);
		addStudentButton.setPrefWidth(500);
		
		Map<String,Student> students = director.getStudents();
		for(Student student : students.values()) {
			String firstName = student.getFirstName();
			String lastName = student.getLastName();
			String birthDate = student.getBirthDate();
			Classroom classroom = null;
			String classroomType = "N/A";
			if(student.getClassroom(student.getClassroomID()) != null) {
				classroom = student.getClassroom(student.getClassroomID());
				classroomType = classroom.getClassroomType();
			}
			Button newButton = new Button();
			newButton.setText(firstName + " " + lastName + "\n" + "Birth Date: " + birthDate + "\n" + "Classroom: " + classroomType);
			newButton.setOnAction(e -> {
				Platform.runLater(new ChangeScene(stage,new ModifyStudentInfo(stage,null,student,director)));
			});
			newButton.getStyleClass().add("button");
			newButton.setPrefHeight(300);
			newButton.setPrefWidth(500);
			int row = 0;
			boolean doesNumberEndInZero = doesNumberEndInZero(rowIndex);
			if(doesNumberEndInZero == false) {
				row = (int) Math.round(rowIndex);
			}else {
				row = (int)rowIndex;
			}
			determinePosition(centerPane,newButton,row,column);
			rowIndex = rowIndex+0.5;
			column++;
		}
		
		centerPane.add(workspaceViewer,1,0);
		GridPane.setConstraints(workspaceViewer,1,0,2,1);
		centerPane.add(addStudentButton,0,1);
		GridPane.setConstraints(addStudentButton,0,1,2,1);
		GridPane.setHalignment(workspaceViewer,HPos.CENTER);
		GridPane.setHalignment(addStudentButton,HPos.CENTER);
		ScrollPane scrollPane = new ScrollPane(centerPane);
		scrollPane.setFitToWidth(true);
		return scrollPane;
	}
	
	public void determinePosition(GridPane gridpane,Button button,int row,int column) {
		int columnIndex;
		if(column == 0) {
			gridpane.add(button, 2, 1);
			GridPane.setConstraints(button, 2, 1, 2, 1);
			GridPane.setHalignment(button, HPos.CENTER);
		}else {
			boolean isIndexEven = isIntEven(column);
			if(isIndexEven == true) {columnIndex = 2;
			}else {columnIndex = 0;}
			gridpane.add(button, columnIndex, row);
			GridPane.setConstraints(button, columnIndex, row, 2, 1);
			GridPane.setHalignment(button, HPos.CENTER);
			
		}
	}
	
	public boolean isIntEven(int n) {
		if( n % 2 == 0) {return true;}
		return false;
	}
	
	public boolean doesNumberEndInZero(double rowIndex) {
		if (Math.abs(rowIndex - Math.rint(rowIndex)) == 0.5) {return false;}
		return true;
	}

	@Override
	public void placeNodes(GridPane gridpane, List<Node> nodes) {
		// TODO Auto-generated method stub
		
	}
	
}

