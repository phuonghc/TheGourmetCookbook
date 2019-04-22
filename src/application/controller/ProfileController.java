package application.controller;

import java.io.IOException;
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
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

public class ProfileController implements Initializable {

	// Class variables for the GUI components on the view
	@FXML
	private Label welcomeMessageLabel;
	@FXML
	private ListView<String> userIntolerancesListView;
	@FXML
	private TextField userIntoleranceTextField;

	/**
	 * This method initializes and displays information related to user intolerance.
	 * 
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		welcomeMessageLabel.setText("Welcome " + User.username);
		try {
			User.userIntolerances.clear();
			User.loadUserIntolerances();
		} catch (IOException exception) {
			exception.printStackTrace();
		}
		userIntolerancesListView.getItems().clear();
		userIntolerancesListView.getItems().addAll(User.userIntolerances);
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
	 * This method handles the event that occurs when the Add button is clicked.
	 * 
	 * @param event
	 */
	@FXML
	public void handleAdd(ActionEvent event) {
		// Create and display alert if no user intolerance provided
		if(userIntoleranceTextField.getText() == null || userIntoleranceTextField.getText().trim().length() == 0) {
			createAndDisplayAlert(AlertType.ERROR, "Alert", "Input Error!", "Please provide an user intolerance to add.");
			return;
		}
		
		// Create and display alert if user intolerance already exists
		if(User.userIntolerances.contains(userIntoleranceTextField.getText().trim())) {
			createAndDisplayAlert(AlertType.ERROR, "Alert", "Input Error!", "User intolerance already exists.");
			return;
		}

		userIntolerancesListView.getItems().add(userIntoleranceTextField.getText().trim());
		User.userIntolerances.add(userIntoleranceTextField.getText().trim());
		try {
			User.saveUserIntolerances();
		} catch (IOException exception) {
			exception.printStackTrace();
		}
		userIntoleranceTextField.setText(null);
	}

	/**
	 * This method handles the event that occurs when the Recipe button is clicked.
	 * 
	 * @param event
	 */
	@FXML
	void handleHome(ActionEvent event) {

		if(!User.isLoggedIn()) {
			try {
				Parent root = FXMLLoader.load(getClass().getResource("../view/Home.fxml"));
				Main.stage.setScene(new Scene(root, 800, 800));
				Main.stage.show();
			} catch(Exception e) {
				e.printStackTrace();
			}
		} else {
			try {
				Parent root = FXMLLoader.load(getClass().getResource("../view/Home.fxml"));
				Main.stage.setScene(new Scene(root, 800, 800));
				Main.stage.show();
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
	}

}
