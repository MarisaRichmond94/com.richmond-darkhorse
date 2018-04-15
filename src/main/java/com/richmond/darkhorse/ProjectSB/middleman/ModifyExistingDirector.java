package com.richmond.darkhorse.ProjectSB.middleman;
import com.richmond.darkhorse.ProjectSB.Admin;
import com.richmond.darkhorse.ProjectSB.Center;
import com.richmond.darkhorse.ProjectSB.StaffMember;

public class ModifyExistingDirector implements Runnable{

	private StaffMember staffMember;
	private String firstName, lastName, newTitle;
	private Center center;
	private Admin admin;
	
	public ModifyExistingDirector(StaffMember staffMember,String firstName,String lastName,String newTitle,Center center,Admin admin) {
		this.staffMember = staffMember;
		this.firstName = firstName;
		this.lastName = lastName;
		this.newTitle = newTitle;
		this.center = center;
		this.admin = admin;
	}

	@Override
	public void run() {
		admin.modifyStaffMemberDirector(staffMember,newTitle,firstName,lastName,center);
	}
	
}
