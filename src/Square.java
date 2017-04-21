import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JToggleButton;

public class Square extends JToggleButton {

	private boolean isLocked; //variable is set to true if value inside square was generated
	private short value; //current number inside square
	private Square[][] board; //reference to all other squares on the board
	private int r, c;

	public Square(short val, Square[][] brd, int row, int col) {
		r = row;
		c = col;
		board = brd;
		value = val;
		updateSquare(value);
		setSize(61, 61);
		setBackground(Color.WHITE);
		setFocusPainted(false);
		setBorderPainted(false);
		setContentAreaFilled(false);
		addMouseListener(new ClickListener());
		addKeyListener(new NumberListener());
	}

	//updates the squares characteristics and value
	public void updateSquare(short val) {
		if(val == 0)
			isLocked = false;
		else
			isLocked = true;
		if(isLocked) {
			setFont(new Font(null, 1, 25));
			setText(val + "");
			setValue(val);
		} else {
			setFont(new Font(null, 0, 25));
			setText(" ");
			setValue(0);
		}
	}

	//returns the current value stored in the square
	public short getValue() { return value; }

	//sets the value in the square without change any other attributes
	public void setValue(int val) { value = (short)val; }

	//returns the list of all squares on the board
	public Square[][] getBoard() { return board; }

	//returns true if the square is locked and false otherwise
	public boolean isLocked() { return isLocked; }
	
	public void setLocked(Boolean b) { isLocked = b; } 

	public int getRow() { return r; }

	public int getCol() { return c; }

	//untoggles every other button on the board
	private static class ClickListener implements MouseListener {
		public void mouseReleased(MouseEvent e) {
			Square square = (Square) e.getSource();
			Square[][] board = square.getBoard();
			for(int i = 0; i < board.length; ++i)
				for(int j = 0; j < board.length; ++j)
					if(!board[i][j].equals(square))
						board[i][j].setSelected(false);
		}
		public void mouseClicked(MouseEvent e) {}
		public void mouseEntered(MouseEvent e) {}
		public void mouseExited(MouseEvent e) {}
		public void mousePressed(MouseEvent e) {}
	}

	//detects when someone activates a button
	private static class NumberListener implements KeyListener {
		public void keyPressed(KeyEvent e) {
			Square square = (Square) e.getSource();
			if(!square.isLocked()) {
				if(e.getKeyCode() == 32 || e.getKeyCode() == 8) { //keycodes for space bar and backspace
					square.setText(" ");
					square.setValue((short)0);
				}
				else {
					switch(e.getKeyChar()) {
						case '1': square.setText("1"); square.setValue(1); break;
						case '2': square.setText("2"); square.setValue(2); break;
						case '3': square.setText("3"); square.setValue(3); break;
						case '4': square.setText("4"); square.setValue(4); break;
						case '5': square.setText("5"); square.setValue(5); break;
						case '6': square.setText("6"); square.setValue(6); break;
						case '7': square.setText("7"); square.setValue(7); break;
						case '8': square.setText("8"); square.setValue(8); break;
						case '9': square.setText("9"); square.setValue(9); break;
					}
				}
				checkIntersection(square.getBoard());
			}
		}
		public void keyReleased(KeyEvent e) {}
		public void keyTyped(KeyEvent e) {}
	}

	//colors the intersecting values red
	public static void checkIntersection(Square[][] board) {
		//set all squares black on the board
		for(int i = 0; i < board.length; ++i)
			for(int j = 0; j < board.length; ++j)
				board[i][j].setForeground(Color.BLACK);

		//turn all intersecting values red
		for(int i = 0; i < board.length; ++i)
			for(int j = 0; j < board.length; ++j)
				turnRed(board[i][j]);
	}

	//turns values red if they have the same value
	private static void turnRed(Square square) {
		Square[][] board = square.getBoard();
		int row = square.getRow();
		int col = square.getCol();

		//checks for intersecting values within a column or row
		for(int i = 0; i < board.length; ++i) {
			if(square.valueEquals(board[row][i])) { //row
				square.setForeground(Color.RED);
				board[row][i].setForeground(Color.RED);
			}
			if(square.valueEquals(board[i][col])) { //column
				square.setForeground(Color.RED);
				board[i][col].setForeground(Color.RED);
			}
		}
		
		//checks for intersecting values within subgrids
		int r = 0, c = 0;
		if(row < 3 && col < 3) {
			r = 0; c = 0;
		} else if(row < 3 && col < 6) {
			r = 0; c = 3;
		} else if(row < 3 && col < 9) {
			r = 0; c = 6;
		} else if(row < 6 && col < 3) {
			r = 3; c = 0;
		} else if(row < 6 && col < 6) {
			r = 3; c = 3;
		} else if(row < 6 && col < 9) {
			r = 3; c = 6;
		} else if(row < 9 && col < 3) {
			r = 6; c = 0;
		} else if(row < 9 && col < 6) {
			r = 6; c = 3;
		} else if(row < 9 && col < 9) {
			r = 6; c = 6;
		}
		
		for(int i = 0; i < 3; ++i)
			for(int j = 0; j < 3; ++j) {
				if(square.valueEquals(board[r + i][c + j])) {
					square.setForeground(Color.RED);
					board[r + i][c + j].setForeground(Color.RED);
				}
			}
	}
	
	//returns true if squares have the same value but are different squares
	public boolean valueEquals(Square other) {
		if(getValue() == other.getValue() && !equals(other))
			return true;
		return false;
	}
}
