package application;

import java.io.IOException;
import guesswho.Network;
import guesswho.Controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Manages the invite players screen, where players enter their name and choose
 * whether they want to join or start a new game.
 * 
 * @author Anna, Dani, Hannah
 *
 */
public class InvitePlayersController extends Controller {
    
    @FXML
    private TextField playerName;
    
    @FXML
    private Label warning;
    
    /**
     * Goes to the screen where the host chooses the game code.
     * 
     * @param event The action of pressing the button. Allows us to know where the
     * button press came from, and therefore which scene the program came from.
     */
    public void startGame(ActionEvent event) {
        player.setHost(true);
        player.setName(playerName.getText());
        if(playerName.getText().isEmpty()) {
            warning.setText("Please enter a name.");
        } else if(!Network.connect()){
            	warning.setText("Could not connect.");
        } else {
        	player.setName(playerName.getText());
            try {
                //Loads the new screen
                Parent startGameParent = FXMLLoader.load(getClass().getResource("HostGamecodeScreen.fxml"));
                Scene startGameScene = new Scene(startGameParent);
                
                //Finds the previous screen and switches off of it
                Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                appStage.setScene(startGameScene);
                
                //Shows the new screen
                appStage.show();
                
            } catch (IOException e) {
                e.printStackTrace();  
            }
            
        }
    }
    
    /**
     * Goes to the screen where the player enters the game code.
     * 
     * @param event The action of pressing the button. Allows us to know where the
     * button press came from, and therefore which scene the program came from.
     */
    public void joinGame(ActionEvent event) {
        player.setHost(false);
        player.setName(playerName.getText());
        if(playerName.getText().isEmpty()) {
            warning.setText("Please enter a name.");
        } else if(!Network.connect()) {
        	warning.setText("Could not connect.");
        } else {
            Network.connect();
            player.setName(playerName.getText());
            try {
                //Loads the new screen
                Parent startGameParent = FXMLLoader.load(getClass().getResource("PlayerGamecodeScreen.fxml"));
                Scene startGameScene = new Scene(startGameParent);
                
                //Finds the previous screen and switches off of it
                Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                appStage.setScene(startGameScene);
                
                //Shows the new screen
                appStage.show();
                
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    /**
     * Allows the user to quit the game
     */
    public void quit() {
        Stage stage = (Stage) warning.getScene().getWindow();
        stage.close();
        System.exit(0); //terminates the java VM
    }
}
