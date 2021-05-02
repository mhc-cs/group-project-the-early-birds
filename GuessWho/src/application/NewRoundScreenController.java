package application;

import java.io.IOException;

import guesswho.Controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

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
     * Goes back to gameplay screen and scores stay as they are,
     * to be increased.
     */
    public void nextRound(ActionEvent event) {
        //TODO reset the hashmap with greyed out cards
        gameStage.close();      
        try {
            //Loads the new screen
            Parent startGameParent = FXMLLoader.load(getClass().getResource("RedrawScreen.fxml"));
            Scene startGameScene = new Scene(startGameParent);
            
            //Finds the previous screen and switches off of it
            Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            appStage.setScene(startGameScene);
            appStage.centerOnScreen();
            
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
        //resets scores to 0
       //TODO reset the hashmap with greyed out cards
        player.reset();
        
        try {
            //Loads the new screen
            Parent startGameParent = FXMLLoader.load(getClass().getResource("RedrawScreen.fxml"));
            Scene startGameScene = new Scene(startGameParent);
            
            //Finds the previous screen and switches off of it
            Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            appStage.setScene(startGameScene);
            appStage.centerOnScreen();
            
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
        //TODO reset the hashmap with greyed out cards
        //TODO close connection with other player
        
        //Resetting player data and hashmap
        player.reset();
//        for(Integer key : greyedOutCards.keySet()) {
//            deck.getCard(key.intValue()).resetGrey();
//            greyedOutCards.put(key, deck.getCard(key).getGrey());
//        }        
        
        //Going to invite players screen
        try {
            //Loads the new screen
            Parent startGameParent = FXMLLoader.load(getClass().getResource("InvitePlayers.fxml"));
            Scene startGameScene = new Scene(startGameParent);
            
            //Finds the previous screen and switches off of it
            Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            appStage.setScene(startGameScene);
            appStage.centerOnScreen();
            
            //Shows the new screen
            appStage.show();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
