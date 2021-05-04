package Messages;

public class Data extends Message{
	Message msg;
	String type;
	String sender;

	
	public Data(String TYPE, String sender, Message msg) {
		super(TYPE);
		this.sender = sender;
		this.msg = msg;
	}
	
	public Message getMsg() {
		return msg;
	}
	
	public void setMsg(Message msg) {
		this.msg = msg;
	}
	
	public String getSender() {
		return sender;
	}
	
	public void setSender(String sender) {
		this.sender = sender;
	}
	
	
	public String toString() {
		return "Mine: {TYPE: " + TYPE + ", Sender: " + sender + ", Msg: " + msg + "}";
	}

}
