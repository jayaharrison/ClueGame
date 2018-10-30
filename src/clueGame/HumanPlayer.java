package clueGame;

import java.awt.Color;
import java.util.Set;

public class HumanPlayer extends Player {

	public HumanPlayer(String playerName, Color color, int row, int column) {
		super(playerName, color, row, column);
	}
	
	public BoardCell pickLocation(Set<BoardCell> targets) {
		BoardCell temp = new BoardCell();
		return temp;	
	}
	
	public void makeAccusation() {
		
	}
	
	public void createSuggestion() {
		
	}

}
