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

/**
 * Manages the various {@link Admin}, {@link Director}, and {@link Teacher} {@link Account}s and generates unique IDs for the former, as well as {@link Student}s,
 * {@link Classroom}s, and {@link Document}s 
 * @author marisarichmond
 */
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
	 * Gets the only instance of the account manager. If one hasn't been created, it will create one
	 * @return {@link AccountManager} - a singleton instance of account manager
	 */
	public static AccountManager getInstance() {
		if (instance == null) {instance = new AccountManager();}
		return instance;
	}
	
	public StaffMember grabAdmin() {
		StaffMember admin = null;
		for(Account account : userIDToAccount.values()) {
			System.out.println(account.getCredentials().getTitle());
			if(account.getCredentials().getTitle().equals("Admin")) {admin = (StaffMember) account;}
		}
		System.out.println(admin);
		return admin;
	}
	
	/**
	 * Generates a userID for a new {@link StaffMember}
	 * @param newStaffMember - a new director or teacher 
	 * @return a new userID
	 */
	public String generateUserID(StaffMember newStaffMember) {
		String userID = newStaffMember.getUserID();
		if(userID != null) {return null;}
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

	/**
	 * Generates a classroomID for a new {@link Classroom} object
	 * @param classroom - the new {@link Classroom}
	 * @return a nine-digit classroomID
	 */
	public String generateClassroomID(Classroom classroom) {
		String classroomID = classroom.getClassroomID();
		if(classroomID != null) {return null;}
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

	/**
	 * Generates a 9-digit identification number for a new {@link Student}
	 * @param student - a new student
	 * @return a 9-digit identification number
	 */
	public String generateStudentID(Student student) {
		String studentID = student.getStudentID();
		if(studentID != null) {return null;}
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

	/**
	 * Generates a unique 9-digit identification number for a new {@link Document}
	 * @param newDocument - a new {@link Document} object
	 * @return a unique 9-digit identification number
	 */
	public String generateDocumentID(Document newDocument) {
		String documentID = newDocument.getDocumentID();
		if(documentID != null) {return null;}
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

	/**
	 * Checks to see if the account information matches up with a set of {@link Credentials} in the system
	 * @param firstName - the user's first name
	 * @param lastName - the user's last name
	 * @param userID - the user's unique 9-digit identification number
	 * @return true if the user's credentials match up and false if they do not
	 */
	public boolean checkAccount(String firstName, String lastName, String userID) {
		Account account = userIDToAccount.get(userID);
		if(userIDToAccount.containsKey(userID)) {
			Credential credentials = account.getCredentials();
			if (credentials != null) {
				String firstNameCheck = credentials.getFirstName();
				String lastNameCheck = credentials.getLastName();
				if (firstName.equals(firstNameCheck) && lastName.equals(lastNameCheck)) {return true;}
			} 
		}
		return false;
	}
	
	/**
	 * Checks to see if the list of {@link Student}s in the system already contains a specific 9-digit ID
	 * @param studentID - the student's 9-digit identification number
	 * @return true if the list of students already contains a student with the given ID or false if it does NOT
	 */
	public boolean checkStudentID(String studentID) {
		if (students.containsKey(studentID)) {return true;}
		return false;
	}

	/**
	 * Checks to see if the list of {@link Classroom}s in the system already contains a specific 9-digit ID
	 * @param classroomID - the classroom's 9-digit identification number
	 * @return true if the list of classrooms already contains a classroom with the given ID or false if it does NOT
	 */
	public boolean checkClassroomID(String classroomID) {
		if (classrooms.containsKey(classroomID)) {return true;}
		return false;
	}

	/**
	 * Checks to see if the list of {@link Document}s in the system already contains a specific 9-digit ID
	 * @param documentID - the document's 9-digit identification number
	 * @return true if the list of documents already contains a document with the given ID or false if it does NOT
	 */
	public boolean checkDocumentID(String documentID) {
		if (documents.containsKey(documentID)) {return true;}
		return false;
	}
	
	/**
	 * Adds a new user to the system
	 * @param userID
	 * @param account
	 */
	public void addUser(String userID, Account account) {
		userIDToAccount.put(userID,account);
	}

	/**
	 * Adds a new student to the system
	 * @param student
	 */
	public void addStudent(Student student) {
		students.put(student.getStudentID(),student);
	}

	/**
	 * Adds a new classroom to the system
	 * @param classroom
	 */
	public void addClassroom(Classroom classroom) {
		classrooms.put(classroom.getClassroomID(),classroom);
	}

	/**
	 * Adds a new document to the system
	 * @param document
	 */
	public void addDocument(Document document) {
		documents.put(document.getDocumentID(),document);
	}
	
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
	
	/**
	 * Serializes a list of users 
	 */
	public void saveUserIDToAccount() {
		try {
			ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream("userIDsToAccounts.ser"));
			output.writeObject(userIDToAccount);
			output.flush();
			output.close();
		} catch (IOException e) {e.printStackTrace();}
	} 
	
	/**
	 * Loads all of the users in the system
	 */
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
	
	/**
	 * Loads all users
	 */
	public void startUp() {
		loadUserIDToAccount();
	}
	
	/**
	 * Saves all users
	 */
	public void shutDown() {
		saveUserIDToAccount();
	}

}
