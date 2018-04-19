package com.richmond.darkhorse.ProjectSB;
import java.io.Serializable;

public class Credential implements Serializable {
	
	private static final long serialVersionUID = 8453756L;
	private String firstName, lastName, title;
	
	public Credential(String firstName,String lastName,String title) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.title = title;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getTitle() {
		return title;
	}
	
	@Override
	public String toString() {
		return firstName + " " + lastName + ", " + title;
	}

}
