package student;

import model.*;
import model.Space;
import student.Route;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * Represents a Railroad Barons map, which consists of empty spaces, stations, tracks, and routes.
 *
 * @author Jack Xu
 * @author Savannah Mattia
 */

public class RailRoadMap implements RailroadMap {

    private int rows;
    private int cols;
    private Collection<model.Route> routes;
    private Collection<model.RailroadMapObserver> observers;
    private model.Space[][] twoDSpace;
    private HashMap<Integer,Station> station;
    private HashMap<Station,Integer> stationtoint;
    private student.Track[][] twoDTrack;

    /**
     * constructor for RailRoadMap
     * @param rows - int
     * @param cols - int
     * @param routes - Collection
     */
    public RailRoadMap(int rows, int cols, Collection<model.Route> routes){
        this.rows = rows;
        this.cols = cols;
        this.routes = routes;
        this.observers = new LinkedList<>();
        this.twoDSpace = new model.Space[rows][cols];
        for(model.Route r: routes){
            model.Station Begin = r.getOrigin();
            model.Station End = r.getDestination();
            Collection<model.Track> AL = r.getTracks();
            int Beginr = Begin.getRow();
            int Beginc = Begin.getCol();
            this.twoDSpace[Beginr][Beginc] = Begin;
            int Endr = End.getRow();
            int Endc = End.getCol();
            this.twoDSpace[Endr][Endc] = End;
            for( model.Track tr: AL){
                int TrackR = tr.getRow();
                int TrackC = tr.getCol();
                this.twoDSpace[TrackR][TrackC] = tr;
            }
        }
    }

    /**
     * adds the specified observer to the map
     * @param observer The {@link RailroadMapObserver} being added to the map.
     */
    @Override
    public void addObserver(RailroadMapObserver observer) {
            this.observers.add(observer);
    }

    /**
     * Removes the specified observer from the map
     * @param observer The observer to remove from the collection of
     *                 registered observers that will be notified of
     */
    @Override
    public void removeObserver(RailroadMapObserver observer) {
            this.observers.remove(observer);
    }


    /**
     * the total rows in the map
     * @return - int
     */
    @Override
    public int getRows() {
        return rows;
    }


    /**
     * the total columns in the map
     * @return - int
     */
    @Override
    public int getCols() {
        return cols;
    }

    /**
     * the space located at the specified cooridinates
     * @param row The row of the desired {@link Space}.
     * @param col The column of the desired {@link Space}.
     *
     * @return - Space
     */
    @Override
    public Space getSpace(int row, int col) {
        return this.twoDSpace[row][col];

    }

    /**
     * The route that contains the track at the specified location
     * @param row The row of the location of one of the {@link Track tracks}
     *            in the route.
     * @param col The column of the location of one of the
     * {@link Track tracks} in the route.
     *
     * @return - Route
     */
    @Override
    public model.Route getRoute(int row, int col) {

        for(model.Route r: this.routes){
            for(model.Track tr: r.getTracks()){
                if (tr.getCol() == col){
                    if(tr.getRow() == row){
                        return r;
                    }
                }
            }
        }
        return null;
    }

    /**
     * Called to update the map when a Baron has claimed a route
     * @param route The {@link Route} that has been claimed.
     */
    @Override
    public void routeClaimed(model.Route route) {
            for(RailroadMapObserver ob: observers){
                    ob.routeClaimed(this,route);
            }
    }

    /**
     * Finds length of shortest unclaimed route in map
     * @return - int
     */
    @Override
    public int getLengthOfShortestUnclaimedRoute() {
        int comp =1000000;
        for(model.Route c: routes){
            if(c.getBaron() == null){
                if (c.getLength() < comp){
                    comp = c.getLength();
            }
        }}
        return comp;
    }

    /**
     * Finds all of the routes in the map
     * @return - Collection - Routes
     */
    @Override
    public Collection<model.Route> getRoutes() {
        return this.routes;
    }


}
