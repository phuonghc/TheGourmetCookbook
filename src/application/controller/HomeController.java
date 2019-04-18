package application.controller;

import java.net.URL;
import java.util.ResourceBundle;

import application.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
     * LogingButton - this button takes the user
     * to the login scene. 
     */
    @FXML
    private Button LoginButton;

    
    /**
     * handleLogin - this method will switch the scene
     * to the login scene. 
     * @param event - ActionEvent
     */
	@FXML
	void handleLogin(ActionEvent event) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("../view/Login.fxml"));
			Main.stage.setScene(new Scene(root, 800, 800));
			Main.stage.show();
		} catch(Exception e) {
			e.printStackTrace();
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
	 * initialize - this method initializes all gui components.
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		MainTitleLabel.setFont(Font.loadFont("file:./Fonts/KGDoYouLoveMe.ttf", 75));
		SearchButton.setFont(Font.loadFont("file:./Fonts/KGDoYouLoveMe.ttf", 23));
		LoginButton.setFont(Font.loadFont("file:./Fonts/KGDoYouLoveMe.ttf", 18));
	}
}
