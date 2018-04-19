package com.richmond.darkhorse.ProjectSB.gui.scene;
import java.util.ArrayList;
import java.util.Arrays;
import javafx.scene.Node;
import java.util.List;
import java.util.Map;
import com.richmond.darkhorse.ProjectSB.Admin;
import com.richmond.darkhorse.ProjectSB.Center;
import com.richmond.darkhorse.ProjectSB.Director;
import com.richmond.darkhorse.ProjectSB.StaffMember;
import com.richmond.darkhorse.ProjectSB.gui.component.AddDirector;
import com.richmond.darkhorse.ProjectSB.gui.component.AdminLayout;
import com.richmond.darkhorse.ProjectSB.gui.component.ModifyDirector;
import com.richmond.darkhorse.ProjectSB.middleman.ChangeScene;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.control.ScrollPane;

public class DirectorWorkspaceScene extends Scene implements AdminLayout{
	
	private AddDirector newAddDirector;
	private double rowIndex = 1.0;
	private int column = 0;
	
	public DirectorWorkspaceScene(Stage stage,Scene nextScene,Admin admin) {
		this(stage,new BorderPane(),nextScene,admin);
		this.newAddDirector = new AddDirector(admin);
	}
	
	public DirectorWorkspaceScene(Stage stage,BorderPane layout,Scene nextScene,Admin admin) {
		super(layout);
		HBox topPane = buildTopPane(stage,admin);
		HBox bottomPane = buildBottomPane();
		VBox leftPane = buildLeftPane(stage,admin);
		ScrollPane scrollPane = new ScrollPane(buildCenterPane(stage,admin));
		scrollPane.setFitToWidth(true);
		BorderPane adminHomeLayout = layout;
		setBorderPaneCenterScroll(adminHomeLayout,scrollPane,null,leftPane,topPane,bottomPane);
		adminHomeLayout.getStylesheets().add("css/admin.css");
	}
	
	/**
	 * Builds the central GridPane for the overall BorderPane layout
	 * @param stage - the current stage
	 * @param admin - the current user (admininstrator)
	 * @return a fully populated GridPane
	 */
	private GridPane buildCenterPane(Stage stage,Admin admin) {
		GridPane gridpane = new GridPane();
		setConstraints(gridpane,4,0,15,15,"gridpane");
		ImageView workspaceViewer = createImageWithFitHeight("images/workspace.png",150);
		Button addDirectorButton = createButton(null,"images/director.png",200,300,400);
		addDirectorButton.setOnAction(e -> {
			newAddDirector.display();
			Platform.runLater(new ChangeScene(stage,new DirectorWorkspaceScene(stage,null,admin)));
		});
		List<Node> nodes = new ArrayList<>();
	    nodes.addAll(Arrays.asList(workspaceViewer,addDirectorButton));
	    placeNodes(gridpane,nodes);
		populateDirectors(gridpane,stage,admin);
		return gridpane;
	}
	
	/**
	 * Places all of the nodes in the list in the given GridPane
	 * @param gridpane - GridPane layout
	 * @param nodes - a list of nodes to be added to the GridPane
	 */
	public void placeNodes(GridPane gridpane,List<Node> nodes) {
		placeNodeSpan(gridpane,nodes.get(0),1,0,2,1,"center",null);
		placeNodeSpan(gridpane,nodes.get(1),0,1,2,1,"center",null);
	}
	
	/**
	 * populates any @{Director}s in the system
	 * @param gridpane - the GridPane being populated
	 * @param stage - the current stage
	 * @param admin - the current user (administrator)
	 */
	private void populateDirectors(GridPane gridpane,Stage stage,Admin admin) {
		Map<String,StaffMember> staffMembers = admin.getStaffMembers();
		List<Director> directors = pullDirectors(staffMembers);
		for(Director theDirector : directors) {
			Center theCenter = theDirector.getCenter(theDirector.getCenterID());
			String firstName = theDirector.getFirstName(), lastName = theDirector.getLastName(), centerString = String.valueOf(theCenter);
			String buttonText = "" + firstName + " " + lastName + "\n" + centerString + "";
			Button newButton = createButton(buttonText,null,0,300,400);
			newButton.setOnAction(e -> {
				ModifyDirector modifyDirector = new ModifyDirector(admin,(Director)theDirector);
				modifyDirector.display();
				newButton.setText(theDirector.getFirstName() + "\n" + theDirector.getLastName() + "\n" + theDirector.getCenter(theDirector.getCenterID()));
				Platform.runLater(new ChangeScene(stage,new DirectorWorkspaceScene(stage,null,admin)));
			});
			int row = 0;
			boolean doesNumberEndInZero = doesNumberEndInZero(rowIndex);
			if(doesNumberEndInZero == false) {row = (int) Math.round(rowIndex);}
			else {row = (int)rowIndex;}
			determinePosition(gridpane,newButton,row,column);
			rowIndex = rowIndex+0.5;
			column++;	
		}
	}
	
	/**
	 * Determines which position to place the new @{Center} button
	 * @param gridpane - GridPane
	 * @param button - button representing a @{Center}
	 * @param row 
	 * @param column
	 */
	private void determinePosition(GridPane gridpane,Button button,int row,int column) {
		int columnIndex;
		if(column == 0) {placeNodeSpan(gridpane,button,2,1,2,1,"center",null);}
		else {
			boolean isIndexEven = isIntEven(column);
			if(isIndexEven == true) {columnIndex = 2;
			}else {columnIndex = 0;}
			placeNodeSpan(gridpane,button,columnIndex,row,2,1,"center",null);
		}
	}
	
	/**
	 * Determines whether or not the given number is even or odd
	 * @param n - the number being analyzed
	 * @return true if the number (n) is even and false if the number (n) is odd
	 */
	private boolean isIntEven(int n) {
		if( n % 2 == 0) { return true; }
		return false;
	}
	
	/**
	 * Determines whether or not the given double ends in 0
	 * @param rowIndex - double representing the row number
	 * @return true if the double ends in 0 and false if the number does not end in 0
	 */
	private boolean doesNumberEndInZero(double rowIndex) {
		if (Math.abs(rowIndex - Math.rint(rowIndex)) == 0.5) { return false; }
		return true;
	}
	
	/**
	 * Goes through a list of all of the @{StaffMember}s in the system and pulls out any @{Director}s
	 * @param staffMembers - a list of all @{StaffMember}s in the system
	 * @return a list of all @{Director}s in the system
	 */
	private List<Director> pullDirectors(Map<String,StaffMember> staffMembers){
		List<Director> directors = new ArrayList<Director>();
		for(StaffMember staffMember: staffMembers.values()) {
			String title = staffMember.getTitle();
			if(title.equals("Director")) {directors.add((Director) staffMember);}
		}
		return directors;
	}
	
}
