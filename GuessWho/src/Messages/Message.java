package Messages;

/**
 * Message super class
 * 
 * @author Hannah, Dani, Anna
 *
 */
public class Message {
	
	//message type
	String TYPE;
	
	/**
	 * Constructor for Message class
	 * @param TYPE String representation of message type, 
	 * used to help recipient handle incoming message correctly
	 */
	public Message(String TYPE) {
		this.TYPE = TYPE;
		
	}
	
	/**
	 * Getter for TYPE
	 * @return TYPE
	 */
	public String getType() {
		return TYPE;
	}
	
	/**
	 * set TYPE
	 * @param TYPE
	 */
	public void setType(String TYPE) {
		this.TYPE = TYPE;
	}
	
	/**
	 * Message to String
	 */
	public String toString() {
		return "Mine: {TYPE: " + TYPE + "}";
	}
	
}