package guesswho;

/**
 * Player
 * Represents a player with a name who can be assigned a card
 * and has booleans for turn indicator and host status. 
 * 
 * @author Hannah, Dani, Anna
 *
 */

public class Player {
	//name of player
	private String playerName;
	
	//Player's card for the round
	private Card playerCard;
	
	//if it is the player's turn
	private boolean isTurn;
	
	//if the player is the host
	private boolean isHost;
	
	//players score
	private int score;
	
	/**
	 * Player Constructor
	 * @param name of player
	 * @param host boolean if player is host
	 */
	public Player(String name, boolean host) {
		playerName = name;
		playerCard = null;
		isTurn = true;
		isHost = host;
		score = 0;
	}
	
	/**
	 * set card
	 * @param newCard to assign player
	 */
	public void setCard(Card newCard) {
		playerCard = newCard;
	}
	
	/**
	 * get card
	 * @return player's card
	 */
	public Card getCard() {
		return playerCard;
	}
	
	/**
	 * get name
	 * @return player's name
	 */
	public String getName() {
		return playerName;
	}
	
	/**
	 * set name
	 * @param name the name to change to
	 */
	public void setName(String name) {
	    playerName = name;
	}
	
	/**
	 * get turn
	 * @return boolean if is turn
	 */
	public boolean getTurn() {
		return isTurn;
	}
	
	/**
	 * set turn
	 * @param turn boolean if is turn
	 */
	public void setTurn(boolean turn) {
		isTurn = turn;
	}
	
	/**
	 * toggle turn
	 * if true becomes flase and vice versa
	 */
	public void toggleTurn() {
		isTurn ^= true;
	}
	
	/**
	 * get host
	 * @return boolean if is host
	 */
	public boolean getHost() {
		return isHost;
	}
	
	/**
	 * set host
	 * @param host if they're the host
	 */
	public void setHost(boolean host) {
	    isHost = host;
	}
	
	/**
	 * get score
	 * @return players score
	 */
	public int getScore() {
		return score;
	}
	
	/**
	 * increase score by 1
	 */
	public void incScore() {
		score++;
	}
	
	/**
	 * reset player
	 * clear score and card
	 */
	public void reset() {
		playerCard = null;
		score = 0;
		isTurn = true;
	}
}
