package application.model;

import java.util.ArrayList;

public class Step {
	
	private String step;
	
	private ArrayList<Ingredient> ingredients;
	
	private ArrayList<Equipment> equipment;

	public String getStep() {
		return step;
	}

	public ArrayList<Ingredient> getIngredients() {
		return ingredients;
	}

	public ArrayList<Equipment> getEquipment() {
		return equipment;
	}
}
