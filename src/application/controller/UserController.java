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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Font;

public class UserController implements Initializable{
	
	@FXML
    private Label MainTitleLabel;

    @FXML
    private Button SearchButton;

    @FXML
    private Button savedRecipesButton;
    
    @FXML
    private Button logoutButton;

    
    @FXML
    void savedRecipes(ActionEvent event) {
    	try {
			Parent root = FXMLLoader.load(getClass().getResource("../view/Home.fxml"));
			Main.stage.setScene(new Scene(root, 800, 800));
			Main.stage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
    }
    
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

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		MainTitleLabel.setFont(Font.loadFont("file:./Fonts/KGDoYouLoveMe.ttf", 75));
		SearchButton.setFont(Font.loadFont("file:./Fonts/KGDoYouLoveMe.ttf", 23));
		savedRecipesButton.setFont(Font.loadFont("file:./Fonts/KGDoYouLoveMe.ttf", 16));
	}
	
	@FXML
    void logout(ActionEvent event) {
		User.setLoggedIn(false);
		try {
			Parent root = FXMLLoader.load(getClass().getResource("../view/Home.fxml"));
			Main.stage.setScene(new Scene(root, 800, 800));
			Main.stage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
    }

}
