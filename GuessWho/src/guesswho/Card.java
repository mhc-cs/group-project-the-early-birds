package guesswho;


public class Card {
	private String cardName;
	private String imagePath;
	private boolean grey;
	
	public Card(String name) {
		cardName = name;
		grey = false;
		imagePath = "defaultImages/defaultNoImage.png";
	}
	
	
	
}
