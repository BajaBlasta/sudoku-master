import java.io.IOException;

import javax.swing.UIManager;

public class Sudoku {
	public static void main(String[] args) throws IOException {
		
		try {
            UIManager.setLookAndFeel(
                UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.err.println("Couldn't use system look and feel.");
        }
		
		//boolean normalTimer = true;	//sets the timer to count up, instead of down- still needs to be implemented in StartPage
		StartPage startPage = new StartPage(); //create new Start Page
		
		System.out.println("WHAT IS GOING ON HERE"); //a print statement
		
	}
}
