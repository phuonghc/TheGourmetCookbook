package application.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.mashape.unirest.http.exceptions.UnirestException;

import application.Main;
import application.model.Menu;
import application.model.Spoonacular;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class MenuController implements EventHandler<ActionEvent>, Initializable {

	@FXML
	private ImageView image1;
	@FXML
	private ImageView image2;
	@FXML
	private ImageView image3;;
	@FXML
	private CheckBox checkBox1;
	@FXML
	private CheckBox checkBox2;
	@FXML
	private CheckBox checkBox3;
	@FXML
	private Label pageNumber;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		try {
			createMenu();
		} catch (UnirestException | IOException e) {
			e.printStackTrace();
		}
	}

	public void handleRecipe(ActionEvent event) {
		
		if(checkBox1.isSelected()) {
			Spoonacular.recipeSearch = "https://spoonacular-recipe-food-nutrition-v1.p.rapidapi.com/recipes/" + Spoonacular.id[0] + "/information";
		}
		
		if(checkBox2.isSelected()) {
			Spoonacular.recipeSearch = "https://spoonacular-recipe-food-nutrition-v1.p.rapidapi.com/recipes/" + Spoonacular.id[1] + "/information";
		}
		
		if(checkBox3.isSelected()) {
			Spoonacular.recipeSearch = "https://spoonacular-recipe-food-nutrition-v1.p.rapidapi.com/recipes/" + Spoonacular.id[2] + "/information";
		}
		
		try {
			Parent root = FXMLLoader.load(getClass().getResource("../view/Recipe.fxml"));
			Main.stage.setScene(new Scene(root, 700, 850));
			Main.stage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void handleSearch(ActionEvent event) {
		
		try {
			Parent root = FXMLLoader.load(getClass().getResource("../view/Search.fxml"));
			Main.stage.setScene(new Scene(root, 700, 850));
			Main.stage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private void createMenu() throws UnirestException, JsonParseException, JsonMappingException, IOException {
		Menu menu = Spoonacular.loadMenu();
		int i = Integer.parseInt(pageNumber.getText());
		
		checkBox1.setText(menu.getResults().get(((i-1)*3)).getTitle());
		Image loadImage1 = new Image(menu.getResults().get(((i-1)*3)).getImage());
		image1.setImage(loadImage1);
		Spoonacular.id[0] = menu.getResults().get(((i-1)*3)).getId();
		
		checkBox2.setText(menu.getResults().get(((i-1)*3)+1).getTitle());
		Image loadImage2 = new Image(menu.getResults().get(((i-1)*3)).getImage());
		image2.setImage(loadImage2);
		Spoonacular.id[1] = menu.getResults().get(((i-1)*3)+1).getId();
		
		checkBox3.setText(menu.getResults().get(((i-1)*3)+2).getTitle());
		Image loadImage3 = new Image(menu.getResults().get(((i-1)*3)).getImage());
		image1.setImage(loadImage3);
		Spoonacular.id[2] = menu.getResults().get(((i-1)*3)+2).getId();
	}
	
    @FXML
    void nextMenuItems(ActionEvent event) throws JsonParseException, JsonMappingException, UnirestException, IOException {
    	int i = Integer.parseInt(pageNumber.getText());
    	pageNumber.setText(Integer.toString(i+1));
    	createMenu();
    }

    @FXML
    void previousMenuItems(ActionEvent event) throws JsonParseException, JsonMappingException, UnirestException, IOException {
    	int i = Integer.parseInt(pageNumber.getText());
    	pageNumber.setText(Integer.toString(i-1));
    	createMenu();
    }

	@Override
	public void handle(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}