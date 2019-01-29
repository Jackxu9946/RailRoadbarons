package student;

import model.Baron;
import model.Orientation;
import model.Route;
import model.Space;

/**
 * A track segment on the map. Combines to form routes.
 * @author Jack Xu
 * @author Savannah Mattia
 */

public class Track implements model.Track{

    private Baron baron;
    private Route route;
    private student.Space space;
    private Orientation or;

    /**
     * Constructor for track
     * @param baron - Baron
     * @param route - Route
     * @param row - int
     * @param column - int
     * @param or - Orientation
     */
    public Track(Baron baron, Route route, int row, int column, Orientation or){
        this.baron = baron;
        this.route = route;
        this.space = new student.Space(row,column);
        this.or = or;
    }

    /**
     * Finds the orientation of the track; horizontal or vertical
     * @return - Orientation
     */
    @Override
    public Orientation getOrientation() {
        return this.or;
    }

    /**
     * Current owner of the track or if it's unclaimed
     * @return - Baron
     */
    @Override
    public Baron getBaron() {
        return this.baron;
    }

    /**
     * Finds the route of which this track is a part.
     * @return - Route
     */
    @Override
    public Route getRoute() {
        return this.route;
    }

    /**
     * Finds row of the track's location in the map
     * @return - int - row
     */
    @Override
    public int getRow() {
        return space.getRow();
    }

    /**
     * Finds the column of the track's location in the map
     * @return - int - column
     */
    @Override
    public int getCol() {
        return space.getCol();
    }

    /**
     * Finds if the track is occupying the same physical location in the map as the parameter space
     * @param other The other space to which this space is being compared for
     *              collocation.
     *
     * @return - true or false
     */
    @Override
    public boolean collocated(Space other) {
        return this.space.collocated(other);
    }
}
