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
	    	player2.setTurn(false);
	    } else {
	    	player2.setTurn(true);
	    	player1.setTurn(false);
	    }
	}
	
	public void toggleGreyCard(int index) {
		Card c = deck.getCard(index);
		c.toggleGrey();
	}
	
	public void endTurn() {
		player1.toggleTurn();
		player2.toggleTurn();
	}
	
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
