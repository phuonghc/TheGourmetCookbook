package application.controller;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;

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
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;

public class MenuController implements EventHandler<ActionEvent>, Initializable {
	
	@FXML
	private GridPane resultPane;

	@FXML
	private Pagination resultPagination;

	private Menu menu;

	private int itemsPerPage;
	
	private ToggleGroup toggleGroup;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		//Populate the scene with the Menu
		try {
			menu = Spoonacular.loadMenu();
			itemsPerPage = 5;
			toggleGroup = new ToggleGroup();
			Spoonacular.recipeSearch = null;
			resultPagination.setPageCount((int)Math.ceil((double)menu.getResults().size() / itemsPerPage));
			resultPagination.setPageFactory(new Callback<Integer, Node>() {
				 
	            @Override
	            public Node call(Integer pageIndex) {
	                if (pageIndex < menu.getResults().size()) {
	                    try {
							return createMenu(pageIndex);
						} catch (IllegalAccessException | InstantiationException | InvocationTargetException
								| NoSuchMethodException exception) {
							exception.printStackTrace();
						}
	                }
	                return null;
	            }
	        });
		} catch (UnirestException | IOException ioException) {
			ioException.printStackTrace();
		}
	}

	public GridPane createMenu(int pageIndex) throws IllegalAccessException, InstantiationException, InvocationTargetException, NoSuchMethodException {
		resultPane = (GridPane) BeanUtils.cloneBean(resultPane);
		Set<Node> deleteNodes = new HashSet<>();
		for (Node child : resultPane.getChildren()) {
			deleteNodes.add(child);
		}
		resultPane.getChildren().removeAll(deleteNodes);
		
		int page = pageIndex * itemsPerPage;
		int rowIndex = 0;
		
		for (int i = page; i < page + itemsPerPage && i < menu.getResults().size(); i++) {
			MenuItem result = menu.getResults().get(i);
			
			RadioButton radioButton = new RadioButton();
			radioButton.getStyleClass().add("menu-radio-button");
			radioButton.setToggleGroup(toggleGroup);
			radioButton.setUserData(result.getId());
			resultPane.add(radioButton, 0, rowIndex);

			InputStream inputStream = null;
			try {
				URL url = new URL(result.getImage());
				URLConnection urlConnection = url.openConnection();
				urlConnection.setRequestProperty("User-Agent", null);
				inputStream = urlConnection.getInputStream();
			} catch (IOException ioException) {
				ioException.printStackTrace();
			}
			Image image = new Image(inputStream);
			ImageView imageView = new ImageView();
			imageView.setFitWidth(75);
			imageView.setFitHeight(75);
			imageView.setPreserveRatio(true);
			imageView.setImage(image);
			resultPane.add(imageView, 1, rowIndex);

			Label label = new Label();
			label.getStyleClass().add("menu-label");
			label.setText(result.getTitle());
			resultPane.add(label, 2, rowIndex);

			rowIndex++;
		}
		return resultPane;
	}
	
	@FXML
	public void handleRecipe(ActionEvent event) {
		if(toggleGroup.getSelectedToggle() == null) {
			Alert alert = new Alert(AlertType.ERROR);
	        alert.setTitle("Alert");
	        alert.setHeaderText("Input Error!");
	        alert.setContentText("Please select an item.");
	        alert.showAndWait();
			return;
		}
		Spoonacular.recipeSearch = "https://spoonacular-recipe-food-nutrition-v1.p.rapidapi.com/recipes/" + toggleGroup.getSelectedToggle().getUserData() + "/information";

		try {
			Parent root = FXMLLoader.load(getClass().getResource("../view/Recipe.fxml"));
			Main.stage.setScene(new Scene(root, 700, 850));
			Main.stage.show();
		} catch(Exception exception) {
			exception.printStackTrace();
		}
	}

	@FXML
	public void handleSearch(ActionEvent event) {

		try {
			Parent root = FXMLLoader.load(getClass().getResource("../view/Search.fxml"));
			Main.stage.setScene(new Scene(root, 700, 850));
			Main.stage.show();
		} catch(Exception exception) {
			exception.printStackTrace();
		}
	}

	@Override
	public void handle(ActionEvent event) {
		// TODO Auto-generated method stub
		
	}

}