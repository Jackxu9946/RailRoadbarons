package student;

import model.Card;

import java.util.Collections;
import java.util.Stack;

/**
 * Creates a deck of cards for the game. Default deck has 20 of each type of playable card.
 * @author Jack Xu
 * @author Savannah Mattia
 */

public class Deck implements model.Deck {

    private int total;
    private int current;
    private Stack<Card> deck;
    private Stack<Card> currentDeck;

    /**
     * Constructor for deck
     */
    public Deck(){
        this.total = 180;
        this.current = total;
        this.deck = new Stack<>();
            for( int i =0; i < 20; i++){
                deck.push(Card.BLACK);
                deck.push(Card.BLUE);
                deck.push(Card.GREEN);
                deck.push(Card.ORANGE);
                deck.push(Card.PINK);
                deck.push(Card.WHITE);
                deck.push(Card.YELLOW);
                deck.push(Card.RED);
                deck.push(Card.WILD);
            }

        currentDeck = deck;
        Collections.shuffle(currentDeck);
    }

    /**
     * Sets the number of cards back to total, adds the cards back to the deck, and shuffles the deck.
     */
    @Override
    public void reset() {
        //current = total;
        //currentDeck = deck;
        this.deck = new Stack<>();
        for( int i =0; i < 20; i++){
            deck.push(Card.BLACK);
            deck.push(Card.BLUE);
            deck.push(Card.GREEN);
            deck.push(Card.ORANGE);
            deck.push(Card.PINK);
            deck.push(Card.WHITE);
            deck.push(Card.YELLOW);
            deck.push(Card.RED);
            deck.push(Card.WILD);
        }

        currentDeck = deck;
        Collections.shuffle(currentDeck);

    }

    /**
     * If there is a card left in the deck the card will be none and if there are cards left it'll take
     * a card of the top of the stack.
     * @return - Card
     */
    @Override
    public Card drawACard() {
        if(current == 0){
            return Card.NONE;
        }
        else{
            Card Returnval = currentDeck.pop();
            this.current = this.currentDeck.size();
            return Returnval;
        }
    }

    /**
     * checks the current number of cards
     * @return - int current
     */
    @Override
    public int numberOfCardsRemaining() {
        return this.currentDeck.size();
    }
}
