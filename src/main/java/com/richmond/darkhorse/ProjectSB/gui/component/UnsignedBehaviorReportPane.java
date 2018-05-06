package com.richmond.darkhorse.ProjectSB.gui.component;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.richmond.darkhorse.ProjectSB.BehaviorReport;
import com.richmond.darkhorse.ProjectSB.Director;
import javafx.scene.Node;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.GridPane;

public class UnsignedBehaviorReportPane extends GridPane implements DirectorLayout{
	
	private int column = 1;
	private double rowIndex = 0.5;
	private Map<String,BehaviorReport> selectedBehaviorReports = new HashMap<String,BehaviorReport>();
	
	public UnsignedBehaviorReportPane(Director director,Map<String,BehaviorReport> unsignedBehaviorReports) {
		setConstraints(this,2,0,10,10,"gridpane");
		this.getStylesheets().add("css/director.css");
		for(BehaviorReport behaviorReport : unsignedBehaviorReports.values()) {
			String student = behaviorReport.getStudentName(), date = behaviorReport.getStringDate(), time = behaviorReport.getStringTime();
			ToggleButton newButton = new ToggleButton();
			newButton.setText("Student: " + student + "\nDate: " + date + "\nTime: " + time);
			newButton.setPrefSize(500,300);
			newButton.getStyleClass().add("toggle-button");
			newButton.setOnAction(e -> {
	    			if(newButton.isSelected() == true) {selectedBehaviorReports.put(behaviorReport.getDocumentID(),behaviorReport);}
	    			else if(newButton.isSelected() == false) {selectedBehaviorReports.remove(behaviorReport.getDocumentID());}
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
	 * Determines the position of the provided ToggleButton
	 * @param gridpane - GridPane
	 * @param button - Button
	 * @param row - integer
	 * @param column - integer
	 */
	private void determinePosition(GridPane gridpane,ToggleButton button,int row,int column) {
		int columnIndex;
		if(column == 0) {
			placeNode(this,button,0,0,"center",null);
		}else {
			boolean isIndexEven = isIntEven(column);
			if(isIndexEven == true) {columnIndex = 1;
			}else {columnIndex = 0;}
			placeNode(this,button,columnIndex,row,"center",null);
		}
	}
	
	/**
	 * Determines if an integer is even
	 */
	public boolean isIntEven(int n) {
		if( n % 2 == 0) {return true;}
		return false;
	}
	
	/**
	 * Determines if a number ends in zero
	 */
	public boolean doesNumberEndInZero(double rowIndex) {
		if (Math.abs(rowIndex - Math.rint(rowIndex)) == 0.5) {return false;}
		return true;
	}
	
	public Map<String,BehaviorReport> getSelectedBehaviorReports(){
		return selectedBehaviorReports;
	}

	@Override
	public void placeNodes(GridPane gridpane, List<Node> nodes) {}

}
