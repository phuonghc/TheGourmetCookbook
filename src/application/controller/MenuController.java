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

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.mashape.unirest.http.exceptions.UnirestException;

import application.Main;
import application.model.MenuItem;
import application.model.Recipe;
import application.model.Spoonacular;
import application.model.User;
import javafx.event.ActionEvent;
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

/**
 * This is menu controller class.
 * 
 * @author Sayontani Ray (pek684)
 * UTSA CS 3443 - Team Project
 * Spring 2019
 */
public class MenuController implements Initializable {
	
	// Class variables for the GUI components on the view
	@FXML
	private GridPane resultPane;

	@FXML
	private Pagination resultPagination;

	private int itemsPerPage;
	
	private ToggleGroup toggleGroup;

	/**
	 * This method initializes and displays information related to menu
	 * 
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		if(User.viewSaved) {
			savedInit();
		} else {
			searchInit();
		}
	}

	/**
	 * This method initializes and displays information related to menu
	 * from User saved recipes
	 * 
	 */
	private void savedInit() {
		if (User.getUserRecipes() == null || User.getUserRecipes().size() == 0) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Alert");
	        alert.setHeaderText("No Item Warning!");
	        alert.setContentText("No saved recipe found for the user.");
	        alert.showAndWait();
	        return;
		}
		
		itemsPerPage = 5;
		toggleGroup = new ToggleGroup();
		Spoonacular.recipeSearch = null;
		resultPagination.setPageCount((int)Math.ceil((double)User.getUserRecipes().size()/ itemsPerPage));
		
		resultPagination.setPageFactory(new Callback<Integer, Node>() {
			 
		    @Override
		    public Node call(Integer pageIndex) {
		        if (pageIndex < User.getUserRecipes().size()) {
		            try {
						return createSavedMenu(pageIndex);
					} catch (IllegalAccessException | InstantiationException | InvocationTargetException| NoSuchMethodException exception) {
						exception.printStackTrace();
					} catch (JsonParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (JsonMappingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (UnirestException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		        }
		        return null;
		    }
		});
	}

	/**
	 * This method initializes and displays information related to menu
	 * from a custom search
	 * 
	 */
	private void searchInit() {
		// Populate the scene with the Menu
		try {
			Spoonacular.menu = Spoonacular.loadMenu();
			
			if (Spoonacular.menu == null || Spoonacular.menu.getResults() == null || Spoonacular.menu.getResults().size() == 0) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Alert");
		        alert.setHeaderText("No Item Warning!");
		        alert.setContentText("No menu found with given search criteria.");
		        alert.showAndWait();
		        return;
			}
			
			itemsPerPage = 5;
			toggleGroup = new ToggleGroup();
			Spoonacular.recipeSearch = null;
			resultPagination.setPageCount((int)Math.ceil((double)Spoonacular.menu.getResults().size() / itemsPerPage));
					
			// Set page factory to create pane with menu items for selected page index
			resultPagination.setPageFactory(new Callback<Integer, Node>() {
						 
				@Override
		        public Node call(Integer pageIndex) {
					if (pageIndex < Spoonacular.menu.getResults().size()) {
						try {
							return createSearchedMenu(pageIndex);
						} catch (IllegalAccessException | InstantiationException | InvocationTargetException| NoSuchMethodException exception) {
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

	/**
	 * This method creates and returns pane with menu items for selected page index.
	 * 
	 * @param pageIndex
	 * @return
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 */
	public GridPane createSearchedMenu(int pageIndex) throws IllegalAccessException, InstantiationException, InvocationTargetException, NoSuchMethodException {
		// Remove all the items from result pane
		resultPane = (GridPane) BeanUtils.cloneBean(resultPane);
		Set<Node> deleteNodes = new HashSet<>();
		for (Node child : resultPane.getChildren()) {
			deleteNodes.add(child);
		}
		resultPane.getChildren().removeAll(deleteNodes);
		
		// Update result pane with menu items for selected page index
		int page = pageIndex * itemsPerPage;
		int rowIndex = 0;
		
		for (int i = page; i < page + itemsPerPage && i < Spoonacular.menu.getResults().size(); i++) {
			MenuItem result = Spoonacular.menu.getResults().get(i);
			
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
	
	/**
	 * createMenu - creates the menu of all recipes 
	 * @param pageIndex - number of page indexes 
	 * @return GridPane
	 * @throws IllegalAccessException - throws this exception if an illegal action occurs
	 * @throws InstantiationException - throws this exception if instantiation happens. 
	 * @throws InvocationTargetException - throws this exception if invocation exception happens.
	 * @throws NoSuchMethodException - throws this exception if no such method exception happens.
	 * @throws JsonParseException - throws this exception if Json exception happens. 
	 * @throws JsonMappingException - throws this exception if Json mapping exception happens 
	 * @throws UnirestException - throws this exception if Unirest exception happens
	 * @throws IOException - throws this exception if IOException happens 
	 */
	public GridPane createSavedMenu(int pageIndex) throws IllegalAccessException, InstantiationException, InvocationTargetException, NoSuchMethodException, JsonParseException, JsonMappingException, UnirestException, IOException {
		resultPane = (GridPane) BeanUtils.cloneBean(resultPane);
		Set<Node> deleteNodes = new HashSet<>();
		for (Node child : resultPane.getChildren()) {
			deleteNodes.add(child);
		}
		resultPane.getChildren().removeAll(deleteNodes);
		
		int page = pageIndex * itemsPerPage;
		int rowIndex = 0;
		
		for (int i = page; i < page + itemsPerPage && i < User.getUserRecipes().size(); i++) {
			Spoonacular.recipeSearch = User.getUserRecipes().get(i);
			Recipe recipe = Spoonacular.loadRecipe();
			
			RadioButton radioButton = new RadioButton();
			radioButton.getStyleClass().add("menu-radio-button");
			radioButton.setToggleGroup(toggleGroup);
			radioButton.setUserData(recipe.getId());
			resultPane.add(radioButton, 0, rowIndex);

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
			ImageView imageView = new ImageView();
			imageView.setFitWidth(75);
			imageView.setFitHeight(75);
			imageView.setPreserveRatio(true);
			imageView.setImage(image);
			resultPane.add(imageView, 1, rowIndex);

			Label label = new Label();
			label.getStyleClass().add("menu-label");
			label.setText(recipe.getTitle());
			resultPane.add(label, 2, rowIndex);

			rowIndex++;
		}
		return resultPane;
	}
	
	/**
	 * This method handles the event that occurs when the Recipe button is clicked.
	 * 
	 * @param event
	 */
	@FXML
	public void handleRecipe(ActionEvent event) {
		// Create and display alert if no item is selected
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
			Main.stage.setScene(new Scene(root, 800, 800));
			Main.stage.show();
		} catch(Exception exception) {
			exception.printStackTrace();
		}
	}

	/**
	 * This method handles the event that occurs when the Search button is clicked.
	 * 
	 * @param event
	 */
	@FXML
	public void handleSearch(ActionEvent event) {

		try {
			Parent root = FXMLLoader.load(getClass().getResource("../view/Search.fxml"));
			Main.stage.setScene(new Scene(root, 800, 800));
			Main.stage.show();
		} catch(Exception exception) {
			exception.printStackTrace();
		}
	}
	
	/**
	 * This method handles the event that occurs when the Recipe button is clicked.
	 * 
	 * @param event
	 */
	@FXML
    void handleHome(ActionEvent event) {
		
		try {
			Parent root = FXMLLoader.load(getClass().getResource("../view/Home.fxml"));
			Main.stage.setScene(new Scene(root, 800, 800));
			Main.stage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
    }

}