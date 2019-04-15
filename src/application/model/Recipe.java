package application.model;

import java.util.ArrayList;

public class Recipe {
	
	private double servings;
	
	private String title;
	
	private String image;
	
	private String id;
	
	private ArrayList<Ingredient> extendedIngredients;
	
	private ArrayList<Instruction> analyzedInstructions;

	public ArrayList<Ingredient> getExtendedIngredients() {
		return extendedIngredients;
	}

	public String getImage() {
		return image;
	}

	public String getTitle() {
		return title;
	}

	public double getServings() {
		return servings;
	}

	public ArrayList<Instruction> getAnalyzedInstructions() {
		return analyzedInstructions;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
