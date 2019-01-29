package student;

/**
 * Represents a space on the Railroad Barons map
 * @author Jack Xu
 * @author Savannah Mattia
 */

public class Space implements model.Space{
    private int Column;
    private int Row;

    /**
     * constructor for space
     * @param Row - int
     * @param Column - int
     */
    public Space(int Row, int Column){
        this.Column = Column;
        this.Row = Row;
    }

    /**
     * Finds row of the space's location in the map
     * @return - int - row
     */
    public int getRow(){
        return this.Row;
    }

    /**
     * Finds the column of the space's location in the map
     * @return - int - column
     */
    public int getCol(){
        return this.Column;
    }

    /**
     * Finds if the space is occupying rhe same physical location in the map as the parameter space
     * @param other The other space to which this space is being compared for
     *              collocation.
     *
     * @return - true or false
     */
    @Override
    public boolean collocated(model.Space other) {
        return (this.Row == other.getRow() && this.Column == other.getCol());
    }


}
