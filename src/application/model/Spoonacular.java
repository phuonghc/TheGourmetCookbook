package application.model;
/**
 * The Spoonacular class makes all calls to the API
 * using Jackson object mapping to create objects from the
 * JSON nodes returned
 * 
 * @author Harrison Luko bqr789
 * UTSA CS 3443 - Team Project
 * Spring 2019
 */
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
	public static Menu menu;
	
	public static String recipeSearch;
	public static Recipe recipe;
	
	public static ArrayList<String> included = new ArrayList<String>();
	public static ArrayList<String> excluded = new ArrayList<String>();
	
	public static String[] id = new String[3];
	
	/**
	 * Autocomplete Ingredient search using the text field string provided
	 * to return a list of ingredients
	 * 
	 * @return
	 * @throws UnirestException
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	public static Ingredient[] loadIngredients() throws UnirestException, JsonParseException, JsonMappingException, IOException {
		
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		
		System.out.println(Spoonacular.ingredientSearch);
		
		HttpResponse<JsonNode> response = Unirest.get(Spoonacular.ingredientSearch)
			.header("X-RapidAPI-Key", "9e51cc9475msh268e855537a8f53p10c371jsn1f5fe43b5541")
			.asJson();
		
		String parse = response.getBody().toString(); 
		Ingredient[] ingredients = objectMapper.readValue(parse, Ingredient[].class);

		return ingredients;
	}
	
	/**
	 * Search Recipe Complex using the search pages inputs
	 * to return a Menu of menu items loaded into the menu page.
	 * @return
	 * @throws UnirestException
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	public static Menu loadMenu() throws UnirestException, JsonParseException, JsonMappingException, IOException {
		
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		
		System.out.println(Spoonacular.menuSearch);
	
		HttpResponse<JsonNode> response = Unirest.get(Spoonacular.menuSearch)
			.header("X-RapidAPI-Key", "9e51cc9475msh268e855537a8f53p10c371jsn1f5fe43b5541")
			.asJson();
		
		String parse = response.getBody().toString(); 
		Menu menu = objectMapper.readValue(parse, Menu.class);
		
		return menu;
	}
	
	/**
	 * Get Recipe Information to load the Recipe into the recipe page
	 * 
	 * @return
	 * @throws UnirestException
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	public static Recipe loadRecipe() throws UnirestException, JsonParseException, JsonMappingException, IOException {
		
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		
		System.out.println(Spoonacular.recipeSearch);
		
		HttpResponse<JsonNode> response = Unirest.get(Spoonacular.recipeSearch)
			.header("X-RapidAPI-Key", "9e51cc9475msh268e855537a8f53p10c371jsn1f5fe43b5541")
			.asJson();
		
		String parse = response.getBody().toString(); 
		Recipe recipe = objectMapper.readValue(parse, Recipe.class);

		return recipe;
	}
}