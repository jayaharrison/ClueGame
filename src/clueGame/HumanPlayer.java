/**
 * @author Jay Harrison
 * @author Adam Kinard
 * 
 * HumanPlayer class to be updated with GUI features and monitor IRL player
 */
package clueGame;

import java.awt.Color;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class HumanPlayer extends Player {

	/**
	 * HumanPlayer constructor, invokes parent constructor
	 * @param playerName
	 * @param color
	 * @param row
	 * @param column
	 */
	public HumanPlayer(String playerName, String color, int row, int column) {
		super(playerName, color, row, column);
	}
	
	public HumanPlayer() {
		super();
	}
	
	// TEMP UNTIL HUMAN LOGIC INSTALLED
	@Override
	public Card disproveSuggestion(Solution suggestion) {

		Card room = suggestion.room;
		Card person = suggestion.person;
		Card weapon = suggestion.weapon;
			
		boolean containsRoom = hand.contains(room);
		boolean containsPerson = hand.contains(person);
		boolean containsWeapon = hand.contains(weapon);
		
		Set<Card> newHand = new HashSet<Card>();
		
		if (containsRoom) newHand.add(room);
		if (containsPerson) newHand.add(person);
		if (containsWeapon) newHand.add(weapon);
		
		if (!containsRoom && !containsPerson && !containsWeapon) {
			return null;
		} else if (hand.contains(room) || hand.contains(person) || hand.contains(weapon)) {
			int size = newHand.size();
			int num = new Random().nextInt(size);
			int i = 0;
			for (Card c : newHand) {
				if (i == num) {
					return c;
				}
				i++;
			}

		}
		return null; 


	}
}
