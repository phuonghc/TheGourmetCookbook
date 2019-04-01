package application.controller;

import application.Main;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class LoginController implements EventHandler<ActionEvent> {

	public void handleLogin(ActionEvent event) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("../view/User.fxml"));
			Main.stage.setScene(new Scene(root, 700, 850));
			Main.stage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void handleHome(ActionEvent event) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("../view/Home.fxml"));
			Main.stage.setScene(new Scene(root, 700, 850));
			Main.stage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void handle(ActionEvent event) {
		// TODO Auto-generated method stub
		
	}

}
