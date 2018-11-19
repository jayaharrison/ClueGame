/**
 * @author Jay Harrison
 * @author Adam Kinard
 */
package clueGame;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

/**
 * Menu bars
 * @author Adam Kinard
 * @author Jay Harrison
 *
 */


public class ControlGUI extends JPanel {
	
	private JTextField currentPlayer;
	private JTextField guessField;
	private JTextField responseField;
	private JTextField rollField;
	private int dieRoll;
	
	public ControlGUI() {
		//Create layout with 2 rows
		setLayout(new GridLayout(2,1));
		
		//Adds Whose Turn? entry, Next Player button and Make Accusation Button
		JPanel panel = createTopPanel();
		add(panel);
		
		//Adds Die, guess and Guess Result Panels
		panel = createBottomPanel();
		add(panel);
	
	}
	
	/**
	 * Creates top panel of bottom menu
	 * @return
	 */
	private JPanel createTopPanel() {
		//no layout specified, so this is flow
		JButton player = new JButton("Next Player");
		JButton accusation = new JButton("Make an Accusation");
		currentPlayer = new JTextField(20);
		currentPlayer.setSize(20, 10);
		currentPlayer.setEditable(false);
		currentPlayer.setBorder(new TitledBorder (new EtchedBorder(), "Whose turn?"));
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1, 3));
		panel.add(currentPlayer);
		panel.add(player);
		panel.add(accusation, getColorModel());
		return panel;
		
	}
		
	/**
	 * Creates bottom panel of bottom menu
	 * @return
	 */
	private JPanel createBottomPanel() {
		//Create panel for lowerUse a grid layout, 2 rows, 1 element per (label, text)
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1,3));
		
		//Create die roll panel
		JPanel roll = new JPanel();
		roll.setBorder(new TitledBorder (new EtchedBorder(), "Die Roll"));
		rollField = new JTextField(1);
		rollField.setEditable(false);
		roll.add(rollField);
		
		// Create guess panel
		JPanel guess = new JPanel();
		guess.setBorder(new TitledBorder (new EtchedBorder(), "Guess"));
		guessField = new JTextField(15);
		guessField.setEditable(false);
		guess.add(guessField);
		
		//Create guess response panel
		JPanel response = new JPanel();
		response.setBorder(new TitledBorder (new EtchedBorder(), "Guess Result"));
		responseField = new JTextField(15);
		responseField.setEditable(false);
		response.add(responseField);
	
		panel.add(roll, BorderLayout.CENTER);
		panel.add(guess, BorderLayout.CENTER);
		panel.add(response, BorderLayout.CENTER);
		
		return panel;
		
	}
	
	public void updateGUI(Player player) {
		dieRoll = (int)Math.floor(Math.random() * Math.floor(5)) + 1;
		rollField.setText(Integer.toString(dieRoll));
		currentPlayer.setText(player.getPlayerName());
	}
	
	// Next Player button listener
//	public class nextPlayerListener implements ActionListener {
//		public void actionPerformed(ActionEvent e) {
//			// Show dialog
//			notesDialog.setVisible(true);
//		}
//	}
	
	public int getDieRoll() {
		return dieRoll;
	}

	/**
	 * Creates control panel
	 * @param args
	 */
	public static void main(String[] args) {
		//Create a JFrame with all the normal functionality
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Control Panel");
		frame.setSize(1000, 200);
		//Create the JPanel and add it to the JFrame
		ControlGUI gui = new ControlGUI();
		frame.add(gui, BorderLayout.CENTER);
		Player p = new Player();
		gui.updateGUI(p);
		//Now lets view
		frame.setVisible(true);
		
	}
	
	

}