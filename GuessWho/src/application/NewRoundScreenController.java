package application;

import java.io.IOException;

import guesswho.Controller;
import guesswho.Network;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * The controller for the screen when a player wins.
 * Gives the player three options: play next round, which goes back
 * to the redraw screen and keeps the players' scores; new game, which 
 * goes back to the redraw screen, but resets the scores to 0, and quit,
 * which takes them back to the invite screen.
 * 
 * @author Hannah, Dani, Anna
 *
 */
public class NewRoundScreenController extends Controller {

    @FXML
    private Label winner;
    
    /**
     * Initializes the screen.
     */
    public void initialize() {
        winner.setText("... wins!"); //set text to winner's name wins
    }
    
    /**
     * Goes back to redraw screen and scores stay as they are,
     * to be increased.
     * @param event the event that the button is pressed
     */
    public void nextRound(ActionEvent event) {
        //TODO assign first turn
        GameplayScreenController.resetHashmap();
        gameStage.close();      
        try {
            //Loads the new screen
            Parent gameParent = FXMLLoader.load(getClass().getResource("RedrawScreen.fxml"));
            Scene gameScene = new Scene(gameParent);
            
            //Finds the previous screen and switches off of it
            Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            appStage.setScene(gameScene);
            appStage.centerOnScreen();
            
            //Allows it to be dragged
            dragScreen(gameScene, appStage);
            
            //Shows the new screen
            appStage.show();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Goes back to redraw screen, but resets scores to 0
     */
    public void newGame(ActionEvent event) {
        gameStage.close(); 
        //resets scores to 0 and clears greyed out hashmap
        //TODO assign first turn
        GameplayScreenController.resetHashmap();
        player.reset();
      
        try {
            //Loads the new screen
            Parent gameParent = FXMLLoader.load(getClass().getResource("RedrawScreen.fxml"));
            Scene gameScene = new Scene(gameParent);
            
            //Finds the previous screen and switches off of it
            Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            appStage.setScene(gameScene);
            appStage.centerOnScreen();
            
            //Allows it to be dragged
            dragScreen(gameScene, appStage);
            
            //Shows the new screen
            appStage.show();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Clears everything and takes player back to the invite screen.
     * @param event
     */
    public void quit(ActionEvent event) {
        gameStage.close();
        Network.close();
        //Resetting player data and hashmap
        player.reset();
        GameplayScreenController.resetHashmap();
        
        //Going to invite players screen
        try {
            //Loads the new screen
            Parent gameParent = FXMLLoader.load(getClass().getResource("InvitePlayers.fxml"));
            Scene gameScene = new Scene(gameParent);
            
            //Finds the previous screen and switches off of it
            Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            appStage.setScene(gameScene);
            appStage.centerOnScreen();
            
            //Allows it to be dragged
            dragScreen(gameScene, appStage);
            
            //Shows the new screen
            appStage.show();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
