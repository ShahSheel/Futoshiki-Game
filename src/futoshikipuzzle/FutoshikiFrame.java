/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package futoshikipuzzle;

/**
 *
 * @author ss799
 */
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.URL;

import java.text.NumberFormat;

import java.util.List;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.NumberFormatter;

/**
 *
 * @author Sheel
 */
public class FutoshikiFrame extends JFrame implements Serializable, FutoshikiGUI {

    private final Random randomnNumber = new Random();

    JFrame frame = new JFrame();
    JPanel gridPanel = new JPanel();
    JPanel gridBox = new JPanel();
    JPanel contentPane = (JPanel) frame.getContentPane();
    int gridSize;
    FutoShikiPuzzle puzzle = new FutoShikiPuzzle(1);

    /**
     * Constructor, creates the JFrame window and calls the entire menu display
     * as methods
     */
    FutoshikiFrame() {
        createWindow();
        menuBar();
        blackBorder();
        title();
        blueBackgroud();
        buttonMenu();
        creamBars();
        frame.pack();
        
    }

    /**
     * Opens with max window on all computers using a toolkit. Window is given a
     * title and a icon for the program. Program window is resizable and it set
     * to open in the middle for all screens as well.
     */
    private void createWindow() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setTitle("Futoshiki Puzzle");
        frame.setIconImage(loadImageIcon("icon/futoshikipuzzle.png").getImage());
     //   frame.setSize(screenSize.width, screenSize.height);
     // frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setLayout(null);
        
