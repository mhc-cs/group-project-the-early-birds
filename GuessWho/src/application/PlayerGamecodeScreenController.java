package application;

import java.io.IOException;

import Messages.Join_Game;
import guesswho.Controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * The screen for the player to enter their gamecode.
 * Has methods for the continue button, the button to go back, and a method
 * to retrieve the gamecode.
 * 
 * @author Dani, Hannah, Anna
 *
 */
public class PlayerGamecodeScreenController {
    
    @FXML
    private TextField gamecode;
    
    private static String code;
    
    private static boolean isReady;
    
    /**
     * Waiting for player screen. This shows when one player is in
     * the room and the other has not joined yet.
     */
    private void waitingForPlayer(Stage waitingWindow) {
        
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
        waitingWindow.show();
        
        
    }
    
    /**
     * Closes the waiting window dialog.
     */
    private void closeWaitingWindow(Stage waitingWindow) {
        waitingWindow.close();
    }
    
    /**
     * Sets the gamecode that the player entered.
     * Happens when continue button is pressed.
     * 
     * @param event The action of pressing the button. Allows us to know where the
     * button press came from, and therefore which scene the program came from.
     */
    public void continueButton(ActionEvent event) {
        code = gamecode.getText();
        System.out.println("Entered gamecode: "+code);
        //TODO connect players with gamecode
        
        Controller.getGame().setGameCode(code);
        Controller.getGame().setStatus("S");
        Controller.network.send(new Join_Game("JOIN_GAME", 2, false, "J", code));
        
        boolean otherPlayerJoined = false;
        Stage window = new Stage();
        
        
        if(!isReady) {
            waitingForPlayer(window);
            if(isReady) {
                closeWaitingWindow(window);
            }
        } else {
            //Going to redraw screen
            try {
                //Loads the new screen
                Parent startGameParent = FXMLLoader.load(getClass().getResource("RedrawScreen.fxml"));
                Scene startGameScene = new Scene(startGameParent);
                
                //Finds the previous screen and switches off of it
                Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                appStage.setScene(startGameScene);
                appStage.centerOnScreen();
                
                //Allows it to be dragged
                Controller.dragScreen(startGameScene, appStage);
                
                //Shows the new screen
                appStage.show();
                
            } catch (IOException e) {
                e.printStackTrace();

        }
}
    }
    
    /**
     * Gets the gamecode that the player entered
     * @return gamecode the player entered
     */
    public static String getPlayerCode() {
        return code;
    }
    
    public static void setIsReady(boolean ready) {
    	isReady = ready;
    }
    
    /**
     * Goes back to the invite players screen.
     * 
     * @param event The action of pressing the button. Allows us to know where the
     * button press came from, and therefore which scene the program came from.
     */
    public void goBack(ActionEvent event) {
        try {
            //Loads the new screen
            Parent invitePlayersParent = FXMLLoader.load(getClass().getResource("InvitePlayers.fxml"));
            Scene invitePlayersScene = new Scene(invitePlayersParent);
            
            //Finds the previous screen and switches off of it
            Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            appStage.setScene(invitePlayersScene);
            
            //Allows it to be dragged
            Controller.dragScreen(invitePlayersScene, appStage);
            
            //Shows the new screen
            appStage.show();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
