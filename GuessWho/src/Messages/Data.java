package Messages;

public class Data extends Message{
	String type;

	
	public Data(String TYPE, String type) {
		super(TYPE);
		this.type = type;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	
	public String toString() {
		return "Mine: {TYPE: " + TYPE + ", type: " + type + "}";
	}
}
