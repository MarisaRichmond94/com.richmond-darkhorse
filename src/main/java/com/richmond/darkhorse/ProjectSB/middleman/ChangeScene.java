package com.richmond.darkhorse.ProjectSB.middleman;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ChangeScene implements Runnable{
	
	private Stage stage;
	private Scene scene;
	
	public ChangeScene(Stage stage, Scene scene) {
		this.stage = stage;
		this.scene = scene;
	}

	@Override
	public void run() {
		stage.setScene(scene);
		//stage.setMaximized(true);
//		stage.setFullScreen(true);
//		stage.setFullScreenExitHint("");
	}
	
}
