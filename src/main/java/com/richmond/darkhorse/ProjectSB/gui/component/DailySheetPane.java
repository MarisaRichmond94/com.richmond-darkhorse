package com.richmond.darkhorse.ProjectSB.gui.component;
import java.util.List;
import java.util.Map;
import com.richmond.darkhorse.ProjectSB.DailySheet;
import com.richmond.darkhorse.ProjectSB.Director;
import com.richmond.darkhorse.ProjectSB.Student;
import javafx.scene.layout.GridPane;
import javafx.scene.Node;
import javafx.scene.control.Button;

public class DailySheetPane extends GridPane implements DirectorLayout{
	
	private int column = 1;
	private double rowIndex = 0.5;
	
	public DailySheetPane(Director director,Student student) {
		buildGridPane(director,student);
	}
	
	/**
	 * Builds the GridPane for the DailySheetPane
	 * @param director - the current user
	 * @param student - the current {@link Student}
	 */
	private void buildGridPane(Director director,Student student) {
		setConstraints(this,2,0,10,10,"gridpane");
	    this.getStylesheets().add("css/director.css");
	    populateDailySheets(director,student);
	}
	
	/**
	 * Populates the GridPane with {@link DailySheet}s belonging to the {@link Student}
	 * @param director - the current user
	 * @param student - the current {@link Student}
	 */
	private void populateDailySheets(Director diretor,Student student) {
		Map<String,DailySheet> dailysheets = student.getRecord().getDailySheets();
	    for(DailySheet dailySheet : dailysheets.values()) {
	    		String studentName = dailySheet.getStudentName(), date = dailySheet.getDate().toString(), buttonText = studentName + "\ndate:" + date;
	    		Button newButton = createButton(buttonText,null,0,300,300);
	    		newButton.setOnAction(e -> {
	    			ViewDailySheet viewDailySheet = new ViewDailySheet(student,dailySheet);
	    			viewDailySheet.display();
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
