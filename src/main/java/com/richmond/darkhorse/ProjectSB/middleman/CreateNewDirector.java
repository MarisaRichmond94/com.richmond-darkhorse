package com.richmond.darkhorse.ProjectSB.middleman;
import com.richmond.darkhorse.ProjectSB.Admin;
import com.richmond.darkhorse.ProjectSB.Center;
import com.richmond.darkhorse.ProjectSB.Director;

public class CreateNewDirector implements Runnable{

	private Admin admin;
	private String firstName,lastName;
	private Center center; 
	
	public CreateNewDirector(Admin admin,String firstName,String lastName,Center center) {
		this.admin = admin;
		this.firstName = firstName;
		this.lastName = lastName;
		this.center = center;
	}

	@Override
	public void run() {
		Director director = admin.createDirector(firstName,lastName,center);
		admin.addNewStaffMember(director);
	}
	
}
