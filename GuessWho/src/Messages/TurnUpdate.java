package Messages;

/**
 * TurnUpdate (Message)
 * Message containing what to set player's turn to
 * Should be used inside a DATA message
 * 
 * @author Hannah, Dani, Anna
 *
 */
public class TurnUpdate extends Message{
	
	//if it should be the receiving player's turn
	private boolean yourTurn;
	
	/**
	 * Turn update message constructor
	 * @param TYPE (should be 'turnUpdate')
	 * @param yourTurn
	 */
	public TurnUpdate(String TYPE, boolean yourTurn) {
		super(TYPE);
		this.yourTurn = yourTurn;
	}
	
	/**
	 * get your turn
	 * @return yourTurn
	 */
	public boolean getYourTurn() {
		return yourTurn;
	}
	
	/**
	 * set your turn
	 * @param yourTurn
	 */
	public void setYourTurn(boolean yourTurn) {
		this.yourTurn = yourTurn;
	}
	
	/**
	 * turn update message to string
	 */
	public String toString() {
		return "Mine: {TYPE: " + TYPE + ", yourTurn: " + yourTurn + "}";
	}

}
