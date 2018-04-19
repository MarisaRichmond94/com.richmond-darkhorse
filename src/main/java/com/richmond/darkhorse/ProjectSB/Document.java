package com.richmond.darkhorse.ProjectSB;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class Document implements Serializable {

	private static final long serialVersionUID = 8453756L;
	protected String title, studentName, studentID, teacherID, documentID, centerID, formattedDate, stringDate, stringTime;
	protected Date date;
	protected String fileName;
	
	public Document(Teacher teacher,Student student) {
		this.studentName = student.getFirstName() + " " + student.getLastName();
		this.teacherID = teacher.getTeacherID();
		this.studentID = student.getStudentID();
		this.centerID = teacher.getCenterID();
		this.date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd hh:mm a");
		this.formattedDate = dateFormat.format(date);
	}

	public String getTitle() {
		return title;
	}

	public String getStringDate() {
		return stringDate;
	}

	public void setStringDate(String stringDate) {
		this.stringDate = stringDate;
	}

	public String getStringTime() {
		return stringTime;
	}

	public void setStringTime(String stringTime) {
		this.stringTime = stringTime;
	}

	public String getStudentID() {
		return studentID;
	}

	public String getTeacherID() {
		return teacherID;
	}

	public String getDocumentID() {
		return documentID;
	}
	
	public String getCenterID() {
		return centerID;
	}
	
	public String getFormattedDate() {
		return formattedDate;
	}

	public Date getDate() {
		return date;
	}

	public Center getCenter(String centerID) {
		SpecialBeginnings sB = SpecialBeginnings.getInstance();
		Map<String,Center> centers = sB.getCenters();
		if(centers.containsKey(centerID)) {
			return centers.get(centerID);
		}
		return null;
	}
	
	public String getStudentName() {
		return studentName; 
	}
	
	public Teacher getTeacher(String teacherID) {
		SpecialBeginnings sB = SpecialBeginnings.getInstance();
		Map<String,StaffMember> staffMembers = sB.getStaffMembers();
		if(staffMembers.containsKey(teacherID)) {
			return (Teacher) staffMembers.get(teacherID);
		}
		return null;
	}
	
	@Override
	public String toString() {
		return "" + title + " " + this.getStudentName() + " " + this.getStringDate() + " " + this.getStringTime();
	}
	
}
