package com.richmond.darkhorse.ProjectSB.middleman;
import com.richmond.darkhorse.ProjectSB.Classroom;
import com.richmond.darkhorse.ProjectSB.Director;

public class ModifyExistingClassSize implements Runnable{
	
	private Classroom theClassroom;
	private int maxSize;
	private Director director;
	
	public ModifyExistingClassSize(Classroom theClassroom,int newMaxSize,Director director){
		this.theClassroom = theClassroom;
		this.maxSize = newMaxSize;
		this.director = director;
	}

	@Override
	public void run() {
		director.modifyClassSize(theClassroom, maxSize);
	}

}
