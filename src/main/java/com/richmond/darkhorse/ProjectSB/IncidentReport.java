package com.richmond.darkhorse.ProjectSB;
import java.io.Serializable;

/**
 * The IncidentReport Class serves as a blueprint for a standard behavior report, which requires both a 
 * director's and a teacher's signature in order to be stored in the record 
 * @author marisarichmond
 */

public class IncidentReport extends Document implements Serializable{

	private static final long serialVersionUID = 8453756L;
	private String fileName, studentDOB, typeOfReport, incidentDescription, actionTaken, studentCondition, teachersPresent, comments;
	private boolean medicalAttention, onSite, teacherSignature, directorSignature = false;
	
	public IncidentReport(Student student,Teacher teacher) {
		super(teacher,student);
		this.title = "IncidentReport";
		this.studentDOB = student.getBirthDate();
		this.teachersPresent = teacher.getFirstName() + " " + teacher.getLastName();
		this.fileName = student.getStudentID() + title + formattedDate;
		AccountManager accountManager = AccountManager.getInstance();
		this.documentID = accountManager.generateDocumentID(this);
	}

	//Standard getters and setters
	public String getTypeOfReport() {
		return typeOfReport;
	}

	public boolean isOnSite() {
		return onSite;
	}

	public void setOnSite(boolean onSite) {
		this.onSite = onSite;
	}

	public void setTypeOfReport(String typeOfReport) {
		this.typeOfReport = typeOfReport;
	}

	public String getIncidentDescription() {
		return incidentDescription;
	}

	public void setIncidentDescription(String incidentDescription) {
		this.incidentDescription = incidentDescription;
	}

	public String getActionTaken() {
		return actionTaken;
	}

	public void setActionTaken(String actionTaken) {
		this.actionTaken = actionTaken;
	}

	public String getStudentCondition() {
		return studentCondition;
	}

	public void setStudentCondition(String studentCondition) {
		this.studentCondition = studentCondition;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public boolean isMedicalAttention() {
		return medicalAttention;
	}

	public void setMedicalAttention(boolean medicalAttention) {
		this.medicalAttention = medicalAttention;
	}

	public boolean isTeacherSignature() {
		return teacherSignature;
	}

	public void setTeacherSignature(boolean teacherSignature) {
		this.teacherSignature = teacherSignature;
	}

	public boolean isDirectorSignature() {
		return directorSignature;
	}

	public void setDirectorSignature(boolean directorSignature) {
		this.directorSignature = directorSignature;
	}

	public String getFileName() {
		return fileName;
	}

	public String getStudentDOB() {
		return studentDOB;
	}

	public String getTeachersPresent() {
		return teachersPresent;
	}
	
	public void setTeachersPresent(String teachersPresent) {
		this.teachersPresent = teachersPresent;
	}
	
}
