package clueGame;


import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class SplashScreen extends JFrame {
	private String playerName;
	private JOptionPane frame;
	
	public void SplashScreen() {
		createSplashScreen();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	@SuppressWarnings("static-access")
	private void createSplashScreen() {
		// Create frame
		frame = new JOptionPane();
		playerName = "Test";
		
		// Populate
		frame.showMessageDialog(frame, "You are " + playerName + ", press Next Player to begin", 
				"Welcome to Clue", JOptionPane.INFORMATION_MESSAGE);
		
		// Add
		this.add(frame);
	}
}
