package application;

import java.io.IOException;
import java.util.HashMap;
import java.util.ListIterator;

import guesswho.Controller;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
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
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
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
import javafx.stage.StageStyle;

/**
 * Provides the controls for the gameplay screen and the buttons, cards, and
 * chat that are displayed on the screen.
 * 
 * @author Dani, Hannah, Anna
 *
 */
public class GameplayScreenController extends Controller {
        
    boolean guessing = false;

    static HashMap<Integer, Boolean> greyedOutCards = new HashMap<>();
    
    @FXML
    private GridPane cardGrid;
    
    @FXML
    private Label turn;
    
    @FXML
    private Button guessButton;
    
    @FXML
    private Button endTurn;
    
    @FXML
    private Label scoresBox;
    
    @FXML
    private ImageView yourCard;
    
    @FXML
    private Label yourCardName;
    
    @FXML
    private TextField chatInput;
    
    @FXML
    private TextArea chatArea;
    
    public static int guessedId;
    
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
        guessButton.setOnAction(e -> guessButtonPressed());
        
        //setting scores text
        scoresBox.setText(player.getName()+" = "+player.getScore()+" \t "+game.getPlayer2Name()+" = "+game.getPlayer2Score());
        
        //set player's card
        String cardPath = "application/" + player.getCard().getImagePath();
        System.out.println(cardPath);
        Image image = new Image(cardPath);
        yourCard.setImage(image);
        
        //set player's card name
        yourCardName.setText(player.getCard().getName());
        
        //Initial chat messages
        chatArea.appendText("SERVER: " + player.getName() + " has entered the game. \n");

        //Opening waiting for players screen
        Stage window = new Stage();
        //waitingForPlayer(window); //COMMENTED OUT FOR NOW. OPENS WAITING FOR PLAYER DIALOG
        
        //TODO Call closeWaitingWindow(window) when the other player has connected to the game.

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
    private void greyOut(ImageView image, int imageId) {
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
            turn.setText("It is your turn \nto ask a question.");
            enableButtons();
            game.endTurn();
        } else {
        	//TODO
        	// this case should be when receiving a message from the server
            turn.setText("It is the other \nplayer's turn to \nask a question.");
            disableButtons();
            //set players turn to true? this might go in a different file
        }
        
    }
    
    /**
     * The action that is taken if the guess button is pressed.
     */
    public void guessButtonPressed() {
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
    private void guess(Node image, int imageId) {
        //Opens confirmation menu if the card is cicked on and isn't greyed out
        if(!greyedOutCards.get(imageId)) {
            image.setOnMouseClicked(e -> {
                try {
                    guessedId = imageId;
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
    private void stopGuessing(ImageView image, int imageId) {
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
    private void openConfirmationWindow() throws IOException {
        Stage thisStage = (Stage) ((Node) scoresBox).getScene().getWindow();
        Stage confirmationWindow = new Stage();
        confirmationWindow.initStyle(StageStyle.UNDECORATED);
        gameStage = thisStage;
        
        confirmationWindow.initModality(Modality.APPLICATION_MODAL);
        confirmationWindow.getIcons().add(new Image("application/icon.png"));
        confirmationWindow.setResizable(false);

        Parent root = FXMLLoader.load(getClass().getResource("ConfirmationMenu.fxml"));
        root.setStyle("-fx-background-color: white; -fx-border-color: black");
        Scene scene = new Scene(root);
        confirmationWindow.setScene(scene);
        dragScreen(scene, confirmationWindow);
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
     * Quits the game and returns to the invite players screen. Also resets the data
     * for this screen, like which cards are greyed out and the player score.
     * 
     * @param event The event that the button is pressed. Lets us know what screen
     * the button was on and therefore what screen to close.
     */
    public void quitGame(ActionEvent event) {
        System.out.println("Quitting...");
        
        //Resetting player data and hashmap
        player.reset();
        resetHashmap();      
        
        //TODO close connection to server
        
        //Going to invite players screen
        try {
            //Loads the new screen
            Parent gameParent = FXMLLoader.load(getClass().getResource("InvitePlayers.fxml"));
            Scene gameScene = new Scene(gameParent);
            
            //Finds the previous screen and switches off of it
            Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            appStage.setScene(gameScene);
            appStage.centerOnScreen();
            
            //Allows it to be dragged
            dragScreen(gameScene, appStage);
            
            //Shows the new screen
            appStage.show();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Waiting for player screen. This shows when one player is in
     * the room and the other has not joined yet.
     */
    private void waitingForPlayer(Stage waitingWindow) {
        //waitingWindow = new Stage();
        
        // Makes it so you can't click on the window behind until this one is closed.
        waitingWindow.initModality(Modality.APPLICATION_MODAL);
        waitingWindow.setResizable(false);
        waitingWindow.initStyle(StageStyle.UNDECORATED);
        
        //Adding Title
        Label text = new Label();
        text.setText("Waiting for other player...");
        text.setFont(Font.font("Century Gothic", 23));
        text.setPadding(new Insets(15,0,0,0));

        //Progress wheel
        ProgressIndicator progress = new ProgressIndicator();
        
        VBox root = new VBox(25);
        root.getChildren().addAll(text, progress);
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-background-color: white; -fx-border-color: black");

        
        // Display the scene
        Scene scene = new Scene(root, 350, 200);
        waitingWindow.setScene(scene);
        waitingWindow.show(); //change to showAndWait if wanting to stop at invite screen
    }
    
    /**
     * Closes the waiting window dialog.
     */
    private void closeWaitingWindow(Stage waitingWindow) {
        waitingWindow.close();
    }
    
    /**
     * Enables the guess and endTurn buttons to be used
     * again when it is the player's turn.
     */
    private void enableButtons() {
        guessButton.setDisable(false);
        endTurn.setDisable(false);
    }
    
    /**
     * Disables the guess and endTurn buttons when it is not the
     * player's turn.
     */
    private void disableButtons() {
        guessButton.setDisable(true);
        endTurn.setDisable(true);
    }
    
    /**
     * 
     */
    public void chat() { //used to pass in String msg... Should I put it back?
        //TODO implement chat using chatInput and chatArea
        //Called when user presses enter inside the chatbox. Puts what they
        //typed into the chat
    	//Is this used to add text to the chat? (Asked by Anna) It is now lol
        String msg = chatInput.getText();
        
        chatArea.appendText(player.getName() + ": " + msg + "\n");
        chatInput.clear();
    }
    
    /**
     * Resets the hashmap that contains the greyed out cards so that
     * all cards are set to false.
     */
    public static void resetHashmap() {
        for(Integer key : greyedOutCards.keySet()) {
            deck.getCard(key.intValue()).resetGrey();
            greyedOutCards.put(key, deck.getCard(key).getGrey());
        }    
    }
        
   
}
