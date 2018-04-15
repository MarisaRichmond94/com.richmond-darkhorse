package com.richmond.darkhorse.ProjectSB.middleman;
import com.richmond.darkhorse.ProjectSB.Account;
import com.richmond.darkhorse.ProjectSB.AccountManager;
import com.richmond.darkhorse.ProjectSB.Admin;
import com.richmond.darkhorse.ProjectSB.Director;
import com.richmond.darkhorse.ProjectSB.Teacher;
import com.richmond.darkhorse.ProjectSB.gui.scene.AdminHome;
import com.richmond.darkhorse.ProjectSB.gui.scene.DirectorHome;
import com.richmond.darkhorse.ProjectSB.gui.scene.LoginScene;
import com.richmond.darkhorse.ProjectSB.gui.scene.TeacherHome;
import javafx.stage.Stage;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Label;

public class Login implements Runnable {

	private String firstName;
	private String lastName;
	private String userID;
	public Label failedLogin;
	public Stage stage;
	public LoginScene thisScene;
	public Scene nextScene;
	AccountManager accountManager = AccountManager.getInstance();

	public Login(String firstName, String lastName, String userID, Label failedLogin, Stage stage, LoginScene scene) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.userID = userID;
		this.failedLogin = failedLogin;
		this.stage = stage;
		this.thisScene = scene;
		this.nextScene = null;
	}

	@Override
	public void run() {
		boolean status = accountManager.checkAccount(firstName, lastName, userID);
		if (status == true) {
			Scene myNextScene = getNextScene(userID);
			this.setNextScene(myNextScene);
			Platform.runLater(new ChangeScene(stage,nextScene));
		} else {thisScene.checkForFailedLogin(status);}
	}
	
	/**
	 * Checks the account type of the user and returns the appropriate next scene.
	 * @param userID - the supplied identification that was matched to the system
	 * @return the next scene to be displayed 
	 */
	public Scene getNextScene(String userID) {
		Account account = accountManager.getAccount(userID);
		if(account instanceof Admin) {
			Admin admin = (Admin)account;
			AdminHome adminHome = new AdminHome(stage,null,admin);
			return adminHome;
		}else if(account instanceof Director) {
			Director director = (Director)account;
			DirectorHome directorHome = new DirectorHome(stage,null,director);
			return directorHome;
		}else if(account instanceof Teacher) {
			Teacher teacher = (Teacher)account;
			TeacherHome teacherHome = new TeacherHome(stage,null,teacher);
			return teacherHome;
		}
		return null;
	}

	/**
	 * Sets nextScene 
	 * @param nextScene - the scene being set to the next scene
	 */
	public void setNextScene(Scene nextScene) {
		this.nextScene = nextScene;
	}

}
