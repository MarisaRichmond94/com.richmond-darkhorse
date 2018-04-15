package com.richmond.darkhorse.ProjectSB.middleman;
import com.richmond.darkhorse.ProjectSB.AccountManager;
import com.richmond.darkhorse.ProjectSB.Center;
import com.richmond.darkhorse.ProjectSB.SpecialBeginnings;
import java.util.Map;

public class LoadData implements Runnable{

	@Override
	public void run() {
		startUp();
	}
	
	/**
	 * Initializes the set up of the program, including the loading of all necessary data/files, including those containing @{Center}s, @{StaffMember}s, etc. 
	 */
	public static void startUp() {
		AccountManager accountManager = AccountManager.getInstance();
		accountManager.loadUserIDToAccount();
		System.out.println(accountManager.getUserIDs());
		SpecialBeginnings sB = SpecialBeginnings.getInstance();
		sB.loadCenters();
		sB.loadStaffMembers();
		Map<String,Center> centers = sB.getCenters();
		for(Center center: centers.values()) {
			center.loadClassrooms();
			center.loadStudents();
		}
	}

}
