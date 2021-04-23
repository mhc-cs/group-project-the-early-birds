package guesswho;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Game
 * has a deck and two players with functions for game actions
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
	
	/**
	 * Game constructor
	 * @param d deck of cards
	 * @param p1 self
	 */
	public Game(Deck d, Player p1, Player p2) {
		deck = d;
		player1 = p1;
	}
	
	/*
	 * draw cards for both players
	 * cannot both have the same card
	 */
	public void drawCards() {
		Card c1 = deck.drawRandomCard();
		//send to other player
		//receive other players card
		player2Card = null; //store other players card
		while( c1 == player2Card){
			drawCards(); //I think this will work??
		}
		player1.setCard(c1);
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
		    } else {
		    	//tell other player to set turn true
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
	}
	
	/**
	 * Guess other player's card
	 * increase score if cards match
	 * must be guessing player's turn
	 * if incorrect end turn
	 * @param c card to guess
	 */
	public void guess(Card c) {
		if (player1.getTurn()){
			if(c==player2Card) {
				player1.incScore();
				//send message that score updated and player wins
			}else {
				endTurn();
			}
		}
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
	public void handle_msg (boolean privateMsg, String sender, boolean spectactor, String kind, HashMap<String,String> msg) {
		if (kind == "CHAT") {
			Network.add_hist(sender + ": " + msg.get("msg"));
		}
		//will need to write handlers for all kinds of messages we send between games
	}
	
	/*
	 * Under construction
	 * This will process incoming messages from the server that handle connection process
	 */
	public void process(HashMap<String,String> msgs) {
		// this code is not right yet, needs to iterate through messages 
		// handle each one
//		for (Map.Entry<String,String> msg : msgs.entrySet()) {
//			try {
//				if( msgs.get("TYPE") == "CONNECT") {
//					
//				}
//			}
//			catch (IOException e) {
//				System.out.print("temp");
//			}
//		}
	}
	
}
