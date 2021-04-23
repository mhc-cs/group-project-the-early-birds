package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class PlayerGamecodeScreenController {
    
    @FXML
    private TextField gamecode;
    
    private static String code;
    
    /**
     * Sets the gamecode that the player entered.
     * Happens when continue button is pressed.
     * 
     * @param event The action of pressing the button. Allows us to know where the
     * button press came from, and therefore which scene the program came from.
     */
    public void continueButton(ActionEvent event) {
        code = gamecode.getText();
        System.out.println(code);
        //TODO connect players with gamecode
        
        //Going to gameplay screen
        try {
            //Loads the new screen
            Parent startGameParent = FXMLLoader.load(getClass().getResource("GameplayScreen.fxml"));
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
    
    /**
     * Gets the gamecode that the player entered
     * @return gamecode the player entered
     */
    public static String getPlayerCode() {
        return code;
    }
    
    /**
     * Goes back to the invite players screen.
     * 
     * @param event The action of pressing the button. Allows us to know where the
     * button press came from, and therefore which scene the program came from.
     */
    public void goBack(ActionEvent event) {
        try {
            //Loads the new screen
            Parent invitePlayersParent = FXMLLoader.load(getClass().getResource("InvitePlayers.fxml"));
            Scene invitePlayersScene = new Scene(invitePlayersParent);
            
            //Finds the previous screen and switches off of it
            Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            appStage.setScene(invitePlayersScene);
            
            //Shows the new screen
            appStage.show();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
