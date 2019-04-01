package application.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.mashape.unirest.http.exceptions.UnirestException;

import application.Main;
import application.model.Ingredient;
import application.model.Recipe;
import application.model.Spoonacular;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		Recipe recipe = null;
		try {
			recipe = Spoonacular.loadRecipe();
		} catch (UnirestException | IOException e) {
			e.printStackTrace();
		}
		
		title.setText(recipe.getTitle());
	}

	public void handleMenu(ActionEvent event) {
		
		try {
			Parent root = FXMLLoader.load(getClass().getResource("../view/Menu.fxml"));
			Main.stage.setScene(new Scene(root, 700, 850));
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
	
	@Override
	public void handle(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}