package application;

import java.io.IOException;

import Messages.Data;
import Messages.Message;
import guesswho.Controller;
import guesswho.Network;
import javafx.application.Platform;
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
 * The controller for the screen when a player wins. Gives the player two
 * options: play next round, which goes back to the gameplay screen and keeps
 * the players' scores, and quit, which takes them back to the invite screen.
 * 
 * @author Hannah, Dani, Anna
 *
 */
public class NewRoundScreenController extends Controller {

    @FXML
    private Label winner;

    private Stage window = new Stage();

    private static boolean runThread;

    private static boolean isReady;

    /**
     * true if the quit button has been pressed at least once.
     */
    public static boolean quitButtonPressed = false;

    /**
     * Initializes the screen.
     */
    public void initialize() {
        winner.setText(game.getWinner() + " wins!"); // set text to winner's
                                                     // name wins
        isReady = false;
    }

    /**
     * Goes back to gameplay screen and scores stay as they are, to be
     * increased.
     * 
     * @param event the event that the button is pressed
     */
    public void nextRound(ActionEvent event) {
        cardsAdded = true;
        GameplayScreenController.resetHashmap();
        GameplayScreenController controller = GameplayScreenController.getController();
        controller.stopGuessingHelper();
        controller.resetGrey();

        Thread waitingThread = new Thread("Waiting Thread") {
            public void run() {
                try {
                    runThread = true;
                    while (runThread) {
                        if (isReady) {
                            Platform.runLater(() -> {
                                controller.initialize();
                                closeWaitingWindow(window);
                                Stage stage = (Stage) winner.getScene().getWindow();
                                stage.close();
                            });
                            runThread = false;
                        }
                        Thread.sleep(500);
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }

        };

        waitingThread.start();
        if (!player.getHost()) {
            Controller.network.send(new Data("DATA", new Message("continue")));
        }
        try {
            waitingForPlayer(window);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Clears everything and takes player back to the invite screen.
     * 
     * @param event
     */
    public void quit(ActionEvent event) {
        // resetting everything
        cardsAdded = true;
        quitButtonPressed = true;
        gameStage.hide();
        Network.close();
        player.reset();
        GameplayScreenController.resetHashmap();
        PlayerGamecodeScreenController.setIsReady(false);
        game.setPlayer2Score(0);

        // Going to invite players screen
        try {
            // Loads the new screen
            Parent gameParent = FXMLLoader
                    .load(getClass().getResource("InvitePlayers.fxml"));
            Scene inviteScene = new Scene(gameParent);

            // Finds the previous screen and switches off of it
            Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            appStage.setScene(inviteScene);
            appStage.centerOnScreen();

            // Allows it to be dragged
            dragScreen(inviteScene, appStage);

            // Shows the new screen
            appStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Waiting for player screen. This shows when one player is in the room and
     * the other has not joined yet.
     * 
     * @throws IOException if fxml file is not found.
     */
    private void waitingForPlayer(Stage waitingWindow) throws IOException {
        Stage thisStage = (Stage) ((Node) winner).getScene().getWindow();
        Controller.setPrevStage(thisStage);
        waitingWindow.initStyle(StageStyle.UNDECORATED);

        waitingWindow.initModality(Modality.APPLICATION_MODAL);
        waitingWindow.getIcons().add(new Image("application/icon.png"));
        waitingWindow.setResizable(false);

        Parent root = FXMLLoader
                .load(getClass().getResource("WaitingForPlayers.fxml"));
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

    /**
     * Sets the run thread.
     * 
     * @param run Whether the thread should be running.
     */
    public static void setRunThread(boolean run) {
        runThread = run;
    }

    /**
     * Sets the value for isReady
     * 
     * @param ready whether the player is ready
     */
    public static void setIsReady(boolean ready) {
        isReady = ready;
    }
}
