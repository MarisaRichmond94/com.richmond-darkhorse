package com.richmond.darkhorse.ProjectSB;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Mailbox implements Serializable{
	
	private static final long serialVersionUID = 8453756L;
	private String userID, centerID;
	private Map<String,BehaviorReport> unsignedBehaviorReports;
	private Map<String,IncidentReport> unsignedIncidentReports;
	private Map<String,BehaviorReport> signedBehaviorReports;
	private Map<String,IncidentReport> signedIncidentReports;
	private Map<String,String> messages;
	
	public Mailbox(StaffMember staffMember) {
		this.userID = staffMember.getUserID();
		this.centerID = staffMember.getCenterID();
		this.unsignedBehaviorReports = new HashMap<String,BehaviorReport>();
		this.unsignedIncidentReports = new HashMap<String,IncidentReport>();
		this.signedBehaviorReports = new HashMap<String,BehaviorReport>();
		this.signedIncidentReports = new HashMap<String,IncidentReport>();
		this.messages = new HashMap<String,String>();
	}
	
	public void clear() {
		unsignedBehaviorReports.clear();
		unsignedIncidentReports.clear();
		signedBehaviorReports.clear();
		signedIncidentReports.clear();
	}
	
	//Add methods
	public void addUnsignedBehaviorReport(BehaviorReport unsignedBehaviorReport) {
		String documentID = unsignedBehaviorReport.getDocumentID();
		unsignedBehaviorReports.put(documentID,unsignedBehaviorReport);
	}
	
	public void addUnsignedIncidentReport(IncidentReport unsignedIncidentReport) {
		String documentID = unsignedIncidentReport.getDocumentID();
		unsignedIncidentReports.put(documentID,unsignedIncidentReport);
	}
	
	public void addSignedBehaviorReport(BehaviorReport signedBehaviorReport) {
		String documentID = signedBehaviorReport.getDocumentID();
		signedBehaviorReports.put(documentID,signedBehaviorReport);
		if(unsignedBehaviorReports.containsKey(documentID)) {
			unsignedBehaviorReports.remove(documentID);
		}
	}
	
	public void addSignedIncidentReport(IncidentReport signedIncidentReport) {
		String documentID = signedIncidentReport.getDocumentID();
		signedIncidentReports.put(documentID,signedIncidentReport);
		if(unsignedIncidentReports.containsKey(documentID)) {
			unsignedIncidentReports.remove(documentID);
		}
	}

	//Standard getters and setters 
	public Map<String,BehaviorReport> getUnsignedBehaviorReports() {
		return unsignedBehaviorReports;
	}

	public void setUnsignedBehaviorReports(Map<String,BehaviorReport> unsignedBehaviorReports) {
		this.unsignedBehaviorReports = unsignedBehaviorReports;
	}

	public Map<String, IncidentReport> getUnsignedIncidentReports() {
		return unsignedIncidentReports;
	}

	public void setUnsignedIncidentReports(Map<String,IncidentReport> unsignedIncidentReports) {
		this.unsignedIncidentReports = unsignedIncidentReports;
	}

	public Map<String, BehaviorReport> getSignedBehaviorReports() {
		return signedBehaviorReports;
	}

	public void setSignedBehaviorReports(Map<String,BehaviorReport> signedBehaviorReports) {
		this.signedBehaviorReports = signedBehaviorReports;
	}

	public Map<String,IncidentReport> getSignedIncidentReports() {
		return signedIncidentReports;
	}

	public void setSignedIncidentReports(Map<String,IncidentReport> signedIncidentReports) {
		this.signedIncidentReports = signedIncidentReports;
	}

	public Map<String,String> getMessages() {
		return messages;
	}

	public void setMessages(Map<String,String> messages) {
		this.messages = messages;
	}

	public String getUserID() {
		return userID;
	}

	public String getCenterID() {
		return centerID;
	}
	
	public Map<String,Document> getAllSignedReports(){
		Map<String,Document> allSignedReports = new HashMap<String,Document>();
		if(this.getSignedBehaviorReports().size() != 0) {
			for(BehaviorReport behaviorReport : this.getSignedBehaviorReports().values()) {
				String documentID = behaviorReport.getDocumentID();
				allSignedReports.put(documentID,behaviorReport);
			}
		}
		if(this.getSignedIncidentReports().size() != 0) {
			for(IncidentReport incidentReport : this.getSignedIncidentReports().values()) {
				String documentID = incidentReport.getDocumentID();
				allSignedReports.put(documentID,incidentReport);
			}
		}
		return allSignedReports;
	}
	
	public StaffMember getStaffMember(String userID) {
		SpecialBeginnings sB = SpecialBeginnings.getInstance();
		Map<String,StaffMember> staffMembers = sB.getStaffMembers();
		if(staffMembers.containsKey(userID)) {
			return staffMembers.get(userID);
		}
		return null;
	}
	
	@Override
	public String toString() {
		return getStaffMember(userID).getFirstName() + " " + getStaffMember(userID).getLastName() + "'s mailbox";
	}

}
