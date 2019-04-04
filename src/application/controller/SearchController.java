package application.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.mashape.unirest.http.exceptions.UnirestException;

import application.Main;
import application.model.Ingredient;
import application.model.Spoonacular;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;


public class SearchController implements EventHandler<ActionEvent>, Initializable {

    @FXML
    private TextField categoryTextField;
    @FXML
    private ComboBox<String> categoryComboBox;
    @FXML
    private ComboBox<String> courseComboBox;
    @FXML
    private ComboBox<String> cuisineComboBox;
    @FXML 
    private TextField includeTextField;
    @FXML
    private TextField excludeTextField;
    @FXML
    private ComboBox<String> excludeComboBox;
    @FXML
    private ComboBox<String> includeComboBox;
    @FXML
    private TextField calorieMaxTextField;
    @FXML
    private TextField calorieMinTextField;
    @FXML
    private CheckBox veganCheckBox;
    @FXML
    private CheckBox vegetarianCheckBox;
    @FXML
    private Button searchButton;
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
	    
		courseComboBox.getItems().removeAll(courseComboBox.getItems());
	    courseComboBox.getItems().addAll( "main course", "side dish", "dessert", "appetizer", "salad", "bread", "breakfast", "soup", "beverage", "sauce", "drink");
	    
	    cuisineComboBox.getItems().removeAll(cuisineComboBox.getItems());
	    cuisineComboBox.getItems().addAll("african", "chinese", "japanese", "korean", "vietnamese", "thai", "indian", "british", "irish", "french", "italian", "mexican", "spanish", "middle eastern", "jewish", "american", "cajun", "southern", "greek", "german", "nordic", "eastern european", "caribbean", "latin american");
	}
	
	public void handleMenu(ActionEvent event) {

		String search = "https://spoonacular-recipe-food-nutrition-v1.p.rapidapi.com/recipes/searchComplex?";
		
		if(!categoryTextField.getText().isEmpty()) {
			search += "query=" + categoryTextField.getText();
		}
		
		if(Spoonacular.included.size()>0) {
			search += "&includeIngredients=";
			for(int i=0; i < Spoonacular.included.size(); i++) {
				String includes = Spoonacular.included.get(i);
				includes.replaceAll(" ", "+");
				search += includes + "%2c+";
			}
		}
		
		if(Spoonacular.excluded.size()>0) {
			search += "&excludeIngredients=";
			for(int i=0; i < Spoonacular.excluded.size(); i++) {
				String excludes = Spoonacular.excluded.get(i);
				excludes.replaceAll(" ", "+");
				search += excludes + "%2c+";
			}
		}
		
		if(courseComboBox.getValue()!=null) {
			String type = courseComboBox.getValue();
			type.replaceAll(" ", "+");
			search += "&type=" + type;
		}

		if(cuisineComboBox.getValue()!=null) {
			String cuisine = cuisineComboBox.getValue();
			cuisine.replaceAll(" ", "+");
			search += "&cuisine=" + cuisine;
		}
		
		if(!calorieMinTextField.getText().isEmpty()) {
			search += "&minCalories=" + calorieMinTextField.getText();
		}
		
		if(!calorieMaxTextField.getText().isEmpty()) {
			search += "&maxCalories=" + calorieMaxTextField.getText();
		}
		
		if(veganCheckBox.isSelected()) {
			search += "&diet=vegan";
		}
		
		if(vegetarianCheckBox.isSelected()) {
			search += "&diet=vegetarian";
		}
		
		search += "&offset=0&number=10";
		
		System.out.println(search);
		
		Spoonacular.menuSearch = search;
		
		try {
			Parent root = FXMLLoader.load(getClass().getResource("../view/Menu.fxml"));
			Main.stage.setScene(new Scene(root, 800, 800));
			Main.stage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void handleHome(ActionEvent event) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("../view/Home.fxml"));
			Main.stage.setScene(new Scene(root, 700, 850));
			Main.stage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void onCategoryEvent(KeyEvent event) throws JsonParseException, JsonMappingException, UnirestException, IOException {
		
		if(event.getCode() == KeyCode.ENTER) {
			
			String all = categoryTextField.getText();
			
			categoryComboBox.getItems().clear();
			
			Spoonacular.ingredientSearch = "https://spoonacular-recipe-food-nutrition-v1.p.rapidapi.com/recipes/autocomplete?number=10&query=" + all;
			
			Ingredient[] ingredientMatch = Spoonacular.loadIngredients();
			
			for(int i = 0; i< ingredientMatch.length; i++) {
				categoryComboBox.getItems().add(ingredientMatch[i].getTitle());
			}
		}
	}
	
	public void setCategory(ActionEvent event) {
		categoryComboBox.setValue(categoryComboBox.getValue());
	}
	
	public void onIncludeEvent(KeyEvent event)  throws UnirestException, JsonParseException, JsonMappingException, IOException {
		
		if(event.getCode() == KeyCode.ENTER) {
			
			String all = includeTextField.getText();
			
			includeComboBox.getItems().clear();
			
			Spoonacular.ingredientSearch = "https://spoonacular-recipe-food-nutrition-v1.p.rapidapi.com/food/ingredients/autocomplete?number=10&query=" + all;
			
			Ingredient[] ingredientMatch = Spoonacular.loadIngredients();
			
			for(int i = 0; i< ingredientMatch.length; i++) {
				includeComboBox.getItems().add(ingredientMatch[i].getName());
			}
		}
	}
	
	public void addIncluded(ActionEvent event) {
		if(includeComboBox.getValue()!=null) {
			Spoonacular.included.add(includeComboBox.getValue());
		}
		includeTextField.clear();
	}
	
	public void onExcludeEvent(KeyEvent event)  throws UnirestException, JsonParseException, JsonMappingException, IOException {
		
		if(event.getCode() == KeyCode.ENTER) {
			
			String all = excludeTextField.getText();
			
			excludeComboBox.getItems().clear();
			
			Spoonacular.ingredientSearch = "https://spoonacular-recipe-food-nutrition-v1.p.rapidapi.com/food/ingredients/autocomplete?number=10&query=" + all;
			
			Ingredient[] ingredientMatch = Spoonacular.loadIngredients();
			
			for(int i = 0; i< ingredientMatch.length; i++) {
				excludeComboBox.getItems().add(ingredientMatch[i].getName());
			}
		}
	}
	
	public void addExcluded(ActionEvent event) {
		if(excludeComboBox.getValue()!=null) {
			Spoonacular.excluded.add(excludeComboBox.getValue());
		}
		excludeTextField.clear();
	}

	@Override
	public void handle(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}