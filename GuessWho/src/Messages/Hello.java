package Messages;

public class Hello extends Message{
	private String name;
	private String gamename;

	
	public Hello(String TYPE, String name, String gamename) {
		super(TYPE);
		this.name = name;
		this.gamename = gamename;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getGamename() {
		return gamename;
	}
	
	public void setGamename(String gamename) {
		this.gamename = gamename;
	}
	
	public String toString() {
		return "Mine: {TYPE: " + TYPE + ", name: " + name + ", gamename: " + gamename + "}";
	}

}
