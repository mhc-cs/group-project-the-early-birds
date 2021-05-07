package application;

import java.io.IOException;

import guesswho.Controller;
import guesswho.Network;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Contains methods for the waiting for players screen. Initializes
 * the screen and defines the action for the button.
 * 
 * @author Dani, Hannah, Anna
 *
 */
public class WaitingForPlayersController {
    @FXML
    private Button close;
    
    /**
     * Initializes the scene. The close button will appear after 3 seconds.
     */
    public void initialize() {
        close.setVisible(false);
        PauseTransition delayButton = new PauseTransition(Duration.seconds(3));
        delayButton.setOnFinished(e -> close.setVisible(true));
        delayButton.play();
    }
    
    /**
     * Allows the user to close the dialog and stop attempting connection to the
     * other player.
     */
    public void closeDialog() {
        Stage stage = (Stage) close.getScene().getWindow();
        stage.close();
        //TODO stop connecting. leave message?
        HostGamecodeScreenController.setRunThread(false);
        PlayerGamecodeScreenController.setRunThread(false);
        Network.close();
        //go back to invite players screen
    }
}
