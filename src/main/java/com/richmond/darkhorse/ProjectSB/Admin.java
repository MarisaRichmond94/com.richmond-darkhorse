package com.richmond.darkhorse.ProjectSB;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Map;

/**
 * subclass of {@link Account} in charge of the creation, modification, and deletion of {@link Center}s, {@link Director}s, {@link Teacher}s, and {@link Classroom}s
 * @author marisarichmond
 */
public class Admin implements Account,Serializable{

	private static final long serialVersionUID = 8453756L;
	private static Admin instance;
	private String userID, firstName, lastName, title = "Admin";
	private Credential credentials;
	
	private Admin() {
	}
	
	public void initializeCredentials(String firstName,String lastName,String userID) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.userID = userID;
		Credential credentials = new Credential(firstName,lastName,title);
		this.credentials = credentials;
		AccountManager.getInstance().addUser(userID,this);
	}
	
	public static Admin getInstance() {
		if (instance == null) {instance = new Admin();}
		return instance;
	}
	
	public Center createCenter(String centerName,String abbreviatedName,String licenseName,String licenseNumber,String address,String city,String county) {
		Center center = new Center(centerName,abbreviatedName,licenseName,licenseNumber,address,city,county);
	    return center;
	}

	public Director createDirector(String firstName,String lastName,Center center) {
		Director director = new Director(firstName,lastName,center);
		center.setDirectorID(director.getDirectorID());
		return director;
	}
	
	public Teacher createTeacher(String firstName,String lastName,Center center) {
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
		return classroom;
	}
	
	public Classroom createClassroomWithAssistant(String className,Center center,Teacher teacher,Teacher assistantTeacher,int maxSize,String ageGroup) {
		Classroom classroom = new Classroom(className,center,teacher,assistantTeacher,maxSize,ageGroup);
		return classroom;
	}
	
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
		Map<String,Classroom> classrooms = center.getClassrooms();
		for(Classroom classroom : classrooms.values()) {if(classroom.getTeacherID().equals(teacher.getTeacherID())) {classroom.removeTeacher(teacher);}}
		if(staffMembers.containsKey(teacher.getUserID())) { staffMembers.remove(teacher.getUserID()); }
	}
	
	public void deleteClassroom(Classroom classroom) {
		Center center = classroom.getCenter(classroom.getCenterID());
		center.deleteClassroom(classroom);
	}
	
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
	
	public StaffMember modifyStaffMemberTeacher(StaffMember staffMember,String title,String firstName,String lastName,Center center) {
	    Teacher modifiedTeacher = (Teacher) staffMember;
	    	modifiedTeacher.setFirstName(firstName);
	    modifiedTeacher.setLastName(lastName);
	    modifiedTeacher.setCenterID(center.getCenterID());
		return modifiedTeacher;
	}
	
	public Classroom modifyClassroom(Classroom classroom,Teacher teacher,Teacher assistantTeacher,int maxSize) {
		classroom.setMaxSize(maxSize);
		if(teacher != null) {classroom.setTeacherID(teacher.getTeacherID());}
		if(assistantTeacher != null) {classroom.setAssistantTeacherID(assistantTeacher.getTeacherID());}
		return classroom;
	}
	
	public Director promoteToDirector(Teacher teacher,Center center) {
		if(teacher.getCenterID() != null && !teacher.getCenterID().equals(center.getCenterID())) {
			Center oldCenter = teacher.getCenter(teacher.getCenterID());
			oldCenter.removeTeacher(teacher);
		}
		String userID = teacher.getUserID(), directorID = teacher.getTeacherID(), firstName = teacher.getFirstName(), lastName = teacher.getLastName();
		this.deleteTeacher(teacher);
		Director newlyPromotedDirector = new Director(firstName,lastName,center);
		newlyPromotedDirector.setDirectorID(directorID);
		newlyPromotedDirector.setUserID(userID);
		this.addNewStaffMember(newlyPromotedDirector);
		return newlyPromotedDirector;
	}

	public void addNewCenter(Center center) {
		SpecialBeginnings.getInstance().addCenter(center);
	}
	
	public void addNewStaffMember(StaffMember staffMember) {
		SpecialBeginnings.getInstance().addStaffMember(staffMember);
	}
	
	public void addNewClassroom(Center center,Classroom classroom) {
		center.addClassroom(classroom);
	}

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
	
	public void saveAdmin() {
		try {
			ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream("admin.ser"));
			output.writeObject(this);
			output.flush();
			output.close();
		} catch (IOException e) {e.printStackTrace();}
	}
	
	public static void loadAdmin() {
		try {
			ObjectInputStream objectInput = new ObjectInputStream(new FileInputStream("admin.ser"));
			Admin superUser = (Admin) objectInput.readObject();
			instance = superUser;
		    objectInput.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
    	}

}
