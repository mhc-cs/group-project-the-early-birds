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
    
    //name of deck
    private String name;
    
    //List of names for default cards
    private String[] defaultNames = {"Jessica", "Ruby", "Thomas", "Fleur", "Sara", "Amir", "Lucy", "Hugo", "Alexandre", "Ayesha", "Lucas", "Adele", "Simon", "Antonio", "Edward", "Mateo", "Daniel", "Cameron", "Gabriel", "Amelia", "Diego", "Roberto", "Sofia", "Zoe"};
    
    //List of names for avengers deck
    private String[] avengersNames = {"Cap. America","Iron Man","Black Widow","Hulk","Thor","Hawkeye","Wanda","Vision","Fury","Falcon","Bucky","Loki","Rhodey","Dr. Strange","Wong","T'Challa","Shuri","Spiderman","Ned","Antman","Star-Lord","Groot","Gamora","Rocket"};
    
    //List of names for sonic deck
    private String[] sonicNames = {"Sonic","Amy","Knuckles","Tails","Silver","Blaze","Charmy","Espio","Shadow","Cream","Cheese","Sally Acorn","Chip","Marine","Scourge","Fiona","Bean","Eggman","Mephiles","Rogue","Big","Gamma","Omega","Metal Sonic"};
    
    //List of names for Super Smash Bros deck
    private String[] SSBNames = {"Luigi", "Peach", "Ike", "Yoshi", "Ness", "Meta Knight", "Wii Fit Trainer", "Pac Man", "Toon Link", "R.O.B", "Snake", "Donkey Kong", "Game&Watch", "Pikachu", "Mario", "Marth", "Lucario", "Link", "Villager", "Kirby", "Pit", "Lucina", "Duck Hunt", "Isabelle"};
    
    //List of names for Avatar deck
    private String[] avatarNames = {"Aang","Katara","Sokka","Zuko","Toph","Iroh","Azula","Mai","Suki","Ty Lee","Princess Yue","Fire Lord Ozai","Admiral Zhao","Roku","Kyoshi","Earth King","Long Feng","Joo Dee","Jet","The Boulder","King Bumi","Cabbage Man","Appa","Momo"};
    
    //Add more name lists for decks here
    
    /**
     * Default Deck constructor
     * The size of the deck is the default 24.
     */
    public Deck() {
        size = 24;
        deck = new ArrayList<Card>(size);
        name = "default";
        
        for(int i = 0; i < size; i++) { //fill the deck until 24 cards
            deck.add(new Card((String)Array.get(defaultNames, i), "defaultImages/default" + i + ".png"));
        }
    }
    
    /**
     * Chosen Deck constructor
     * The size of the deck is the default 24.
     * @param name of deck
     */
    public Deck(String name) {
        size = 24;
        deck = new ArrayList<Card>(size);
        this.name = name;
        String[] useNames = null;
        if(name.equals("default")) {
        	useNames = defaultNames;
        }
        else if(name.equals("avengers")) {
        	useNames = avengersNames;
        }
        else if(name.equals("sonic")) {
        	useNames = sonicNames;
        }
        else if(name.equals("super smash bros")) {
            useNames = SSBNames;
        }
        else if(name.equals("avatar")) {
        	useNames = avatarNames;
        }
        //add more else if cases for decks here
        for(int i = 0; i < size; i++) { //fill the deck until 24 cards
            deck.add(new Card((String)Array.get(useNames, i), "defaultImages/"+ name + i + ".png"));
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
            deck.add(new Card("Card #"+size,"defaultImages/defaultNoImage.png"));
        }
    }
    
    /**
     * Adds a card to the deck
     * @param card the card to add to the deck
     */
    public void addCard() {
    	size++;
        deck.add(new Card("Card #"+size,"defaultImages/defaultNoImage.png"));
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
    
    /**
     * get deck name
     * @return name
     */
    public String getName() {
    	return name;
    }

}
