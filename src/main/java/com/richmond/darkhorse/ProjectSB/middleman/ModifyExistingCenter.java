package com.richmond.darkhorse.ProjectSB.middleman;
import com.richmond.darkhorse.ProjectSB.Admin;
import com.richmond.darkhorse.ProjectSB.Center;

public class ModifyExistingCenter implements Runnable{

	private Center center;
	private String centerName;
	private String abbreviatedName;
	private String licenseName;
	private String address;
	private String city;
	private String county;
	private Admin admin;
	
	public ModifyExistingCenter(Center center,String centerName,String abbreviatedName,String licenseName,String address,String city,String county,Admin admin) {
		this.center = center;
		this.centerName = centerName;
		this.abbreviatedName = abbreviatedName;
		this.licenseName = licenseName;
		this.address = address;
		this.city = city;
		this.county = county;
		this.admin = admin;
	}

	@Override
	public void run() {
		admin.modifyCenter(center,centerName,abbreviatedName,licenseName,address,city,county);
		admin.addNewCenter(center);
	}
	
}
