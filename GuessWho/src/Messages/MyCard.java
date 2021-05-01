package Messages;
import guesswho.Card;

public class MyCard extends Data{
	private Card myCard;
	
	public MyCard(String TYPE, String type, Card myCard) {
		super(TYPE, type);
		this.myCard = myCard;
	}
	
	public Card getMyCard() {
		return myCard;
	}
	
	public void setMyCard(Card myCard) {
		this.myCard = myCard;
	}
	
	public String toString() {
		return "Mine: {TYPE: " + TYPE + ", type: " + type + ", myCard: " + myCard + "}";
	}

}
