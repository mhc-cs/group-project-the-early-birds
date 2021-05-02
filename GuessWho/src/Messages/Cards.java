package Messages;
import guesswho.Card;

public class Cards extends Message{
	private Card myCard;
	private Card yourCard;
	
	public Cards(String TYPE, Card myCard, Card yourCard) {
		super(TYPE);
		this.myCard = myCard;
		this.yourCard = yourCard;
	}
	
	public Card getMyCard() {
		return myCard;
	}
	
	public void setMyCard(Card myCard) {
		this.myCard = myCard;
	}
	
	public Card getYourCard() {
		return yourCard;
	}
	
	public void setYourCard(Card yourCard) {
		this.yourCard = yourCard;
	}
	
	public String toString() {
		return "Mine: {TYPE: " + TYPE + ", myCard: " + myCard + ", yourCard: " + yourCard + "}";
	}

}
