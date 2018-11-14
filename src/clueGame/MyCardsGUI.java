package clueGame;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class MyCardsGUI extends JPanel {
	
	
	private JPanel weapons;
	private JPanel rooms;
	private JPanel people;
	ArrayList<String> personCard = new ArrayList();
	ArrayList<String> roomCard = new ArrayList();
	ArrayList<String> weaponCard = new ArrayList();
	
	
	
	public MyCardsGUI(Set<Card> playerHand) {
		
		
		for(Card c : playerHand) {
			if (c.getCardType() == CardType.PERSON) {
				personCard.add(c.getName());
			}
			else if (c.getCardType() == CardType.ROOM) {
				roomCard.add(c.getName());
			}
			else if (c.getCardType() == CardType.WEAPON) {
				weaponCard.add(c.getName());
			}
		}
		
		
		
		
		//Adds Whose Turn? entry, Next Player button and Make Accusation Button
		JPanel panel = createCardPanel();
		add(panel);
	
	}
	
	/**
	 * Creates top panel of bottom menu
	 * @return
	 */
	private JPanel createCardPanel() {		
		// JPanel for cards
		JPanel myCards = new JPanel();
		myCards.setLayout(new GridLayout(3, 1));
		myCards.setBorder(new TitledBorder (new EtchedBorder(), "My Cards"));
		
		// Panels for people, rooms, weapons	
		//People panel
		people = new JPanel();
		
		people.setSize(30, 50);
		people.setLayout(new GridLayout(3, 1));
		people.setBorder(new TitledBorder (new EtchedBorder(), "People"));
		
		// Room panel
		rooms = new JPanel();

		rooms.setSize(30, 50);
		rooms.setLayout(new GridLayout(3, 1));
		rooms.setBorder(new TitledBorder (new EtchedBorder(), "Rooms"));

		// Weapon panel
		weapons = new JPanel();

		weapons.setSize(30, 50);
		weapons.setLayout(new GridLayout(3, 1));
		weapons.setBorder(new TitledBorder (new EtchedBorder(), "Weapons"));

		
		
		for (int i = 0; i < personCard.size(); i++) {
			JTextField person = new JTextField(personCard.get(i));
			person.setEditable(false);
			people.add(person);
		}
		
		for (int i = 0; i < roomCard.size(); i++) {
			JTextField room = new JTextField(roomCard.get(i));
			room.setEditable(false);
			rooms.add(room);
		}
		
		for (int i = 0; i < weaponCard.size(); i++) {
			JTextField person = new JTextField(weaponCard.get(i));
			person.setEditable(false);
			weapons.add(person);
		}
		
		
		// Create people
		JTextField person = new JTextField("Mr. Green");
		person.setEditable(false);
		JTextField person2 = new JTextField("Colonel Mustard");
		person2.setEditable(false);
		
		// Add people to panel
		
		
		
		
		
		
		//people.add(person);
		//people.add(person2);
		
		
		myCards.add(people);
		myCards.add(rooms);
		myCards.add(weapons);
		
		
		return myCards;
		
	}
	
}
