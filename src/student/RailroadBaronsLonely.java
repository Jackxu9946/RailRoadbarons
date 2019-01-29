package student;

import model.*;
import model.Deck;
import model.Player;
import model.RailroadBarons;
import model.Station;
import org.junit.runner.Computer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * The main entry point for the Lonely Edition of the game
 * @author Jack Xu
 * @author Savannah Mattia
 */

public class RailroadBaronsLonely implements RailroadBarons {
    private LinkedList<RailroadBaronsObserver> obs;
    private RailroadMap map;
    private Deck deck;
    private Player currentplayer;
    private LinkedList<Player> listoplayer;
    private int where =0;

    /**
     * Constructor for Lonely Edition
     */
    public RailroadBaronsLonely(){
        this.deck = new student.Deck();
        listoplayer = new LinkedList<>();
        this.obs = new LinkedList<>();
        int Who = 0;
        for(Baron b:Baron.values()){
            LinkedList<Card> CA = new LinkedList<>();
            if(b.equals(Baron.UNCLAIMED)){}
            else{
                for(int i = 0; i < 4; i++){
                    CA.add(this.deck.drawACard());
                }
                if(Who == 0){
                    Player P = new student.Player(b,CA);
                    this.listoplayer.add(P);
                    Who++;
                }
                else{
                    ComputerPlayer CP = new student.ComputerPlayer(b,this,CA);
                    this.listoplayer.add(CP);
                }

            }
        }
        this.currentplayer = this.listoplayer.get(where);
    }

    /**
     * Adds a new observer to the collection. Will be notified when state of game changes.
     * @param observer The {@link RailroadBaronsObserver} to add to the
     */
    @Override
    public void addRailroadBaronsObserver(RailroadBaronsObserver observer) {
        obs.add(observer);
    }

    /**
     * Removes the observer from the collection of observers that will be notified when the state of the game
     * changes
     */
    @Override
    public void removeRailroadBaronsObserver(RailroadBaronsObserver observer) {
        obs.remove(observer);
    }

