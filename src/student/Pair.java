package student;

import model.Card;

/**
 * Cards in this game are dealt in pairs to each player. This is used to hold one such pair.
 * If the deck is empty, one or both may be empty.
 * @author Jack Xu
 * @author Savannah Mattia
 */

public class Pair implements model.Pair {

    private Card first;
    private Card second;

    /**
     * constructor for pair
     * @param first - card
     * @param second - card
     */
    public Pair(Card first, Card second){
        this.first = first;
        this.second = second;
    }

    /**
     * returns the first card in the pair including Card.None if the deck is empty
     * @return first card in the pair
     */
    @Override
    public Card getFirstCard() {
        return first;
    }

    /**
     * returns the second card in the pair including Card.None if the deck is empty
     * @return second card in the pair
     */
    @Override
    public Card getSecondCard() {
        return second;
    }
}
