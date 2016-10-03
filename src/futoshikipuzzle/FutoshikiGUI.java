/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package futoshikipuzzle;

import java.util.List;

/**
 *
 * @author ss799
 */
public interface FutoshikiGUI {

    /**
     *
     * @param row gets row
     * @param column gets column
     * @param val sets value
     */
    public void setSquareValue(int row, int column, int val);
    
    /**
     *
     * @param row gets row
     * @param column gets column
     * @return number
     */
    public int getSquareValue(int row, int column);

    /**
     *
     * @return isLegal
     */
    public boolean isLegal();

    /**
     *
     * @return arraylist of printProblems
     */
    public List<String> getProblems();

}
