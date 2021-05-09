package Messages;

/**
 * Chat (Message)
 * Message containing chat sent from player to player
 * should be used inside a DATA message
 * 
 * @author Hannah, Dani, Anna
 *
 */
public class Chat extends Message{
	
	//Chat message text
	private String entrytext;
	
	/**
	 * Chat Message constructor
	 * @param TYPE (should be 'chat')
	 * @param entrytext
	 */
	public Chat(String TYPE, String entrytext) {
		super(TYPE);
		this.entrytext = entrytext;
	}
	
	/**
	 * get entrytext
	 * @return entrytext
	 */
	public String getEntrytext() {
		return entrytext;
	}
	
	/**
	 * set entrytext
	 * @param entrytext
	 */
	public void setEntrytext(String entrytext) {
		this.entrytext = entrytext;
	}
	
	/**
	 * Chat message to String
	 */
	public String toString() {
		return "Mine: {TYPE: " + TYPE + ", entrytext: " + entrytext + "}";
	}

}
