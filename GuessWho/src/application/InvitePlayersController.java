package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class InvitePlayersController {
    
    @FXML
    private TextField playerName;
    
    @FXML
    private Label warning;
    
    private static String name;
    
    private static boolean isHost;
    
    /**
     * Goes to the screen where the host chooses the game code.
     * 
     * @param event The action of pressing the button. Allows us to know where the
     * button press came from, and therefore which scene the program came from.
     */
    public void startGame(ActionEvent event) {
        isHost = true;
        if(playerName.getText().isEmpty()) {
            warning.setText("Please enter a name.");
        } else {
            name = playerName.getText();
            System.out.println("Name: " + name);
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
        isHost = false;
        if(playerName.getText().isEmpty()) {
            warning.setText("Please enter a name.");
        } else {
            name = playerName.getText();
            System.out.println("Name: " + name);
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
     * Returns the player's name.
     * @return player name
     */
    public static String getName() {
        return name;
    }
    
    /**
     * Returns whether the player is the host.
     * @return whether true if the player is the host, false otherwise.
     */
    public static boolean isHost() {
        return isHost;
    }
}
