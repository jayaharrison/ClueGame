/**
 * @author Jay Harrison
 * @author Adam Kinard
 */

package clueGame;

public enum CardType {
	
	PERSON("Person"), WEAPON("Weapon"), ROOM("Room"), OTHER("Other");
	
	private String cardtype;
	
	/**
	 * CardType constructor
	 * @param cardtype
	 */
	CardType (String cardtype){
		this.cardtype = cardtype;
	}
	
	
	@Override 
	public String toString() {
		return cardtype;
	}
}
