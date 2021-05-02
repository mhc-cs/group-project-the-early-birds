package guesswho;

import java.util.ArrayList;
import java.util.Random;
import com.google.gson.*;
import Messages.Cards;
import Messages.Chat;
import Messages.Hello;
import Messages.Join;
import Messages.Join_Game;
import Messages.Message;
import Messages.TurnUpdate;
import Messages.Error;
import Messages.Guess;
import Messages.Leave;
import Messages.Data;
import application.GameplayScreenController;
import application.InvitePlayersController;

/**
 * Game
 * has a deck and player with functions for game actions
 * @author Hannah, Dani, Anna
 *
 */
public class Game {

	//deck of cards for game
	private Deck deck;
	
	//player represents self
	private Player player1;
	
	//store other players card
	private Card player2Card = null;
	
	//stores whether or not player is hosting or joining a game
	private String status;
	
	//stores gamecode
	private String gamecode;
	
	//TODO
	//store other players name? get from some message??
	private String player2Name = "[NAME]";
	
	//stores other player's score
	private int player2Score = 3;
	
	boolean receivedWelcome = false;
	
	/**
	 * Game constructor
	 * @param d deck of cards
	 * @param p1 self
	 */
	public Game(Deck d, Player p1) {
		deck = d;
		player1 = p1;
	}
	
	/**
	 * draw cards for both players
	 * only host draws and sends results to player2
	 * cannot both have the same card
	 */
	public void drawCards() {
		if (player1.getHost()) {
			Card c1 = deck.drawRandomCard();
			Card c2 = deck.drawRandomCard();
			while( c1 == c2){
				c2 = deck.drawRandomCard();
			}
			player1.setCard(c1);
			player2Card = c2;
			//send to other player
			Controller.network.send(new Cards("DATA","cards", c1, c2));
		}
	}
	
	/**
	 * redraw cards for both players
	 * called by either player
	 */
	public void redrawCards() {
		if (player1.getHost()) {
			drawCards();
		} else {
			Controller.network.send(new Data("DATA","redraw"));
		}
	}
	
	/**
	 * randomly assign which player goes first
	 */
	public void assignFirstTurn() {
		if (player1.getHost()){
			Random r = new Random();
		    int chance = r.nextInt(2);
		    if (chance == 1) {
		    	player1.setTurn(true);
		    	//tell other player to set turn false
		    	Controller.network.send(new TurnUpdate("DATA","turnUpdate", false));
		    } else {
		    	//tell other player to set turn true
		    	Controller.network.send(new TurnUpdate("DATA","turnUpdate", true));
		    	player1.setTurn(false);
		    }
		}
	}
	
	/**
	 * Grey out a card (or un-grey)
	 * @param index of the card in the deck
	 */
	public void toggleGreyCard(int index) {
		Card c = deck.getCard(index);
		c.toggleGrey();
	}
	
	/**
	 * Change which player's turn it is
	 */
	public void endTurn() {
		player1.toggleTurn();
		//tell other player to set turn true
    	Controller.network.send(new TurnUpdate("DATA","turnUpdate", true));
	}
	
	/**
	 * Guess other player's card
	 * increase score if cards match
	 * must be guessing player's turn
	 * if incorrect end turn
	 * @param c card to guess
	 * @param true if the player guessed correctly, false otherwise
	 */
	public boolean guess(Card c) {
		if (player1.getTurn()){
			if(c==player2Card) {
				player1.incScore();
				//send message that score updated and player wins
				Controller.network.send(new Guess("DATA","guess",c,true,player1.getScore()));
				return true;
			}
			else {
				//send message that player guessed incorrectly
				Controller.network.send(new Guess("DATA","guess",c,false,player1.getScore()));
				endTurn();
				return false;
			}
		}
		return false;
	}
	
	/**
	 * Setter for gamecode
	 * @param gamecode String representation of gamecode
	 */
	public void setGameCode(String gamecode) {
		this.gamecode = gamecode;
	}
	
	/**
	 * Setter for status. Status will either be J (join) or S (start new game/host)
	 * @param status String representation of whether gamecode is used to start a 
	 * new room or join an existing one
	 */
	public void setStatus(String status) {
		this.status = status;
	}

