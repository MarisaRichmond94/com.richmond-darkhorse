package com.richmond.darkhorse.ProjectSB.gui.component;
import java.time.*;
import java.util.HashMap;
import java.util.Map;
import com.richmond.darkhorse.ProjectSB.Classroom;
import com.richmond.darkhorse.ProjectSB.Teacher;
import javafx.scene.control.Button;

public interface ScheduleHelper {

	static Map<LocalTime,Integer> columnSelector = new HashMap<>();
	static Duration CORE_HOURS = Duration.between(LocalTime.of(8,00),LocalTime.of(17,00));
	
	/**
	 * Using the information provided, creates a new Button representing a snapshot of the {@link Classroom} on the passed day, including {@link Teacher}s' 
	 * {@link Schedule}s. If there is not an appropriate {@link Teacher} to {@link Student} ratio, the Button's color will be set to red to let the {@link Director}
	 * know that an adjustment needs to be made
	 * @param classroom - the {@link Classroom}
	 * @param leadTeacher - the lead {@link Teacher}
	 * @param assistantTeacher - the assistant {@link Teacher} 
	 * @param dayNum - the number correlating with the passed day (e.g. 1 for Monday, etc.)
	 * @param day - the String day (e.g. "Monday")
	 * @return Button
	 */
	public default Button isInRatio(Classroom classroom,Teacher leadTeacher,Teacher assistantTeacher,int dayNum,String day) throws IllegalArgumentException {
		if(assistantTeacher == null || leadTeacher == null) { throw new IllegalArgumentException();}
		Button newButton = new Button();
		newButton.setPrefSize(300,200);
		if(leadTeacher.getSchedule().getStartTimes().get(dayNum) != null && assistantTeacher.getSchedule().getStartTimes().get(dayNum) != null) { 
			String lead = leadTeacher.getFirstName() + " " + leadTeacher.getLastName(), assistant = assistantTeacher.getFirstName() + " " + assistantTeacher.getLastName();
			String leadsSchedule = leadTeacher.getSchedule().getStartTimes().get(dayNum) + "-" + leadTeacher.getSchedule().getStopTimes().get(dayNum);
			String assistantsSchedule = assistantTeacher.getSchedule().getStartTimes().get(dayNum) + "-" + leadTeacher.getSchedule().getStopTimes().get(dayNum);
			newButton.setText(classroom.toString() + "\n" + lead + "\n" + leadsSchedule + "\n" + assistant + "\n" + assistantsSchedule);
			LocalTime leadStart = leadTeacher.getSchedule().getStartTimes().get(dayNum), leadEnd = leadTeacher.getSchedule().getStopTimes().get(dayNum);
			LocalTime assistantStart = assistantTeacher.getSchedule().getStartTimes().get(dayNum), assistantEnd = assistantTeacher.getSchedule().getStopTimes().get(dayNum);
			Duration leadDuration = Duration.between(leadStart.plusHours(isLoop(leadStart)),leadEnd.plusHours(isLoop(leadEnd)));
			Duration assistantDuration = Duration.between(assistantStart.plusHours(isLoop(assistantStart)),assistantEnd.plusHours(isLoop(assistantEnd)));
			checkCoreHours(classroom,newButton,classroom.generateClassList(day).size(),leadDuration,assistantDuration);
		}else if(leadTeacher.getSchedule().getStartTimes().get(dayNum) != null && assistantTeacher.getSchedule().getStartTimes().get(dayNum) == null) {
			String lead = leadTeacher.getFirstName() + " " + leadTeacher.getLastName();
			String leadsSchedule = leadTeacher.getSchedule().getStartTimes().get(dayNum) + "-" + leadTeacher.getSchedule().getStopTimes().get(dayNum);
			newButton.setText(classroom.toString() + "\n" + lead + "\n" + leadsSchedule);
			LocalTime leadStart = leadTeacher.getSchedule().getStartTimes().get(dayNum), leadEnd = leadTeacher.getSchedule().getStopTimes().get(dayNum);
			Duration leadDuration = Duration.between(leadStart.plusHours(isLoop(leadStart)),leadEnd.plusHours(isLoop(leadEnd)));
			checkCoreHours(classroom,newButton,classroom.generateClassList(day).size(),leadDuration);
		}else if(leadTeacher.getSchedule().getStartTimes().get(dayNum) == null && assistantTeacher.getSchedule().getStartTimes().get(dayNum) != null) {
			String assistant = assistantTeacher.getFirstName() + " " + assistantTeacher.getLastName();
			String assistantsSchedule = assistantTeacher.getSchedule().getStartTimes().get(dayNum) + "-" + assistantTeacher.getSchedule().getStopTimes().get(dayNum);
			newButton.setText(classroom.toString() + "\n" + assistant + "\n" + assistantsSchedule);
			LocalTime assistantStart = assistantTeacher.getSchedule().getStartTimes().get(dayNum), assistantEnd = assistantTeacher.getSchedule().getStopTimes().get(dayNum);
			Duration assistantDuration = Duration.between(assistantStart.plusHours(isLoop(assistantStart)),assistantEnd.plusHours(isLoop(assistantEnd)));
			checkCoreHours(classroom,newButton,classroom.generateClassList(day).size(),assistantDuration);
		}else {
			newButton.setText(classroom.toString());
			newButton.getStyleClass().add("badbutton");
		}
		return newButton;
	}
	
