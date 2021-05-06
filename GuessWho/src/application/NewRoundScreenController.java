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
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * The controller for the screen when a player wins.
 * Gives the player three options: play next round, which goes back
 * to the gameplay screen and keeps the players' scores; new game, which 
 * goes back to the gameplay screen, but resets the scores to 0, and quit,
 * which takes them back to the invite screen.
 * 
 * @author Hannah, Dani, Anna
 *
 */
public class NewRoundScreenController extends Controller {

    @FXML
    private Label winner;
    
    private Stage window = new Stage();
    
    /**
     * Initializes the screen.
     */
    public void initialize() {
        winner.setText(game.getWinner() + " wins!"); //set text to winner's name wins
    }
    
    /**
     * Goes back to gameplay screen and scores stay as they are,
     * to be increased.
     * @param event the event that the button is pressed
     */
    public void nextRound(ActionEvent event) {
        GameplayScreenController.resetHashmap();
        gameStage.close();      
        
        //waitingForPlayers(window);
        
        try {
            //Loads the new screen
            Parent gameParent = FXMLLoader.load(getClass().getResource("GameplayScreen.fxml"));
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
     * Goes back to gameplay screen, but resets scores to 0
     */
    public void newGame(ActionEvent event) {
        gameStage.close(); 
        GameplayScreenController.resetHashmap();
        player.reset();
        try {
            //Loads the new screen
            Parent gameParent = FXMLLoader.load(getClass().getResource("GameplayScreen.fxml"));
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
    
    /**
     * Waiting for player screen. This shows when one player is in
     * the room and the other has not joined yet.
     * @throws IOException if fxml file is not found.
     */
    private void waitingForPlayer(Stage waitingWindow) throws IOException {
        waitingWindow.initStyle(StageStyle.UNDECORATED);
        
        waitingWindow.initModality(Modality.APPLICATION_MODAL);
        waitingWindow.getIcons().add(new Image("application/icon.png"));
        waitingWindow.setResizable(false);

        Parent root = FXMLLoader.load(getClass().getResource("WaitingForPlayers.fxml"));
        root.setStyle("-fx-background-color: white; -fx-border-color: black");
        Scene scene = new Scene(root);
        waitingWindow.setScene(scene);
        waitingWindow.showAndWait(); 
    }
    
    /**
     * Closes the waiting window dialog.
     */
    private void closeWaitingWindow(Stage waitingWindow) {
        waitingWindow.close();
    }
}
