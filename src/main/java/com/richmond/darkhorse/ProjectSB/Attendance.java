package com.richmond.darkhorse.ProjectSB;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Attendance implements Serializable{

	private static final long serialVersionUID = 8453756L;
	private String studentID;
	private String recordID;
	private Map<String,Boolean> attendancePlan;
	private Map<String,Boolean> attendanceHistory;
	
	public Attendance(String studentID,String recordID) {
		this.studentID = studentID;
		this.recordID = recordID;
		this.attendancePlan = new HashMap<String,Boolean>();
	}

	//Standard getters and setters
	public Map<String,Boolean> getAttendancePlan() {
		return attendancePlan;
	}
	
	public void setAttendancePlan(Map<String,Boolean> attendancePlan) {
		this.attendancePlan = attendancePlan;
	}

	public Map<String,Boolean> getAttendanceHistory() {
		return attendanceHistory;
	}

	public String getStudentID() {
		return studentID;
	}

	public String getRecordID() {
		return recordID;
	}

	public void setAttendanceHistory(Map<String, Boolean> attendanceHistory) {
		this.attendanceHistory = attendanceHistory;
	}
	
}

