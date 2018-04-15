package com.richmond.darkhorse.ProjectSB;

import java.io.Serializable;

public class Event implements Serializable{
	
	private static final long serialVersionUID = 8453756L;
	private String summary,day,time,details;
	private int dayOfWeek;
	
	public Event(String summary,String day,String time,String details) {
		this.summary = summary;
		this.day = day;
		this.time = time;
		this.details = details;
		if(day.equals("Monday")) { this.dayOfWeek = 1;}
		if(day.equals("Tuesday")) { this.dayOfWeek = 2;}
		if(day.equals("Wednesday")) { this.dayOfWeek = 3;}
		if(day.equals("Thursday")) { this.dayOfWeek = 4;}
		if(day.equals("Friday")) { this.dayOfWeek = 5;}
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public int getDayOfWeek() {
		return dayOfWeek;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

}
