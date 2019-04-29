package application.model;
/**
 * The instructions are returned in the Get Recipe Information
 * API call and returned as a list of analyzed instructions.
 * 
 * @author Harrison Luko bqr789
 * UTSA CS 3443 - Team Project
 * Spring 2019
 */
import java.util.ArrayList;

public class Instruction {

	private String name;
	
	private ArrayList<Step> steps;

	/**
	 * return the arraylist of steps
	 * @return
	 */
	public ArrayList<Step> getSteps() {
		return steps;
	}
	
	/**
	 * set the arraylist of steps
	 * @param steps
	 */
	public void setSteps(ArrayList<Step> steps) {
		this.steps = steps;
	}
	
	/**
	 * return the instruction name
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * Set the instruction name
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}
}
