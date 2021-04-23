package application;

import java.io.IOException;
import java.util.HashMap;
import java.util.ListIterator;

import guesswho.Controller;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
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
public class GameplayScreenController extends Controller {
        
    boolean guessing = false;

    HashMap<Integer, Boolean> greyedOutCards = new HashMap<>();
    
    @FXML
    private GridPane cardGrid;
    
    @FXML
    private Label turn;
    
    @FXML
    private Button guessButton;
    
    @FXML
    private Label scoresBox;
    
    @FXML
    private ImageView yourCard;
    
    @FXML
    private Label yourCardName;
    
    /**
     * Initializes the grid with the cards that the players guess from.
     */
    public void initialize() {
        int column = 0; //goes up to 7
        int row = 0; //3 rows of faces
        
        for(int i = 0; i < deck.getSize(); i++) {
            // Wrapping in ImageView
            ImageView imageView = new ImageView(getClass().getResource(deck.getCard(i).getImagePath()).toExternalForm());
            imageView.setFitWidth(95.0);
            imageView.setFitHeight(150.0);
            
            //Displaying names
            Label name = new Label();
            name.getStyleClass().remove(name);
            name.setText(deck.getCard(i).getName());
            name.setFont(Font.font("Century Gothic", 13));
            GridPane.setHalignment(name, HPos.CENTER);
            
            // When clicked on, can be greyed out (and un-greyed out)
            final int imageId = i;
            greyedOutCards.put(imageId, deck.getCard(i).getGrey());
            imageView.setOnMouseClicked(e -> greyOut(imageView, imageId));    
            
            // Adding to grid
            cardGrid.add(imageView, column, row);
            cardGrid.add(name, column, row + 1);
            if(column == 7) {
                row += 2;
                column = 0;
            } else {
                column++;
            }
            
        }
        cardGrid.setVgap(2);
        cardGrid.setHgap(7);
        
        //Passes every image in the grid to guess button when guess button is pressed
        guessButton.setText("Guess "+game.getPlayer2Name()+"'s card");
        guessButton.setOnAction(e -> {
            //Action that the guess button takes if guessing is not in action
            if(guessing == false) {
                int id = 0;
                guessing = true;
                guessButton.setText("Stop guessing");
                
                //iterates through each image
                ListIterator<javafx.scene.Node> iterator = cardGrid.getChildren().listIterator(0);
                while(iterator.hasNext()) {
                    //calls guess on every image
                    Node next = iterator.next();
                    if(next instanceof ImageView) {
                        guess(next, id);
                        id++;
                    }
                } 
            } else {
                //Action that the guess button takes if guessing is in action
                int id = 0;
                guessing = false;
                guessButton.setText("Guess "+game.getPlayer2Name()+"'s card");
                
                //iterates through each image
                ListIterator<javafx.scene.Node> iterator = cardGrid.getChildren().listIterator(0);
                while(iterator.hasNext()) {
                    //calls stopGuessing on every image
                    Node next = iterator.next();
                    if(next instanceof ImageView) {
                        stopGuessing((ImageView) next, id);
                        id++; 
                    }
                }
            }
        });
        
        //setting scores text
        scoresBox.setText(player.getName()+" = "+player.getScore()+" \t "+game.getPlayer2Name()+" = "+game.getPlayer2Score());
        
        //set player's card
        game.drawCards();
        String cardPath = "application/" + player.getCard().getImagePath();
        System.out.println(cardPath);
        Image image = new Image(cardPath);
        yourCard.setImage(image);
        
        //set player's card name
        yourCardName.setText(player.getCard().getName());
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
        if(!guessing) {
            if(greyedOutCards.get(imageId) == false) { //if it's not greyed out
                //set image to be greyed out
                image.setImage(new Image("application/defaultImages/grey.png"));
            } else { //if it's greyed out
                //set image back
                image.setImage(new Image("application/"+deck.getCard(imageId).getImagePath()));
            }
            //set value for that key to indicate if it is greyed out
            deck.getCard(imageId).toggleGrey();
            greyedOutCards.put(imageId, deck.getCard(imageId).getGrey());
        }
    }
    
    /**
     * Ends the player's turn by changing the turn indicator label to
     * let them know whose turn it is.
     */
    public void endTurn() {
        if(player.getTurn()) {
            turn.setText("It is the other \nplayer's turn to \nask a question.");
            game.endTurn();
        } else {
        	//TODO
        	// this case should be when receiving a message from the server
            turn.setText("It is your turn \nto ask a question.");
            //set players turn to true? this might go in a different file
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
    public void guess(Node image, int imageId) {
        //Opens confirmation menu if the card is cicked on and isn't greyed out
        if(!greyedOutCards.get(imageId)) {
            image.setOnMouseClicked(e -> {
                try {
                    openConfirmationWindow();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            });
        }
        //Adds hover property to card if it isn't greyed out
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
     * Removes the hover effect when the player is no longer guessing.
     * 
     * @param image one of the images in the card grid
     * @param imageId the index, or ID, of one of the images in the card grid
     */
    public void stopGuessing(ImageView image, int imageId) {
        //Now when you click, it's back to just greying out, instead of opening the menu
        image.setOnMouseClicked(e -> greyOut(image, imageId));
        
        //sets hover property back to nothing
        image.hoverProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean hovering) -> {
            image.setEffect(null);
        });
    }
    
    /**
     * Opens the guessing confirmation window. This asks the player
     * if they are sure if they would like to choose that card. They
     * can press "yes" and choose the card or "cancel" and exit the window.
     * 
     * @throws IOException if the file to make the window is not found.
     */
    public void openConfirmationWindow() throws IOException {
        Stage confirmationWindow = new Stage();
        confirmationWindow.initModality(Modality.APPLICATION_MODAL);
        confirmationWindow.setTitle("Are you sure?");
        confirmationWindow.getIcons().add(new Image("application/icon.png"));
        confirmationWindow.setResizable(false);
        
        Parent root = FXMLLoader.load(getClass().getResource("ConfirmationMenu.fxml"));
        Scene scene = new Scene(root);
        confirmationWindow.setScene(scene);
        confirmationWindow.showAndWait(); 
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
        //TODO close connection to server
    }
   
}
