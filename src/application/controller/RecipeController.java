package application.controller;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.mashape.unirest.http.exceptions.UnirestException;
import com.sun.javafx.scene.control.skin.VirtualFlow;

import application.Main;
import application.model.Recipe;
import application.model.Spoonacular;
import application.model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Cell;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;

public class RecipeController implements EventHandler<ActionEvent>, Initializable {
	
	@FXML
	private Label title;
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
	@FXML 
	private Label labelServes;
	@FXML
    private Button saveRecipe;
	@FXML
    private ListView<String> listRecipeIngredients;
	@FXML
	private Slider sliderServings;
	
	private ObservableList<String> ingredientItems = FXCollections.observableArrayList();
	private double initialSliderValue = 0;
	private Recipe currentRecipe = null;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		Recipe recipe = null;
		
		try {
			recipe = Spoonacular.loadRecipe();
			currentRecipe = recipe;
	
			popLabelRecipeName( recipe);
			popImg( recipe);
			popTxtInstructions( recipe);
			popListIngredients( recipe);
			setSlider(recipe);
			initialSliderValue = sliderServings.getValue();
			
		} catch (UnirestException | IOException e) {
			e.printStackTrace();
		}
		if(User.isLoggedIn()) {
			for( String s : User.userRecipes) {
				if( s.contentEquals(Spoonacular.recipeSearch)){
					saveRecipe.disableProperty();
				}
			}	
		} else {
			
		}
	}
			

	/**
	 * Changes the view back to the previous view without having to go through search again when an event is detected
	 * @param event
	 */
	public void handlePrevious(ActionEvent event) {
		
		try {
			Parent root = FXMLLoader.load(getClass().getResource("../view/Menu.fxml"));
			Main.stage.setScene(new Scene(root, 800, 800));
			Main.stage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * Changes the view depending on User status, whether they are logged in, or not, when an event is detected
	 * @param event
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
	 * This method acquires the new value of the Slider once the User has released the mouse, it then calls other methods
	 * to use the information
	 */
	public void handleServingSlider() { //Note: Sliders are inherently double values, an (int) cast is required
		double newSliderValue = sliderServings.getValue();
		double oldSliderValue = initialSliderValue;
	    setServeLabel( (int) newSliderValue);
	    ratioChangeOfIngredients( oldSliderValue, newSliderValue);
	}
	/**
	 * This method sets the initial value of the slider according to the recipe serving size
	 * @param recipe
	 */
	public void setSlider( Recipe recipe) { 
		int initialValue = (int) recipe.getServings();
		sliderServings.adjustValue(initialValue);
		setServeLabel(initialValue);
	}
	/**
	 * This method updates the labelServes to the current amount of people the recipe can serve
	 * @param serves
	 */
	public void setServeLabel( int serves) { 
		String s = "Currently Serves: " + serves;
		labelServes.setText(s);
	}
	/**
	 * This method receives two double values and calculates whether the value represent an increase or decrease
	 * and calls updateList to handle accordingly
	 * @param oldServing
	 * @param newServing
	 */
	public void ratioChangeOfIngredients( double oldServing, double newServing) { 
		double calculatedServing = 0;
		if( oldServing > newServing) {
			calculatedServing = oldServing - newServing;
			double ratioOfDecrease = calculatedServing / oldServing;
			ratioOfDecrease = 1.0 - ratioOfDecrease;
			updateList( ratioOfDecrease);
		} else if( oldServing < newServing) {
			calculatedServing = newServing - oldServing;
			double ratioOfIncrease = calculatedServing / oldServing;
			ratioOfIncrease += 1;
			updateList( ratioOfIncrease);
		} else updateList( 1.0);
	}
	/**
	 * This method updates the ListView with the new ingredient amounts
	 * @param ratio
	 */
	public void updateList( double ratio) { 
		Recipe recipe = currentRecipe;
		String recipeIngre = new String( "");
		ArrayList<String> arrIngredients = new ArrayList<String>();
	
		for(int i = 0; i < recipe.getExtendedIngredients().size(); i++) {
			recipeIngre += (recipe.getExtendedIngredients().get(i).getAmount() * ratio) + " ";
			if( recipe.getExtendedIngredients().get(i).getUnit().matches( ""))
				recipeIngre += recipe.getExtendedIngredients().get(i).getUnit();
			else
				recipeIngre += recipe.getExtendedIngredients().get(i).getUnit() + " of ";
			recipeIngre += recipe.getExtendedIngredients().get(i).getName();
			arrIngredients.add(i, recipeIngre);
			recipeIngre = "";
		}
		ingredientItems.clear();
		ingredientItems.addAll(arrIngredients);
		listRecipeIngredients.setItems(ingredientItems);
	}

	/**
	 * This method populates the ImageView with the corresponding image
	 * @param recipe
	 */
	public void popImg( Recipe recipe) { 
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
	 * This method populates ListView with Instructions required	
	 * @param recipe
	 */
	public void popListIngredients( Recipe recipe) { 
		String recipeIngre = new String( "");
		ArrayList<String> arrIngredients = new ArrayList<String>();

		for(int i = 0; i < recipe.getExtendedIngredients().size(); i++) {
			recipeIngre += recipe.getExtendedIngredients().get(i).getAmount() + " ";
			if( recipe.getExtendedIngredients().get(i).getUnit().matches( ""))
				recipeIngre += recipe.getExtendedIngredients().get(i).getUnit();
			else
				recipeIngre += recipe.getExtendedIngredients().get(i).getUnit() + " of ";
			recipeIngre += recipe.getExtendedIngredients().get(i).getName();
			arrIngredients.add(i, recipeIngre);
			recipeIngre = "";
		}
		ingredientItems.addAll(arrIngredients);
		listRecipeIngredients.setItems(ingredientItems);
		
		/* This will hopefully let me edit the font size within the list view
		if( !listRecipeIngredients.getItems().isEmpty())
        {
            VirtualFlow<?> ch=(VirtualFlow<?>) listRecipeIngredients.getChildrenUnmodifiable();
            Font anyfont=new Font("Tahoma",16);
            for (int i = 0; i < ch.getCellCount(); i++)
            {
                Cell<String> cell= ch.getCell(i);
                cell.setFont(anyfont);
            }
        }
        */
	}
	/**
	 * This method populates TextArea with Instructions required	
	 * @param recipe
	 */
	public void popTxtInstructions( Recipe recipe) {
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
	 * This method populates Label with Recipe Name
	 * @param recipe
	 */
	public void popLabelRecipeName( Recipe recipe) { 
		labelRecipeName.setText(recipe.getTitle());
	}
	@Override
	public void handle(ActionEvent event) {
		if(User.loggedIn) {
			if(!User.checkIfRecipeExist(Spoonacular.recipeSearch)) {
				User.getUserRecipes().add(Spoonacular.recipeSearch);
				User.saveRecipes();
				
				Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Saved");
                alert.setHeaderText("This recipe has been added to your \"Saved Recipes\"!");
                alert.setContentText("Click OK to continue");
                alert.showAndWait();

				
			}else {
				Alert alert = new Alert(AlertType.ERROR);
	    		alert.setTitle("Alert");
		        alert.setHeaderText("Input Error!");
		        alert.setContentText("Username has already saved this recipe!!");
		        alert.showAndWait();
			}
		}else {
			User.setTemp(Spoonacular.recipeSearch);
			try {
				Parent root = FXMLLoader.load(getClass().getResource("../view/Login.fxml"));
				Main.stage.setScene(new Scene(root, 800, 800));
				Main.stage.show();
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
}