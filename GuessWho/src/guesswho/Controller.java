package guesswho;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
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
    
    @Override
    public void start(Stage primaryStage) {
        try {
            // load in the page here.
            Parent root = FXMLLoader.load(getClass().getResource("/application/InvitePlayers.fxml"));
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/application/application.css").toExternalForm());
            
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
    
    public static Game getGame() {
    	return game;
    }
    
    public static void redrawScene() {
        
    }
    
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
    	  			// TODO Auto-generated catch block
    	  			e.printStackTrace();
    	  		}
    	      }
    	   };
    	   
    	 networkThread.start();
    	
        launch(args);
        //testing
//       
//        System.out.println(player.getName());
//        System.out.println(player.getHost());
//        Network network = new Network();
//        while (true) {
//        	game.process(network.do_communication());
//        }
        
    }
}
