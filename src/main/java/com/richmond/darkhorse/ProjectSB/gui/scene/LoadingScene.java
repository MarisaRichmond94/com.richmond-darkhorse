package com.richmond.darkhorse.ProjectSB.gui.scene;
import com.richmond.darkhorse.ProjectSB.middleman.LoadingSceneProgressUpdater;
import com.richmond.darkhorse.ProjectSB.gui.component.SceneFormatter;
import com.richmond.darkhorse.ProjectSB.middleman.ChangeScene;
import com.richmond.darkhorse.ProjectSB.middleman.LoadData;
import javafx.scene.layout.BorderPane;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class LoadingScene extends Scene implements SceneFormatter {

	private ProgressBar progressBar;
	private Scene nextScene;
	private Stage stage;
	 
	public LoadingScene(Stage stage,Scene nextScene) {
		this(stage,new BorderPane(),nextScene);
	}
	
	public LoadingScene(final Stage stage,BorderPane layout,Scene nextScene) {
		super(layout);
		this.nextScene = nextScene;
		this.stage = stage; 
		BorderPane loadingLayout = layout;
		GridPane gridpane = new GridPane();
		setConstraints(gridpane,4,0,0,0,"gridpane");
		gridpane.getStyleClass().add("gridpane");
		ImageView loadingViewer = createImage("images/plainlogowithsmile.png");
		loadingViewer.fitWidthProperty().bind(stage.widthProperty());
		placeNodeSpan(gridpane,loadingViewer,0,0,4,2,"center","center");
		progressBar = new ProgressBar(0.0);
		progressBar.setMaxSize(1500,100);
		progressBar.setPrefHeight(50);
		placeNodeSpan(gridpane,progressBar,0,2,4,2,"center","center");
		setBorderPane(loadingLayout,gridpane,null,null,null,null);
		loadingLayout.getStylesheets().add("css/loadingscene.css");
		Thread progressThread = new Thread(new LoadingSceneProgressUpdater(this,3f));
		progressThread.start();
		Thread loadData = new Thread(new LoadData());
		loadData.start();
		}
	
	/**
	 * Sets the value of the progress bar. If the value is greater than or equal to one, progress is considered complete and the next scene is loaded 
	 * @param value - a double value to which the progress bar will be set to 
	 */
	public void setProgressBarValue(double value) {
		progressBar.setProgress(value);
		if(value >= 1) {
			Platform.runLater(new ChangeScene(stage,nextScene));
		}
	}
	
	/**
	 * Checks the progress of the progress bar 
	 * @return - true if progress is complete (1 == 100%) and false if progress is incomplete (>1)
	 */
	public boolean isProgressComplete() {
		boolean progressStatus = false;
		if(progressBar.getProgress() == 1) {
			progressStatus = true;
		}
		return progressStatus;
	}
	
	public void loadApplication() {	
	}
	
}
