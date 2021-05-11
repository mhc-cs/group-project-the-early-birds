package guesswho;

import java.util.ArrayList;
import java.util.Random;
import Messages.*;
import Messages.Error;
import application.GameplayScreenController;
import application.HostGamecodeScreenController;
import application.NewRoundScreenController;
import application.PlayerGamecodeScreenController;

/**
 * Manages game logic, player information and actions, and the card desk
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

	//store other player's name
	private String player2Name = "[NAME]";
	
	//stores other player's score
	private int player2Score = 0;
	
	//if welcome received
	boolean receivedWelcome = false;
	boolean badname = false;
	
	String winner = "";
	
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
			Controller.network.send(new Data("DATA",new Cards("cards", c1, c2)));
		}
	}
	
	//TODO REDRAW
	/**
	 * redraw cards for both players
	 * called by either player
	 */
	public void redrawCards() {
		if (player1.getHost()) {
			drawCards();
		} else {
			Controller.network.send(new Data("DATA",new Message("redraw")));
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
		    	Controller.network.send(new Data("DATA",new TurnUpdate("turnUpdate", false)));
		    } else {
		    	//tell other player to set turn true
		    	Controller.network.send(new Data("DATA", new TurnUpdate("turnUpdate", true)));
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
		player1.setTurn(false);
		//tell other player to set turn true
    	Controller.network.send(new Data("DATA",new TurnUpdate("turnUpdate", true)));
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
			if(c.getName().equals(player2Card.getName())) {
				player1.incScore();
				//send message that score updated and player wins
				Controller.network.send(new Data("DATA",new Guess("guess",c,true,player1.getScore())));
				winner = player1.getName();
				return true;
			}
			else {
				//send message that player guessed incorrectly
				Controller.network.send(new Data("DATA",new Guess("guess",c,false,player1.getScore())));
				GameplayScreenController.add_hist("guessed " + c.getName() + " but it was incorrect!");
				return false;
			}
		}
		return false;
	}
	
	/**
	 * Getter for recievedWelcome
	 * @return true if recieved WELCOME message from server, false otherwise
	 */
	public Boolean welcomed() {
		return receivedWelcome;
	}
	
	/**
	 * Getter for badname
	 * @return true if badname error recieved, false otherwise
	 */
	public Boolean badname() {
		return badname;
	}
	
	public void setBadName(Boolean badname) {
		this.badname = badname;
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
	 * set player 2 score
	 * @param score the score to set it to
	 */
	public void setPlayer2Score(int score) {
	    player2Score = score;
	}
	
	/**
	 * get name of winner
	 * @return winner
	 */
	public String getWinner() {
		return winner;
	}
	
	/**
	 * This will process and handle incoming messages from the server
	 * @param msgs ArrayList of incoming messages
	 * @param msgs ArrayList of incoming message
	 */
	public void process(ArrayList<Message> msgs) {
		if (msgs.isEmpty()) {
			return;
		}
		for (int i =0; i < msgs.size(); i++ ) {
			Message msg = msgs.get(i);
			//Handles message sent from server when game connects to server
			if (msg.getType().equals("HELLO")) {
//				Controller.network.send(new Hello("HELLO", player1.getName(), "guesswho"));
			}
			//Handles message sent from server when server receives hello message
			else if (msg.getType().equals("WELCOME" )) {
				this.receivedWelcome = true;
				badname = false;
				//TODO ??
//				Controller.network.send(new Join_Game("JOIN_GAME", 2, false, status, gamecode));
			}
			//Handles error message sent at various stages of connection process
			else if (msg.getType().equals("ERROR")) {
				Error errorMsg = (Error) msg;
				// sent from server if name is nonexistent or if it's a duplicate
				if (errorMsg.getErr().equals("BADNAME")) {
					badname = true;
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
				}	
			}
			//Handles message sent when someone leaves the room
			else if (msg.getType().equals("LEAVE")) {
				Leave LeaveMsg = (Leave) msg;
				GameplayScreenController.serverMsg(LeaveMsg.getUser()+" has left the game.");
			}
			//Handles message sent when someone joins the room
			else if (msg.getType().equals("JOIN")) {
				//Join joinMsg = (Join) msg;
				//No functionality needed when receive join message
			}
			//Handles room status update
			else if (msg.getType().equals("ROOM_STATUS")) {
				Room_Status roomMsg = (Room_Status) msg;
				for (String name:roomMsg.getUsers()) {
					if (!name.equals(player1.getName())) {
						player2Name = name;
					}
				}
				HostGamecodeScreenController.setIsReady(roomMsg.getIs_ready());
			}
			//Handles message from host when drawing cards
			else if (msg.getType().equals("cards")) {
				player2Card = ((Cards)msg).getMyCard();
				player1.setCard(((Cards)msg).getYourCard());
				PlayerGamecodeScreenController.setIsReady(true);
				NewRoundScreenController.setIsReady(true);
			}
			//TODO REDRAW
			//Handles message from player2 requesting redraw
			else if (msg.getType().equals("redraw")) {
				drawCards();
			}
			//Handles continue message
			else if (msg.getType().equals("continue")) {
				NewRoundScreenController.setIsReady(true);
			}
			//Handles turn updates
			else if (msg.getType().equals("turnUpdate")) {
				player1.setTurn(((TurnUpdate)msg).getYourTurn());
				GameplayScreenController.setTurnCorrect(false);
			}
			//Handles guesses
			else if (msg.getType().equals("guess")) {
				if (((Guess)msg).getCorrect()) {
					winner = player2Name;
					player2Score = ((Guess)msg).getScore();
					GameplayScreenController.setGuess((Guess)msg);
				}
			}
			//Handles chat messages
			else if (msg.getType().equals("chat")) {
				GameplayScreenController.receiveMsg(((Chat)msg).getEntrytext());
			}
		}
	}
	
}
