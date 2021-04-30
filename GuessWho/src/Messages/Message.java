package Messages;

public class Message {
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
	 * @return
	 */
	public String getType() {
		return TYPE;
	}
	
	public void setType(String TYPE) {
		this.TYPE = TYPE;
	}
	
	public String toString() {
		return "Mine: {TYPE: " + TYPE + "}";
	}
	
}

//TODO create child classes for types JOIN_GAME, DATA (seen in make_move method) 
//and DATA which contains message data, WELCOME, ERROR, GUESS, CORRECT GUESS, INCORRECT GUESS
//Each class needs: (the type for each will be the name of the class in all caps)
//constuctor
//getter
//setter
//toString