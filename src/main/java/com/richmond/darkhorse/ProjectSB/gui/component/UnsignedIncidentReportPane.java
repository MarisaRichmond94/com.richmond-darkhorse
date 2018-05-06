package com.richmond.darkhorse.ProjectSB.gui.component;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.richmond.darkhorse.ProjectSB.IncidentReport;
import com.richmond.darkhorse.ProjectSB.Director;
import javafx.scene.Node;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.GridPane;

public class UnsignedIncidentReportPane extends GridPane implements DirectorLayout{

	private int column = 1;
	private double rowIndex = 0.5;
	private Map<String,IncidentReport> selectedIncidentReports = new HashMap<String,IncidentReport>();
	
	public UnsignedIncidentReportPane(Director director,Map<String,IncidentReport> unsignedIncidentReports) {
		setConstraints(this,2,0,10,10,"gridpane");
		this.getStylesheets().add("css/director.css");
		for(IncidentReport incidentReport : unsignedIncidentReports.values()) {
			String student = incidentReport.getStudentName(), date = incidentReport.getStringDate(), time = incidentReport.getStringTime();
			ToggleButton newButton = new ToggleButton();
			newButton.setText("Student: " + student + "\nDate: " + date + "\nTime: " + time);
			newButton.setPrefSize(500,300);
			newButton.getStyleClass().add("toggle-button");
			newButton.setOnAction(e -> {
	    			if(newButton.isSelected() == true) {selectedIncidentReports.put(incidentReport.getDocumentID(),incidentReport);}
	    			else if(newButton.isSelected() == false) {selectedIncidentReports.remove(incidentReport.getDocumentID());}
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
	
	public Map<String,IncidentReport> getSelectedIncidentReports(){
		return selectedIncidentReports;
	}

	@Override
	public void placeNodes(GridPane gridpane, List<Node> nodes) {}

}
