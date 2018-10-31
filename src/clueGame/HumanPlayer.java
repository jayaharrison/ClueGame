package clueGame;

import java.awt.Color;
import java.util.Set;

public class HumanPlayer extends Player {

	/**
	 * HumanPlayer constructor, invokes parent constructor
	 * @param playerName
	 * @param color
	 * @param row
	 * @param column
	 */
	public HumanPlayer(String playerName, Color color, int row, int column) {
		super(playerName, color, row, column);
	}
	
	/**
	 * Prompts player to decide on game piece movement
	 * @param targets
	 * @return
	 */
	public BoardCell pickLocation(Set<BoardCell> targets) {
		BoardCell temp = new BoardCell();
		return temp;	
	}
	
	public void makeAccusation() {
		
	}
	
	public void createSuggestion() {
		
	}

}
