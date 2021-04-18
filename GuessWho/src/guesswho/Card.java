package guesswho;

/**
 * Card
 * Stores a card with a name and imagePath that can be greyed out. 
 * 
 * @author Hannah, Dani, Anna
 *
 */

public class Card {
	//Name of card
	private String cardName;
	
	//Path to image for card
	private String imagePath;
	
	//status of card, if greyed out.
	private boolean grey;
	
	/**
	 * Card constructor
	 * Makes a card with the default image.
	 * @param name is the name of the card.
	 */
	public Card(String name) {
		cardName = name;
		grey = false;
		imagePath = "application/defaultImages/defaultNoImage.png";
	}
	
	/**
	 * Card constructor 
	 * Makes a card with given image.
	 * @param name is the name of the card.
	 * @param image is the image path.
	 */
	public Card(String name, String image) {
		cardName = name;
		grey = false;
		imagePath = image;
	}
	
	/**
	 * Get name
	 * @return name of card
	 */
	public String getName() {
		return cardName;
	}
	
	/**
	 * Set name
	 * @param name of card
	 */
	public void setName(String name) {
		cardName = name;
	}
	
	/**
	 * Get image path
	 * @return imagePath of card
	 */
	public String getImagePath() {
		return imagePath;
	}
	
	/**
	 * set image path
	 * @param path to image
	 */
	public void setImagePath(String path) {
		imagePath = path;
	}
	
	/**
	 * get grey
	 * @return grey boolean
	 */
	public boolean getGrey() {
		return grey;
	}
	
	/**
	 * set grey
	 * @param status of grey boolean
	 */
	public void setGrey(boolean status) {
		grey = status;
	}
	
}