	/**
	 * Using the information provided, creates a new Button representing a snapshot of the {@link Classroom} on the passed day, including {@link Teacher}s' 
	 * {@link Schedule}s. If there is not an appropriate {@link Teacher} to {@link Student} ratio, the Button's color will be set to red to let the {@link Director}
	 * know that an adjustment needs to be made
	 * @param classroom - the {@link Classroom}
	 * @param leadTeacher - the lead {@link Teacher}
	 * @param dayNum - the number correlating with the passed day (e.g. 1 for Monday, etc.)
	 * @param day - the String day (e.g. "Monday")
	 * @return Button
	 */
	public default Button isInRatio(Classroom classroom,Teacher leadTeacher,int dayNum,String day) throws IllegalArgumentException{
		if(leadTeacher == null) { throw new IllegalArgumentException();}
		Button newButton = new Button();
		newButton.setPrefSize(300,200);
		if(leadTeacher.getSchedule().getStartTimes().get(dayNum) != null) { 
			String lead = leadTeacher.getFirstName() + " " + leadTeacher.getLastName();
			String leadsSchedule = leadTeacher.getSchedule().getStartTimes().get(dayNum) + "-" + leadTeacher.getSchedule().getStopTimes().get(dayNum);
			newButton.setText(classroom.toString() + "\n" + lead + "\n" + leadsSchedule);
			LocalTime leadStart = leadTeacher.getSchedule().getStartTimes().get(dayNum), leadEnd = leadTeacher.getSchedule().getStopTimes().get(dayNum);
			Duration leadDuration = Duration.between(leadStart.plusHours(isLoop(leadStart)),leadEnd.plusHours(isLoop(leadEnd)));
			checkCoreHours(classroom,newButton,classroom.generateClassList(day).size(),leadDuration);
		}else {
			newButton.setText(classroom.toString());
			newButton.getStyleClass().add("badbutton");
		}
		return newButton;
	}
	
