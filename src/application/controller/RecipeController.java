package application.controller;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.mashape.unirest.http.exceptions.UnirestException;

import application.Main;
import application.model.Ingredient;
import application.model.Instruction;
import application.model.Recipe;
import application.model.Spoonacular;
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

public class RecipeController implements EventHandler<ActionEvent>, Initializable {
	
	@FXML
	private Label title;
	@FXML
	private TextArea ingredientsText;
	@FXML
	private TextArea instructionsText;
	@FXML
	private Button buttonPrevious;
	@FXML
	private Button buttonLogout;
	@FXML
	private TextArea txtRecipeIngredients;
	@FXML
	private TextArea txtRecipeDirections;
	@FXML
	private ImageView imgRecipe;
	@FXML 
	private Label labelRecipeName;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		Recipe recipe = null;
		try {
			recipe = Spoonacular.loadRecipe();
			popLabelRecipeName( recipe); // WORKING
			popImg( recipe); // WORKING
			popTxtInstructions( recipe); // WORKING
			popTxtIngredients( recipe); // WORKING
			
		} catch (UnirestException | IOException e) {
			e.printStackTrace();
		}
		
	}

	public void handlePrevious(ActionEvent event) {
		
		try {
			Parent root = FXMLLoader.load(getClass().getResource("../view/Menu.fxml"));
			Main.stage.setScene(new Scene(root, 600, 850));
			Main.stage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	public void handleLogout(ActionEvent event) {
			
			try {
				Parent root = FXMLLoader.load(getClass().getResource("../view/Home.fxml"));
				Main.stage.setScene(new Scene(root, 700, 850));
				Main.stage.show();
			} catch(Exception e) {
				e.printStackTrace();
			}
	}
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
	public void popTxtIngredients( Recipe recipe) { //Populate TextArea with Ingredients required
		String recipeIngre = new String( "");
		for(int i = 0; i < recipe.getExtendedIngredients().size(); i++) {
			int j = i + 1;
			recipeIngre += "Ingredient " + j + ":\n";
			recipeIngre += recipe.getExtendedIngredients().get(i).getAmount() + " ";
			if( recipe.getExtendedIngredients().get(i).getUnit().matches( " "))
				recipeIngre += recipe.getExtendedIngredients().get(i).getUnit();
			else
				recipeIngre += recipe.getExtendedIngredients().get(i).getUnit() + " of ";
			recipeIngre += recipe.getExtendedIngredients().get(i).getName();
			recipeIngre += "\n";
		}
		txtRecipeIngredients.setText( recipeIngre);
		txtRecipeIngredients.setEditable(false);
	}
	public void popTxtInstructions( Recipe recipe) { //Populate TextArea with Instructions required	
		String recipeInstuc = new String( "");
		for(int i = 0; i < recipe.getAnalyzedInstructions().size(); i++) {
			for(int j = 0; j < recipe.getAnalyzedInstructions().get(i).getSteps().size(); j++){
				recipeInstuc += "Step " + j + ":\n";
				recipeInstuc += recipe.getAnalyzedInstructions().get(i).getSteps().get(j).getStep();
				recipeInstuc += "\n";
			}
		}
		txtRecipeDirections.setText( recipeInstuc);
		txtRecipeDirections.setEditable(false);
	}
	public void popLabelRecipeName( Recipe recipe) { //Populate Label with Recipe Name
		labelRecipeName.setText(recipe.getTitle());
	}

	@Override
	public void handle(ActionEvent event) {
		// TODO Auto-generated method stub
		
	}
}