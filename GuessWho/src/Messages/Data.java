package Messages;

public class Data extends Message{
	String type;

	
	public Data(String TYPE, String type) {
		super(TYPE);
		this.type = type;
	}
	
	public String getDataType() {
		return type;
	}
	
	public void setDataType(String type) {
		this.type = type;
	}
	
	
	public String toString() {
		return "Mine: {TYPE: " + TYPE + ", type: " + type + "}";
	}
}
