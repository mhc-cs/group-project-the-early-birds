package guesswho;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.google.gson.*;

import guesswho.Controller;
import Messages.*;
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
	//store other players name? might go in dif file?
	private String player2Name = "[NAME]";
	//same thing with score
	private int player2Score = 3;
	
	/**
	 * Game constructor
	 * @param d deck of cards
	 * @param p1 self
	 */
	public Game(Deck d, Player p1) {
		deck = d;
		player1 = p1;
	}
	
	/*
	 * draw cards for both players
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
	 */
	public void guess(Card c) {
		if (player1.getTurn()){
			if(c==player2Card) {
				player1.incScore();
				//send message that score updated and player wins
			}
			else {
				endTurn();
			}
		}
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
	public void handle_msg (boolean privateMsg, String sender, boolean spectactor, String kind, HashMap<String,String> msg) {
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
			if (msg.getType() == "HELLO" ) {
				System.out.println("Recieved hello message");
				Controller.network.send(new Hello("HELLO", "Dani", "guesswho"));
//				Add this once the bit above works
//				Controller.network.send(new Hello("HELLO", player1.getName(), "guesswho"));
			}
			else if (msg.getType() == "WELCOME" ) {
				System.out.println("Recieved welcome message");
//				Controller.network.send(new JOIN_GAME(JOIN_GAME, 2, False, status, gamecode));
//				HashMap<String,String> newMsg = new HashMap<String,String>();
//				msg.put("TYPE", "JOIN_GAME");
//				//this is going to create an issue because 2 needs to be a number
//				msg.put("size", "2");
//				msg.put("allow_spectators", "False");
//				msg.put("status", status);
//				msg.put("gamecode", gamecode);
				
//				Network.send(newMsg);
			}
			else if (msg.getType() == "DATA") {
				System.out.println("Recieved DATA message");
				if (((Data) msg).getDataType() == "cards") {
					System.out.println("Recieved Cards message");
					player2Card = ((Cards)msg).getMyCard();
					player1.setCard(((Cards)msg).getYourCard());
				} 
				else if (((Data) msg).getDataType() == "turnUpdate") {
					System.out.println("Recieved TurnUpdate Message");
					player1.setTurn(((TurnUpdate)msg).getYourTurn());
				}
			}
			
		}
		
		
	}
	
}
