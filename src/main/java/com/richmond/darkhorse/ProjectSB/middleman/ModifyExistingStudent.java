package com.richmond.darkhorse.ProjectSB.middleman;
import java.util.Map;
import com.richmond.darkhorse.ProjectSB.Classroom;
import com.richmond.darkhorse.ProjectSB.Director;
import com.richmond.darkhorse.ProjectSB.Student;

public class ModifyExistingStudent implements Runnable{

	private Director director;
	private Student student;
	private Classroom classroom;
	private Map<String,Boolean> attendancePlan;
	
	public ModifyExistingStudent(Director director,Student student,Classroom classroom,Map<String,Boolean> attendancePlan) {
		this.director = director;
		this.student = student;
		this.classroom = classroom;
		this.attendancePlan = attendancePlan;
	}
	
	@Override
	public void run() {
		director.modifyStudent(student,classroom,attendancePlan);
	}

}
