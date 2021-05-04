package Messages;

public class Data extends Message{
	Message msg;
	String type;
	String sender;

	
	public Data(String TYPE, Message msg) {
		super(TYPE);
		this.msg = msg;
	}
	
	public Message getMsg() {
		return msg;
	}
	
	public void setMsg(Message msg) {
		this.msg = msg;
	}
	
	
	public String toString() {
		return "Mine: {TYPE: " + TYPE + ", Msg: " + msg + "}";
	}

}
