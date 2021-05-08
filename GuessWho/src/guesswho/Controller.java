package guesswho;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.application.Application;


public class Controller extends Application {
    
    /**
     * The deck of Guess Who that the players will use.
     */
    protected static Deck deck = new Deck();

    /**
     * The player using this computer.
     */
    protected static Player player = new Player("Name", true);
    
    /**
     * The game of Guess Who.
     */
    protected static Game game = new Game(deck, player);
    
    public static Network network;
    
    public static Stage gameStage;
    
    public static Scene gameScene;
    
    public static Stage prevStage;
    
    public static boolean cardsAdded = false;
    
    @Override
    public void start(Stage primaryStage) {
        try {
            // load in the page here.
            Parent root = FXMLLoader.load(getClass().getResource("/application/InvitePlayers.fxml"));
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/application/application.css").toExternalForm());
            dragScreen(scene, primaryStage);   
            
            // Sets properties for the window and displays it
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            primaryStage.setTitle("Guess Who");
            primaryStage.getIcons().add(new Image("application/icon.png"));
            primaryStage.initStyle(StageStyle.UNDECORATED);
            primaryStage.show();
            
        } catch(Exception e) {
            e.printStackTrace();
        }
        
    }
    
    public static Game getGame() {
    	return game;
    }
    
    public static void setPrevStage (Stage stage) {
        prevStage = stage;
    }
    
    public static Stage getPrevStage() {
        return prevStage;
    }
    
    /**
     * Allows the screen to be dragged.
     * Subtracts where the mouse is when you click on the stage
     * from where it is after you start dragging the mouse and sets 
     * the screen position to that to mimic dragging the screen.
     * @param scene The scene to be dragged
     * @param stage The stage that the scene is in
     */
    public static void dragScreen(Scene scene, Stage stage) {
        scene.setOnMousePressed(pressEvent -> {
            scene.setOnMouseDragged(dragEvent -> {
                //dragEvent.getScreenX/Y gets the position of mouse when dragging
                //pressEvent.getScreenX/Y gets the position of mouse when clicking
                stage.setX(dragEvent.getScreenX() - pressEvent.getSceneX());
                stage.setY(dragEvent.getScreenY() - pressEvent.getSceneY());
            });
        });  
    }
    
    @SuppressWarnings("deprecation")
	public static void main(String[] args) {
    	Thread networkThread = new Thread("Network Thread") {
    	      public void run(){
    	    	  network = new Network();
    	          try {
    	  			while (true) {
    	  				game.process(network.do_communication());
    	  				Thread.sleep(500);
    	  			}
    	  		} catch (InterruptedException e) {
    	  			Thread.currentThread().interrupt();
          		System.out.println("Thread was interrupted, Failed to complete operation");
    	  		}
    	      }
    	      
    	   };
    	   
    	networkThread.start();
    	
        launch(args);
       
        networkThread.stop();
    }
}
