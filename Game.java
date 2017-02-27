import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.NoSuchElementException;
import java.util.Scanner;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Game extends JFrame {

	private Board gameBoard;
	
	public Game() {
		//sets up the JFrame
		setTitle("Sudoku");
		setResizable(false);
		setLayout(new BorderLayout());
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		//creates all components
		JMenuBar menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		JMenuItem newGame = new JMenuItem("New Game");
		JMenuItem save = new JMenuItem("Save");
		JMenuItem load = new JMenuItem("Load");
		JMenu settingsMenu = new JMenu("Settings");
		JMenu helpMenu = new JMenu("Help");
		gameBoard = new Board(3);

		//adds components to the frame
		fileMenu.add(newGame);
		fileMenu.add(save);
		fileMenu.add(load);
		menuBar.add(fileMenu);
		menuBar.add(settingsMenu);
		menuBar.add(helpMenu);
		load.addActionListener(e -> load());
		add(menuBar, BorderLayout.NORTH);
		add(gameBoard, BorderLayout.CENTER);

		//scales the frame to fit panel size
		gameBoard.setPreferredSize(new Dimension(gameBoard.getWidth(), gameBoard.getHeight() + menuBar.getHeight()));
		pack();
		setVisible(true);
	}

	/*Loads a saved board
    Method requires a text file that matches the following format:
  
      "Sudoku Board" (typed exactly as shown, ignoring the quotations)
      board size, represented by an integer
      time (in seconds), represented by an integer
      list of every number on the board, each represented by an integer and separated by a whitespace delimiter
      
      Example file:
      
      Sudoku Board
      9
      157
      1 2 3 4 5 6 7 8 0
      1 2 3 4 5 6 7 0 9
      1 2 3 4 5 6 0 8 9
      1 2 3 4 5 0 7 8 9
      1 2 3 4 0 6 7 8 9
      1 2 3 0 5 6 7 8 9
      1 2 0 4 5 6 7 8 9
      1 0 3 4 5 6 7 8 9
      0 2 3 4 5 6 7 8 9
      
      Note: All zeroes in the text file represent an empty space on the board
	 */
	public void load() {
		//Creates a new pop-up to allow the user to select a save file
		JFileChooser fc = new JFileChooser();
		File file = null;
		File directory = new File(System.getProperty("user.dir") + "/saves");
		fc.setCurrentDirectory(directory);
		FileNameExtensionFilter filter = new FileNameExtensionFilter("TEXT FILES", "txt", "text");
		fc.setFileFilter(filter);
		fc.showOpenDialog(null);
		file = fc.getSelectedFile();
		Scanner reader = new Scanner(System.in);

		if(file == null)
			return;

		//Tries to create a Scanner to read from the file
		try {
			reader = new Scanner(file);
		}	catch(FileNotFoundException e) {	//If the file was not found, shows the user an error message
			JOptionPane.showMessageDialog(null, "File not found");
			return;
		}

		//Tries to read in a header from the file to check if it is a sudoku save file
		String header;
		try {
			header = reader.nextLine();
		} catch(NoSuchElementException e) {	//If it fails to read in a header, shows the user a message that it is not a valid save file
			JOptionPane.showMessageDialog(null, "File is not a valid Sudoku Board!");
			return;
		}

		//If the check for a header succeeded, checks to see if the header indicates that it is a valid save file
		//If it is not a valid save file, shows the user an error message
		if(!header.equals("Sudoku Board")) {
			JOptionPane.showMessageDialog(null, "File is not a valid Sudoku Board!");
			return;
		} else { //If it is a valid save file, completes the load operation	          
			int size = reader.nextInt();
			int time = reader.nextInt();
			for(int i = 0; i < size; ++i)
				for(int j = 0; j < size; ++j)
					gameBoard.setSquare(i, j, reader.nextShort());
		}
	}
}
