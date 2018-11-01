package clueGame;

import java.awt.Color;
import java.util.HashSet;
import java.util.Set;

public class Player {

	private String playerName;
	private int row;
	private int column;
	private Color color;
	
	private Set<Card> myCards;
	
	/**
	 * Player constructor including name, color, and position
	 * @param playerName
	 * @param color
	 * @param row
	 * @param column
	 */
	public Player(String playerName, Color color, int row, int column) {
		this.playerName = playerName;
		this.color = color;
		this.row = row;
		this.column = column;
		myCards = new HashSet<Card>();
	}
	
	/**
	 * Provides a card from the player's deck that disproves a suggestion
	 * @param suggestion
	 * @return
	 */
	public Card disproveSuggestion(Solution suggestion) {
		return new Card();
	}

	public String getPlayerName() {
		return playerName;
	}

	public int getRow() {
		return row;
	}

	public int getColumn() {
		return column;
	}

	public Color getColor() {
		return color;
	}
	
	public Set<Card> getPlayerCards() {
		return myCards;
	}
}