	public default boolean isClassroomInRatio(Classroom classroom,Teacher leadTeacher,Teacher assistantTeacher,int dayNum,String day) {
		if(classroom.generateClassList(day).size() > 18 || assistantTeacher == null && leadTeacher == null) { return false;}
		boolean lead = false, assistant = false;
		if(classroom.generateClassList(day).size() <= 18) {
			if(leadTeacher.getSchedule().getStartTimes().get(dayNum) != null && assistantTeacher.getSchedule().getStartTimes().get(dayNum) != null) {
				LocalTime leadStart = leadTeacher.getSchedule().getStartTimes().get(dayNum), leadEnd = leadTeacher.getSchedule().getStopTimes().get(dayNum);
				LocalTime assistantStart = assistantTeacher.getSchedule().getStartTimes().get(dayNum), assistantEnd = assistantTeacher.getSchedule().getStopTimes().get(dayNum);
				Duration leadDuration = Duration.between(leadStart.plusHours(isLoop(leadStart)),leadEnd.plusHours(isLoop(leadEnd)));
				Duration assistantDuration = Duration.between(assistantStart.plusHours(isLoop(assistantStart)),assistantEnd.plusHours(isLoop(assistantEnd)));
				if(leadDuration.compareTo(CORE_HOURS) > 0) { lead = true; }
				if(assistantDuration.compareTo(CORE_HOURS) > 0) { assistant = true; }
				if(lead && assistant) { return true; } 
				else { return false; }
			}else{ return false; }
		}else if(classroom.generateClassList(day).size() <= 12) {
			if(leadTeacher.getSchedule().getStartTimes().get(dayNum) != null && assistantTeacher.getSchedule().getStartTimes().get(dayNum) != null) {
				LocalTime leadStart = leadTeacher.getSchedule().getStartTimes().get(dayNum), leadEnd = leadTeacher.getSchedule().getStopTimes().get(dayNum);
				LocalTime assistantStart = assistantTeacher.getSchedule().getStartTimes().get(dayNum), assistantEnd = assistantTeacher.getSchedule().getStopTimes().get(dayNum);
				Duration leadDuration = Duration.between(leadStart.plusHours(isLoop(leadStart)),leadEnd.plusHours(isLoop(leadEnd)));
				Duration assistantDuration = Duration.between(assistantStart.plusHours(isLoop(assistantStart)),assistantEnd.plusHours(isLoop(assistantEnd)));
				if(leadDuration.compareTo(CORE_HOURS) > 0) { lead = true; }
				if(assistantDuration.compareTo(CORE_HOURS) > 0) { assistant = true; }
				if(lead || assistant) { return true; } 
				else { return false; }
			}else if(leadTeacher.getSchedule().getStartTimes().get(dayNum) != null && assistantTeacher.getSchedule().getStartTimes().get(dayNum) == null) {
				LocalTime leadStart = leadTeacher.getSchedule().getStartTimes().get(dayNum), leadEnd = leadTeacher.getSchedule().getStopTimes().get(dayNum);
				Duration leadDuration = Duration.between(leadStart.plusHours(isLoop(leadStart)),leadEnd.plusHours(isLoop(leadEnd)));
				if(leadDuration.compareTo(CORE_HOURS) > 0) { lead = true; }
				else { return false; }
			}else if(leadTeacher.getSchedule().getStartTimes().get(dayNum) == null && assistantTeacher.getSchedule().getStartTimes().get(dayNum) != null) {
				LocalTime assistantStart = assistantTeacher.getSchedule().getStartTimes().get(dayNum), assistantEnd = assistantTeacher.getSchedule().getStopTimes().get(dayNum);
				Duration assistantDuration = Duration.between(assistantStart.plusHours(isLoop(assistantStart)),assistantEnd.plusHours(isLoop(assistantEnd)));
				if(assistantDuration.compareTo(CORE_HOURS) > 0) { assistant = true; }
				else { return false; }
			}
		}
		return false;
	}
	
