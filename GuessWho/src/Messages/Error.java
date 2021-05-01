package Messages;

public class Error extends Message{
	private String err;
	
	public Error(String TYPE, String err) {
		super(TYPE);
		this.err = err;
	}
	
	public String getErr() {
		return err;
	}
	
	public void setErr(String err) {
		this.err = err;
	}
	
	public String toString() {
		return "Mine: {TYPE: " + TYPE + ", ERR: " + err + "}";
	}
}
