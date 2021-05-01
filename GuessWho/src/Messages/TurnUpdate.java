package Messages;

public class TurnUpdate extends Data{
	private boolean yourTurn;
	
	public TurnUpdate(String TYPE, String type, boolean yourTurn) {
		super(TYPE, type);
		this.yourTurn = yourTurn;
	}
	
	public boolean getYourTurn() {
		return yourTurn;
	}
	
	public void setYourTurn(boolean yourTurn) {
		this.yourTurn = yourTurn;
	}
	
	public String toString() {
		return "Mine: {TYPE: " + TYPE + ", type: " + type + ", yourTurn: " + yourTurn + "}";
	}

}
