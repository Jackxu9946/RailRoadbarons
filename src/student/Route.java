package student;

import model.Baron;
import model.Orientation;
import model.Space;
import model.Station;
import model.Track;

import java.util.LinkedList;
import java.util.List;

/**
 * The route represents a train route for the game. It is made from tracks and is in between two stations on the map.
 * @author Jack Xu
 * @author Savannah Mattia
 */

public class Route implements model.Route {
    private model.Station Begin;
    private model.Station End;
    private LinkedList<Track> TrackSet;
    private Orientation or;
    private Baron baron;

    /**
     * constructor for Route
     * @param Begin - Station
     * @param End - Station
     * @param or - Orientation
     */
    public Route(model.Station Begin, model.Station End, Orientation or){
        this.Begin = Begin;
        this.End = End;
        this.or = or;
        this.TrackSet = new LinkedList<Track>();
        if (or.equals(Orientation.VERTICAL)){
            //Go Vertical
            //Begin on the top
            //End on the bottom
            int Column = End.getCol();
            int distance = End.getRow() - Begin.getRow()-1;
            System.out.println("This is distnace"+distance);
            int InitialRow = Begin.getRow();
            //System.out.println(distance);
            for (int i =0; i < distance; i++){
                InitialRow++;

                student.Track Temp = new student.Track(baron.UNCLAIMED,this,InitialRow,Column,or);
                //System.out.println(InitialRow);
                TrackSet.add(Temp);
             }




        }
        if( or.equals(Orientation.HORIZONTAL)){
            //Go Horizontal
            int Row = End.getRow();
            int distance = End.getCol() - Begin.getCol()-1;
            System.out.println("This is distance"+distance);
            int initialCol = Begin.getCol();
            for(int i =0; i < distance; i++){
                initialCol++;

                Track Temp2 = new student.Track(Baron.UNCLAIMED,this,Row,initialCol,or);
                TrackSet.add(Temp2);
            }


        }

    }

    /**
     * Finds the baron of the route
     * @return - baron
     */
    @Override
    public Baron getBaron() {
         return this.baron;
    }

    /**
     * Finds the station that the train starts at
     * @return - Station
     */
    @Override
    public model.Station getOrigin() {
        return this.Begin;
    }

    /**
     * Finds the station that the train ends at
     * @return - Station
     */
    @Override
    public model.Station getDestination() {
        return this.End;
    }

    /**
     * Finds the orientation of the route
     * @return - Orientation
     */
    @Override
    public Orientation getOrientation() {
        return this.or;
    }

    /**
     * Gets a list of the tracks on the route
     * @return - List of tracks
     */
    @Override
    public List<model.Track> getTracks() {
        return this.TrackSet;
    }

    /**
     * Finds the length of the route
     * @return - int - length
     */
    @Override
    public int getLength() {
        return this.TrackSet.size();
    }

    /**
     * Finds the number of points that the route is worth
     * @return - int
     */
    @Override
    public int getPointValue() {
        int length = this.getLength();
        int point = 0;
        System.out.println(length);
        if( length ==1){
            point =1;
        }
        else if(length ==2){
            point = 2;
        }
        else if(length ==3){
            point =4;
        }
        else if(length ==4){
            point = 7;
        }
        else{
            point = 5*(length-3);
        }
        return point;
    }

    /**
     * This checks if the space is contained within the track
     * @param space The {@link Space} that may be in this route.
     *
     * @return
     */
    @Override
    public boolean includesCoordinate(Space space) {
        for (Track t: this.TrackSet){
            if (t.collocated(space) == true){
                return true;
            }
            }
        return false;

    }

    /**
     * If the baron isn't null then the baron is claimant else it's false
     * @param claimant The {@link Baron} attempting to claim the route. Must
     *                 not be null or {@link Baron#UNCLAIMED}.
     * @return - true or false
     */
    @Override
    public boolean claim(Baron claimant) {
        if (this.baron == null){
            this.baron = claimant;
            //System.out.println(this.baron);
            return true;
        }
        else{
            return false;
        }
    }
}
