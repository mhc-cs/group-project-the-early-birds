package application;

import java.io.IOException;

import guesswho.Controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * The controller for the confirmation menu that opens when the user
 * tries to guess. Has methods for each of the buttons, as well as the
 * incorrect guess popup window.
 * 
 * @author Hannah, Dani, Anna
 *
 */
public class ConfirmationMenuController extends Controller {
    
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
     * @param event the event that the confirm button is pressed.
     * @throws IOException 
     */
    public void confirm(ActionEvent event) throws IOException {
        int guessed = GameplayScreenController.guessedId;
        boolean correctGuess = game.guess(deck.getCard(guessed));
        
        if(correctGuess) {
            try {
                //Loads the new screen
                Parent newRoundParent = FXMLLoader.load(getClass().getResource("NewRoundScreen.fxml"));
                Scene newRoundScene = new Scene(newRoundParent);
                
                //Finds the previous screen and switches off of it
                Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                appStage.setScene(newRoundScene);
                appStage.centerOnScreen();
                
              //Allows it to be dragged
                dragScreen(newRoundScene, appStage);
                
                //Shows the new screen
                appStage.show();
                
            } catch (IOException e) {
                e.printStackTrace();
            
            }
        } else {
            GameplayScreenController controller = HostGamecodeScreenController.controller;
            
            //ends turn, mimics pressing the guess button and sets guessing to false
            controller.endTurn();
            controller.guessButtonPressed();
            controller.guessing = false;
            
            incorrectGuessWindow();
        }
    }
    
    /**
     * Displays the incorrect guess popup when the user guesses incorrectly.
     */
    public void incorrectGuessWindow() {
        Stage incorrectGuess = new Stage();
        
        // Makes it so you can't click on the window behind until this one is closed.
        incorrectGuess.initModality(Modality.APPLICATION_MODAL);
        incorrectGuess.setTitle("Incorrect!");
        incorrectGuess.getIcons().add(new Image("application/icon.png"));
        incorrectGuess.setResizable(false);
        
        //Adding Title
        Label title = new Label();
        title.setText("You guessed\n wrong!");
        title.setFont(Font.font("Century Gothic", 48));
        title.setPadding(new Insets(15,0,0,0));
        title.setTextAlignment(TextAlignment.CENTER);
        
        // Adding button to close the window.
        Button closeButton = new Button("Continue");
        closeButton.setOnAction(e -> incorrectGuess.close());
        closeButton.setMinWidth(159);
        closeButton.setMaxHeight(38);
        closeButton.setFont(Font.font("Century Gothic", 20));
        BackgroundFill backgroundFill = new BackgroundFill(Color.rgb(134, 190, 255), CornerRadii.EMPTY, Insets.EMPTY);
        Background background = new Background(backgroundFill);
        closeButton.setBackground(background);
        
        VBox root = new VBox(30);
        root.getChildren().addAll(title, closeButton);
        root.setAlignment(Pos.CENTER);
        
        // Display the scene
        Scene scene = new Scene(root, 357, 275);
        incorrectGuess.setScene(scene);
        incorrectGuess.showAndWait(); //can't interact with window below until this is closed
    }
}
