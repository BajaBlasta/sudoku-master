import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;


public class StartPage extends JFrame { 
	
	private int difficulty = 0;
	private BufferedImage background;
	boolean normalTimer = true;
	private Timer timer;
	

	
	public StartPage() throws IOException{
		BufferedImage image = ImageIO.read(new File("src/resources/startPage.png")); //background image of the page
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
		setDefaultCloseOperation(EXIT_ON_CLOSE);

	}

	private void gameOption() {
		JFrame newGame = new JFrame();
		
		newGame.setTitle("Select New Game Options");
		newGame.setSize(500, 300);
		newGame.setResizable(false);
		JPanel difficultyPanel = new JPanel();
		JPanel timerPanel = new JPanel();
		JPanel backgroundPanel = new JPanel();
		JPanel playPanel = new JPanel();
		
		newGame.setLayout(new GridLayout(0,1));
		newGame.setLocationRelativeTo(this);
		JToggleButton easy = new JToggleButton("Easy");
		JToggleButton medium = new JToggleButton("Medium");
		JToggleButton hard = new JToggleButton("Hard");
		JToggleButton veryHard = new JToggleButton("Very Hard");
		
		easy.setFocusPainted(false);
		easy.setBackground(Color.WHITE);
		medium.setFocusPainted(false);
		medium.setBackground(Color.WHITE);
		hard.setFocusPainted(false);
		hard.setBackground(Color.WHITE);
		veryHard.setFocusPainted(false);
		veryHard.setBackground(Color.WHITE);
		
		ButtonGroup difficultyGroup = new ButtonGroup();
		difficultyGroup.add(easy);
		difficultyGroup.add(medium);
		difficultyGroup.add(hard);
		difficultyGroup.add(veryHard);
		
		JRadioButton normalTimer = new JRadioButton("Normal Timer");
		JRadioButton countdown = new JRadioButton("Countdown Timer");
		
		JTextField counterInput = new JTextField();
		
		JButton play = new JButton("Play");
		play.setFocusPainted(false);
		play.setBackground(Color.WHITE);
		
		ButtonGroup timerGroup = new ButtonGroup();
		timerGroup.add(normalTimer);
		timerGroup.add(countdown);
		
		String[] backgrounds = {"Default","Red","Orange","Yellow","Green","Blue","Purple","Pink","Etown","DISCO"};
		
		JComboBox options = new JComboBox(backgrounds);
		options.setSelectedIndex(0);
		
		
		easy.addActionListener(e -> setDifficulty(1));
		medium.addActionListener(e -> setDifficulty(2));
		hard.addActionListener(e -> setDifficulty(3));
		veryHard.addActionListener(e -> setDifficulty(4));
		
		normalTimer.addActionListener(e -> setTimer(true));
		countdown.addActionListener(e -> setTimer(false));
		
		play.addActionListener(e -> newGame(newGame));
		
		difficultyPanel.add(easy);
		difficultyPanel.add(medium);
		difficultyPanel.add(hard);
		difficultyPanel.add(veryHard);
		
		timerPanel.add(normalTimer);
		timerPanel.add(countdown);
		
		backgroundPanel.add(options);
		
		playPanel.add(play);
		
		newGame.add(difficultyPanel);
		newGame.add(timerPanel);
		newGame.add(backgroundPanel);
		newGame.add(playPanel);
		
		
		
		newGame.setVisible(true);


	}




	private void newGame(JFrame newGame) {
		if(difficulty == 0){
			return;
		}
		Game game = new Game(difficulty, normalTimer);
		//game.changeBackground()
		newGame.dispose();
		dispose();
	}
	
	
	
	private void setDifficulty(int difficulty){
		 this.difficulty = difficulty;
	}
	
	private void setImage(String fileName){
		try {
			background = ImageIO.read(new File(fileName));
		} catch (IOException e) {
			System.out.println("Image not found!");
			
		}
	}
	
	private boolean setTimer(boolean normalTimer) {
		this.normalTimer = normalTimer;
		return normalTimer;
	}
	

	public void load() { //needs polish
		
	
		ImageIcon pause = new ImageIcon("src/resources/pause.png");
		Game oldGame = new Game(4,true);
		oldGame.setVisible(false);
		oldGame.load(pause);

		dispose();
		
	}



}
