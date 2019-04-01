package application.model;

import java.util.ArrayList;

public class Menu {
	
	private int totalResults;
	private ArrayList<MenuItem> results;

	public ArrayList<MenuItem> getResults() {
		return results;
	}
	public void setResults(ArrayList<MenuItem> results) {
		this.results = results;
	}
	public int getTotalResults() {
		return totalResults;
	}
	public void setTotalResults(int totalResults) {
		this.totalResults = totalResults;
	}
	
}
