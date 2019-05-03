package application.controller;

/**
 * This is profile controller class.
 * 
 * @author Sayontani Ray (pek684)
 * UTSA CS 3443 - Team Project
 * Spring 2019
 */
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.mashape.unirest.http.exceptions.UnirestException;

import application.Main;
import application.model.Ingredient;
import application.model.Spoonacular;
import application.model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public class ProfileController implements Initializable {

	// Class variables for the GUI components on the view
	@FXML
	private Label welcomeMessageLabel;
	@FXML
	private ListView<String> userIntolerancesListView;
	@FXML
	private TextField userIntoleranceTextField;
	@FXML
	private ComboBox<String> comboBox;
	
	private ObservableList<String> intoleranceItems = FXCollections.observableArrayList();

	/**
	 * This method initializes and displays information related to user intolerance.
	 * 
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		welcomeMessageLabel.setText("Welcome " + User.username);
		loadListBox();
	}
	
	/**
	 * This method initializes and loads list of user intolerances.
	 * 
	 */
	public void loadListBox() {
		try {
			User.userIntolerances.clear();
			User.loadUserIntolerances();
		} catch (IOException exception) {
			exception.printStackTrace();
		}
		
		ArrayList<String> arrIntolerances = new ArrayList<String>();
		for(String intolerance : User.userIntolerances) {
			arrIntolerances.add(intolerance);
		}
		
		intoleranceItems.clear();
		intoleranceItems.addAll(arrIntolerances);
		userIntolerancesListView.setItems(intoleranceItems);
	}
	
	/**
	 * This method creates and displays alert
	 * 
	 * @param alertType
	 * @param title
	 * @param headerText
	 * @param contentText
	 */
	private void createAndDisplayAlert(AlertType alertType, String title, String headerText, String contentText) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle(title);
		alert.setHeaderText(headerText);
		alert.setContentText(contentText);
		alert.showAndWait();
	}

	/**
	 * This method handles the event that occurs when an item
	 * from the combobox is chosen to add to the listview
	 * 
	 * @param event
	 * @throws IOException 
	 */
	@FXML
	public void handleAdd(ActionEvent event) throws IOException {		
		// Create and display alert if user intolerance already exists
		if(User.userIntolerances.contains(comboBox.getValue().trim())) {
			createAndDisplayAlert(AlertType.ERROR, "Alert", "Input Error!", "User intolerance already exists.");
			return;
		}

		//Add new User intolerance and save user preferences
		userIntolerancesListView.getItems().add(comboBox.getValue().trim());
		User.userIntolerances.add(comboBox.getValue().trim());
		try {
			User.saveUserIntolerances();
		} catch (IOException exception) {
			exception.printStackTrace();
		}
		userIntoleranceTextField.setText(null);
	}
	
	/**
	 * This method displays matching ingredients from the text field
	 * for the user to select
	 * @param event
	 * @throws UnirestException
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	public void handleSearch(KeyEvent event)  throws UnirestException, JsonParseException, JsonMappingException, IOException {
        
        if(event.getCode() == KeyCode.ENTER) {
            
        	String all = "";
            all = userIntoleranceTextField.getText();
            userIntoleranceTextField.setText("");
            comboBox.getItems().clear();
            comboBox.setPromptText("Click here");
            
            Spoonacular.ingredientSearch = "https://spoonacular-recipe-food-nutrition-v1.p.rapidapi.com/food/ingredients/autocomplete?number=10&query=" + all;
            
            Ingredient[] ingredientMatch = Spoonacular.loadIngredients();
            
            for(int i = 0; i< ingredientMatch.length; i++) {
                comboBox.getItems().add(ingredientMatch[i].getName());
            }
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
	
	/**
	 * This method handles the event that occurs when the item in list view is clicked.
	 * 
	 * @param event
	 * @throws IOException
	 */
	public void editListView(MouseEvent event) throws IOException {
		User.userIntolerances.remove(userIntolerancesListView.getSelectionModel().getSelectedIndex());
		User.saveUserIntolerances();
		loadListBox();
	}
}
