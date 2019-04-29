package application.model;

/**
 * The steps are returned in the Get Recipe information
 * API call
 * @author Harrison Luko bqr789
 * 
 * UTSA CS 3443 - Team Project
 * Spring 2019
 */
import java.util.ArrayList;

public class Step {
	
	private String step;
	
	private ArrayList<Ingredient> ingredients;
	
	private ArrayList<Equipment> equipment;

	/**
	 * get the step in the recipe
	 * @return
	 */
	public String getStep() {
		return step;
	}
	
	/**
	 * set the recipe step
	 * @param step
	 */
	public void setStep(String step) {
		this.step = step;
	}

	/**
	 * get the recipe step ingredients
	 * @return
	 */
	public ArrayList<Ingredient> getIngredients() {
		return ingredients;
	}
	
	/**
	 * set the recipe set ingredients
	 * @param ingredients
	 */
	public void setIngredients(ArrayList<Ingredient> ingredients) {
		this.ingredients = ingredients;
	}

	/**
	 * get the recipe step equipments
	 * @return
	 */
	public ArrayList<Equipment> getEquipment() {
		return equipment;
	}
	
	/**
	 * set the recipe step equipment
	 * @param equipment
	 */
	public void setEquipment(ArrayList<Equipment> equipment) {
		this.equipment = equipment;
	}
}
