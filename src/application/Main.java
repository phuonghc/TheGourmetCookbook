package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class Main extends Application {
	/**
	 * stage - Stage object
	 */
	public static Stage stage;
	
	/**
	 * start - method that starts the app
	 */
	public void start(Stage primaryStage) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("view/Home.fxml"));
			primaryStage.setScene(new Scene(root, 800, 800));
			primaryStage.show();
			
			stage = primaryStage;
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * main - runs the app
	 * @param args
	 */
	public static void main(String[] args){
		launch(args);
	}
}
