import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.NoSuchElementException;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
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

	private final String savesDir = "saves/";
	private Board gameBoard;
	private Timer timer;
	private boolean countdown;
	private JTextField field;
	private JButton pauseButton;
	private int seconds;
	private int minutes;

	public Game(int difficulty, boolean normalTimer) {
		//sets up the JFrame
		setTitle("Sudoku");
		setResizable(false);
		setLayout(new BorderLayout());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		ImageIcon pauseTemp = null;
		ImageIcon playTemp = null;
		//adds button images
		try {
			InputStream stream = getClass().getResourceAsStream("pause.png"); 
			pauseTemp = new ImageIcon(ImageIO.read(stream));
			stream = getClass().getResourceAsStream("play.jpg"); 
			playTemp = new ImageIcon(ImageIO.read(stream));
		} catch (IOException e1) {
			System.err.println("could not load pause and play icons");
		}
		final ImageIcon play = playTemp;
		final ImageIcon pause = pauseTemp;

		//creates all components
		JMenuBar menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu("File");

		JMenuItem newGame = new JMenuItem("New Game");
		JMenuItem save = new JMenuItem("Save");
		JMenuItem load = new JMenuItem("Load");

		JMenu settingsMenu = new JMenu("Settings");
		JMenu difficultyMenu = new JMenu("Change Difficulty");
		JMenu background = new JMenu("Change Background");
		JMenu boardSize = new JMenu("Change Board Size");

		JMenuItem easy = new JMenuItem("Easy");
		JMenuItem medium = new JMenuItem("Medium");
		JMenuItem hard = new JMenuItem("Hard");
		JMenuItem veryHard = new JMenuItem("Very Hard");

		JMenuItem defaultBoard = new JMenuItem("Default");
		JMenuItem red = new JMenuItem("Red");
		JMenuItem orange = new JMenuItem("Orange");
		JMenuItem yellow = new JMenuItem("Yellow");
		JMenuItem green = new JMenuItem("Green");
		JMenuItem blue = new JMenuItem("Blue");
		JMenuItem purple = new JMenuItem("Purple");
		JMenuItem pink = new JMenuItem("Pink");
		JMenuItem disco = new JMenuItem("Disco");
		JMenuItem etown = new JMenuItem("Etown");

		JMenuItem four = new JMenuItem("4 x 4");

		JMenu helpMenu = new JMenu("Help");
		JMenuItem done = new JMenuItem("Done");
		JMenuItem check = new JMenuItem("Check Progress");
		JMenuItem showHelp = new JMenuItem("Show Help");

		JPanel timerPanel = new JPanel();
		timerPanel.setLayout(new BorderLayout());
		gameBoard = new Board(difficulty);

		//adds components to the frame
		menuBar.add(fileMenu);
		menuBar.add(settingsMenu);
		menuBar.add(helpMenu);
		//menuBar.add(done);

		fileMenu.add(newGame);
		fileMenu.add(save);
		fileMenu.add(load);

		settingsMenu.add(difficultyMenu);
		settingsMenu.add(background);
		settingsMenu.add(boardSize);

		helpMenu.add(check);
		helpMenu.add(showHelp);

		difficultyMenu.add(easy);
		difficultyMenu.add(medium);
		difficultyMenu.add(hard);
		difficultyMenu.add(veryHard);

		background.add(defaultBoard);
		background.add(red);
		background.add(orange);
		background.add(yellow);
		background.add(green);
		background.add(blue);
		background.add(purple);
		background.add(pink);
		background.add(disco);
		background.add(etown);

		boardSize.add(four);

		load.addActionListener(e -> load(pause));
		save.addActionListener(e -> save());
		newGame.addActionListener(e -> newGame());

		easy.addActionListener(e -> changeDifficulty(1));
		medium.addActionListener(e -> changeDifficulty(2));
		hard.addActionListener(e -> changeDifficulty(3));
		veryHard.addActionListener(e -> changeDifficulty(4));

		defaultBoard.addActionListener(e -> changeBackground(difficulty, "default.png"));
		red.addActionListener(e -> changeBackground(difficulty, "red.png"));
		orange.addActionListener(e -> changeBackground(difficulty, "orange.png"));
		yellow.addActionListener(e -> changeBackground(difficulty, "yellow.png"));
		green.addActionListener(e -> changeBackground(difficulty, "green.png"));
		blue.addActionListener(e -> changeBackground(difficulty, "blue.png"));
		purple.addActionListener(e -> changeBackground(difficulty, "purple.png"));
		pink.addActionListener(e -> changeBackground(difficulty, "pink.png"));
		disco.addActionListener(e -> changeBackground(difficulty, "disco.png"));
		etown.addActionListener(e -> changeBackground(difficulty, "etown.png"));

		done.addActionListener(e -> done(endCheck(gameBoard.getBoard(), gameBoard.getSolution(), gameBoard.getBoardSize()), play));
		check.addActionListener(e -> gameBoard.checkProgress());
		showHelp.addActionListener(e -> Help.showHelp());
		add(menuBar, BorderLayout.NORTH);
		add(gameBoard, BorderLayout.CENTER);

		//adds timer to the frame
		seconds = 0;
		minutes = 0;
		countdown = !normalTimer;

		if(countdown == true && minutes == 0)
			minutes = 30;

		field = new JTextField("Time: 0:00");
		field.setEditable(false);
		field.setBorder(null);
		field.setPreferredSize(new Dimension(100, 30));
		timer = new Timer(1000, e -> updateTime());
		timer.start();
		timerPanel.add(field, BorderLayout.EAST);

		//adds pause button
		pauseButton = new JButton(pause);
		pauseButton.setPreferredSize(new Dimension(30, 30));
		pauseButton.addActionListener(e -> pause(pause, play));
		timerPanel.add(pauseButton, BorderLayout.WEST);
		add(timerPanel, BorderLayout.SOUTH);

		//scales the frame to fit panel size
		gameBoard.setPreferredSize(new Dimension(gameBoard.getWidth(), gameBoard.getHeight() + menuBar.getHeight()));
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}

	/*Loads a saved board
    Method requires a text file that matches the following format:

      "Sudoku Board" (typed exactly as shown, ignoring the quotations)
      board size, represented by an integer
      time (minutes), represented by an integer
      time (seconds), represented by an integer
      whether or not the timer is a countdown (true if it counts down, false if it counts up), represented by a boolean
      list of every number on the board, each represented by an integer and separated by a whitespace delimiter

      Example file:

      Sudoku Board
      9
      0
      27
      false
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
	public void load(ImageIcon pause) {
		//create folder if necessary
		File folder = new File(savesDir);
		if(folder.mkdirs()) {
			System.out.println("created folder " + savesDir);
		}
		
		//Creates a new pop-up to allow the user to select a save file
		JFileChooser fc = new JFileChooser();
		File file = null;
		File directory = new File(savesDir);
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
			countdown = reader.nextBoolean();
			short[] puzzle = new short[size*size];
			int puzzleIndex = 0;
			for(int i = 0; i < size; ++i)
				for(int j = 0; j < size; ++j) {
					short number = reader.nextShort();
					gameBoard.setSquare(i, j, number);
					puzzle[puzzleIndex++] = number;
				}

			//regenerate Solution when loading
			//to not break check Progress
			Generator.solveSudoku(puzzle, gameBoard.solution);

			for(int i = 0; i < size; ++i)
				for(int j = 0; j < size; ++j)
					if(reader.nextInt() == 1)
						gameBoard.getSquare(i, j).setLocked(true);
					else
						gameBoard.getSquare(i, j).setLocked(false);

			Square.checkIntersection(gameBoard.getBoard());
		}

		reader.close();
		timer.start();
		pauseButton.setIcon(pause);
		setVisible(true);
	}

	//Creates a new text file with a user specified name in the saves directory,
	//then writes the current board information into the file
	public void save() {
		//create folder if necessary
		File folder = new File(savesDir);
		if(folder.mkdirs()) {
			System.out.println("created folder " + savesDir);
		}
		
		String fileName = savesDir + 
				JOptionPane.showInputDialog("Enter a name for the save file (do not include file extension):") + ".txt";

		//If the user didn't enter a name for the file (or if they name the file "null", gives an error message
		if(fileName.equals(savesDir + ".txt")) {
			JOptionPane.showMessageDialog(null, "Invalid file");
			return;
		}

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

			if(countdown == true)
				writer.write("true" + System.getProperty("line.separator"));
			else if(countdown == false)
				writer.write("false" + System.getProperty("line.separator"));

			for(int i = 0; i < size; ++i){
				for(int j = 0; j < size; ++j) {
					writer.write((int)gameBoard.getSquare(i, j).getValue() + " ");
				}
				writer.write(System.getProperty("line.separator"));	
			}
			writer.write(System.getProperty("line.separator"));	

			for(int i = 0; i < size; ++i){
				for(int j = 0; j < size; ++j) {
					if(gameBoard.getSquare(i, j).isLocked())
						writer.write(1 + " ");
					else
						writer.write(0 + " ");
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
		yes.addActionListener(e -> yes(newGame, 1, "default.png"));
		no.addActionListener(e -> no(newGame));
		newGame.add(response, BorderLayout.SOUTH);
		newGame.add(question, BorderLayout.CENTER);
		newGame.setVisible(true);
	}

	public void yes(JFrame newGame, int difficulty, String fileName) { //if the user wants a new game then clear current gameboard and dialog box
		newGame.dispose();
		dispose();
		Game game = new Game(difficulty, !countdown);
		game.gameBoard.setImage(fileName);
	}

	public void no(JFrame newGame) { //if the user doesn't want a new game just close the dialog box
		newGame.dispose();
	}

	public void changeDifficulty(int diff) {
		String[] difficultyNames = { "Easy", "Medium", "Hard", "Very Hard"};
		JFrame newGame = new JFrame();
		JPanel response = new JPanel();
		JLabel question = new JLabel("<html>Are you sure you want to change difficulty to " + difficultyNames[diff - 1]
				+ "?<br>Any unsaved progress will be lost.</html>"); //need to center this text
		newGame.setTitle("New Game");
		newGame.setSize(300, 200);
		newGame.setResizable(false);
		newGame.setLayout(new BorderLayout());
		newGame.setLocationRelativeTo(null);
		JButton yes = new JButton("Yes");
		JButton no = new JButton("No");
		response.add(yes);
		response.add(no);
		yes.addActionListener(e -> yes(newGame, diff, "default.png"));
		no.addActionListener(e -> no(newGame));
		newGame.add(response, BorderLayout.SOUTH);
		newGame.add(question, BorderLayout.CENTER);
		newGame.setVisible(true);
	}

	public void changeBackground(int diff, String fileName) {
		JFrame newGame = new JFrame();
		JPanel response = new JPanel();
		JLabel question = new JLabel("<html>Are you sure you want to choose a new background?<br>Any unsaved progress will be lost.</html>");
		newGame.setSize(300, 200);
		newGame.setResizable(false);
		newGame.setLayout(new BorderLayout());
		newGame.setLocationRelativeTo(null);
		JButton yes = new JButton("Yes");
		JButton no = new JButton("No");
		response.add(yes);
		response.add(no);
		yes.addActionListener(e -> yes(newGame, diff, fileName));
		no.addActionListener(e -> no(newGame));
		newGame.add(response, BorderLayout.SOUTH);
		newGame.add(question, BorderLayout.CENTER);
		newGame.setVisible(true);
	}

	public boolean endCheck(Square[][] temp, short[] solution, int size){ 
		int k = 0;
		for(int i = 0; i < size; ++i ) {
			for(int j = 0; j < size; ++j) {
				if(temp[i][j].getValue() != solution[k])
					return false;
				else
					++k;
			}
		}
		return true;
	}

	public void done(boolean correct, ImageIcon play){
		if(correct){
			if(timer.isRunning()) {
				timer.stop();
				pauseButton.setIcon(play);
			}
			Alert alert = new Alert(true);
		}
		else{
			if(timer.isRunning()) {
				timer.stop();
				pauseButton.setIcon(play);
			}
			Alert alert = new Alert(false);
		}
	}

	public void updateTime() {
		if(countdown == false)
			incrementTime();
		else
			countdown();
	}

	public void incrementTime() {
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

	public void countdown() {
		if(seconds <= 0) {
			seconds = 60;
			minutes--;
		}
		seconds -= timer.getDelay() / 1000;

		if(seconds <= 9)
			field.setText("Time: " + minutes + ":0" + seconds);
		else
			field.setText("Time: " + minutes + ":" + seconds);
	}

	public void pause(ImageIcon pause, ImageIcon play) {
		if(timer.isRunning()) {
			timer.stop();
			pauseButton.setIcon(play);
		}
		else {
			timer.start();
			pauseButton.setIcon(pause);
		}
	}
	
	public Board getBoard() {
		return gameBoard;
	}
}
