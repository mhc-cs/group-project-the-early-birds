package Messages;

/**
 * Hello (Message)
 * Message sent to server to initiate connection
 * 
 * @author Hannah, Dani, Anna
 *
 */
public class Hello extends Message{
	
	//player name
	private String name;
	
	//game name
	private String gamename;

	/**
	 * Hello message constructor
	 * @param TYPE (should be 'HELLO')
	 * @param name
	 * @param gamename
	 */
	public Hello(String TYPE, String name, String gamename) {
		super(TYPE);
		this.name = name;
		this.gamename = gamename;
	}
	
	/**
	 * get player name
	 * @return name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * set player name
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * get game name
	 * @return gamename
	 */
	public String getGamename() {
		return gamename;
	}
	
	/**
	 * set game name
	 * @param gamename
	 */
	public void setGamename(String gamename) {
		this.gamename = gamename;
	}
	
	/**
	 * Hello message to String
	 */
	public String toString() {
		return "Mine: {TYPE: " + TYPE + ", name: " + name + ", gamename: " + gamename + "}";
	}

}
