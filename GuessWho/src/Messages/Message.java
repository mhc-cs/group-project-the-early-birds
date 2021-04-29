package Messages;

public class Message {
	private String TYPE;
	private String name;
	private String gamename;
	
	/**
	 * Constructor for Message class
	 * @param TYPE String representation of message type, 
	 * used to help recipient handle incoming message correctly
	 */
	public Message(String TYPE, String name, String gamename) {
		this.TYPE = TYPE;
		this.name = name;
		this.gamename = gamename;
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
		return "Mine: {TYPE: " + TYPE + ", name: " + name + ", gamename: " + gamename + "}";
	}
	
}

//TODO create child classes for types HELLO, JOIN_GAME, DATA (seen in make_move method) 
//and DATA which contains message data
//Each class needs: (the type for each will be the name of the class in all caps)
//constuctor
//getter
//setter
//toString