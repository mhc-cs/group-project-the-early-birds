package Messages;

/**
 * Join_Game (Message)
 * Message containing parameters for starting/joining a game room
 * 
 * @author Hannah, Dani, Anna
 *
 */
public class Join_Game extends Message{
	
	//number of players in game room
	private int size;
	
	//if spectators are allowed in game room
	private Boolean allow_spectators;
	
	//if starting or joining room
	private String status;
	
	//game code to join room
	private String gamecode;

	/**
	 * Join_Game message constructor
	 * @param TYPE (should be 'JOIN_GAME')
	 * @param size (should be 2)
	 * @param allow_spectators (should be false)
	 * @param status (either 'S' for start or 'J' for join)
	 * @param gamecode
	 */
	public Join_Game(String TYPE, int size, Boolean allow_spectators, String status, String gamecode) {
		super(TYPE);
		this.size = size;
		this.allow_spectators = allow_spectators;
		this.status = status;
		this.gamecode = gamecode;
	}
	
	/**
	 * get size
	 * @return size
	 */
	public int getSize() {
		return size;
	}
	
	/**
	 * set size
	 * @param size
	 */
	public void setSize(int size) {
		this.size = size;
	}
	
	/**
	 * get allow spectators
	 * @return allow_spectators
	 */
	public Boolean getAllow_spectators() {
		return allow_spectators;
	}
	
	/**
	 * set allow spectators
	 * @param allow_spectators
	 */
	public void setAllow_spectators(Boolean allow_spectators) {
		this.allow_spectators = allow_spectators;
	}
	
	/**
	 * get status
	 * @return status
	 */
	public String getStatus() {
		return status;
	}
	
	/**
	 * set status
	 * @param status
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	
	/**
	 * get gamecode
	 * @return gamecode
	 */
	public String getGamecode() {
		return gamecode;
	}
	
	/**
	 * set gamecode
	 * @param gamecode
	 */
	public void setGamecode(String gamecode) {
		this.gamecode = gamecode;
	}
	
	/**
	 * join game message to string
	 */
	public String toString() {
		return "Mine: {TYPE: " + TYPE + ", size: " + size + ", allow_spectators: " + allow_spectators + ", status: " + status + ", gamecode: " + gamecode + "}";
	}
}
