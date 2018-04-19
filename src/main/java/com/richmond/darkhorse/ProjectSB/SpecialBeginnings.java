package com.richmond.darkhorse.ProjectSB;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;

public class SpecialBeginnings {

	private static SpecialBeginnings instance;
	private Map<String,Center> centers;
	private Map<String,StaffMember> staffMembers;
	
	public SpecialBeginnings() {
		this.centers = new HashMap<String,Center>();
		this.staffMembers = new HashMap<String,StaffMember>();
	}
	
	/**
	 * Gets the only instance of the special beginnings. If one hasn't been created, it
	 * @return {@link SpecialBeginnings} - a singleton instance of special beginnings
	 */
	public static SpecialBeginnings getInstance() {
		if (instance == null) {
			instance = new SpecialBeginnings();
		}
		return instance;
	}
	
	//Add methods
    public void addCenter(Center center) {
		centers.put(center.getCenterID(),center);
	}
    
    public void addStaffMember(StaffMember staffMember) {
		staffMembers.put(staffMember.getUserID(),staffMember);
	}
    
    //Save methods
    public void saveCenters() {
    		try {
				ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream("centers.ser"));
				output.writeObject(centers);
				output.flush();
				output.close();
			} catch (IOException e) {e.printStackTrace();}
    }
    
    public void saveStaffMembers() {
    		try {
				ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream("staffmembers.ser"));
				output.writeObject(staffMembers);
				output.flush();
				output.close();
			} catch (IOException e) {e.printStackTrace();}
    }  

    //Load methods
    public void loadCenters() {
	    	if(centers.isEmpty()) {
				try {
					ObjectInputStream objectInput = new ObjectInputStream(new FileInputStream("centers.ser"));
					@SuppressWarnings("unchecked")
					Map<String,Center> centerList = (Map<String,Center>)objectInput.readObject();
					centers = centerList;
			        objectInput.close();
				} catch (FileNotFoundException e) {e.printStackTrace();
				} catch (IOException e) {e.printStackTrace();
				} catch (ClassNotFoundException e) {e.printStackTrace();}
	    	}
    }
    
    public void loadStaffMembers() {
    		try {
				ObjectInputStream objectInput = new ObjectInputStream(new FileInputStream("staffmembers.ser"));
				@SuppressWarnings("unchecked")
				Map<String,StaffMember> staffList = (Map<String,StaffMember>)objectInput.readObject();
				staffMembers = staffList;
		        objectInput.close();
			} catch (FileNotFoundException e) {e.printStackTrace();
			} catch (IOException e) {e.printStackTrace();
			} catch (ClassNotFoundException e) {e.printStackTrace();}
    	}
    
    //Remove methods
    public void removeCenter(String centerID) {
    		if(centers.containsKey(centerID)) {centers.remove(centerID);}
    }
    
    public void removeDirector(Director director) {
	    	Center center = director.getCenter(director.getCenterID());
	    	center.removeCenterDirector();
    		String userID = director.getUserID();
    		if(staffMembers.containsKey(userID)) {staffMembers.remove(userID);}
    }
    
    //Standard getters 
    public Map<String,Center> getCenters() {
		return centers;
	}
    
    public Map<String,StaffMember> getStaffMembers() {
		return staffMembers;
	}
    
}