package com.richmond.darkhorse.ProjectSB.middleman;
import com.richmond.darkhorse.ProjectSB.Admin;
import com.richmond.darkhorse.ProjectSB.Center;
import com.richmond.darkhorse.ProjectSB.Classroom;
import com.richmond.darkhorse.ProjectSB.Teacher;

public class ModifyExistingTeacher implements Runnable {

	private Admin admin;
	private Teacher teacher;
	private String teachersFirstName,teachersLastName,title;
	private Center selectedCenter;
	private Classroom selectedClassroom;
	
	//Constructor for creating a teacher WITH a {@link Classroom}
	public ModifyExistingTeacher(Admin admin,Teacher teacher,String teachersFirstName,String teachersLastName,String title,Center selectedCenter,Classroom selectedClassroom) {
		this.admin = admin;
		this.teacher = teacher;
		this.teachersFirstName = teachersFirstName;
		this.teachersLastName = teachersLastName;
		this.title = title;
		this.selectedCenter = selectedCenter;
		this.selectedClassroom = selectedClassroom;
	}
	
	//Constructor for creating a teacher WITHOUT a {@link Classroom}
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
		if(selectedClassroom != null && title.equals("Teacher")) {admin.modifyStaffMemberTeacherWithClassroom(teacher, title, teachersFirstName, teachersLastName, selectedCenter, selectedClassroom);}
		else if(selectedClassroom == null && title.equals("Teacher")) {admin.modifyStaffMemberTeacherWithoutClassroom(teacher, title, teachersFirstName, teachersLastName, selectedCenter);}
		else {admin.promoteToDirector(teacher, selectedCenter);}
	}
	
}
