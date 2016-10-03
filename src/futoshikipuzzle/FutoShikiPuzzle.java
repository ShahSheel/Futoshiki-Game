package futoshikipuzzle;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;
import java.util.HashSet;
import java.util.List;

/**
 *
 * @author Sheel Shah
 */
public class FutoShikiPuzzle implements Serializable {

    int gridSize = 5;
    final FutoShikiSquare grid[][];
    final FutoShikiConstraints[][] rowConstraints;
    final FutoShikiConstraints[][] columnConstraints;
    private int row1;
    private int col1;
    private final Random randomnNumber = new Random();
    private FutoshikiFrame ui;
    /**
     * ArrayList of printProblems, all problems such as users input mistakes,
     * duplication of row numbers are stored here.
     */
    protected List<String> printProblems = new ArrayList<>();

    /**
     * Constructor, setting up grid, rowConstraint and columnConstraints.
     * Setting grid, rowConstraint and columnConstraints to 0 and then changing
     * the constraints / numbers.
     *
     * @param gridSize user enters gridSize
     */
    public FutoShikiPuzzle(int gridSize) {
        grid = new FutoShikiSquare[gridSize][gridSize];
        rowConstraints = new FutoShikiConstraints[gridSize][gridSize - 1]; // FIX so it holds > <  ^ V
        columnConstraints = new FutoShikiConstraints[gridSize - 1][gridSize];
        //Setting all squares to 0's
        for (int row = 0; row < grid.length; row++) {
            for (int column = 0; column < grid.length; column++) {
                grid[row][column] = new FutoShikiSquare(0);
            }
        }
        // Setting row constraints to NoConstraints
        for (int row = 0; row < grid.length; row++) {
            for (int column = 0; column < grid.length - 1; column++) {
                rowConstraints[row][column] = new NoConstraint(row, column, row, column + 1, this);
            }
        }
        // Setting column constraints to NoConstraints
        for (int row = 0; row < grid.length - 1; row++) {
            for (int column = 0; column < grid.length; column++) {
                columnConstraints[row][column] = new NoConstraint(row, column, row + 1, column, this);
            }
        }

    }

    /**
     * Sets the public interface
     *
     * @param ui userInterface
     */
    protected void setGUI(FutoshikiFrame ui) {
        this.ui = ui;
    }

    /**
     * Setting a value in a specific square, calls the squareNumber in
     * FutoShikiSquare UserInput value is then stored in that square
     *
     * @param row gets row
     * @param column gets column
     * @param userInput sets userInput into specified row and column
     */
    public void setSquare(int row, int column, int userInput) {

        //grid[row][column].setSquareNumber(userInput);
        grid[row][column] = new FutoShikiSquare(userInput);

    }

    /**
     * Getting the value from a specific square
     *
     * @param row gets row
     * @param column gets column
     * @return number
     */
    public FutoShikiSquare getSquare(int row, int column) {
        return grid[row][column];
    }

    /**
     * Setting rowConstraint at a specific row and column.
     *
     * @param row gets row
     * @param column gets column
     * @param constraint gets constraint
     */
    public void setRowConstraint(int row, int column, String constraint) {
        switch (constraint) {
            case "<":
                rowConstraints[row][column] = new LessThan(row, column, row, column + 1, this);
                break;
            case ">":
                rowConstraints[row][column] = new GreaterThan(row, column, row, column + 1, this);
                break;
        }
    }

    /**
     * Setting columnConstraint at a specific row and column
     *
     * @param column gets column
     * @param row gets row
     * @param constraint String constraint
     */
    public void setColumnConstraint(int column, int row, String constraint) {
        switch (constraint) {
            case "V":
                columnConstraints[column][row] = new LessThan(row, column, row + 1, column, this);
                break;
            case "^":
                columnConstraints[column][row] = new GreaterThan(row, column, row + 1, column, this);
                break;
        }
    }

    /**
     *
     * @param row sets row
     * @param col sets col
     * @return rowConstraint
     */
    public FutoShikiConstraints getRowConstraints(int row, int col) {
        return rowConstraints[row][col];
    }

    /**
     *
     * @param row get row
     * @param col get col
     * @return columnConstraint
     */
    public FutoShikiConstraints getColumnConstraints(int row, int col) {
        return columnConstraints[row][col];
    }

