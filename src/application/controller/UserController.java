/**
 * UserController - controller for the 
 * user scene. 
 * Application Programming Spring 2019 
 * The Gourmet Cookbook 
 * @author Marco Zamora - bld783 
 */
package application.controller;

import java.net.URL;
import java.util.ResourceBundle;

import application.Main;
import application.model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.text.Font;

public class UserController implements Initializable{
	
	/**
	 * MainTitleLabel - holds the title of the scene
	 */
	@FXML
    private Label MainTitleLabel;
	/**
	 * SearchButton - switches the scene to search scene
	 */
    @FXML
    private Button SearchButton;
    /**
     * savedRecipesButton - switched the scene to saved 
     * recipes scene
     */
    @FXML
    private Button savedRecipesButton;
    /**
     * logoutButton - switched the scene to the logged out
     * scene
     */
    @FXML
    private Button logoutButton;
    /**
     * error - label that show error
     */
    @FXML
    private Label error;

    /**
     * savedRecipes - switches the scene to saved recipe scene
     * @param event - ActionEvent
     */
    @FXML
    void savedRecipes(ActionEvent event) {
    	
    	if(User.getUserRecipes().size() < 1) {	
    		Alert alert = new Alert(AlertType.ERROR);
    		alert.setTitle("Alert");
	        alert.setHeaderText("Input Error!");
	        alert.setContentText("Username has no saved recipes!!");
	        alert.showAndWait();
    	}else {
    	try {
    		User.viewSaved=true;
			Parent root = FXMLLoader.load(getClass().getResource("../view/Recipe.fxml"));
			Main.stage.setScene(new Scene(root, 800, 800));
			Main.stage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
      }
    }
    
    /**
     * handleSearch - changes scene to search scene
     * @param event - ActionEvent
     */
    @FXML
    void handleSearch(ActionEvent event) {
    	try {
			Parent root = FXMLLoader.load(getClass().getResource("../view/Search.fxml"));
			Main.stage.setScene(new Scene(root, 800, 800));
			Main.stage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
    }

    /**
     * initialize - initializes the gui components in the scene
     */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		MainTitleLabel.setFont(Font.loadFont("file:./Fonts/KGDoYouLoveMe.ttf", 75));
		SearchButton.setFont(Font.loadFont("file:./Fonts/KGDoYouLoveMe.ttf", 23));
		savedRecipesButton.setFont(Font.loadFont("file:./Fonts/KGDoYouLoveMe.ttf", 16));
	}
	
	/**
	 * logout - logs the user out and switched to the home scene
	 * @param event - ActionEvent
	 */
	@FXML
    void logout(ActionEvent event) {
		User.logout();
		try {
			Parent root = FXMLLoader.load(getClass().getResource("../view/Home.fxml"));
			Main.stage.setScene(new Scene(root, 800, 800));
			Main.stage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
    }
	
	/**
	 * This method handles the event that occurs when the Profile button is clicked.
	 * 
	 * @param event
	 */
	@FXML
	public void handleProfile(ActionEvent event) {

		try {
			Parent root = FXMLLoader.load(getClass().getResource("../view/Profile.fxml"));
			Main.stage.setScene(new Scene(root, 800, 800));
			Main.stage.show();
		} catch(Exception exception) {
			exception.printStackTrace();
		}
	}

}
