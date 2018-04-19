package com.richmond.darkhorse.ProjectSB;
import java.io.Serializable;
import java.util.Map;

/**
 * The Student Class serves as the blueprint for a standard student 
 * @author marisarichmond
 */

public class Student implements Serializable{

	private static final long serialVersionUID = 8453756L;
	private String firstName, lastName, birthDate, studentID, teacherID, classroomID;
	private Record record;

    public Student(String firstName,String lastName,String birthDate){
    		this.firstName = firstName;
    		this.lastName = lastName;
    		this.birthDate = birthDate;
    		AccountManager accountManager = AccountManager.getInstance();
    		this.studentID = accountManager.generateStudentID(this);
    		this.record = new Record(this);
    }
    
	//Standard getters and setters
	public String getTeacherID() {
		return teacherID;
	}

	public void setTeacherID(String teacherID) {
		this.teacherID = teacherID;
	}

	public String getClassroomID() {
		return classroomID;
	}

	public void setClassroomID(String classroomID) {
		this.classroomID = classroomID;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getBirthDate() {
		return birthDate;
	}

	public String getStudentID() {
		return studentID;
	}

	public Record getRecord() {
		return record;
	}
	
	public Teacher getTeacher(String teacherID) {
		Map<String,StaffMember> staffMembers = SpecialBeginnings.getInstance().getStaffMembers();
		if(staffMembers.containsKey(teacherID) && staffMembers.get(teacherID).getTitle().equals("Teacher")) {return (Teacher) staffMembers.get(teacherID);}
		return null;
	}
	
	public Classroom getClassroom(String classroomID) {
		SpecialBeginnings sB = SpecialBeginnings.getInstance();
		Map<String,Center> centers = sB.getCenters();
		for(Center center : centers.values()) {
			Map<String,Classroom> classrooms = center.getClassrooms();
			if(classrooms.containsKey(this.getClassroomID())) {return classrooms.get(this.getClassroomID());}
		}
		return null;
	}

	@Override
	public String toString() {
		return "" + firstName + " " + lastName + "";
	}
	
}
