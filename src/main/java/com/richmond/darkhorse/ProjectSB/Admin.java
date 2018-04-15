package com.richmond.darkhorse.ProjectSB;
import java.io.Serializable;
import java.util.Map;

public class Admin implements Account,Serializable{

	private static final long serialVersionUID = 8453756L;
	private String userID = "123456789";
	private String firstName = "Marisa";
	private String lastName = "Richmond";
	private String title = "Admin";
	private Credential credentials;
	
	public Admin() {
		Credential credentials = new Credential(firstName,lastName,title);
		this.credentials = credentials;
	}

	//Object creation methods
	public Center createCenter(String centerName,String abbreviatedName,String licenseName,String licenseNumber,String address,String city,String county) {
		Center center = new Center(centerName,abbreviatedName,licenseName,licenseNumber,address,city,county);
	    return center;
	}

	public Director createDirector(String firstName,String lastName,Center center) {
		Director director = new Director(firstName,lastName,center);
		center.setDirectorID(director.getDirectorID());
		return director;
	}
	
	public Teacher createTeacherWithClassroom(String firstName,String lastName,Center center,Classroom classroom) {
		Teacher teacher = new Teacher(firstName,lastName,center,classroom);
		center.addTeacher(teacher);
		if(classroom.getTeacherID() == null) {
			classroom.setTeacherID(teacher.getTeacherID());
		}else{
			classroom.setAssistantTeacherID(teacher.getTeacherID());
		}
		return teacher;
	}
	
	public Teacher createTeacherWithoutClassroom(String firstName,String lastName,Center center) {
		Teacher teacher = new Teacher(firstName,lastName,center);
		center.addTeacher(teacher);
		return teacher;
	}
	
	public Classroom createClassroomWithoutTeachers(String className,Center center,int maxSize,String ageGroup) {
		Classroom classroom = new Classroom(className,center,maxSize,ageGroup);
		return classroom;
	}
	
	public Classroom createClassroomWithoutAssistant(String className,Center center,Teacher teacher,int maxSize,String ageGroup) {
		Classroom classroom = new Classroom(className,center,teacher,maxSize,ageGroup);
		teacher.setClassroom(classroom);
		return classroom;
	}
	
	public Classroom createClassroomWithAssistant(String className,Center center,Teacher teacher,Teacher assistantTeacher,int maxSize,String ageGroup) {
		Classroom classroom = new Classroom(className,center,teacher,assistantTeacher,maxSize,ageGroup);
		teacher.setClassroom(classroom);
		assistantTeacher.setClassroom(classroom);
		return classroom;
	}
	
	//Object removal methods
	public void deleteCenter(Center center) {
		SpecialBeginnings.getInstance().removeCenter(center.getCenterID());
	}
	
	public void deleteDirector(Director director) {
		SpecialBeginnings.getInstance().removeDirector(director);
	}
	
	public void deleteTeacher(Teacher teacher) {
		Map<String,StaffMember> staffMembers = SpecialBeginnings.getInstance().getStaffMembers();
		Center center = teacher.getCenter(teacher.getCenterID());
		center.removeTeacher(teacher);
		if(teacher.getClassroomID() != null) {
			Classroom classroom = teacher.getClassroom(teacher.getClassroomID());
			classroom.removeTeacher(teacher);
		}
		if(staffMembers.containsKey(teacher.getUserID())) {
			staffMembers.remove(teacher.getUserID());
		}
	}
	
	public void deleteClassroom(Classroom classroom) {
		if(classroom.getTeacherID() != null) {
			Teacher leadTeacher = classroom.getTeacher(classroom.getTeacherID());
			leadTeacher.removeClassroom(classroom);
		}else if(classroom.getAssistantTeacherID() != null) {
			Teacher assistantTeacher = classroom.getAssistantTeacher(classroom.getAssistantTeacherID());
			assistantTeacher.removeClassroom(classroom);
		}
		Center center = classroom.getCenter(classroom.getCenterID());
		center.deleteClassroom(classroom);
	}
	
	//Modify methods
	public Center modifyCenter(Center center,String centerName,String abbreviatedName,String licenseName,String address,String city,String county) {
		center.setCenterName(centerName);
		center.setAbbreviatedName(abbreviatedName);
		center.setLicenseName(licenseName);
		center.setAddress(address);
		center.setCity(city);
		center.setCounty(county);
		return center;
	}
	
	public StaffMember modifyStaffMemberDirector(StaffMember staffMember,String title,String firstName,String lastName,Center center) {
	    if(!staffMember.getTitle().equals(title)) {
	    		staffMember.setTitle(title);
	    		Teacher teacher = (Teacher) new Teacher(firstName,lastName,center);
	    		return teacher;
	    }
	    staffMember.setFirstName(firstName);
	    staffMember.setLastName(lastName);
	    staffMember.setCenterID(center.getCenterID());
		return staffMember;
	}
	
