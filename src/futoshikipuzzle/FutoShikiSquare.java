package futoshikipuzzle;

import java.io.Serializable;

/**
 *
 * @author Sheel Shah
 */
public class FutoShikiSquare implements Serializable{

    /**
     *
     */
    protected int squareNumber;

    /**
     *
     */
    protected String pencilMark;

    /**
     * *
     * Sets the square with the number(Uses FutoShikiPuzzle.setSquare)
     *
     * @param userInput checks if userInput is legal
     */
    public FutoShikiSquare(int userInput) {
        if (userInput < 0) {
            throw new IllegalArgumentException("Input must be positive");
        } else {
            squareNumber = userInput;
        }
    }

    /**
     * *
     * Gets the selected square
     *
     * @param squareNumber set number
     */
    public void setSquareNumber(int squareNumber) {
        this.squareNumber = squareNumber;
    }

    /**
     * Returns the square
     *
     * @return get number
     */
    public int getSquareNumber() {
        return squareNumber;
    }

    /**
     * *
     * Sets recorded notes to help solving the puzzle
     *
     * @param pencilMark set pencil mark
     */
    public void setPencilMark(String pencilMark) {
        this.pencilMark = pencilMark;
    }

    /**
     * *
     * Returns pencil mark
     *
     * @return pencil mark
     */
    public String getPencilMark() {
        return pencilMark;
    }

}
