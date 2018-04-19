package com.richmond.darkhorse.ProjectSB;

import java.io.Serializable;

public class Contact implements Serializable{

	private static final long serialVersionUID = 8453756L;
	private String iD, firstName, lastName, relationshipToStudent, cellNumber, workNumber, email;
	
	public Contact(String studentID,String firstName,String lastName,String relationship,String cellNumber,String workNumber,String email) {
		this.iD = studentID;
		this.firstName = firstName;
		this.lastName = lastName;
		this.relationshipToStudent = relationship;
		this.cellNumber = cellNumber;
		this.workNumber = workNumber;
		this.email = email;
	}
	
	public Contact(String teacherID,String firstName,String lastName) {
		this.iD = teacherID;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public String getID() {
		return iD;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getRelationshipToStudent() {
		return relationshipToStudent;
	}

	public String getCellNumber() {
		return cellNumber;
	}

	public String getWorkNumber() {
		return workNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setCellNumber(String cellNumber) {
		this.cellNumber = cellNumber;
	}

	public void setWorkNumber(String workNumber) {
		this.workNumber = workNumber;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setRelationshipToStudent(String relationshipToStudent) {
		this.relationshipToStudent = relationshipToStudent;
	}

	@Override
	public String toString() {
		return this.getFirstName() + " " + this.getLastName();
	}
	
}
