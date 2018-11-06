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
	private Solution suggestion;
	BoardCell currentRoom;
	
	public ComputerPlayer(String playerName, String color, int row, int column) {
		super(playerName, color, row, column);
		currentRoom = Board.getInstance().getCellAt(getRow(), getColumn());
	}
	
	public ComputerPlayer() {
		super();
	}
	
	@Override
	public BoardCell pickLocation(Set<BoardCell> targets) {
		
		//Should only choose a valid target (calculating targets already tested –yay)
		//If room in list and has not been visited last:
		for ( BoardCell cell : targets ) {
			if ( cell.isDoorway() && cell.getInitial() != lastRoom ) {
				//choose room
				lastRoom = cell.getInitial();
				currentRoom = Board.getInstance().getCellAt(getRow(), getColumn());
				return cell;
			}
		}
		
		// Else, choose random cell and return
		int size = targets.size();
		int num = new Random().nextInt(size);
		int i = 0;
		for ( BoardCell cell : targets ) {
			if ( i == num ) {
				currentRoom = Board.getInstance().getCellAt(getRow(), getColumn());
				return cell;
			}
			i++;
		}
		
		// Something has gone wrong
		return null;
	}
	
	@Override
	public void createSuggestion() {
		
		Card room = Board.getInstance().getRoomWithInitial(currentRoom.getInitial());
		
		
		
		Card weapon = null;
		Set<Card> unseenWeapons = Board.getInstance().getWeapons();
		
		for ( Card w : unseenWeapons ) {
			if ( getSeen().contains(w) ) {
				unseenWeapons.remove(w);
			}
		}
		
		int sizeW = unseenWeapons.size();
		int numW = new Random().nextInt(sizeW);
		int j = 0;
		for ( Card w : unseenWeapons ) {
			if ( j == numW ) {
				weapon = w;
				break;
			}
			else {
				j++;
			}
		}
		
		Card player = null;
		Set<Card> unseenPlayers = Board.getInstance().getPeople();
		
		for ( Card p : unseenPlayers ) {
			if ( getSeen().contains(p) ) {
				unseenPlayers.remove(p);
			}
		}
		
		int size = unseenPlayers.size();
		int num = new Random().nextInt(size);
		int i = 0;
		for ( Card p : unseenPlayers ) {
			if ( i == num ) {
				player = p;
				break;
			}
			else {
				i++;
			}
		}
		
		
		
		suggestion = new Solution(room, player, weapon);
	}
	
	public Solution getSuggestion() {
		return suggestion;
	}

	
	@Override
	public Card disproveSuggestion(Solution suggestion) {
		//TODO
		return new Card();
	}
	
	
	
	public void setLastRoom(char initial) {
		lastRoom = initial;
	}
	
	
	@Override
	public void move(int row, int column) {
		
	}
	
	// TESTING ONLY
	
	public void clearLastRoom() {
		lastRoom = '_';
	}
	
	public void setHand(Set<Card> hand) {
		this.hand = hand;
	}

}
