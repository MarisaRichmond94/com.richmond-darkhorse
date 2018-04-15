package com.richmond.darkhorse.ProjectSB.gui.scene;
import com.richmond.darkhorse.ProjectSB.AccountManager;
import com.richmond.darkhorse.ProjectSB.Center;
import com.richmond.darkhorse.ProjectSB.Classroom;
import com.richmond.darkhorse.ProjectSB.Director;
import com.richmond.darkhorse.ProjectSB.Record;
import com.richmond.darkhorse.ProjectSB.SpecialBeginnings;
import com.richmond.darkhorse.ProjectSB.Student;
import com.richmond.darkhorse.ProjectSB.gui.component.AddContactPane;
import com.richmond.darkhorse.ProjectSB.gui.component.ImageButton;
import com.richmond.darkhorse.ProjectSB.gui.component.ModifyPlanPane;
import com.richmond.darkhorse.ProjectSB.gui.component.ViewRecordPane;
import com.richmond.darkhorse.ProjectSB.middleman.ChangeScene;
import com.richmond.darkhorse.ProjectSB.middleman.ModifyExistingStudent;
import javafx.application.Platform;
import javafx.geometry.HPos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import java.util.List;
import java.util.Map;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.ChoiceBox;

public class ModifyStudentInfo extends Scene{

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
		
		GridPane topBar = buildTopBar(stage);
		HBox bottomBar = buildBottomBar();
		VBox leftSideBar = buildLeftSideBar(stage);
		Record record = student.getRecord();
		ScrollPane scrollPane = buildRightSideBar();
		Map<String,Boolean> attendancePlan = record.getAttendance().getAttendancePlan();
		GridPane centerPane = buildDefaultPane(stage,attendancePlan);
	    
