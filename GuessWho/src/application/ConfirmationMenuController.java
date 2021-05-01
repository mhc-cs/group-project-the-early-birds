package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class ConfirmationMenuController {
    
    @FXML
    public Button cancel;
    
    /**
     * Closes the window.
     * 
     * @param event The event of pushing the button
     */
    public void close(ActionEvent event) {
        //Gets the current scene that the button is in and closes it
        Stage stage = (Stage) cancel.getScene().getWindow();
        stage.close();
    }
    
    /**
     * Checks that the guess is correct.
     * Happens when the "yes" button is pressed in the confirmation
     * menu. 
     */
    public void confirm(ActionEvent event) {
        //TODO implement
        
        try {
            //Loads the new screen
            Parent startGameParent = FXMLLoader.load(getClass().getResource("NewRoundScreen.fxml"));
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
