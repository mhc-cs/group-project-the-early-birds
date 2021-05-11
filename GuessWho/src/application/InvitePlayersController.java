package application;

import java.io.IOException;

import Messages.Hello;
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

    private boolean connected;

    /**
     * Goes to the screen where the host chooses the game code.
     * 
     * @param event The action of pressing the button. Allows us to know where
     *              the button press came from, and therefore which scene the
     *              program came from.
     */
    public void startGame(ActionEvent event) {
        player.setHost(true);
        player.setName(playerName.getText());
        if (playerName.getText().isEmpty()) {
            warning.setText("Please enter a name.");
        } else {
            if (!connected) {
                connected = Network.connect();
                if (!connected) {
                    warning.setText("Could not connect.");
                    return;
                }
            }
            Controller.network
                    .send(new Hello("HELLO", player.getName(), "guesswho"));
            game.setBadName(false);
            if (game.welcomed() == false) {
                    try {
                        Thread.sleep(2000);
                        if (game.badname()) {
                            warning.setText(
                                    "Name already in use, choose another.");
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
            }
        }
        if (game.welcomed() == true) {
            player.setName(playerName.getText());
            try {
                // Loads the new screen
                Parent startGameParent = FXMLLoader.load(
                        getClass().getResource("HostGamecodeScreen.fxml"));
                Scene startGameScene = new Scene(startGameParent);

                // Finds the previous screen and switches off of it
                Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                appStage.setScene(startGameScene);

                // Allows it to be dragged
                dragScreen(startGameScene, appStage);

                // Shows the new screen
                appStage.show();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * Goes to the screen where the player enters the game code.
     * 
     * @param event The action of pressing the button. Allows us to know where
     *              the button press came from, and therefore which scene the
     *              program came from.
     */
    public void joinGame(ActionEvent event) {
        player.setHost(false);
        player.setName(playerName.getText());
        if (playerName.getText().isEmpty()) {
            warning.setText("Please enter a name.");
        } else {
            if (!connected) {
                connected = Network.connect();
                if (!connected) {
                    warning.setText("Could not connect.");
                    return;
                }
            }
            Controller.network
                    .send(new Hello("HELLO", player.getName(), "guesswho"));
            game.setBadName(false);
            if (game.welcomed() == false) {
                warning.setText("Waiting for server confirmation.");
                try {
                    Thread.sleep(2000);
                    if (game.badname()) {
                        warning.setText("Name already in use, choose another.");
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if (game.welcomed() == true) {
//                player.setName(playerName.getText());
                try {
                    // Loads the new screen
                    Parent startGameParent = FXMLLoader.load(getClass()
                            .getResource("PlayerGamecodeScreen.fxml"));
                    Scene startGameScene = new Scene(startGameParent);

                    // Finds the previous screen and switches off of it
                    Stage appStage = (Stage) ((Node) event.getSource())
                            .getScene().getWindow();
                    appStage.setScene(startGameScene);

                    // Allows it to be dragged
                    dragScreen(startGameScene, appStage);

                    // Shows the new screen
                    appStage.show();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    /**
     * Allows the user to quit the game
     */
    public void quit() {
        Stage stage = (Stage) warning.getScene().getWindow();
        stage.close();
    }
}
