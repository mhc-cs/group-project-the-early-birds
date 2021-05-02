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
    
    public void initialize() {
        //winner.setText("... wins!"); //set text to winner's name wins
    }
    
    /**
     * Goes back to gameplay screen and scores stay as they are,
     * to be increased.
     */
    public void nextRound() {
        Stage stage = (Stage) winner.getScene().getWindow();
        stage.close();
        //TODO redraw
        //game.assignFirstTurn();
    }
    
    /**
     * Goes back to gameplay screen, but resets scores to 0
     */
    public void newGame() {
        Stage stage = (Stage) winner.getScene().getWindow();
        stage.close();
        //resets scores to 0
        player.reset();
        //TODO should go to redraw screen again
        //game.assignFirstTurn();
    }
    
    /**
     * Clears everything and takes player back to the invite screen.
     * @param event
     */
    public void quit(ActionEvent event) {
        System.out.println("Quitting...");
        
        //Resetting player data and hashmap
        player.reset();
//        for(Integer key : greyedOutCards.keySet()) {
//            deck.getCard(key.intValue()).resetGrey();
//            greyedOutCards.put(key, deck.getCard(key).getGrey());
//        }        
        
        //TODO close connection to server
        
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
