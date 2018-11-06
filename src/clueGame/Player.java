/**
 * @author Jay Harrison
 * @author Adam Kinard
 */

package clueGame;

import java.awt.Color;
import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

public class Player {

	private String playerName;
	private int row;
	private int column;
	private String color;
	
	protected Set<Card> hand;
	protected Set<Card> seen;
	
	/**
	 * Player constructor including name, color, and position
	 * @param playerName
	 * @param color
	 * @param row
	 * @param column
	 */
	public Player(String playerName, String color, int row, int column) {
		this.playerName = playerName;
		this.color = color;
		this.row = row;
		this.column = column;
		
		seen = new HashSet<Card>();
		hand = new HashSet<Card>();
	}
	
	public Player() {
		seen = new HashSet<Card>();
		hand = new HashSet<Card>();
	}
	
	/**
	 * Provides a card from the player's deck that disproves a suggestion
	 * @param suggestion
	 * @return
	 */
	public Card disproveSuggestion(Solution suggestion) {
		return null;
	}
	
	public void createSuggestion() {}
	
	/**
	 * Moves a player to a specific location
	 * @param row
	 * @param column
	 */
	public void move(int row, int column) {
		this.row = row;
		this.column = column;
		
		// Lets human choose from target list
		
		// comp will randomly select from target list
	}

	public BoardCell pickLocation(Set<BoardCell> targets) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
	// GETTERS & SETTERS
	/**
	 * Name getter
	 * @return
	 */
	public String getPlayerName() {
		return playerName;
	}

	/**
	 * Row getter
	 * @return
	 */
	public int getRow() {
		return row;
	}

	/**
	 * Column getter
	 * @return
	 */
	public int getColumn() {
		return column;
	}

	/**
	 * Color getter
	 * @return
	 */
	public String getColor() {
		
		//Color charColor = convertColor(color);
		return color;
	}
	
	
	/**
	 * Color converter to use with player colors
	 * @param strColor
	 * @return
	 */
	public Color convertColor(String strColor) {
		Color color;
		try {
			// We can use reflection to convert the string to a color
			Field field = Class.forName("java.awt.Color").getField(strColor.trim());
			color = (Color)field.get(null);
		} catch (Exception e) {
			color = null; // Not defined
		}
		return color;
	}
	
	
	/**
	 * Player's cards getter
	 * @return
	 */
	public Set<Card> getHand() {
		return hand;
	}
	
	/**
	 * Player's seen cards getter
	 * @return
	 */
	public Set<Card> getSeen(){
		return seen;
	}
	
	public void addCardToHand(Card card) {
		hand.add(card);
	}
	
	
	public void addCardToSeen(Card card) {
		seen.add(card);
	}
	
}
