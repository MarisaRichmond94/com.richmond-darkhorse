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
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class DirectorStudentWorkspace extends Scene implements DirectorLayout {
	
	private BorderPane directorStudentWorkspaceLayout;
	private AddStudent addStudent;
	private final double ROW_INDEX = 1.0;
	private final int COLUMN = 0;
	
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
		directorStudentWorkspaceLayout = layout;
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
		Label view = createLabel("view:","label");
		ChoiceBox<String> viewBox = buildViewBox(stage,director);
		Button addStudentButton = createButton(null,"images/addstudent.png",200,300,500);
		addStudentButton.setOnAction(e -> {
			addStudent.display();
			Platform.runLater(new ChangeScene(stage,new DirectorStudentWorkspace(stage,null,director)));
		});
		populateStudents(gridpane,stage,director);
		List<Node> nodes = Arrays.asList(workspaceViewer,view,viewBox,addStudentButton);
		placeNodes(gridpane,nodes);
		ScrollPane scrollPane = new ScrollPane(gridpane);
		scrollPane.setFitToWidth(true);
		return scrollPane;
	}
	
	@Override
	public void placeNodes(GridPane gridpane, List<Node> nodes) {
		placeNodeSpan(gridpane,nodes.get(0),0,0,3,1,"center",null);
		placeNode(gridpane,nodes.get(1),2,0,"right",null);
		placeNode(gridpane,nodes.get(2),3,0,"left",null);
		placeNodeSpan(gridpane,nodes.get(3),0,1,2,1,"center",null);
	}
	
	/**
	 * Creates a ChoiceBox populated with the {@link Classroom}s in the {@link Center} 
	 * @param stage - the current stage
	 * @param director - a {@link Director}
	 * @return ChoiceBox
	 */
	private ChoiceBox<String> buildViewBox(Stage stage,Director director){
		ChoiceBox<String> viewBox = new ChoiceBox<String>();
	    viewBox.getItems().add("all classrooms");
	    Map<String,Classroom> classrooms = director.getClassrooms();
	    for(Classroom classroom : classrooms.values()) {viewBox.getItems().add(classroom.toString());}
	    viewBox.setValue(viewBox.getItems().get(0));
	    viewBox.getStyleClass().add("choice-box-mini");
	    ChangeListener<String> changeListener = new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if(!newValue.equals("all classrooms")) {
					Classroom classroom = null;
					Map<String,Classroom> classrooms = director.getClassrooms();
					for(Classroom classroomCheck : classrooms.values()) {if(classroomCheck.toString().equals(newValue)) {classroom = classroomCheck;}}
					if(classroom != null) {
						ScrollPane scrollPane = new ScrollPane(new FilterClassroomPane(stage,director,classroom));
						directorStudentWorkspaceLayout.setCenter(scrollPane);
						scrollPane.setFitToWidth(true);
					}
				}else {directorStudentWorkspaceLayout.setCenter(buildCenterPane(stage,director));}
			}
        };
        viewBox.getSelectionModel().selectedItemProperty().addListener(changeListener);
        return viewBox;
	}
	
	/**
	 * Populates the given GridPane with all of the {@link Student}s in the {@link Director}'s {@link Center}
	 * @param gridpane - GridPane layout
	 * @param stage - the current stage
	 * @param director - the current user
	 */
	private void populateStudents(GridPane gridpane,Stage stage,Director director) {
		double rowIndex = ROW_INDEX;
		int column = COLUMN;
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
	
	class FilterClassroomPane extends GridPane implements DirectorLayout{
		
		private Stage stage;
		private Director director;
		private Classroom classroom;
		
		public FilterClassroomPane(Stage stage,Director director,Classroom classroom) {
			this.stage = stage;
			this.director = director;
			this.classroom = classroom;
			buildGridPane();
		}
		/**
		 * Builds the GridPane
		 */
		private void buildGridPane() {
			setConstraints(this,4,0,10,10,"gridpane");
			ImageView workspaceViewer = createImageWithFitHeight("images/studentsworkspace.png",150);
			Label view = createLabel("view:","label");
			ChoiceBox<String> viewBox = buildClassroomBox(stage,director);
			Button classroomButton = createButton(null,"images/classroom.png",200,300,500);
			classroomButton.setOnAction(e -> Platform.runLater(new ChangeScene(stage,new ModifyClassroomSetup(stage,null,classroom,director))));
			populateClassroomStudents(stage,director);
			placeNodeSpan(this,workspaceViewer,0,0,3,1,"center",null);
			placeNode(this,view,2,0,"right",null);
			placeNode(this,viewBox,3,0,"left",null);
			placeNodeSpan(this,classroomButton,0,1,2,1,"center",null);
		}
		
		/**
		 * Populates a ChoiceBox of type String with every active {@link Classroom} in the {@link Center} 
		 * @param stage - the current stage
		 * @param director - a {@link Director}
		 * @return ChoiceBox
		 */
		private ChoiceBox<String> buildClassroomBox(Stage stage,Director director){
			ChoiceBox<String> viewBox = new ChoiceBox<String>();
		    viewBox.getItems().add("all classrooms");
		    Map<String,Classroom> classrooms = director.getClassrooms();
		    for(Classroom classroomCheck : classrooms.values()) {
		    		viewBox.getItems().add(classroomCheck.toString());
		    		if(classroomCheck.equals(this.classroom)) {viewBox.setValue(classroomCheck.toString());}
		    	}
		    viewBox.getStyleClass().add("choice-box-mini");
		    ChangeListener<String> changeListener = new ChangeListener<String>() {
				@Override
				public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
					if(!newValue.equals("all classrooms")) {
						Classroom classroom = null;
						Map<String,Classroom> classrooms = director.getClassrooms();
						for(Classroom classroomCheck : classrooms.values()) {if(classroomCheck.toString().equals(newValue)) {classroom = classroomCheck;}}
						if(classroom != null) {
							ScrollPane scrollPane = new ScrollPane(new FilterClassroomPane(stage,director,classroom));
							directorStudentWorkspaceLayout.setCenter(scrollPane);
							scrollPane.setFitToWidth(true);
						}
					}else {directorStudentWorkspaceLayout.setCenter(buildCenterPane(stage,director));}
				}
	        };
	        viewBox.getSelectionModel().selectedItemProperty().addListener(changeListener);
	        return viewBox;
		}
		
		/**
		 * Populates the GridPane with every {@link Student} in the {@link Classroom}
		 */
		private void populateClassroomStudents(Stage stage,Director director) {
			double rowIndex = ROW_INDEX;
			int column = COLUMN;
			List<String> studentsEnrolled = classroom.getStudentsEnrolled();
			for(String studentEnrolled : studentsEnrolled) {
				Student student = classroom.getStudent(studentEnrolled);
				String firstName = student.getFirstName(), lastName = student.getLastName(), birthDate = student.getBirthDate(), classroomType = classroom.getClassroomType();
				String buttonText = firstName + " " + lastName + "\n" + "Birth Date: " + birthDate + "\n" + "Classroom: " + classroomType;
				Button newButton = createButton(buttonText,null,0,300,500);
				newButton.setOnAction(e -> Platform.runLater(new ChangeScene(stage,new ModifyStudentInfo(stage,null,student,director))));
				int row = 0;
				boolean doesNumberEndInZero = doesNumberEndInZero(rowIndex);
				if(doesNumberEndInZero == false) { row = (int) Math.round(rowIndex); }
				else {row = (int)rowIndex;}
				determinePosition(this,newButton,row,column);
				rowIndex = rowIndex+0.5;
				column = column+1;
			}
		}
		
		@Override
		public void placeNodes(GridPane gridpane, List<Node> nodes) {}
		
	}
	
}

