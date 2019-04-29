package application.model;
/**
 * Equipment is returned in the Get Recipe Information
 * API call
 * 
 * @author Harrison Luko bqr789
 * UTSA CS 3443 - Team Project
 * Spring 2019
 */
public class Equipment {
	
	private String name;

	/**
	 * Get the equipment name
	 * @return
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Set the equipment name
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}
}
