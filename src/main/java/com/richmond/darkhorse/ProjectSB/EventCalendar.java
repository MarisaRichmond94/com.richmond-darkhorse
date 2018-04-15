package com.richmond.darkhorse.ProjectSB;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class EventCalendar implements Serializable{

	private static final long serialVersionUID = 8453756L;
	private List<Event> events;
	private List<Event> mondayEvents = new ArrayList<Event>();
	private List<Event> tuesdayEvents = new ArrayList<Event>();
	private List<Event> wednesdayEvents = new ArrayList<Event>();
	private List<Event> thursdayEvents = new ArrayList<Event>();
	private List<Event> fridayEvents = new ArrayList<Event>();
	
	public EventCalendar() {
		this.events = new ArrayList<Event>();
	}
	
	public void addEvent(Event event) {
		events.add(event);
		if(event.getDayOfWeek() == 1) {mondayEvents.add(event);}
		else if(event.getDayOfWeek() == 2) {tuesdayEvents.add(event);}
		else if(event.getDayOfWeek() == 3) {wednesdayEvents.add(event);}
		else if(event.getDayOfWeek() == 4) {thursdayEvents.add(event);}
		else {fridayEvents.add(event);}
	}
	
	public void removeEvent(Event event) {
		events.remove(event);
		if(event.getDayOfWeek() == 1) {mondayEvents.remove(event);}
		else if(event.getDayOfWeek() == 2) {tuesdayEvents.remove(event);}
		else if(event.getDayOfWeek() == 3) {wednesdayEvents.remove(event);}
		else if(event.getDayOfWeek() == 4) {thursdayEvents.remove(event);}
		else {fridayEvents.remove(event);}
	}
	
	public List<Event> getEvents(){
		return events;
	}

	public List<Event> getMondayEvents() {
		return mondayEvents;
	}

	public List<Event> getTuesdayEvents() {
		return tuesdayEvents;
	}

	public List<Event> getWednesdayEvents() {
		return wednesdayEvents;
	}

	public List<Event> getThursdayEvents() {
		return thursdayEvents;
	}

	public List<Event> getFridayEvents() {
		return fridayEvents;
	}
	
	public void clear() {
		events.removeAll(events);
		mondayEvents.removeAll(mondayEvents);
		tuesdayEvents.removeAll(tuesdayEvents);
		wednesdayEvents.removeAll(wednesdayEvents);
		thursdayEvents.removeAll(thursdayEvents);
		fridayEvents.removeAll(fridayEvents);
	}
	
}
