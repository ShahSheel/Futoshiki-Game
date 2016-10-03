/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package futoshikipuzzle;

import java.util.Random;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.ComparisonFailure;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

/**
 *
 * @author Sheel Shah
 */
public class FutoShikiPuzzleTest {

    FutoShikiPuzzle puzzleTest;
    int userInput;

    @Before
    public void setUp() {
        puzzleTest = new FutoShikiPuzzle(5);

    }
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    /**
     * Test of setSquare method when inputs is legal, of class FutoShikiPuzzle.
     */
    @Test
    public void TestSetSquare() {
        puzzleTest.setSquare(0, 0, 5);
        Assert.assertEquals(0, 0);
        //Throw no Exception

    }

    /*** 
     * Test for set square when row and column is out of bounds
     */
    @Test(expected = IndexOutOfBoundsException.class)
    public void setSquareWhenRowAndColumnIsGreaterThanGrid() {
        puzzleTest.setSquare(10, 10, 3);
        assertEquals(10, 10);
        //Throw Exception
    }
    /**
     * Test to see if you can change the number after inputting a number
     */
    @Test
    public void setSquareOverridingInput() {
        puzzleTest.setSquare(0, 0, 1);
        Assert.assertEquals(0, 0);
        puzzleTest.setSquare(0, 0, 2);
        Assert.assertEquals(0, 0);

        // Can override! Great!
    }
    /**
     * Test to see if you can enter strings instead of Intergers
     */
    @Test(expected = IllegalArgumentException.class)
    public void setSquareTestIllegalExpressions() {
        //You cannot enter invalid characters since it's an interger! 
    }

    /**
     * Test of setRowConstraint when constraints input is legal in class FutoShikiPuzzle.
     */
    @Test
    public void TestsetRowConstraint() {

        puzzleTest.setRowConstraint(3, 0, "<");
        Assert.assertEquals("<", "<");

        puzzleTest.setRowConstraint(4, 0, ">");
        Assert.assertEquals(">", ">");

    }
    /**
     * Test to see if you can change the input constraint 
     */
    @Test(expected = IllegalArgumentException.class)
    public void setRowConstraintOverridingInputConstraint() {

        try {
            puzzleTest.setRowConstraint(3, 0, "<");
            Assert.assertEquals(">", ">");
            puzzleTest.setRowConstraint(3, 0, "<");
            Assert.assertEquals("<", "<");
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Illegal argument Exception " + e.getMessage());

        }
    }
    /**
     * Test to see if you can input a a column constraint when requires a row
     */
    @Test(expected = ComparisonFailure.class)
    public void setRowConstraintWhenInputIsColumn() {

        try {
            puzzleTest.setRowConstraint(3, 0, "^");
            Assert.assertEquals("<", "^");

        } catch (AssertionError e) {
            System.err.println("Caught an AssertionError..." + e.getMessage());
        }

    }
    /**
     * Test to see if you can enter more than one constraints at a time
     */
    @Test(expected = ComparisonFailure.class)
    public void setRowConstraintWhenInputContainsMoreThanOneConstraint() {
        try {
            puzzleTest.setRowConstraint(3, 0, "^V^<v");
            Assert.assertEquals("^V^<v", "^");
        } catch (AssertionError e) {
            System.err.println("Caught an AssertionError..." + e.getMessage());
        }
    }

    /**
     * Test to see if row Constrains can be asserted out of bounds
     */
    @Test(expected = IndexOutOfBoundsException.class)
    public void setRowConstraintsTestOutOfBounds() {
        try {
            puzzleTest.setColumnConstraint(100, 100, "<");
            Assert.assertEquals(this, this);
        } catch (ComparisonFailure e) {
            fail("IndexOutOfBoundsException");
            System.err.println("Caught an IndexOutOfBoundsException for rowConstraints... " + e.getMessage());
        }
    }