	public StaffMember modifyStaffMemberTeacherWithoutClassroom(StaffMember staffMember,String title,String firstName,String lastName,Center center) {
	    Teacher modifiedTeacher = (Teacher) staffMember;
	    if(modifiedTeacher.getClassroomID() != null) {
	    		Classroom oldClassroom = modifiedTeacher.getClassroom(modifiedTeacher.getClassroomID());
	    		oldClassroom.removeTeacher(modifiedTeacher);
	    		modifiedTeacher.removeClassroom(oldClassroom);
	    }
	    	modifiedTeacher.setFirstName(firstName);
	    modifiedTeacher.setLastName(lastName);
	    modifiedTeacher.setCenterID(center.getCenterID());
		return modifiedTeacher;
	}
	
	public StaffMember modifyStaffMemberTeacherWithClassroom(StaffMember staffMember,String title,String firstName,String lastName,Center center,Classroom classroom) {
	    Teacher modifiedTeacher = (Teacher) staffMember;
	    modifiedTeacher.setFirstName(firstName);
	    modifiedTeacher.setLastName(lastName);
	    if(!modifiedTeacher.getCenterID().equals(center.getCenterID())) {
	    		Center oldCenter = modifiedTeacher.getCenter(modifiedTeacher.getCenterID());
	    		oldCenter.removeTeacher(modifiedTeacher);
	    }
	    modifiedTeacher.setCenterID(center.getCenterID());
	    if(modifiedTeacher.getClassroomID() != null && !modifiedTeacher.getClassroomID().equals(classroom.getClassroomID())) {
	    		Classroom oldClassroom = modifiedTeacher.getClassroom(modifiedTeacher.getClassroomID());
	    		oldClassroom.removeTeacher(modifiedTeacher);
	    }
		if(classroom.getTeacherID() == null) {
	    		classroom.setTeacherID(modifiedTeacher.getTeacherID());
	    	}else {classroom.setAssistantTeacherID(modifiedTeacher.getTeacherID());}
		modifiedTeacher.setClassroomID(classroom.getClassroomID());
		return modifiedTeacher;
	}
	
	public Classroom modifyClassroom(Classroom classroom,Teacher teacher,Teacher assistantTeacher,int maxSize) {
		classroom.setMaxSize(maxSize);
		if(teacher != null) {
			if(classroom.getTeacherID() != null && !teacher.getTeacherID().equals(classroom.getTeacherID())) {
				Teacher oldTeacher = classroom.getTeacher(classroom.getTeacherID());
				oldTeacher.removeClassroom(classroom);	
			}
			teacher.setClassroom(classroom);
			classroom.setTeacherID(teacher.getTeacherID());
		}else {
			if(classroom.getTeacherID() != null) {classroom.removeTeacher(classroom.getTeacher(classroom.getTeacherID()));}
		}
		if(assistantTeacher != null) {
			if(classroom.getAssistantTeacherID() != null && !assistantTeacher.getTeacherID().equals(classroom.getAssistantTeacherID())) {
				Teacher oldAssistant = classroom.getAssistantTeacher(classroom.getAssistantTeacherID());
				oldAssistant.removeClassroom(classroom);
			}
			assistantTeacher.setClassroom(classroom);
			classroom.setAssistantTeacherID(assistantTeacher.getTeacherID());
		}else {
			if(classroom.getAssistantTeacherID() != null) {classroom.removeTeacher(classroom.getAssistantTeacher(classroom.getAssistantTeacherID()));}
		}
		return classroom;
	}
	
	public Director promoteToDirector(Teacher teacher,Center center) {
		if(teacher.getCenterID() != null && !teacher.getCenterID().equals(center.getCenterID())) {
			Center oldCenter = teacher.getCenter(teacher.getCenterID());
			oldCenter.removeTeacher(teacher);
		}
		if(teacher.getClassroomID() != null) {
			Classroom classroom = teacher.getClassroom(teacher.getClassroomID());
			classroom.removeTeacher(teacher);
			teacher.removeClassroom(classroom);
		}
		String userID = teacher.getUserID();
		String directorID = teacher.getTeacherID();
		String firstName = teacher.getFirstName();
		String lastName = teacher.getLastName();
		this.deleteTeacher(teacher);
		Director newlyPromotedDirector = new Director(firstName,lastName,center);
		newlyPromotedDirector.setDirectorID(directorID);
		newlyPromotedDirector.setUserID(userID);
		this.addNewStaffMember(newlyPromotedDirector);
		return newlyPromotedDirector;
	}
	
	//Add methods
	public void addNewCenter(Center center) {
		SpecialBeginnings.getInstance().addCenter(center);
	}
	
	public void addNewStaffMember(StaffMember staffMember) {
		SpecialBeginnings.getInstance().addStaffMember(staffMember);
	}
	
	public void addNewClassroom(Center center,Classroom classroom) {
		center.addClassroom(classroom);
	}

	//Standard getters and setters
	public String getUserID() {
		return userID;
	}

	public String getTitle() {
		return title;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public Credential getCredentials() {
		return credentials;
	}
	
	public Map<String,Center> getCenters(){
		Map<String,Center> centers = SpecialBeginnings.getInstance().getCenters();
		return centers;
	}
	
	public Map<String,StaffMember> getStaffMembers(){
		return SpecialBeginnings.getInstance().getStaffMembers();
	}
	
	public Map<String,Classroom> getClassrooms(Center center){
		return center.getClassrooms();
	}

}
