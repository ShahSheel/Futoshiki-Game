package futoshikipuzzle;

import java.io.Serializable;

/**
 *
 * @author Sheel Shah
 */
public class NoConstraint extends FutoShikiConstraints implements Serializable {
    
     /**
     * Creating the instance of subclass
     * @param row get row for first square
     * @param column get column for first square
     * @param row2 get row2 for second square
     * @param column2 get column2 for second square
     * @param puzzle initialising puzzle
     */
    public NoConstraint(int row, int column, int row2, int column2, FutoShikiPuzzle puzzle) {
        super(row, column, row2, column2, puzzle);
    }
    /**
     * Returns true if there is no constraint
     * @return checkConstraint
     */
    @Override
    public boolean checkConstraint() {
        return true;
    }
    /**
     * Inserts a blank space
     * @return symbol
     */
    @Override
    public String getSymbol() {
        return " ";
    }
}
