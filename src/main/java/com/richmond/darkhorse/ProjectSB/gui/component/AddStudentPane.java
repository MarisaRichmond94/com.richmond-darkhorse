package com.richmond.darkhorse.ProjectSB.gui.component;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.richmond.darkhorse.ProjectSB.Center;
import com.richmond.darkhorse.ProjectSB.Classroom;
import com.richmond.darkhorse.ProjectSB.Director;
import com.richmond.darkhorse.ProjectSB.Student;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.control.ToggleButton;

public class AddStudentPane extends GridPane{
	
	private Center center;
	private int column = 1;
	private double rowIndex = 0.5;
	private List<Student> studentsPressed = new ArrayList<Student>();
	
	public AddStudentPane(Classroom classroom,Director director) {
		this.center = classroom.getCenter(classroom.getCenterID());
		
		this.setVgap(10);
		this.setHgap(10);
		GridPane.setHalignment(this,HPos.CENTER);
		GridPane.setValignment(this,VPos.CENTER);
		ColumnConstraints columnOne = new ColumnConstraints();
		columnOne.setPercentWidth(50);
	    ColumnConstraints columnTwo = new ColumnConstraints();
	    columnTwo.setPercentWidth(50);
	    this.getColumnConstraints().addAll(columnOne,columnTwo);
	    this.getStyleClass().add("gridpane");
	    this.getStylesheets().add("addremovepane.css");
	    
	    	Map<String,Student> students = center.getStudents();
		for(Student student : students.values()) {
		    	if(student.getClassroom(student.getClassroomID()) == null || !student.getClassroom(student.getClassroomID()).equals(classroom)) {
		    		String firstName = student.getFirstName();
		    		String lastName = student.getLastName();
		    		String birthDate = student.getBirthDate();
		    		String currentClassroom = "N/A";
		    		if(student.getClassroom(student.getClassroomID()) != null) {currentClassroom = student.getClassroom(student.getClassroomID()).getClassroomType();}
		    		ToggleButton newButton = new ToggleButton();
		    		newButton.setText(firstName + " " + lastName + "\nbirthdate: " + birthDate + "\nclassroom: " + currentClassroom);
		    		newButton.getStyleClass().add("toggle-button");
		    		newButton.setPrefSize(500,300);
		    		newButton.setOnAction(e -> {
		    			if(newButton.isSelected() == true) {
		    				studentsPressed.add(student);
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
	}
	
	public void determinePosition(GridPane gridpane,ToggleButton button,int row,int column) {
		int columnIndex;
		if(column == 0) {
			this.add(button,0,0);
			GridPane.setHalignment(button,HPos.CENTER);
		}else {
			boolean isIndexEven = isIntEven(column);
			if(isIndexEven == true) {
				columnIndex = 1;
			}else {
				columnIndex = 0;
			}
			this.add(button,columnIndex,row);
			GridPane.setHalignment(button,HPos.CENTER);
			
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
	
	public List<Student> getStudentsAdded(){
		return studentsPressed;
	}
	
}
