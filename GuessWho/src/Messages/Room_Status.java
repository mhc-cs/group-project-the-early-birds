package Messages;

import java.util.Arrays;

/**
 * Room_Status (Message)
 * Message containing information on status of room, sent by server
 * 
 * @author Hannah, Dani, Anna
 *
 */
public class Room_Status extends Message{
	
	//list of all players in the room
	private String[] users;
	
	//if room is full ->ready to play
	private boolean is_ready;
	
	//if room was full
	private boolean was_ready;
	
	//name of host player
	private String leader;
	
	//if spectators are allowed
	private String allow_spectators;
	
	//list of all spectators in the room
	private String[] spectators;
	
	//size of room
	private int size;

	/**
	 * Room_Status message constructor
	 * @param TYPE (Should be 'ROOM_STATUS')
	 * @param users
	 * @param is_ready
	 * @param was_ready
	 * @param leader
	 * @param allow_spectators
	 * @param spectators
	 * @param size
	 */
	public Room_Status(String TYPE, String[] users, boolean is_ready, boolean was_ready, String leader, String allow_spectators, String[] spectators, int size) {
		super(TYPE);
		this.users = users;
		this.is_ready = is_ready;
		this.was_ready = was_ready;
		this.leader = leader;
		this.allow_spectators = allow_spectators;
		this.spectators = spectators;
		this.size = size;
	}
	
	/**
	 * get users
	 * @return users
	 */
	public String[] getUsers() {
		return users;
	}
	
	/**
	 * set users
	 * @param users
	 */
	public void setUsers(String[] users) {
		this.users = users;
	}
	
	/**
	 * get is ready
	 * @return is_ready
	 */
	public boolean getIs_ready() {
		return is_ready;
	}
	
	/**
	 * set is ready
	 * @param is_ready
	 */
	public void setIs_ready(boolean is_ready) {
		this.is_ready = is_ready;
	}
	
	/**
	 * get was ready
	 * @return was_ready
	 */
	public boolean getWas_ready() {
		return was_ready;
	}
	
	/**
	 * set was ready
	 * @param was_ready
	 */
	public void setWas_ready(boolean was_ready) {
		this.was_ready = was_ready;
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
	 * get allow spectators
	 * @return allow_spectators
	 */
	public String getAllow_spectators() {
		return allow_spectators;
	}
	
	/**
	 * set allow spectators
	 * @param allow_spectators
	 */
	public void setAllow_spectators(String allow_spectators) {
		this.allow_spectators = allow_spectators;
	}
	
	/**
	 * get spectators
	 * @return spectators
	 */
	public String[] getSpectators() {
		return spectators;
	}
	
	/**
	 * set spectators
	 * @param spectators
	 */
	public void setSpectators(String[] spectators) {
		this.spectators = spectators;
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
	 * Room status message to string
	 */
	public String toString() {
		return "Mine: {TYPE: " + TYPE + ", users: " + Arrays.toString(users) + ", is_ready: " + is_ready + ", was_ready: " + was_ready + ", leader: " + leader +", allow_spectators: " + allow_spectators + ", spectators: " + Arrays.toString(spectators) + ", size: " + size + "}";
	}
}