    /**
     * Starts a new Railroad Barons game with the specified map and a default deck of cards
     * @param map The {@link RailroadMap} on which the game will be played.
     */
    @Override
    public void startAGameWith(RailroadMap map) {
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
     * Find if the player can claim the route at a specified location
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
     * Attempts to claim the route at the specified location on behalf of the player
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
     * Called when the player ends their turn
     */
    @Override
    public void endTurn() {
        //System.out.println(this.gameIsOver());
        if (!this.gameIsOver()){
            int i = this.where;
            i++;
            this.where = i;
            System.out.println(currentplayer+"1");
            for(RailroadBaronsObserver ob: obs){
                ob.turnEnded(this,currentplayer);
            }
            currentplayer = listoplayer.get(i%4);
            Pair deal = new Pair(Card.NONE,Card.NONE);
            if(this.deck.numberOfCardsRemaining() ==0){
                Card c1 = Card.NONE;
                Card c2 = Card.NONE;
                deal = new Pair(c1,c2);
            }
            else{
            Card c1 = deck.drawACard();
            Card c2= deck.drawACard();
                 deal = new Pair(c1,c2);}


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
            ArrayList<Integer> HortVertBonus = FindifValid.FindBonusVals(this.map);
            for(Player P: this.listoplayer){
                System.out.println("Player and score");
                System.out.println(P.getBaron().toString()+": " + P.getScore());
                //PlayerPerScore.put(P,P.getScore());
                ArrayList<Boolean> TruthList = FindifValid.BonusQ(P,this.map);
                System.out.println(TruthList +": "+ P.getBaron().toString());
                //First index is horizontal truth
                if(TruthList.get(0) == true){
                    //Add bonus
                    if(P instanceof ComputerPlayer){
                      ComputerPlayer CP = (ComputerPlayer) P;
                        int val = P.getScore();
                        val += HortVertBonus.get(0)* 5;
                        CP.score = val;
                        CP.Updateall();

                    }
                    else{
                        student.Player CP = (student.Player) P;
                        int val = P.getScore();
                        val += HortVertBonus.get(0)* 5;
                        CP.score = val;
                        CP.Updateall();

                    }
                    //int val = P.getScore();
                    //val += HortVertBonus.get(0)* 5;
                    //P.setScore(val);
                    //PlayerPerScore.put(P,val);
                }
                //Second value is vertical truth
                if(TruthList.get(1) == true){
                if(P instanceof ComputerPlayer){
                    ComputerPlayer CP = (ComputerPlayer) P;
                    int val = P.getScore();
                    val += HortVertBonus.get(0)* 5;
                    CP.score = val;
                    CP.Updateall();
                }
                else{
                    student.Player CP = (student.Player) P;
                    int val = P.getScore();
                    val += HortVertBonus.get(0)* 5;
                    CP.score = val;
                    CP.Updateall();

                }}
                 //PlayerPerScore.put(P,val);
                }
                Player Winner = new student.Player();
                int comp = 0;
                for(Player P2: this.listoplayer){
                    if(P2.getScore()> comp){
                        comp = P2.getScore();
                        Winner = P2;
                    }
                }
                /**
                 for(Player P2: PlayerPerScore.keySet()){

                 //System.out.println(P2.toString() + P2.getScore());
                 if(PlayerPerScore.get(P2) > comp){
                 Winner = P2;
                 comp = PlayerPerScore.get(P2);
                 }
                 }**/
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
        return this.currentplayer;
    }

    /**
     * Finds all the currently playing players
     * @return - Collection - Players
     */
    @Override
    public Collection<Player> getPlayers() {
        return this.listoplayer;
    }

    /**
     * Indicates whether or not the game is over.
     * @return - true or false
     */
    @Override
    public boolean gameIsOver() {
        int shortest = map.getLengthOfShortestUnclaimedRoute();
        //System.out.println(shortest);
        //System.out.println(shortest);
        int i = 0;
        //I is a counter to see how many player can still play
        //System.out.println("Card remaining"+this.deck.numberOfCardsRemaining());
        if(this.deck.numberOfCardsRemaining() == 0){
            //System.out.println("Num of card is 0");
            for (model.Player p : this.listoplayer) {
                if (p.canContinuePlaying(shortest)) {
                } else {
                    i++;
                }
            }}
        //System.out.println("Num of players" +i);
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

    /**
     * Returns a collection of Rep stations
     * @param P - Player
     * @return - Collection<Rep>
     */
    public static Collection<Rep> MakeAll(Player P){
        HashMap<Station,Rep> CheckIfIn = new HashMap<>();
        for(model.Route R: P.getClaimedRoutes()){
            model.Station S1 = R.getDestination();
            model.Station S2 = R.getOrigin();
            student.Rep N1 = new student.Rep(S1,0);
            student.Rep N2 = new student.Rep(S2,0);
            if(!CheckIfIn.containsKey(S1)){
                N1.AddNeighbor(N2);
                CheckIfIn.put(S1,N1);

            }
            else{
                //Its the node has already been made

                //If the other station is not already in the neighbor list
                //Add it in
                if(!CheckIfIn.get(S1).getNeighbors().contains(N2)){
                    CheckIfIn.get(S1).AddNeighbor(N2);
                }
            }
            if(!CheckIfIn.containsKey(S2)){

                N2.AddNeighbor(N1);
                CheckIfIn.put(S2,N2);
            }
            else{
                //The node has already been made
                if(!CheckIfIn.get(S2).getNeighbors().contains(N1)){
                    CheckIfIn.get(S2).AddNeighbor(N1);
                }

            }


        }
        LinkedList<Rep> ReturnL = new LinkedList<>();
        for(model.Station S: CheckIfIn.keySet()){
            ReturnL.add(CheckIfIn.get(S));
        }
        return ReturnL;
    }




}
