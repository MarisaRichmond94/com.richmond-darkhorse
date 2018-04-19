package com.richmond.darkhorse.ProjectSB.middleman;
import java.util.Map;
import com.richmond.darkhorse.ProjectSB.Classroom;
import com.richmond.darkhorse.ProjectSB.Director;

public class CreateNewStudent implements Runnable{

	private Director director;
	private String firstName,lastName,birthDate;
	private Classroom classroom;
	private Map<String,Boolean> attendancePlan;
	
	public CreateNewStudent(Director director,String firstName,String lastName,String birthDate,Classroom classroom,Map<String,Boolean> attendancePlan) {
		this.director = director;
		this.firstName = firstName;
		this.lastName = lastName;
		this.birthDate = birthDate;
		this.classroom = classroom;
		this.attendancePlan = attendancePlan;
	}
	
	@Override
	public void run() {
		if(classroom != null) {director.createNewStudent(firstName, lastName, birthDate, classroom, attendancePlan);}
		else {director.createNewStudentWithoutClassroom(firstName,lastName,birthDate,attendancePlan);}
	}

}
