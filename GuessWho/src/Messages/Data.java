package Messages;

public class Data extends Message{
	Message msg;
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
//	public Data(String TYPE, Message msg) {
//		super(TYPE);
//		this.msg = msg;
//	}
}
