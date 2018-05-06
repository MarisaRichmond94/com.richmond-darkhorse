package com.richmond.darkhorse.ProjectSB.gui.component;
import java.util.List;

import com.richmond.darkhorse.ProjectSB.Teacher;
import com.richmond.darkhorse.ProjectSB.gui.scene.TeacherDashboard;
import com.richmond.darkhorse.ProjectSB.gui.scene.TeacherHome;
import com.richmond.darkhorse.ProjectSB.gui.scene.TeacherReportWorkspace;
import com.richmond.darkhorse.ProjectSB.gui.scene.TeacherSchedule;
import com.richmond.darkhorse.ProjectSB.gui.scene.TeacherStudentWorkspace;
import com.richmond.darkhorse.ProjectSB.middleman.ChangeScene;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public interface TeacherLayout extends SceneFormatter,CoreFunctions{
	
	/**
	 * Builds an HBox, which serves as the top pane in the default BorderPane layout for a director account
	 * @param stage - the current program stage
	 * @param teacher - the account user 
	 * @return an HBox pane 
	 */
	public default HBox buildTopPane(Stage stage,Teacher teacher) {
		HBox topPane = new HBox();
		ImageView logoViewer = createImageWithFitHeight("images/logo.png",250);
		ImageButton homeButton = new ImageButton(createImageWithFitHeight("images/home.png",125));
		homeButton.setOnAction(e -> Platform.runLater(new ChangeScene(stage,new TeacherHome(stage,null,teacher))));
		ImageButton logoutButton = new ImageButton(createImageWithFitHeight("images/logout.png",125));
		logoutButton.setOnAction(e -> saveInit(stage));
		VBox homeButtonBox = buildButtonWithLabel(homeButton,createLabel("home","text"));
		VBox logoutButtonBox = buildButtonWithLabel(logoutButton,createLabel("logout","text"));
		topPane.setAlignment(Pos.CENTER);
		topPane.getChildren().addAll(homeButtonBox,logoViewer,logoutButtonBox);
		topPane.getStyleClass().add("toppane");
		return topPane;
	}
	
	/**
	 * Builds a button with a label beneath it
	 * @param button - an ImageButton
	 * @param label - the desired label for the button
	 * @return a centered VBox with the button and the label 
	 */
	public default VBox buildButtonWithLabel(ImageButton button,Label label) {
		VBox labeledButton = new VBox();
		labeledButton.setAlignment(Pos.CENTER);
		labeledButton.getChildren().addAll(button,label);
		return labeledButton;
	}
	
	/**
	 * Builds an HBox, which serves as the bottom pane in the default BorderPane layout for an administrator account
	 * @return an HBox pane
	 */
	public default HBox buildBottomPane() {
		HBox bottomPane = new HBox();
		Label signature = new Label("Created by Marisa Richmond");
		signature.getStyleClass().add("text");
		bottomPane.getChildren().add(signature);
		bottomPane.getStyleClass().add("bottompane");
		return bottomPane;
	}
	
	/**
	 * Builds a VBox, which serves as the left pane in the default BorderPane layout for an administrator account
	 * @param stage - the current program stage
	 * @param teacher - the account user 
	 * @return a VBox pane
	 */
	public default VBox buildLeftPane(Stage stage,Teacher teacher) {
		VBox leftPane = new VBox();
		ImageButton dashboardButton = new ImageButton(createImageWithFitHeight("images/dashboard.png",100));
		dashboardButton.setOnAction(e -> Platform.runLater(new ChangeScene(stage,new TeacherDashboard(stage,null,teacher))));
		Label viewDashboard = createLabel("Dashboard","label");
		ImageButton studentButton = new ImageButton(createImageWithFitHeight("images/mailbox.png",75));
		studentButton.setOnAction(e -> Platform.runLater(new ChangeScene(stage,new TeacherStudentWorkspace(stage,null,teacher))));
		Label viewStudent = createLabel("Students","label");
		ImageButton reportButton = new ImageButton(createImageWithFitHeight("images/classroom.png",100));
		reportButton.setOnAction(e -> Platform.runLater(new ChangeScene(stage,new TeacherReportWorkspace(stage,null,teacher))));
		Label reportLabel = createLabel("Reports","label");
		ImageButton scheduleButton = new ImageButton(createImageWithFitHeight("images/students.png",85));
		scheduleButton.setOnAction(e -> Platform.runLater(new ChangeScene(stage,new TeacherSchedule(stage,null,teacher))));
		Label viewSchedule = createLabel("Schedule","label");
		leftPane.getChildren().addAll(dashboardButton,viewDashboard,studentButton,viewStudent,reportButton,reportLabel,scheduleButton,viewSchedule);
		leftPane.getStyleClass().add("leftpane");
		return leftPane;
	}
	
	/**
	 * Places all of the Nodes in the center GridPane of the overall BorderPane layout
	 * @param gridpane - GridPane
	 * @param nodes - a List of all necessary Nodes to be placed on the GridPane
	 */
	public abstract void placeNodes(GridPane gridpane,List<Node> nodes);

}
