package com.richmond.darkhorse.ProjectSB;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A container for {@link Student}s and {@link Teacher}s
 * @author marisarichmond
 *
 */
public class Classroom implements Serializable{

	private static final long serialVersionUID = 8453756L;
	@SuppressWarnings("unused")
	private String classroomID, centerID, teacherID, assistantTeacherID,classroomType,ageGroup;
	private int maxSize;
	private Map<String,String> classroomTypes;
	private List<String> studentsEnrolled, studentsExpected, studentsPresent, teachersPresent;
	private Map<String,DailySheet> dailySheets;
	
	public Classroom(String classroomType,Center center,int maxSize,String ageGroup) {
		this.classroomTypes = new HashMap<String,String>();
		this.classroomTypes = populateClassroomTypes();
		this.classroomType = classroomType;
		this.centerID = center.getCenterID();
		AccountManager accountManager = AccountManager.getInstance();
		this.classroomID = accountManager.generateClassroomID(this);
		center.addClassroom(this);
		this.maxSize = maxSize;
		this.studentsEnrolled = new ArrayList<String>();
		this.studentsExpected = new ArrayList<String>();
		this.studentsPresent = new ArrayList<String>();
		this.teachersPresent = new ArrayList<String>();
		this.dailySheets = new HashMap<String,DailySheet>();
	}
	
	public Classroom(String classroomType,Center center,Teacher teacher,int maxSize,String ageGroup) {
		this.classroomTypes = new HashMap<String,String>();
		this.classroomTypes = populateClassroomTypes();
		this.classroomType = classroomType;
		this.centerID = center.getCenterID();
		AccountManager accountManager = AccountManager.getInstance();
		this.classroomID = accountManager.generateClassroomID(this);
		center.addClassroom(this);
		this.teacherID = teacher.getTeacherID();
		this.maxSize = maxSize;
		this.studentsEnrolled = new ArrayList<String>();
		this.studentsExpected = new ArrayList<String>();
		this.studentsPresent = new ArrayList<String>();
		this.teachersPresent = new ArrayList<String>();
		this.dailySheets = new HashMap<String,DailySheet>();
	}
	
	public Classroom(String classroomType,Center center,Teacher teacher,Teacher assistantTeacher,int maxSize,String ageGroup) { 
		this.classroomTypes = new HashMap<String,String>();
		this.classroomTypes = populateClassroomTypes();
		this.classroomType = classroomType;
		this.centerID = center.getCenterID();
		AccountManager accountManager = AccountManager.getInstance();
		this.classroomID = accountManager.generateClassroomID(this);
		center.addClassroom(this);
		this.teacherID = teacher.getTeacherID();
		this.assistantTeacherID = assistantTeacher.getTeacherID();
		this.maxSize = maxSize;
		this.studentsEnrolled = new ArrayList<String>();
		this.studentsExpected = new ArrayList<String>();
		this.studentsPresent = new ArrayList<String>();
		this.teachersPresent = new ArrayList<String>();
		this.dailySheets = new HashMap<String,DailySheet>();
	}
	
	public Map<String,String> populateClassroomTypes() {
		classroomTypes.put("PreK2", "4+ years");
		classroomTypes.put("PreK1", "3-4 years");
		classroomTypes.put("Pre3", "2-3 years");
		return classroomTypes;
	}
	
	public void addClassroomType(String classroomName,String ageGroup) {
		classroomTypes.put(classroomName,ageGroup);
	}
	
	public Teacher getTeacher(String teacherID) {
		SpecialBeginnings sB = SpecialBeginnings.getInstance();
		Map<String,StaffMember> staffMembers = sB.getStaffMembers();
		if(staffMembers.containsKey(teacherID)) {return (Teacher)staffMembers.get(teacherID);}
		return null;
	}
	
	public Teacher getAssistantTeacher(String assistantTeacherID) {
		SpecialBeginnings sB = SpecialBeginnings.getInstance();
		Map<String,StaffMember> staffMembers = sB.getStaffMembers();
		if(staffMembers.containsKey(assistantTeacherID)) {return (Teacher)staffMembers.get(assistantTeacherID);}
		return null;
	}
	
	public void removeTeacher(Teacher teacher) {
		if(teacherID != null && this.teacherID.equals(teacher.getTeacherID())) {this.teacherID = null;}
		else if(assistantTeacherID != null && this.assistantTeacherID.equals(teacher.getTeacherID())) {this.assistantTeacherID = null;	}
	}
	
	/**
	 * Generates a {@link List} of {@link Student}s expected in the {@link Classroom} based on the provided {@link String} day
	 * @param day - {@link String}
	 * @return a {@link List} of {@link Student}s
	 */
	public List<String> generateClassList(String day){
		List<String> studentsExpected = new ArrayList<String>();
		for(String studentID : studentsEnrolled) {
			Student student = this.getStudent(studentID);
			Map<String,Boolean> attendancePlan = student.getRecord().getAttendance().getAttendancePlan();
			if(attendancePlan.containsKey(day) && attendancePlan.get(day) == true) {studentsExpected.add(studentID);}
		}
		this.studentsExpected = studentsExpected;
		return studentsExpected;
	}
	
	public void addStudentEnrolled(Student student) {
		studentsEnrolled.add(student.getStudentID());
	}
	
	public void removeStudentEnrolled(Student student) {
		if(student.getStudentID() != null) {studentsEnrolled.remove(student.getStudentID());}
		if(studentsExpected.contains(student.getStudentID())) {studentsExpected.remove(student.getStudentID());}
	}
	
	public void addStudentPresent(Student student) {
		studentsPresent.add(student.getStudentID());
	}
	
	public void removeStudentPresent(Student student) {
		studentsPresent.remove(student.getStudentID());
	}
	
