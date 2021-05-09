package Messages;

public class Error extends Message{
	private String ERR;
	
	public Error(String TYPE, String ERR) {
		super(TYPE);
		this.ERR = ERR;
	}
	
	public String getErr() {
		return ERR;
	}
	
	public void setErr(String err) {
		this.ERR = err;
	}
	
	public String toString() {
		return "Mine: {TYPE: " + TYPE + ", ERR: " + this.ERR + "}";
	}
}
