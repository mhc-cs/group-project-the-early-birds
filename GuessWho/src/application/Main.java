package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
		    // load in the page here. Right now I'm hardcoding it to the page I'm working on, but I'll
		    // have to un-hardcode it later. 
		    Parent root = FXMLLoader.load(getClass().getResource("gameplayScreen.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
			// Sets properties for the window and displays it
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.setTitle("Guess Who");
			primaryStage.getIcons().add(new Image("application/icon.png"));
			primaryStage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
