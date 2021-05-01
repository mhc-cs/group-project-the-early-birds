package Messages;

public class Chat extends Data{
	private String entrytext;
	
	public Chat(String TYPE, String type, String entrytext) {
		super(TYPE, type);
		this.entrytext = entrytext;
	}
	
	public String getEntrytext() {
		return entrytext;
	}
	
	public void setEntrytext(String entrytext) {
		this.entrytext = entrytext;
	}
	
	public String toString() {
		return "Mine: {TYPE: " + TYPE + ", type: " + type + ", entrytext: " + entrytext + "}";
	}

}
