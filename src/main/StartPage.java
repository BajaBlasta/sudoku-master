import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.*;


public class StartPage extends JFrame { 

	private static final long serialVersionUID = 1L;
	private int difficulty = 0;
	boolean normalTimer = true;
	private String choice = "default.png";

	public StartPage() throws IOException{
		
		InputStream stream = getClass().getResourceAsStream("startPage.png"); 
		BufferedImage image = ImageIO.read(stream); //background image of the page
		JLabel background = new JLabel(new ImageIcon(image));
		setTitle("Sudoku Master");

		stream = getClass().getResourceAsStream("pause.png"); 
		ImageIcon pauseTemp = new ImageIcon(ImageIO.read(stream));
		stream = getClass().getResourceAsStream("play.jpg"); 
		ImageIcon playTemp = new ImageIcon(ImageIO.read(stream));


		final ImageIcon playIcon = playTemp;
		final ImageIcon pauseIcon = pauseTemp;

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
		newGame.addActionListener(e -> gameOption(pauseIcon, playIcon));
		JButton loadGame = new JButton();
		loadGame.setBounds(189, 360, 189, 50);
		loadGame.setText("Load Game");
		loadGame.setFocusPainted(false);
		loadGame.setBackground(Color.WHITE);
		loadGame.addActionListener(new loadListener());

		add(newGame);
		add(loadGame);
		add(background);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

	}
	
	class loadListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			InputStream stream = getClass().getResourceAsStream("pause.png");
			ImageIcon pause = null;
			
			try
			{
				pause = new ImageIcon(ImageIO.read(stream));
			}
			catch(Exception a)
			{
				System.out.println("Could not load image");
			}
			
			Game game = new Game(1, normalTimer);
			game.load(pause);
		}
	}

	private void gameOption(ImageIcon pauseIcon, ImageIcon playIcon) {
		JFrame newGame = new JFrame();
		newGame.setTitle("Select New Game Options");
		newGame.setSize(500, 300);
		newGame.setResizable(false);
		newGame.setLayout(new GridLayout(0,1));
		newGame.setLocationRelativeTo(this);

		JPanel difficultyPanel = new JPanel();
		JPanel timerPanel = new JPanel();
		JPanel backgroundPanel = new JPanel();
		JPanel playPanel = new JPanel();

		JToggleButton easy = new JToggleButton("Easy");
		JToggleButton medium = new JToggleButton("Medium");
		JToggleButton hard = new JToggleButton("Hard");
		JToggleButton veryHard = new JToggleButton("Very Hard");

		ButtonGroup difficultyGroup = new ButtonGroup();
		ButtonGroup timerGroup = new ButtonGroup();

		JRadioButton normalTimer = new JRadioButton("Normal Timer");
		JRadioButton countdown = new JRadioButton("Countdown Timer");

		new JTextField();

		JButton play = new JButton("Play");
		play.setFocusPainted(false);
		play.setBackground(Color.WHITE);

		String[] backgrounds = {"Default", "Red", "Orange", "Yellow", "Green", "Blue", "Purple", "Pink", "Disco", "Etown"};	
		JComboBox<Object> options = new JComboBox<Object>(backgrounds);
		options.setSelectedIndex(0);

		options.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				choice = ((String)options.getSelectedItem() + ".png").toLowerCase();
				setChoice(choice);
			}
		});

		easy.setFocusPainted(false);
		easy.setBackground(Color.WHITE);
		medium.setFocusPainted(false);
		medium.setBackground(Color.WHITE);
		hard.setFocusPainted(false);
		hard.setBackground(Color.WHITE);
		veryHard.setFocusPainted(false);
		veryHard.setBackground(Color.WHITE);

		difficultyGroup.add(easy);
		difficultyGroup.add(medium);
		difficultyGroup.add(hard);
		difficultyGroup.add(veryHard);

		timerGroup.add(normalTimer);
		timerGroup.add(countdown);

		easy.addActionListener(e -> setDifficulty(1));
		medium.addActionListener(e -> setDifficulty(2));
		hard.addActionListener(e -> setDifficulty(3));
		veryHard.addActionListener(e -> setDifficulty(4));

		normalTimer.addActionListener(e -> setTimer(true));
		countdown.addActionListener(e -> setTimer(false));

		play.addActionListener(e -> newGame(newGame,pauseIcon,playIcon));

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

	private void newGame(JFrame newGame, ImageIcon pause, ImageIcon play) {
		if(difficulty == 0){
			return;
		}
		Game game = new Game(difficulty, normalTimer);
		Board.setImage(choice);
		
		if(!normalTimer)
			game.setTimerFrame(pause, play);
		
		newGame.dispose();
		dispose();
	}

	private void setDifficulty(int difficulty){
		this.difficulty = difficulty;
	}

	public void setChoice(String choice) {
		this.choice = choice;
	}

	private boolean setTimer(boolean normalTimer) {
		this.normalTimer = normalTimer;
		return normalTimer;
	}


	public void load() { //needs polish

		ImageIcon pauseTemp = null;
		try {
			InputStream stream = getClass().getResourceAsStream("pause.png"); 
			pauseTemp = new ImageIcon(ImageIO.read(stream));
		} catch (IOException e1) {
			System.err.println("could not load pause and play icons");
		}
		final ImageIcon pause = pauseTemp;

		Game oldGame = new Game(4,true);
		oldGame.setVisible(false);
		oldGame.load(pause);

		//dispose();

	}

}

