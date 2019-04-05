package application.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Font;


public class SearchController implements EventHandler<ActionEvent>, Initializable {

    @FXML
    private TextField categoryTextField;
    @FXML
    private Label searchLabel;
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
    @FXML
    private Button homeButton;
    @FXML 
    private ImageView backgroundPic;
//    @FXML 
//    private ImageView textBackgroundPic;
    
    String includeString = "";
    int numIncluded = 0;
	String excludeString = "";
	int numExcluded = 0;
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		searchLabel.setFont(Font.loadFont("file:./Fonts/KGDoYouLoveMe.ttf", 54));
		homeButton.setFont(Font.loadFont("file:./Fonts/KGDoYouLoveMe.ttf", 25));
		searchButton.setFont(Font.loadFont("file:./Fonts/KGDoYouLoveMe.ttf", 20));
//		categoryTextField.setFont(Font.loadFont("file:./Fonts/KGDoYouLoveMe.ttf", 15));
//		includeTextField.setFont(Font.loadFont("file:./Fonts/KGDoYouLoveMe.ttf", 15));
//		excludeTextField.setFont(Font.loadFont("file:./Fonts/KGDoYouLoveMe.ttf", 15));
//		calorieMinTextField.setFont(Font.loadFont("file:./Fonts/KGDoYouLoveMe.ttf", 15));
//		calorieMaxTextField.setFont(Font.loadFont("file:./Fonts/KGDoYouLoveMe.ttf", 15));
		
		try {
		InputStream stream = null;
		
		Image image;
		String background = "Flat-Pack-Kitchen-Ranges_main kitchen.jpg";
		stream = new FileInputStream("backgroundPics/" + background );
		image = new Image(stream);
		backgroundPic.setImage(image);
		
//		Image image2;
//		String textBackground = "background-kitchen.jpg";
//		stream = new FileInputStream("backgroundPics/" + textBackground);
//		image2 = new Image(stream);
//		textBackgroundPic.setImage(image2);
		
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		//Include options
		includeComboBox.getItems().removeAll(includeComboBox.getItems());
		includeComboBox.getItems().addAll( "Bacon", "Eggs", "Ham", "Omelete", "Mash Potatoes");
		
		//Exclude options
		excludeComboBox.getItems().removeAll(excludeComboBox.getItems());
		excludeComboBox.getItems().addAll( "Nuts", "Dairy", "Seeds", "Corn", "Soy");
				
		//Category options
		categoryComboBox.getItems().removeAll(categoryComboBox.getItems());
		categoryComboBox.getItems().addAll( " 1", " 2", " 3", " 4", " 5");
	    
	    //Courses options
		courseComboBox.getItems().removeAll(courseComboBox.getItems());
	    courseComboBox.getItems().addAll( "main course", "side dish", "dessert", "appetizer", "salad", "bread", "breakfast", "soup", "beverage", "sauce", "drink");
	    
	    //Cuisine options
	    cuisineComboBox.getItems().removeAll(cuisineComboBox.getItems());
	    cuisineComboBox.getItems().addAll("african", "chinese", "japanese", "korean", "vietnamese", "thai", "indian", "british", "irish", "french", "italian", "mexican", "spanish", "middle eastern", "jewish", "american", "cajun", "southern", "greek", "german", "nordic", "eastern european", "caribbean", "latin american");
	}
	
	/**
	 * This function takes information chosen by the user and makes a call to the API
	 * It then goes to the menu page
	 * @param event - User clicks on "Search!" at the bottom
	 */
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
			Main.stage.setScene(new Scene(root, 600, 850));
			Main.stage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * This function goes back to the home page
	 * @param event - When user clicks on "Home"
	 */
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
		//update the category combo box's text field to match choosen value
		categoryTextField.setText(categoryComboBox.getValue());
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
		//includeTextField.clear();
		numIncluded ++;
		
		if(numIncluded > 1)
			includeString += ", " + includeComboBox.getValue();
		else
			includeString += includeComboBox.getValue();		
		
		//update the include combo box's text field to match choosen value
		includeTextField.setText(includeString);
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
		//excludeTextField.clear();
		numExcluded ++;
		
		if(numExcluded > 1)
			excludeString += ", " + excludeComboBox.getValue();
		else
			excludeString += excludeComboBox.getValue();
		//update the include combo box's text field to match choosen value
		excludeTextField.setText(excludeString);
	}

	@Override
	public void handle(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}