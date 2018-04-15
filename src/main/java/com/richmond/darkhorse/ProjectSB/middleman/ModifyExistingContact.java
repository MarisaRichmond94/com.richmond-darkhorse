package com.richmond.darkhorse.ProjectSB.middleman;
import com.richmond.darkhorse.ProjectSB.Contact;
import com.richmond.darkhorse.ProjectSB.Director;
import com.richmond.darkhorse.ProjectSB.Student;

public class ModifyExistingContact implements Runnable{
	
	private Director director;
	private Student student;
	private Contact contact;
	private String firstName,lastName,relationship,workNumber,cellNumber,email;
	
	public ModifyExistingContact(Director director,Contact contact,Student student,String firstName,String lastName,String relationship,String workNumber,String cellNumber,String email) {
		this.director = director;
		this.student = student;
		this.contact = contact;
		this.firstName = firstName;
		this.lastName = lastName;
		this.relationship = relationship;
		this.workNumber = workNumber;
		this.cellNumber = cellNumber;
		this.email = email;
	}

	@Override
	public void run() {
		director.modifyStudentContact(contact, student, firstName, lastName, relationship, cellNumber, workNumber, email);
	}

}
