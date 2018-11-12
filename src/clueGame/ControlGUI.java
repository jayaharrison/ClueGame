package clueGame;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

/**
 * Experiment with GUI: making the Lower panel of the Game
 * @author Adam Kinard
 * @author Jay Harrison
 *
 */


public class ControlGUI extends JPanel {
	
	private JTextField name;
	private JTextField guessField;
	private JTextField responseField;
	private JTextField rollField;
	
	public ControlGUI() {
		//Create layout with 2 rows
		setLayout(new GridLayout(2,0));
		
		//Adds Whose Turn? entry, Next Player button and Make Accusation Button
		JPanel panel = createTopPanel();
		add(panel);
		
		//Adds Die, guess and Guess Result Panels
		panel = createBottomPanel();
		add(panel);
	
	}
	
	/**
	 * Methods for the Buttons
	 * @return
	 */
	
	private JPanel createTopPanel() {
		//no layout specified, so this is flow
		JButton player = new JButton("Next Player");
		JButton accusation = new JButton("Make an Accusation");
		name = new JTextField(20);
		name.setSize(20, 10);
		name.setEditable(false);
		name.setBorder(new TitledBorder (new EtchedBorder(), "Whose turn?"));
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1, 1));
		panel.add(name);
		panel.add(player);
		panel.add(accusation, getColorModel());
		return panel;
		
	}
		
	private JPanel createBottomPanel() {
		JPanel panel = new JPanel();
		//Use a grid layout, 2 rows, 1 element per (label, text)
		panel.setLayout(new GridLayout(2,1));
		JLabel roll = new JLabel("Roll");
		JLabel guess = new JLabel("Guess");
		JLabel response = new JLabel("Response");
		rollField = new JTextField(20);
		guessField = new JTextField(20);
		responseField = new JTextField(20);
		rollField.setEditable(false);
		guessField.setEditable(false);
		responseField.setEditable(false);
		panel.add(roll, BorderLayout.CENTER);
		panel.add(guess, BorderLayout.CENTER);
		panel.add(response, BorderLayout.CENTER);
		panel.add(responseField, BorderLayout.CENTER);
		panel.add(guessField, BorderLayout.CENTER);
		panel.add(rollField, BorderLayout.CENTER);
		roll.setBorder(new TitledBorder (new EtchedBorder(), "Die"));
		guess.setBorder(new TitledBorder (new EtchedBorder(), "Guess"));
		response.setBorder(new TitledBorder (new EtchedBorder(), "Guess Result"));
		return panel;
		
	}

	
	
	
	public static void main(String[] args) {
		//Create a JFrame with all the normal functionality
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Control Panel");
		frame.setSize(1000, 200);
		//Create the JPanel and add it to the JFrame
		ControlGUI gui = new ControlGUI();
		frame.add(gui, BorderLayout.CENTER);
		//Now lets view
		frame.setVisible(true);
		
	}

}