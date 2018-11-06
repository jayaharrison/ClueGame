/**
 * @author Jay Harrison
 * @author Adam Kinard
 */

package clueGame;

import java.awt.Color;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class ComputerPlayer extends Player {
	
	private char lastRoom;
	
	public ComputerPlayer(String playerName, String color, int row, int column) {
		super(playerName, color, row, column);
	}
	
	public ComputerPlayer() {
		super();
	}
	
	@Override
	public BoardCell pickLocation(Set<BoardCell> targets) {
		
		/*
		//Should only choose a valid target (calculating targets already tested –yay)
		//If room in list and has not been visited last:
		for ( BoardCell cell : targets ) {
			if ( cell.isDoorway() && !cell.getInitial().equals(lastRoom) ) {
				//choose room
				lastRoom = cell.getInitial();
				return cell;
			}
		}
		
		// Else, choose random cell and return
		int size = targets.size();
		int num = new Random().nextInt(size);
		int i = 0;
		for ( BoardCell cell : targets ) {
			if ( i == num ) {
				return cell;
			}
			i++;
		}
		
		// Something has gone wrong
		return null;
		*/
		return new BoardCell();
	}
	
	@Override
	public void createSuggestion() {
		// creates suggestion based on seen/unseen cards
	}
	
	@Override
	public Card disproveSuggestion(Solution suggestion) {
		//TODO
		return null;
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
	
	
	public void setLastRoom(char initial) {
		lastRoom = initial;
	}
	
	
	
	public void revealCard(Card weapon) {
		
		
	}
	
	@Override
	public void move(int row, int column) {
		
	}
	
	// TESTING ONLY
	
	public void setHand(Set<Card> hand) {
		this.hand = hand;
	}

}
