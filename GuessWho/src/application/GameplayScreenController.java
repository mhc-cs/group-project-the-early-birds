package application;

import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

public class GameplayScreenController {
    
    /**
     * The total number of cards in the game that the players guess from.
     */
    public static final int NUM_CARDS = 24;
    
    boolean isYourTurn = true;
    
    @FXML
    private GridPane cardGrid;
    
    @FXML
    private Label turn;
    
    /**
     * Initializes the grid with the cards that the players guess from.
     */
    public void initialize() {
        int column = 0; //goes up to 7
        int row = 0; //goes up to 2
        for(int i = 0; i < NUM_CARDS; i++) {
            ImageView imageView = new ImageView(getClass().getResource("defaultImages/default" + i + ".png").toExternalForm());
            imageView.setFitWidth(95.0);
            imageView.setFitHeight(150.0);
            cardGrid.add(imageView, column, row);
            if(column == 7) {
                row++;
                column = 0;
            } else {
                column++;
            }
        }
        cardGrid.setVgap(27.5);
        cardGrid.setHgap(7);
    }
    
    /**
     * Ends the player's turn by changing the turn indicator label to
     * let them know whose turn it is.
     */
    public void endTurn() {
        if(isYourTurn) {
            turn.setText("It is the other \nplayer's turn to \nask a question.");
            isYourTurn = false;
        } else {
            turn.setText("It is your turn \nto ask a question.");
            isYourTurn = true;
        }
        
    }
    
    

    
}