    /**
     * Set ColumnConstraint when input is legal  in FutoShikiPuzzle
     */
    @Test
    public void TestsetColumnConstraint() {

        puzzleTest.setRowConstraint(3, 0, "^");
        Assert.assertEquals("^", "^");
    }
    /**
     * Test to see if you can assert constraint out of bounds
     */
    @Test(expected = IndexOutOfBoundsException.class)
    public void setColumnConstraintWhenGreaterThanInPutIsLegal() {

        puzzleTest.setRowConstraint(6, 0, "V");
        Assert.assertEquals("V", "V");
    }
    /**
     * Test to see if you can change the input constraints 
     */
    @Test(expected = RuntimeException.class)
    public void setColumnConstraintOverridingInputConstraint() {

        puzzleTest.setRowConstraint(3, 0, "^");
        Assert.assertEquals(this, this);
        puzzleTest.setRowConstraint(3, 0, "V");
        Assert.assertEquals(this, this);

        System.err.println("\n Illegal argument Exception, overring input constraints");

    }
    /*** 
     * Test to see if you can input a row constraint when requires column
     */
    @Test(expected = ComparisonFailure.class)
    public void setColumnConstraintWhenInputIsRow() {

        try {
            puzzleTest.setRowConstraint(3, 0, "<");
            Assert.assertEquals("^", "<");

        } catch (AssertionError e) {
            System.err.println("Caught an AssertionError..." + e.getMessage());
        }

    }
    /**
     * Test to see if you can input more than one constraint
     */
    @Test(expected = ComparisonFailure.class)
    public void setColumnonstraintWhenInputContainsMoreThanOneConstraint() {
        try {
            puzzleTest.setRowConstraint(3, 0, "<^>^<v");
            Assert.assertEquals("<V>^<v", "V");
        } catch (AssertionError e) {
            System.err.println("Caught an AssertionError..." + e.getMessage());

        }
    }
    /**
    * Test to see if you can assert a constraint out of bounds
    */
    @Test(expected = IndexOutOfBoundsException.class)
    public void setColumnConstraintsTestOutOfBounds() {
        try {
            puzzleTest.setColumnConstraint(100, 100, "^");
            Assert.assertEquals(this, this);
        } catch (IndexOutOfBoundsException e) {
            System.err.println(" Caught an IndexOutOfBoundsException for columnConstraints out of bounds value input for row/column is:  " + e.getMessage());
        }
    }

