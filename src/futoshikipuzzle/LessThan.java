package futoshikipuzzle;

import java.io.Serializable;

/**
 *
 * @author Sheel Shah
 */
public class LessThan extends FutoShikiConstraints implements Serializable {
    
    /**
     * Creating the instance of subclass
     * @param row get row for first square
     * @param column get column for first square
     * @param row2 get row2 for second square
     * @param column2 get column2 for second square
     * @param puzzle initialising puzzle
     */
    public LessThan(int row, int column, int row2, int column2, FutoShikiPuzzle puzzle) {
        super(row, column, row2, column2, puzzle);
    }
   
    /**
     * Compares the first and second square and checks if the first square is greater than the second square 
     * Returns True if the first is greater than second square,else false
     * @return  checkConstraint
     */
    @Override
    public boolean checkConstraint() {
        FutoShikiSquare FirstSquare = getFirstSquare();
        FutoShikiSquare SecondSquare = getSecondSquare();

        if (!(FirstSquare.getSquareNumber() < SecondSquare.getSquareNumber())) {
            if (((SecondSquare).getSquareNumber() != 0) && (FirstSquare).getSquareNumber() != 0) {
                return false;
            }
        }
        if (FirstSquare.getSquareNumber() == SecondSquare.getSquareNumber()) {
            if ((SecondSquare.getSquareNumber() != 0) && FirstSquare.getSquareNumber() != 0) {
                return false;
            }
        }
        return true;
    }
     /**
     * Inserts the constraints at the specific place 
     * @return syombol
     */
    @Override
    public String getSymbol() {
        if (row == row2) {
            return "<";
        } else {            
            return "^";
        }
    }

}
