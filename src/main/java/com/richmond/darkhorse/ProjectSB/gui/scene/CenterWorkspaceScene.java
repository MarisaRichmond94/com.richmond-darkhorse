package com.richmond.darkhorse.ProjectSB.gui.scene;
import java.util.Arrays;
import java.util.List;
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
import javafx.scene.Node;

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
		setBorderPaneCenterScroll(centerWorkspaceLayout,scrollPane,null,leftPane,topPane,bottomPane);
		centerWorkspaceLayout.getStylesheets().add("css/admin.css");
	}
	
	/**
	 * Builds the central GridPane for the BorderPane layout 
	 * @param stage - the stage of the current scene
	 * @param admin - the user 
	 * @return GridPane
	 */
	public GridPane buildCenterPane(Stage stage,Admin admin) {
		GridPane centerPane = new GridPane();
		setConstraints(centerPane,4,0,15,15,"gridpane");
		ImageView workspaceViewer = createImageWithFitHeight("images/workspace.png",150);
		Button addCenterButton = createButton(null,"images/center.png",200,300,500);
		addCenterButton.setOnAction(e -> {
			newAddCenter.display();
			Platform.runLater(new ChangeScene(stage,new CenterWorkspaceScene(stage,null,admin)));
		});
		List<Node> nodes = Arrays.asList(workspaceViewer,addCenterButton);
		placeNodes(centerPane,nodes);
		populateCenters(centerPane,stage,admin);
		return centerPane;
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
	 * Populates the GridPane with all of the @{Center}s that exist in the system
	 * @param gridpane - GridPane
	 * @param stage - the stage of the current scene
	 * @param admin - the user 
	 */
	public void populateCenters(GridPane gridpane,Stage stage,Admin admin) {
		Map<String,Center> centers = admin.getCenters();
		for(Center value : centers.values()) {
			String centerName = value.getCenterName(),licenseNumber = value.getLicenseNumber(),address = value.getAddress(),city = value.getCity(),county = value.getCounty();
			Button newButton = createButtonWithoutText(300,500);
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
	
}
