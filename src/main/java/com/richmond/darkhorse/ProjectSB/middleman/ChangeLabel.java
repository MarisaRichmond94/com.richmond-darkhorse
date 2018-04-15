package com.richmond.darkhorse.ProjectSB.middleman;
import javafx.scene.control.Label;

public class ChangeLabel implements Runnable{

	private Label label;
	private String message;
		
	public ChangeLabel(Label label,String message) {
		this.label = label;
		this.message = message;
	}

	@Override
	public void run() {
		label.setText(message);
	}
}

