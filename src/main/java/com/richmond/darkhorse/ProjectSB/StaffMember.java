package com.richmond.darkhorse.ProjectSB;
import java.io.Serializable;
import java.util.Map;

public class StaffMember implements Account,Serializable{
	
	private static final long serialVersionUID = 8453756L;
	protected String firstName;
	protected String lastName;
	protected String title;
	protected String userID;
	protected String centerID; 
	protected Mailbox mailbox;
	protected Credential credentials;
	protected Contact contact;
	protected Schedule schedule;
	
	public StaffMember(String firstName,String lastName,Center center) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.centerID = center.getCenterID();
		this.mailbox = new Mailbox(this);
		this.schedule = new Schedule(this);
	}
	
	/**
	 * This method uses a staff member's centerID to get the corresponding center by pulling all of the centers from Special Beginnings and searching them for the center
	 * with the same centerID
	 * @param centerID - a unique 4-digit String held by a {@link Center} object
	 * @return - the {@link Center} with that 4-digit String 
	 */
	public Center getCenter(String centerID) {
		SpecialBeginnings sB = SpecialBeginnings.getInstance();
		Map<String,Center> centers = sB.getCenters();
		if(centers.containsKey(centerID)) {
			return centers.get(centerID);
		}
		return null;
	}
	
    //Standard getters and setters
	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getTitle() {
		return title;
	}

	public String getUserID() {
		return userID;
	}

	public String getCenterID() {
		return centerID;
	}

	public Mailbox getMailbox() {
		return mailbox;
	}

	public Credential getCredentials() {
		return credentials;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setCenterID(String centerID) {
		this.centerID = centerID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public Contact getContact() {
		return contact;
	}

	public void setContact(Contact contact) {
		this.contact = contact;
	}

	public Schedule getSchedule() {
		return schedule;
	}

}
