package com.richmond.darkhorse.ProjectSB.gui.scene;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToggleButton;
import com.richmond.darkhorse.ProjectSB.Director;
import com.richmond.darkhorse.ProjectSB.Document;
import com.richmond.darkhorse.ProjectSB.StaffMember;
import com.richmond.darkhorse.ProjectSB.Teacher;
import com.richmond.darkhorse.ProjectSB.BehaviorReport;
import com.richmond.darkhorse.ProjectSB.IncidentReport;
import com.richmond.darkhorse.ProjectSB.gui.component.DirectorLayout;
import com.richmond.darkhorse.ProjectSB.gui.component.ImageButton;
import com.richmond.darkhorse.ProjectSB.gui.component.UnsignedBehaviorReportPane;
import com.richmond.darkhorse.ProjectSB.gui.component.UnsignedIncidentReportPane;
import com.richmond.darkhorse.ProjectSB.middleman.ChangeScene;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBase;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class DirectorMailbox extends Scene implements DirectorLayout {
	
	private boolean isBehaviorReportPane = true;
	private GridPane innerPane;
	private UnsignedBehaviorReportPane unsignedBehaviorReportPane;
	private UnsignedIncidentReportPane unsignedIncidentReportPane;
	private BorderPane directorMailboxLayout;
	
	public DirectorMailbox(Stage stage,Scene nextScene,Director director) {
		this(stage,new BorderPane(),nextScene,director);
	}
	
	public DirectorMailbox(Stage stage,BorderPane layout,Scene nextScene,Director director) {
		super(layout);
		HBox topPane = buildTopPane(stage,director);
		HBox bottomPane = buildBottomPane();
		VBox leftPane = buildLeftPane(stage,director);
		VBox rightPane = buildRightPane(director);
		GridPane centerPane = buildGridPane(director,stage);
		directorMailboxLayout = layout;
		setBorderPane(directorMailboxLayout,centerPane,rightPane,leftPane,topPane,bottomPane);
		directorMailboxLayout.getStylesheets().add("css/director.css");
	}
	
	/**
	 * Builds a right pane for the overall BorderPane layout
	 * @param director - the current user
	 * @return VBox
	 */
	private VBox buildRightPane(Director director) {
		GridPane rightPane = new GridPane();
		rightPane.getStyleClass().add("rightpane");
		Label title = createLabel("Signed Reports","subtitle");
		ImageButton sendButton = new ImageButton(createImageWithFitHeight("images/send.png",50));
		sendButton.setOnAction(e -> director.returnAllReports());
		populateReports(rightPane,director);
		placeNode(rightPane,title,0,0,"center",null);
		placeNode(rightPane,sendButton,1,0,"center",null);
		ScrollPane scrollPane = new ScrollPane(rightPane);
		scrollPane.setFitToWidth(true);
		VBox wrapper = new VBox();
		wrapper.getStyleClass().add("wrapper");
		wrapper.getChildren().add(scrollPane);
		return wrapper;
	}
	
	/**
	 * Builds a GridPane for the overall BorderPane layout
	 * @param director - the current {@link Director}
	 * @param stage - the current {@link Stage}
	 * @return GridPane
	 */
	private GridPane buildGridPane(Director director,Stage stage) {
		GridPane gridpane = new GridPane();
		setConstraints(gridpane,6,0,10,10,"gridpane");
		gridpane.getStylesheets().add("css/director.css");
		kirbyAllBehaviorReports(director);
		unsignedBehaviorReportPane = new UnsignedBehaviorReportPane(director,director.getMailbox().getUnsignedBehaviorReports());
		innerPane = unsignedBehaviorReportPane;
		ScrollPane scrollPane = new ScrollPane(innerPane);
		scrollPane.setPrefHeight(500);
		scrollPane.setPrefWidth(500);
		Label title = createLabel("Unsigned Reports","title");
		ImageButton refreshButton = new ImageButton(createImageWithFitHeight("images/refresh.png",75));
		ToggleButton behaviorReportButton = createToggleButton("behavior report(s)",true,50,300,"toggle-button");
		ToggleButton incidentReportButton = createToggleButton("incident report(s)",false,50,300,"toggle-button");
		Label behaviorView = createLabel("view","label");
		Button behaviorViewButton = createButtonWithLabel(behaviorView,40,150);
		Label incidentView = createLabel("view","label");
		Button incidentViewButton = createButtonWithLabel(incidentView,40,150);
		Label behaviorSign = createLabel("sign","label");
		Button behaviorSignButton = createButtonWithLabel(behaviorSign,40,150);
		Label incidentSign = createLabel("sign","label");
		Button incidentSignButton = createButtonWithLabel(incidentSign,40,150);
		incidentSignButton.setVisible(false);
		List<ButtonBase> buttons = Arrays.asList(incidentReportButton,behaviorReportButton,behaviorViewButton,incidentViewButton,behaviorSignButton,incidentSignButton);
		behaviorReportButton.setOnAction(e -> selectBehaviorReport(scrollPane,director,buttons));
		incidentReportButton.setOnAction(e -> selectIncidentReport(scrollPane,director,buttons));
		behaviorViewButton.setOnAction(e -> viewBehaviorReport(stage,director));
		behaviorSignButton.setOnAction(e -> signBehaviorReport(director,scrollPane));
		incidentViewButton.setOnAction(e -> viewIncidentReport(stage,director));
		incidentSignButton.setOnAction(e -> signIncidentReport(director,scrollPane));
		refreshButton.setOnAction(e -> refresh(director,scrollPane));
		List<Node> nodes = Arrays.asList(title,refreshButton,behaviorReportButton,incidentReportButton,scrollPane,behaviorViewButton,incidentViewButton,behaviorSignButton,incidentSignButton);
		placeNodes(gridpane,nodes);
		return gridpane;
	}
	
	/**
	 * Populates the given GridPane with any signed {@link BehaviorReport}s or {@link IncidentReport}s
	 * @param rightPane - GridPane
	 * @param director - the current {@link Director}
	 */
	private void populateReports(GridPane rightPane,Director director) {
		int row = 1;
		Map<String,Document> signedReports = director.getMailbox().getAllSignedReports();
		for(Document document : signedReports.values()) {
			String documentTitle = document.getTitle(), student = document.getStudentName(), date = document.getStringDate(), buttonText = "" + documentTitle + "\nStudent: " + student + "\nDate: " + date;
			ToggleButton newButton = createToggleButton(buttonText,false,250,150,"toggle-button");
			newButton.setText("" + documentTitle + "\nStudent: " + student + "\nDate: " + date);
			placeNodeSpan(rightPane,newButton,0,row,2,1,"center",null);
			row++;
		}
	}
	
	/**
	 * Changes the inner ScrollPane to a Pane displaying unsigned {@link BehaviorReport}s
	 * @param scrollPane - ScrollPane
	 * @param director - the current {@link Director}
	 * @param buttons - a List<ButtonBase>
	 */
	private void selectBehaviorReport(ScrollPane scrollPane,Director director,List<ButtonBase> buttons) {
		((ToggleButton) buttons.get(0)).setSelected(false);
		((ToggleButton) buttons.get(1)).setSelected(true);
		unsignedBehaviorReportPane = (UnsignedBehaviorReportPane) buildBehaviorReport(director);
		scrollPane.setContent(unsignedBehaviorReportPane);
		buttons.get(2).setVisible(true);
		buttons.get(3).setVisible(false);
		buttons.get(4).setVisible(true);
		buttons.get(5).setVisible(false);
		isBehaviorReportPane = true;
	}
	
	/**
	 * Changes the inner ScrollPane to a Pane displaying unsigned {@link IncidentReport}s
	 * @param scrollPane - ScrollPane
	 * @param director - the current {@link Director}
	 * @param buttons - a List<ButtonBase>
	 */
	private void selectIncidentReport(ScrollPane scrollPane,Director director,List<ButtonBase> buttons) {
		((ToggleButton) buttons.get(0)).setSelected(true);
		((ToggleButton) buttons.get(1)).setSelected(false);
		unsignedIncidentReportPane = (UnsignedIncidentReportPane) buildIncidentReport(director);
		scrollPane.setContent(unsignedIncidentReportPane);
		buttons.get(2).setVisible(false);
		buttons.get(3).setVisible(true);
		buttons.get(4).setVisible(false);
		buttons.get(5).setVisible(true);
		isBehaviorReportPane = false;
	}
	
	/**
	 * Opens the selected {@link BehaviorReport} in a new Stage
	 * @param director - the current {@link Director}
	 * @param stage - the current {@link Stage}
	 */
	private void viewBehaviorReport(Stage stage,Director director) {
		List<BehaviorReport> selectedReports = new ArrayList<BehaviorReport>();
		if(isBehaviorReportPane == true) {
			Map<String,BehaviorReport> selectedBRs = unsignedBehaviorReportPane.getSelectedBehaviorReports();
			for(BehaviorReport behaviorReport : selectedBRs.values()) {selectedReports.add(behaviorReport);}
			Platform.runLater(new ChangeScene(stage,new ModifyBehaviorReports(stage,null,director,selectedReports)));
		}
	}
	
	/**
	 * Signs the current {@link BehaviorReport}
	 * @param director - the current {@link Director}
	 * @param scrollPane - ScrollPane
	 */
	private void signBehaviorReport(Director director,ScrollPane scrollPane) {
		if(isBehaviorReportPane == true) {
			Map<String,BehaviorReport> selectedBRs = unsignedBehaviorReportPane.getSelectedBehaviorReports();
			for(BehaviorReport behaviorReport : selectedBRs.values()) {
				behaviorReport.setDirectorSignature(true);
				director.getMailbox().addSignedBehaviorReport(behaviorReport);
			}
			VBox rightSideBar = buildRightPane(director);
			directorMailboxLayout.setRight(rightSideBar);
			unsignedBehaviorReportPane = (UnsignedBehaviorReportPane) buildBehaviorReport(director);
			scrollPane.setContent(unsignedBehaviorReportPane);
		}
	}
	
	/**
	 * Opens the selected {@link IncidentReport} in a new Stage
	 * @param director - the current {@link Director}
	 * @param stage - the current {@link Stage}
	 */
	private void viewIncidentReport(Stage stage,Director director) {
		List<IncidentReport> selectedReports = new ArrayList<IncidentReport>();
		if(isBehaviorReportPane == false) {
			Map<String,IncidentReport> selectedIRs = unsignedIncidentReportPane.getSelectedIncidentReports();
			for(IncidentReport incidentReport : selectedIRs.values()) {selectedReports.add(incidentReport);}
			Platform.runLater(new ChangeScene(stage,new ModifyIncidentReports(stage,null,director,selectedReports)));
		}
	}
	
	/**
	 * Signs the current {@link IncidentReport}
	 * @param director - the current {@link Director}
	 * @param scrollPane - ScrollPane
	 */
	private void signIncidentReport(Director director,ScrollPane scrollPane) {
		if(isBehaviorReportPane == false) {
			Map<String,IncidentReport> selectedIRs = (Map<String, IncidentReport>) unsignedIncidentReportPane.getSelectedIncidentReports();
			for(IncidentReport incidentReport : selectedIRs.values()) {
				incidentReport.setDirectorSignature(true);
				director.getMailbox().addSignedIncidentReport(incidentReport);
			}
			VBox rightSideBar = buildRightPane(director);
			directorMailboxLayout.setRight(rightSideBar);
			unsignedIncidentReportPane = (UnsignedIncidentReportPane) buildIncidentReport(director);
			scrollPane.setContent(unsignedIncidentReportPane);
		}
	}
	
	/**
	 * Refreshes the page to check for more unsigned {@link BehaviorReport}s and {@link IncidentReport}s
	 * @param director - the current {@link Director}
	 * @param scrollPane - ScrollPane
	 */
	private void refresh(Director director,ScrollPane scrollPane) {
		if(isBehaviorReportPane == true) {
			kirbyAllBehaviorReports(director);
			UnsignedBehaviorReportPane newBRPane = (UnsignedBehaviorReportPane) buildBehaviorReport(director);
			unsignedBehaviorReportPane = newBRPane;
			scrollPane.setContent(unsignedBehaviorReportPane);
		}else {
			kirbyAllIncidentReports(director);
			UnsignedIncidentReportPane newIRPane = (UnsignedIncidentReportPane) buildIncidentReport(director);
			unsignedIncidentReportPane = newIRPane;
			scrollPane.setContent(unsignedIncidentReportPane);
		}
	}
	
	/**
	 * Builds a new {@link BehaviorReport} pane
	 * @param director - the current {@link Director}
	 * @return GridPane
	 */
	private GridPane buildBehaviorReport(Director director) {
		kirbyAllBehaviorReports(director);
		UnsignedBehaviorReportPane unsignedBRPane = new UnsignedBehaviorReportPane(director,director.getMailbox().getUnsignedBehaviorReports());
		unsignedBehaviorReportPane = unsignedBRPane;
		return unsignedBehaviorReportPane;
	}
	
	/**
	 * Builds a new {@link IncidentReport} pane
	 * @param director - the current {@link Director}
	 * @return GridPane
	 */
	private GridPane buildIncidentReport(Director director) {
		kirbyAllIncidentReports(director);
		UnsignedIncidentReportPane unsignedIRPane = new UnsignedIncidentReportPane(director,director.getMailbox().getUnsignedIncidentReports());
		unsignedIncidentReportPane = unsignedIRPane;
		return unsignedIncidentReportPane;
	}
	
	/**
	 * Gets all {@link Teacher}s
	 * @param director - the current {@link Director}
	 * @return a List<Teacher>
	 */
	private List<Teacher> getTeachers(Director director){
		Map<String,StaffMember> staffMembers = director.getStaffMembers();
		List<Teacher> teachers = new ArrayList<Teacher>();
		for(StaffMember staffMember : staffMembers.values()) {
			if(staffMember.getCenterID() == director.getCenterID() && staffMember.getTitle().equals("Teacher")) {teachers.add((Teacher)staffMember);}
		}
		return teachers;
	}
	
	/**
	 * Checks for {@link BehaviorReport}s in each {@link Teacher}'s {@link Mailbox}. If found, they are pulled back into the {@link Director}'s {@link Mailbox}
	 * @param director - the current {@link Director}
	 */
	private void kirbyAllBehaviorReports(Director director) {
		List<Teacher> teachers = getTeachers(director);
		for(Teacher teacher : teachers) {
			Map<String,BehaviorReport> unsignedBehaviorReports = teacher.getMailbox().getUnsignedBehaviorReports();
			if(unsignedBehaviorReports.size() > 0) {
				for(BehaviorReport behaviorReport : unsignedBehaviorReports.values()) {director.getMailbox().addUnsignedBehaviorReport(behaviorReport);}
			}
		}
	}
	
	/**
	 * Checks for {@link IncidentReport}s in each {@link Teacher}'s {@link Mailbox}. If found, they are pulled back into the {@link Director}'s {@link Mailbox}
	 * @param director - the current {@link Director}
	 */
	private void kirbyAllIncidentReports(Director director){
		List<Teacher> teachers = getTeachers(director);
		for(Teacher teacher : teachers) {
			Map<String,IncidentReport> unsignedIncidentReports = teacher.getMailbox().getUnsignedIncidentReports();
			if(unsignedIncidentReports.size() > 0) {
				for(IncidentReport incidentReport : unsignedIncidentReports.values()) {director.getMailbox().addUnsignedIncidentReport(incidentReport);}
			}
		}
	}

	@Override
	public void placeNodes(GridPane gridpane, List<Node> nodes) {
		placeNodeSpan(gridpane,nodes.get(0),0,0,4,1,"right",null);
		placeNode(gridpane,nodes.get(1),4,0,"left",null);
		placeNodeSpan(gridpane,nodes.get(2),0,1,2,1,"left",null);
		placeNodeSpan(gridpane,nodes.get(3),2,1,2,1,"left",null);
		placeNodeSpan(gridpane,nodes.get(4),0,3,6,6,"center",null);
		placeNode(gridpane,nodes.get(5),4,9,"center",null);
		placeNode(gridpane,nodes.get(6),4,9,"center",null);
		placeNode(gridpane,nodes.get(7),5,9,"center",null);
		placeNode(gridpane,nodes.get(8),5,9,"center",null);
	}
	
}