        frame.setVisible(true);

        
    }

    /**
     * **
     * MENU BAR METHODS * /*
     */
    /**
     * Creates menu bar display, user can click on file to create a new game,
     * save a game(Does not work if they are on the menu display), loadgame and
     * quit. As well they can click on help to find out how to play futoshiki
     */
    private void menuBar() {
        JMenuBar menuBar = new JMenuBar(); // Instantiating a MenuBar
        JMenu menu = new JMenu("File");
        menuBar.add(menu);

        JMenuItem menuItem = new JMenuItem("New Game");
        menu.add(menuItem);
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setGrid();
            }
        });
        menu.add(menuItem);

        JMenuItem menuItemSaveFile = new JMenuItem("Save Game");
        menuItemSaveFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser chooser = new JFileChooser();
                int returnVal = chooser.showSaveDialog(menuItemSaveFile);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File file = chooser.getSelectedFile();
                    saveFile(file);
                }
            }
        });
        menu.add(menuItemSaveFile);

        JMenuItem menuItemLoadGame = new JMenuItem("Load Game");
        menuItemLoadGame.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JFileChooser chooser = new JFileChooser();
                int returnVal = chooser.showOpenDialog(menuItemLoadGame);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File file = chooser.getSelectedFile();
                    loadFile(file);
                }
                contentPane.removeAll();
                gridBox.removeAll();
                sidePanel();
            }
        });
        menu.add(menuItemLoadGame);

        JMenuItem menuItemQuit = new JMenuItem("Quit");
        menu.add(menuItemQuit);
        menuItemQuit.addActionListener(new CloseListener());

        JMenu help = new JMenu("Help");
        menuBar.add(help);

        JMenuItem menItemHelp = new JMenuItem("How to play");
        help.add(menItemHelp);
        menItemHelp.addActionListener((ActionEvent evt) -> { // popup box, displaying a how - to - guide
            JOptionPane.showMessageDialog(FutoshikiFrame.this,
                    "The aim of Futoshiki is to place the numbers 1 to 5 (or higher, if the puzzle is larger) into each row"
                    + "\n" + "and column of the puzzle so that no number is repeated in a row or column and so that all of the"
                    + "\n" + "inequality signs (< and >) are obeyed.",
                    "How to play",
                    JOptionPane.INFORMATION_MESSAGE);
        });
        frame.setJMenuBar(menuBar);
    }

    /**
     * A blackborder / background in the game display is given to the
     * contentPane with the values 64 64 64.
     */
    private void blackBorder() {
        contentPane.setLayout(new BorderLayout());
        contentPane.add(gridPanel, BorderLayout.CENTER);
        contentPane.setBackground(new Color(64, 64, 64));
        contentPane.setBorder(new BevelBorder(BevelBorder.RAISED));
        contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
    }

    /**
     * Title in the menu selection named "Futoshiki puzzle"
     */
    private void title() {
        JPanel titlePanel = new JPanel(new FlowLayout());
        titlePanel.setOpaque(false);
        JLabel title = new JLabel("Futoshiki Puzzle");
        title.setFont(new Font("Arial", Font.BOLD, 25));
        title.setForeground(Color.white);
        contentPane.add(titlePanel, BorderLayout.NORTH);
        titlePanel.add(title);
    }

    /**
     * A blue background (colors 30, 101, 109) is given to the menu bar which is
     * on top of the contentPant
     */
    private void blueBackgroud() {
        gridPanel.setBorder(new BevelBorder(BevelBorder.RAISED));
        gridPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        gridPanel.setBackground(new Color(30, 101, 109));
        gridPanel.setBorder(new BevelBorder(BevelBorder.RAISED));
        gridPanel.setLayout(new BorderLayout());
    }

    /**
     * Button panel is now displayed ontop of the contentPane using a panel
     * called gridPanel. User can click on "New Game", "Load Game" and "Quit"
     */
    private void buttonMenu() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridBagLayout());
        gridPanel.add(buttonPanel, BorderLayout.CENTER);
        buttonPanel.setOpaque(false);

        JButton playGame = new JButton("New Game");
        playGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                setGrid(); // Calls set grid where user can set a gridsize
            }
        });
        buttonPanel.add(playGame);

        JButton loadGame = new JButton("Load Game");
        loadGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {

                final JFileChooser chooser = new JFileChooser();
                int returnVal = chooser.showOpenDialog(loadGame);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File file = chooser.getSelectedFile();
                    loadFile(file);    //User can load a game but only in a format of .ser
                }
                gridBox.removeAll(); // Removes gridBox panel - also removes memory!
                sidePanel(); // Loads a choice of buttons on the side the ingame grid
            }
        });
        buttonPanel.add(loadGame);

        JButton quit = new JButton("Quit");
        buttonPanel.add(quit);
        quit.addActionListener(new CloseListener()); // Calls a class "closeListener" which exits the program
    }

    /**
     * Creambars are added above and below the buttons for decoration and for a
     * friendly look
     */
    private void creamBars() {
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BorderLayout());
        gridPanel.add(bottomPanel, BorderLayout.SOUTH);
        bottomPanel.setBorder(new BevelBorder(BevelBorder.RAISED));
        bottomPanel.setBackground(new Color(235, 220, 178));
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());
        gridPanel.add(topPanel, BorderLayout.NORTH);
        topPanel.setBorder(new BevelBorder(BevelBorder.RAISED));
        topPanel.setBackground(new Color(235, 220, 178));
    }

    /**
     * **
     * IN GAME METHODS AND LAYOUT * /*
     */
    /**
     * Loads buttons on the side of a generated grid: New game, where everytime
     * they can enter a new grid size, check if their answer is correct, if
     * wrong provides the problems, a solution as well as save and load game.
     */
    private void sidePanel() {
        frame.setTitle("Futoshiki Puzzle: Lets Play!"); // Changes the window title to Futoshiki puzzle: let's play!
        JPanel newGamePanel = new JPanel(new FlowLayout());
        newGamePanel.setOpaque(false);
        JLabel titleNewGame = new JLabel("Futoshiki Puzzle New Game"); // Title 
        titleNewGame.setFont(new Font("Arial", Font.BOLD, 20));
        titleNewGame.setForeground(Color.white);

        contentPane.add(newGamePanel, BorderLayout.NORTH);
        newGamePanel.add(titleNewGame);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(5, 1));
        contentPane.add(buttonPanel, BorderLayout.WEST);
        buttonPanel.setOpaque(false);

        JButton newGame = new JButton("New Game");
        newGame.addActionListener((ActionEvent e) -> {
            setGrid(); // Calls setgrid, where user can set a grid size
        });
        buttonPanel.add(newGame);
        JButton solution = new JButton("Show Solution");
        solution.addActionListener((ActionEvent e) -> {
            gridBox.removeAll();
            titleNewGame.removeAll();
            solve(); // Calls solve 
            createGame(); // Updates createGame output 
            contentPane.updateUI();
            contentPane.revalidate();
            contentPane.repaint(); // Repaints the colors
        });

        buttonPanel.add(solution);
        JButton isRight = new JButton("Check My Answer");
        isRight.addActionListener((ActionEvent e) -> {
            if (!(puzzle.isLegal())) { // Popup box displaying problems with the users input if there are any
                JOptionPane.showMessageDialog(FutoshikiFrame.this,
                        puzzle.getProblems() + "\n",
                        "Uh oh!",
                        JOptionPane.ERROR_MESSAGE);

            } else if (puzzle.isEmpty()) { // Checks if the user has entered anything, if they havn't and they click on  check my answer, it will display this
                JOptionPane.showMessageDialog(FutoshikiFrame.this,
                        "Puzzle cannot be empty! You must fill the grid then check if you have won!",
                        "OOPS!",
                        JOptionPane.WARNING_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(FutoshikiFrame.this, // Else, tells the user they have won 
                        "You have completed the game!",
                        "Congratulations!",
                        JOptionPane.INFORMATION_MESSAGE);
            }
            puzzle.printProblems.clear();
        });
        buttonPanel.add(isRight);

        JButton saveFile = new JButton("Save To File");
        saveFile.addActionListener((ActionEvent e) -> {
            JFileChooser chooser = new JFileChooser(); // Opens a explorer, user can save there file anywhere they wish to
            chooser.showSaveDialog(buttonPanel);
            chooser.setFileFilter(new FileNameExtensionFilter("ser file", "ser"));
            File file = chooser.getSelectedFile();
            saveFile(file);
        });

        buttonPanel.add(saveFile);

        JButton loadFile = new JButton("Load File");

        loadFile.addActionListener((ActionEvent e) -> {
            JFileChooser chooser = new JFileChooser(); // Opens explorer, user can load there file from anywhere
            chooser.showOpenDialog(buttonPanel);
            chooser.setFileFilter(new FileNameExtensionFilter("ser file", "ser"));
            File file = chooser.getSelectedFile();
            loadFile(file);
            chooser.updateUI();
        });
        buttonPanel.add(loadFile);

        createGame(); // Calls createGame which creates the futoshiki game!
    }

    /**
     * Saves the object to a file, if they have entered an illegal character and
     * tried to save it it will not be saved. Must be saved as .ser
     *
     * @param file
     */
    private void saveFile(File file) {
        try (
                FileOutputStream fos = new FileOutputStream(file);
                ObjectOutputStream oos = new ObjectOutputStream(fos);) {
            if (!puzzle.isLegal()) {
                JOptionPane.showMessageDialog(FutoshikiFrame.this,
                        "Cannot save this to a file, you are to saved the game as an illegal puzzle! " + "\n",
                        "Silly mistake made! :( ",
                        JOptionPane.ERROR_MESSAGE);
            } else {
                oos.writeObject(puzzle);
                oos.close();
            }
        } catch (Exception exc) {
        }
    }

    /**
     * Loads an object of futoshiki puzzle and displays it onto the grid
     *
     * @param file
     */
    private void loadFile(File file) {
        try {
            FileInputStream fin = new FileInputStream(file);
            try (ObjectInputStream ois = new ObjectInputStream(fin)) {
                puzzle = (FutoShikiPuzzle) ois.readObject();
                ois.close();

            }

            gridBox.removeAll();
            createGame();
            contentPane.updateUI();
            contentPane.revalidate();
            contentPane.repaint();
        } catch (IOException | ClassNotFoundException exc) {
        }
    }

    /**
     * This creates the futoshiki grid. The grid is resizable by using the users
     * input for the gridsize. All numbers that are "0" are shown as as empty
     * box where the user can enter there number. All numbers that are not 0's
     * are Un-editable as it would have already generated a legal and solveable
     * puzzle.
     */
    private void createGame() {
        gridBox.setLayout(new GridLayout((gridSize) + (gridSize - 1), gridSize)); //Resizable grid layout
        contentPane.add(gridBox, BorderLayout.CENTER);
        gridBox.setOpaque(false);
        NumberFormat format = NumberFormat.getInstance();
        NumberFormatter formatter = new NumberFormatter(format);
        formatter.setValueClass(Integer.class);
        formatter.setMinimum(null);
        formatter.setMaximum(gridSize); // Max value entered can only be up to gridsize
        formatter.setCommitsOnValidEdit(true);

        for (int row = 0; row < (gridSize) + (gridSize - 1); row++) {
            for (int column = 0; column < gridSize; column++) {
                final int r = row / 2;
                final int c = column;
                if (row % 2 == 0 && column != gridSize - 1) {
                    if (getSquareValue(row / 2, column) == 0) {
                        //For all 0's in grid display a empty square which lets the user enter a number
                        JFormattedTextField Emptysquare = new JFormattedTextField(formatter);
                        Emptysquare.setFont(new Font("Arial", Font.BOLD, 25));
                        Emptysquare.setHorizontalAlignment(JFormattedTextField.CENTER);
                        Emptysquare.addKeyListener(new KeyAdapter() {
                            /**
                             * Handle the key-released event from the text
                             * field.
                             */
                            @Override
                            public void keyReleased(KeyEvent e) {
                                //Gets the string text, converts to int, stores into setSquare
                                String getInsertedValue = Emptysquare.getText();
                                int getInsertedIntValue = Integer.parseInt(getInsertedValue);
                                setSquareValue(r, c, getInsertedIntValue);
                                System.out.print(getSquareValue(r, c));
                            }
                        });
                        gridBox.add(Emptysquare); // Adds the EmptySquare to the gridbox

                    } else {
                        // If the number is not  0, set the box to un-editable with  black background and red forground color 
                        JTextField square = new JTextField("" + getSquareValue(row / 2, column));
                        square.setFont(new Font("Arial", Font.BOLD, 25));
                        square.setBackground(Color.BLACK);
                        square.setForeground(Color.RED);
                        square.setEditable(false);
                        square.setHorizontalAlignment(JTextField.CENTER);
                        gridBox.add(square);
                    }
                    JLabel rowLabel = new JLabel(puzzle.getRowConstraints(row / 2, column).getSymbol(), JLabel.CENTER);
                    rowLabel.setFont(new Font("Arial", Font.BOLD, 25));
                    rowLabel.setForeground(Color.WHITE);
                    gridBox.add(rowLabel);
                } else if (row % 2 == 0 && column == gridSize - 1) { // Adding an extra column
                    if (getSquareValue(row / 2, column) == 0) { // For all numbers that are 0, display an empty text box for the user to enter a value 

                        JFormattedTextField Emptysquare = new JFormattedTextField(formatter);
                        Emptysquare.setFont(new Font("Arial", Font.BOLD, 25));
                        Emptysquare.setHorizontalAlignment(JTextField.CENTER);
                        Emptysquare.addKeyListener(new KeyAdapter() {
                            /**
                             * Handle the key-released event from the text
                             * field.
                             */
                            public void keyReleased(KeyEvent e) {
                                String getInsertedValue = Emptysquare.getText(); // Gets the string from the formattedTextField, converts to int and gets gets asserted into setsquare
                                int getInsertedIntValue = Integer.parseInt(getInsertedValue);
                                setSquareValue(r, c, getInsertedIntValue);
                                System.out.print(getSquareValue(r, c));
                            }
                        });
                        gridBox.add(Emptysquare);
                    } else {
                        // If the number is not  0, set the box to un-editable with  black background and red forground color 
                        JTextField square = new JTextField("" + getSquareValue(row / 2, column));
                        square.setHorizontalAlignment(JTextField.CENTER);
                        square.setFont(new Font("Arial", Font.BOLD, 25));
                        square.setBackground(Color.BLACK);
                        square.setForeground(Color.RED);
                        square.setEditable(false);
                        gridBox.add(square);
                    }
                } else if (row % 2 != 0 && column != gridSize - 1) {  // Display column constraints
                    JLabel rowLabel = new JLabel(puzzle.getColumnConstraints(row / 2, column).getSymbol(), JLabel.CENTER);
                    rowLabel.setFont(new Font("Arial", Font.BOLD, 25));
                    rowLabel.setForeground(Color.WHITE);
                    gridBox.add(rowLabel);
                    JLabel rowLabel1 = new JLabel(); //DO NOT FILL IN
                    gridBox.add(rowLabel1);
                } else if (row % 2 != 0 && column == gridSize - 1) { // Display row constraints
                    JLabel rowLabel = new JLabel(puzzle.getColumnConstraints(row / 2, column).getSymbol(), JLabel.CENTER);
                    rowLabel.setFont(new Font("Arial", Font.BOLD, 25));
                    rowLabel.setForeground(Color.WHITE);
                    gridBox.add(rowLabel);
                }
            }
        }
    }

    /**
     * Gets the location of the program icon, returns null if it couldn't find
     * it.
     *
     * @param path
     * @return
     */
    private static ImageIcon loadImageIcon(String path) {
        URL imageURL = FutoshikiFrame.class
                .getResource(path);
        if (imageURL != null) {
            return new ImageIcon(imageURL);

        } else {
            System.err.println("Could not find file: " + path);
            return null;
        }
    }

    /**
     * Setting a value in a specific square, calls the setSquare in
     * FutoShikiPuzzle and passes across the values for it to store it into the
     * grid
     *
     * @param row gets row
     * @param column gets column
     * @param val gets value number
     */
    @Override
    public void setSquareValue(int row, int column, int val) {
        puzzle.setSquare(row, column, val);
    }

    /**
     * Getting a value from a speciic square by passing row and column into
     * futoshiki puzzle which gets the square using the row and column.
     *
     * @param row gets row
     * @param column gets column
     * @return number
     */
    @Override
    public final int getSquareValue(int row, int column) {
        return puzzle.grid[row][column].getSquareNumber();
    }

    /**
     * Sets the grid, user can set the grid them self. Creates a new instance of
     * futoshikipuzzle and calls newPuzzle with the grid size passed.
     */
    private void setGrid() {

        String setGridSize = JOptionPane.showInputDialog("Set GridSize", null);
        gridSize = Integer.parseInt(setGridSize);
        puzzle = new FutoShikiPuzzle(gridSize); //New instance of futoshikipuzzle with a specificed gridSize 
        gridBox.removeAll();
        contentPane.removeAll();
        contentPane.updateUI();
        contentPane.revalidate();
        contentPane.repaint();
        newPuzzle(gridSize);
        sidePanel(); // Dislays the side button panels 
    }

    /**
     * Keeps creating a new puzzle with the users specified grid size till the
     * puzzle can be solved, when solved calls remove numbers.
     *
     * newPuzzle then displays a legal and solveable grid with some numbers as 0
     * (User changes the 0 numbers till it becomes legal and solved!).
     *
     * @param gridSize
     */
    private void newPuzzle(int gridSize) {
        do {
            puzzle = new FutoShikiPuzzle(gridSize);
            puzzle.fillPuzzle();
        } while (!puzzle.Solve()); // Using solve2 algorithm to increase the rate of producing a legal and solveable puzzle at base level
        removeNumbers();
        contentPane.updateUI();
    }

    /**
     * Removes a numbers from the grid by setting random numbers to 0.
     */
    private void removeNumbers() {
        for (FutoShikiSquare[] row : puzzle.grid) {
            for (FutoShikiSquare[] column : puzzle.grid) {
                int row1 = randomnNumber.nextInt(puzzle.grid.length);
                int column1 = randomnNumber.nextInt(puzzle.grid.length);
                setSquareValue(row1, column1, 0);
            }
        }
    }

    /**
     * Checks if the puzzle is legal or not
     *
     * @return isLegal
     */
    @Override
    public boolean isLegal() {
        return puzzle.isLegal();
    }

    /**
     * Displays solutions to the grid using deep-search solve algorithm
     *
     * @return solve
     */
    public boolean solve() {
        return puzzle.Solve();
    }

    /**
     * Returns an arraylist of illegal inputs
     *
     * @return ArrayList of printProblems
     */
    @Override
    public List<String> getProblems() {
        return puzzle.getProblems();
    }

    /**
     * Ensures that the same class was used during Serialization is loaded
     * during Deserialization.
     */
    public static final long serialVersionUID = 24362462L;

    /**
     * Creates an instance of FutoshikiFrame to display the GUI
     *
     * @param args args
     */
    public static void main(String[] args) {
        FutoshikiFrame futoshikiFrame = new FutoshikiFrame();
    }
}
