package Messages;

public class Chat extends Message{
	private String entrytext;
	
	public Chat(String TYPE, String entrytext) {
		super(TYPE);
		this.entrytext = entrytext;
	}
	
	public String getEntrytext() {
		return entrytext;
	}
	
	public void setEntrytext(String entrytext) {
		this.entrytext = entrytext;
	}
	
	public String toString() {
		return "Mine: {TYPE: " + TYPE + ", entrytext: " + entrytext + "}";
	}

}
