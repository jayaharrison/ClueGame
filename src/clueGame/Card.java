/**
 * @author Jay Harrison
 * @author Adam Kinard
 */

package clueGame;

public class Card {
	private String cardName;
	private CardType type;
	
	/**
	 * Card constructor, allows name and type to be set
	 * @param cardName
	 * @param type
	 */
	public Card(String cardName, CardType type) {
		this.cardName = cardName;
		this.type = type;
	}
	
	/**
	 * Equals for cards
	 * @param name
	 * @return
	 */
	public boolean equals(String name) {
		return (name == cardName); 
	}
	
	/**
	 * Card name getter
	 * @return
	 */
	public String getName() {
		return cardName;
	}
	
	/**
	 * Card type getter
	 * @return
	 */
	public CardType getCardType() {
		return type;
	}

	
	// FOR TESTING
	public Card() {
		cardName = "Card";
		type = CardType.OTHER;
	}
}
