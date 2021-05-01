package Messages;

public class Join_Game extends Message{
	private int size;
	private String allow_spectators;
	private String status;
	private String gamecode;

	
	public Join_Game(String TYPE, int size, String allow_spectators, String status, String gamecode, String role) {
		super(TYPE);
		this.size = size;
		this.allow_spectators = allow_spectators;
		this.status = status;
		this.gamecode = gamecode;
	}
	
	public int getSize() {
		return size;
	}
	
	public void setSize(int size) {
		this.size = size;
	}
	
	public String getAllow_spectators() {
		return allow_spectators;
	}
	
	public void setAllow_spectators(String allow_spectators) {
		this.allow_spectators = allow_spectators;
	}
	
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getGamecode() {
		return gamecode;
	}
	
	public void setGamecode(String gamecode) {
		this.gamecode = gamecode;
	}
	
	public String toString() {
		return "Mine: {TYPE: " + TYPE + ", size: " + size + ", allow_spectators: " + allow_spectators + ", status: " + status + ", gamecode: " + gamecode + "}";
	}
}