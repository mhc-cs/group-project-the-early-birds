package Messages;

/**
 * Error (Message)
 * Message containing an error from the server
 * 
 * @author Hannah, Dani, Anna
 *
 */
public class Error extends Message{
	
	//description of error
	private String ERR;
	
	/**
	 * Error message constructor
	 * @param TYPE (should be 'ERROR')
	 * @param ERR
	 */
	public Error(String TYPE, String ERR) {
		super(TYPE);
		this.ERR = ERR;
	}
	
	/**
	 * get error description
	 * @return ERR
	 */
	public String getErr() {
		return ERR;
	}
	
	/**
	 * set error description
	 * @param err
	 */
	public void setErr(String err) {
		this.ERR = err;
	}
	
	/**
	 * Error message to String
	 */
	public String toString() {
		return "Mine: {TYPE: " + TYPE + ", ERR: " + this.ERR + "}";
	}
}
