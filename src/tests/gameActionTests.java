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
		// Make computerPlayer
		ComputerPlayer player = new ComputerPlayer();
		
		// Test with 2 moves, no rooms
		board.calcTargets(8, 7, 2);
		boolean loc_06_07 = false;
		boolean loc_10_07 = false;
		boolean loc_09_06 = false;
		boolean loc_07_05 = false;
		boolean loc_08_05 = false;
	
		for (int i=0; i<100; i++) {
			BoardCell selected = player.pickLocation(board.getTargets());
			if (selected == board.getCellAt(6, 7))
				loc_06_07 = true;
			else if (selected == board.getCellAt(10, 7))
				loc_10_07 = true;
			else if (selected == board.getCellAt(9, 6))
				loc_09_06 = true;
			else if (selected == board.getCellAt(7, 6))
				loc_07_05 = true;
			else if (selected == board.getCellAt(8, 5))
				loc_08_05 = true;
			else
				fail("Invalid target selected");
		}
		// Ensure targets were picked
		assertTrue(loc_06_07);
		assertTrue(loc_10_07);
		assertTrue(loc_09_06);
		assertTrue(loc_07_05);
		assertTrue(loc_08_05);
		
		
		// Test with 4 moves, room every time
		board.calcTargets(20, 5, 4);
		boolean loc_19_05 = false;
		boolean loc_18_05 = false;
		boolean loc_17_05 = false;
		boolean loc_16_05 = false;
		boolean door_1 = false;
		boolean door_2 = false;
		
		for (int i=0; i<100; i++) {
			BoardCell selected = player.pickLocation(board.getTargets());
			if (selected == board.getCellAt(19, 5))
				loc_19_05 = true;
			if (selected == board.getCellAt(18, 5))
				loc_18_05 = true;
			if (selected == board.getCellAt(17, 5))
				loc_17_05 = true;
			if (selected == board.getCellAt(16, 5))
				loc_16_05 = true;
			if (selected == board.getCellAt(17, 4))
				door_1 = true;
				player.clearLastRoom();
			if (selected == board.getCellAt(18, 4))
				door_2 = true;
				player.clearLastRoom();
		}
		
		// Ensure doors were picked
		assertTrue(door_1 || door_2);
		assertFalse(loc_19_05);
		assertFalse(loc_18_05);
		assertFalse(loc_17_05);
		assertFalse(loc_16_05);
		
		
		// Test last visited room, 1 move
		board.calcTargets(14, 1, 1);
		player.setLastRoom('B');
		
		boolean door = false;
		boolean loc_14_00 = false;
		boolean loc_14_02 = false;
		boolean loc_15_01 = false;
		
		for (int i=0; i<100; i++) {
			BoardCell selected = player.pickLocation(board.getTargets());
			if (selected == board.getCellAt(13, 1))
				door = true;
			if (selected == board.getCellAt(14, 0))
				loc_14_00 = true;
			if (selected == board.getCellAt(14, 2))
				loc_14_02 = true;
			if (selected == board.getCellAt(15, 1))
				loc_15_01 = true;
		}
		
		// Ensure all targets chosen
		assertTrue(door);
		assertTrue(loc_14_00);
		assertTrue(loc_14_02);
		assertTrue(loc_15_01);
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
		// Create player in Ballroom doorway
		//Assure room matches current location
		ComputerPlayer player = new ComputerPlayer("Miss Scarlett", "red", 13, 1);
		player.createSuggestion();
		Solution suggestion = player.getSuggestion();

		assertTrue(suggestion.getRoom().getName().equals("Ballroom"));
				
		Set<Card> seenList = player.getSeen();

		//if only one weapon not seen, its selected
		Set<Card> weapons = new HashSet<Card>();
		weapons.addAll(board.getWeapons());//puts all game weapons into a set

		for (Card w : weapons){
			seenList.add(w);
			if(w.getName().equals("Wrench")) {
				seenList.remove(w);
			}
		}
		player.createSuggestion();
		suggestion = player.getSuggestion();

		assertTrue(suggestion.getWeapon().getName().equals("Wrench"));

		//if only one person not seen, its selected
		Set<Card> people = new HashSet<Card>();
		people.addAll(board.getPeople());

		for (Card p : people) {
			seenList.add(p);
			if(p.getName().equals("Colonel Mustard")) {
				seenList.remove(p);
			}
		}
		player.createSuggestion();
		suggestion = player.getSuggestion();
		assertTrue(suggestion.getPerson().getName().equals("Colonel Mustard"));

		//if multiple weapons not seen, one of them is randomly selected
		seenList.removeAll(weapons);

		Set<Card> weaponsGuessed = new HashSet<Card>();

		// generates set without dupes of all weapons
		for (int i=0; i<100; i++) {
			suggestion = player.getSuggestion();
			weaponsGuessed.add(suggestion.getWeapon());
		}

		assertTrue(weaponsGuessed.equals(weapons));



		//if multiple persons not seen, one of them is randomly selected
		seenList.removeAll(people);
		seenList.add(board.getAllCards().get(2)); //Miss Scarlett
		seenList.add(board.getAllCards().get(0)); //Professor Plum


		Set<Card> peopleGuessed = new HashSet<Card>();

		for (int i=0; i<100; i++) {
			Solution suggestion2 = player.getSuggestion();
			peopleGuessed.add(suggestion2.getPerson());
		}
		assertFalse(peopleGuessed.contains(board.getAllCards().get(0)));
		assertTrue(peopleGuessed.contains(board.getAllCards().get(1)));
		assertFalse(peopleGuessed.contains(board.getAllCards().get(2)));
		assertTrue(peopleGuessed.contains(board.getAllCards().get(3)));
		assertTrue(peopleGuessed.contains(board.getAllCards().get(4)));
		assertTrue(peopleGuessed.contains(board.getAllCards().get(5)));
	}


	/**
	 * Disproves a suggestion that a Player makes
	 */
	@Test
	public void disproveSuggestionTest() {
		// Create player and give them a deck with known cards
		ComputerPlayer player = new ComputerPlayer();
		Set<Card> hand = new HashSet<Card>();
		
		//Professor Plum
		Card person = board.getAllCards().get(0);
		hand.add(person);
		//Dining Room
		Card room = board.getAllCards().get(6);
		hand.add(room);
		//Wrench
		Card weapon = board.getAllCards().get(20);
		hand.add(weapon);
		
		player.setHand(hand);
		
		// Make new suggestion
		Solution suggestion = new Solution(person, room, weapon);
		
		// Test to see if player disproves all randomly
		boolean disprovedPerson = false;
		boolean disprovedRoom = false;
		boolean disprovedWeapon = false;
		
		for (int i=0; i<100; i++) {
			// Call disprove on player
			Card card = player.disproveSuggestion(suggestion);
			
			if ( card.equals(person) )
				disprovedPerson = true;
			else if ( card.equals(room) )
				disprovedRoom = true;
			else if ( card.equals(weapon) )
				disprovedWeapon = true;
		}
		
		// Ensure disprove suggestion chooses all cards at least once
		assertTrue(disprovedPerson);
		assertTrue(disprovedRoom);
		assertTrue(disprovedWeapon);
		
		
		//if player has no matching cards, null is returned
		
		//Clear hand, change cards to not match
		hand.clear();
		//White
		Card person1 = board.getAllCards().get(1);
		hand.add(person);
		//Ballroom
		Card room1 = board.getAllCards().get(7);
		hand.add(room);
		//Revolver
		Card weapon1 = board.getAllCards().get(19);
		hand.add(weapon);

		player.setHand(hand);
		
		//Should return null now if suggestion remains the same as before
		Card card = player.disproveSuggestion(suggestion);
		assertEquals(null, card);
		
	}
	
	/**
	 * Handles a valid/invalid suggestion for Board properly
	 */
	@Test
	public void handleSuggestionTest() {
		
		//solution that is correct
		
		
		//Suggestion no one can disprove returns null
		
		//Suggestion only accusing player can disprove returns null
		
		//Suggestion only human can disprove returns answer (card that disproves suggestion)
		
		//Suggestion only human can disproves but humasn is accuser returns null
		
		//Suggestion that two players can disprove, correct player (based on starting with next plpayer list) returns answer
		
		
		//Suggestion that human and another player can disprove, other player is next in list, ensure other player returns answer
		
		
		
	}

}
