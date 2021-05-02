package Messages;

public class TurnUpdate extends Message{
	private boolean yourTurn;
	
	public TurnUpdate(String TYPE, boolean yourTurn) {
		super(TYPE);
		this.yourTurn = yourTurn;
	}
	
	public boolean getYourTurn() {
		return yourTurn;
	}
	
	public void setYourTurn(boolean yourTurn) {
		this.yourTurn = yourTurn;
	}
	
	public String toString() {
		return "Mine: {TYPE: " + TYPE + ", yourTurn: " + yourTurn + "}";
	}

}
