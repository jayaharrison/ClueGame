/**
 * @author Jay Harrison
 * @author Adam Kinard
 */

package clueGame;

import java.awt.Color;
import java.util.HashSet;
import java.util.Set;

public class ComputerPlayer extends Player {
	BoardCell currentRoom;
	Solution suggestion;
	Set<Card> seen = new HashSet<Card>();
	
	
	public ComputerPlayer(String playerName, String color, int row, int column) {
		super(playerName, color, row, column);
	}
	

	public BoardCell pickLocation(Set<BoardCell> targets) {
		BoardCell temp = new BoardCell();
		return temp;	
	}
	
	
	public void createSuggestion() {
		
	}
	
	public Solution getSuggestion() {
		return suggestion;
	}
	
	public void setCurrentRoom(BoardCell currentRoom) {
		this.currentRoom = currentRoom;
	}
	
	public void setSeen(Set<Card> seen) {
		this.seen = seen;
	}
	
	public Set<Card> getSeenPeople(){
		Set<Card> seenPeople = new HashSet<Card>();
		for (Card c : seen) {
			if (c.getCardType() == CardType.PERSON)
				seenPeople.add(c);
		}
		return seenPeople;
	}
	
	public Set<Card> getSeenWeapons(){
		Set<Card> seenWeapons = new HashSet<Card>();
		for (Card c : seen) {
			if (c.getCardType() == CardType.WEAPON)
				seenWeapons.add(c);
		}
		return seenWeapons;
	}


	public void revealCard(Card weapon) {
		
		
	}
	
	@Override
	public void move(int row, int column) {
		
	}

}
