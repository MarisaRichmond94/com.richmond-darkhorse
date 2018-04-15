package com.richmond.darkhorse.ProjectSB.middleman;
import com.richmond.darkhorse.ProjectSB.Admin;
import com.richmond.darkhorse.ProjectSB.Center;

public class CreateNewCenter implements Runnable{
	
	private String centerName, abbreviatedName, licenseName, licenseNumber, address, city, county;
	private Admin admin;
	
	public CreateNewCenter(String centerName,String abbreviatedName,String licenseName,String licenseNumber,String address,String city,String county,Admin admin) {
		this.centerName = centerName;
		this.abbreviatedName = abbreviatedName;
		this.licenseName = licenseName;
		this.licenseNumber = licenseNumber;
		this.address = address;
		this.city = city;
		this.county = county;
		this.admin = admin;
	}

	@Override
	public void run() {
		Center center = admin.createCenter(centerName,abbreviatedName,licenseName,licenseNumber,address,city,county);
		admin.addNewCenter(center);
	}
	
}
