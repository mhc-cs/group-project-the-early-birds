package guesswho;

import java.util.ArrayList;
import java.util.Random;

/**
 * Deck
 * Represents a deck of cards, where a card can be added and removed and
 * a random card can be drawn.
 * 
 * @author Hannah, Dani, Anna
 *
 */
public class Deck {
    //number of cards in the deck
    private int size;
    
    //the list of cards in the deck
    ArrayList<Card> deck;
    
    /**
     * Deck constructor
     * The size of the deck is the default 24.
     */
    public Deck() {
        size = 24;
        deck = new ArrayList<Card>();
        for(int i = 0; i < 24; i++) { //fill the deck until 24 cards
            //What should we do about names here?
            deck.add(new Card("", "application/defaultImages/default" + i + ".png"));
        }
    }
    
    /**
     * Deck constructor
     * @param deckSize number of cards in deck
     */
    public Deck(int deckSize) {
        size = deckSize;
        deck = new ArrayList<Card>();
        for(int i = 0; i < size; i++) { //fill the deck until (size) cards
          //What should we do about names here?
            deck.add(new Card(""));
        }
    }
    
    
    /**
     * Adds a card to the deck
     * 
     * @param card the card to add to the deck
     */
    public void addCard(Card card) {
        deck.add(card);
    }
    
    /**
     * Removes a card from the deck
     * 
     * @param card the card to remove from the deck
     */
    public void removeCard(Card card) {
        deck.remove(card);
    }
    
    /**
     * Returns a random card from the deck
     * @return random card from the deck
     */
    public Card drawRandomCard() {
        Random rand = new Random();
        int randomIndex = rand.nextInt(size);
        return deck.get(randomIndex);
    }
    
    /**
     * Returns the size of the deck
     * @return number of cards in deck
     */
    public int getSize() {
        return size;
    }

}
