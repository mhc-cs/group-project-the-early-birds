package Messages;

import java.util.Arrays;

public class Room_Status extends Message{
	private String[] users;
	private boolean is_ready;
	private boolean was_ready;
	private String leader;
	private String allow_spectators;
	private String[] spectators;
	private int size;

	
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
	
	public String[] getUsers() {
		return users;
	}
	
	public void setUsers(String[] users) {
		this.users = users;
	}
	
	public boolean getIs_ready() {
		return is_ready;
	}
	
	public void setIs_ready(boolean is_ready) {
		this.is_ready = is_ready;
	}
	
	public boolean getWas_ready() {
		return was_ready;
	}
	
	public void setWas_ready(boolean was_ready) {
		this.was_ready = was_ready;
	}
	
	public String getLeader() {
		return leader;
	}
	
	public void setLeader(String leader) {
		this.leader = leader;
	}
	
	public String getAllow_spectators() {
		return allow_spectators;
	}
	
	public void setAllow_spectators(String allow_spectators) {
		this.allow_spectators = allow_spectators;
	}
	
	public String[] getSpectators() {
		return spectators;
	}
	
	public void setSpectators(String[] spectators) {
		this.spectators = spectators;
	}
	
	public int getSize() {
		return size;
	}
	
	public void setSize(int size) {
		this.size = size;
	}
	
	public String toString() {
		return "Mine: {TYPE: " + TYPE + ", users: " + Arrays.toString(users) + ", is_ready: " + is_ready + ", was_ready: " + was_ready + ", leader: " + leader +", allow_spectators: " + allow_spectators + ", spectators: " + Arrays.toString(spectators) + ", size: " + size + "}";
	}
}
