package application.controller;

/**
 * 
 * The search page allows the user to customize
 * a meal seach with varying text fields check boxes
 * and comboboxes before concatenating a string
 * 
 * Phuong Huynh
 * UTSA CS 3443 - Team Project
 * Spring 2019
 */

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
import javafx.application.Platform;
import javafx.concurrent.Task;
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
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
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
    private Button searchButton;
    @FXML
    private Button homeButton;
    @FXML 
    private ImageView backgroundPic;
    @FXML
    private ImageView loadingImage;
    private @FXML Button includeClearButton;
    private @FXML Button excludeClearButton;
    private @FXML TextArea includeTA;
    private @FXML TextArea excludeTA;
    @FXML private ToggleGroup toggleDiets;
    @FXML private RadioButton radioVegan;
    @FXML private RadioButton radioVegetarian;
    @FXML private RadioButton radioPaleo;
    @FXML private RadioButton radioKeto;
    @FXML private RadioButton radioPrimal;

    
    /**
     * this thread begins to load the result from the api call
     * and upon completion takes the user to the Menu page.
     */
    static String includeString = "";
    static String excludeString = "";
    
    Thread th = new Thread( new Task(){
		@Override
		protected String call() throws Exception {

		 	Spoonacular.menu = Spoonacular.loadMenu();
		 	
			Platform.runLater(new Runnable() {
				 @Override
				 public void run() {
					 try {
				            Parent root = FXMLLoader.load(getClass().getResource("../view/Menu.fxml"));
				            Main.stage.setScene(new Scene(root, 800, 800));
				            Main.stage.show();
				     } catch(Exception e) {
				            e.printStackTrace();
				     }
				 }
			});
		 	
			return null;
		}
	}); 
      
    /**
     * Initialize the search page to include prepopulated comboboxes
     * with user options for courses and cuisines.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
        searchLabel.setFont(Font.loadFont("file:./Fonts/KGDoYouLoveMe.ttf", 54));
        homeButton.setFont(Font.loadFont("file:./Fonts/KGDoYouLoveMe.ttf", 25));
        searchButton.setFont(Font.loadFont("file:./Fonts/KGDoYouLoveMe.ttf", 20));
        
        //load background image
        try {
	        InputStream stream = null;
	        Image image;
	        String background = "Flat-Pack-Kitchen-Ranges_main kitchen.jpg";
	        stream = new FileInputStream("backgroundPics/" + background );
	        image = new Image(stream);
	        backgroundPic.setImage(image);
        } 
        catch (FileNotFoundException e) {
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
        courseComboBox.getItems().addAll( "", "Main Course", "Side Dish", "Dessert", "Appetizer", "Salad", "Bread", "Breakfast", "Soup", "Beverage", "Sauce", "Drink");
        
        //Cuisine options
        cuisineComboBox.getItems().removeAll(cuisineComboBox.getItems());
        cuisineComboBox.getItems().addAll("", "African", "Chinese", "Japanese", "Korean", "Vietnamese", "Thai", "Indian", "British", "Irish", "French", "Italian", "Mexican", "Spanish", "Middle Eastern", "Jewish", "American", "Cajun", "Southern", "Greek", "German", "Nordic", "Eastern European", "Caribbean", "Latin American");
    
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
            String tokens[] = includeString.split(",");
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
            String temp = "";
            String tokens2[] = excludeString.split(",");
            for (String value : tokens2 ) {
                //Add the word (without spaces) to the arraylist
                temp = value.replaceAll(" ", "+");
                Spoonacular.excluded.add(temp);
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
        
        if(User.isLoggedIn()) {
        	if(User.userIntolerances.size()>0) {
        		search += "&intolerances=";
        		for(int i = 0; i< User.userIntolerances.size(); i++) {
        			String temp = "";
        			temp = User.userIntolerances.get(i).replaceAll(" ", "+");
        			if (i == 0)
                        search += temp + "%2c";
                    else
                        search += temp + "+%2c";
        		}
        	}
        }
        
        if(courseComboBox.getValue()!=null) {
            String type = courseComboBox.getValue();
            type = type.replaceAll(" ", "+");
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

        RadioButton selectedRadioButton = (RadioButton) toggleDiets.getSelectedToggle();
        if( selectedRadioButton != null) {
        	if( selectedRadioButton.equals(radioVegan) ) search += "&diet=vegan";
        	else if( selectedRadioButton.equals(radioVegetarian) ) search += "&diet=vegetarian";
        	else if( selectedRadioButton.equals(radioPaleo) ) search += "&diet=paleo";
        	else if( selectedRadioButton.equals(radioKeto) ) search += "&diet=keto";
        	else if( selectedRadioButton.equals(radioPrimal) ) search += "&diet=primal";
        }
        
        search += "&offset=0&number=10";        
        Spoonacular.menuSearch = search;
        
        User.viewSaved = false;
        resetValues();
        
        //start thread to load Menu and change to MenuController when done
        new Thread(th).start();
    	
        //Show the loadingImage while thread does work
        //*********commented out GIF
    	loadingImage.setVisible(true);
    }
    
    /**
     * resets the user selected items
     */
    public static void resetValues() {
        includeString = "";
        excludeString = "";
        Spoonacular.included.clear();
        Spoonacular.excluded.clear();
    }
    
    /**
     * resets the user selected included items
     */
    public void clearStringInclude(ActionEvent event) {
        includeString = "";
        Spoonacular.included.clear();
        includeTA.setText("");
    }
    
    /**
     * resets the user selected excluded items
     */
    public void clearStringExclude(ActionEvent event) {
        excludeString = "";
        Spoonacular.excluded.clear();
        excludeTA.setText("");
    }
    
    /**
     * This method uses the text field to search
     * for similar worded food groups.
     * @param event
     * @throws JsonParseException
     * @throws JsonMappingException
     * @throws UnirestException
     * @throws IOException
     */
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
    
    /**
     * sets the combobox with foods returned from API
     * @param event
     */
    public void setCategory(ActionEvent event) {
        categoryComboBox.setValue(categoryComboBox.getValue());
        //update the category combo box's text field to match chosen value
        categoryTextField.setText(categoryComboBox.getValue());
    }
    
    /**
     * This method uses the text field to search
     * for similar worded ingredients.
     * @param event
     * @throws UnirestException
     * @throws JsonParseException
     * @throws JsonMappingException
     * @throws IOException
     */
    public void onIncludeEvent(KeyEvent event)  throws UnirestException, JsonParseException, JsonMappingException, IOException {
        
        if(event.getCode() == KeyCode.ENTER) {
            
        	String all = "";
            all = includeTextField.getText();
            includeTextField.setText("");
            includeComboBox.getItems().clear();
            includeComboBox.setPromptText("Click here");
            
            Spoonacular.ingredientSearch = "https://spoonacular-recipe-food-nutrition-v1.p.rapidapi.com/food/ingredients/autocomplete?number=10&query=" + all;
            
            Ingredient[] ingredientMatch = Spoonacular.loadIngredients();
            
            for(int i = 0; i< ingredientMatch.length; i++) {
                includeComboBox.getItems().add(ingredientMatch[i].getName());
            }
            includeComboBox.show();
        }
    }
    
    /**
     * Adds the selected item from the combobox for
     * ingredients to include in search
     * @param event
     */
    public void addIncluded(ActionEvent event) {
        if(includeComboBox.getValue()!=null) {
        	String temp = includeComboBox.getValue();
            if(!includeString.isEmpty())
                includeString += ", " + temp;
            else
                includeString += temp;      
            includeTA.setText(includeString); 
            includeComboBox.show();
        }
    }
    
    /**
     * This method uses the text field to search
     * for similar worded ingredients. 
     * @param event
     * @throws UnirestException
     * @throws JsonParseException
     * @throws JsonMappingException
     * @throws IOException
     */
    public void onExcludeEvent(KeyEvent event)  throws UnirestException, JsonParseException, JsonMappingException, IOException {
        
        if(event.getCode() == KeyCode.ENTER) {
            
        	String all = "";
            all = excludeTextField.getText();
            excludeTextField.setText("");
            excludeComboBox.getItems().clear();
            excludeComboBox.setPromptText("Click here");
            
            Spoonacular.ingredientSearch = "https://spoonacular-recipe-food-nutrition-v1.p.rapidapi.com/food/ingredients/autocomplete?number=10&query=" + all;
            
            Ingredient[] ingredientMatch = Spoonacular.loadIngredients();
            
            for(int i = 0; i< ingredientMatch.length; i++) {
                excludeComboBox.getItems().add(ingredientMatch[i].getName());
            }

        }
    }
    
    /**
     * Adds the selected item from the combobox for
     * ingredients to include in search
     * @param event
     */
    public void addExcluded(ActionEvent event) {
    	if(excludeComboBox.getValue()!=null) {
            //update static String value
            String temp = excludeComboBox.getValue();
            if(!excludeString.isEmpty())
                excludeString += ", " + temp;
            else
                excludeString += temp;        
            excludeTA.setText(excludeString);      
        }        
    }
    
    /**
     * returns to the home page
     */
    @Override
    public void handle(ActionEvent arg0) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../view/Home.fxml"));
            Main.stage.setScene(new Scene(root, 800, 800));
            Main.stage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
        
    }
}
