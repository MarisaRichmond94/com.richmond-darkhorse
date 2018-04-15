package com.richmond.darkhorse.ProjectSB;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DailySheet extends Document implements Serializable {

	private static final long serialVersionUID = 8453756L;
	private boolean morningSnack, afternoonSnack, schoolLunch, lunchFromHome, sleepStatus, restStatus, grossMotor, fineMotor, cognitive, social, creative, selfHelp, morningWasntHere,afternoonWasntHere;
	private String date, mainCourse, vegetable, napStart, napEnd, fruit, preAc, activityDescription, academicNote, observationOne, observationTwo;
	private List<String> friends, activities;

    public DailySheet(Teacher teacher,Student student,String date){
    		super(teacher,student);
    		title = "DailySheet";
        this.friends = new ArrayList<String>();
        this.activities = new ArrayList<String>();
        this.fileName = student.getStudentID() + title + formattedDate;
    }

    //Standard getters and setters
	public boolean isMorningSnack() {
		return morningSnack;
	}

	public String getActivityDescription() {
		return activityDescription;
	}

	public void setActivityDescription(String activityDescription) {
		this.activityDescription = activityDescription;
	}

	public String getAcademicNote() {
		return academicNote;
	}

	public void setAcademicNote(String academicNote) {
		this.academicNote = academicNote;
	}

	public String getNapStart() {
		return napStart;
	}

	public void setNapStart(String napStart) {
		this.napStart = napStart;
	}

	public String getNapEnd() {
		return napEnd;
	}

	public void setNapEnd(String napEnd) {
		this.napEnd = napEnd;
	}

	public String getStringDate() {
		return date;
	}

	public void setStringDate(String date) {
		this.date = date;
	}

	public void setMorningSnack(boolean morningSnack) {
		this.morningSnack = morningSnack;
	}

	public boolean isMorningWasntHere() {
		return morningWasntHere;
	}

	public void setMorningWasntHere(boolean morningWasntHere) {
		this.morningWasntHere = morningWasntHere;
	}

	public boolean isAfternoonWasntHere() {
		return afternoonWasntHere;
	}

	public void setAfternoonWasntHere(boolean afternoonWasntHere) {
		this.afternoonWasntHere = afternoonWasntHere;
	}

	public boolean isAfternoonSnack() {
		return afternoonSnack;
	}

	public void setAfternoonSnack(boolean afternoonSnack) {
		this.afternoonSnack = afternoonSnack;
	}

	public boolean isSchoolLunch() {
		return schoolLunch;
	}

	public void setSchoolLunch(boolean schoolLunch) {
		this.schoolLunch = schoolLunch;
	}

	public boolean isLunchFromHome() {
		return lunchFromHome;
	}

	public void setLunchFromHome(boolean lunchFromHome) {
		this.lunchFromHome = lunchFromHome;
	}

	public boolean isSleepStatus() {
		return sleepStatus;
	}

	public void setSleepStatus(boolean sleepStatus) {
		this.sleepStatus = sleepStatus;
	}

	public boolean isRestStatus() {
		return restStatus;
	}

	public void setRestStatus(boolean restStatus) {
		this.restStatus = restStatus;
	}

	public boolean isGrossMotor() {
		return grossMotor;
	}

	public void setGrossMotor(boolean grossMotor) {
		this.grossMotor = grossMotor;
	}

	public boolean isFineMotor() {
		return fineMotor;
	}

	public void setFineMotor(boolean fineMotor) {
		this.fineMotor = fineMotor;
	}

	public boolean isCognitive() {
		return cognitive;
	}

	public void setCognitive(boolean cognitive) {
		this.cognitive = cognitive;
	}

	public boolean isSocial() {
		return social;
	}

	public void setSocial(boolean social) {
		this.social = social;
	}

	public boolean isCreative() {
		return creative;
	}

	public void setCreative(boolean creative) {
		this.creative = creative;
	}

	public boolean isSelfHelp() {
		return selfHelp;
	}

	public void setSelfHelp(boolean selfHelp) {
		this.selfHelp = selfHelp;
	}

	public String getMainCourse() {
		return mainCourse;
	}

	public void setMainCourse(String mainCourse) {
		this.mainCourse = mainCourse;
	}

	public String getVegetable() {
		return vegetable;
	}

	public void setVegetable(String vegetable) {
		this.vegetable = vegetable;
	}

	public String getFruit() {
		return fruit;
	}

	public void setFruit(String fruit) {
		this.fruit = fruit;
	}

	public String getPreAc() {
		return preAc;
	}

	public void setPreAc(String preAc) {
		this.preAc = preAc;
	}

	public String getObservationOne() {
		return observationOne;
	}

	public void setObservationOne(String observationOne) {
		this.observationOne = observationOne;
	}

	public String getObservationTwo() {
		return observationTwo;
	}

	public void setObservationTwo(String observationTwo) {
		this.observationTwo = observationTwo;
	}

	public List<String> getFriends() {
		return friends;
	}

	public void setFriends(List<String> friends) {
		this.friends = friends;
	}

	public List<String> getActivities() {
		return activities;
	}

	public void setActivities(List<String> activities) {
		this.activities = activities;
	}
    
}