	    modifyStudentInfoLayout = layout;
	    modifyStudentInfoLayout.setTop(topBar);
	    modifyStudentInfoLayout.setBottom(bottomBar);
	    modifyStudentInfoLayout.setLeft(leftSideBar);
	    modifyStudentInfoLayout.setRight(scrollPane);
	    modifyStudentInfoLayout.setCenter(centerPane);
	    modifyStudentInfoLayout.getStylesheets().add("modifyclassroomsetup.css");
	}
	
	private GridPane buildTopBar(Stage stage) {
		GridPane topBar = new GridPane();
		topBar.setVgap(10);
		topBar.setHgap(10);
		ColumnConstraints columnOne = new ColumnConstraints();
		columnOne.setPercentWidth(25);
	    ColumnConstraints columnTwo = new ColumnConstraints();
	    columnTwo.setPercentWidth(25);
	    ColumnConstraints columnThree = new ColumnConstraints();
	    columnThree.setPercentWidth(25);
	    ColumnConstraints columnFour = new ColumnConstraints();
	    columnFour.setPercentWidth(25);
	    topBar.getColumnConstraints().addAll(columnOne,columnTwo,columnThree,columnFour);
	    topBar.getStyleClass().add("topbar");
	    
		ImageView studentViewer = new ImageView();
		Image studentIcon = new Image("students.png");
		studentViewer.setImage(studentIcon);
		studentViewer.setPreserveRatio(true);
		studentViewer.setFitHeight(100);
		ImageButton classroomButton = new ImageButton(studentViewer);
		classroomButton.setOnAction(e -> Platform.runLater(new ChangeScene(stage,new DirectorStudentWorkspace(stage,null,director))));
		classroomButton.getStyleClass().add("button");
		Label returnToStudents = new Label("<--return");
		returnToStudents.setTranslateY(-30);
		returnToStudents.setTranslateX(35);
		returnToStudents.getStyleClass().add("label");
		
		Label studentName = new Label(student.getFirstName() + " " + student.getLastName());
		studentName.getStyleClass().add("title");
		
		ImageView logoutViewer = new ImageView();
		Image lB = new Image("logout.png");
		logoutViewer.setImage(lB);
		logoutViewer.setPreserveRatio(true);
		logoutViewer.setFitHeight(125);
		ImageButton logoutButton = new ImageButton(logoutViewer);
		logoutButton.setOnAction(e -> {
			SpecialBeginnings sB = SpecialBeginnings.getInstance();
			sB.saveCenters();
			sB.saveStaffMembers();
			Map<String,Center> centers = sB.getCenters();
			for(Center center : centers.values()) {
				center.saveClassrooms();
				center.saveStudents();
			}
			AccountManager.getInstance().saveUserIDToAccount();
			Platform.runLater(new ChangeScene(stage,new LoginScene(stage,null)));
		});
		logoutButton.getStyleClass().add("button");
		Label logoutLabel = new Label("logout");
		logoutLabel.setTranslateY(-30);
		logoutLabel.setTranslateX(-40);
		logoutLabel.getStyleClass().add("label");
		
		topBar.add(classroomButton,0,0);
		GridPane.setHalignment(classroomButton,HPos.LEFT);
		topBar.add(returnToStudents,0,1);
		GridPane.setHalignment(returnToStudents,HPos.LEFT);
		topBar.add(studentName,1,0);
		GridPane.setHalignment(studentName,HPos.CENTER);
		GridPane.setConstraints(studentName,1,0,2,1);
		topBar.add(logoutButton,3,0);
		GridPane.setHalignment(logoutButton,HPos.RIGHT);
		topBar.add(logoutLabel,3,1);
		GridPane.setHalignment(logoutLabel,HPos.RIGHT);
		return topBar;
	}
	
	public HBox buildBottomBar() {
		HBox bottomBar = new HBox();
		Label signature = new Label("Created by Marisa Richmond");
		signature.getStyleClass().add("text");
		bottomBar.getChildren().add(signature);
		bottomBar.getStyleClass().add("bottombar");
		return bottomBar;
	}
	
	private VBox buildLeftSideBar(Stage stage) {
		VBox leftSideBar = new VBox();
		leftSideBar.getStyleClass().add("leftbar");
		ImageView addContactViewer = new ImageView();
		Image addContactIcon = new Image("newicon.png");
		addContactViewer.setImage(addContactIcon);
		addContactViewer.setPreserveRatio(true);
		addContactViewer.setFitHeight(125);
		ImageButton addContactButton = new ImageButton(addContactViewer);
		addContactButton.setOnAction(e -> {
			AddContactPane addContactPane = new AddContactPane(director,student);
			ScrollPane scrollPane = new ScrollPane(addContactPane);
			scrollPane.setFitToWidth(true);
			modifyStudentInfoLayout.setCenter(scrollPane);
		});
		addContactButton.getStyleClass().add("button");
		Label addContact = new Label("contact info");
		addContact.getStyleClass().add("label");
		
		ImageView editPlanViewer = new ImageView();
		Image editPlanIcon = new Image("editplan.png");
		editPlanViewer.setImage(editPlanIcon);
		editPlanViewer.setPreserveRatio(true);
		editPlanViewer.setFitHeight(100);
		ImageButton editPlanButton = new ImageButton(editPlanViewer);
		editPlanButton.setOnAction(e -> {modifyStudentInfoLayout.setCenter(new ModifyPlanPane(student,director));});
		editPlanButton.getStyleClass().add("button");
		Label editPlan = new Label("modify plan");
		editPlan.getStyleClass().add("label");
		
		ImageView recordViewer = new ImageView();
		Image recordIcon = new Image("record.png");
		recordViewer.setImage(recordIcon);
		recordViewer.setPreserveRatio(true);
		recordViewer.setFitHeight(110);
		ImageButton recordButton = new ImageButton(recordViewer);
		recordButton.setOnAction(e -> {
			modifyStudentInfoLayout.setCenter(new ViewRecordPane(director,student));
		});
		recordButton.getStyleClass().add("button");
		Label recordView = new Label("view record");
		recordView.getStyleClass().add("label");
		
		ImageView returnViewer = new ImageView();
		Image returnIcon = new Image("back.png");
		returnViewer.setImage(returnIcon);
		returnViewer.setPreserveRatio(true);
		returnViewer.setFitHeight(80);
		ImageButton returnButton = new ImageButton(returnViewer);
		returnButton.setOnAction(e -> {
			Map<String,Boolean> attendancePlan = student.getRecord().getAttendance().getAttendancePlan();
			modifyStudentInfoLayout.setCenter(buildDefaultPane(stage,attendancePlan));
		});
		returnButton.getStyleClass().add("button");
		Label returnLabel = new Label("return");
		returnLabel.getStyleClass().add("label");
		
		leftSideBar.getChildren().addAll(addContactButton,addContact,editPlanButton,editPlan,recordButton,recordView,returnButton,returnLabel);
		return leftSideBar;
	}
	
	private ScrollPane buildRightSideBar() {
		GridPane rightSideBar = new GridPane();
	    rightSideBar.getStyleClass().add("rightbar");
	    
	    ImageView commentViewer = new ImageView();
	    Image commentIcon = new Image("teachercomments.png");
	    commentViewer.setImage(commentIcon);
	    commentViewer.setPreserveRatio(true);
	    commentViewer.setFitHeight(125);

	    Label sideTitle = new Label("Teacher \n Comments");
	    sideTitle.getStyleClass().add("centrallabel");
	    Label noTeacherComments = new Label("no teacher comments yet; \ncheck back later");
	    noTeacherComments.getStyleClass().add("label");
	    if(student.getRecord().getTeacherComments() != null) {
	    		List<String> teacherComments = student.getRecord().getTeacherComments();
		    int columnIndex = 1;
		    	int rowIndex = 1;
		    for(String teacherComment : teacherComments) {
		    		Label commentLabel = new Label(teacherComment);
		    		rightSideBar.add(commentLabel,columnIndex,rowIndex);
		    		GridPane.setHalignment(commentLabel,HPos.CENTER);
		    		GridPane.setConstraints(commentLabel,columnIndex,rowIndex,3,1);
		    		rowIndex++;
		    }
	    }else {
	    		rightSideBar.add(noTeacherComments,0,2);
	    		GridPane.setConstraints(noTeacherComments,0,2,3,1);
	    		GridPane.setHalignment(noTeacherComments,HPos.CENTER);
	    }

	    rightSideBar.add(commentViewer,0,0);
	    rightSideBar.add(sideTitle,1,0);
	    ScrollPane scrollPane = new ScrollPane(rightSideBar);

		scrollPane.setFitToWidth(true);
		scrollPane.setFitToHeight(true);
		return scrollPane;
	}
	
	private GridPane buildDefaultPane(Stage stage,Map<String,Boolean> attendancePlan) {
		GridPane centerPane = new GridPane();
	    centerPane.setVgap(10);
	    centerPane.setHgap(10);
		ColumnConstraints columnOne = new ColumnConstraints();
		columnOne.setPercentWidth(33.3);
	    ColumnConstraints columnTwo = new ColumnConstraints();
	    columnTwo.setPercentWidth(33.3);
	    ColumnConstraints columnThree = new ColumnConstraints();
	    columnThree.setPercentWidth(33.3);
	    centerPane.getColumnConstraints().addAll(columnOne,columnTwo,columnThree);
	    centerPane.getStyleClass().add("gridpane");
	    
	    Label birthDate = new Label("birth date: ");
	    birthDate.getStyleClass().add("centrallabel");
	    TextField birthDateField = new TextField();
	    birthDateField.setPromptText(student.getBirthDate());
	    birthDateField.setDisable(true);
	    birthDateField.getStyleClass().add("textfield");
	    birthDateField.setMaxWidth(600);
	    
	    Label enrolledClassroom = new Label("classroom: ");
	    enrolledClassroom.getStyleClass().add("centrallabel");
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
	    classroomBox.setMaxWidth(600);
	    classroomBox.setMinWidth(600);
	    classroomBox.getStyleClass().add("choicebox");
	    classroomBox.setDisable(true);
	    classroomBox.getStyleClass().add("choicebox");
	    
	    Label monday = new Label("monday plan: ");
	    monday.getStyleClass().add("centrallabel");
	    ChoiceBox<String> mondayBox = new ChoiceBox<String>();
	    mondayBox.getItems().addAll("present","not present");
	    if(attendancePlan.get("Monday") == true) {mondayBox.setValue(mondayBox.getItems().get(0));}
	    else {mondayBox.setValue(mondayBox.getItems().get(1));}
	    mondayBox.setDisable(true);
	    mondayBox.getStyleClass().add("choicebox");
	    mondayBox.setMaxWidth(600);
	    
	    Label tuesday = new Label("tuesday plan: ");
	    tuesday.getStyleClass().add("centrallabel");
	    ChoiceBox<String> tuesdayBox = new ChoiceBox<String>();
	    tuesdayBox.getItems().addAll("present","not present");
	    if(attendancePlan.get("Tuesday") == true) {tuesdayBox.setValue(tuesdayBox.getItems().get(0));}
	    else {tuesdayBox.setValue(tuesdayBox.getItems().get(1));}
	    tuesdayBox.setDisable(true);
	    tuesdayBox.getStyleClass().add("choicebox");
	    tuesdayBox.setMaxWidth(600);
	    
	    Label wednesday = new Label("wednesday plan: ");
	    wednesday.getStyleClass().add("centrallabel");
	    ChoiceBox<String> wednesdayBox = new ChoiceBox<String>();
	    wednesdayBox.getItems().addAll("present","not present");
	    if(attendancePlan.get("Wednesday") == true) {wednesdayBox.setValue(wednesdayBox.getItems().get(0));}
	    else {wednesdayBox.setValue(wednesdayBox.getItems().get(1));}
	    wednesdayBox.setDisable(true);
	    wednesdayBox.getStyleClass().add("choicebox");
	    wednesdayBox.setMaxWidth(600);
	    
	    Label thursday = new Label("thursday plan: ");
	    thursday.getStyleClass().add("centrallabel");
	    ChoiceBox<String> thursdayBox = new ChoiceBox<String>();
	    thursdayBox.getItems().addAll("present","not present");
	    if(attendancePlan.get("Thursday") == true) {thursdayBox.setValue(thursdayBox.getItems().get(0));}
	    else {thursdayBox.setValue(thursdayBox.getItems().get(1));}
	    thursdayBox.setDisable(true);
	    thursdayBox.getStyleClass().add("choicebox");
	    thursdayBox.setMaxWidth(600);
	    
	    Label friday = new Label("friday plan: ");
	    friday.getStyleClass().add("centrallabel");
	    ChoiceBox<String> fridayBox = new ChoiceBox<String>();
	    fridayBox.getItems().addAll("present","not present");
	    if(attendancePlan.get("Friday") == true) {fridayBox.setValue(fridayBox.getItems().get(0));}
	    else {fridayBox.setValue(fridayBox.getItems().get(1));}
	    fridayBox.setDisable(true);
	    fridayBox.getStyleClass().add("choicebox");
	    fridayBox.setMaxWidth(600);
	    
	    ImageView writeViewer = new ImageView();
		Image write = new Image("write.png");
		writeViewer.setImage(write);
		writeViewer.setPreserveRatio(true);
		writeViewer.setFitHeight(150);
		ImageButton writeButton = new ImageButton(writeViewer);
		writeButton.setOnAction(e -> {
			Classroom selectedClassroom = classroomBox.getValue().getClassroom();
			Map<String,Boolean> studentAttendancePlan = student.getRecord().getAttendance().getAttendancePlan();
			Thread modifyExistingStudent = new Thread(new ModifyExistingStudent(director,student,selectedClassroom,studentAttendancePlan));
			Platform.runLater(new ChangeScene(stage,new ModifyStudentInfo(stage,null,student,director)));
			modifyExistingStudent.start();
		});
		writeButton.setVisible(false);
		Label writeLabel = new Label("write");
		writeLabel.setVisible(false);
		writeLabel.getStyleClass().add("label");
		
		Label editLabel = new Label("edit");
		editLabel.getStyleClass().add("label");
		ImageView editViewer = new ImageView();
		Image edit = new Image("edit.png");
		editViewer.setImage(edit);
		editViewer.setPreserveRatio(true);
		editViewer.setFitHeight(150);
		ImageButton editButton = new ImageButton(editViewer);
		editButton.setOnAction(e -> {
			writeButton.setVisible(true);
			writeLabel.setVisible(true);
			editButton.setVisible(false);
			editLabel.setVisible(false);
			classroomBox.setDisable(false);
		});
		 
		//GridPane - Delete Button
		ImageView trashViewer = new ImageView();
		Image trash = new Image("trash.png");
		trashViewer.setImage(trash);
		trashViewer.setPreserveRatio(true);
		trashViewer.setFitHeight(150);
		ImageButton trashButton = new ImageButton(trashViewer);
		trashButton.setOnAction(e -> {
			director.deleteStudent(student);
			Platform.runLater(new ChangeScene(stage,new DirectorStudentWorkspace(stage,null,director)));
		});
		Label trashLabel = new Label("delete");
		trashLabel.getStyleClass().add("label");
	    
	    centerPane.add(birthDate,0,0);
	    GridPane.setHalignment(birthDate,HPos.RIGHT);
	    centerPane.add(birthDateField,1,0);
	    GridPane.setConstraints(birthDateField,1,0,2,1);
	    centerPane.add(enrolledClassroom,0,1);
	    GridPane.setHalignment(enrolledClassroom,HPos.RIGHT);
	    centerPane.add(classroomBox,1,1);
	    GridPane.setConstraints(classroomBox,1,1,2,1);
	    centerPane.add(monday,0,2);
	    GridPane.setHalignment(monday,HPos.RIGHT);
	    centerPane.add(mondayBox,1,2);
	    GridPane.setConstraints(mondayBox,1,2,2,1);
	    centerPane.add(tuesday,0,3);
	    GridPane.setHalignment(tuesday,HPos.RIGHT);
	    centerPane.add(tuesdayBox,1,3);
	    GridPane.setConstraints(tuesdayBox,1,3,2,1);
	    centerPane.add(wednesday,0,4);
	    GridPane.setHalignment(wednesday,HPos.RIGHT);
	    centerPane.add(wednesdayBox,1,4);
	    GridPane.setConstraints(wednesdayBox,1,4,2,1);
	    centerPane.add(thursday,0,5);
	    GridPane.setHalignment(thursday,HPos.RIGHT);
	    centerPane.add(thursdayBox,1,5);
	    GridPane.setConstraints(thursdayBox,1,5,2,1);
	    centerPane.add(friday,0,6);
	    GridPane.setHalignment(friday,HPos.RIGHT);
	    centerPane.add(fridayBox,1,6);
	    GridPane.setConstraints(fridayBox,1,6,2,1);
	    centerPane.add(editButton,1,7);
	    GridPane.setHalignment(editButton,HPos.CENTER);
	    centerPane.add(editLabel,1,8);
	    GridPane.setHalignment(editLabel,HPos.CENTER);
	    centerPane.add(writeButton,1,7);
	    GridPane.setHalignment(writeButton,HPos.CENTER);
	    centerPane.add(writeLabel,1,8);
		GridPane.setHalignment(writeLabel,HPos.CENTER);
	    centerPane.add(trashButton,2,7);
	    GridPane.setHalignment(trashButton,HPos.LEFT);
	    centerPane.add(trashLabel,2,8);
	    GridPane.setHalignment(trashLabel,HPos.LEFT);
	    return centerPane;
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

