import java.awt.*;

import javax.swing.*;


public class StartPage extends JFrame {
	private Timer timer;
	private int minutes;
	private int seconds;
	private Board gameBoard;

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
		Game game = new Game(difficulty, true); //temporary fix
		newGame.dispose();
		dispose();
	}

	public void load() { //needs polish
		
	
		ImageIcon pause = new ImageIcon("src/resources/pause.png");
		Game oldGame = new Game(4,true);
		oldGame.setVisible(false);
		oldGame.load(pause);
		//oldGame.setVisible(true);

		dispose();
	}



}
