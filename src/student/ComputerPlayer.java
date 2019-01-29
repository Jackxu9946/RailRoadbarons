package student;

import model.*;
import model.Pair;
import model.Route;

import java.util.*;

/**
 * Creates an AI player so a single person is capable of playing
 * @author Jack Xu
 * @author Savannah Mattia
 */

public class ComputerPlayer implements model.Player {
    private List<Card> hand;
    private List<Route> Owned;
    private boolean claimthisturn;
    private HashMap<Card,Integer> cards;
    public int score;
    private int trainPiece;
    private Baron baron;
    private RailroadBaronsLonely RB;
    private Set<PlayerObserver> PO;

    /**
     * Constructor for ComputerPlayer
     * @param baron - Baron
     * @param RB - RailroadBaronsLonely
     * @param initialhand - Collection<Card>
     */
    public ComputerPlayer(Baron baron,RailroadBaronsLonely RB,Collection<Card> initialhand){
        this.score = 0;
        this.trainPiece = 45;
        this.Owned = new LinkedList<>();
        this.baron = baron;
        this.RB = RB;
        this.claimthisturn = true;
        this.cards = new HashMap<>();
        this.cards.put(Card.BLACK,0);
        this.cards.put(Card.BLUE,0);
        this.cards.put(Card.GREEN,0);
        this.cards.put(Card.ORANGE,0);
        this.cards.put(Card.RED,0);
        this.cards.put(Card.PINK,0);
        this.cards.put(Card.WHITE,0);
        this.cards.put(Card.WILD,0);
        this.cards.put(Card.YELLOW,0);
        this.hand = new LinkedList<>();
        this.PO = new HashSet<PlayerObserver>();
        for( Card c: initialhand){
            hand.add(c);
            int i = this.cards.get(c);
            i++;
            this.cards.put(c,i);

        }
    }

    /**
     * What is called at the beginning of every game
     * @param dealt The hand of {@link Card cards} dealt to the player at the beginning
     */
    @Override
    public void reset(Card... dealt) {
        this.claimthisturn = true;
        this.trainPiece = 45;
        this.score =0;
        this.hand = new LinkedList<>();
        this.Owned = new LinkedList<>();
        this.cards = new HashMap<>();
        this.cards.put(Card.BLACK,0);
        this.cards.put(Card.BLUE,0);
        this.cards.put(Card.GREEN,0);
        this.cards.put(Card.ORANGE,0);
        this.cards.put(Card.RED,0);
        this.cards.put(Card.PINK,0);
        this.cards.put(Card.WHITE,0);
        this.cards.put(Card.WILD,0);
        this.cards.put(Card.YELLOW,0);
        this.cards.put(Card.NONE,0);
        this.cards.put(Card.BACK,0);
        for(Card C: dealt){
            this.hand.add(C);
        }
        for(PlayerObserver op:PO){
            op.playerChanged(this);
        }
    }

    /**
     * Adds an observer that will be notified when the player changes in some way.
     * @param observer The new {@link PlayerObserver}.
     */
    @Override
    public void addPlayerObserver(PlayerObserver observer) {
        this.PO.add(observer);
    }

    /**
     * Removes an observer so that it is no longer notified when the player changes in some way
     * @param observer The {@link PlayerObserver} to remove.
     */
    @Override
    public void removePlayerObserver(PlayerObserver observer) {
        this.PO.remove(observer);
    }

    /**
     * The baron as which this player is playing the game
     * @return - Baron
     */
    @Override
    public Baron getBaron() {
        return this.baron;
    }

    /**
     * Starts the player's next turn. A pair of cards is dealt to the player, and the player is once again able to
     * claim a route on the map
     * @param dealt a {@linkplain Pair pair of cards} to the player. Note that
     */
    @Override
    public void startTurn(Pair dealt) {
        if(!this.RB.gameIsOver()){
        Card c1 = dealt.getFirstCard();
        Card c2 = dealt.getSecondCard();
        this.Insertsomething(c1);
        this.Insertsomething(c2);
        this.hand.add(c1);
        this.hand.add(c2);
        this.claimthisturn = false;
        //This is where you make the AI smart
        for(Route R: this.RB.getRailroadMap().getRoutes()){

                if(this.canClaimRoute(R)){
                    try{

                        this.claimRoute(R);
                        for(PlayerObserver ob:PO){
                            ob.playerChanged(this);
                        }
                        this.RB.getRailroadMap().routeClaimed(R);
                        break;
                    }
                    catch(Exception ex){
                    }
                }
            }

        this.RB.endTurn();
        }
        else{
        }
    }


