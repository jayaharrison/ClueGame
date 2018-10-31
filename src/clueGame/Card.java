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
	
	// FOR TESTING
	public Card() {
		cardName = "Card";
		type = CardType.OTHER;
	}
	
	public boolean equals() {
		return false; //update later
	}
	
	public String getName() {
		return cardName;
	}
	
	public CardType getCardType() {
		return type;
	}

}
