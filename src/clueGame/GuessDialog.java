package clueGame;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class GuessDialog extends JDialog  {

	private JTextField currentRoom;
	private JTextField person;
	private JTextField weapon;
	private String personString;
	private String weaponString;
	public Card savedRoomGuess;
	public Card savedPersonGuess;
	public Card savedWeaponGuess;
	public Solution suggestion;

	public Board board;
	
	private Player player;
	
	JComboBox<String> playersDropdown;
	JComboBox<String> weaponsDropdown;
	JComboBox<String> roomsDropdown;
	JButton submit;
	JButton cancel;
	
	public GuessDialog(Player p) {
		//Board/player
		Board board = Board.getInstance();
		this.player = p;
		
		//Settings
		setSize(300,300);
		setLayout(new GridLayout(4,2));
		
		// Room panel
		JLabel roomLabel = new JLabel("Room");
		currentRoom = new JTextField(20);
		currentRoom.setText(board.getRoom(player).getName());
		savedRoomGuess = board.getRoom(player);
		currentRoom.setEditable(false);
		
		// Dropdowns
		JLabel personLabel = new JLabel("Person");
		JLabel weaponLabel = new JLabel("Weapon");
		
		playersDropdown = new JComboBox<String>();
		playersDropdown = createPlayersDropdown(board.getPeople());
		
		weaponsDropdown = new JComboBox<String>();
		weaponsDropdown = createWeaponsDropdown(board.getWeapons());
		
		//Buttons
		JButton submit = new JButton("Submit");
		submit.addActionListener(new SubmitGuessListener());
		
		JButton cancel = new JButton("Cancel");
		cancel.addActionListener(new CancelListener());
		
		// Add to main panel
		add(roomLabel);
		add(currentRoom);
		add(personLabel);
		add(playersDropdown);
		add(weaponLabel);
		add(weaponsDropdown);
		add(submit);
		add(cancel);
	}

	/**
	 * returns selected weapon from suggestion
	 * @param weapons
	 * @return
	 */
	private JComboBox<String> createWeaponsDropdown(Set<Card> weapons) {
		JComboBox<String> weaponList = new JComboBox<String>();
		for (Card w : weapons) {
			weaponList.addItem(w.getName());
		}
		return weaponList;
	}

	/**
	 * returns selected player from suggestion
	 * @param players
	 * @return
	 */
	private JComboBox<String> createPlayersDropdown(Set<Card> players) {
		JComboBox<String> people = new JComboBox<String>();
		for (Card p : players) {
			people.addItem(p.getName());
		}
		return people;
	}
	
	// ActionListeners
	public class SubmitGuessListener implements ActionListener {

		Board board = Board.getInstance();
		
		@Override
		public void actionPerformed(ActionEvent e) {
			personString = (String) playersDropdown.getSelectedItem();
			weaponString = (String) weaponsDropdown.getSelectedItem();
			
			// Find cards to make suggestion
			for (Card c : board.getAllCards()) {
				if ( c.getName().equals(personString) )
					savedPersonGuess = c;
				else if (c.getName().equals(weaponString))
					savedWeaponGuess = c;
			}
			
			// Set suggestion, flag
			((HumanPlayer) player).createHumanSuggestion(board.getRoom(player), savedPersonGuess, savedWeaponGuess);
			
			// Close
			dispose();
		}

	}


	public class CancelListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			dispose();
		}
	}
	



	
}
