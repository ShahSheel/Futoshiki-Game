package futoshikipuzzle;

import java.io.Serializable;

/**
 *
 * @author Sheel Shah
 */
public abstract class FutoShikiConstraints implements Serializable{

    int row, column, row2, column2;
    FutoShikiPuzzle puzzle;
    /**
     * Constructor, creating an instance of a FutoShikiConstraints.
     * @param row , row for first square
     * @param column, column for fist square
     * @param row2 , row2 for second square
     * @param column2 , column 2 for second square
     * @param puzzle  , initialising  futoshikipuzzle
     */
    public FutoShikiConstraints(int row, int column, int row2, int column2, FutoShikiPuzzle puzzle) {
        this.row = row;
        this.column = column;
        this.row2 = row2;
        this.column2 = column2;
        this.puzzle = puzzle;
    }
    /**
     * Gets the first square
     * @return GetFirstSquare
     */
    public FutoShikiSquare getFirstSquare() {
        return puzzle.getSquare(row, column);
    }
    /**
     * Gets the second square
     * @return getSecondSquare
     */
    public FutoShikiSquare getSecondSquare() {
        return puzzle.getSquare(row2, column2);
    }
    
    /**
     * Calls abstract method
     * @return boolean
     */
    public abstract boolean checkConstraint();

    /**
     *
     * @return constraint symbol
     */
    public abstract String getSymbol();

}