    /**
     * Generates random rowConstraints and inserts them into the grid by calling
     * sub class methods from FutoShikiConstraints
     */
    private void generateRowConstraint() {
        for (int column = 0; column < grid.length - 1; column++) {
            for (int row = 0; row < grid.length; row++) {
                int numberGenerated = randomnNumber.nextInt(8);

                switch (numberGenerated) {
                    case 1:
                        rowConstraints[row][column] = new GreaterThan(row, column, row, column + 1, this);
                        break;
                    case 2:
                        rowConstraints[row][column] = new LessThan(row, column, row, column + 1, this);
                        break;
                    default:
                        rowConstraints[row][column] = new NoConstraint(row, column, row, column + 1, this);
                        break;
                }

            }
        }
    }

    /**
     * Generates random column constraints and inserts them into the grid by
     * calling sub class methods from FutoShikiConstraints
     */
    private void generateColumnConstraint() {
        for (int column = 0; column < grid.length; column++) {
            for (int row = 0; row < grid.length - 1; row++) {
                int numberGenerated = randomnNumber.nextInt(26);
                switch (numberGenerated) {
                    case 1:
                        columnConstraints[row][column] = new GreaterThan(row, column, row + 1, column, this);
                        break;
                    case 2:
                        columnConstraints[row][column] = new LessThan(row, column, row + 1, column, this);
                        break;
                    default:
                        columnConstraints[row][column] = new NoConstraint(row, column, row + 1, column, this);
                        break;
                }
            }
        }
    }

    /**
     * Partially fills the grid with random values. MAX is gridLength
     */
    public void fillPuzzle() {
        // for (int row = 0; row < grid.length/2; row++) {
        //     for (int column = 0; column < grid.length/2; column++) {
        int positionIndex1 = randomnNumber.nextInt(grid.length); // Random number decides position index, fills that position index with another random number
        int positionIndex = randomnNumber.nextInt(grid.length); // Random number decides position index, fills that position index with another random number
        int fillNumber = randomnNumber.nextInt(grid.length + 1);

        grid[positionIndex][positionIndex1].setSquareNumber(fillNumber);
        //          }

        //    }
        generateRowConstraint();
        generateColumnConstraint();
    }

    /**
     * Checks duplications by using a HashSet in column Adds a string -
     * printProblems to a ArrayList if there is one
     *
     * @return
     */
    private List<String> checkColumn() {

        for (int row = 0; row < grid.length; row++) {
            HashSet<Integer> set = new HashSet<>();
            for (int column = 0; column < grid.length; column++) {
                if (set.contains(grid[column][row].getSquareNumber()) && grid[column][row].getSquareNumber() > 0) {
                    printProblems.add("Co-ords: " + "(" + (row + 1) + ":" + (column + 1) + ") " + "Duplicated column number: " + grid[column][row].getSquareNumber() + "\n");
                }
                set.add(grid[column][row].getSquareNumber());
            }
        }
        return printProblems;
    }

    /**
     * Checks duplications by using a HashSet in row Adds a string to a
     * ArrayList "printProblems" if there is one
     *
     * @return
     */
    private List<String> checkRow() {
        for (int row = 0; row < grid.length; row++) {
            HashSet<Integer> set = new HashSet<>();
            for (int column = 0; column < grid.length; column++) {
                if (set.contains(grid[row][column].getSquareNumber()) && grid[row][column].getSquareNumber() > 0) {
                    printProblems.add("Co-ords: " + "(" + (row + 1) + ":" + (column + 1) + ") " + "Duplicated row number: " + grid[row][column].getSquareNumber() + "\n");
                }
                set.add(grid[row][column].getSquareNumber());
            }
        }
        return printProblems;
    }

    /**
     * Checks to see if generated rowConstraints is legal by calling
     * checkConstraint in FutoShikiConstraints object. If it is not, adds the
     * String - printProblems to an ArrayList
     *
     * @return
     *
     */
    private List<String> checkRowConstraints() {
        for (int row = 0; row < grid.length; row++) {
            for (int column = 0; column < grid.length - 1; column++) {
                FutoShikiConstraints constraint = rowConstraints[row][column];
                if (!constraint.checkConstraint()) {
                    printProblems.add("Co-ords " + "(" + (row + 1) + ":" + (column + 1) + ")" + "doesn't match the constraint needed " + constraint.getSymbol() + "\n");
                }
            }
        }
        return printProblems;
    }

