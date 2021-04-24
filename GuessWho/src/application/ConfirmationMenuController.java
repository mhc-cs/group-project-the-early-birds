package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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
    public void confirm() {
        //TODO implement
    }
}
