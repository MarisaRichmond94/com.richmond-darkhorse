package com.richmond.darkhorse.ProjectSB.gui.scene;
import com.richmond.darkhorse.ProjectSB.Center;
import com.richmond.darkhorse.ProjectSB.Classroom;
import com.richmond.darkhorse.ProjectSB.Director;
import com.richmond.darkhorse.ProjectSB.Record;
import com.richmond.darkhorse.ProjectSB.Student;
import com.richmond.darkhorse.ProjectSB.gui.component.AddContactPane;
import com.richmond.darkhorse.ProjectSB.gui.component.DirectorLayout;
import com.richmond.darkhorse.ProjectSB.gui.component.ImageButton;
import com.richmond.darkhorse.ProjectSB.gui.component.ModifyPlanPane;
import com.richmond.darkhorse.ProjectSB.gui.component.ViewRecordPane;
import com.richmond.darkhorse.ProjectSB.middleman.ChangeScene;
import com.richmond.darkhorse.ProjectSB.middleman.ModifyExistingStudent;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.ChoiceBox;

public class ModifyStudentInfo extends Scene implements DirectorLayout{

	private Director director;
	private Student student;
	private BorderPane modifyStudentInfoLayout;
	
	public ModifyStudentInfo(Stage stage,Scene nextScene,Student student,Director director) {
		this(stage,new BorderPane(),nextScene,student,director);
	}
	
	public ModifyStudentInfo(Stage stage,BorderPane layout,Scene nextScene,Student student,Director director) {
		super(layout);
		this.director = director;
		this.student = student;
		Record record = student.getRecord();
		Map<String,Boolean> attendancePlan = record.getAttendance().getAttendancePlan();
		GridPane topPane = buildTopPane(stage);
		HBox bottomPane = buildBottomPane();
		VBox leftPane = buildLeftPane(stage);
		ScrollPane scrollPane = buildRightPane();
		GridPane centerPane = buildGridPane(stage,attendancePlan);
	    modifyStudentInfoLayout = layout;
	    setBorderPaneRightScroll(modifyStudentInfoLayout,centerPane,scrollPane,leftPane,topPane,bottomPane);
	    modifyStudentInfoLayout.getStylesheets().add("css/director.css");
	}
	
	/**
	 * Builds the top pane (HBox)
	 * @param stage - the current stage
	 * @return HBox
	 */
	private GridPane buildTopPane(Stage stage) {
		GridPane topPane = new GridPane();
		setConstraints(topPane,6,0,10,10,"toppane");
		ImageButton classroomButton = new ImageButton(createImageWithFitHeight("images/students.png",100));
		classroomButton.setOnAction(e -> Platform.runLater(new ChangeScene(stage,new DirectorStudentWorkspace(stage,null,director))));
		VBox returnButton = buildButtonWithLabel(classroomButton,createLabel("return","label"));
		String currentStudent = student.getFirstName() + " " + student.getLastName();
		Label studentName = createLabel(currentStudent,"med-title");
		ImageButton logoutButton = new ImageButton(createImageWithFitHeight("images/logout.png",125));
		logoutButton.setOnAction(e -> saveInit(stage));
		VBox logout = buildButtonWithLabel(logoutButton,createLabel("logout","label"));
		placeNode(topPane,returnButton,0,0,"center",null);
		placeNodeSpan(topPane,studentName,1,0,4,1,"center",null);
		placeNode(topPane,logout,5,0,"center",null);
		return topPane;
	}
	
	/**
	 * Builds the bottom pane (HBox)
	 * @return HBox
	 */
	public HBox buildBottomPane() {
		HBox bottomPane = new HBox();
		Label signature = createLabel("Created by Marisa Richmond","label");
		bottomPane.getChildren().add(signature);
		bottomPane.getStyleClass().add("bottompane");
		return bottomPane;
	}
	
