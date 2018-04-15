package com.richmond.darkhorse.ProjectSB.gui.component;
import java.util.Map;
import com.richmond.darkhorse.ProjectSB.Director;
import com.richmond.darkhorse.ProjectSB.IncidentReport;
import com.richmond.darkhorse.ProjectSB.Student;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;

//Note: This cannot be tested until the teacher has been created 

public class IncidentReportPane extends GridPane{
	
	private int column = 1;
	private double rowIndex = 0.5;
	
	public IncidentReportPane(Director director,Student student) {
		
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
	    this.getStylesheets().add("modifystudentinfo.css");
	    
	    Map<String,IncidentReport> incidentReports = student.getRecord().getIncidentReports();
	    for(IncidentReport incidentReport : incidentReports.values()) {
	    		String studentName = incidentReport.getStudentName();
	    		String date = incidentReport.getDate().toString();
	    		Button newButton = new Button();
	    		newButton.setText(studentName + "\ndate:" + date);
	    		newButton.getStyleClass().add("button");
	    		newButton.setPrefSize(300,300);
	    		newButton.setOnAction(e -> {
	    			ViewIncidentReport viewIncidentReport = new ViewIncidentReport(student,incidentReport);
	    			viewIncidentReport.display();
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
	    
	public void determinePosition(GridPane gridpane,Button button,int row,int column) {
		int columnIndex;
		if(column == 0) {
			this.add(button,0,0);
			GridPane.setHalignment(button,HPos.CENTER);
		}else {
			boolean isIndexEven = isIntEven(column);
			if(isIndexEven == true) {columnIndex = 1;
			}else {columnIndex = 0;}
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

}
