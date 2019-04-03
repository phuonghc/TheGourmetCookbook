package application.model;

import java.io.IOException;
import java.util.ArrayList;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

public class Spoonacular {
	
	public static String ingredientSearch;
	
	public static String menuSearch;
	
	public static String recipeSearch;
	
	public static ArrayList<String> included = new ArrayList<String>();
	public static ArrayList<String> excluded = new ArrayList<String>();
	
	public static String[] id = new String[3];
	
	public static Ingredient[] loadIngredients() throws UnirestException, JsonParseException, JsonMappingException, IOException {
		
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		
		System.out.println(Spoonacular.ingredientSearch);
		
		HttpResponse<JsonNode> response = Unirest.get(Spoonacular.ingredientSearch)
			.header("X-RapidAPI-Key", "9e51cc9475msh268e855537a8f53p10c371jsn1f5fe43b5541")
			.asJson();
		
		String parse = response.getBody().toString(); 
		Ingredient[] ingredients = objectMapper.readValue(parse, Ingredient[].class);
		
		
		//for(int i = 0; i< ingredients.length;i++) {
		//	System.out.println(ingredients[i].getTitle());
		//}

		return ingredients;
	}
	
	public static Menu loadMenu() throws UnirestException, JsonParseException, JsonMappingException, IOException {
		
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		
		System.out.println(Spoonacular.menuSearch);
	
		HttpResponse<JsonNode> response = Unirest.get(Spoonacular.menuSearch)
			.header("X-RapidAPI-Key", "9e51cc9475msh268e855537a8f53p10c371jsn1f5fe43b5541")
			.asJson();
		
		String parse = response.getBody().toString(); 
		Menu menu = objectMapper.readValue(parse, Menu.class);
		
		//for(int i = 0; i < menu.getTotalResults(); i++) {
		//	System.out.println(menu.getResults().get(i).getImage());
		//	System.out.println(menu.getResults().get(i).getId());
		//	System.out.println(menu.getResults().get(i).getTitle());
		//}
		
		return menu;
	}
	
	public static Recipe loadRecipe() throws UnirestException, JsonParseException, JsonMappingException, IOException {
		
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		
		System.out.println(Spoonacular.recipeSearch);
		
		HttpResponse<JsonNode> response = Unirest.get(Spoonacular.recipeSearch)
			.header("X-RapidAPI-Key", "9e51cc9475msh268e855537a8f53p10c371jsn1f5fe43b5541")
			.asJson();
		
		String parse = response.getBody().toString(); 
		Recipe recipe = objectMapper.readValue(parse, Recipe.class);
		
		System.out.println(recipe.getTitle());
		
		//System.out.println(parse);
		
		//for(int i = 0; i <recipe.getExtendedIngredients().size(); i++) {
		//	System.out.println(recipe.getExtendedIngredients().get(i).getUnit());
		//}
		
		//for(int i = 0; i <recipe.getAnalyzedInstructions().size(); i++) {
		//	for(int j = 0; j < recipe.getAnalyzedInstructions().get(i).getSteps().size(); j++){
		//		System.out.println(recipe.getAnalyzedInstructions().get(i).getSteps().get(j).getStep());
				
		//	}
		//}	
		
		return recipe;
	}
}