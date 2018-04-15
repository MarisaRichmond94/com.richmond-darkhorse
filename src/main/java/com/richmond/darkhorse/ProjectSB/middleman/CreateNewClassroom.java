package com.richmond.darkhorse.ProjectSB.middleman;
import com.richmond.darkhorse.ProjectSB.Admin;
import com.richmond.darkhorse.ProjectSB.Center;
import com.richmond.darkhorse.ProjectSB.Classroom;
import com.richmond.darkhorse.ProjectSB.Teacher;

public class CreateNewClassroom implements Runnable{
	
	private String classroomType,ageGroup;
	private Center center;
	private Teacher teacher,assistantTeacher;
	private int maxCapacity;
	private Admin admin;
	
	//Constructor for creating a classroom without ANY {@link Teacher}s
	public CreateNewClassroom(String classroomType,Center center,int maxCapacity,String ageGroup,Admin admin) {
		this.classroomType = classroomType;
		this.center = center;
		this.maxCapacity = maxCapacity;
		this.ageGroup = ageGroup;
		this.admin = admin;
	}
	
	//Constructor for creating a classroom with a {@link Teacher}
	public CreateNewClassroom(String classroomType,Center center,Teacher teacher,int maxCapacity,String ageGroup,Admin admin) {
		this.classroomType = classroomType;
		this.center = center;
		this.teacher = teacher;
		this.maxCapacity = maxCapacity;
		this.ageGroup = ageGroup;
		this.admin = admin;	
	}
	
	//Constructor for creating a classroom with TWO {@link Teacher}s
	public CreateNewClassroom(String classroomType,Center center,Teacher teacher,Teacher assistantTeacher,int maxCapacity,String ageGroup,Admin admin) {
		this.classroomType = classroomType;
		this.center = center;
		this.teacher = teacher;
		this.assistantTeacher = assistantTeacher;
		this.maxCapacity = maxCapacity;
		this.ageGroup = ageGroup;
		this.admin = admin;
	}

	@Override
	public void run() {
		if(assistantTeacher == null && teacher == null) {
			Classroom classroom = admin.createClassroomWithoutTeachers(classroomType,center,maxCapacity,ageGroup);	
			admin.addNewClassroom(center, classroom);
		}else if(assistantTeacher == null) {
			Classroom classroom = admin.createClassroomWithoutAssistant(classroomType,center,teacher,maxCapacity,ageGroup);
			admin.addNewClassroom(center, classroom);
		}else {
			Classroom classroom = admin.createClassroomWithAssistant(classroomType,center,teacher,assistantTeacher,maxCapacity,ageGroup);
			admin.addNewClassroom(center, classroom);
		}
	}
	
}
