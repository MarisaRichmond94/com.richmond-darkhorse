package com.richmond.darkhorse.ProjectSB.gui.component;
import java.util.List;
import com.richmond.darkhorse.ProjectSB.DailySheet;
import com.richmond.darkhorse.ProjectSB.Student;
import javafx.geometry.HPos;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;

public class ViewDailySheet {

	private Student student;
	private DailySheet dailySheet;
	
	public ViewDailySheet(Student student,DailySheet dailySheet) {
		this.student = student;
		this.dailySheet = dailySheet;
	}
	
	public void display() {
		
		Stage stage = new Stage();
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.setTitle("Daily Sheet - " + student.getFirstName() + " " + student.getLastName() + ", " + dailySheet.getStringDate());
		
		GridPane viewDailySheetLayout = new GridPane();
		viewDailySheetLayout.setVgap(10);
		viewDailySheetLayout.setHgap(10);
		ColumnConstraints columnOne = new ColumnConstraints();
		columnOne.setPercentWidth(16.6);
	    ColumnConstraints columnTwo = new ColumnConstraints();
	    columnTwo.setPercentWidth(16.6);
	    ColumnConstraints columnThree = new ColumnConstraints();
	    columnThree.setPercentWidth(16.6);
	    ColumnConstraints columnFour = new ColumnConstraints();
	    columnFour.setPercentWidth(16.6);
	    ColumnConstraints columnFive = new ColumnConstraints();
	    columnFive.setPercentWidth(16.6);
	    ColumnConstraints columnSix = new ColumnConstraints();
	    columnSix.setPercentWidth(16.6);
	    viewDailySheetLayout.getColumnConstraints().addAll(columnOne,columnTwo,columnThree,columnFour,columnFive,columnSix);
	    viewDailySheetLayout.getStyleClass().add("gridpane");
	    viewDailySheetLayout.getStylesheets().add("modifystudentinfo.css");
	    
	    Label documentType = new Label("Daily Sheet");
	    documentType.getStyleClass().add("subtitle");
	    Label date = new Label("date:");
	    date.getStyleClass().add("label");
	    Label dateValue = new Label("" + dailySheet.getStringDate() + "");
	    dateValue.getStyleClass().add("response-label");
	    Label name = new Label("name:");
	    name.getStyleClass().add("label");
	    Label nameValue = new Label(student.getFirstName() + " " + student.getLastName());
	    nameValue.getStyleClass().add("response-label");
	    
	    viewDailySheetLayout.add(documentType,0,0);
	    GridPane.setConstraints(documentType,0,0,4,2);
	    GridPane.setHalignment(documentType,HPos.CENTER);
	    viewDailySheetLayout.add(date,3,0);
	    GridPane.setHalignment(date,HPos.RIGHT);
	    viewDailySheetLayout.add(dateValue,4,0);
	    GridPane.setConstraints(dateValue,4,0,2,1);
	    viewDailySheetLayout.add(name,3,1);
	    GridPane.setHalignment(name,HPos.RIGHT);
	    viewDailySheetLayout.add(nameValue,4,1);
	    GridPane.setConstraints(nameValue,4,1,2,1);
	    
	    //Food
	    Label morningSnack = new Label("A.M. SNACK:");
	    morningSnack.getStyleClass().add("pre-label");
	    Label morningSnackValue = new Label();
	    morningSnackValue.getStyleClass().add("response-label");
	    boolean morningSnackStatus = dailySheet.isMorningSnack();
	    boolean morningAbsentStatus = dailySheet.isMorningWasntHere();
	    if(morningSnackStatus == true) {morningSnackValue.setText(student.getFirstName() + " enjoyed morning snack with his friends");
	    	}else if(morningAbsentStatus == true) {morningSnackValue.setText(student.getFirstName() + "was not here during morning snack");
	    	}else {morningSnackValue.setText(student.getFirstName() + "was not hungry and chose not to participate in morning snack");}
	    
	    Label afternoonSnack = new Label("P.M. SNACK:");
	    afternoonSnack.getStyleClass().add("pre-label");
	    Label afternoonSnackValue = new Label();
	    afternoonSnackValue.getStyleClass().add("response-label");
	    boolean afternoonSnackStatus = dailySheet.isAfternoonSnack();
	    boolean afternoonAbsentStatus = dailySheet.isAfternoonWasntHere();
	    if(afternoonSnackStatus == true) {afternoonSnackValue.setText(student.getFirstName() + " enjoyed afternoon snack with his friends");
	    	}else if(afternoonAbsentStatus == true) {afternoonSnackValue.setText(student.getFirstName() + "was not here during afternoon snack");
	    	}else {afternoonSnackValue.setText(student.getFirstName() + "was not hungry and chose not to participate in afternoon snack");}
	    
	    Label lunch = new Label("LUNCH:");
	    lunch.getStyleClass().add("pre-label");
	    Label lunchValue = new Label();
	    lunchValue.getStyleClass().add("response-label");
	    boolean schoolLunchStatus = dailySheet.isSchoolLunch();
	    if(schoolLunchStatus == true) {
	    		lunchValue.setText(student.getFirstName() + " finished " + dailySheet.getMainCourse() + " of his main course, " + dailySheet.getVegetable() + " of his vegetables, and " + dailySheet.getFruit() + " of his fruit");
	    }else {lunchValue.setText("*" + student.getFirstName() + " ate a lunch from home");}
	    
	    viewDailySheetLayout.add(morningSnack,0,2);
	    GridPane.setHalignment(morningSnack,HPos.RIGHT);
	    viewDailySheetLayout.add(morningSnackValue,1,2);
	    GridPane.setHalignment(morningSnackValue,HPos.LEFT);
	    GridPane.setConstraints(morningSnackValue,1,2,5,1);
	    viewDailySheetLayout.add(afternoonSnack,0,3);
	    GridPane.setHalignment(afternoonSnack,HPos.RIGHT);
	    viewDailySheetLayout.add(afternoonSnackValue,1,3);
	    GridPane.setHalignment(afternoonSnackValue,HPos.LEFT);
	    GridPane.setConstraints(afternoonSnackValue,1,3,5,1);
	    viewDailySheetLayout.add(lunch,0,4);
	    GridPane.setHalignment(lunch,HPos.RIGHT);
	    viewDailySheetLayout.add(lunchValue,1,4);
	    GridPane.setHalignment(lunchValue,HPos.LEFT);
	    GridPane.setConstraints(lunchValue,1,4,5,1);
	    
	    Label nap = new Label("NAP:");
	    nap.getStyleClass().add("pre-label");
	    Label napValue = new Label();
	    napValue.getStyleClass().add("response-label");
	    boolean sleepStatus = dailySheet.isSleepStatus();
	    if(sleepStatus == true) {napValue.setText(student.getFirstName() + " slept from " + dailySheet.getNapStart() + " to " + dailySheet.getNapEnd());}
	    else {napValue.setText(student.getFirstName() + " rested from " + dailySheet.getNapStart() + " to " + dailySheet.getNapEnd());}
	    
	    viewDailySheetLayout.add(nap,0,5);
	    GridPane.setHalignment(nap,HPos.RIGHT);
	    viewDailySheetLayout.add(napValue,1,5);
	    GridPane.setHalignment(napValue,HPos.LEFT);
	    GridPane.setConstraints(napValue,1,5,5,1);
	    
	    Label morningCenters = new Label("A.M. CENTERS:");
	    morningCenters.getStyleClass().add("pre-label");
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
	    
	    viewDailySheetLayout.add(morningCenters,0,6);
	    GridPane.setHalignment(morningCenters,HPos.RIGHT);
	    viewDailySheetLayout.add(friends,1,6);
	    GridPane.setConstraints(friends,1,6,5,1);
	    GridPane.setHalignment(friends,HPos.LEFT);
	    
	    Label academicFocus = new Label("ACADEMICS:");
	    academicFocus.getStyleClass().add("pre-label");
	    Label preAc = new Label("Today's pre-academic focus was " + dailySheet.getPreAc() + "; " + student.getFirstName() + " was supposed to");
	    preAc.getStyleClass().add("response-label");
	    Label activityDescription = new Label(dailySheet.getActivityDescription());
	    activityDescription.getStyleClass().add("response-label");
	    Label teacherComment = new Label(dailySheet.getAcademicNote());
	    teacherComment.getStyleClass().add("response-label");
	    
	    viewDailySheetLayout.add(academicFocus,0,7);
	    GridPane.setHalignment(academicFocus,HPos.RIGHT);
	    viewDailySheetLayout.add(preAc,1,7);
	    GridPane.setConstraints(preAc,1,7,5,1);
	    GridPane.setHalignment(preAc,HPos.LEFT);
	    viewDailySheetLayout.add(activityDescription,1,8);
	    GridPane.setHalignment(activityDescription,HPos.LEFT);
	    GridPane.setConstraints(activityDescription,1,8,5,1);
	    viewDailySheetLayout.add(teacherComment,1,9);
	    GridPane.setConstraints(teacherComment,1,9,6,1);
	    GridPane.setHalignment(teacherComment,HPos.LEFT);
	    
	    Label observations = new Label("OBSERVED:");
	    observations.getStyleClass().add("pre-label");
	    boolean isFineMotor = dailySheet.isFineMotor();
	    String fineMotor;
	    Label fineMotorLabel = new Label("fine motor:");
	    fineMotorLabel.getStyleClass().add("label");
	    if(isFineMotor == true) {fineMotor = "X";}
	    else {fineMotor = "";}
	    Label fineMotorValue = new Label(fineMotor);
	    fineMotorValue.getStyleClass().add("response-label");
	    boolean isGrossMotor = dailySheet.isGrossMotor();
	    String grossMotor;
	    Label grossMotorLabel = new Label("gross motor:");
	    grossMotorLabel.getStyleClass().add("label");
	    if(isGrossMotor == true) {grossMotor = "X";}
	    else {grossMotor = "";}
	    Label grossMotorValue = new Label(grossMotor);
	    grossMotorValue.getStyleClass().add("response-label");
	    boolean isSocial = dailySheet.isSocial();
	    String social;
	    Label socialLabel = new Label("social:");
	    socialLabel.getStyleClass().add("label");
	    if(isSocial == true) {social = "X";}
	    else {social = "";}
	    Label socialValue = new Label(social);
	    socialValue.getStyleClass().add("response-label");
	    boolean isSelfHelp = dailySheet.isSelfHelp();
	    String selfHelp;
	    Label selfHelpLabel = new Label("self-help:");
	    selfHelpLabel.getStyleClass().add("label");
	    if(isSelfHelp == true) {selfHelp = "X";}
	    else {selfHelp = "";}
	    Label selfHelpValue = new Label(selfHelp);
	    selfHelpValue.getStyleClass().add("response-label");
	    boolean isCreative = dailySheet.isCreative();
	    String creative;
	    Label creativeLabel = new Label("creative:");
	    creativeLabel.getStyleClass().add("label");
	    if(isCreative == true) {creative = "X";}
	    else {creative = "";}
	    Label creativeValue = new Label(creative);
	    creativeValue.getStyleClass().add("response-label");
	    boolean isCognitive = dailySheet.isCognitive();
	    String cognitive;
	    Label cognitiveLabel = new Label("cognitive:");
	    cognitiveLabel.getStyleClass().add("label");
	    if(isCognitive == true) {cognitive = "X";}
	    else {cognitive = "";}
	    Label cognitiveValue = new Label(cognitive);
	    cognitiveValue.getStyleClass().add("response-label");
	    
	    viewDailySheetLayout.add(observations,0,10);
	    GridPane.setHalignment(observations,HPos.RIGHT);
	    viewDailySheetLayout.add(grossMotorLabel,0,11);
	    GridPane.setHalignment(grossMotorLabel,HPos.RIGHT);
	    viewDailySheetLayout.add(grossMotorValue,1,11);
	    GridPane.setHalignment(grossMotorValue,HPos.LEFT);
	    viewDailySheetLayout.add(fineMotorLabel,2,11);
	    GridPane.setHalignment(fineMotorLabel,HPos.RIGHT);
	    viewDailySheetLayout.add(fineMotorValue,3,11);
	    GridPane.setHalignment(fineMotorValue,HPos.LEFT);
	    viewDailySheetLayout.add(socialLabel,4,11);
	    GridPane.setHalignment(socialLabel,HPos.RIGHT);
	    viewDailySheetLayout.add(socialValue,5,11);
	    GridPane.setHalignment(socialValue,HPos.LEFT);
	    viewDailySheetLayout.add(selfHelpLabel,0,12);
	    GridPane.setHalignment(selfHelpLabel,HPos.RIGHT);
	    viewDailySheetLayout.add(selfHelpValue,1,12);
	    GridPane.setHalignment(selfHelpValue,HPos.LEFT);
	    viewDailySheetLayout.add(creativeLabel,2,12);
	    GridPane.setHalignment(creativeLabel,HPos.RIGHT);
	    viewDailySheetLayout.add(creativeValue,3,12);
	    GridPane.setHalignment(creativeValue,HPos.LEFT);
	    viewDailySheetLayout.add(cognitiveLabel,4,12);
	    GridPane.setHalignment(cognitiveLabel,HPos.RIGHT);
	    viewDailySheetLayout.add(cognitiveValue,5,12);
	    GridPane.setHalignment(cognitiveValue,HPos.LEFT);
	    
	    Label observationOne = new Label(dailySheet.getObservationOne());
	    observationOne.getStyleClass().add("response-label");
	    Label observationTwo = new Label(dailySheet.getObservationTwo());
	    observationTwo.getStyleClass().add("response-label");
	    
	    viewDailySheetLayout.add(observationOne,1,13);
	    GridPane.setHalignment(observationOne,HPos.LEFT);
	    GridPane.setConstraints(observationOne,1,13,5,1);
	    viewDailySheetLayout.add(observationTwo,1,14);
	    GridPane.setHalignment(observationTwo,HPos.LEFT);
	    GridPane.setConstraints(observationTwo,1,14,5,1);
		
		Scene viewDailySheetScene = new Scene(viewDailySheetLayout);
		stage.setScene(viewDailySheetScene);
		stage.showAndWait();
		
	}
	
}
