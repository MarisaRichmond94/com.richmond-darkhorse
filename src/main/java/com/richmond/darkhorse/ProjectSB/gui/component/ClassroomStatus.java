package com.richmond.darkhorse.ProjectSB.gui.component;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.richmond.darkhorse.ProjectSB.Classroom;
import com.richmond.darkhorse.ProjectSB.Director;
import com.richmond.darkhorse.ProjectSB.StaffMember;
import com.richmond.darkhorse.ProjectSB.Student;
import com.richmond.darkhorse.ProjectSB.Teacher;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.control.ScrollPane;

public class ClassroomStatus implements DirectorLayout {
	
	private Director director;
	private Classroom classroom;
	private Map<String,Student> allStudents = new HashMap<String,Student>();
	
	public ClassroomStatus(Director director,Classroom classroom) {
		this.director = director;
		this.classroom = classroom;
		this.allStudents = director.getStudents();
	}
	
	public void display() {
		Stage stage = new Stage();
		GridPane classroomStatusLayout = buildGridPane();
		String stageTitle = classroom.toString() + " status";
		buildPopUp(stage,classroomStatusLayout,stageTitle);
	}

	/**
	 * Builds the layout for ClassroomStatus
	 * @return a fully-populated GridPane
	 */
	private GridPane buildGridPane() {
		GridPane gridpane = new GridPane();
		setConstraints(gridpane,6,0,10,10,"modulargridpane");
	    gridpane.getStylesheets().add("css/director.css");
	    Label title = createLabel(classroom.toString(),"title");
	    Label teachers = createLabel("Teacher(s):","label");
	    Label teachersPresent = populateTeachersPresent();
	    ScrollPane studentsPresentGrid = buildStudentsPresentPane();
	    ScrollPane studentsExpectedGrid = buildStudentsExpectedPane();
	    Label totalPresent = createLabel("Total: " + classroom.getStudentsPresent().size() + "","label");
	    Label totalExpected = createLabel("Total: " + classroom.getStudentsExpected().size() + "","label");
	    List<Node> nodes = new ArrayList<>();
	    nodes.addAll(Arrays.asList(title,teachers,teachersPresent,studentsPresentGrid,studentsExpectedGrid,totalPresent,totalExpected));
	    placeNodes(gridpane,nodes);
	    return gridpane;
	}
	
	@Override
	public void placeNodes(GridPane gridpane, List<Node> nodes) {
		placeNodeSpan(gridpane,nodes.get(0),2,0,2,1,"center",null);
		placeNodeSpan(gridpane,nodes.get(1),0,1,2,1,"right",null);
		placeNodeSpan(gridpane,nodes.get(2),2,1,4,1,"left",null);
		placeNodeSpan(gridpane,nodes.get(3),0,2,3,4,"center",null);
		placeNodeSpan(gridpane,nodes.get(4),3,2,3,4,"center",null);
		placeNodeSpan(gridpane,nodes.get(5),0,7,3,1,"center",null);
		placeNodeSpan(gridpane,nodes.get(6),3,7,3,1,"center",null);
	}
	
	/**
	 * Determines how many {@link Teacher}s are present in the {@link Classroom} and creates a new label containing their names
	 * @return label
	 */
	private Label populateTeachersPresent() {
		List<String> teachersPresent = classroom.getTeachersPresent();
	    Map<String,StaffMember> staffMembers = director.getStaffMembers();
	    String teachersPresentString = null;
	    if(classroom.getTeachersPresent().size() == 0 || classroom.getTeachersPresent() == null) {teachersPresentString = "no active teachers";}
	    else {
		    for(String teacherPresent : teachersPresent) {
		    		Teacher teacher = null;
		    		if(staffMembers.containsKey(teacherPresent)) {
		    			teacher = (Teacher) staffMembers.get(teacherPresent);
		    			teachersPresentString = teachersPresentString + " " + teacher.getFirstName() + " " + teacher.getLastName();
		    		}
		    }
	    }
	    Label teachersPresentLabel = createLabel(teachersPresentString,"label");
	    return teachersPresentLabel;
	}
	
	/**
	 * Creates a ScrollPane containing a list of present students
	 * @return ScrollPane
	 */
	private ScrollPane buildStudentsPresentPane() {
		GridPane gridpane = new GridPane();
	    Label presentStudentsLabel = createLabel("Students Present","subtitle");
	    gridpane.getStyleClass().add("gridpane");
	    placeNodeSpan(gridpane,presentStudentsLabel,0,0,2,1,"center",null);
	    populateStudentsPresent(gridpane);
	    ScrollPane scrollPane = new ScrollPane(gridpane);
	    scrollPane.getStyleClass().add("scrollpane");
	    scrollPane.setFitToHeight(true);
	    scrollPane.setFitToWidth(true);
	    return scrollPane;
	}
	
	/**
	 * Checks to see if the {@link Classroom} contains any {@link Student}s. If the {@link Classroom} contains {@link Student}s, a label is created for each and
	 * added to the GridPane. Otherwise, a default "no active student(s)" label is created 
	 * @param gridpane - layout
	 */
	private void populateStudentsPresent(GridPane gridpane) {
		List<String> studentsPresentList = classroom.getStudentsPresent();
	    if(studentsPresentList.size() < 1) {
		    	Label noStudents = createLabel("no active student(s)","label");
		    	placeNodeSpan(gridpane,noStudents,0,1,2,1,"center",null);
	    }else {
	    		int rowIndex = 1;
			for(String presentID : studentsPresentList) {
				if(allStudents.containsKey(presentID)) {
					Student theStudent = allStudents.get(presentID);
					Label newLabel = createLabel(theStudent.toString(),"label");
					placeNodeSpan(gridpane,newLabel,0,rowIndex,2,1,"center",null);
					rowIndex++;
				}
			}
	    }
	}
	
	/**
	 * Builds a ScrollPane containing the names of all {@link Student}s in the {@link Classroom} that are expected to arrive for the day but have not yet been 
	 * marked present by the {@link Teacher}
	 * @return ScrollPane
	 */
	private ScrollPane buildStudentsExpectedPane() {
		List<String> expected = classroom.getStudentsExpected(), present = classroom.getStudentsPresent();
	    Map<String,Student> presentMap = new HashMap<String,Student>();
	    for(String presentStudent : present) { if(allStudents.containsKey(presentStudent)) {presentMap.put(presentStudent,allStudents.get(presentStudent));}}
	    List<String> stillExpected = new ArrayList<String>();
	    for(String studentExpected : expected) {if(!presentMap.containsKey(studentExpected)) {stillExpected.add(studentExpected);}}
	    GridPane gridpane = new GridPane();
	    Label expectedStudentsLabel = createLabel("Students Expected","subtitle");
	    gridpane.getStyleClass().add("gridpane");
	    placeNodeSpan(gridpane,expectedStudentsLabel,0,0,2,1,"center",null);
		int row = 1;
		for(String expectedID : stillExpected) {
			if(allStudents.containsKey(expectedID)) {
				Student theStudent = allStudents.get(expectedID);
				Label newLabel = createLabel(theStudent.toString(),"label");
				placeNodeSpan(gridpane,newLabel,0,row,2,1,"center",null);
				row++;
			}
		}
	    ScrollPane scrollPane = new ScrollPane(gridpane);
	    scrollPane.getStyleClass().add("scrollpane");
	    scrollPane.setFitToHeight(true);
	    scrollPane.setFitToWidth(true);
	    return scrollPane;
	}
	
}
