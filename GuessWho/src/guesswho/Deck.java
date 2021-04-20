package guesswho;

import java.lang.reflect.Array;
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
    private ArrayList<Card> deck;
    
    private String[] defaultNames = {"Samuel","Pepe","Pablo","Jorge","Felipe","Clara","Anita","Alfredo","Susana","Ricardo","Paco","Manuel","German","David","Bernardo","Alejandro","Tomas","Roberto","Pedro","Maria","Guillermo","Erneseto","Carlos","Ana"};
    
    /**
     * Default Deck constructor
     * The size of the deck is the default 24.
     */
    public Deck() {
        size = 24;
        deck = new ArrayList<Card>(size);
        for(int i = 0; i < size; i++) { //fill the deck until 24 cards
            deck.add(new Card((String)Array.get(defaultNames, i-1), "application/defaultImages/default" + i + ".png"));
        }
    }
    
    /**
     * Custom Deck constructor
     * @param deckSize number of cards in deck
     */
    public Deck(int deckSize) {
        size = deckSize;
        deck = new ArrayList<Card>(size);
        for(int i = 0; i < size; i++) { //fill the deck until (size) cards
            deck.add(new Card("Card #"+size,"applicatoin/defaultImages/defaultNoImage.png"));
        }
    }
    
    
    /**
     * Adds a card to the deck
     * 
     * @param card the card to add to the deck
     */
    public void addCard() {
    	size++;
        deck.add(new Card("Card #"+size,"applicatoin/defaultImages/defaultNoImage.png"));
    }
    
    /**
     * Removes a card from the deck
     * 
     * @param index of the card to remove from the deck
     */
    public void removeCard(int index) {
        deck.remove(index);
        size--;
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
     * Returns a specified card from the deck
     * @param index of card
     * @return card from deck
     */
    public Card getCard(int index) {
    	return deck.get(index);
    }
    
    /**
     * Returns the size of the deck
     * @return number of cards in deck
     */
    public int getSize() {
        return size;
    }

}
