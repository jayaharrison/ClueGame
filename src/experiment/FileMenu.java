/**
 * @author Jay Harrison
 * @author Adam Kinard
 * 
 * Creates menu and custom dialog for detective notes
 */

package experiment;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class FileMenu extends JMenuBar{
	private JDialog notesDialog;
	private JMenu fileMenuOption;
	private JMenuItem notes;
	private JMenuItem exit;
	
	public FileMenu() {
		// Create
		createNotesDialog();
		createMenuBar();
	}
	

	private void createMenuBar() {
		// Initialize
		fileMenuOption = new JMenu("File");
		notes = new JMenuItem("Show Notes");
		exit = new JMenuItem("Exit");
		
		// Setup- exit
		exit.addActionListener(new exitListener());
		
		// Setup- notes
		notes.addActionListener(new notesDialogListener());
		
		// Add all to JMenuBar
		fileMenuOption.add(notes);
		fileMenuOption.add(exit);
		this.add(fileMenuOption);
	}
	
	// Exit listener
	public class exitListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			// Exit
			System.exit(0);
		}
	}
	
	// Window listener
	public class notesDialogListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			// Show dialog
			notesDialog.setVisible(true);
		}
	}
	
	private void createNotesDialog() {
		// Initialize
		notesDialog = new JDialog();
		JDialog n = notesDialog;
		
		// Set attributes
		n.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		n.setTitle("Detective Notes");
		n.setSize(425, 600);
		
		// CHECKBOXES PANEL ************************************
		JPanel checkboxes = new JPanel();
		checkboxes.setLayout(new GridLayout(3, 1));
		
		// People boxes
		JPanel people = new JPanel();
		people.setLayout(new GridLayout(3, 2));
		
		JCheckBox redBox = new JCheckBox("Miss Scarlett");
		people.add(redBox);
		JCheckBox magentaBox = new JCheckBox("Professor Plum");
		people.add(magentaBox);
		JCheckBox blueBox = new JCheckBox("Mrs. Peacock");
		people.add(blueBox);
		JCheckBox greenBox = new JCheckBox("Reverend Green");
		people.add(greenBox);
		JCheckBox yellowBox = new JCheckBox("Colonel Mustard");
		people.add(yellowBox);
		JCheckBox whiteBox = new JCheckBox("Mrs. White");
		people.add(whiteBox);
		// Title
		people.setBorder(new TitledBorder(new EtchedBorder(), "People"));
		checkboxes.add(people);
		
		// Room boxes
		JPanel rooms = new JPanel();
		rooms.setLayout(new GridLayout(5, 2));
		
		JCheckBox kitchen = new JCheckBox("Kitchen");
		rooms.add(kitchen);
		JCheckBox diningRoom= new JCheckBox("Dining Room");
		rooms.add(diningRoom);
		JCheckBox planetarium = new JCheckBox("Planetarium");
		rooms.add(planetarium);
		JCheckBox reptile = new JCheckBox("Reptile Room");
		rooms.add(reptile);
		JCheckBox hydro = new JCheckBox("Hydroponics Lab");
		rooms.add(hydro);
		JCheckBox tool = new JCheckBox("Tool Closet");
		rooms.add(tool);
		JCheckBox cream = new JCheckBox("Crematorium");
		rooms.add(cream);
		JCheckBox cinema = new JCheckBox("Cinema");
		rooms.add(cinema);
		JCheckBox balls = new JCheckBox("Ballroom");
		rooms.add(balls);
		//Title
		rooms.setBorder(new TitledBorder(new EtchedBorder(), "Rooms"));
		checkboxes.add(rooms);
		
		// Weapon boxes
		JPanel weapons = new JPanel();
		weapons.setLayout(new GridLayout(3, 2));
		
		JCheckBox candlestick = new JCheckBox("Candlestick");
		weapons.add(candlestick);
		JCheckBox dumbell = new JCheckBox("Dumbell");
		weapons.add(dumbell);
		JCheckBox pipe = new JCheckBox("Lead Pipe");
		weapons.add(pipe);
		JCheckBox gun = new JCheckBox("Revolver");
		weapons.add(gun);
		JCheckBox rope = new JCheckBox("Rope");
		weapons.add(rope);
		JCheckBox wrench = new JCheckBox("Wrench");
		weapons.add(wrench);
		// Title
		weapons.setBorder(new TitledBorder(new EtchedBorder(), "Weapons"));
		checkboxes.add(weapons);
		
		// Add checkboxes to main panel
		n.add(checkboxes, BorderLayout.WEST);
		
		// DROPDOWN PANEL ******************************************
		JPanel dropdowns = new JPanel();
		dropdowns.setLayout(new GridLayout(3, 1));
		
		// People dropdown
		String[] playerGuess = { "Miss Scarlett", "Professor Plum", "Mrs. Peacock", "Reverend Green", "Colonel Mustard", "Mrs. White"};
		JComboBox playerBox = new JComboBox(playerGuess);
		playerBox.setBorder(new TitledBorder(new EtchedBorder(), "Player Guess"));
		dropdowns.add(playerBox);
		
		// Room dropdown
		String[] roomGuess = { "Kitchen", "Dining Room", "Planetarium", "Reptile Room", "Hydroponics Lab", 
				"Tool Closet", "Crematorium", "Cinema", "Ballroom"};
		JComboBox roomBox = new JComboBox(roomGuess);
		playerBox.setBorder(new TitledBorder(new EtchedBorder(), "Room Guess"));
		dropdowns.add(roomBox);
		
		// Weapon dropdown
		String[] weaponGuess = { "Candlestick", "Dumbell", "Lead Pipe", "Revolver", "Rope", "Wrench"};
		JComboBox weaponBox = new JComboBox(weaponGuess);
		playerBox.setBorder(new TitledBorder(new EtchedBorder(), "Weapon Guess"));
		dropdowns.add(weaponBox);
		
		// Add dropdowns to main panel
		n.add(dropdowns, BorderLayout.CENTER);
		
		}

}
