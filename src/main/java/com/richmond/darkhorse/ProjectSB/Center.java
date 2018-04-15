package com.richmond.darkhorse.ProjectSB;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Center implements Serializable{

	private static final long serialVersionUID = 8453756L;
    private String centerName, abbreviatedName, licenseName, licenseNumber, centerID, address, city, county;
    private String directorID;
    private String studentsFileName, classroomsFileName;
    private Map<String,Classroom> classrooms;
    private Map<String,Student> students;
    private List<String> teacherIDs;
    private EventCalendar eventCalendar;
    private Map<String,Schedule> staffSchedules;

    public Center(String centerName,String abbreviatedName,String licenseName,String licenseNumber,String address,String city,String county){
        this.centerName = centerName;
        this.abbreviatedName = abbreviatedName;
    		this.licenseName = licenseName;
    		this.licenseNumber = licenseNumber;
    		this.centerID = generateCenterID(licenseNumber); 
    		this.address = address;
    		this.city = city;
    		this.county = county;
    		this.studentsFileName = abbreviatedName + "students";
    		this.classroomsFileName = abbreviatedName + "classrooms";
        this.classrooms = new HashMap<String,Classroom>();
        this.students = new HashMap<String,Student>();
        this.teacherIDs = new ArrayList<String>();
        this.eventCalendar = new EventCalendar();
        this.staffSchedules = new HashMap<String,Schedule>();
    }

    //CenterID methods
	public String generateCenterID(String licenseNumber) {
    		int a = Character.digit(licenseNumber.charAt(0), 10);
    		String aString = Integer.toString(a);
    		int b = Character.digit(licenseNumber.charAt(1), 10);
    		String bString = Integer.toString(b);
    		int c = Character.digit(licenseNumber.charAt(2), 10);
    		String cString = Integer.toString(c);
    		int d = Character.digit(licenseNumber.charAt(3), 10);
    		String dString = Integer.toString(d);
    		String centerID = aString + bString + cString + dString;
    		return centerID;
	}
    
	//Director methods
    public void removeCenterDirector() {
    		this.directorID = null;
    }
 
    //Classroom methods
    public void addClassroom(Classroom classroom) {
		classrooms.put(classroom.getClassroomID(),classroom);
	}
	
    public void deleteClassroom(Classroom classroom) {
    		if(classroom.getTeacher(classroom.getTeacherID()) != null) {
    			Teacher teacher = classroom.getTeacher(classroom.getTeacherID());
    			teacher.removeClassroom(classroom);
    			classroom.removeTeacher(teacher);
    		}
    		if(classroom.getAssistantTeacherID() != null) {
    			Teacher assistantTeacher = classroom.getAssistantTeacher(classroom.getAssistantTeacherID());
        		assistantTeacher.removeClassroom(classroom);
        		classroom.removeTeacher(assistantTeacher);
    		}
    		if(classrooms.containsKey(classroom.getClassroomID())) {
    			classrooms.remove(classroom.getClassroomID());
    		}
    }
	
    public void saveClassrooms() {
	    	try {
				ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(classroomsFileName + ".ser"));
				output.writeObject(classrooms);
				output.flush();
				output.close();
			} catch (IOException e) {
			}
    }
   
    public void loadClassrooms() {
		try {
			ObjectInputStream objectInput = new ObjectInputStream(new FileInputStream(classroomsFileName + ".ser"));
			@SuppressWarnings("unchecked")
			Map<String,Classroom> classroomList = (Map<String,Classroom>)objectInput.readObject();
			classrooms = classroomList;
	        objectInput.close();
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
		} catch (ClassNotFoundException e) {
		}
    }
    
    //Teacher methods
    public void addTeacher(Teacher teacher) {
    		teacherIDs.add(teacher.getTeacherID());
    		setTeacherIDs(teacherIDs);
    }
    
    public void removeTeacher(Teacher teacher) {
		if(teacherIDs.contains(teacher.getTeacherID())){
			teacherIDs.remove(teacher.getTeacherID());
		}
    }
    
    //Student methods
    public void addStudent(Student student) {
    		students.put(student.getStudentID(), student);
    }
    
    public void removeStudent(Student student) {
    		if(students.containsKey(student.getStudentID())) {
    			students.remove(student.getStudentID());
    		}
    }
    
    public void saveStudents() {
	    	try {
				ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(studentsFileName + ".ser"));
				output.writeObject(students);
				output.flush();
				output.close();
			} catch (IOException e) {
			}
	}
	
	public void loadStudents() {
		try {
			ObjectInputStream objectInput = new ObjectInputStream(new FileInputStream(studentsFileName + ".ser"));
			@SuppressWarnings("unchecked")
			Map<String,Student> studentList = (Map<String,Student>)objectInput.readObject();
			students = studentList;
	        objectInput.close();
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
		} catch (ClassNotFoundException e) {
		}
	}
	
	//Add methods
	public void addSchedule(StaffMember staffMember) {
		staffSchedules.put(staffMember.getUserID(),staffMember.getSchedule());
	}
	
	//Remove methods
	public void removeSchedule(StaffMember staffMember) {
		staffSchedules.remove(staffMember.getUserID());
	}
	
    //Getters and setters
    public Map<String, Classroom> getClassrooms() {
		return classrooms;
	}

	public void setClassrooms(Map<String, Classroom> classrooms) {
		this.classrooms = classrooms;
	}

	public List<String> getTeacherIDs() {
		return teacherIDs;
	}

	public void setTeacherIDs(List<String> teacherIDs) {
		this.teacherIDs = teacherIDs;
	}

	public String getCenterName() {
		return centerName;
	}

	public String getAbbreviatedName() {
		return abbreviatedName;
	}

	public String getLicenseName() {
		return licenseName;
	}

	public String getLicenseNumber() {
		return licenseNumber;
	}

	public String getCenterID() {
		return centerID;
	}

	public String getAddress() {
		return address;
	}

	public String getCity() {
		return city;
	}

	public String getCounty() {
		return county;
	}

	public String getDirectorID() {
		return directorID;
	}

	public Map<String, Student> getStudents() {
		return students;
	}
	
	public void setCenterName(String centerName) {
		this.centerName = centerName;
	}

	public void setAbbreviatedName(String abbreviatedName) {
		this.abbreviatedName = abbreviatedName;
	}

	public void setLicenseName(String licenseName) {
		this.licenseName = licenseName;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public void setCounty(String county) {
		this.county = county;
	}
	
	public void setDirectorID(String directorID) {
		this.directorID = directorID;
	}

	public EventCalendar getEventCalendar() {
		return eventCalendar;
	}

	public Map<String,Schedule> getStaffSchedules() {
		return staffSchedules;
	}

	public Director getDirector(String directorID) {
		SpecialBeginnings sB = SpecialBeginnings.getInstance();
		Map<String,StaffMember> staffMembers = sB.getStaffMembers();
		if(staffMembers.containsKey(directorID)) {
			return (Director) staffMembers.get(directorID);
		}
		return null;
	}

	@Override
    public String toString() {
    		return "" + centerName + "";
    }
	
}
