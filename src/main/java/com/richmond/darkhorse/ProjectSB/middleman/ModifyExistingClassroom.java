package com.richmond.darkhorse.ProjectSB.middleman;
import com.richmond.darkhorse.ProjectSB.Admin;
import com.richmond.darkhorse.ProjectSB.Classroom;
import com.richmond.darkhorse.ProjectSB.Teacher;

public class ModifyExistingClassroom implements Runnable {
	
	private Classroom classroom;
	private Teacher teacher,assistantTeacher = null;
	private int maxCapacity;
	private Admin admin;
	
	public ModifyExistingClassroom(Classroom classroom,Teacher teacher,Teacher assistantTeacher,int maxCapacity,Admin admin) {
		this.classroom = classroom;
		this.teacher = teacher;
		this.assistantTeacher = assistantTeacher;
		this.maxCapacity = maxCapacity;
		this.admin = admin;
	}

	@Override
	public void run() {
		admin.modifyClassroom(classroom, teacher, assistantTeacher, maxCapacity);
	}


}
