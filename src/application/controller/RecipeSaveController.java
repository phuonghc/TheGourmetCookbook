package application.controller;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ResourceBundle;

import com.mashape.unirest.http.exceptions.UnirestException;

import application.Main;
import application.model.Recipe;
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
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class RecipeSaveController implements EventHandler<ActionEvent>, Initializable {
	/**
	 * title - label that holds the tile of the scene
	 */
	@FXML
	private Label title;
	/**
	 * ingredientsTexts - text area that holds the
	 * recipes ingredients
	 */
	@FXML
	private TextArea ingredientsText;
	/**
	 * instructionsText - text area that holds the
	 * recipes instruction
	 */
	@FXML
	private TextArea instructionsText;
	/**
	 * buttonPrevious - takes user to the saved menu 
	 */
	@FXML
	private Button buttonPrevious;
	/**
	 * buttonLogout - takes user to the user menu
	 */
	@FXML
	private Button buttonLogout;
	/**
	 * txtRecipeIngredients - TextArea the holds recipes ingredients
	 */
	@FXML
	private TextArea txtRecipeIngredients;
	/**
	 * txtRecipeDirections - TextArea that holds recipes directions
	 */
	@FXML
	private TextArea txtRecipeDirections;
	/**
	 * imgRecipe - holds the recipe image
	 */
	@FXML
	private ImageView imgRecipe;
	/**
	 * labelRecipeName - holds the name of the recipe name 
	 */
	@FXML 
	private Label labelRecipeName;
	/**
	 * saveRecipe - button that calls the methods that saves the 
	 * recipes to the user saved recipe files
	 */
	@FXML
    private Button saveRecipe;
	
	/**
	 * initialize - initializes the gui components of the scene
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
	
		
		saveRecipe.setVisible(false);
		
		Recipe recipe = null;
		
		try {
	
			recipe = Spoonacular.loadRecipe();
	
			popLabelRecipeName( recipe); // WORKING
			System.out.println(recipe.getImage());
			popImg( recipe); // WORKING
	
			popTxtInstructions( recipe); // WORKING
		
			popTxtIngredients( recipe); // WORKING
		
			
		} catch (UnirestException | IOException e) {
			e.printStackTrace();
		}
		System.out.println("w");
	}

	/**
	 * handlePrevios - takes user to the previous menu scene. 
	 * @param event - ActionEvent 
	 */
	public void handlePrevious(ActionEvent event) {
		
		try {
			Parent root = FXMLLoader.load(getClass().getResource("../view/SavedRecipes.fxml"));
			Main.stage.setScene(new Scene(root, 800, 800));
			Main.stage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * handleLogout - takes user home or user scene
	 * @param event - ActionEvent
	 */
	public void handleLogout(ActionEvent event) {
		if(!User.isLoggedIn()) {
			try {
				Parent root = FXMLLoader.load(getClass().getResource("../view/Home.fxml"));
				Main.stage.setScene(new Scene(root, 800, 800));
				Main.stage.show();
			} catch(Exception e) {
				e.printStackTrace();
			}
		} else {
			try {
				Parent root = FXMLLoader.load(getClass().getResource("../view/User.fxml"));
				Main.stage.setScene(new Scene(root, 800, 800));
				Main.stage.show();
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * popImg - populates recipes images 
	 * @param recipe - Recipe obj
	 */
	public void popImg( Recipe recipe) { //Populate ImageView with Recipe img	
		InputStream inputStream = null;
		try {
			URL url = new URL(recipe.getImage());
			URLConnection urlConnection = url.openConnection();
			urlConnection.setRequestProperty("User-Agent", null);
			inputStream = urlConnection.getInputStream();
		} catch (IOException ioException) {
			ioException.printStackTrace();
		}
		Image image = new Image(inputStream);
		imgRecipe.setImage(image);	
	}
	/**
	 * popTxtIngredients - populates the ingredient textfields
	 * @param recipe - Recipe object
	 */
	public void popTxtIngredients( Recipe recipe) { //Populate TextArea with Ingredients required
		String recipeIngre = new String( "");
		for(int i = 0; i < recipe.getExtendedIngredients().size(); i++) {
			int j = i + 1;
			recipeIngre += "Ingredient " + j + ":\n";
			recipeIngre += recipe.getExtendedIngredients().get(i).getAmount() + " ";
			if( recipe.getExtendedIngredients().get(i).getUnit().matches( ""))
				recipeIngre += recipe.getExtendedIngredients().get(i).getUnit();
			else
				recipeIngre += recipe.getExtendedIngredients().get(i).getUnit() + " of ";
			recipeIngre += recipe.getExtendedIngredients().get(i).getName();
			recipeIngre += "\n";
		}
		txtRecipeIngredients.setText( recipeIngre);
		txtRecipeIngredients.setWrapText(true);
		txtRecipeIngredients.setEditable(false);
	}
	
	/**
	 * popTxtInstructions - populates the instruction text fields. 
	 * @param recipe - Recipe object 
	 */
	public void popTxtInstructions( Recipe recipe) { //Populate TextArea with Instructions required	
		String recipeInstuc = new String( "");
		for(int i = 0; i < recipe.getAnalyzedInstructions().size(); i++) {
			for(int j = 0; j < recipe.getAnalyzedInstructions().get(i).getSteps().size(); j++){
				int k = j + 1;
				recipeInstuc += "Step " + k + ":\n\t";
				recipeInstuc += recipe.getAnalyzedInstructions().get(i).getSteps().get(j).getStep();
				recipeInstuc += "\n";
			}
		}
		txtRecipeDirections.setText( recipeInstuc);
		txtRecipeDirections.setWrapText(true);
		txtRecipeDirections.setEditable(false);
	}
	
	/**
	 * popLabelRecipeName - populates the name of the recipe labels
	 * @param recipe - Recipe Object
	 */
	public void popLabelRecipeName( Recipe recipe) { //Populate Label with Recipe Name
		labelRecipeName.setText(recipe.getTitle());
	}
	
	/**
	 * handle - saves users recipes. 
	 */
	@Override
	public void handle(ActionEvent event) {
		User.getUserRecipes().add(Spoonacular.recipeSearch);
		User.saveRecipes();
	}
}