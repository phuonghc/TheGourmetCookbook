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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;

public class LoginController implements Initializable {
	
		@FXML
		private Button backHome;
	 
		@FXML
	    private Label loginTitle;
	
	  	@FXML
	    private TextField userNameField;

	    @FXML
	    private PasswordField passwordField;

	    @FXML
	    private Button createUser;

	    @FXML
	    private Button LoginButton;
	    
	    @FXML
	    private Label error;


	public void handleLogin(ActionEvent event) {
		error.setText("");
		try {
			if(User.validateUser(userNameField.getText(), passwordField.getText())) {
				
				User.setUsername(userNameField.getText());
				User.setPassword(passwordField.getText());
				User.setLoggedIn(true);
				Parent root = FXMLLoader.load(getClass().getResource("../view/User.fxml"));
				Main.stage.setScene(new Scene(root, 800, 800));
				Main.stage.show();
			}else {
				error.setText("Username or Pasword is incorrect!");
				error.setFont(Font.loadFont("file:./Fonts/KGDoYouLoveMe.ttf", 28));
				error.setStyle("-fx-background-color: #ffffff");
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void handleHome(ActionEvent event) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("../view/Home.fxml"));
			Main.stage.setScene(new Scene(root, 800, 800));
			Main.stage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
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


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		loginTitle.setFont(Font.loadFont("file:./Fonts/KGDoYouLoveMe.ttf", 75));
		backHome.setFont(Font.loadFont("file:./Fonts/KGDoYouLoveMe.ttf", 20));
		LoginButton.setFont(Font.loadFont("file:./Fonts/KGDoYouLoveMe.ttf", 15));
		createUser.setFont(Font.loadFont("file:./Fonts/KGDoYouLoveMe.ttf", 15));
		userNameField.setFont(Font.loadFont("file:./Fonts/KGDoYouLoveMe.ttf", 15));
		passwordField.setFont(Font.loadFont("file:./Fonts/KGDoYouLoveMe.ttf", 15));
	}

	

}
