import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;


public class StartPage extends JFrame { 

	
	public StartPage() throws IOException{
		BufferedImage image = ImageIO.read(new File("src/resources/startPage.png")); //background image of the board
		JLabel background = new JLabel(new ImageIcon(image));
		setTitle("Sudoku Master");
		
		setSize(568,568);
		background.setSize(image.getWidth(),image.getHeight()); 
		setResizable(false);
		setLocationRelativeTo(null);
		setLayout(null);

		JButton newGame = new JButton();
		newGame.setBounds(189, 180, 189, 50);
		newGame.setText("New Game");
		newGame.setFocusPainted(false);
		newGame.setBackground(Color.WHITE);
		newGame.addActionListener(e -> gameOption());
		JButton loadGame = new JButton();
		loadGame.setBounds(189, 360, 189, 50);
		loadGame.setText("Load Game");
		loadGame.setFocusPainted(false);
		loadGame.setBackground(Color.WHITE);
		loadGame.addActionListener(e -> load());
		
		add(newGame);
		add(loadGame);
		add(background);
		//background.setVisible(true);
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
		easy.setFocusPainted(false);
		easy.setBackground(Color.WHITE);
		medium.setFocusPainted(false);
		medium.setBackground(Color.WHITE);
		hard.setFocusPainted(false);
		hard.setBackground(Color.WHITE);
		
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

		//dispose();
	}



}
