package com.richmond.darkhorse.ProjectSB.gui.component;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.richmond.darkhorse.ProjectSB.Classroom;
import com.richmond.darkhorse.ProjectSB.Director;
import com.richmond.darkhorse.ProjectSB.StaffMember;
import com.richmond.darkhorse.ProjectSB.Student;
import com.richmond.darkhorse.ProjectSB.Teacher;
import javafx.geometry.HPos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.ScrollPane;

public class ClassroomStatus {

	//TODO debug this once you get to networking, because it has a phantom student and refuses to load students that actually exist
	
	private Director director;
	private Classroom classroom;
	private Map<String,Student> allStudents = new HashMap<String,Student>();
	
	public ClassroomStatus(Director director,Classroom classroom) {
		this.director = director;
		this.classroom = classroom;
		this.allStudents = director.getStudents();
	}
	
	public void display() {
		Stage stage = new Stage();
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.setTitle(classroom.toString() + " status");
		
		//Grid Pane
		GridPane classroomStatusLayout = new GridPane();
		classroomStatusLayout.setVgap(10);
		classroomStatusLayout.setHgap(10);
		ColumnConstraints columnOne = new ColumnConstraints();
		columnOne.setPercentWidth(16.6);
	    ColumnConstraints columnTwo = new ColumnConstraints();
	    columnTwo.setPercentWidth(16.6);
	    ColumnConstraints columnThree = new ColumnConstraints();
	    columnThree.setPercentWidth(16.6);
	    ColumnConstraints columnFour = new ColumnConstraints();
	    columnFour.setPercentWidth(16.6);
	    ColumnConstraints columnFive = new ColumnConstraints();
	    columnFive.setPercentWidth(16.6);
	    ColumnConstraints columnSix = new ColumnConstraints();
	    columnSix.setPercentWidth(16.6);
	    classroomStatusLayout.getColumnConstraints().addAll(columnOne,columnTwo,columnThree,columnFour,columnFive,columnSix);
	    classroomStatusLayout.getStyleClass().add("gridpane");
	    classroomStatusLayout.getStylesheets().add("directorhome.css");
	    
	    Label title = new Label(classroom.toString());
	    title.getStyleClass().add("title");
	    Label teachers = new Label("Teacher(s):");
	    teachers.getStyleClass().add("label");
	    List<String> teachersPresent = classroom.getTeachersPresent();
	    Map<String,StaffMember> staffMembers = director.getStaffMembers();
	    String teachersPresentString = null;
	    if(classroom.getTeachersPresent().size() == 0 || classroom.getTeachersPresent() == null) {
	    		teachersPresentString = "no active teachers";
	    }else {
		    for(String teacherPresent : teachersPresent) {
		    		Teacher teacher = null;
		    		if(staffMembers.containsKey(teacherPresent)) {
		    			teacher = (Teacher) staffMembers.get(teacherPresent);
		    			teachersPresentString = teachersPresentString + " " + teacher.getFirstName() + " " + teacher.getLastName();
		    		}
		    }
	    }
	    Label teachersPresentLabel = new Label(teachersPresentString);
	    teachersPresentLabel.getStyleClass().add("label");
	    
	    GridPane studentsPresentGrid = new GridPane();
	    Label presentStudentsLabel = new Label("Students Present");
	    presentStudentsLabel.getStyleClass().add("subtitle");
	    studentsPresentGrid.getStyleClass().add("gridpane");
	    studentsPresentGrid.add(presentStudentsLabel,0,0);
	    GridPane.setHalignment(presentStudentsLabel,HPos.CENTER);
	    GridPane.setConstraints(presentStudentsLabel,0,0,2,1);
	    List<String> studentsPresentList = classroom.getStudentsPresent();
	    if(studentsPresentList.size() >= 1) {
		    int rowIndex = 1;
			for(String presentID : studentsPresentList) {
				if(allStudents.containsKey(presentID)) {
					Student theStudent = allStudents.get(presentID);
					Label newLabel = new Label(theStudent.toString());
					newLabel.getStyleClass().add("label");
					studentsPresentGrid.add(newLabel,0, rowIndex);
					GridPane.setHalignment(newLabel,HPos.CENTER);
					GridPane.setConstraints(newLabel,0,rowIndex,2,1);
					rowIndex++;
				}
			}
	    }else {
	    		Label noStudents = new Label("no active student(s)");
	    		noStudents.getStyleClass().add("label");
	    		studentsPresentGrid.add(noStudents,0, 1);
			GridPane.setHalignment(noStudents,HPos.CENTER);
			GridPane.setConstraints(noStudents,0,1,2,1);
	    }
	    ScrollPane presentScrollPane = new ScrollPane(studentsPresentGrid);
	    presentScrollPane.getStyleClass().add("scrollpane");
	    presentScrollPane.setFitToHeight(true);
	    presentScrollPane.setFitToWidth(true);

	    List<String> expected = classroom.getStudentsExpected();
	    List<String> present = classroom.getStudentsPresent();
	    Map<String,Student> presentMap = new HashMap<String,Student>();
	    for(String presentStudent : present) {
	    		if(allStudents.containsKey(presentStudent)) {presentMap.put(presentStudent,allStudents.get(presentStudent));}
	    }
	    List<String> stillExpected = new ArrayList<String>();
	    for(String studentExpected : expected) {
	    		if(!presentMap.containsKey(studentExpected)) {stillExpected.add(studentExpected);}
	    }
	   
	    GridPane studentsExpectedGrid = new GridPane();
	    Label expectedStudentsLabel = new Label("Students Expected");
	    expectedStudentsLabel.getStyleClass().add("subtitle");
	    studentsExpectedGrid.getStyleClass().add("gridpane");
	    studentsExpectedGrid.add(expectedStudentsLabel,0,0);
	    GridPane.setHalignment(expectedStudentsLabel,HPos.CENTER);
	    GridPane.setConstraints(expectedStudentsLabel,0,0,2,1);
		int row = 1;
		for(String expectedID : stillExpected) {
			if(allStudents.containsKey(expectedID)) {
				Student theStudent = allStudents.get(expectedID);
				Label newLabel = new Label(theStudent.toString());
				newLabel.getStyleClass().add("label");
				studentsExpectedGrid.add(newLabel,0, row);
				GridPane.setHalignment(newLabel,HPos.CENTER);
				GridPane.setConstraints(newLabel,0,row,2,1);
				row++;
			}
		}
	    ScrollPane expectedScrollPane = new ScrollPane(studentsExpectedGrid);
	    expectedScrollPane.getStyleClass().add("scrollpane");
	    expectedScrollPane.setFitToHeight(true);
	    expectedScrollPane.setFitToWidth(true);
	    
	    Label totalPresent = new Label("Total: " + classroom.getStudentsPresent().size() + "");
	    totalPresent.getStyleClass().add("label");
	    Label totalExpected = new Label("Total: " + stillExpected.size() + "");
	    totalExpected.getStyleClass().add("label");
	    
	    classroomStatusLayout.add(title,2,0);
	    GridPane.setHalignment(title,HPos.CENTER);
	    GridPane.setConstraints(title,2,0,2,1);
	    classroomStatusLayout.add(teachers,0,1);
	    GridPane.setHalignment(teachers,HPos.RIGHT);
	    GridPane.setConstraints(teachers,0,1,2,1);
	    classroomStatusLayout.add(teachersPresentLabel,2,	1);
	    GridPane.setHalignment(teachersPresentLabel,HPos.LEFT);
	    GridPane.setConstraints(teachersPresentLabel,2,1,4,1);
	    classroomStatusLayout.add(presentScrollPane,0,2);
	    GridPane.setHalignment(presentScrollPane,HPos.CENTER);
	    GridPane.setConstraints(presentScrollPane,0,2,3,4);
	    classroomStatusLayout.add(expectedScrollPane,3,2);
	    GridPane.setHalignment(expectedScrollPane,HPos.CENTER);
	    GridPane.setConstraints(expectedScrollPane,3,2,3,4);
	    classroomStatusLayout.add(totalPresent,0,7);
	    GridPane.setHalignment(totalPresent,HPos.CENTER);
	    GridPane.setConstraints(totalPresent,0,7,3,1);
	    classroomStatusLayout.add(totalExpected,3,7);
	    GridPane.setHalignment(totalExpected,HPos.CENTER);
	    GridPane.setConstraints(totalExpected,3,7,3,1);
		
		Scene classroomLayoutScene = new Scene(classroomStatusLayout);
		stage.setScene(classroomLayoutScene);
		stage.showAndWait();
	}
	
}
