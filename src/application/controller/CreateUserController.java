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

public class CreateUserController implements Initializable{
	
	@FXML
    private PasswordField password;

    @FXML
    private PasswordField confirmPassword;

    @FXML
    private Button createUser;

    @FXML
    private Label error;

    @FXML
    private TextField username;
    
    @FXML
    private Label title;
    
    @FXML
    private Label userLabel;

    @FXML
    private Label confirmLabel;
    
    @FXML
    private Label passwordLabel;

    @FXML
    private Button backHomeButton;
    
    @FXML
    void createUser(ActionEvent event) {
    	int result = User.createNewAccount(username.getText(),password.getText(),confirmPassword.getText());
    	
    		if(result == 3) {
    			error.setText("Passwords fields don't match!");
    			error.setFont(Font.loadFont("file:./Fonts/KGDoYouLoveMe.ttf", 26));
    			error.setStyle("-fx-background-color: #ffffff");
    		}else if(result == 2) {
    			error.setText("User already exist!");
    			error.setFont(Font.loadFont("file:./Fonts/KGDoYouLoveMe.ttf", 40));
    			error.setStyle("-fx-background-color: #ffffff");
    		}else if(result == 1){
    			try {
    				Parent root = FXMLLoader.load(getClass().getResource("../view/User.fxml"));
    				Main.stage.setScene(new Scene(root, 800, 800));
    				Main.stage.show();
    			} catch(Exception e) {
    				e.printStackTrace();
    			}
    		}else {
    			error.setText("Couldn't make user!");
    			error.setFont(Font.loadFont("file:./Fonts/KGDoYouLoveMe.ttf", 40));
    			error.setStyle("-fx-background-color: #ffffff");
    		}
    }
    
    @FXML
    public void backHome(ActionEvent event) {
    	try {
			Parent root = FXMLLoader.load(getClass().getResource("../view/Home.fxml"));
			Main.stage.setScene(new Scene(root, 800, 800));
			Main.stage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
    }
    

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		username.setFont(Font.loadFont("file:./Fonts/KGDoYouLoveMe.ttf", 20));
		password.setFont(Font.loadFont("file:./Fonts/KGDoYouLoveMe.ttf", 20));
		confirmPassword.setFont(Font.loadFont("file:./Fonts/KGDoYouLoveMe.ttf", 20));
		createUser.setFont(Font.loadFont("file:./Fonts/KGDoYouLoveMe.ttf", 20));
		title.setFont(Font.loadFont("file:./Fonts/KGDoYouLoveMe.ttf", 60));
		userLabel.setFont(Font.loadFont("file:./Fonts/KGDoYouLoveMe.ttf", 20));
		confirmLabel.setFont(Font.loadFont("file:./Fonts/KGDoYouLoveMe.ttf", 20));
		passwordLabel.setFont(Font.loadFont("file:./Fonts/KGDoYouLoveMe.ttf", 20));
		backHomeButton.setFont(Font.loadFont("file:./Fonts/KGDoYouLoveMe.ttf", 20));
	}

}