    /**
     * Finds the most recently dealt pair of cards.
     * @return - Pair
     */
    @Override
    public Pair getLastTwoCards() {
        Card lastone = this.hand.get(this.hand.size()-1);
        Card secondlast = this.hand.get(this.hand.size()-2);
        return new student.Pair(lastone,secondlast);
    }

    /**
     * Finds the number of the specific kind of card that the player currently has in hand
     * @param card The {@link Card} of interest.
     * @return - int
     */
    @Override
    public int countCardsInHand(Card card) {
        return this.cards.get(card);
    }

    /**
     * Finds the number of game pieces the player has remaining
     * @return - int
     */
    @Override
    public int getNumberOfPieces() {
        return this.trainPiece;
    }

    /**
     * Checks if route is claimed, player has already claimed a route this round, player has enough cards and
     * player has enough train pieces.
     * @param route The {@link Route} being tested to determine whether or not
     *              the player is able to claim it.
     * @return - true or false
     */
    @Override
    public boolean canClaimRoute(Route route) {
         if(route.getBaron() == null){
            if( this.claimthisturn == false){
                if (this.canContinuePlaying(route.getLength())){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Claims the given route on behalf of the players Railroad Baron.
     * @param route The {@link Route} to claim.
     *
     * @throws RailroadBaronsException
     */
    @Override
    public void claimRoute(Route route) throws RailroadBaronsException {
        try{
            if( this.canClaimRoute(route)){
                this.score += route.getPointValue();
                Card ca = Card.BACK;
                int i = 0;

                for(Card c: this.cards.keySet()){
                    if(c.equals(Card.NONE)|| c.equals(Card.WILD)){}
                    else{
                    if(this.cards.get(c)>i){
                        ca = c;
                        i = this.cards.get(c);
                    }
                }}
                if( i < route.getLength()){
                    int wild =this.cards.get(Card.WILD);
                    wild -=1;
                    this.cards.put(Card.WILD,wild);
                }
                int after = i - route.getLength();
                this.cards.put(ca,after);
                route.claim(this.baron);
                this.Owned.add(route);
                this.claimthisturn=true;
                this.trainPiece = this.trainPiece- route.getLength();
                //System.out.println(this.Owned);
                for(PlayerObserver op:PO){
                    op.playerChanged(this);
                }

            }}
        catch(Exception ex){throw new RailroadBaronsException("Error");}


    }

    /**
     * Find the collection of routes claimed by this player
     * @return - Collection - Route
     */
    @Override
    public Collection<Route> getClaimedRoutes() {
        return this.Owned;
    }

    /**
     * Finds the players current score based on the point value of each route that the player has currently claimed
     * @return - int
     */
    @Override
    public int getScore() {
        return this.score;
    }

    /**
     * Looks for if the player has enough cards to claim route, and if they have enough train pieces
     * @param shortestUnclaimedRoute The length of the shortest unclaimed
     *                               {@link Route} in the current game.
     *
     * @return - true or false
     */
    @Override
    public boolean canContinuePlaying(int shortestUnclaimedRoute) {
        int comp =0;
        for( Card c: cards.keySet()){
            //System.out.println(cards.keySet());
            //System.out.println(c);
            if(c.equals(Card.NONE) || c.equals(Card.WILD)){}
            else{
                int now = this.cards.get(c);
                if(this.cards.get(Card.WILD) != 0)
                {
                    now += 1;
                }
                if(now > comp){
                    comp = now;
                }

            }
        }
        if (comp>= shortestUnclaimedRoute){
            if(this.trainPiece>= shortestUnclaimedRoute){
                return true;
            }
        }

        return false;
    }

    /**
     * Helper function to find a track
     * @param c - Card
     */
    public void Insertsomething(Card c){
        int i = this.cards.get(c);
        i++;
        this.cards.put(c,i);

    }

    /**
     * Changes the score
     * @param Score - int
     */
    public void setScore(int Score){
        this.score = Score;
        for(PlayerObserver op:PO){
            op.playerChanged(this);
        }

    }

    /**
     * Updates all the player observers in the set PO
     */
    public void Updateall(){
        for(PlayerObserver POB: this.PO){
            POB.playerChanged(this);
        }
    }



}
