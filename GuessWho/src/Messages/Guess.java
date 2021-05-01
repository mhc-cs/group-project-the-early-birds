package Messages;
import guesswho.Card;

public class Guess extends Data{
	private Card guess;
	private boolean correct;
	private int score;
	
	public Guess(String TYPE, String type, Card guess, boolean correct, int score) {
		super(TYPE, type);
		this.guess = guess;
		this.correct = correct;
		this.score = score;
	}
	
	public Card getGuess() {
		return guess;
	}
	
	public void setGuess(Card guess) {
		this.guess = guess;
	}
	
	public boolean getCorrect() {
		return correct;
	}
	
	public void setCorrect(boolean correct) {
		this.correct = correct;
	}
	
	public int getScore() {
		return score;
	}
	
	public void setScore(int score) {
		this.score = score;
	}
	
	public String toString() {
		return "Mine: {TYPE: " + TYPE + ", type: " + type + ", guess: " + guess + ", correct: " + correct + ", score: " + score + "}";
	}

}
