package com.richmond.darkhorse.ProjectSB;
import java.io.Serializable;
import java.util.Map;

public class Teacher extends StaffMember implements Serializable {

	private static final long serialVersionUID = 8453756L;
	private String teacherID;

	//Constructor for a teacher WITHOUT a {@link Classroom}
	public Teacher(String firstName, String lastName, Center center) {
		super(firstName, lastName, center);
		title = "Teacher";
		AccountManager accountManager = AccountManager.getInstance();
		this.userID = accountManager.generateUserID(this);
		this.teacherID = this.getUserID();
		Credential credentials = new Credential(firstName,lastName,title);
		this.credentials = credentials;
		this.contact = new Contact(userID,firstName,lastName);
	}

	/**
	 * This method uses a teacher's centerID to get all of the classrooms within the teacher's {@link Center} and then searches those classrooms using the teacher's 
	 * classroomID in order to find the classroom belonging to that teacher
	 * @param classroomID - the 4-digit ID linked to the teacher's classroom
	 * @return - the classroom with the 4-digit classroomID matching the teacher's 4-digit classroomID
	 */
	public Classroom getClassroom(String classroomID) {
		Center center = this.getCenter(centerID);
		Map<String,Classroom> classrooms = center.getClassrooms();
		if(classrooms.containsKey(classroomID)) {return classrooms.get(classroomID);}
		return null;
	}

	public String getTeacherID() {
		return teacherID;
	}

	public void setTeacherID(String teacherID) {
		this.teacherID = teacherID;
	}

	@Override
	public String toString() {
		return firstName + " " + lastName;
	}

}
