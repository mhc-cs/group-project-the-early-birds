package Messages;

/**
 * Data (Message)
 * Message sent to all players in a room
 * 
 * @author Hannah, Dani, Anna
 *
 */
public class Data extends Message{
	
	//Message inside data message that will be received by players
	Message msg;

	/**
	 * Data message constructor
	 * @param TYPE (should be 'DATA')
	 * @param msg
	 */
	public Data(String TYPE, Message msg) {
		super(TYPE);
		this.msg = msg;
	}
	
	/**
	 * get message
	 * @return msg
	 */
	public Message getMsg() {
		return msg;
	}
	
	/**
	 * set message
	 * @param msg
	 */
	public void setMsg(Message msg) {
		this.msg = msg;
	}
	
	/**
	 * Data message to string
	 */
	public String toString() {
		return "Mine: {TYPE: " + TYPE + ", Msg: " + msg + "}";
	}

}
