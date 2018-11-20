/**
 * @author Jay Harrison
 * @author Adam Kinard
 */
package clueGame;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

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
	
	JButton player;
	JButton accusation;
	
	private JPanel topPanel;
	private JPanel bottomPanel;
	
	private int dieRoll;
	
	public ControlGUI() {
		//Create layout with 2 rows
		setLayout(new GridLayout(2,1));
		
		//Adds Whose Turn? entry, Next Player button and Make Accusation Button
		createTopPanel();
		add(topPanel);
		
		//Adds Die, guess and Guess Result Panels
		createBottomPanel();
		add(bottomPanel);
	
	}
	
	/**
	 * Creates top panel of bottom menu
	 * @return
	 */
	private void createTopPanel() {
		//no layout specified, so this is flow
		player = new JButton("Next Player");
		
		
		accusation = new JButton("Make an Accusation");
		
		currentPlayer = new JTextField(20);
		currentPlayer.setSize(20, 10);
		currentPlayer.setEditable(false);
		currentPlayer.setBorder(new TitledBorder (new EtchedBorder(), "Whose turn?"));
		
		topPanel = new JPanel();
		topPanel.setLayout(new GridLayout(1, 3));
		topPanel.add(currentPlayer);
		topPanel.add(player);
		topPanel.add(accusation, getColorModel());
	}
		
	/**
	 * Creates bottom panel of bottom menu
	 * @return
	 */
	private void createBottomPanel() {
		//Create panel for lowerUse a grid layout, 2 rows, 1 element per (label, text)
		bottomPanel = new JPanel();
		bottomPanel.setLayout(new GridLayout(1,3));
		
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
	
		bottomPanel.add(roll, FlowLayout.LEFT);
		bottomPanel.add(guess, FlowLayout.CENTER);
		bottomPanel.add(response, FlowLayout.RIGHT);
		
	}
	
	/**
	 * Updates the control panel with the next player's turn and die roll.
	 * @param player
	 * @param dieRoll
	 */
	public void updateGUI(Player player, int dieRoll) {
		// Update any and all GUI needs
		rollField.setText(Integer.toString(dieRoll));
		currentPlayer.setText(player.getPlayerName());
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
		frame.add(gui);
		Player p = new Player();
		//Now lets view
		frame.setVisible(true);
		
	}
	
	


}