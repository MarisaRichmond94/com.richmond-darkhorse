package com.richmond.darkhorse.ProjectSB.middleman;
import com.richmond.darkhorse.ProjectSB.Admin;
import com.richmond.darkhorse.ProjectSB.Center;
import com.richmond.darkhorse.ProjectSB.Teacher;

public class CreateNewTeacher implements Runnable{

	private Admin admin;
	private String firstName,lastName;
	private Center center; 
	
	public CreateNewTeacher(Admin admin,String firstName,String lastName,Center center) {
		this.admin = admin;
		this.firstName = firstName;
		this.lastName = lastName;
		this.center = center;
	}

	@Override
	public void run() {
		Teacher teacher = admin.createTeacher(firstName, lastName, center);
		admin.addNewStaffMember(teacher);
	}
	
}
