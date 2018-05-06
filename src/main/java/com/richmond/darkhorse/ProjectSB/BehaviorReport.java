package com.richmond.darkhorse.ProjectSB;
import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/**
 * subclass of {@link Document} containing a snapshot of a {@link Student}'s behavior
 * @author marisarichmond
 */
public class BehaviorReport extends Document implements Serializable{

	private static final long serialVersionUID = 8453756L;
	private String signingTeacher, activityDuringBehavior, childsBehavior, consequence, studentResponse, durationBehaviorOccurred, teacherObserved;
	private Boolean teacherSignature, directorSignature = false;

	public BehaviorReport(Student student,Teacher teacher) {
		super(teacher,student);
		title = "BehaviorReport";
		this.fileName = student.getStudentID() + title + formattedDate;
		AccountManager accountManager = AccountManager.getInstance();
		this.documentID = accountManager.generateDocumentID(this);
	}
	
	//Standard getters and setters
	public void setDate(Date date) {
		this.date = date;
	}
	
	public void setSigningTeacher(Teacher teacherVal) {
		this.signingTeacher = teacherVal.getFirstName() + " " + teacherVal.getLastName();
	}
	
	public String getSigningTeacher() {
		return signingTeacher;
	}

	public String getActivityDuringBehavior() {
		return activityDuringBehavior;
	}

	public void setActivityDuringBehavior(String activityDuringBehavior) {
		this.activityDuringBehavior = activityDuringBehavior;
	}

	public String getChildsBehavior() {
		return childsBehavior;
	}

	public void setChildsBehavior(String childsBehavior) {
		this.childsBehavior = childsBehavior;
	}

	public String getConsequence() {
		return consequence;
	}

	public void setConsequence(String consequence) {
		this.consequence = consequence;
	}

	public String getStudentResponse() {
		return studentResponse;
	}

	public void setStudentResponse(String studentResponse) {
		this.studentResponse = studentResponse;
	}

	public String getDurationBehaviorOccurred() {
		return durationBehaviorOccurred;
	}

	public void setDurationBehaviorOccurred(String durationBehaviorOccurred) {
		this.durationBehaviorOccurred = durationBehaviorOccurred;
	}

	public String getTeacherObserved() {
		return teacherObserved;
	}

	public void setTeacherObserved(String teacherObserved) {
		this.teacherObserved = teacherObserved;
	}

	public Boolean getTeacherSignature() {
		return teacherSignature;
	}

	public void setTeacherSignature(Boolean teacherSignature) {
		this.teacherSignature = teacherSignature;
	}

	public Boolean getDirectorSignature() {
		return directorSignature;
	}

	public void setDirectorSignature(Boolean directorSignature) {
		this.directorSignature = directorSignature;
	}

	public String getFileName() {
		return fileName;
	}
	
	public Student getStudent(String studentID) {
		Center center = this.getCenter(this.getCenterID());
		Map<String,Student> students = center.getStudents();
		if(students.containsKey(studentID)) {
			return students.get(studentID);
		}
		return null;
	}
	
}
