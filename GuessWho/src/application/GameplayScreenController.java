package application;

import java.util.HashMap;
import java.util.ListIterator;

import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Provides the controls for the gameplay screen and the buttons, cards, and
 * chat that are displayed on the screen.
 * 
 * @author Dani, Hannah, Anna
 *
 */
public class GameplayScreenController {
    
    /**
     * The total number of cards in the game that the players guess from.
     */
    public static final int NUM_CARDS = 24;
    
    boolean isYourTurn = true;
    
    boolean canBeGreyed = true;

    HashMap<Integer, Boolean> greyedOutCards = new HashMap<>();
    
    @FXML
    private GridPane cardGrid;
    
    @FXML
    private Label turn;
    
    @FXML
    private Button guessButton;
    
    /**
     * Initializes the grid with the cards that the players guess from.
     */
    public void initialize() {
        int column = 0; //goes up to 7.
        int row = 0; //goes up to 2
        for(int i = 0; i < NUM_CARDS; i++) {
            // Wrapping in ImageView
            ImageView imageView = new ImageView(getClass().getResource("defaultImages/default" + i + ".png").toExternalForm());
            imageView.setFitWidth(95.0);
            imageView.setFitHeight(150.0);
            
            // When clicked on, can be greyed out (and un-greyed out)
            final int imageId = i;
            greyedOutCards.put(imageId, false);
            imageView.setOnMouseClicked(e -> greyOut(imageView, imageId));    
            
            // Adding to grid
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
        
        //Passes every image in the grid to guess button when guess button is pressed
        guessButton.setOnAction(e -> {
            int id = 0;
            //iterates through each image
            ListIterator<javafx.scene.Node> iterator = cardGrid.getChildren().listIterator(0);
            while(iterator.hasNext()) {
                //calls guess on every image
                guess((ImageView) iterator.next(), id);
                id++;
            } 
        });
    }
    
    /**
     * Allows an image to be greyed out and returned to normal. This means the image
     * is replaced with a gray box of the same size when clicked on, and then returned
     * back to the original image when clicked on again.
     * 
     * @param image The image to be greyed out
     * @param imageId The ID of the image to be greyed out
     */
    @SuppressWarnings("boxing")
    public void greyOut(ImageView image, int imageId) {
        if(canBeGreyed) {
            if(greyedOutCards.get(imageId) == false) { //if it's not greyed out
                //set image to be greyed out
                image.setImage(new Image("application/defaultImages/grey.png"));
                //set value for that key to indicate that it's greyed out
                greyedOutCards.put(imageId, true);
            } else { //if it's greyed out
                //set image back
                image.setImage(new Image("application/defaultImages/default" + imageId + ".png"));
                //set value for that key to indicate that it's not greyed out
                greyedOutCards.put(imageId, false);
            }
        }
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
    
    /**
     * Allows the player to guess which card is the other player's.
     * When pressed, the cards that are not greyed out have a drop shadow when
     * hovered over to show that they can be selected. A confirmation menu will open
     * when the player chooses a card.
     * 
     * @param image one of the images in the card grid
     * @param imageId the index, or ID, of one of the images in the card grid
     */
    public void guess(ImageView image, int imageId) {
        canBeGreyed = false;
        image.hoverProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean hovering) -> {
            //checking that the mouse is hovering over and that the card isn't greyed out
            if(hovering && !greyedOutCards.get(imageId)) {
                image.setEffect(new DropShadow());
            } else {
                image.setEffect(null);
            }
        });
    }
  
    
    /**
     * Displays the help window with instructions on how to play guess who.
     * Has a button for closing the window and the window behind it cannot be
     * interacted with while this window is open.
     */
    public void displayHelpWindow() {
        Stage helpWindow = new Stage();
        
        // Makes it so you can't click on the window behind until this one is closed.
        helpWindow.initModality(Modality.APPLICATION_MODAL);
        helpWindow.setTitle("Guess Who Instructions");
        helpWindow.getIcons().add(new Image("application/icon.png"));
        helpWindow.setResizable(false);
        
        //Adding Title
        Label title = new Label();
        title.setText("How to play Guess Who");
        title.setFont(Font.font("Century Gothic", 23));
        title.setPadding(new Insets(15,0,0,0));
        
        //Adding instructions
        Label instructions = new Label();
        instructions.setText(
                "Notice how each face in the grid has distinctive features. You'll use "
                + "these to ask questions to guess who is on your opponent's card before "
                + "your opponent guesses who is on yours. Alternate asking 'yes' or 'no'"
                + " questions about the person on your opponent's card using the chat. "
                + "The game will randomly chose which player goes first, and the text at "
                + "the bottom of the screen indicates whether it is your turn.\n\nFor example,"
                + " you could ask: 'Does your person have brown hair?'. If your opponent answers"
                + " 'yes', then you can eliminate every person without brown hair. If the answer"
                + " is 'no', then all the people with brown hair can be eliminated. Eliminate a "
                + "card by clicking on it. When you are done with your turn, press the end turn "
                + "button.\n\nWhen you are ready to guess, click on the guess button and choose the "
                + "card from the grid that you believe is your opponent's card. If you guess "
                + "correctly, you win! If you guess incorrectly, gameplay continues.");
        instructions.setFont(Font.font("Century Gothic", 18));
        instructions.setMaxWidth(500);
        instructions.setPadding(new Insets(10,15,10,15));
        instructions.setWrapText(true);
        
        // Labels for source
        Label source = new Label();
        source.setText("Source: https://www.ultraboardgames.com/guess-who/game-rules.php");
        source.setFont(Font.font("Century Gothic", 10));
        source.setPadding(new Insets(0,0,10,0));
        
        // Adding button to close the window.
        Button closeButton = new Button("Close");
        closeButton.setOnAction(e -> helpWindow.close());
        closeButton.setTranslateY(-10);
        closeButton.setFont(Font.font("Century Gothic"));
        BackgroundFill backgroundFill = new BackgroundFill(Color.rgb(255, 26, 26), CornerRadii.EMPTY, Insets.EMPTY);
        Background background = new Background(backgroundFill);
        closeButton.setBackground(background);
        
        VBox root = new VBox(10);
        root.getChildren().addAll(title, instructions, source, closeButton);
        root.setAlignment(Pos.CENTER);
        
        // Display the scene
        Scene scene = new Scene(root);
        helpWindow.setScene(scene);
        helpWindow.showAndWait(); //can't interact with window below until this is closed
    }
    
    /**
     * Quits the game and returns to the invite players screen.
     */
    public void quitGame() {
        System.out.println("Quitting...");
        //Here's where to add the code for the quit button
    }

    
}
