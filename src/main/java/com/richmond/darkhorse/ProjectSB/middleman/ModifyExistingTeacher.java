package com.richmond.darkhorse.ProjectSB.middleman;
import com.richmond.darkhorse.ProjectSB.Admin;
import com.richmond.darkhorse.ProjectSB.Center;
import com.richmond.darkhorse.ProjectSB.Teacher;

public class ModifyExistingTeacher implements Runnable {

	private Admin admin;
	private Teacher teacher;
	private String teachersFirstName,teachersLastName,title;
	private Center selectedCenter;
	
	public ModifyExistingTeacher(Admin admin,Teacher teacher,String teachersFirstName,String teachersLastName,String title,Center selectedCenter) {
		this.admin = admin;
		this.teacher = teacher;
		this.teachersFirstName = teachersFirstName;
		this.teachersLastName = teachersLastName;
		this.title = title;
		this.selectedCenter = selectedCenter;	
	}

	@Override
	public void run() {
		if(title.equalsIgnoreCase("Teacher")) {admin.modifyStaffMemberTeacher(teacher, title, teachersFirstName, teachersLastName, selectedCenter);}
		else {admin.promoteToDirector(teacher, selectedCenter);}
	}
	
}