	public default boolean isClassroomInRatio(Classroom classroom,Teacher leadTeacher,int dayNum,String day) {
		if(classroom.generateClassList(day).size() > 12 || leadTeacher == null) { return false;}
		if(classroom.generateClassList(day).size() <= 12) {
			if(leadTeacher.getSchedule().getStartTimes().get(dayNum) != null) {
				LocalTime leadStart = leadTeacher.getSchedule().getStartTimes().get(dayNum), leadEnd = leadTeacher.getSchedule().getStopTimes().get(dayNum);
				Duration leadDuration = Duration.between(leadStart.plusHours(isLoop(leadStart)),leadEnd.plusHours(isLoop(leadEnd)));
				if(leadDuration.compareTo(CORE_HOURS) > 0) { return true; }
				else { return false; }
			}
		}
		return false;
	}
	
	/**
	 * Checks to see if {@link Teacher}s' {@link Schedule}s cover the core hours of the {@link Classroom} between 8:00am and 5:00pm based on the ratio of the 
	 * {@link Student}s. If they do, the passed Button's color will be set to green, showing the {@link Schedule} has no obvious holes; otherwise, the passed Button's
	 * color will be set to red, signifying an obvious hole
	 * @param newButton - Button
	 * @param studentCount - the number of {@link Student}s anticipated on that day
	 * @param leadDuration - the period of time the lead {@link Teacher} is scheduled for
	 * @param assistantDuration - the period of time the assistant {@link Teacher} is scheduled for
	 */
	public default void checkCoreHours(Classroom classroom,Button newButton,int studentCount,Duration leadDuration,Duration assistantDuration) {
		if(studentCount > 18) {newButton.getStyleClass().add("badbutton");}
		else if(studentCount <= 18 && studentCount > 12) {
			if(leadDuration.compareTo(CORE_HOURS) < 0) {newButton.getStyleClass().add("badbutton");}
			if(assistantDuration.compareTo(CORE_HOURS) < 0) {newButton.getStyleClass().add("badbutton");}
			else {newButton.getStyleClass().add("goodbutton");}
		}else if(studentCount <= 12) {
			if(leadDuration.compareTo(CORE_HOURS) < 0 && assistantDuration.compareTo(CORE_HOURS) < 0) {newButton.getStyleClass().add("badbutton");}
			else {newButton.getStyleClass().add("goodbutton");}
		}
	}
	
	/**
	 * Checks to see if {@link Teacher}'s {@link Schedule} covers the core hours of the {@link Classroom} between 8:00am and 5:00pm based on the ratio of the 
	 * {@link Student}s. If they do, the passed Button's color will be set to green, showing the {@link Schedule} has no obvious holes; otherwise, the passed Button's
	 * color will be set to red, signifying an obvious hole
	 * @param newButton - Button
	 * @param studentCount - the number of {@link Student}s anticipated on that day
	 * @param leadDuration - the period of time the lead {@link Teacher} is scheduled for
	 */
	public default void checkCoreHours(Classroom classroom,Button newButton,int studentCount,Duration leadDuration) {
		if(studentCount > 12) {newButton.getStyleClass().add("badbutton");}
		else if(studentCount <= 12) {
			if(leadDuration.compareTo(CORE_HOURS) < 0) {newButton.getStyleClass().add("badbutton");}
			else {newButton.getStyleClass().add("goodbutton");}
		}
	}
	
