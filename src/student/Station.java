package student;

import model.Space;

/**
 * Represents the train station on the map. A train station is a space that has a name and is at one end of at
 * least one train route
 *
 * @author Jack Xu
 * @author Savannah Mattia
 */
public class Station implements model.Station {
    private String Name;
    private student.Space Space;
    private int stationnumber;
    public Station(int row, int column, String Name,int stationnumber){

    /**
     * constructor for station
     * @param row - int
     * @param column - int
     * @param Name - String
     */
        this.Space = new student.Space(row, column);
        this.Name = Name;
        this.stationnumber = stationnumber;
    }

    /**
     * The name of the station
     * @return - String
     */
    public String getName() {
        return this.Name;
    }

    /**
     * Finds row of the station's location in the map
     * @return - int - row
     */
    @Override
    public int getRow() {
        return Space.getRow();
    }


    /**
     * Finds the column of the station's location in the map
     * @return - int - column
     */
    @Override
    public int getCol() {
        return Space.getCol();
    }



    /**
     * Finds if the station is occupying rhe same physical location in the map as the parameter space
     * @param other The other space to which this space is being compared for
     *              collocation.
     *
     * @return - true or false
     */
    @Override
    public boolean collocated(Space other) {
        return this.Space.collocated(other);
    }
}

