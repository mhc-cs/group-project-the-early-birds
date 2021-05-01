package Messages;
import guesswho.Card;

public class Cards extends Data{
	private Card myCard;
	private Card yourCard;
	
	public Cards(String TYPE, String type, Card myCard, Card yourCard) {
		super(TYPE, type);
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
		return "Mine: {TYPE: " + TYPE + ", type: " + type + ", myCard: " + myCard + ", yourCard: " + yourCard + "}";
	}

}
