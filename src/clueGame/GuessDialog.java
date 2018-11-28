package clueGame;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
		setSize(300,300);
		board = Board.getInstance();
		board.setConfigFiles("ClueLayout.csv", "ClueRooms.txt", "CluePlayers.txt", "ClueWeapons.txt");
		board.initialize();
		setLayout(new GridLayout(4,2));
		
		JLabel roomLabel = new JLabel("Your Room");
		//Cannot make changes to this since you are in this room
		currentRoom = new JTextField(20);
		currentRoom.setText(roomEntered);
		currentRoom.setEditable(false);
		
		JLabel personLabel = new JLabel("Person");
		JLabel weaponLabel = new JLabel("Weapon");
		
		playersDropdown = new JComboBox<String>();
		playersDropdown = createPlayersDropdown(board.getPeople());
		
		weaponsDropdown = new JComboBox<String>();
		weaponsDropdown = createWeaponsDropdown(board.getWeapons());
		
		roomsDropdown = new JComboBox<String>();
		roomsDropdown = createRoomsDropdown(board.getRooms());
		
		submit = new JButton();
		submit = submitPanel();
		
		cancel = new JButton();
		cancel = cancelPanel();
		
		add(roomLabel);
		add(roomsDropdown);
		add(personLabel);
		add(playersDropdown);
		add(weaponLabel);
		add(weaponsDropdown);
		add(submit);
		add(cancel);
		
	}
	


	public GuessDialog() {
		setSize(300,300);
		board = Board.getInstance();
		board.setConfigFiles("ClueLayout.csv", "ClueRooms.txt", "CluePlayers.txt", "ClueWeapons.txt");
		board.initialize();
		setLayout(new GridLayout(4,2));
		
		JLabel roomLabel = new JLabel("Your Room");
		JLabel personLabel = new JLabel("Person");
		JLabel weaponLabel = new JLabel("Weapon");
		
		playersDropdown = new JComboBox<String>();
		playersDropdown = createPlayersDropdown(board.getPeople());
		
		weaponsDropdown = new JComboBox<String>();
		weaponsDropdown = createWeaponsDropdown(board.getWeapons());
		
		roomsDropdown = new JComboBox<String>();
		roomsDropdown = createRoomsDropdown(board.getRooms());
		
		JButton submit = new JButton();
		submit = submitPanel();
		
		JButton cancel = new JButton();
		cancel = cancelPanel();
		
		add(roomLabel);
		add(roomsDropdown);
		add(personLabel);
		add(playersDropdown);
		add(weaponLabel);
		add(weaponsDropdown);
		add(submit);
		add(cancel);
		
		
	}
	
	private JButton submitPanel() {
		JButton submit = new JButton("Submit");
		return submit;
	}
	
	private JButton cancelPanel() {
		JButton cancel = new JButton("Cancel");
		return cancel;
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
	
	private JComboBox<String> createRoomsDropdown(Set<Card> rooms) {
		JComboBox<String> roomList = new JComboBox<String>();
		for (Card r : rooms) {
			roomList.addItem(r.getName());
		}
		return roomList;
	}


	public static void main(String[] args) {
		GuessDialog main = new GuessDialog();
		main.setVisible(true);
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
}
