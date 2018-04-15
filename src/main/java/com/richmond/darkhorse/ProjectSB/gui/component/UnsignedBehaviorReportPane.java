package com.richmond.darkhorse.ProjectSB.gui.component;
import java.util.HashMap;
import java.util.Map;
import com.richmond.darkhorse.ProjectSB.BehaviorReport;
import com.richmond.darkhorse.ProjectSB.Director;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;

public class UnsignedBehaviorReportPane extends GridPane {
	
	private int column = 1;
	private double rowIndex = 0.5;
	private Map<String,BehaviorReport> selectedBehaviorReports = new HashMap<String,BehaviorReport>();
	
	public UnsignedBehaviorReportPane(Director director,Map<String,BehaviorReport> unsignedBehaviorReports) {
		
		for(BehaviorReport behaviorReport : unsignedBehaviorReports.values()) {
			String student = behaviorReport.getStudentName();
			String date = behaviorReport.getStringDate();
			String time = behaviorReport.getStringTime();
			ToggleButton newButton = new ToggleButton();
			newButton.setText("Student: " + student + "\nDate: " + date + "\nTime: " + time);
			newButton.setPrefSize(500,300);
			newButton.getStyleClass().add("toggle-button");
			newButton.setOnAction(e -> {
	    			if(newButton.isSelected() == true) {
	    				selectedBehaviorReports.put(behaviorReport.getDocumentID(),behaviorReport);
	    			}else if(newButton.isSelected() == false) {
	    				selectedBehaviorReports.remove(behaviorReport.getDocumentID());
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
	    this.getStylesheets().add("directormailbox.css");
	}
	
	public Map<String,BehaviorReport> getSelectedBehaviorReports(){
		return selectedBehaviorReports;
	}
	
	public void determinePosition(GridPane gridpane,ToggleButton button,int row,int column) {
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