    /**
     * Tests for a valid input of numbers, and says input is Legal by calling isLegal in FutoShikiPuzzle
     */
    @Test
    public void TestFillPuzzle() {
        System.out.print("--------------------- Fill Puzzle test Legal-------------------------" + "\n");
        puzzleTest.setSquare(0, 0, 2);
        puzzleTest.setSquare(0, 1, 4);
        puzzleTest.setSquare(0, 2, 1);
        puzzleTest.setSquare(0, 3, 5);
        puzzleTest.setSquare(0, 4, 3);
        puzzleTest.setSquare(3, 1, 3);
        puzzleTest.setSquare(3, 2, 2);
        puzzleTest.setSquare(3, 3, 1);
        puzzleTest.setSquare(3, 4, 4);
        puzzleTest.setSquare(4, 0, 4);
        puzzleTest.setSquare(4, 1, 1);
        puzzleTest.setSquare(4, 2, 5);
        puzzleTest.setSquare(4, 3, 3);
        puzzleTest.setSquare(4, 4, 2);
        puzzleTest.setRowConstraint(3, 1, ">");
        puzzleTest.setRowConstraint(3, 2, ">");
        puzzleTest.setColumnConstraint(2, 2, "^");

        assertTrue(puzzleTest.isLegal());
        System.out.print("--------------------- Invalid Assertion testing -------------------------" + "\n");

    }
    /**
     * Test for fillPuzzle when input numbers is duplicated. Calls isLegal which shows the problem
     */
    @Test
    public void FillPuzzleInputNumbersIllegal() {
        System.out.print("--------------------- Fill Puzzle test numers input is Illegal-------------------------" + "\n");

        puzzleTest.setSquare(1, 2, 5);
        puzzleTest.setSquare(1, 4, 5);
        puzzleTest.setSquare(4, 2, 5);
        puzzleTest.setSquare(3, 1, 5);
        puzzleTest.setSquare(3, 2, 5);
        puzzleTest.setSquare(3, 3, 5);
        puzzleTest.setSquare(3, 4, 5);
        assertFalse(puzzleTest.isLegal());
        System.err.print("Puzzle is illegal");

    }
    /**
     * Tests for invalid constraints in FillPuzzle. Calls IsLegal which shows the problem
     */
    @Test
    public void FillPuzzlInvalidConstraint() {
        System.out.print("--------------------- Fill Puzzle constraint is Illegal-------------------------" + "\n");

        puzzleTest.setSquare(1, 4, 5);
        puzzleTest.setSquare(4, 2, 5);
        puzzleTest.setSquare(3, 1, 5);
        puzzleTest.setSquare(3, 2, 5);
        puzzleTest.setRowConstraint(3, 1, ">");
        puzzleTest.setRowConstraint(3, 2, ">");
        puzzleTest.setColumnConstraint(3, 2, "^");
        puzzleTest.setColumnConstraint(3, 2, "V");
        puzzleTest.setColumnConstraint(3, 3, "^");
        puzzleTest.setColumnConstraint(3, 4, "V");
        puzzleTest.setColumnConstraint(3, 3, "V");

        assertFalse(puzzleTest.isLegal());
        System.err.print("Puzzle is illegal");
    }
    /**
     * Tests if generated numbers can be out of bounds
     */
    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void FillPuzzleOutOfBounds() {

        try {
            puzzleTest.setSquare(10, 3, 5);
            puzzleTest.setSquare(10, 4, 5);
            puzzleTest.setSquare(10, 0, 5);
            puzzleTest.setSquare(10, 1, 5);
            puzzleTest.setSquare(10, 2, 5);
            puzzleTest.setSquare(10, 3, 5);
            puzzleTest.setSquare(10, 4, 5);
            puzzleTest.setRowConstraint(10, 1, ">");
            puzzleTest.setRowConstraint(10, 2, ">");
            puzzleTest.setColumnConstraint(10, 2, "^");
            puzzleTest.setColumnConstraint(10, 2, "V");
            puzzleTest.setColumnConstraint(10, 3, "^");
            puzzleTest.setColumnConstraint(10, 4, "V");
            puzzleTest.setColumnConstraint(10, 3, "V");

        } catch (IndexOutOfBoundsException e) {
            System.err.println("IndexOutOfBoundsException, out of bounds exception " + e.getMessage());
        }

    }

    /**
     * ToString test in FutoShikiPuzzle
     * Generates sample grids on what it should look like
     */
    @Test
    public void testToString() {
        Random randomnNumber = new Random();
        int fillNumber = randomnNumber.nextInt(5);
        int gridSize = 5;
        System.out.print("------------- Valid Empty grid ----------------" + "\n");
        String emptyPuzzleString = String.format("%s%n%s%n%s%n", (repeat("   ---   ", gridSize)), (repeat("  |   |  ", gridSize)), (repeat("   ---   ", gridSize)));
        for (int i = 0; i < gridSize; i++) {
            System.out.println(emptyPuzzleString);
        }

        System.out.print("------------- Filled grid Invalid ----------------" + "\n");
        String nonemptyPuzzleString = String.format("%s%n%s%n%s%n", (repeat("   ---   ", gridSize)), (repeat("  | " + fillNumber + " |  ", gridSize)), (repeat("   ---   ", gridSize)));
        for (int i = 0; i < gridSize; i++) {
            System.out.println(nonemptyPuzzleString);
        }

    }

    public static String repeat(String str, int times) {
        return new String(new char[times]).replace("\0", str);
    }

}
