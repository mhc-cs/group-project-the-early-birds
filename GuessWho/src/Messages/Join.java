package Messages;

public class Join extends Message{
	private String user;
	private String leader;

	
	public Join(String TYPE, String user, String leader) {
		super(TYPE);
		this.user = user;
		this.leader = leader;
	}
	
	public String getUser() {
		return user;
	}
	
	public void setUser(String user) {
		this.user = user;
	}
	
	public String getLeader() {
		return leader;
	}
	
	public void setLeader(String leader) {
		this.leader = leader;
	}
	
	public String toString() {
		return "Mine: {TYPE: " + TYPE + ", user: " + user + ", leader: " + leader + "}";
	}
}
