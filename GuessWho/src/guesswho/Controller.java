package guesswho;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import application.HostGamecodeScreenController;
import javafx.application.Application;

/**
 * Controller
 * Contains the main method to run the game and the thread to
 * communicate with the server. 
 * @author Hannah, Dani, Anna
 *
 */
public class Controller extends Application {
    
    //The deck of Guess Who that the players will use.
    //protected static Deck deck = HostGamecodeScreenController.chosenDeck;
    protected static Deck deck = new Deck();

    //The player using this computer.
    protected static Player player = new Player("Name", true);
    
    //The game of Guess Who.
    protected static Game game = new Game(deck, player);
    
    //The network instance
    public static Network network;
    
    //The gamestage
    public static Stage gameStage;
    
    //The previous stage
    public static Stage prevStage;
    
    //If cards have been added
    public static boolean cardsAdded = false;
    
    /**
     * Start: open the invite players stage to start the application. 
     */
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
    
    /**
     * get the game object
     * @return game
     */
    public static Game getGame() {
    	return game;
    }
    
    /**
     * set the previous stage
     * @param stage
     */
    public static void setPrevStage (Stage stage) {
        prevStage = stage;
    }
    
    /**
     * get the previous stage
     * @return prevStage
     */
    public static Stage getPrevStage() {
        return prevStage;
    }
    
    /**
     * set deck
     * @param deck
     */
    public static void setDeck(Deck d) {
    	deck = d;
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
    
    @SuppressWarnings("deprecation") //TODO
	public static void main(String[] args) {
    	Thread networkThread = new Thread("Network Thread") {
    	      public void run(){
    	    	  network = new Network();
    	          try {
    	  			while (true) {
    	  				//receive and process messages from the server
    	  				game.process(network.do_communication());
    	  				Thread.sleep(500);
    	  			}
    	  		} catch (InterruptedException e) {
    	  			Thread.currentThread().interrupt();
    	  		}
    	      }
    	   };
    	System.out.println("Starting Guess Who...");
    	networkThread.start();
        launch(args); //blocking
        //when application closes, stop network thread
        System.out.println("Closing Guess Who...");
        networkThread.stop();
    }
}
