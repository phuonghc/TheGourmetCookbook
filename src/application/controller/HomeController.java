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
	
	@FXML
    private Label MainTitleLabel;

    @FXML
    private Button SearchButton;

    @FXML
    private Button LoginButton;


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

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		MainTitleLabel.setFont(Font.loadFont("file:./Fonts/KGDoYouLoveMe.ttf", 75));
		SearchButton.setFont(Font.loadFont("file:./Fonts/KGDoYouLoveMe.ttf", 23));
		LoginButton.setFont(Font.loadFont("file:./Fonts/KGDoYouLoveMe.ttf", 18));
	}
}
