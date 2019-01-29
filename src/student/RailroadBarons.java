package student;

import model.*;
import model.Deck;
import model.Player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * The main entry point for the Classic Edition of the game
 * @author Jack Xu
 * @author Savannah Mattia
 */

public class RailroadBarons implements model.RailroadBarons {
    private LinkedList<RailroadBaronsObserver> obs;
    private RailroadMap map;
    private LinkedList<Player> listoplayer;
    private Player currentplayer;
    private Deck deck;
    private int where =0;


    /**
     * constructor for RailroadBarons
     */
    public RailroadBarons(){
        this.deck = new student.Deck();
        listoplayer = new LinkedList<>();
        this.obs = new LinkedList<>();
        this.listoplayer = new LinkedList<>();
        LinkedList<Card> ca = new LinkedList<Card>();

        for(Baron b: Baron.values()) {
            System.out.println(b);
            if (b.equals(Baron.UNCLAIMED)) {
            } else {
                for (int i = 0; i < 4; i++) {

                    ca.add(deck.drawACard());
                }
                Player p1 = new student.Player(b, ca);
                listoplayer.add(p1);
                ca.clear();
            }
        }
        currentplayer = listoplayer.get(0);
    }

    /**
     * Adds a new observer to the collection. Will be notified when state of game changes.
     * @param observer The {@link RailroadBaronsObserver} to add to the
     */
    @Override
    public void addRailroadBaronsObserver(RailroadBaronsObserver observer) {
        this.obs.add(observer);
    }

    /**
     * Removes the observer from the collection of observers that will be notified when the state of the game
     * changes
     */
    @Override
    public void removeRailroadBaronsObserver(RailroadBaronsObserver observer) {
        this.obs.remove(observer);
    }

    /**
     * Starts a new Railroad Barons game with the specified map and a default deck of cards
     * @param map The {@link RailroadMap} on which the game will be played.
     */
    @Override
    public void startAGameWith(RailroadMap map) {
        //System.out.println("GETS RESET");
        this.map = map;
        this.deck.reset();
        for(Player P: listoplayer){
            Card[] C4 = new Card[4];
            for(int counter =0; counter < 4; counter ++){
                C4[counter] = this.deck.drawACard();

            }
            P.reset(C4);
        }
        Card c1 = deck.drawACard();
        Card c2 = deck.drawACard();
        Pair pair = new Pair(c1,c2);
        currentplayer.startTurn(pair);
        for(RailroadBaronsObserver ob: obs){
            ob.turnStarted(this,this.currentplayer);
        }

        }

    /**
     * Starts a new Railroad Barons game with the specified map and deck of cards
     * @param map The {@link RailroadMap} on which the game will be played.
     * @param deck The {@link Deck} of cards used to play the game. This may
     *             be ANY implementation of the {@link Deck} interface,
     *             meaning that a valid implementation of the
     *             {@link RailroadBarons} interface should use only the
     */
    @Override
    public void startAGameWith(RailroadMap map, Deck deck) {
        this.map = map;
        this.deck = deck;
        Card c1 = deck.drawACard();
        Card c2 = deck.drawACard();
        Pair pair = new Pair(c1,c2);
        currentplayer.startTurn(pair);
        for(RailroadBaronsObserver ob: obs){
            ob.turnStarted(this,this.currentplayer);
        }

    }

    /**
     * Finds the map that is currently being used for play
     * @return - RailroadMap
     */
    @Override
    public RailroadMap getRailroadMap() {
        return this.map;
    }

    /**
     * Finds the number of cards that remain to be dealt in the current game's deck
     * @return - int
     */
    @Override
    public int numberOfCardsRemaining() {
        return this.deck.numberOfCardsRemaining();
    }

    /**
     * Find if the current player can claim the route at a specified location
     * @param row The row of a {@link Track} in the {@link Route} to check.
     * @param col The column of a {@link Track} in the {@link Route} to check.
     * @return
     */
    @Override
    public boolean canCurrentPlayerClaimRoute(int row, int col) {
        model.Route r = map.getRoute(row,col);
        return this.currentplayer.canClaimRoute(r);
    }

