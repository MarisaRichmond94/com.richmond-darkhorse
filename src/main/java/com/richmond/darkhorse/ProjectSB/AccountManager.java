package com.richmond.darkhorse.ProjectSB;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

public class AccountManager implements Serializable {

	private static final long serialVersionUID = 8453756L;
	private static AccountManager instance;
	private Map<String, Student> students;
	private Map<String, Classroom> classrooms;
	private Map<String, Document> documents;
	private Map<String, Account> userIDToAccount;

	private AccountManager() {
		this.students = new HashMap<String, Student>();
		this.classrooms = new HashMap<String, Classroom>();
		this.documents = new HashMap<String, Document>();
		this.userIDToAccount = new HashMap<String, Account>();
	}

	/**
	 * Gets the only instance of the account manager. If one hasn't been created, it
	 * will create one
	 * @return {@link AccountManager} - a singleton instance of account manager
	 */
	public static AccountManager getInstance() {
		if (instance == null) {
			instance = new AccountManager();
		}
		return instance;
	}
	
	//ID generation methods
	public String generateUserID(StaffMember newStaffMember) {
		String userID = newStaffMember.getUserID();
		if(userID != null) {
			return null;
		}
		String title = newStaffMember.getTitle();
		String centerID = newStaffMember.getCenterID();
		int uniqueCode = 0;
		boolean status = true;
		while (status == true) {
			if (title.equals("Admin")) {
				int leftLimit = 00000;
				int rightLimit = 10000;
				uniqueCode = leftLimit + (int) (new Random().nextFloat() * (rightLimit - leftLimit));
				userID = centerID + uniqueCode;
				try {
					status = checkAccount(newStaffMember.getFirstName(),newStaffMember.getLastName(),userID);
					addUser(userID,newStaffMember);
				} catch (Exception e) {
				}
			} else if (title.equals("Director")) {
				int leftLimit = 10001;
				int rightLimit = 19999;
				uniqueCode = leftLimit + (int) (new Random().nextFloat() * (rightLimit - leftLimit));
				userID = "" + centerID + uniqueCode + "";
				try {
					status = checkAccount(newStaffMember.getFirstName(),newStaffMember.getLastName(),userID);
					addUser(userID,newStaffMember);
				} catch (Exception e) {
				}
			} else if (title.equals("Teacher")) {
				int leftLimit = 20001;
				int rightLimit = 49999;
				uniqueCode = leftLimit + (int) (new Random().nextFloat() * (rightLimit - leftLimit));
				userID = "" + centerID + uniqueCode + "";
				try {
					status = checkAccount(newStaffMember.getFirstName(),newStaffMember.getLastName(),userID);
					addUser(userID,newStaffMember);
				} catch (Exception e) {
				}
			}
		}
		return userID;
	}

	public String generateClassroomID(Classroom classroom) {
		String classroomID = classroom.getClassroomID();
		if(classroomID != null) {
			return null;
		}
		String centerID = classroom.getCenterID();
		boolean status = true;
		while (status == true) {
			int leftLimit = 00000;
			int rightLimit = 10000;
			int uniqueCode = leftLimit + (int) (new Random().nextFloat() * (rightLimit - leftLimit));
			classroomID = centerID + uniqueCode;
			status = checkClassroomID(classroomID);
		}
		addClassroom(classroom);
		return classroomID; 
	}

	public String generateStudentID(Student student) {
		String studentID = student.getStudentID();
		if(studentID != null) {
			return null;
		}
		boolean status = true;
		while (status == true) {
			int leftLimit = 500000000;
			int rightLimit = 999999999;
			int uniqueID = leftLimit + (int) (new Random().nextFloat() * (rightLimit - leftLimit));
			studentID = String.valueOf(uniqueID);
			status = checkStudentID(studentID);
		}
		addStudent(student);
		return studentID;
	}