    /**
     * Checks to see if generated columnConstraints is legal by calling
     * CheckConstraints. If returns true, then adds a String - printProblems to
     * an ArrayList the String to an ArrayList
     *
     * @return
     */
    private List<String> checkColumnConstraints() {
        for (int row = 0; row < grid.length - 1; row++) {
            for (int column = 0; column < grid.length; column++) {
                FutoShikiConstraints constraint = columnConstraints[row][column];
                if (!constraint.checkConstraint()) {
                    printProblems.add("Co-ords " + "(" + (column + 1) + ":" + (row + 1) + ")" + " doesn't match the constraint needed " + constraint.getSymbol() + "\n");
                }
            }
        }
        return printProblems;
    }

    /**
     * Checks if the ArrayList - printProblems is empty, if empty shows grid is
     * legal. Else, calls getProblems.
     *
     * @return isLegal
     */
    public boolean isLegal() {
        boolean legalCheck;

        if (checkRowConstraints().isEmpty() && checkRow().isEmpty() && checkColumn().isEmpty() && checkColumnConstraints().isEmpty()) {
            System.out.print("Puzzle is legal!" + "\n");
            legalCheck = true;
        } else {
            getProblems();
            legalCheck = false;

        }

        return legalCheck;
    }

    /**
     * Gets called by isLegal if the ArrayList is not empty. Prints out the
     * ArrayList which contains the Strings of problems
     *
     * @return arrayList of printProblems
     */
    protected List<String> getProblems() {
        return printProblems;
    }

    /**
     *
     * @return isEmpty
     */
    protected boolean isEmpty() {
        for (int row = 0; row < grid.length; row++) {
            for (int column = 0; column < grid.length; column++) {
                if (grid[row][column].getSquareNumber() == 0) {
                    row1 = row;
                    col1 = column;
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Generates the Futoshiki grid and implements rowConstraints and
     * columnConstraints along with numbers generated by FillPuzzle
     *
     * @return generated grid
     */
    @Override
    public String toString() {
        String generatedGrid = "";
        for (int row = 0; row < grid.length; row++) {
            for (int i = 0; i < grid.length; i++) {
                generatedGrid += "  ---   "; // Pritning top cells seperated by spaces
            }
            generatedGrid += "\n";
            for (int column = 0; column < grid.length; column++) {
                if (grid[row][column].getSquareNumber() == 0) {
                    generatedGrid += "  " + "|" + " " + "|" + "  ";
                    if (column < grid.length - 1) {
                        generatedGrid += rowConstraints[row][column].getSymbol();
                    }
                } else {
                    generatedGrid += "  " + "|" + grid[row][column].getSquareNumber() + "|" + "  ";
                    if (column < grid.length - 1) {
                        generatedGrid += rowConstraints[row][column].getSymbol();
                    }
                }
            }
            generatedGrid += "\n";
            for (int j = 0; j < grid.length; j++) {
                generatedGrid += "  ---   ";
            }
            generatedGrid += "\n";
            if (row < grid.length - 1) {
                for (int column = 0; column < grid.length; column++) {
                    if (" ".equals(columnConstraints[row][column].getSymbol())) {
                        generatedGrid += "        ";
                    } else {
                        generatedGrid += "   " + columnConstraints[row][column].getSymbol() + "    ";
                    }
                }
            }
            generatedGrid += "\n";
        }

        return generatedGrid;
    }

    /**
     * Deep-search algorithm involving backtracking and recursion. Displays a
     * solution onto the grid
     *
     * @return solve
     */
    protected boolean Solve() {
        boolean solutionSolved = false;
        int counter = 1;

        if (!isLegal()) {
            return false;
        }
        if (!isEmpty()) {
            return true;
        } else {
            for (int row = 0; row < grid.length; row++) {
                for (int column = 0; column < grid.length; column++) {
                    if (grid[row][column].getSquareNumber() == 0) {
                        while ((!solutionSolved) && (counter <= grid.length)) {
                            grid[row][column].setSquareNumber(counter);
                            solutionSolved = Solve();
                            counter++;
                            printProblems.clear();
                        }
                        if (!solutionSolved) {
                            grid[row][column].setSquareNumber(0);
                        } else {
                            return solutionSolved;
                        }
                    }
                    if (!isLegal()) {
                        row--;
                        grid[row][column].setSquareNumber(counter);
                        Solve();

                    }
                }
            }
        }
        return solutionSolved;
    }
}
