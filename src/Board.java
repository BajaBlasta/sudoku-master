import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Board extends JPanel {

	private static final int SIZE = 9;
	private Square[][] board = new Square[SIZE][SIZE]; //grid of squares that for the board
	protected short[] solution; //holds original solution to the puzzle
	private BufferedImage image; //background image of the board

	public Board(int diff) {
		//Load in the background image
		try {                
			image = ImageIO.read(new File("src/resources/board.png"));
		} catch (IOException ex) {
			System.out.println("Image not found!");
		}
		
		setLayout(null);
		setSize(image.getWidth(), image.getHeight());

		short[] generated = Generator.generateSudoku();
		solution = generated.clone();
		
		generated = removeNumbers(generated, diff);
		System.out.println(Generator.isUniqelySolvable(generated) ? 
							"uniquely solvable" : "not uniquely solvable");
		
		createBoard(generated);
	}
	
	//loads a new value into a square
	public void setSquare(int r, int c, short val) {
		board[r][c].updateSquare(val);
	}
	
	//returns the square at the specified location on the board
	public Square getSquare(int r, int c){
		return board[r][c];
	}

	//resets the board with a random new board at a specified difficulty
	public void reset(int diff) {
		short[] newBoard = Generator.generateSudoku();
		solution = newBoard.clone();
		removeNumbers(newBoard, diff);
		int k = 0;
		for(int i = 0; i < SIZE; ++i)
			for(int j = 0; j < SIZE; ++j) {
				setSquare(i, j, newBoard[k]);
				++k;
			}
	}
	
	//creates squares and adds them to the board and repositions them to fit on the image
	private void createBoard(short[] gen) {
		//creates squares and adds them to the board
		int k = 0;
		for(int i = 0; i < SIZE; ++i)
			for(int j = 0; j < SIZE; ++j) {
				board[i][j] = new Square(gen[k], board, i, j);
				add(board[i][j]);
				++k;
			}
		
		//repositions buttons to fit board image
		int x = 3, y = 3;
		int xOffset = 1, yOffset = 1;
		for(int i = 0; i < SIZE; i++) {
			for(int j = 0; j < SIZE; j++) {
				board[i][j].setLocation(x, y);
				if(xOffset == 3) {
					x += 64;
					xOffset = 1;
				} else {
					x += 62;
					xOffset++;
				}
			}
			x = 3;
			if(yOffset == 3) {
				y += 64;
				yOffset = 1;
			} else {
				y += 62;
				yOffset++;
			}
		}
	}
	
	// removes numbers
	// medium and hard are now guaranteed to produce uniquely solvable puzzles
	private static short[] removeNumbers(short[] gen, int diff) {
		if (diff == 1) {
			for(int i = 0; i < gen.length; ++i)
				if(Math.random() < .3)
					gen[i] = 0;
		}
		else if(diff == 2){
//			for(int i = 0; i < gen.length; ++i)
//				if(Math.random() < .6)
//					gen[i] = 0;
			return Generator.generatePuzzle(gen, 32);
		} else if(diff == 3) {
			return Generator.generatePuzzle(gen, 25);
		}
		else if(diff == 4){
			for(int i = 0; i < gen.length; ++i)
				if(Math.random() < .79)
					gen[i] = 0;
		}
		return gen;
	}
	
	public void checkProgress() {
		int k = 0;
		for(int i = 0; i < SIZE; ++i) {
			for(int j = 0; j < SIZE; ++j) {
				if(!board[i][j].isLocked() && !board[i][j].getForeground().equals(Color.RED)) {
					if(board[i][j].getValue() == solution[k])
						board[i][j].setForeground(Color.GREEN);
					else
						board[i][j].setForeground(Color.ORANGE);
				}
				++k;
			}
		}
	}
	
	//return the size of the board
	public int getBoardSize() { return SIZE; }
	
	public short[] getSolution() { return solution; }
	
	public Square[][] getBoard() { return board; }

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(image, 0, 0, this);         
	}
}
