package tests;

/**
 * Program test for testing Selecting a target Location, checking an accusation, disproving a suggestion, handling a suggestion, and creating a suggestion. 
 */

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.BoardCell;
import clueGame.ComputerPlayer;
import clueGame.Player;
import clueGame.Solution;
import clueGame.Card;
import clueGame.CardType;

public class gameActionTests {

	// Constants to check whether data was loaded correctly
	public static final int LEGEND_SIZE = 11;
	public static final int NUM_ROWS = 21;
	public static final int NUM_COLUMNS = 22;
	public static final int NUM_ROOMS = 11;
	public static final int NUM_PEOPLE = 6;
	public static final int NUM_WEAPONS = 6;
	public static final int TOTAL_CARDS = 23;

	// Left board as static due to suggestion of other test class
	private static Board board;
	
	@BeforeClass 
	public static void setUp() {
		// Board is singleton, get the only instance
		board = Board.getInstance();
		// set the file names to use our specific files
		board.setConfigFiles("ClueRooms.csv", "ClueRooms.txt", "CluePlayers.txt", "ClueWeapons.txt");		
		// Initialize will load ALL config files 
		board.initialize();
	}
	
	
	
	/**
	 * Tests selecting a target location for Computer Player
	 */
	
	@Test
	public void selectTargetLocationTest() {
		
		//if no rooms in list, select randomly
		
		
		//if room in list that was not just visited, must select it
		
		
		//if room just visited is in list, each target (including room) selected randomly
		
	}
	
	/**
	 * Checks the validity of an accusation based on Board
	 */
	@Test
	public void makeAccusationTest() {
		
		//solution that is correct
		Solution accusation = board.getSolution();
		assertTrue(board.checkAccusaton(accusation));
		
		//solution with wrong person
		accusation.setPerson(new Card("Adam", CardType.PERSON));
		assertTrue(board.checkAccusaton(accusation));
		
		//solution with wrong weapon
		accusation = board.getSolution();
		accusation.setWeapon(new Card("AK47", CardType.WEAPON));
		assertTrue(board.checkAccusaton(accusation));
		
		//solution with wrong room
		accusation = board.getSolution();
		accusation.setRoom(new Card("Basement", CardType.ROOM));
		assertTrue(board.checkAccusaton(accusation));
		
	}
	
	/**
	 * Assures computer player can properly create a suggestion
	 */
	@Test
	public void createSuggestionTest() {
		ArrayList<ComputerPlayer> players = board.getComputerPlayers();

		//Room matches current location
		for (ComputerPlayer p: players) {
			if (p.getPlayerName().equals("Professor Plum")) {
				p.move(2, 13); //moves to doorway of tool closet
				p.setCurrentRoom(board.getCellAt(2, 13));
				p.createSuggestion();
				Solution suggestion = p.getSuggestion();
				assertTrue(p.getSuggestion().getRoom().getName().equals("Tool Closet"));

				p.setSeen(new HashSet<Card>()); //creates empty hashset to be filled with seen cards
			}

//				//if only one weapon not seen, its selected
//				Set<Card> weapons = new HashSet<Card>();//puts all game weapons into a set
//				weapons.addAll(board.getWeapons());
//				Card weapon1 = null;
//				Card weapon2 = null;
//				int i = weapons.size();
//			
//				
//
//				//if multiple weapons not seen, one of them is randomly selected
//				int seen1 = 0;
//				int seen2 = 0;
//				for (i = 0; i < 10; i++) {
//					p.createSuggestion();
//					suggestion = p.getSuggestion();
//					if(suggestion.getWeapon().equals(weapon1)) seen1++;
//					if(suggestion.getWeapon().equals(weapon2)) seen2++;	
//				}
//				assertTrue(seen1 > 0);
//				assertTrue(seen2 > 0);
//
//				p.revealCard(weapon2);
//				p.createSuggestion();
//				suggestion = p.getSuggestion();
//				assertEquals(suggestion.getWeapon(), weapon1);
//
//			}
//
//
//			//if only one person not seen, its selected (can be same test as weapon)
//			Set<Card> people = new HashSet<Card>();//puts all game weapons into a set
//			people.addAll(board.getWeapons());
//			Card person1 = null;
//			Card person2 = null;
//			int i = people.size();
//			for (Card person : people) {
//				if (i == 1) {
//					person1 = person;
//				}
//				else if (i == 2) {
//					person2 = person;
//				}
//				else {
//					p.revealCard(person);
//				}
//				i--;
//			}
//
//			//if multiple persons not seen, one of them is randomly selected
//			int seen1 = 0;
//			int seen2 = 0;
//			for (i = 0; i < 10; i++) {
//				p.createSuggestion();
//				Solution suggestion1 = p.getSuggestion();
//				if(suggestion1.getWeapon().equals(person1)) seen1++;
//				if(suggestion1.getWeapon().equals(person1)) seen2++;	
//			}
//			assertTrue(seen1 > 0);
//			assertTrue(seen2 > 0);
//
//			p.revealCard(person1);
//			p.createSuggestion();
//			Solution suggestion2 = p.getSuggestion();
//			assertEquals(suggestion2.getWeapon(), person2);
//
//		}
//



	}
	}
	
	
	/**
	 * Disproves a suggestion that a Player makes
	 */
	@Test
	public void disproveSuggestionTest() {
		//if player has only one matching card, it should be returned
		
		//if player has >1 matching cards, returned card should be chosen randomly
		
		//if player has no matching cards, null is returned
	}
	
	/**
	 * Handles a valid/invalid suggestion for Board properly
	 */
	@Test
	public void handleSuggestionTest() {
		
		//Suggestion no one can disprove returns null
		
		//Suggestion only accusing player can disprove returns null
		
		//Suggestion only human can disprove returns answer (card that disproves suggestion)
		
		//Suggestion only human can disproves but humasn is accuser returns null
		
		//Suggestion that two players can disprove, correct player (based on starting with next plpayer list) returns answer
		
		
		//Suggestion that human and another player can disprove, other player is next in list, ensure other player returns answer
		
		
		
	}

}
