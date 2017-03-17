import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Game extends JFrame {

	private Board gameBoard;
	private Timer timer;
	private JTextField field;
	private int seconds;
	private int minutes;
	
	public Game(int difficulty) {
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
		JMenu difficultyMenu = new JMenu("Change Difficulty");
		JMenuItem easy = new JMenuItem("Easy");
		JMenuItem medium = new JMenuItem("Medium");
		JMenuItem hard = new JMenuItem("Hard");
		JMenu helpMenu = new JMenu("Help");
		JMenuItem done = new JMenuItem("Done");
		done.setBackground(Color.WHITE); //make prettier later
		gameBoard = new Board(difficulty);

		//adds components to the frame
		fileMenu.add(newGame);
		fileMenu.add(save);
		fileMenu.add(load);
		fileMenu.add(done);
		menuBar.add(fileMenu);
		menuBar.add(settingsMenu);
		menuBar.add(done);
		settingsMenu.add(difficultyMenu);
		difficultyMenu.add(easy);
		difficultyMenu.add(medium);
		difficultyMenu.add(hard);
		menuBar.add(helpMenu);
		load.addActionListener(e -> load());
		save.addActionListener(e -> save());
		newGame.addActionListener(e -> newGame());
		easy.addActionListener(e -> easy());
		medium.addActionListener(e -> medium());
		hard.addActionListener(e -> hard());
		done.addActionListener(e -> done(endCheck(gameBoard.getBoard(),gameBoard.getSolution(),gameBoard.getBoardSize())));
		add(menuBar, BorderLayout.NORTH);
		add(gameBoard, BorderLayout.CENTER);
		
		
		//adds timer to the frame
		seconds = 0;
		minutes = 0;
		field = new JTextField("Time: 0:00");
		timer = new Timer(1000, e -> updateTime());
		timer.start();
		add(field, BorderLayout.SOUTH);

		//scales the frame to fit panel size
		gameBoard.setPreferredSize(new Dimension(gameBoard.getWidth(), gameBoard.getHeight() + menuBar.getHeight()));
		pack();
		setVisible(true);
	}

	/*Loads a saved board
    Method requires a text file that matches the following format:
  
      "Sudoku Board" (typed exactly as shown, ignoring the quotations)
      board size, represented by an integer
      time (minutes), represented by an integer
      time (seconds), represented by an integer
      list of every number on the board, each represented by an integer and separated by a whitespace delimiter
      
      Example file:
      
      Sudoku Board
      9
      0
      27
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
		File directory = new File(System.getProperty("user.dir") + "/src/resources/saves/");
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
			minutes = reader.nextInt();
			seconds = reader.nextInt();
			for(int i = 0; i < size; ++i)
				for(int j = 0; j < size; ++j)
					gameBoard.setSquare(i, j, reader.nextShort());
		}
	}
	
	//Creates a new text file with a user specified name in the saves directory,
	//then writes the current board information into the file
	public void save() {
		String fileName = System.getProperty("user.dir") + "/src/resources/saves/" + 
				  JOptionPane.showInputDialog("Enter a name for the save file (do not include file extension):") + ".txt";
		File file = new File(fileName);
		FileWriter writer;
		int size = gameBoard.getBoardSize();
		
		//Tries to create a new file with the given information
		try {
			file.createNewFile();
		} catch (IOException e1) {
			JOptionPane.showMessageDialog(null, "Invalid file");
			return;
		}
		
		//Tries to create a new FileWriter to write to the new file
		try {
			writer = new FileWriter(file);
			writer.write("Sudoku Board" + System.getProperty("line.separator"));
			writer.write(size + System.getProperty("line.separator"));
			writer.write(minutes + System.getProperty("line.separator"));
			writer.write(seconds + System.getProperty("line.separator"));
			
			for(int i = 0; i < size; ++i){
				for(int j = 0; j < size; ++j)
				{
					writer.write((int)gameBoard.getSquare(i, j).getValue() + " ");
				}
				writer.write(System.getProperty("line.separator"));
			}
			writer.close();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Invalid file");
			return;
		}
		JOptionPane.showMessageDialog(null, "File saved successfully");
	}
	
	public void newGame() { //creates a new dialog box prompting the user to decide if he/she wants to start a new game
		JFrame newGame = new JFrame();
		JPanel response = new JPanel();
		JLabel question = new JLabel("<html>Are you sure you want to start a new game?<br>Any unsaved progress will be lost.</html>"); //need to center this text
		newGame.setTitle("New Game");
		newGame.setSize(300, 200);
		newGame.setResizable(false);
		newGame.setLayout(new BorderLayout());
		newGame.setLocationRelativeTo(null);
		JButton yes = new JButton("Yes");
		JButton no = new JButton("No");
		response.add(yes);
		response.add(no);
		yes.addActionListener(e -> yes(newGame, 1));
		no.addActionListener(e -> no(newGame));
		newGame.add(response, BorderLayout.SOUTH);
		newGame.add(question, BorderLayout.CENTER);
		newGame.setVisible(true);
	}
	
	public void yes(JFrame newGame, int difficulty) { //if the user wants a new game then clear current gameboard and dialog box
		newGame.dispose();
		dispose();
		Game game = new Game(difficulty);
	}
		
	public void no(JFrame newGame) { //if the user doesn't want a new game just close the dialog box
		newGame.dispose();
	}
	
	public void easy() { //creates new game w/ easy difficulty
		JFrame newGame = new JFrame();
		JPanel response = new JPanel();
		JLabel question = new JLabel("<html>Are you sure you want to change difficulty to easy?<br>Any unsaved progress will be lost.</html>"); //need to center this text
		newGame.setTitle("New Game");
		newGame.setSize(300, 200);
		newGame.setResizable(false);
		newGame.setLayout(new BorderLayout());
		newGame.setLocationRelativeTo(null);
		JButton yes = new JButton("Yes");
		JButton no = new JButton("No");
		response.add(yes);
		response.add(no);
		yes.addActionListener(e -> yes(newGame, 1));
		no.addActionListener(e -> no(newGame));
		newGame.add(response, BorderLayout.SOUTH);
		newGame.add(question, BorderLayout.CENTER);
		newGame.setVisible(true);
	}
	
	public void medium() { //creates new game w/ medium difficulty
		JFrame newGame = new JFrame();
		JPanel response = new JPanel();
		JLabel question = new JLabel("<html>Are you sure you want to change difficulty to medium?<br>Any unsaved progress will be lost.</html>"); //need to center this text
		newGame.setTitle("New Game");
		newGame.setSize(300, 200);
		newGame.setResizable(false);
		newGame.setLayout(new BorderLayout());
		newGame.setLocationRelativeTo(null);
		JButton yes = new JButton("Yes");
		JButton no = new JButton("No");
		response.add(yes);
		response.add(no);
		yes.addActionListener(e -> yes(newGame, 2));
		no.addActionListener(e -> no(newGame));
		newGame.add(response, BorderLayout.SOUTH);
		newGame.add(question, BorderLayout.CENTER);
		newGame.setVisible(true);
	}
	
	public void hard() { //I can clean up and combine these methods later, creates new game w/ hard difficulty
		JFrame newGame = new JFrame();
		JPanel response = new JPanel();
		JLabel question = new JLabel("<html>Are you sure you want to change difficulty to hard?<br>Any unsaved progress will be lost.</html>"); //need to center this text
		newGame.setTitle("New Game");
		newGame.setSize(300, 200);
		newGame.setResizable(false);
		newGame.setLayout(new BorderLayout());
		newGame.setLocationRelativeTo(null);
		JButton yes = new JButton("Yes");
		JButton no = new JButton("No");
		response.add(yes);
		response.add(no);
		yes.addActionListener(e -> yes(newGame, 3));
		no.addActionListener(e -> no(newGame));
		newGame.add(response, BorderLayout.SOUTH);
		newGame.add(question, BorderLayout.CENTER);
		newGame.setVisible(true);
	}
	
	public boolean endCheck(Square[][] temp, short[] solution, int size){ //not finished
		int k = 0;
		for(int i = 0; i < size; ++i )
			for(int j = 0; j < size; ++j){
				if(temp[i][j].getValue() != solution[k])
					return false;
				else
					++k;
			}
		}
				
		return true;

	}
	
	public void done(boolean correct){
		if(correct){
			Alert alert = new Alert(true);
		}
		else{
			Alert alert = new Alert(false);
		}
	}
	
	public void updateTime() {
		seconds += timer.getDelay() / 1000;
		
		if(seconds >= 60) {
			seconds = 0;
			minutes++;
		}
		if(seconds <= 9)
			field.setText("Time: " + minutes + ":0" + seconds);
		else
			field.setText("Time: " + minutes + ":" + seconds);
	}
}
