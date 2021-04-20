package guesswho;

import java.util.Random;

public class Game {

	private Deck deck;
	private Player player1;
	private Player player2;
	
	public Game(Deck d, Player p1, Player p2) {
		deck = d;
		player1 = p1;
		player2 = p2;	
	}
	
	public void drawCards() {
		Card c1 = deck.drawRandomCard();
		Card c2 = deck.drawRandomCard();
		while( c1 == c2){
			c1 = deck.drawRandomCard();
		}
		player1.setCard(c1);
		player2.setCard(c2);
	}
	
	public void assignFirstTurn() {
		Random r = new Random();
	    int chance = r.nextInt(2);
	    if (chance == 1) {
	    	player1.setTurn(true);
	    } else {
	    	player2.setTurn(true);
	    }
	}
	
	public void toggleGreyCard(int index) {
		Card c = deck.getCard(index);
		c.toggleGrey();
	}
	
	
}
