/**
 * LoginController - controller that controls the login scene
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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.text.Font;

public class LoginController implements Initializable {
	
		/**
		 * backHome - this button will send the user back to the home 
		 * scene.
		 */
		@FXML
		private Button backHome;
		/**
		 * loginTitle - this is a label that say login
		 */
		@FXML
	    private Label loginTitle;
		/**
		 * loginTitle - label that holds the title of the scene
		 */
	  	@FXML
	    private TextField userNameField;
	  	/**
	  	 * passwordField - field that holds the password of the
	  	 * user.
	  	 */
	    @FXML
	    private PasswordField passwordField;
	    /**
	     * createUser - calls all the methods used to create a user. 
	     */
	    @FXML
	    private Button createUser;
	    /*
	     * LoginButton - calls login methods and changed scenes. 
	     */
	    @FXML
	    private Button LoginButton;
	    /**
	     * error - label that holds the users error
	     */
	    @FXML
	    private Label error;

	/**
	 * handleLogin - this will call the login methods and switch 
	 * scene.
	 * @param event - ActionEvent
	 */
	public void handleLogin(ActionEvent event) {
		error.setText("");
		try {
			if(User.validateUser(userNameField.getText(), passwordField.getText())) {
				User.setUsername(userNameField.getText());
				User.setPassword(passwordField.getText());
				User.setLoggedIn(true);
				User.getUserRecipes().clear();
				User.loadUserSavedRecipes();
				if(!User.getTemp().isEmpty()) {
					User.getUserRecipes().add(User.temp);
					User.saveRecipes();
					User.setTemp("");
				}
				Parent root = FXMLLoader.load(getClass().getResource("../view/Home.fxml"));
				Main.stage.setScene(new Scene(root, 800, 800));
				Main.stage.show();
			}else {
//				error.setText("Username or Pasword is incorrect!");
//				error.setFont(Font.loadFont("file:./Fonts/KGDoYouLoveMe.ttf", 28));
//				error.setStyle("-fx-background-color: #ffffff");
				Alert alert = new Alert(AlertType.ERROR);
		        alert.setTitle("Alert");
		        alert.setHeaderText("Input Error!");
		        alert.setContentText("Username or Passwords don't match!!");
		        alert.showAndWait();
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * handleHome - this method will send the scene to the home scene
	 * @param event
	 */
	public void handleHome(ActionEvent event) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("../view/Home.fxml"));
			Main.stage.setScene(new Scene(root, 800, 800));
			Main.stage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * createUser - this method will take you to the create user scene. 
	 * @param event - ActionEvent
	 */
	@FXML
    void createUser(ActionEvent event) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("../view/CreateUser.fxml"));
			Main.stage.setScene(new Scene(root, 800, 800));
			Main.stage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
    }

	/**
	 * initialize - initializes the gui components in the scene. 
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		loginTitle.setFont(Font.loadFont("file:./Fonts/KGDoYouLoveMe.ttf", 75));
		backHome.setFont(Font.loadFont("file:./Fonts/KGDoYouLoveMe.ttf", 20));
		LoginButton.setFont(Font.loadFont("file:./Fonts/KGDoYouLoveMe.ttf", 15));
		createUser.setFont(Font.loadFont("file:./Fonts/KGDoYouLoveMe.ttf", 15));
		
		
	}

	

}
