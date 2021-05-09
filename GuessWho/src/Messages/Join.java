package Messages;

/**
 * Join (Message)
 * Message containing player name and if host, sent when joining room
 * 
 * @author Hannah, Dani, Anna
 *
 */
public class Join extends Message{
	
	//player name
	private String user;
	
	//if player is host
	private String leader;

	/**
	 * Join message constructor
	 * @param TYPE (should be 'JOIN')
	 * @param user
	 * @param leader
	 */
	public Join(String TYPE, String user, String leader) {
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
	 *set leader
	 * @param leader
	 */
	public void setLeader(String leader) {
		this.leader = leader;
	}
	
	/**
	 * Join message to String
	 */
	public String toString() {
		return "Mine: {TYPE: " + TYPE + ", user: " + user + ", leader: " + leader + "}";
	}
}