	public default boolean isHolePresent(Classroom classroom,Teacher leadTeacher,Teacher assistantTeacher,int dayNum,String day) throws IllegalArgumentException{
		if(assistantTeacher == null || leadTeacher == null) { throw new IllegalArgumentException();}
		boolean status = false;
		if(leadTeacher.getSchedule().getStartTimes().get(dayNum) != null && assistantTeacher.getSchedule().getStartTimes().get(dayNum) != null) { 
			LocalTime leadStart = leadTeacher.getSchedule().getStartTimes().get(dayNum), leadEnd = leadTeacher.getSchedule().getStopTimes().get(dayNum);
			LocalTime assistantStart = assistantTeacher.getSchedule().getStartTimes().get(dayNum), assistantEnd = assistantTeacher.getSchedule().getStopTimes().get(dayNum);
			Duration leadDuration = Duration.between(leadStart.plusHours(isLoop(leadStart)),leadEnd.plusHours(isLoop(leadEnd)));
			Duration assistantDuration = Duration.between(assistantStart.plusHours(isLoop(assistantStart)),assistantEnd.plusHours(isLoop(assistantEnd)));
			return areCoreHoursNotMet(classroom,classroom.generateClassList(day).size(),leadDuration,assistantDuration);
		}else if(leadTeacher.getSchedule().getStartTimes().get(dayNum) != null && assistantTeacher.getSchedule().getStartTimes().get(dayNum) == null) {
			LocalTime leadStart = leadTeacher.getSchedule().getStartTimes().get(dayNum), leadEnd = leadTeacher.getSchedule().getStopTimes().get(dayNum);
			Duration leadDuration = Duration.between(leadStart.plusHours(isLoop(leadStart)),leadEnd.plusHours(isLoop(leadEnd)));
			return areCoreHoursNotMet(classroom,classroom.generateClassList(day).size(),leadDuration);
		}else if(leadTeacher.getSchedule().getStartTimes().get(dayNum) == null && assistantTeacher.getSchedule().getStartTimes().get(dayNum) != null) {
			LocalTime assistantStart = assistantTeacher.getSchedule().getStartTimes().get(dayNum), assistantEnd = assistantTeacher.getSchedule().getStopTimes().get(dayNum);
			Duration assistantDuration = Duration.between(assistantStart.plusHours(isLoop(assistantStart)),assistantEnd.plusHours(isLoop(assistantEnd)));
			return areCoreHoursNotMet(classroom,classroom.generateClassList(day).size(),assistantDuration);
		}
		return status;
	}
	
	public default boolean areCoreHoursNotMet(Classroom classroom,int studentCount,Duration leadDuration,Duration assistantDuration) {
		if(studentCount > 18) {return true;}
		else if(studentCount <= 18 && studentCount > 12) {
			if(leadDuration.compareTo(CORE_HOURS) < 0) {return true;}
			if(assistantDuration.compareTo(CORE_HOURS) < 0) {return true;}
			else {return false;}
		}else if(studentCount <= 12) {
			if(leadDuration.compareTo(CORE_HOURS) < 0 && assistantDuration.compareTo(CORE_HOURS) < 0) {return true;}
			else {return false;}
		}
		return true;
	}
	
	public default boolean isHolePresent(Classroom classroom,Teacher leadTeacher,int dayNum,String day) throws IllegalArgumentException{
		if(leadTeacher == null) { throw new IllegalArgumentException();}
		boolean status = false;
		if(leadTeacher.getSchedule().getStartTimes().get(dayNum) != null) { 
			LocalTime leadStart = leadTeacher.getSchedule().getStartTimes().get(dayNum), leadEnd = leadTeacher.getSchedule().getStopTimes().get(dayNum);
			Duration leadDuration = Duration.between(leadStart.plusHours(isLoop(leadStart)),leadEnd.plusHours(isLoop(leadEnd)));
			return areCoreHoursNotMet(classroom,classroom.generateClassList(day).size(),leadDuration);
		}
		return status;
	}
	
	public default boolean areCoreHoursNotMet(Classroom classroom,int studentCount,Duration leadDuration) {
		if(studentCount > 12) {return true;}
		else if(studentCount <= 12) {
			if(leadDuration.compareTo(CORE_HOURS) < 0) {return true;}
			else {return false;}
		}
		return false;
	}
	
	public default long isLoop(LocalTime time) {
		if(time == null) {
			IllegalArgumentException e = new IllegalArgumentException();
			e.printStackTrace();
		}
		LocalTime start = LocalTime.of(1,00), end = LocalTime.of(6,30);
		if(time.equals(start) || time.isAfter(start) && time.isBefore(end)) {return 12L;}
		return 0L;
	}
	
}