    /**
     * Attempts to claim the route at the specified location on behalf of the current player
     * @param row The row of a {@link Track} in the {@link Route} to claim.
     * @param col The column of a {@link Track} in the {@link Route} to claim.
     * @throws RailroadBaronsException
     */
    @Override
    public void claimRoute(int row, int col) throws RailroadBaronsException {
        try{
        if(this.canCurrentPlayerClaimRoute(row,col)){
            model.Route r = map.getRoute(row,col);
            this.currentplayer.claimRoute(r);
            map.routeClaimed(r);
        }}
        catch(Exception ex){}
    }

    /**
     * Called when the current player ends their turn
     */
    @Override
    public void endTurn() {
        if (!this.gameIsOver()){
            int i = this.where;
            i++;
            this.where = i;
            //System.out.println(currentplayer+"1");
            for(RailroadBaronsObserver ob: obs){
                ob.turnEnded(this,currentplayer);
            }
            currentplayer = listoplayer.get(i%4);
            Card c1 = deck.drawACard();
            Card c2= deck.drawACard();
            Pair deal = new Pair(c1,c2);
            currentplayer.startTurn(deal);
            for(RailroadBaronsObserver ob: obs){
                ob.turnStarted(this,currentplayer);
            }

        }
        else{
            //Runs through every single player and see if their shit connects
            System.out.println("Row"+this.map.getRows());
            System.out.println("Column" + this.map.getCols());
            //HashMap<Player,Integer> PlayerPerScore = new HashMap<>();
            ArrayList<student.Player> NewList = new ArrayList<>();
            for(Player P: this.listoplayer){
                student.Player P1 = (student.Player) P;
                NewList.add(P1);
            }

            ArrayList<Integer> HortVertBonus = FindifValid.FindBonusVals(this.map);
            for(student.Player P: NewList){
                System.out.println("Player and score");
                System.out.println(P.getBaron().toString()+": " + P.getScore());
                //PlayerPerScore.put(P,P.getScore());
                ArrayList<Boolean> TruthList = FindifValid.BonusQ(P,this.map);
                System.out.println(TruthList +": "+ P.getBaron().toString());
                //First index is horizontal truth
                if(TruthList.get(0) == true){
                    //Add bonus
                    int val = P.getScore();
                    val += HortVertBonus.get(0)* 5;
                    P.score = val;
                    //PlayerPerScore.put(P,val);
                }
                //Second value is vertical truth
                if(TruthList.get(1) == true){
                    int val = P.getScore();
                    val += HortVertBonus.get(1)* 5;
                    P.score = val;
                    //PlayerPerScore.put(P,val);
                }
                P.Updateall();
            }

            Player Winner = new student.Player();
            int comp = 0;
            for(student.Player P2: NewList){
                if(P2.getScore()> comp){
                    comp = P2.getScore();
                    Winner = P2;
                }
            }

            //System.out.println("After bonus");
            //System.out.println((Winner));
            //Goes through every observer and announce the winner
            for(RailroadBaronsObserver Obs: this.obs){
                Obs.gameOver(this,Winner);
            }






            }
        }



    /**
     * Finds the player whose turn it is
     * @return - Player
     */
    @Override
    public Player getCurrentPlayer() {
        return currentplayer;
    }

    /**
     * Finds all the currently playing players
     * @return - Collection - Players
     */
    @Override
    public Collection<Player> getPlayers() {
        return listoplayer;
    }

    /**
     * Indicates whether or not the game is over.
     * @return - true or false
     */
    @Override
    public boolean gameIsOver() {

        int shortest = map.getLengthOfShortestUnclaimedRoute();
        System.out.println(shortest);
        int i = 0;
        //I is a counter to see how many player can still play
        if(this.deck.numberOfCardsRemaining() == 0){
        for (model.Player p : this.listoplayer) {
            if (p.canContinuePlaying(shortest)) {

            } else {
                i++;
            }
        }}
        if (i == 4) {
            return true;
        } else {
            for (model.Route r : map.getRoutes()) {
                if (r.getBaron() == null) {
                    return false;
                }
            }
            return true;


        }
    }
}