	/**
	 * Builds the left pane (VBox)
	 * @param stage - the current stage 
	 * @return VBox
	 */
	private VBox buildLeftPane(Stage stage) {
		VBox leftPane = new VBox();
		leftPane.getStyleClass().add("leftpane");
		ImageButton addContactButton = new ImageButton(createImageWithFitHeight("images/newicon.png",125));
		addContactButton.setOnAction(e -> addContact());
		Label addContact = createLabel("contact info","label");
		ImageButton editPlanButton = new ImageButton(createImageWithFitHeight("images/editplan.png",100));
		editPlanButton.setOnAction(e -> modifyStudentInfoLayout.setCenter(new ModifyPlanPane(student,director)));
		Label editPlan = createLabel("modify plan","label");
		ImageButton recordButton = new ImageButton(createImageWithFitHeight("images/record.png",110));
		recordButton.setOnAction(e -> modifyStudentInfoLayout.setCenter(new ViewRecordPane(director,student)));
		Label recordView = createLabel("view record","label");
		ImageButton returnButton = new ImageButton(createImageWithFitHeight("images/back.png",80));
		returnButton.setOnAction(e -> modifyStudentInfo(stage));
		Label returnLabel = createLabel("return","label");
		leftPane.getChildren().addAll(addContactButton,addContact,editPlanButton,editPlan,recordButton,recordView,returnButton,returnLabel);
		return leftPane;
	}
	
	/**
	 * Creates a right ScrollPane holding a {@link Student}'s {@link Teacher} comments (if any exist)
	 * @return ScrollPane
	 */
	private ScrollPane buildRightPane() {
		GridPane rightPane = new GridPane();
		rightPane.getStyleClass().add("rightpane");
	    ImageView commentViewer = createImageWithFitHeight("images/teachercomments.png",125);
	    Label sideTitle = createLabel("Teacher \n Comments","centrallabel");
	    if(student.getRecord().getTeacherComments() != null) {
	    		List<String> teacherComments = student.getRecord().getTeacherComments();
		    int columnIndex = 1, rowIndex = 1;
		    for(String teacherComment : teacherComments) {
		    		Label commentLabel = createLabel(teacherComment,"label");
		    		placeNodeSpan(rightPane,commentLabel,columnIndex,rowIndex,3,1,"center",null);
		    		rowIndex++;
		    }
	    }else {
	    	 	Label noTeacherComments = createLabel("no teacher comments yet; \ncheck back later","label");
	    		placeNodeSpan(rightPane,noTeacherComments,0,2,3,1,"center",null);
	    	}
	    placeNode(rightPane,commentViewer,0,0,"center",null);
	    placeNode(rightPane,sideTitle,1,0,"center",null);
	    ScrollPane scrollPane = new ScrollPane(rightPane);
		scrollPane.setFitToWidth(true);
		scrollPane.setFitToHeight(true);
		return scrollPane;
	}
	
