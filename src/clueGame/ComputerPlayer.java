/**
 * @author Jay Harrison
 * @author Adam Kinard
 */

package clueGame;

import java.awt.Color;
import java.util.Set;

public class ComputerPlayer extends Player {
	
	public ComputerPlayer(String playerName, Color color, int row, int column) {
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
