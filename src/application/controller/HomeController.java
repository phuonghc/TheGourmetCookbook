/**
 * HomeController - controller for the home scene 
 * Application Programming Spring 2019 
 * The Gourmet Cookbook 
 * @author Marco Zamora - bld783 
 */
package application.controller;

import java.io.File;
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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;

public class HomeController implements Initializable{
	
	/**
	 * MainTitleLabel - this is a label that holds 
	 * the tile of the scene.
	 */
	@FXML
    private Label MainTitleLabel;
	/**
	 * SearchButton - this is the button
	 * that takes you to the search scene. 
	 */
    @FXML
    private Button SearchButton;
    /**
     * changingButton - changes purpose depending on 
     * if the user has logged in successfully. 
     */
    @FXML
    private Button changingButton;
    /**
     *changingImage - changes images depending on 
     * if the user has logged in successfully.
     */
    @FXML
    private ImageView changingImage;
    /**
     * logoutButton - this button when pressed will logout the 
     * user, and take them to the default menu screen. 
     */
    @FXML
    private Button logoutButton;
    /**
     * profileImage - this image view will hold the profile picture of 
     * logged in user. 
     */
    @FXML
    private ImageView profileImage;
    /**
     * buttonProfile - this button will lead to a users profile. 
     */
    @FXML
    private Button buttonProfile;

    
    /**
     * handleLogin - this method will switch the scene
     * to the login scene. 
     * @param event - ActionEvent
     */
	@FXML
	public void handleLogin(ActionEvent event) {
		
		if(!User.isLoggedIn()) {
			try {
				Parent root = FXMLLoader.load(getClass().getResource("../view/Login.fxml"));
				Main.stage.setScene(new Scene(root, 800, 800));
				Main.stage.show();
			} catch(Exception e) {
				e.printStackTrace();
			}
		}else {
			try {
				User.viewSaved = true;
				Parent root = FXMLLoader.load(getClass().getResource("../view/Menu.fxml"));
				Main.stage.setScene(new Scene(root, 800, 800));
				Main.stage.show();
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * handleSearch - this method will switch to the 
	 * search scene. 
	 * @param event - ActionEvent
	 */
	@FXML
	public void handleSearch(ActionEvent event) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("../view/Search.fxml"));
			Main.stage.setScene(new Scene(root, 800, 800));
			Main.stage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	/**
	 * logout - logs the user out and switched to the home scene
	 * @param event - ActionEvent
	 */
	@FXML
    public void logout(ActionEvent event) {
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
	
	/**
	 * initialize - this method initializes all GUI components.
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		MainTitleLabel.setFont(Font.loadFont("file:./Fonts/KGDoYouLoveMe.ttf", 75));
		SearchButton.setFont(Font.loadFont("file:./Fonts/KGDoYouLoveMe.ttf", 23));
		if( User.isLoggedIn()) {
			changingButton.setText("Saved Recipes");
			File file = new File("./backgroundPics/iconfinder_book_285636.png");
			Image image = new Image(file.toURI().toString());
			changingImage.setImage(image);
			changingButton.setFont(Font.loadFont("file:./Fonts/KGDoYouLoveMe.ttf", 16));
		} else {
			logoutButton.setVisible(false);
			profileImage.setVisible(false);
			buttonProfile.setVisible(false);
			changingButton.setText("Login");		
			File file = new File("./backgroundPics/chef.png");
			Image image = new Image(file.toURI().toString());
			changingImage.setImage(image);
			changingButton.setFont(Font.loadFont("file:./Fonts/KGDoYouLoveMe.ttf", 18));
		}
	}
}