	/**
	 * Creates a default GridPane
	 * @param stage - the current stage
	 * @param attendancePlan 
	 * @return GridPane
	 */
	private GridPane buildGridPane(Stage stage,Map<String,Boolean> attendancePlan) {
		GridPane gridpane = new GridPane();
		gridpane.getStyleClass().add("css/director.css");
		setConstraints(gridpane,3,0,10,10,"gridpane");
		Label title = createLabel("Change Classroom","super-subtitle");
	    Label birthDate = createLabel("birth date:","centrallabel");
	    TextField birthDateField = createTextField(student.getBirthDate(),"textfield",650);
	    birthDateField.setDisable(true);
	    Label enrolledClassroom = createLabel("classroom:","centrallabel");
	    ChoiceBox<ClassroomHolder> classroomBox = createClassroomBox();
	    List<String> labelStrings =  Arrays.asList("monday plan:","tuesday plan:","wednesday plan:","thursday plan:","friday plan:");
	    List<Label> planLabels = populateLabels(labelStrings,"centrallabel");
	    ChoiceBox<String> mondayBox = populateDayBox("Monday",attendancePlan);
	    ChoiceBox<String> tuesdayBox = populateDayBox("Tuesday",attendancePlan);
	    ChoiceBox<String> wednesdayBox = populateDayBox("Wednesday",attendancePlan);
	    ChoiceBox<String> thursdayBox = populateDayBox("Thursday",attendancePlan);
	    ChoiceBox<String> fridayBox = populateDayBox("Friday",attendancePlan);
		ImageButton writeButton = new ImageButton(createImageWithFitHeight("images/write.png",100));
		writeButton.setOnAction(e -> write(stage,classroomBox));
		writeButton.setVisible(false);
		Label writeLabel = createLabel("write","label");
		writeLabel.setVisible(false);
		Label editLabel = createLabel("edit","label");
		ImageButton editButton = new ImageButton(createImageWithFitHeight("images/edit.png",100));
		editButton.setOnAction(e -> edit(writeButton,writeLabel,editButton,editLabel,classroomBox));
		ImageButton trashButton = new ImageButton(createImageWithFitHeight("images/trash.png",100));
		trashButton.setOnAction(e -> delete(stage));
		Label trashLabel = createLabel("delete","label");
	    List<Node> nodes = Arrays.asList(birthDate,birthDateField,enrolledClassroom,classroomBox,planLabels.get(0),mondayBox,planLabels.get(1),tuesdayBox,planLabels.get(2),wednesdayBox,planLabels.get(3),thursdayBox,planLabels.get(4),fridayBox,editButton,editLabel,writeButton,writeLabel,trashButton,trashLabel,title);
	    placeNodes(gridpane,nodes);
	    return gridpane;
	}
	
	@Override
	public void placeNodes(GridPane gridpane, List<Node> nodes) {
		placeNode(gridpane,nodes.get(0),0,1,"right",null);
		placeNodeSpan(gridpane,nodes.get(1),1,1,2,1,"left",null);
		placeNode(gridpane,nodes.get(2),0,2,"right",null);
		placeNodeSpan(gridpane,nodes.get(3),1,2,2,1,"left",null);
		placeNode(gridpane,nodes.get(4),0,3,"right",null);
		placeNodeSpan(gridpane,nodes.get(5),1,3,2,1,"left",null);
		placeNode(gridpane,nodes.get(6),0,4,"right",null);
		placeNodeSpan(gridpane,nodes.get(7),1,4,2,1,"left",null);
		placeNode(gridpane,nodes.get(8),0,5,"right",null);
		placeNodeSpan(gridpane,nodes.get(9),1,5,2,1,"left",null);
		placeNode(gridpane,nodes.get(10),0,6,"right",null);
		placeNodeSpan(gridpane,nodes.get(11),1,6,2,1,"left",null);
		placeNode(gridpane,nodes.get(12),0,7,"right",null);
		placeNodeSpan(gridpane,nodes.get(13),1,7,2,1,"left",null);
		placeNode(gridpane,nodes.get(14),1,8,"center",null);
		placeNode(gridpane,nodes.get(15),1,9,"center",null);
		placeNode(gridpane,nodes.get(16),1,8,"center",null);
		placeNode(gridpane,nodes.get(17),1,9,"center",null);
		placeNode(gridpane,nodes.get(18),2,8,"left",null);
		placeNode(gridpane,nodes.get(19),2,9,"left",null);
		placeNodeSpan(gridpane,nodes.get(20),0,0,3,1,"center",null);
	}
	
	/**
	 * Creates a ChoiceBox populated with each {@link Classroom} in the {@link Center}
	 * @return ChoiceBox
	 */
	private ChoiceBox<ClassroomHolder> createClassroomBox(){
		ChoiceBox<ClassroomHolder> classroomBox = new ChoiceBox<ClassroomHolder>();
	    ClassroomHolder emptyClassroom = new ClassroomHolder(null);
	    classroomBox.getItems().add(emptyClassroom);
	    classroomBox.setValue(emptyClassroom);
	    Classroom myClassroom = null;
	    if(student.getClassroom(student.getClassroomID()) != null) {myClassroom = student.getClassroom(student.getClassroomID());}
	    Center center = director.getCenter(director.getCenterID());
	    Map<String,Classroom> classrooms = center.getClassrooms();
	    for(Classroom theClassroom : classrooms.values()) {
	    		ClassroomHolder newClassroom = new ClassroomHolder(theClassroom);
	    		classroomBox.getItems().add(newClassroom);
	    		if(theClassroom.equals(myClassroom)) {classroomBox.setValue(newClassroom);}
	    }
	    classroomBox.getStyleClass().add("choice-box");
	    classroomBox.setDisable(true);
	    return classroomBox;
	}
	
