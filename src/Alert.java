import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Alert extends JFrame {
	
	
	
	
	public Alert(boolean win){
		
		if(win){
			setSize(400,50);
			setTitle("Congratulations");
			setResizable(false);
			JLabel text = new JLabel("Congratulations, you completed the Sudoku board");
			setLayout(new BorderLayout());
			setLocationRelativeTo(null);
			add(text, BorderLayout.CENTER);
			setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			setVisible(true);
		}
		else{
			setSize(400,50);
			setTitle("Sorry");
			setResizable(false);
			JLabel text = new JLabel("Sorry, you did not complete the Sudoku board correctly");
			setLayout(new BorderLayout());
			setLocationRelativeTo(null);
			add(text, BorderLayout.CENTER);
			setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			setVisible(true);
		}
	}
	
	
	
	
}
