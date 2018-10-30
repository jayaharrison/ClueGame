package clueGame;

import java.awt.Color;

public class Player {

	private String playerName;
	private int row;
	private int column;
	private Color color;
	
	public Player(String playerName, Color color, int row, int column) {
		this.playerName = playerName;
		this.color = color;
		this.row = row;
		this.column = column;
	}
	
	
	public Card disproveSuggestion(Solution suggestion) {
		return null;
		
	}
	
}
