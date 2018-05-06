package com.richmond.darkhorse.ProjectSB.gui.component;
import java.util.ArrayList;
import java.util.List;
import com.richmond.darkhorse.ProjectSB.Classroom;
import com.richmond.darkhorse.ProjectSB.Director;
import com.richmond.darkhorse.ProjectSB.Student;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.layout.GridPane;
import javafx.scene.Node;
import javafx.scene.control.ToggleButton;

public class RemoveStudentPane extends GridPane implements DirectorLayout{
	
	private int column = 1;
	private double rowIndex = 0.5;
	private List<Student> studentsPressed = new ArrayList<Student>();
	
	public RemoveStudentPane(Classroom classroom,Director director) {
		buildClassroom(classroom);
	}
	
	/**
	 * Builds the GridPane for {@link RemoveStudentPane}
	 * @param classroom - a {@link Classroom}
	 */
	private void buildClassroom(Classroom classroom) {
		setConstraints(this,2,0,10,10,"gridpane");
		GridPane.setHalignment(this,HPos.CENTER);
		GridPane.setValignment(this,VPos.CENTER);
	    this.getStylesheets().add("css/director.css");
	    populateStudents(classroom);
	}
	
	/**
	 * Populates the GridPane will any {@link Student}s who do not have a {@link Classroom}
	 * @param classroom - a {@link Classroom}
	 */
	private void populateStudents(Classroom classroom) {
		List<String> students = classroom.getStudentsEnrolled();
		for(String studentID : students) {
			Student student = classroom.getStudent(studentID);
		    	String firstName = student.getFirstName(),lastName = student.getLastName(),birthDate = student.getBirthDate(),currentClassroom = classroom.getClassroomType();
		    	ToggleButton newButton = new ToggleButton();
		    	newButton.setText(firstName + " " + lastName + "\nbirthdate: " + birthDate + "\nclassroom: " + currentClassroom);
		    	newButton.getStyleClass().add("toggle-button");
		    	newButton.setPrefSize(500,300);
		    	newButton.setOnAction(e -> {
		    		if(newButton.isSelected() == true) {studentsPressed.add(student);
		    		}else if(newButton.isSelected() == false) {
		    			if(studentsPressed.size() >= 1) {
		    				Student removeRequest = null;
		    				for(Student studentCheck : studentsPressed) {
		    					if(studentCheck.equals(student)) {removeRequest = studentCheck;}
		    				}
		    				if(removeRequest != null) {studentsPressed.remove(removeRequest);}
		    			}
		    		}
		    	});
		    	int row = 0;
		    	boolean doesNumberEndInZero = doesNumberEndInZero(rowIndex);
		    	if(doesNumberEndInZero == false) {row = (int) Math.round(rowIndex);}
		    	else {row = (int)rowIndex;}
		    	determinePosition(this,newButton,row,column);
		    	rowIndex = rowIndex+0.5;
		    	column++;
		}
	}
	
	/**
	 * Determines the position of a ToggleButton
	 * @param gridpane - GridPane
	 * @param button - ToggleButton
	 * @param row - integer
	 * @param column - integer
	 */
	private void determinePosition(GridPane gridpane,ToggleButton button,int row,int column) {
		int columnIndex;
		if(column == 0) {placeNode(this,button,0,0,"center",null);}
		else {
			boolean isIndexEven = isIntEven(column);
			if(isIndexEven == true) {columnIndex = 1;}
			else {columnIndex = 0;}
			placeNode(this,button,columnIndex,row,"center",null);
		}
	}
	
	/**
	 * Determines whether or not the given number is odd
	 */
	public boolean isIntEven(int n) {
		if( n % 2 == 0) {return true;}
		return false;
	}
	
	/**
	 * determines whether or not a number ends in zero
	 */
	public boolean doesNumberEndInZero(double rowIndex) {
		if (Math.abs(rowIndex - Math.rint(rowIndex)) == 0.5) {return false;}
		return true;
	}
	
	public List<Student> getStudentsRemoved(){
		return studentsPressed;
	}

	@Override
	public void placeNodes(GridPane gridpane, List<Node> nodes) {}
	
}