  /**
	 * get player2 name
	 * @return name
	 */
	public String getPlayer2Name() {
		return player2Name;
	}
	
	/**
	 * get player2 score
	 * @return score
	 */
	public int getPlayer2Score() {
		return player2Score;

	}
	
	/**
	 * Still under contruction
	 * This method handles incoming messages from the other player
	 * such as messages that should go in the chat
	 * @param privateMsg true if message only to one person, not to group
	 * @param sender Name of sender
	 * @param spectactor True if sender is spectator, false otherwise
	 * @param kind Type of message
	 * @param msg Content of the message
	 */
	public void handle_msg (boolean privateMsg, String sender, boolean spectactor, String kind, Chat chat) {
		if (kind == "CHAT") {
			//needs to add message to chat
//			GameplayScreenController.chat(sender + ": " + msg.get("msg"));
		}
		
		//will need to write handlers for all kinds of messages we send between games
	}
	
	/**
	 * Under construction
	 * This will process incoming messages from the server that handle parts of the connection process
	 * @param msgs ArrayList of incoming message
	 */
	public void process(ArrayList<Message> msgs) {
		if (msgs.isEmpty()) {
			return;
		}
		for (int i =0; i < msgs.size(); i++ ) {
			System.out.println("Message recieved" + msgs.get(i).getType() + "stop");
			Message msg = msgs.get(i);
			//Handles message sent from server when game connects to server
			if (msg.getType().equals("HELLO")) {
				System.out.println("Recieved hello message");
				Controller.network.send(new Hello("HELLO", player1.getName(), "guesswho"));
			}
			//Handles message sent from server when server recieves hello message
			else if (msg.getType().equals("WELCOME" )) {
				System.out.println("Recieved welcome message");
				receivedWelcome = true;
//				Controller.network.send(new Join_Game("JOIN_GAME", 2, false, status, gamecode));
			}
			//Handles error message sent at various stages of connection process
			else if (msg.getType().equals("ERROR")) {
				Error errorMsg = (Error) msg;
				// sent from server if name is nonexistent or if it's a duplicate
				if (errorMsg.getErr().equals("BADNAME")) {
					System.out.println("Received badname error");
				}
				// sent from server when someone tries to create a new game with
				//a code that already is in use for a game in wait_rooms
				if (errorMsg.getErr().equals("REPEATCODE")) {
					
				}
				//sent from server when user joins with invalid code
				if (errorMsg.getErr().equals("BADCODE")) {
	
				}
				// sent from server if status sent is not J or S
				if (errorMsg.getErr().equals("BADSTATUS")) {
					System.out.println("Received badstatus error");
	
				}	
			}
			//Handles message sent when someone leaves the room
			else if (msg.getType().equals("LEAVE")) {
				Leave LeaveMsg = (Leave) msg;
				
			}
			//Handles message sent when someone joins the room
			else if (msg.getType().equals("JOIN")) {
				Join joinMsg = (Join) msg;
				player2Name = (joinMsg.getUser());
			}
			//Handles room status update
			else if (msg.getType().equals("ROOM_STATUS")) {
				System.out.println("Got ROOM_STATUS");
			}
			
			else if (msg.getType() == "DATA") {
				System.out.println("Recieved DATA message");
				if (((Data) msg).getDataType() == "cards") {
					System.out.println("Recieved Cards message");
					player2Card = ((Cards)msg).getMyCard();
					player1.setCard(((Cards)msg).getYourCard());
				} 
				else if (((Data) msg).getDataType() == "redraw") {
					System.out.println("Recieved Redraw Message");
					drawCards();
				}
				else if (((Data) msg).getDataType() == "turnUpdate") {
					System.out.println("Recieved TurnUpdate Message");
					player1.setTurn(((TurnUpdate)msg).getYourTurn());
				}
				else if (((Data) msg).getDataType() == "guess") {
					System.out.println("Recieved Guess Message");
					player2Score = ((Guess) msg).getScore();
					//TODO 
					//End round when guess correct
					//Put card guessed in chat?
				}
			}
			//Handles any messages not provided with special handling
			else {
				System.out.println("Unprocessed message: " + msg);
			}
			
		}
		
		
	}
	
}
