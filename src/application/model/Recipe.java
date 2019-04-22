package application.model;

import java.util.ArrayList;

public class Recipe {
	
	private double servings;
	
	private String title;
	
	private String image;
	
	private String id;
	
	private ArrayList<Ingredient> extendedIngredients;
	
	private ArrayList<Instruction> analyzedInstructions;

	/**
	 * @return extendedIngredients the Recipe Ingredients
	 */
	public ArrayList<Ingredient> getExtendedIngredients() {
		return extendedIngredients;
	}

	/**
	 * @return image the Recipe image
	 */
	public String getImage() {
		return image;
	}

	/**
	 * @return title the Recipe Title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @return servings the Recipe servings
	 */
	public double getServings() {
		return servings;
	}

	/**
	 * @return analyzedInstructions the Recipe Instructions
	 */
	public ArrayList<Instruction> getAnalyzedInstructions() {
		return analyzedInstructions;
	}

	/**
	 * @return id the Recipe id
	 */
	public String getId() {
		return id;
	}

	/**
	 * Sets the current recipe id to ensure a rapid call to the API should the user so choose
	 * @param id
	 */
	public void setId(String id) {
		this.id = id;
	}
}
