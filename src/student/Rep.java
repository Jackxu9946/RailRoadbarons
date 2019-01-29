
package student;

import model.Station;

import java.util.LinkedList;

/**
 * Finds stations and if the station is next to another it is added to a list of Neighbors
 * @author Jack Xu
 * @author Savannah Mattia
 */

public class Rep {
    private model.Station Station;
    private int I;
    private LinkedList<Rep> Neighbors;
    private int Rank;

    /**
     * Constructor for Rep
     * @param Station - model.Station
     * @param I - int
     */
    public Rep(model.Station Station,int I){
        this.Station = Station;
        this.I = I;
        this.Neighbors = new LinkedList<>();
        this.Rank = 0;
    }
    public void setRank(int Rank){
        this.Rank = Rank;
    }
    public int getRank(){
        return this.Rank;
    }

    /**
     * finds the station
     * @return - model.Station
     */
    public model.Station getStation(){
        return this.Station;
    }

    /**
     * Finds the integer I
     * @return - int
     */
    public int getI(){
        return this.I;
    }

    /**
     * Finds a list of the neighbors
     * @return - LinkedList<Rep>
     */
    public LinkedList<Rep> getNeighbors(){
        return this.Neighbors;
    }

    /**
     * Adds a neighbor station to the LinkedList Neighbors
     * @param N - Rep
     */
    public void AddNeighbor(Rep N){
        //System.out.println(N.toString());
        this.Neighbors.add(N);
    }

    /**
     * finds the name of the station
     * @return - String
     */
    public String toString(){
        String S = "";
        S += this.Station.getName();
        return S;
    }

    /**
     * increments int I
     */
    public void IncreaseI(){
        this.I ++;
    }

    /**
     * finds if the current station and another station are collocate
     * @param Other - Rep
     * @return true or false
     */
    public boolean equals(Rep Other){

        if(this.getStation().collocated(Other.getStation())){

            return true;
        }
        return false;

}
}
