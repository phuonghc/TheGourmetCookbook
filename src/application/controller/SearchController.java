package application.controller;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import java.util.ResourceBundle;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.mashape.unirest.http.exceptions.UnirestException;

import application.Main;
import application.model.Ingredient;
import application.model.Spoonacular;
import application.model.User;
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
    
    static String includeString = "";
    static String excludeString = "";
    
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
		
		//Exclude options
		excludeComboBox.getItems().removeAll(excludeComboBox.getItems());
				
		//Category options
		categoryComboBox.getItems().removeAll(categoryComboBox.getItems());
	    
	    //Courses options
		courseComboBox.getItems().removeAll(courseComboBox.getItems());
	    courseComboBox.getItems().addAll( "main course", "side dish", "dessert", "appetizer", "salad", "bread", "breakfast", "soup", "beverage", "sauce", "drink");
	    
	    //Cuisine options
	    cuisineComboBox.getItems().removeAll(cuisineComboBox.getItems());
	    cuisineComboBox.getItems().addAll("african", "chinese", "japanese", "korean", "vietnamese", "thai", "indian", "british", "irish", "french", "italian", "mexican", "spanish", "middle eastern", "jewish", "american", "cajun", "southern", "greek", "german", "nordic", "eastern european", "caribbean", "latin american");
	
	    //reset arraylists of included and excluded items, and clear their strings
	    resetValues();
	}
	
	/**
	 * This function takes information chosen by the user and makes a call to the API
	 * It then goes to the menu page
	 * @param event - User clicks on "Search!" at the bottom
	 */
	public void handleMenu(ActionEvent event) {

		String search = "https://spoonacular-recipe-food-nutrition-v1.p.rapidapi.com/recipes/searchComplex?";
		
		if(!categoryTextField.getText().isEmpty()) {
			String temp = categoryTextField.getText();
			temp = temp.replaceAll(" ", "+");
			search += "query=" + temp;
			
		}
		
		if(!includeString.isEmpty()) { 
			String temp = "";
			String tokens[] = includeTextField.getText().split(",");
			for (String value : tokens ) {
				//Add the word (without spaces) to the arraylist
				temp = value.replaceAll(" ", "+");
				Spoonacular.included.add(temp);
			}
			
			search += "&includeIngredients=";
			for(int i=0; i < Spoonacular.included.size(); i++) {
				String includes = Spoonacular.included.get(i);
				if (i == 0)
					search += includes + "%2c";
				else
					search += includes + "+%2c";
			}
			
		}
		
		if(!excludeString.isEmpty()) { 
			String temp2 = "";
			String tokens2[] = excludeTextField.getText().split(",");
			for (String value2 : tokens2 ) {
				//Add the word (without spaces) to the arraylist
				temp2 = value2.replaceAll(" ", "+");
				Spoonacular.included.add(temp2);
			}
			
			search += "&excludeIngredients=";
			for(int j=0; j < Spoonacular.excluded.size(); j++) {
				String excludes = Spoonacular.excluded.get(j);
				if (j == 0)
					search += excludes + "%2c";
				else
					search += excludes + "+%2c";
			}
			
		}
		
		if(courseComboBox.getValue()!=null) {
			String type = courseComboBox.getValue();
			type = type.replaceAll(" ", "+");
			System.out.println(type);
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

		resetValues();
	}
	
	public static void resetValues() {
		includeString = "";
		excludeString = "";
		Spoonacular.included.clear();
		Spoonacular.excluded.clear();
	}

	/**
	 * This function goes back to the home page
	 * @param event - When user clicks on "Home"
	 */
	public void handleHome(ActionEvent event) {
		if(!User.loggedIn) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("../view/Home.fxml"));
			Main.stage.setScene(new Scene(root, 800, 800));
			Main.stage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
		}else {
			try {
				Parent root = FXMLLoader.load(getClass().getResource("../view/User.fxml"));
				Main.stage.setScene(new Scene(root, 800, 800));
				Main.stage.show();
			} catch(Exception e) {
				e.printStackTrace();
			}
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
		//update the category combo box's text field to match chosen value
		categoryTextField.setText(categoryComboBox.getValue());
	}
	
	public void onIncludeEvent(KeyEvent event)  throws UnirestException, JsonParseException, JsonMappingException, IOException {
		
		if(event.getCode() == KeyCode.ENTER) {
			
			String all = includeTextField.getText();
			
			System.out.println("Inside onIncludeEvent Function");
			
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
			//update static String value
			includeString = includeTextField.getText();
			if(!includeString.isEmpty())
				includeString += ", " + includeComboBox.getValue();
			else
				includeString += includeComboBox.getValue();		
			
			//update the include combo box's text field to match chosen value
			includeTextField.setText(includeString);		
		
		}

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
			//update static String value
			excludeString = excludeTextField.getText();
			if(!excludeString.isEmpty())
				excludeString += ", " + excludeComboBox.getValue();
			else
				excludeString += excludeComboBox.getValue();		
			
			//update the include combo box's text field to match chosen value
			excludeTextField.setText(excludeString);		
		
		}		
	}

	@Override
	public void handle(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}