	public String generateDocumentID(Document newDocument) {
		String documentID = newDocument.getDocumentID();
		if(documentID != null) {
			return null;
		}
		String documentType = newDocument.getTitle();
		String studentID = newDocument.getStudentID();
		byte[] digits = studentID.getBytes();
		String studentCode = "" + digits[0] + digits[1] + digits[2] + digits[3] + "";
		boolean status = true;
		while (status == true) {
			if (documentType.equals("BehaviorReport")) {
				int leftLimit = 00000;
				int rightLimit = 29999;
				int uniqueID = leftLimit + (int) (new Random().nextFloat() * (rightLimit - leftLimit));
				documentID = "" + studentCode + uniqueID + "";
				status = checkDocumentID(documentID);
			} else if (documentType.equals("IncidentReport")) {
				int leftLimit = 30000;
				int rightLimit = 39999;
				int uniqueID = leftLimit + (int) (new Random().nextFloat() * (rightLimit - leftLimit));
				documentID = "" + studentCode + uniqueID + "";
				status = checkDocumentID(documentID);
			} else if (documentType.equals("DailySheet")) {
				int leftLimit = 40000;
				int rightLimit = 99999;
				int uniqueID = leftLimit + (int) (new Random().nextFloat() * (rightLimit - leftLimit));
				documentID = "" + studentCode + uniqueID + "";
				status = checkDocumentID(documentID);
			}
		}
		addDocument(newDocument);
		return documentID;
	}

	//Check methods
	public boolean checkAccount(String firstName, String lastName, String userID) {
		Account account = userIDToAccount.get(userID);
		if(userIDToAccount.containsKey(userID)) {
			Credential credentials = account.getCredentials();
			if (credentials != null) {
				String firstNameCheck = credentials.getFirstName();
				String lastNameCheck = credentials.getLastName();
				if (firstName.equals(firstNameCheck) && lastName.equals(lastNameCheck)) {
					return true;
				}
			} 
		}
		return false;
	}
	
	public boolean checkStudentID(String studentID) {
		if (students.containsKey(studentID)) {
			return true;
		}
		return false;
	}

	public boolean checkClassroomID(String classroomID) {
		if (classrooms.containsKey(classroomID)) {
			return true;
		}
		return false;
	}

	public boolean checkDocumentID(String documentID) {
		if (documents.containsKey(documentID)) {
			return true;
		}
		return false;
	}
	
	//Add methods
	public void addUser(String userID, Account account) {
		userIDToAccount.put(userID,account);
	}

	public void addStudent(Student student) {
		students.put(student.getStudentID(),student);
	}

	public void addClassroom(Classroom classroom) {
		classrooms.put(classroom.getClassroomID(),classroom);
	}

	public void addDocument(Document document) {
		documents.put(document.getDocumentID(),document);
	}
	
	//Standard getters and setters
	public Account getAccount(String userID) {
		Account account = userIDToAccount.get(userID);
		return account;
	}
	
	public Map<String, Credential> getUserIDs() {
		HashMap<String, Credential> userIDs = new HashMap<String, Credential>();
		for(Entry<String,Account> entry : userIDToAccount.entrySet()) {
			userIDs.put(entry.getKey(),entry.getValue().getCredentials());
		}
		return userIDs;
	} 
	
	public Map<String,Classroom> getClassrooms() {
		return classrooms;
	}
	
	public Map<String,Document> getDocuments() {
		return documents;
	}
	
	public Map<String,Student> getStudents(){
		return students;
	}
	
	//Save and load methods
	public void saveUserIDToAccount() {
		try {
			ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream("userIDsToAccounts.ser"));
			output.writeObject(userIDToAccount);
			output.flush();
			output.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	} 
	
	@SuppressWarnings("unchecked")
	public void loadUserIDToAccount() {
		try {
			ObjectInputStream objectInput = new ObjectInputStream(new FileInputStream("userIDsToAccounts.ser"));
			Map<String,Account> userIDToAccountList = (Map<String, Account>) objectInput.readObject();
			userIDToAccount = userIDToAccountList;
		    objectInput.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
    	}
	
	public void startUp() {
		loadUserIDToAccount();
	}
	
	public void shutDown() {
		saveUserIDToAccount();
	}

}
