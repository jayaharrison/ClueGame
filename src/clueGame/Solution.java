/**
 * @author Jay Harrison
 * @author Adam Kinard
 * 
 */

package clueGame;

public class Solution {
	public Card person;
	public Card room ;
	public Card weapon;
	
	/**
	 * Constructor for solution
	 * @param room
	 * @param person
	 * @param weapon
	 */
	public Solution(Card room, Card person, Card weapon) {
		this.person = person;
		this.room = room;
		this.weapon = weapon;
	}

	public Card getPerson() {
		return person;
	}

	public void setPerson(Card person) {
		this.person = person;
	}

	public Card getRoom() {
		return room;
	}

	public void setRoom(Card room) {
		this.room = room;
	}

	public Card getWeapon() {
		return weapon;
	}

	public void setWeapon(Card weapon) {
		this.weapon = weapon;
	}

	

}
