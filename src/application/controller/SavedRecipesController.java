/**
 * SavedRecipesController - controller for the saved recipe scene
 * Application Programming Spring 2019 
 * The Gourmet Cookbook 
 * @author Marco Zamora - bld783 
 */
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
import application.model.Recipe;
import application.model.Spoonacular;
import application.model.User;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;

public class SavedRecipesController implements Initializable, EventHandler<ActionEvent> {
	
	/**
	 * resultPane - GripPane that holds the recipes
	 * results
	 */
	@FXML
	private GridPane resultPane;
	/**
	 * resultPagination - Pagination of all 
	 * pages of recipe results
	 */
	@FXML
	private Pagination resultPagination;
	/**
	 * homeButton - button that take a user home 
	 */
	@FXML
    private Button homeButton;

	/**
	 * itemsPerPage - count of the number of pages 
	 */
	private int itemsPerPage;
	
	/**
	 * toggleGroup - button toggle group 
	 */
	private ToggleGroup toggleGroup;
	

	/**
	 * initialize - this initializes all gui components of the scene
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {

		itemsPerPage = 5;
		toggleGroup = new ToggleGroup();
		Spoonacular.recipeSearch = null;
		resultPagination.setPageCount((int)Math.ceil((double)User.getUserRecipes().size()/ itemsPerPage));
		resultPagination.setPageFactory(new Callback<Integer, Node>() {
			 
		    @Override
		    public Node call(Integer pageIndex) {
		        if (pageIndex < User.getUserRecipes().size()) {
		            try {
						try {
							return createMenu(pageIndex);
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
					} catch (IllegalAccessException | InstantiationException | InvocationTargetException
							| NoSuchMethodException exception) {
						exception.printStackTrace();
					}
		        }
		        return null;
		    }
		});
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
	public GridPane createMenu(int pageIndex) throws IllegalAccessException, InstantiationException, InvocationTargetException, NoSuchMethodException, JsonParseException, JsonMappingException, UnirestException, IOException {
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
	 * handleRecipe - uses picked recipe to help load recipe scene. 
	 * @param event - ActionEvent
	 */
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
			Parent root = FXMLLoader.load(getClass().getResource("../view/RecipeSaved.fxml"));
			Main.stage.setScene(new Scene(root, 800, 800));
			Main.stage.show();
		} catch(Exception exception) {
			exception.printStackTrace();
		}
	}

	/**
	 * handle - this method takes user to user scene. 
	 */
	@Override
	public void handle(ActionEvent event) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("../view/User.fxml"));
			Main.stage.setScene(new Scene(root, 800, 800));
			Main.stage.show();
		} catch(Exception exception) {
			exception.printStackTrace();
		}
		
	}
}
