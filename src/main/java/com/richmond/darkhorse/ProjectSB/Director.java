package com.richmond.darkhorse.ProjectSB;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Director extends StaffMember implements Serializable {

	private static final long serialVersionUID = 8453756L;
	private String directorID;
	
	public Director(String firstName,String lastName,Center center) {
		super(firstName,lastName,center);
		title = "Director";
		AccountManager accountManager = AccountManager.getInstance();
		this.userID = accountManager.generateUserID(this);
		this.directorID = this.getUserID();
		Credential credentials = new Credential(firstName,lastName,title);
		this.credentials = credentials;
		this.contact = new Contact(userID,firstName,lastName);
	}
	
	//Creation methods
	public void createNewStudent(String firstName,String lastName,String birthDate,Classroom classroom,Map<String,Boolean> attendancePlan) {
		Student student = new Student(firstName,lastName,birthDate);
		Center center = this.getCenter(this.getCenterID());
		center.addStudent(student);
		Record record = student.getRecord();
		Attendance attendance = record.getAttendance();
		attendance.setAttendancePlan(attendancePlan);
		if(classroom != null) {
			student.setClassroomID(classroom.getClassroomID());
			classroom.addStudentEnrolled(student);
		}
	}
	
	public void createNewStudentWithoutClassroom(String firstName,String lastName,String birthDate,Map<String,Boolean> attendancePlan) {
		Student student = new Student(firstName,lastName,birthDate);
		Center center = this.getCenter(this.getCenterID());
		center.addStudent(student);
		Record record = student.getRecord();
		Attendance attendance = record.getAttendance();
		attendance.setAttendancePlan(attendancePlan);
	}
	
	public Event createNewEvent(String summary,String day,String time,String details) {
		Event newEvent = new Event(summary,day,time,details);
		return newEvent;
	}
	
	//Modification methods
	public void modifyStaffSchedule(StaffMember staffMember,Map<Integer,String> newStartTimes,Map<Integer,String> newStopTimes) {
		Schedule schedule = staffMember.getSchedule();
		schedule.setStartTimes(newStartTimes);
		schedule.setStopTimes(newStopTimes);
	}
	
	public void modifyClassSize(Classroom classroom,int maxSize) {
		classroom.setMaxSize(maxSize);
	}
	
	public void modifyStudent(Student student,Classroom classroom,Map<String,Boolean> attendancePlan) {
		if(student.getClassroomID() == null && classroom != null) {
			student.setClassroomID(classroom.getClassroomID());
			classroom.addStudentEnrolled(student);
		}else if(student.getClassroomID() != null && classroom != null && student.getClassroomID() != classroom.getClassroomID()) {
			Classroom oldClassroom = student.getClassroom(student.getClassroomID());
			oldClassroom.removeStudentEnrolled(student);
			student.setClassroomID(classroom.getClassroomID());
			classroom.addStudentEnrolled(student);
		}else if(student.getClassroomID() != null && classroom == null) {
			Classroom oldClassroom = student.getClassroom(student.getClassroomID());
			oldClassroom.removeStudentEnrolled(student);
			student.setClassroomID(null);
		}
		student.getRecord().getAttendance().setAttendancePlan(attendancePlan);
	}
	
	public void modifyStudentContact(Contact contact,Student student,String firstName,String lastName,String relationship,String cellNumber,String workNumber,String email) {
		contact.setFirstName(firstName);
		contact.setLastName(lastName);
		contact.setRelationshipToStudent(relationship);
		contact.setWorkNumber(workNumber);
		contact.setCellNumber(cellNumber);
		contact.setEmail(email);
	}
	
	public void modifyBehaviorReport(BehaviorReport behaviorReport,String activityDuringBehavior,String childsBehavior,String consequence,String studentResponse,String durationBehaviorOccurred) {
		if(activityDuringBehavior != null) {behaviorReport.setActivityDuringBehavior(activityDuringBehavior);}
		if(childsBehavior != null) {behaviorReport.setChildsBehavior(childsBehavior);}
		if(consequence != null) {behaviorReport.setConsequence(consequence);}
		if(studentResponse != null) {behaviorReport.setStudentResponse(studentResponse);}
		if(durationBehaviorOccurred != null) {behaviorReport.setDurationBehaviorOccurred(durationBehaviorOccurred);}
	}
	
	public void modifyIncidentReport(IncidentReport incidentReport,String incidentDescription,String actionTaken,String studentCondition,String comments) {
		if(incidentDescription != null) {incidentReport.setIncidentDescription(incidentDescription);}
		if(actionTaken != null) {incidentReport.setActionTaken(actionTaken);}
		if(studentCondition != null) {incidentReport.setStudentCondition(studentCondition);}
		if(comments != null) {incidentReport.setComments(comments);}
	}
	
	//Removal methods
	public void deleteStudent(Student student) {
		Classroom classroom = student.getClassroom(student.getClassroomID());
		Center center = classroom.getCenter(classroom.getCenterID());
		classroom.removeStudentEnrolled(student);
		center.removeStudent(student);
	}
	
	public void removeStudentFromClassroom(Student student,Classroom classroom) {
		classroom.removeStudentEnrolled(student);
		student.setClassroomID(null);
	}
	
	public void deleteStudentContact(Student student,Contact contact) {
		List<Contact> contacts = student.getRecord().getContacts();
		boolean status = false;
		int removeContact = 0;
		int index = 0;
		for(Contact contactCheck : contacts) {
			if(contactCheck.equals(contact)) {
				removeContact = index;
				status = true;
			}
			index++;
		}
		if(status == true) {contacts.remove(removeContact);}
		student.getRecord().setContacts(contacts);
	}
	
	public void clearSchedules() {
		Map<String,StaffMember> staff = this.getStaffMembers();
		List<Teacher> teachers = new ArrayList<Teacher>();
		for(StaffMember staffMember: staff.values()) {
			if(staffMember.getTitle().equals("Teacher")) {teachers.add((Teacher) staffMember);}
		}
		for(Teacher teacher : teachers) {teacher.getSchedule().clear();}
	}
	
	//Add methods
	public void addNewStudentContact(Student student,String firstName,String lastName,String relationship,String cellNumber,String workNumber,String email) {
		Contact contact = new Contact(student.getStudentID(),firstName,lastName,relationship,cellNumber,workNumber,email);
		student.getRecord().addContact(contact);
	}
	
	public void addStudentToClassroom(Student student,Classroom classroom) {
		classroom.addStudentEnrolled(student);
		student.setClassroomID(classroom.getClassroomID());
	}
	
	public void addSignedReport(Document document) {
		if(document.getTitle().equals("BehaviorReport")) {
			mailbox.addSignedBehaviorReport((BehaviorReport) document);
		}else {
			mailbox.addSignedIncidentReport((IncidentReport) document);
		}
	}
	
	//Report methods
	public void returnAllReports() {
		for(BehaviorReport signedBR : this.getMailbox().getSignedBehaviorReports().values()) {
			Teacher teacher = signedBR.getTeacher(signedBR.getTeacherID());
			teacher.getMailbox().addSignedBehaviorReport(signedBR);
			this.removeBehaviorReport(signedBR.getDocumentID());
		}
		for(IncidentReport signedIR : this.getMailbox().getSignedIncidentReports().values()) {
			Teacher teacher = signedIR.getTeacher(signedIR.getTeacherID());
			teacher.getMailbox().addSignedIncidentReport(signedIR);
			this.removeIncidentReport(signedIR.getDocumentID());
		}
	}
	
	public void removeBehaviorReport(String documentID) {
		if(this.getMailbox().getSignedBehaviorReports().containsKey(documentID)) {
			this.getMailbox().getSignedBehaviorReports().remove(documentID);
		}
	}
	
	public void removeIncidentReport(String documentID) {
		if(this.getMailbox().getSignedIncidentReports().containsKey(documentID)) {
			this.getMailbox().getSignedIncidentReports().remove(documentID);
		}
	}
	
    //Standard getters and setters
	public String getDirectorID() {
		return directorID;
	}
	
	public void setDirectorID(String directorID) {
		this.directorID = directorID;
	}
	
	public Map<String,Center> getCenters(){
		Map<String,Center> centers = SpecialBeginnings.getInstance().getCenters();
		return centers;
	}
	
	public Map<String,StaffMember> getStaffMembers(){
		Map<String,StaffMember> staffMembers = SpecialBeginnings.getInstance().getStaffMembers();
		return staffMembers;
	}
	
	public Map<String,Student> getStudents(){
		Center center = this.getCenter(this.getCenterID());
		return center.getStudents();
	}
	
	public Map<String,Classroom> getClassrooms(){
		Center center = this.getCenter(this.getCenterID());
		return center.getClassrooms();
	}
	
	public Map<String,Schedule> getSchedules(){
		Center center = this.getCenter(this.getCenterID());
		return center.getStaffSchedules();
	}

	@Override
	public String toString() {
		return firstName + " " + lastName + ", employee #" + userID;
	}
	
}
