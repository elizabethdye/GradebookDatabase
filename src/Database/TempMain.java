package Database;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;


public class TempMain extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(TempMain.class.getResource("tempGUI.fxml"));
			AnchorPane root = (AnchorPane) loader.load();
			Scene scene = new Scene(root,640,500);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace(); 
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}