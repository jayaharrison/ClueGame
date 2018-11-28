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

public class GuessDialog extends JDialog implements ActionListener {

	private JTextField currentRoom;
	private JTextField person;
	private JTextField weapon;
	public String savedRoomGuess;
	public String savedPersonGuess;
	public String savedWeaponGuess;
	
	JComboBox<String> playersDropdown;
	JComboBox<String> weaponsDropdown;
	JComboBox<String> roomsDropdown;
	JButton submit;
	JButton cancel;
	
	Board board;
	
	class GetSelectedItem implements ActionListener {


		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == playersDropdown) {
				savedPersonGuess = (String) playersDropdown.getSelectedItem();
				System.out.println(savedPersonGuess);
			}
			if (e.getSource() == roomsDropdown) {
				savedRoomGuess = (String) roomsDropdown.getSelectedItem();
				System.out.println(savedRoomGuess);
			}
			if (e.getSource() == weaponsDropdown) {
				savedWeaponGuess = (String) weaponsDropdown.getSelectedItem();
				System.out.println(savedWeaponGuess);
			}

		}
	}
	
	public GuessDialog(String roomEntered) {
		//Board
		Board board = Board.getInstance();
		
		//Settings
		setSize(300,300);
		setLayout(new GridLayout(4,2));
		
		// Room panel
		JLabel roomLabel = new JLabel("Room");
		currentRoom = new JTextField(20);
		currentRoom.setText(roomEntered);
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
		JButton cancel = new JButton("Cancel");
		
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

	private JComboBox<String> createWeaponsDropdown(Set<Card> weapons) {
		JComboBox<String> weaponList = new JComboBox<String>();
		for (Card w : weapons) {
			weaponList.addItem(w.getName());
		}
		return weaponList;
	}

	private JComboBox<String> createPlayersDropdown(Set<Card> players) {
		JComboBox<String> people = new JComboBox<String>();
		for (Card p : players) {
			people.addItem(p.getName());
		}
		return people;
	}
	

	public static void main(String[] args) {
		// Board init
		Board board = Board.getInstance();
		board.setConfigFiles("ClueLayout.csv", "ClueRooms.txt", "CluePlayers.txt", "ClueWeapons.txt");
		board.initialize();
		
		// Room init
		String room = board.getLegend().get('K');
		
		// Test
		GuessDialog main = new GuessDialog(room);
		main.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
}
