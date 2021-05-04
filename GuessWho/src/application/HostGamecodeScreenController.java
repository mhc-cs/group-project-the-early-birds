package application;

import java.io.IOException;

import Messages.Join_Game;
import guesswho.Controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * The screen for the host to enter their gamecode.
 * Has methods for the continue button, the button to go back, and a method
 * to retrieve the gamecode.
 * 
 * @author Dani, Hannah, Anna
 *
 */
public class HostGamecodeScreenController {

    @FXML
    private TextField gamecode;
    
    private static String code;
    
    /**
     * Sets the gamecode. Happens when continue button is pressed.
     * @param event The action of pressing the button. Allows us to know where the
     * button press came from, and therefore which scene the program came from.
     */
    public void continueButton(ActionEvent event) {
        code = gamecode.getText();
        System.out.println("Entered gamecode: "+code);

        Controller.getGame().setGameCode(code);
        Controller.getGame().setStatus("S");
        Controller.network.send(new Join_Game("JOIN_GAME", 2, false, "S", code));
        
        //should only be able to continue when join has been recieved, need to do error handling otherwise
        
        //TODO connect players with gamecode
        
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
    
    /**
     * Returns the gamecode the host entered
     * @return gamecode the host entered
     */
    public static String getHostCode() {
        return code;
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
