package application;

import java.io.IOException;

import Messages.Join_Game;
import guesswho.Controller;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

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
    
    @FXML
    private Label warning;
    
    private static String code;
    
    private static boolean isReady;
    
    private Stage window = new Stage();
    
    protected static GameplayScreenController controller;
    
    /**
     * Waiting for player screen. This shows when one player is in
     * the room and the other has not joined yet.
     * @throws IOException 
     */
    private void waitingForPlayer(Stage waitingWindow) throws IOException {
        
//        // Makes it so you can't click on the window behind until this one is closed.
//        waitingWindow.initModality(Modality.APPLICATION_MODAL);
//        waitingWindow.setResizable(false);
//        waitingWindow.initStyle(StageStyle.UNDECORATED);
//        
//        //Adding Title
//        Label text = new Label();
//        text.setText("Waiting for other player...");
//        text.setFont(Font.font("Century Gothic", 23));
//        text.setPadding(new Insets(15,0,0,0));
//
//        //Progress wheel
//        ProgressIndicator progress = new ProgressIndicator();
//        
//        Button close = new Button();
//        close.setText("Stop attempting connection");
//        close.setOnAction(e -> stopWaitingForPlayers(waitingWindow));
//        close.setStyle("-fx-background-color: #c4c4c4");
//        close.setFont(Font.font("Century Gothic", 10));
//        
//        //Add hover property to button
//        close.hoverProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean hovering) -> {
//            if(hovering) {
//                close.setStyle("-fx-background-color: #a6a6a6");
//            } else {
//                close.setStyle("-fx-background-color: #c4c4c4");
//            }
//        });
//        
//        VBox root = new VBox(25);
//        root.getChildren().addAll(text, progress, close);
//        root.setAlignment(Pos.CENTER);
//        root.setStyle("-fx-background-color: white; -fx-border-color: black");
//
//        
//        // Display the scene
//        Scene scene = new Scene(root, 350, 200);
//        waitingWindow.setScene(scene);
//        waitingWindow.show();
        
        //Stage confirmationWindow = new Stage();
        waitingWindow.initStyle(StageStyle.UNDECORATED);
        
        waitingWindow.initModality(Modality.APPLICATION_MODAL);
        waitingWindow.getIcons().add(new Image("application/icon.png"));
        waitingWindow.setResizable(false);

        Parent root = FXMLLoader.load(getClass().getResource("WaitingForPlayers.fxml"));
        root.setStyle("-fx-background-color: white; -fx-border-color: black");
        Scene scene = new Scene(root);
        waitingWindow.setScene(scene);
        waitingWindow.showAndWait(); 
    }
    
    /**
     * Closes the waiting window dialog.
     */
    private void closeWaitingWindow(Stage waitingWindow) {
        waitingWindow.close();
    }
    
    /**
     * Sets the gamecode. Happens when continue button is pressed.
     * @param event The action of pressing the button. Allows us to know where the
     * button press came from, and therefore which scene the program came from.
     * @throws IOException 
     */
    public void continueButton(ActionEvent event) throws IOException {
        //should only be able to continue when join has been received, need to do error handling otherwise

        if(gamecode.getText().isEmpty()) {
            warning.setText("Please enter a gamecode.");
        } else {
            code = gamecode.getText();
            System.out.println("Entered gamecode: "+code);

            Controller.getGame().setGameCode(code);
            Controller.getGame().setStatus("S");
            Controller.network.send(new Join_Game("JOIN_GAME", 2, false, "S", code));
            Thread waitingThread = new Thread("Waiting Thread") {
                public void run(){
                    try {
                      boolean runThread = true;
                      while (runThread) {
                          if(isReady) {
                          Platform.runLater(() -> {
                          closeWaitingWindow(window);  
                      try {
                          //Loads the new screen
                          Parent startGameParent = FXMLLoader.load(getClass().getResource("GameplayScreen.fxml"));
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
                              });
                          runThread = false;
                          }
                          Thread.sleep(500);
                      }
                  } catch (InterruptedException e) {
                      Thread.currentThread().interrupt();
                      System.out.println("Thread was interrupted, Failed to complete operation");
                  }
                }
                
             };
             
             waitingThread.start();
          
              if(isReady) {
                  closeWaitingWindow(window);
                  try {
                      //Loads the new screen
                      FXMLLoader loader = new FXMLLoader(getClass().getResource("GameplayScreen.fxml"));
                      Parent startGameParent = loader.load();
                      controller = loader.getController();
                      //Parent startGameParent = FXMLLoader.load(getClass().getResource("GameplayScreen.fxml"));
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
              }else {
                  waitingForPlayer(window);
              }
        }
        
    }
    
    /**
     * Returns the gamecode the host entered
     * @return gamecode the host entered
     */
    public static String getHostCode() {
        return code;
    }
    
    public static void setIsReady(boolean ready) {
    	isReady = ready;
    }
    
    /**
     * Called when the user presses the button to stop attempting
     * connection on the waiting for players screen. Closes the screen and
     * stops trying to connect.
     */
    private void stopWaitingForPlayers(Stage waitingWindow) {
        waitingWindow.close();
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
