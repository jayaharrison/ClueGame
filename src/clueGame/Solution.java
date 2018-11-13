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

	/**
	 * Get person
	 * @return
	 */
	public Card getPerson() {
		return person;
	}

	/**
	 * Set person
	 * @param person
	 */
	public void setPerson(Card person) {
		this.person = person;
	}

	/**
	 * Get room
	 * @return
	 */
	public Card getRoom() {
		return room;
	}

	/**
	 * Set room
	 * @param room
	 */
	public void setRoom(Card room) {
		this.room = room;
	}

	/**
	 * Get weapon
	 * @return
	 */
	public Card getWeapon() {
		return weapon;
	}

	/**
	 * Set weapon
	 * @param weapon
	 */
	public void setWeapon(Card weapon) {
		this.weapon = weapon;
	}

	

}
