package com.richmond.darkhorse.ProjectSB.gui.scene;
import com.richmond.darkhorse.ProjectSB.Admin;
import com.richmond.darkhorse.ProjectSB.gui.component.SceneFormatter;
import com.richmond.darkhorse.ProjectSB.middleman.Login;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class AdminSetUp extends Scene implements SceneFormatter{

	public AdminSetUp(Stage stage,Scene nextScene) {
		this(stage,new BorderPane(),nextScene);
	}
	
	public AdminSetUp(Stage stage,BorderPane layout,Scene nextScene) {
		super(layout);
		HBox topPane = buildTopPane();
		HBox bottomPane = buildBottomPane();
		GridPane centerPane = buildCenterPane(stage);
		BorderPane adminSetupLayout = layout;
		setBorderPane(adminSetupLayout,centerPane,null,null,topPane,bottomPane);
		adminSetupLayout.getStylesheets().add("css/admin.css");
	}
	
	private HBox buildTopPane() {
		HBox topPane = new HBox();
		ImageView logoViewer = createImageWithFitHeight("images/logo.png",250);
		topPane.getChildren().add(logoViewer);
		topPane.getStyleClass().add("hbox");
		topPane.getStyleClass().add("toppane");
		return topPane;
	}
	
	private HBox buildBottomPane() {
		HBox bottomPane = new HBox();
		Label signature = new Label("Created by Marisa Richmond");
		signature.getStyleClass().add("text");
		bottomPane.getChildren().add(signature);
		bottomPane.getStyleClass().add("hbox");
		bottomPane.getStyleClass().add("bottompane");
		return bottomPane;
	}
	
	private GridPane buildCenterPane(Stage stage) {
		GridPane gridpane = new GridPane();
		gridpane.getStyleClass().add("gridpane");
		Label firstNameLabel = createLabel("First Name:","text");
		TextField fnTextField = createTextField("first name","textfield",500);
		Label lastNameLabel = createLabel("Last Name:","text");
		TextField lnTextField = createTextField("last name","textfield",500);
		Label employeeIDLabel = createLabel("Admin ID:","text");
		TextField eidTextField = createTextField("000000000","textfield",500);
		Button createButton = new Button("Set Admin");
		createButton.setOnAction(e -> {
			String firstName = fnTextField.getText();
			String lastName = lnTextField.getText();
			String userID = eidTextField.getText();
			Admin.getInstance().initializeCredentials(firstName,lastName,userID);
			Admin.getInstance().saveAdmin();
			Thread loginThread = new Thread(new Login(firstName,lastName,userID,stage));
			loginThread.start();
		});
		placeNode(gridpane,firstNameLabel,0,1,"right",null);
		placeNode(gridpane,fnTextField,1,1,"left",null);
		placeNode(gridpane,lastNameLabel,0,2,"right",null);
		placeNode(gridpane,lnTextField,1,2,"left",null);
		placeNode(gridpane,employeeIDLabel,0,3,"right",null);
		placeNode(gridpane,eidTextField,1,3,"left",null);
		placeNode(gridpane,createButton,1,5,"center",null);
		return gridpane;
	}
	
}