	/**
	 * Creates a ChoiceBox for a particular day
	 * @param day - the day of the week (Monday - Friday)
	 * @param attendancePlan - the {@link Student}'s current {@link AttendancePlan}
	 * @return ChoiceBox<String>
	 */
	private ChoiceBox<String> populateDayBox(String day,Map<String,Boolean> attendancePlan){
		ChoiceBox<String> dayBox = new ChoiceBox<String>();
	    dayBox.getItems().addAll("present","not present");
	    if(attendancePlan.get(day) == true) {dayBox.setValue(dayBox.getItems().get(0));}
	    else {dayBox.setValue(dayBox.getItems().get(1));}
	    dayBox.setDisable(true);
	    dayBox.getStyleClass().add("choice-box");
	    return dayBox;
	}
	
	/**
	 * Writes any changes made
	 * @param stage - the current stage
	 * @param classroomBox - ChoiceBox<ClassroomHolder>
	 */
	private void write(Stage stage,ChoiceBox<ClassroomHolder> classroomBox) {
		Classroom selectedClassroom = classroomBox.getValue().getClassroom();
		Map<String,Boolean> studentAttendancePlan = student.getRecord().getAttendance().getAttendancePlan();
		Thread modifyExistingStudent = new Thread(new ModifyExistingStudent(director,student,selectedClassroom,studentAttendancePlan));
		Platform.runLater(new ChangeScene(stage,new ModifyStudentInfo(stage,null,student,director)));
		modifyExistingStudent.start();
	}
	
	/**
	 * Edits any changes made about the {@link Student}
	 * @param writeButton
	 * @param writeLabel
	 * @param editButton
	 * @param editLabel
	 * @param classroomBox
	 */
	private void edit(ImageButton writeButton,Label writeLabel,ImageButton editButton,Label editLabel,ChoiceBox<ClassroomHolder> classroomBox) {
		writeButton.setVisible(true);
		writeLabel.setVisible(true);
		editButton.setVisible(false);
		editLabel.setVisible(false);
		classroomBox.setDisable(false);
	}
	
	/**
	 * Deletes the {@link Student}
	 * @param stage - the current stage
	 */
	private void delete(Stage stage) {
		director.deleteStudent(student);
		Platform.runLater(new ChangeScene(stage,new DirectorStudentWorkspace(stage,null,director)));
	}
	
	/**
	 * Creates a new addContactPane and sets the central GridPane to it
	 */
	private void addContact() {
		AddContactPane addContactPane = new AddContactPane(director,student);
		ScrollPane scrollPane = new ScrollPane(addContactPane);
		scrollPane.setFitToWidth(true);
		modifyStudentInfoLayout.setCenter(scrollPane);
	}
	
	/**
	 * Changes the center pane to a new GridPane that allows the user to modify the {@link Student}'s information
	 * @param stage - the current stage
	 */
	private void modifyStudentInfo(Stage stage) {
		Map<String,Boolean> attendancePlan = student.getRecord().getAttendance().getAttendancePlan();
		modifyStudentInfoLayout.setCenter(buildGridPane(stage,attendancePlan));
	}
	
	public class ClassroomHolder{
		private Classroom classroom;
		public ClassroomHolder(Classroom classroom) {
			this.classroom = classroom;
		}
		public Classroom getClassroom() {return classroom;}
		@Override
		public String toString() {
			if(classroom == null) {return "N/A";
			}else {return classroom.getCenter(classroom.getCenterID()).getAbbreviatedName() + ": " + classroom.getClassroomType();}
		}
	}
	
}

