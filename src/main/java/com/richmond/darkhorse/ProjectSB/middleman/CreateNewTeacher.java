package com.richmond.darkhorse.ProjectSB.middleman;
import com.richmond.darkhorse.ProjectSB.Admin;
import com.richmond.darkhorse.ProjectSB.Center;
import com.richmond.darkhorse.ProjectSB.Teacher;
import com.richmond.darkhorse.ProjectSB.Classroom;

public class CreateNewTeacher implements Runnable{

	private Admin admin;
	private String firstName,lastName;
	private Center center; 
	private Classroom classroom;
	
	//Constructor for a teacher WITH a {@link Classroom}
	public CreateNewTeacher(Admin admin,String firstName,String lastName,Center center,Classroom classroom) {
		this.admin = admin;
		this.firstName = firstName;
		this.lastName = lastName;
		this.center = center;
		this.classroom = classroom;
	}
	
	//Constructor for a teacher WITHOUT a {@link Classroom}
	public CreateNewTeacher(Admin admin,String firstName,String lastName,Center center) {
		this.admin = admin;
		this.firstName = firstName;
		this.lastName = lastName;
		this.center = center;
	}

	@Override
	public void run() {
		if(classroom != null) {
			Teacher teacher = admin.createTeacherWithClassroom(firstName, lastName, center, classroom);
			admin.addNewStaffMember(teacher);
			center.addTeacher(teacher);
		}
		else {
			Teacher teacher = admin.createTeacherWithoutClassroom(firstName, lastName, center);
			admin.addNewStaffMember(teacher);
		}
	}
	
}
