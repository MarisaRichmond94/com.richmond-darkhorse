package com.richmond.darkhorse.ProjectSB.gui.component;
import java.util.Arrays;
import java.util.List;
import com.richmond.darkhorse.ProjectSB.DailySheet;
import com.richmond.darkhorse.ProjectSB.Student;
import javafx.scene.Node;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class ViewDailySheet implements DirectorLayout{

	private Student student;
	private DailySheet dailySheet;
	
	public ViewDailySheet(Student student,DailySheet dailySheet) {
		this.student = student;
		this.dailySheet = dailySheet;
	}
	
	public void display() {
		Stage stage = new Stage();
		GridPane viewDailySheetLayout = buildGridPane();
		String title = "Daily Sheet - " + student.getFirstName() + " " + student.getLastName() + ", " + dailySheet.getStringDate();
		buildPopUp(stage,viewDailySheetLayout,title);
	}
	
	/**
	 * Builds the central GridPane for {@link ViewDailySheet}
	 * @return GridPane
	 */
	private GridPane buildGridPane() {
		GridPane gridpane = new GridPane();
		setConstraints(gridpane,6,0,10,10,"modulargridpane");
	    gridpane.getStylesheets().add("css/director.css");
	    Label documentType = createLabel("Daily Sheet","subtitle");
	    String dateString = "" + dailySheet.getStringDate() + "", nameString = student.getFirstName() + " " + student.getLastName();
	    List<Label> labels = populateLabels(Arrays.asList("date:","name:","a.m. snack:","p.m. snack:","lunch:","nap:","a.m. centers","academics:","observed:","fine motor:","gross motor:","social:","self-help:","creative:","cognitive:"),"label");
	    Label date = createLabel(dateString,"response-label"), name = createLabel(nameString,"response-label");
	    Label morningSnack = isMorningSnack(), afternoonSnack = isAfternoonSnack(), lunch = getLunchValue(), nap = getNapValue(), friends = getFriendsValue(), academics = getAcademicsValue();
	    Label activityDescription = createLabel(dailySheet.getActivityDescription(),"response-label"),teacherComment = createLabel(dailySheet.getAcademicNote(),"response-label");
	    	Label fineMotor = getFineMotor(), grossMotor = getGrossMotor(), social = getSocial(), selfHelp = getSelfHelp(),creative = getCreative(), cognitive = getCognitive();
	   	Label observationOne = createLabel(dailySheet.getObservationOne(),"response-label"), observationTwo = createLabel(dailySheet.getObservationTwo(),"response-label");
	    List<Node> nodes = Arrays.asList(documentType,labels.get(0),date,labels.get(1),name,labels.get(2),morningSnack,labels.get(3),afternoonSnack,labels.get(4),lunch,labels.get(5),nap,labels.get(6),friends,labels.get(7),academics,activityDescription,teacherComment,labels.get(8),labels.get(9),fineMotor,labels.get(10),grossMotor,labels.get(11),social,labels.get(12),selfHelp,labels.get(13),creative,labels.get(14),cognitive,observationOne,observationTwo);
	    placeNodes(gridpane,nodes);
	    return gridpane;
	}

	@Override
	public void placeNodes(GridPane gridpane, List<Node> nodes) {
		placeNodeSpan(gridpane,nodes.get(0),0,0,4,2,"center",null);
		placeNode(gridpane,nodes.get(1),3,0,"right",null);
		placeNodeSpan(gridpane,nodes.get(2),4,0,2,1,"left",null);
		placeNode(gridpane,nodes.get(3),3,1,"right",null);
		placeNodeSpan(gridpane,nodes.get(4),4,1,2,1,"left",null);
		placeNode(gridpane,nodes.get(5),0,2,"right",null);
		placeNodeSpan(gridpane,nodes.get(6),1,2,5,1,"left",null);
		placeNode(gridpane,nodes.get(7),0,3,"right",null);
		placeNodeSpan(gridpane,nodes.get(8),1,3,5,1,"left",null);
		placeNode(gridpane,nodes.get(9),0,4,"right",null);
		placeNodeSpan(gridpane,nodes.get(10),1,4,5,1,"left",null);
		placeNode(gridpane,nodes.get(11),0,5,"right",null);
		placeNodeSpan(gridpane,nodes.get(12),1,5,5,1,"left",null);
		placeNode(gridpane,nodes.get(13),0,6,"right",null);
		placeNodeSpan(gridpane,nodes.get(14),1,6,5,1,"left",null);
		placeNode(gridpane,nodes.get(15),0,7,"right",null);
		placeNodeSpan(gridpane,nodes.get(16),1,7,5,1,"left",null);
		placeNodeSpan(gridpane,nodes.get(17),1,8,5,1,"left",null);
		placeNodeSpan(gridpane,nodes.get(18),1,9,6,1,"left",null);
		placeNode(gridpane,nodes.get(19),0,10,"right",null);
		placeNode(gridpane,nodes.get(20),0,11,"right",null);
		placeNode(gridpane,nodes.get(21),1,11,"left",null);
		placeNode(gridpane,nodes.get(22),2,11,"right",null);
		placeNode(gridpane,nodes.get(23),3,11,"left",null);
		placeNode(gridpane,nodes.get(24),4,11,"right",null);
		placeNode(gridpane,nodes.get(25),5,11,"left",null);
		placeNode(gridpane,nodes.get(26),0,12,"right",null);
		placeNode(gridpane,nodes.get(27),1,12,"left",null);
		placeNode(gridpane,nodes.get(28),2,12,"right",null);
		placeNode(gridpane,nodes.get(29),3,12,"left",null);
		placeNode(gridpane,nodes.get(30),4,12,"right",null);
		placeNode(gridpane,nodes.get(31),5,12,"left",null);
		placeNodeSpan(gridpane,nodes.get(32),1,13,5,1,"left",null);
		placeNodeSpan(gridpane,nodes.get(33),1,14,5,1,"left",null);
	}
	
	/**
	 * Creates a new Label based on the {@link Student}'s {@link DialySheet}
	 * @return Label
	 */
	private Label isMorningSnack() {
		Label morningSnackValue = new Label();
	    morningSnackValue.getStyleClass().add("response-label");
	    boolean morningSnackStatus = dailySheet.isMorningSnack();
	    boolean morningAbsentStatus = dailySheet.isMorningWasntHere();
	    if(morningSnackStatus == true) {morningSnackValue.setText(student.getFirstName() + " enjoyed morning snack with his friends");
	    	}else if(morningAbsentStatus == true) {morningSnackValue.setText(student.getFirstName() + "was not here during morning snack");
	    	}else {morningSnackValue.setText(student.getFirstName() + "was not hungry and chose not to participate in morning snack");}
	    return morningSnackValue;
	}
	
	/**
	 * Creates a new Label based on the {@link Student}'s {@link DialySheet}
	 * @return Label
	 */
	private Label isAfternoonSnack() {
		Label afternoonSnackValue = new Label();
	    afternoonSnackValue.getStyleClass().add("response-label");
	    boolean afternoonSnackStatus = dailySheet.isAfternoonSnack();
	    boolean afternoonAbsentStatus = dailySheet.isAfternoonWasntHere();
	    if(afternoonSnackStatus == true) {afternoonSnackValue.setText(student.getFirstName() + " enjoyed afternoon snack with his friends");
	    	}else if(afternoonAbsentStatus == true) {afternoonSnackValue.setText(student.getFirstName() + "was not here during afternoon snack");
	    	}else {afternoonSnackValue.setText(student.getFirstName() + "was not hungry and chose not to participate in afternoon snack");}
	    return afternoonSnackValue;
	}
	
	/**
	 * Creates a new Label based on the {@link Student}'s {@link DialySheet}
	 * @return Label
	 */
	private Label getLunchValue() {
		Label lunchValue = new Label();
	    lunchValue.getStyleClass().add("response-label");
	    boolean schoolLunchStatus = dailySheet.isSchoolLunch();
	    if(schoolLunchStatus == true) {
	    		lunchValue.setText(student.getFirstName() + " finished " + dailySheet.getMainCourse() + " of his main course, " + dailySheet.getVegetable() + " of his vegetables, and " + dailySheet.getFruit() + " of his fruit");
	    }else {lunchValue.setText("*" + student.getFirstName() + " ate a lunch from home");}
	    return lunchValue;
	}
	
	/**
	 * Creates a new Label based on the {@link Student}'s {@link DialySheet}
	 * @return Label
	 */
	private Label getNapValue() {
		Label napValue = new Label();
	    napValue.getStyleClass().add("response-label");
	    boolean sleepStatus = dailySheet.isSleepStatus();
	    if(sleepStatus == true) {napValue.setText(student.getFirstName() + " slept from " + dailySheet.getNapStart() + " to " + dailySheet.getNapEnd());}
	    else {napValue.setText(student.getFirstName() + " rested from " + dailySheet.getNapStart() + " to " + dailySheet.getNapEnd());}
	    return napValue;
	}
	
	/**
	 * Creates a new Label based on the {@link Student}'s {@link DialySheet}
	 * @return Label
	 */
	private Label getFriendsValue() {
		Label friends = new Label();
	    friends.getStyleClass().add("response-label");
	    List<String> friendsList = dailySheet.getFriends();
	    String myFriends = "Today, " + student.getFirstName() + " played with ";
	    int index = 0;
	    for(String friend : friendsList) {
	    		if(index < friendsList.size()-1) {myFriends = myFriends + friend + ", ";}
	    		else {myFriends = myFriends + "and " + friend;}
	    }
	    List<String> activitiesList = dailySheet.getActivities();
	    String myActivities = "; Together, they enjoyed playing with ";
	    int activityIndex = 0;
	    for(String activity : activitiesList) {
	    		if(activityIndex < activitiesList.size()-1) {myActivities = myActivities + activity + ", ";}
	    		else {myActivities = myActivities + "and " + activity;}
	    }
	    friends.setText(myFriends + myActivities);
	    return friends;
	}
	
	/**
	 * Creates a new Label based on the {@link Student}'s {@link DialySheet}
	 * @return Label
	 */
	private Label getAcademicsValue() {
		Label preAc = new Label("Today's pre-academic focus was " + dailySheet.getPreAc() + "; " + student.getFirstName() + " was supposed to");
	    preAc.getStyleClass().add("response-label");
	    return preAc;
	}
	
	/**
	 * Creates a new Label based on the {@link Student}'s {@link DialySheet}
	 * @return Label
	 */
	private Label getFineMotor() {
		boolean isFineMotor = dailySheet.isFineMotor();
	    String fineMotor;
	    if(isFineMotor == true) {fineMotor = "X";}
	    else {fineMotor = "";}
	    Label fineMotorValue = new Label(fineMotor);
	    fineMotorValue.getStyleClass().add("response-label");
	    return fineMotorValue;
	}
	
	/**
	 * Creates a new Label based on the {@link Student}'s {@link DialySheet}
	 * @return Label
	 */
	private Label getGrossMotor() {
		boolean isGrossMotor = dailySheet.isGrossMotor();
	    String grossMotor;
	    if(isGrossMotor == true) {grossMotor = "X";}
	    else {grossMotor = "";}
	    Label grossMotorValue = new Label(grossMotor);
	    grossMotorValue.getStyleClass().add("response-label");
	    return grossMotorValue;
	}
	
	/**
	 * Creates a new Label based on the {@link Student}'s {@link DialySheet}
	 * @return Label
	 */
	private Label getSocial() {
		boolean isSocial = dailySheet.isSocial();
	    String social;
	    if(isSocial == true) {social = "X";}
	    else {social = "";}
	    Label socialValue = new Label(social);
	    socialValue.getStyleClass().add("response-label");
	    return socialValue;
	}
	
	/**
	 * Creates a new Label based on the {@link Student}'s {@link DialySheet}
	 * @return Label
	 */
	private Label getSelfHelp() {
		boolean isSelfHelp = dailySheet.isSelfHelp();
	    String selfHelp;
	    if(isSelfHelp == true) {selfHelp = "X";}
	    else {selfHelp = "";}
	    Label selfHelpValue = new Label(selfHelp);
	    selfHelpValue.getStyleClass().add("response-label");
	    return selfHelpValue;
	}
	
	/**
	 * Creates a new Label based on the {@link Student}'s {@link DialySheet}
	 * @return Label
	 */
	private Label getCreative() {
		boolean isCreative = dailySheet.isCreative();
	    String creative;
	    if(isCreative == true) {creative = "X";}
	    else {creative = "";}
	    Label creativeValue = new Label(creative);
	    creativeValue.getStyleClass().add("response-label");
	    return creativeValue;
	}
	
	/**
	 * Creates a new Label based on the {@link Student}'s {@link DialySheet}
	 * @return Label
	 */
	private Label getCognitive() {
		boolean isCognitive = dailySheet.isCognitive();
	    String cognitive;
	    if(isCognitive == true) {cognitive = "X";}
	    else {cognitive = "";}
	    Label cognitiveValue = new Label(cognitive);
	    cognitiveValue.getStyleClass().add("response-label");
	    return cognitiveValue; 
	}
	
}
