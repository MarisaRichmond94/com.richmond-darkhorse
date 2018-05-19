package com.richmond.darkhorse.ProjectSB.gui;
import javafx.geometry.Rectangle2D;
import java.util.Map;
import com.richmond.darkhorse.ProjectSB.AccountManager;
import com.richmond.darkhorse.ProjectSB.Admin;
import com.richmond.darkhorse.ProjectSB.Center;
import com.richmond.darkhorse.ProjectSB.SpecialBeginnings;
import com.richmond.darkhorse.ProjectSB.gui.scene.AdminSetUp;
import com.richmond.darkhorse.ProjectSB.gui.scene.LoadingScene;
import com.richmond.darkhorse.ProjectSB.gui.scene.LoginScene;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.Screen;

public class Main extends Application implements EventHandler<ActionEvent> {

	private Stage stage;

	public static void main(String args[]) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		this.stage = stage;
		stage.setTitle("Project{SB} - The Paperless Solution to Great Childcare");
		Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
		stage.setX(primaryScreenBounds.getMinX());
		stage.setY(primaryScreenBounds.getMinY());
		stage.setWidth(primaryScreenBounds.getWidth());
		stage.setHeight(primaryScreenBounds.getHeight());
		LoginScene loginScene = new LoginScene(stage, null);
		LoadingScene loadingScene;
		AdminSetUp adminSetup = new AdminSetUp(stage,null);
		try {Admin.loadAdmin();}
		catch(NullPointerException e) {};
		if(Admin.getInstance().getUserID() == null) { loadingScene = new LoadingScene(stage, adminSetup);}
		else {loadingScene = new LoadingScene(stage,loginScene);}
		stage.setScene(loadingScene);
		stage.show();
		if (loadingScene.isProgressComplete() == true) {changeScene(loginScene);}
		stage.setOnCloseRequest(e -> closeProgram());
	}

	@Override
	public void handle(ActionEvent actionEvent) {
	}

	/**
	 * Changes the stage's current scene
	 * @param scene - new scene
	 */
	public void changeScene(Scene scene) {
		stage.setScene(scene);
		stage.show();
	}
	
	/**
	 * Saves files upon program close 
	 */
	public static void closeProgram() {
		SpecialBeginnings sB = SpecialBeginnings.getInstance();
		sB.saveCenters();
		sB.saveStaffMembers();
		Map<String,Center> centers = sB.getCenters();
		for(Center center : centers.values()) {
			center.saveClassrooms();
			center.saveStudents();
		}
		AccountManager accountManager = AccountManager.getInstance();
		accountManager.saveUserIDToAccount();
	}

}
