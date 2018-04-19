package com.richmond.darkhorse.ProjectSB.gui.component;
import java.util.List;
import com.richmond.darkhorse.ProjectSB.Director;
import com.richmond.darkhorse.ProjectSB.gui.scene.DirectorClassroomWorkspace;
import com.richmond.darkhorse.ProjectSB.gui.scene.DirectorDashboard;
import com.richmond.darkhorse.ProjectSB.gui.scene.DirectorHome;
import com.richmond.darkhorse.ProjectSB.gui.scene.DirectorMailbox;
import com.richmond.darkhorse.ProjectSB.gui.scene.DirectorStudentWorkspace;
import com.richmond.darkhorse.ProjectSB.middleman.ChangeScene;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public interface DirectorLayout extends SceneFormatter,CoreFunctions {
	
	/**
	 * Builds an HBox, which serves as the top pane in the default BorderPane layout for a director account
	 * @param stage - the current program stage
	 * @param director - the account user 
	 * @return an HBox pane 
	 */
	public default HBox buildTopPane(Stage stage,Director director) {
		HBox topPane = new HBox();
		ImageView logoViewer = createImageWithFitHeight("images/logo.png",250);
		ImageButton homeButton = new ImageButton(createImageWithFitHeight("images/home.png",125));
		homeButton.setOnAction(e -> Platform.runLater(new ChangeScene(stage,new DirectorHome(stage,null,director))));
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
	 * @param director - the account user 
	 * @return a VBox pane
	 */
	public default VBox buildLeftPane(Stage stage,Director director) {
		VBox leftPane = new VBox();
		ImageButton dashboardButton = new ImageButton(createImageWithFitHeight("images/dashboard.png",100));
		dashboardButton.setOnAction(e -> Platform.runLater(new ChangeScene(stage,new DirectorDashboard(stage,null,director))));
		Label viewDashboard = createLabel("Dashboard","label");
		ImageButton mailboxButton = new ImageButton(createImageWithFitHeight("images/mailbox.png",75));
		mailboxButton.setOnAction(e -> Platform.runLater(new ChangeScene(stage,new DirectorMailbox(stage,null,director))));
		Label checkMailbox = createLabel("Mailbox","label");
		ImageButton classroomButton = new ImageButton(createImageWithFitHeight("images/classroom.png",100));
		classroomButton.setOnAction(e -> Platform.runLater(new ChangeScene(stage,new DirectorClassroomWorkspace(stage,null,director))));
		Label viewClassrooms = createLabel("Classrooms","label");
		ImageButton studentButton = new ImageButton(createImageWithFitHeight("images/students.png",85));
		studentButton.setOnAction(e -> Platform.runLater(new ChangeScene(stage,new DirectorStudentWorkspace(stage,null,director))));
		Label createStudents = createLabel("Students","label");
		leftPane.getChildren().addAll(dashboardButton,viewDashboard,mailboxButton,checkMailbox,classroomButton,viewClassrooms,studentButton,createStudents);
		leftPane.getStyleClass().add("leftpane");
		return leftPane;
	}
	
	/**
	 * Builds a pop-up scene with modality
	 * @param stage - a new secondary stage
	 * @param gridpane - the layout
	 * @param title - the title at the top of the new window
	 */
	public default void buildPopUp(Stage stage,GridPane gridpane,String title) {
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.setTitle(title);
		Scene scene = new Scene(gridpane);
		stage.setScene(scene);
		stage.setMaxWidth(1100);
		stage.setMinWidth(1100);
		stage.showAndWait();
	}
	
	public abstract void placeNodes(GridPane gridpane,List<Node> nodes);

}
