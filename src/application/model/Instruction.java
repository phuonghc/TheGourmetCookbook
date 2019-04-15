package application.model;

import java.util.ArrayList;

public class Instruction {

	private String name;
	
	private ArrayList<Step> steps;

	public ArrayList<Step> getSteps() {
		return steps;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
