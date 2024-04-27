package application;
	
import java.sql.Connection;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;



public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			//BorderPane root = new BorderPane();
			Parent root =  FXMLLoader.load(getClass().getResource("MainScene.fxml"));
			Scene scene = new Scene(root,722,396);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setTitle("MySql Connection");
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
		Connection con = DBConnection.getConnection();
		if (con == null) {
			System.out.println("Connection Failed");
		}
		else
		{
			System.out.println("Connection Success");

		}
		
	}
}
