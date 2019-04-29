package application.model;
/**
 * These ingredients are returned in the Get Recipe Information
 * API call and listed as extended ingredients
 * @author Harrison Luko bqr789
 * 
 * UTSA CS 3443 - Team Project
 * Spring 2019
 */
public class Ingredient {
	
	private String name;
	
	private String title;
	
	private double amount;
	
	private String unit;
	
	/**
	 * Get ingredient name
	 * @return
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * Set ingredient Name
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Get ingredient title
	 * @return
	 */
	public String getTitle() {
		return this.title;
	}

	/**
	 * Set ingredient title
	 * @param title
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	
	/**
	 * Get ingredient Amount
	 * @return
	 */
	public double getAmount() {
		return amount;
	}
	
	/**
	 * set ingredient Amount
	 * @param amount
	 */
	public void setAmount(double amount) {
		this.amount = amount;
	}
	
	/**
	 * Get ingredient Unit
	 * @return
	 */
	public String getUnit() {
		return unit;
	}
	
	/**
	 * Set ingredient Unit
	 * @param unit
	 */
	public void setUnit(String unit) {
		this.unit = unit;
	}
}
