package application;

import java.io.IOException;
import java.util.HashMap;
import java.util.ListIterator;

import Messages.Chat;
import Messages.Data;
import Messages.Guess;
import Messages.TurnUpdate;
import guesswho.Card;
import guesswho.Controller;
import guesswho.Network;
import javafx.application.Platform;
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
    
    private static String message;
    
    public static boolean turnCorrect;
    
    public static Guess guess = null;
    
    public static GameplayScreenController controller;
    
    /**
     * Initializes the grid with the cards that the players guess from.
     */
    public void initialize() {
    	System.out.println("!!!!!!!!!!!!!!!! INITIALIZING GAMEPLAYSCREEN !!!!!!!!!!!!");
    	if(!cardsAdded) {
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
                //System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$" + greyedOutCards.get(imageId));
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
    	}

        
        //Passes every image in the grid to guess button when guess button is pressed
        guessButton.setText("Guess "+game.getPlayer2Name()+"'s card");
        guessButton.setOnAction(e -> guessButtonPressed());
        guessButton.setStyle(String.format("-fx-font-size: %dpx;", (int)(-1 * game.getPlayer2Name().length() + 30 )));
        
        //setting scores text
        scoresBox.setText(player.getName()+" = "+player.getScore()+" \t "+game.getPlayer2Name()+" = "+game.getPlayer2Score());
        scoresBox.setStyle(String.format("-fx-font-size: %dpx;", (int) (-.1 * (game.getPlayer2Name().length() + player.getName().length()) + 15)));
        
        //set player's card
        game.drawCards();
        setCard(player.getCard());
        
        //Initial chat messages
        chatArea.clear();
        chatArea.appendText("SERVER: If you need instructions, press the '?' button! \n");
        chatArea.appendText("SERVER: " + game.getPlayer2Name() + " has entered the game. \n");
        
        setController(this);
        
     // longrunning operation runs on different thread
        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                Runnable updater = new Runnable() {

                    @Override
                    public void run() {
                    	if(message!=null) {
            	        	chatArea.appendText(game.getPlayer2Name() + ": " + message + "\n");
            	        	message=null;
            	        }                     }
                };

                while (true) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                    }

                    // UI update is run on the Application thread
                    Platform.runLater(updater);
                }
            }

        });
        // don't let thread prevent JVM shutdown
        thread.setDaemon(true);
        thread.start();
        
     // longrunning operation runs on different thread
        Thread turnThread = new Thread(new Runnable() {

            @Override
            public void run() {
                Runnable updater = new Runnable() {

                    @Override
                    public void run() {
                    	if(!turnCorrect) {
                    		if(player.getTurn()) {
                	        	startTurn();
                	        } else {
                	        	turnEnd();
                	        }
                    	}
                    	                     }
                };

                while (true) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                    }

                    // UI update is run on the Application thread
                    Platform.runLater(updater);
                }
            }

        });
        // don't let thread prevent JVM shutdown
        turnThread.setDaemon(true);
        turnThread.start();
        
        game.assignFirstTurn();
        turnCorrect=false;

     // longrunning operation runs on different thread
        Thread guessThread = new Thread(new Runnable() {

            @Override
            public void run() {
                Runnable updater = new Runnable() {

                    @Override
                    public void run() {
                    	if(guess!=null) {
            	        	if(guess.getCorrect()) {
            	        		//open newRound screen
            	        		try {
//            	                    //Loads the new screen
//            	                    Parent newRoundParent = FXMLLoader.load(getClass().getResource("NewRoundScreen.fxml"));
//            	                    Scene newRoundScene = new Scene(newRoundParent);
//            	                    
//            	                    //Finds the previous screen and switches off of it
//            	                    Stage appStage = (Stage) guessButton.getScene().getWindow();
//            	                    appStage.setScene(newRoundScene);
//            	                    appStage.centerOnScreen();
//            	                    
//            	                  //Allows it to be dragged
//            	                    dragScreen(newRoundScene, appStage);
//            	                    
//            	                    //Shows the new screen
//            	                    appStage.show();
            	                    openNewRoundWindow();
            	                } catch (IOException e) {
            	                    e.printStackTrace();
            	                
            	                }
            	        	}
            	        	guess=null;
            	        }                     }
                };

                while (true) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                    }

                    // UI update is run on the Application thread
                    Platform.runLater(updater);
                }
            }

        });
        // don't let thread prevent JVM shutdown
        guessThread.setDaemon(true);
        guessThread.start();
        enableButtons();
        guessing = false;
        
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
        if(!guessing && player.getTurn()) {
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
        if(!player.getTurn()) {
        	//This case should never be reached
            //if not their turn
            turn.setText("It is your turn \nto ask a question.");
            System.out.println("^^^^^^^^^^^^^^^^^^^^ 1 " + endTurn.isDisabled());
            enableButtons();
            System.out.println("^^^^^^^^^^^^^^^^^^^^ 2 " + endTurn.isDisabled());
            System.out.println("TURN BUTTON 1 ################# "+player.getTurn());
            
            //turnCorrect=false;

        } else { //if it IS their turn, make it not their turn
        	//TODO
        	// this case should be when receiving a message from the server
            turn.setText("It is the other \nplayer's turn to \nask a question.");
            System.out.println("^^^^^^^^^^^^^^^^^^^^ 1 " + endTurn.isDisabled());
            disableButtons();
            System.out.println("^^^^^^^^^^^^^^^^^^^^ 2 " + endTurn.isDisabled());
            game.endTurn();
            System.out.println("TURN BUTTON 2 ################# "+player.getTurn());
            //set players turn to true? this might go in a different file
            turnCorrect=false;
            Controller.network.send(new Data("DATA",new TurnUpdate("turnUpdate",true)));

        }
        
    }
    
    public void startTurn() {
    	//TODO
        turn.setText("It is your turn \nto ask a question.");
        enableButtons();
        System.out.println("TURN START ################# "+player.getTurn());
        turnCorrect=true;
        Controller.network.send(new Data("DATA",new TurnUpdate("turnUpdate",false)));

    }
    
    public void turnEnd() {
    	turn.setText("It is the other \nplayer's turn to \nask a question.");
    	//THIS IS BEING CALLED BUT NOT WORKING!!!
    	System.out.println("turnend^^^^^^^^^^^^^^^^^^^^ 1 " + endTurn.isDisabled());
        disableButtons();
        System.out.println("turnend^^^^^^^^^^^^^^^^^^^^ 2 " + endTurn.isDisabled());
        System.out.println("TURN THREAD ################# "+player.getTurn());
        turnCorrect=true;
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
        //Opens confirmation menu if the card is clicked on and isn't greyed out
        if(greyedOutCards.containsKey(imageId) && !greyedOutCards.get(imageId)) {
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
            if(greyedOutCards.containsKey(imageId) && hovering && !greyedOutCards.get(imageId)) {
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
     * Helper method for stopGuessing. Allows it to be called outside this class.
     */
    public void stopGuessingHelper() {
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
    
    private void openNewRoundWindow() throws IOException {
        Stage thisStage = (Stage) ((Node) scoresBox).getScene().getWindow();
        Stage confirmationWindow = new Stage();
        confirmationWindow.initStyle(StageStyle.UNDECORATED);
        gameStage = thisStage;
        
        confirmationWindow.initModality(Modality.APPLICATION_MODAL);
        confirmationWindow.getIcons().add(new Image("application/icon.png"));
        confirmationWindow.setResizable(false);

        Parent root = FXMLLoader.load(getClass().getResource("NewRoundScreen.fxml"));
        root.setStyle("-fx-background-color: white; -fx-border-color: black");
        Scene scene = new Scene(root);
        confirmationWindow.setScene(scene);
        dragScreen(scene, confirmationWindow);
        confirmationWindow.show(); 
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
        Stage thisStage = (Stage) ((Node) scoresBox).getScene().getWindow();
        gameStage = thisStage;
        System.out.println("Quitting...");
        NewRoundScreenController.quitButtonPressed = true;
        PlayerGamecodeScreenController.setIsReady(false);
        cardsAdded = true;
        
        //Resetting player data and hashmap
        player.reset();
        resetHashmap();      
        
        Network.close();
        //Going to invite players screen
        try {
            //Loads the new screen
            Parent gameParent = FXMLLoader.load(getClass().getResource("InvitePlayers.fxml"));
            Scene gameScene = new Scene(gameParent);
            
            //Finds the previous screen and switches off of it
            Stage appStage = new Stage();
            appStage.setScene(gameScene);
            appStage.centerOnScreen();
            appStage.setTitle("Guess Who");
            appStage.getIcons().add(new Image("application/icon.png"));
            appStage.initStyle(StageStyle.UNDECORATED);
            
            //Allows it to be dragged
            dragScreen(gameScene, appStage);
            
            //Shows the new screen
            appStage.show();
            gameStage.close();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
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
    protected void disableButtons() {
        guessButton.setDisable(true);
        endTurn.setDisable(true);
    }
    
    /**
     * 
     */
    public void chat() { 
        //Called when user presses enter inside the chatbox. Puts what they
        //typed into the chat
        String msg = chatInput.getText();
        Controller.network.send(new Data("DATA",new Chat("chat",msg)));
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
    
    /**
     * Resets the cards to all be back to their original image when a new round starts,
     * so that none of them are greyed out.
     */
    public void resetGrey() {
        int imageId = 0;
        ListIterator<javafx.scene.Node> iterator = cardGrid.getChildren().listIterator(0);
        while(iterator.hasNext()) {
            Node next = iterator.next();
            if(next instanceof ImageView) {
                ((ImageView) next).setImage(new Image("application/"+deck.getCard(imageId).getImagePath()));
                imageId++;
            }
        }
        
    }
    
    public static void receiveMsg(String msg) {
    	message = msg;
    }
        
    public static void setTurnCorrect(boolean turn) {
    	turnCorrect = turn;
    }
    
    public void setController(GameplayScreenController cont) {
        controller = cont;
    }
    
    public static GameplayScreenController getController() {
        return controller;
    }
    
    public static void setGuess(Guess g) {
    	guess = g;
    }

    
    public static void add_hist(String msg) {
    	controller.chatArea.appendText(player.getName() + " " + msg + "\n");
    	Controller.network.send(new Data("DATA",new Chat("chat",msg)));

    }
    
    /**
     * Sets the players card
     * @param card The player's new card
     */
    public void setCard(Card card) {
        String cardPath = "application/" + card.getImagePath();
        Image image = new Image(cardPath);
        yourCard.setImage(image);
        yourCardName.setText(card.getName());
    }
   
}
