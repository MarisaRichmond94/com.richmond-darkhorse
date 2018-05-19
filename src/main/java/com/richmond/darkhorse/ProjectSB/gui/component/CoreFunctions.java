package com.richmond.darkhorse.ProjectSB.gui.component;
import java.util.Map;
import com.richmond.darkhorse.ProjectSB.AccountManager;
import com.richmond.darkhorse.ProjectSB.Admin;
import com.richmond.darkhorse.ProjectSB.Center;
import com.richmond.darkhorse.ProjectSB.SpecialBeginnings;
import com.richmond.darkhorse.ProjectSB.gui.scene.LoginScene;
import com.richmond.darkhorse.ProjectSB.middleman.ChangeScene;
import javafx.application.Platform;
import javafx.stage.Stage;

public interface CoreFunctions {

	public default void saveInit(Stage stage) {
		SpecialBeginnings sB = SpecialBeginnings.getInstance();
		sB.saveCenters();
		sB.saveStaffMembers();
		Map<String,Center> centers = sB.getCenters();
		for(Center center : centers.values()) {
			center.saveClassrooms();
		}
		AccountManager.getInstance().saveUserIDToAccount();
		Admin.getInstance().saveAdmin();
		Platform.runLater(new ChangeScene(stage,new LoginScene(stage,null)));
	}
	
}
