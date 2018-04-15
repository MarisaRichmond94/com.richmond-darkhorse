package com.richmond.darkhorse.ProjectSB.gui.scene;
import java.util.Map;
import com.richmond.darkhorse.ProjectSB.Admin;
import com.richmond.darkhorse.ProjectSB.Center;
import com.richmond.darkhorse.ProjectSB.gui.component.AddCenter;
import com.richmond.darkhorse.ProjectSB.gui.component.AdminLayout;
import com.richmond.darkhorse.ProjectSB.gui.component.ModifyCenter;
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

public class CenterWorkspaceScene extends Scene implements AdminLayout {
	
	private AddCenter newAddCenter;
	private double rowIndex = 1.0;
	private int column = 0;
	
	public CenterWorkspaceScene(Stage stage,Scene nextScene,Admin admin) {
		this(stage,new BorderPane(),nextScene,admin);
		this.newAddCenter = new AddCenter(admin);
	}
	
	public CenterWorkspaceScene(Stage stage,BorderPane layout,Scene nextScene,Admin admin) {
		super(layout);
		HBox topPane = buildTopPane(stage,admin);
		HBox bottomPane = buildBottomPane();
		VBox leftPane = buildLeftPane(stage,admin);
		GridPane centerPane = buildCenterPane(stage,admin);
		ScrollPane scrollPane = new ScrollPane(centerPane);
		scrollPane.setFitToWidth(true);
		BorderPane centerWorkspaceLayout = layout;
		setBorderPaneWithCenterScrollPane(centerWorkspaceLayout,scrollPane,null,leftPane,topPane,bottomPane);
		centerWorkspaceLayout.getStylesheets().add("centerworkspace.css");
	}
	
	/**
	 * Builds the central GridPane for the BorderPane layout 
	 * @param stage - the stage of the current scene
	 * @param admin - the user 
	 * @return GridPane
	 */
	public GridPane buildCenterPane(Stage stage,Admin admin) {
		GridPane centerPane = new GridPane();
		setConstraints(centerPane,4,0,15,15);
		ImageView workspaceViewer = createImageWithFitHeight("workspace.png",150);
		Button addCenterButton = createButton(null,"addcenter.png",200,300,400);
		addCenterButton.setOnAction(e -> {
			newAddCenter.display();
			Platform.runLater(new ChangeScene(stage,new CenterWorkspaceScene(stage,null,admin)));
		});
		addCenters(centerPane,stage,admin);
		placeNodeSpan(centerPane,workspaceViewer,1,0,2,1,"center",null);
		placeNodeSpan(centerPane,addCenterButton,0,1,2,1,"center",null);
		return centerPane;
	}
	
	/**
	 * Populates the GridPane with all of the @{Center}s that exist in the system
	 * @param gridpane - GridPane
	 * @param stage - the stage of the current scene
	 * @param admin - the user 
	 */
	public void addCenters(GridPane gridpane,Stage stage,Admin admin) {
		Map<String,Center> centers = admin.getCenters();
		for(Center value : centers.values()) {
			String centerName = value.getCenterName(),licenseNumber = value.getLicenseNumber(),address = value.getAddress(),city = value.getCity(),county = value.getCounty();
			Button newButton = createButtonWithoutText(300,400);
			newButton.setText(centerName + "\n" + licenseNumber + "\n" + "Address: " + address + "\n" + "City: " + city + "\n" + "County: " + county);
			newButton.setOnAction(e -> {
				ModifyCenter modifyCenter = new ModifyCenter(admin,value);
				modifyCenter.display();
				newButton.setText(value.getCenterName() + "\n" + value.getLicenseName() + "\n" + value.getLicenseNumber() + "\n" + value.getCenterID() + "\n" + value.getAddress() + "\n" + value.getCity() + "\n" + value.getCity());
				Platform.runLater(new ChangeScene(stage,new CenterWorkspaceScene(stage,null,admin)));
			});
			int row = 0;
			boolean doesNumberEndInZero = doesNumberEndInZero(rowIndex);
			if(doesNumberEndInZero == false) {row = (int) Math.round(rowIndex);
			}else {row = (int)rowIndex;}
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
	public void determinePosition(GridPane gridpane,Button button,int row,int column) {
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
	public boolean isIntEven(int n) {
		if( n % 2 == 0) { return true; }
		return false;
	}
	
	/**
	 * Determines whether or not the given double ends in 0
	 * @param rowIndex - double representing the row number
	 * @return true if the double ends in 0 and false if the number does not end in 0
	 */
	public boolean doesNumberEndInZero(double rowIndex) {
		if (Math.abs(rowIndex - Math.rint(rowIndex)) == 0.5) { return false; }
		return true;
	}
	
}
