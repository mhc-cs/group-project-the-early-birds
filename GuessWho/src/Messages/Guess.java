package Messages;

import guesswho.Card;

/**
 * Guess (Message)
 * Message containing the cards drawn for both players
 * should be used inside a DATA message
 * 
 * @author Hannah, Dani, Anna
 *
 */
public class Guess extends Message{
	
	//Card that was guessed
	private Card guess;
	
	//If guess was correct
	private boolean correct;
	
	//Guessing player's new score
	private int score;
	
	/**
	 * Guess message constructor
	 * @param TYPE (should be 'guess')
	 * @param guess
	 * @param correct
	 * @param score
	 */
	public Guess(String TYPE, Card guess, boolean correct, int score) {
		super(TYPE);
		this.guess = guess;
		this.correct = correct;
		this.score = score;
	}
	
	/**
	 * get card guessed
	 * @return guess
	 */
	public Card getGuess() {
		return guess;
	}
	
	/**
	 * set card guessed
	 * @param guess
	 */
	public void setGuess(Card guess) {
		this.guess = guess;
	}
	
	/**
	 * get if guess was correct
	 * @return correct
	 */
	public boolean getCorrect() {
		return correct;
	}
	
	/**
	 * set if guess was correct
	 * @param correct
	 */
	public void setCorrect(boolean correct) {
		this.correct = correct;
	}
	
	/**
	 * get player's new score
	 * @return score
	 */
	public int getScore() {
		return score;
	}
	
	/**
	 * set player's new score
	 * @param score
	 */
	public void setScore(int score) {
		this.score = score;
	}
	
	/**
	 * Guess message to String
	 */
	public String toString() {
		return "Mine: {TYPE: " + TYPE + ", guess: " + guess + ", correct: " + correct + ", score: " + score + "}";
	}

}
