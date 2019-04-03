package application.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.mashape.unirest.http.exceptions.UnirestException;

import application.Main;
import application.model.Menu;
import application.model.MenuItem;
import application.model.Spoonacular;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

public class MenuController implements EventHandler<ActionEvent>, Initializable {
	
	@FXML
	private GridPane resultPane;

	@FXML
	private Pagination resultsPagination;

	private Menu menu;

	private int itemsPerPage;
	
	private ToggleGroup toggleGroup;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		//Populate the scene with the Menu
		try {
			menu = Spoonacular.loadMenu();
			itemsPerPage = 3;
			toggleGroup = new ToggleGroup();
		} catch (UnirestException | IOException exception) {
			exception.printStackTrace();
		}

		resultsPagination.setPageCount((int)Math.ceil((double)menu.getResults().size() / itemsPerPage));
		resultsPagination.setPageFactory((Integer pageIndex) -> createMenu(pageIndex));
	}

	public GridPane createMenu(int pageIndex) {
		resultPane = new GridPane();
		int page = pageIndex * itemsPerPage;
		int rowIndex = 0;
		
		for (int i = page; i < page + itemsPerPage && i < menu.getResults().size(); i++) {
			MenuItem result = menu.getResults().get(i);
			
			RadioButton radioButton = new RadioButton();
			radioButton.setToggleGroup(toggleGroup);
			radioButton.setUserData(result.getId());
			resultPane.add(radioButton, 0, rowIndex);

			Image image = new Image(result.getImage());
			ImageView imageView = new ImageView();
			imageView.setFitWidth(50);
			imageView.setFitHeight(50);
			imageView.setPreserveRatio(true);
			imageView.setImage(image);
			resultPane.add(imageView, 1, rowIndex);

			Label label = new Label();
			label.setText(result.getTitle());
			resultPane.add(label, 2, rowIndex);

			rowIndex++;
		}
		return resultPane;
	}
	
	@FXML
	public void handleRecipe(ActionEvent event) {
		System.out.println("toggleGroup.getSelectedToggle().getUserData(): " + toggleGroup.getSelectedToggle().getUserData());
		Spoonacular.recipeSearch = "https://spoonacular-recipe-food-nutrition-v1.p.rapidapi.com/recipes/" + toggleGroup.getSelectedToggle().getUserData() + "/information";

		try {
			Parent root = FXMLLoader.load(getClass().getResource("../view/Recipe.fxml"));
			Main.stage.setScene(new Scene(root, 700, 850));
			Main.stage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	public void handleSearch(ActionEvent event) {

		try {
			Parent root = FXMLLoader.load(getClass().getResource("../view/Search.fxml"));
			Main.stage.setScene(new Scene(root, 700, 850));
			Main.stage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void handle(ActionEvent event) {
		// TODO Auto-generated method stub
		
	}

}