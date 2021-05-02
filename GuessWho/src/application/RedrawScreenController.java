package application;

import java.io.IOException;

import guesswho.Controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 * Shows the player their card and allows them to redraw.
 * @author Dani, Hannah, Anna
 *
 */
public class RedrawScreenController extends Controller{

    @FXML
    private Label cardName;
    
    @FXML
    private ImageView card;
    
    /**
     * Initializes the screen. Draws cards and displays them.
     */
    public void initialize() {
        game.drawCards();
        Image image = new Image("application/" + player.getCard().getImagePath());
        card.setImage(image);
        
        //set player's card name
        cardName.setText(player.getCard().getName());
    }
    
    /**
     * Redraws the card.
     */
    public void redraw() {
        game.redrawCards();
        Image image = new Image("application/" + player.getCard().getImagePath());
        card.setImage(image);
        
        //set player's card name
        cardName.setText(player.getCard().getName());
    }
    
    /**
     * Continues to the gameplay screen.
     * @param event the event that the button is pressed.
     */
    public void continueButton(ActionEvent event) {
        //Going to gameplay screen
        try {
            //Loads the new screen
            Parent startGameParent = FXMLLoader.load(getClass().getResource("GameplayScreen.fxml"));
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
