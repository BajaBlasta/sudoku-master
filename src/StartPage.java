import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.NoSuchElementException;
import java.util.Scanner;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;


public class StartPage extends JFrame {

	public StartPage(){
		setTitle("Sudoku Master");
		setSize(567,567); //create another background image for this JFrame
		setResizable(false);
		setLocationRelativeTo(null);
		setLayout(null);

		JButton newGame = new JButton();
		newGame.setBounds(189, 180, 189, 50);
		newGame.setText("New Game");
		newGame.addActionListener(e -> gameOption());
		JButton loadGame = new JButton();
		loadGame.setBounds(189, 360, 189, 50);
		loadGame.setText("Load Game");
		loadGame.addActionListener(e -> load());


		add(newGame);
		add(loadGame);


		setVisible(true);

	}

	private void gameOption() {
		JFrame newGame = new JFrame();
		JLabel question = new JLabel("What difficulty would you like your new Sudoku game to be?");
		newGame.setTitle("New Game");
		newGame.setSize(300, 200);
		newGame.setResizable(false);
		newGame.setLayout(new GridLayout(0,1));
		newGame.setLocationRelativeTo(null);
		JButton easy = new JButton("Easy");
		JButton medium = new JButton("Medium");
		JButton hard = new JButton("Hard");
		easy.addActionListener(e -> newGame(newGame,1));
		medium.addActionListener(e -> newGame(newGame,2));
		hard.addActionListener(e -> newGame(newGame,3));
		newGame.add(easy);
		newGame.add(medium);
		newGame.add(hard);
		newGame.setVisible(true);




	}
	

	private void newGame(JFrame newGame, int difficulty) {
		Game game = new Game(difficulty);
		newGame.dispose();
		dispose();
	}

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
			//			int size = reader.nextInt();
			//			minutes = reader.nextInt();
			//			seconds = reader.nextInt();
			//			for(int i = 0; i < size; ++i)
			//				for(int j = 0; j < size; ++j)
			//					gameBoard.setSquare(i, j, reader.nextShort());
			//		}
			//		
			//		timer.start();

		}

		//Creates a new text file with a user specified name in the saves directory,
		//then writes the current board information into the file
		dispose();
	}
}
