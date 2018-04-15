package com.richmond.darkhorse.ProjectSB.middleman;
import com.richmond.darkhorse.ProjectSB.Director;
import com.richmond.darkhorse.ProjectSB.Student;

public class CreateNewContact implements Runnable{

	private Director director;
	private Student student;
	private String firstName,lastName,relationshipToStudent,workNumber,cellNumber,email;
	
	public CreateNewContact(Director director,Student student,String firstName,String lastName,String relationshipToStudent,String workNumber,String cellNumber,String email) {
		this.director = director;
		this.student = student;
		this.firstName = firstName;
		this.lastName = lastName;
		this.relationshipToStudent = relationshipToStudent;
		this.workNumber = workNumber;
		this.cellNumber = cellNumber;
		this.email = email;
	}
	
	@Override
	public void run() {
		director.addNewStudentContact(student,firstName, lastName, relationshipToStudent, cellNumber, workNumber, email);
	}

}