	public void addTeacherPresent(Teacher teacher) {
		teachersPresent.add(teacher.getTeacherID());
	}
	
	public void removeTeacherPresent(Teacher teacher) {
		teachersPresent.remove(teacher.getTeacherID());
	}
		
	public void addStudentExpected(Student student) {
		studentsExpected.add(student.getStudentID());
	}
	
	public void removeStudentsExpected(Student student) {
		for(String studentID : studentsExpected) {
			String studentIDCheck = student.getStudentID();
			if(studentIDCheck.equals(studentID)) {studentsExpected.remove(studentID);}
		}
	}
	
	//Daily sheet methods 
	public Map<String,DailySheet> generateDailySheets(Map<String,Student> studentsPresent,String date){
		Teacher teacher = this.getTeacher(teacherID);
		Map<String,DailySheet> dailySheets = new HashMap<String,DailySheet>();
		for(Student student : studentsPresent.values()) {
			DailySheet dailySheet = new DailySheet(teacher,student,date);
			String documentID = dailySheet.getDocumentID();
			dailySheets.put(documentID,dailySheet);
		}
		this.dailySheets = dailySheets;
		return dailySheets;
	}

	public void storeDailySheets() {
		for(DailySheet dailySheet : dailySheets.values()) {
			Student student = this.getStudent(dailySheet.getStudentID());
			student.getRecord().addDailySheet(dailySheet);
		}
	}
	
	public void clearBinder() {
		Map<String,DailySheet> clearedDailySheets = new HashMap<String,DailySheet>();
		setDailySheets(clearedDailySheets);
		List<String> clearedStudentsExpected = new ArrayList<String>();
		setStudentsExpected(clearedStudentsExpected);
	}
	
	public int getCount(String day) {
		int count = 0;
		List<String> studentsEnrolled = this.getStudentsEnrolled();
		for(String studentID : studentsEnrolled) {
			Student student = this.getStudent(studentID);
			Record record = student.getRecord();
			Attendance attendance = record.getAttendance();
			Map<String,Boolean> attendancePlan = attendance.getAttendancePlan();
			if(attendancePlan.containsKey(day) && attendancePlan.get(day) == true) {count++;}
		}
		return count;
	}
	
	public String getClassroomID() {
		return classroomID;
	}

	public void setClassroomID(String classroomID) {
		this.classroomID = classroomID;
	}

	public String getTeacherID() {
		return teacherID;
	}

	public String getAssistantTeacherID() {
		return assistantTeacherID;
	}

	public List<String> getStudentsEnrolled() {
		return studentsEnrolled;
	}

	public void setStudentsEnrolled(List<String> studentsEnrolled) {
		this.studentsEnrolled = studentsEnrolled;
	}

	/**
	 * Populates the {@link List} of {@link Student}s expected in the {@link Classroom} based on the current {@link LocalDate}
	 */
	private void populateStudentsExpected() {
		LocalDate now = LocalDate.now();
		String dayOfWeek = now.getDayOfWeek().toString(), day = dayOfWeek.substring(0,1) + dayOfWeek.substring(1).toLowerCase();
		if(day.equals("Saturday") || day.equals("Sunday")) {System.out.println("Invalid day");}
		this.studentsExpected = new ArrayList<>();
		for(String studentID : studentsEnrolled) {
			Student student = this.getStudent(studentID);
			Map<String,Boolean> attendancePlan = student.getRecord().getAttendance().getAttendancePlan();
			if(attendancePlan.containsKey(day) && attendancePlan.get(day) == true) {studentsExpected.add(studentID);}
		}
	}
	
	public List<String> getStudentsExpected() {
		populateStudentsExpected();
		return studentsExpected;
	}

	public void setStudentsExpected(List<String> studentsExpected) {
		this.studentsExpected = studentsExpected;
	}

	public Map<String,DailySheet> getDailySheets() {
		return dailySheets;
	}

	public void setDailySheets(Map<String,DailySheet> dailySheets) {
		this.dailySheets = dailySheets;
	}

	public int getMaxSize() {
		return maxSize;
	}

	public String getCenterID() {
		return centerID;
	}

	public void setCenterID(String centerID) {
		this.centerID = centerID;
	}

	public void setTeacherID(String teacherID) {
		this.teacherID = teacherID;
	}

	public void setAssistantTeacherID(String assistantTeacherID) {
		this.assistantTeacherID = assistantTeacherID;
	}

	public void setMaxSize(int maxSize) {
		this.maxSize = maxSize;
	}

	public String getClassroomType() {
		return classroomType;
	}

	public Map<String,String> getClassroomTypes() {
		return classroomTypes;
	}

	public String getAgeGroup() {
		return classroomTypes.get(classroomType);
	}

	public Center getCenter(String centerID) {
		SpecialBeginnings sB = SpecialBeginnings.getInstance();
		Map<String,Center> centers = sB.getCenters();
		if(centers.containsKey(centerID)) {
			return centers.get(centerID);
		}
		return null;
	}
	
	public Student getStudent(String studentID) {
		Center center = this.getCenter(this.getCenterID());
		Map<String,Student> students = center.getStudents();
		if(students.containsKey(studentID)) {
			Student student = students.get(studentID);
			return student;
		}
		return null;
	}
	
	public List<String> getStudentsPresent() {
		return studentsPresent;
	}

	public void setStudentsPresent(List<String> studentsPresent) {
		this.studentsPresent = studentsPresent;
	}

	public List<String> getTeachersPresent() {
		return teachersPresent;
	}

	public void setTeachersPresent(List<String> teachersPresent) {
		this.teachersPresent = teachersPresent;
	}

	@Override
	public String toString() {
		if(this.equals(null)) {return "N/A";}
		return this.getClassroomType();
	}
}
