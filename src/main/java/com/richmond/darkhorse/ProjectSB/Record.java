package com.richmond.darkhorse.ProjectSB;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Record implements Serializable {

	private static final long serialVersionUID = 8453756L;
	private String recordID, studentID;
	private Map<String,DailySheet> dailySheets;
	private Map<String,BehaviorReport> behaviorReports;
	private Map<String,IncidentReport> incidentReports;
	private List<String> teacherComments;
	private Attendance attendance;
	private List<Contact> contacts;
    
    public Record(Student student) {
    		this.recordID = generateRecordID(student.getStudentID());
    		this.studentID = student.getStudentID();
    		this.dailySheets = new HashMap<String,DailySheet>();
    		this.behaviorReports = new HashMap<String,BehaviorReport>();
    		this.incidentReports = new HashMap<String,IncidentReport>();
    		this.teacherComments = new ArrayList<String>();
    		this.attendance = new Attendance(studentID,recordID);
    		this.contacts = new ArrayList<Contact>();
    }
    
    public String generateRecordID(String studentID) {
    		int a = Character.digit(studentID.charAt(0), 10);
    		String aString = Integer.toString(a);
    		int b = Character.digit(studentID.charAt(1), 10);
    		String bString = Integer.toString(b);
    		int c = Character.digit(studentID.charAt(2), 10);
    		String cString = Integer.toString(c);
    		int d = Character.digit(studentID.charAt(3), 10);
    		String dString = Integer.toString(d);
    		String recordID = aString + bString + cString + dString;
    		return recordID;
    }
    
    //Add methods
    public void addDailySheet(DailySheet dailySheet){
    		dailySheets.put(dailySheet.getDocumentID(), dailySheet);
    }
    
    public void addBehaviorReport(BehaviorReport behaviorReport){
		behaviorReports.put(behaviorReport.getDocumentID(), behaviorReport);
    }
    
    public void addIncidentReport(IncidentReport incidentReport){
		incidentReports.put(incidentReport.getDocumentID(), incidentReport);
    }
    
    public void addTeacherComment(String teacherComment) {
    		teacherComments.add(teacherComment);
    }

    //Standard getters and setters
	public Map<String, DailySheet> getDailySheets() {
		return dailySheets;
	}

	public void setDailySheets(Map<String, DailySheet> dailySheets) {
		this.dailySheets = dailySheets;
	}

	public Map<String, BehaviorReport> getBehaviorReports() {
		return behaviorReports;
	}

	public void setBehaviorReports(Map<String, BehaviorReport> behaviorReports) {
		this.behaviorReports = behaviorReports;
	}

	public Map<String, IncidentReport> getIncidentReports() {
		return incidentReports;
	}

	public void setIncidentReports(Map<String, IncidentReport> incidentReports) {
		this.incidentReports = incidentReports;
	}

	public String getRecordID() {
		return recordID;
	}

	public String getStudentID() {
		return studentID;
	}

	public Attendance getAttendance() {
		return attendance;
	}
	
	public List<String> getTeacherComments(){
		return teacherComments;
	}
	
	public void addContact(Contact contact) {
		contacts.add(contact);
	}
	
	public List<Contact> getContacts(){
		return contacts;
	}
	
	public void setContacts(List<Contact> contacts) {
		this.contacts = contacts;
	}

}
