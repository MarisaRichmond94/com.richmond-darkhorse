package com.richmond.darkhorse.ProjectSB.gui.scene;
import com.richmond.darkhorse.ProjectSB.AccountManager;
import com.richmond.darkhorse.ProjectSB.gui.component.SceneFormatter;
import com.richmond.darkhorse.ProjectSB.middleman.ChangeLabel;
import com.richmond.darkhorse.ProjectSB.middleman.Login;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Paint;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.scene.image.ImageView;

public class LoginScene extends Scene implements SceneFormatter {

	AccountManager accountManager = AccountManager.getInstance();
	private Label failedLogin = new Label("");
	public Stage stage;
	public Scene nextScene;
	
	public LoginScene(Stage stage,Scene nextScene) {
		this(stage,new BorderPane(),nextScene);
		this.stage = stage;
	}
	
	public LoginScene(Stage stage,BorderPane layout,Scene nextScene) {
		super(layout);
		
		//Top Pane
		HBox topBar = new HBox();
		ImageView logoViewer = createImageWithFitHeight("logo.png",250);
		topBar.getChildren().add(logoViewer);
		topBar.getStyleClass().add("hbox");
		
		//Bottom Pane
		HBox bottomBar = new HBox();
		Label signature = new Label("Created by Marisa Richmond");
		signature.getStyleClass().add("text");
		bottomBar.getChildren().add(signature);
		bottomBar.getStyleClass().add("hbox");
		
		//Center Pane
		GridPane gridpane = new GridPane();
		gridpane.getStyleClass().add("gridpane");
		ImageView loginViewer = createImageWithFitHeight("loginwithsmile.png",150);
		Label firstNameLabel = createLabel("First Name:","text");
		TextField fnTextField = createTextField("first name","textfield",500);
		Label lastNameLabel = createLabel("Last Name:","text");
		TextField lnTextField = createTextField("last name","textfield",500);
		Label employeeIDLabel = createLabel("Employee ID:","text");
		TextField eidTextField = createTextField("employee id","textfield",500);
		Button loginButton = new Button("Login");
		loginButton.setOnAction(e -> {
			String firstName = fnTextField.getText();
			String lastName = lnTextField.getText();
			String employeeID = eidTextField.getText();
			Thread loginThread = new Thread(new Login(firstName,lastName,employeeID,failedLogin,stage,this));
			loginThread.start();
		});
		failedLogin.getStyleClass().add("failedlogin");
		failedLogin.setTextFill(Paint.valueOf("#ff3b00"));
		setConstraints(gridpane,3,0,8,10);
		placeNode(gridpane,loginViewer,1,0,"center",null);
		placeNode(gridpane,firstNameLabel,0,1,"right",null);
		placeNode(gridpane,fnTextField,1,1,"left",null);
		placeNode(gridpane,lastNameLabel,0,2,"right",null);
		placeNode(gridpane,lnTextField,1,2,"left",null);
		placeNode(gridpane,employeeIDLabel,0,3,"right",null);
		placeNode(gridpane,eidTextField,1,3,"left",null);
		placeNode(gridpane,loginButton,1,5,"center",null);
		placeNode(gridpane,failedLogin,1,6,"center",null);
		
		//Formatting
		BorderPane loginLayout = layout;
		setBorderPane(loginLayout,gridpane,null,null,topBar,bottomBar);
		loginLayout.getStylesheets().add("loginscene.css");
	
	}
	
	/**
	 * Checks to see if log-in status failed and displays a warning message.
	 * @param status - true if the log-in was successful; false if the log-in was unsuccessful 
	 */
	public void checkForFailedLogin(boolean status) {
		if(status == false) {
			String message = "No account matches this combination";
			Platform.runLater(new ChangeLabel(failedLogin,message));
		}
	}
	
}
