package com.richmond.darkhorse.ProjectSB.gui.component;
import java.util.List;
import java.util.Map;
import com.richmond.darkhorse.ProjectSB.BehaviorReport;
import com.richmond.darkhorse.ProjectSB.Director;
import com.richmond.darkhorse.ProjectSB.Student;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

public class BehaviorReportPane extends GridPane implements DirectorLayout{
	
	private int column = 1;
	private double rowIndex = 0.5;
	
	public BehaviorReportPane(Director director,Student student) {
		buildGridPane(director,student);
	}
	
	/**
	 * Builds the default GridPane
	 * @param director - the current user
	 * @param student - the current {@link Student}
	 */
	private void buildGridPane(Director director,Student student) {
		setConstraints(this,2,0,10,10,"gridpane");
	    this.getStylesheets().add("css/director.css");
	    populateBehaviorReports(director,student);
	}
	
	/**
	 * Populates the GridPane with any {@link BehaviorReport}s belonging to the {@link Student}
	 * @param director - the current user
	 * @param student - the current {@link Student}
	 */
	private void populateBehaviorReports(Director director,Student student) {
		Map<String,BehaviorReport> behaviorReports = student.getRecord().getBehaviorReports();
	    for(BehaviorReport behaviorReport : behaviorReports.values()) {
	    		String studentName = behaviorReport.getStudent(student.getStudentID()).getFirstName() + " " + behaviorReport.getStudent(student.getStudentID()).getLastName(), date = behaviorReport.getDate().toString(), buttonText = studentName + "\ndate:" + date;
	    		Button newButton = createButton(buttonText,null,0,300,300);
	    		newButton.setOnAction(e -> {
	    			ViewBehaviorReport viewBehaviorReport = new ViewBehaviorReport(director,student,behaviorReport);
	    			viewBehaviorReport.display();
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
	
	@Override
	public void placeNodes(GridPane gridpane, List<Node> nodes) {}

}
