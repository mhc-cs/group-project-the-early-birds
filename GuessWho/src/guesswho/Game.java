package guesswho;

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
	
}
