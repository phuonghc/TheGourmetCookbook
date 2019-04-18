package application.model;

import java.util.ArrayList;

/**
 * This class contains attributes and behaviors related to object Menu.
 * 
 * @author Sayontani Ray (pek684)
 * UTSA CS 3443 - Team Project
 * Spring 2019
 */
public class Menu {
	
	// Attributes or properties of a Menu
	private int totalResults;
	private ArrayList<MenuItem> results;
	
	/**
	 * @return the totalResults
	 */
	public int getTotalResults() {
		return totalResults;
	}
	
	/**
	 * @param totalResults the totalResults to set
	 */
	public void setTotalResults(int totalResults) {
		this.totalResults = totalResults;
	}
	
	/**
	 * @return the results
	 */
	public ArrayList<MenuItem> getResults() {
		return results;
	}
	
	/**
	 * @param results the results to set
	 */
	public void setResults(ArrayList<MenuItem> results) {
		this.results = results;
	}
	
}
