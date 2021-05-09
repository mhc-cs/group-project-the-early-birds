package Messages;

/**
 * Leave (Message)
 * Message containing player name and if host, sent when player leaves room
 * 
 * @author Hannah, Dani, Anna
 *
 */
public class Leave extends Message{
	
	//player name
	private String user;
	
	//if player is host
	private String leader;

	/**
	 * Leave message constructor
	 * @param TYPE (should be 'LEAVE')
	 * @param user
	 * @param leader
	 */
	public Leave(String TYPE, String user, String leader) {
		super(TYPE);
		this.user = user;
		this.leader = leader;
	}
	
	/**
	 * get user
	 * @return user
	 */
	public String getUser() {
		return user;
	}
	
	/**
	 * set user
	 * @param user
	 */
	public void setUser(String user) {
		this.user = user;
	}
	
	/**
	 * get leader
	 * @return leader
	 */
	public String getLeader() {
		return leader;
	}
	
	/**
	 * set leader
	 * @param leader
	 */
	public void setLeader(String leader) {
		this.leader = leader;
	}
	
	/**
	 * leave message to string
	 */
	public String toString() {
		return "Mine: {TYPE: " + TYPE + ", user: " + user + ", leader: " + leader + "}";
	}
}
