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
	
	//one player in game
	private Player player1;
	
	//second player in game
	private Player player2;
	
	/**
	 * Game constructor
	 * @param d deck of cards
	 * @param p1 first player
	 * @param p2 second player
	 */
	public Game(Deck d, Player p1, Player p2) {
		deck = d;
		player1 = p1;
		player2 = p2;	
	}
	
	/*
	 * draw cards for both players
	 * cannot both have the same card
	 */
	public void drawCards() {
		Card c1 = deck.drawRandomCard();
		Card c2 = deck.drawRandomCard();
		while( c1 == c2){
			c1 = deck.drawRandomCard();
		}
		player1.setCard(c1);
		player2.setCard(c2);
	}
	
	/**
	 * randomly assign which player goes first
	 */
	public void assignFirstTurn() {
		Random r = new Random();
	    int chance = r.nextInt(2);
	    if (chance == 1) {
	    	player1.setTurn(true);
	    	player2.setTurn(false);
	    } else {
	    	player2.setTurn(true);
	    	player1.setTurn(false);
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
		player2.toggleTurn();
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
			if(c==player2.getCard()) {
				player1.incScore();
				//announce winner player1?
			}else {
				endTurn();
			}
		}else {
			if(c==player1.getCard()) {
				player2.incScore();
				//announce winner player2?
			}else {
				endTurn();
			}
		}
	}
	
}
