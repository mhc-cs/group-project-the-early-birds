package Messages;

import guesswho.Card;

/**
 * Cards (Message)
 * Message containing the cards drawn for both players
 * should be used inside a DATA message
 * 
 * @author Hannah, Dani, Anna
 *
 */
public class Cards extends Message{
	
	//Card drawn by player sending the message
	private Card myCard;
	
	//Card drawn for player receiving the message
	private Card yourCard;
	
	/**
	 * Cards Message Constructor
	 * @param TYPE (should be 'cards')
	 * @param myCard
	 * @param yourCard
	 */
	public Cards(String TYPE, Card myCard, Card yourCard) {
		super(TYPE);
		this.myCard = myCard;
		this.yourCard = yourCard;
	}
	
	/**
	 * Get my card
	 * @return myCard
	 */
	public Card getMyCard() {
		return myCard;
	}
	
	/**
	 * set my card
	 * @param myCard
	 */
	public void setMyCard(Card myCard) {
		this.myCard = myCard;
	}
	
	/**
	 * get your card
	 * @return yourCard
	 */
	public Card getYourCard() {
		return yourCard;
	}
	
	/**
	 * set your card
	 * @param yourCard
	 */
	public void setYourCard(Card yourCard) {
		this.yourCard = yourCard;
	}
	
	/**
	 * Cards message to string
	 */
	public String toString() {
		return "Mine: {TYPE: " + TYPE + ", myCard: " + myCard + ", yourCard: " + yourCard + "}";
	}

}
