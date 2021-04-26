package application;

import java.io.IOException;

import guesswho.Controller;
import guesswho.Game;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class HostGamecodeScreenController {

    @FXML
    private TextField gamecode;
    
    private static String code;
    
    /**
     * Sets the gamecode
     */
    public void setHostCode() {
        code = gamecode.getText();
        System.out.println(code);
        Controller.getGame().setGameCode(code);
        Controller.getGame().setStatus("S");
        
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
            
            //Shows the new screen
            appStage.show();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
