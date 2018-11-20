package tests;

/**
 * This program tests the Loading of People, Load/create the deck of cards, and Dealing of the Cards
 */

//Doing a static import allows me to write assertEquals rather than
//Assert.assertEquals
import static org.junit.Assert.*;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import clueGame.Board;
import clueGame.Card;
import clueGame.CardType;
import clueGame.DoorDirection;
import clueGame.Player;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class gameSetupTests {
	// Constants to check whether data was loaded correctly
	public static final int LEGEND_SIZE = 11;
	public static final int NUM_ROWS = 21;
	public static final int NUM_COLUMNS = 22;
	public static final int NUM_ROOMS = 9;
	public static final int NUM_PEOPLE = 6;
	public static final int NUM_WEAPONS = 6;
	public static final int TOTAL_CARDS = 21;

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
	
	@Test
	public void testLoadPeople() {
		//Load all people from config file and check against known characters
		//Config file should be loaded at this point, testing against known characters
		Map<String,Player> players = board.getPlayerMap();
		
		// Miss Scarlet
		Player scarlett = board.getPlayer("red");
		assertTrue(players.containsValue(scarlett));
		assertTrue(scarlett.getPlayerName().equals("Miss Scarlett"));
		assertEquals("red", scarlett.getColor());
		assertEquals(0, scarlett.getRow());
		assertEquals(3, scarlett.getColumn());
		
		// Mr. Plum
		Player plum = board.getPlayer("magenta");
		assertTrue(players.containsValue(plum));
		assertTrue(plum.getPlayerName().equals("Professor Plum"));
		assertEquals("magenta", plum.getColor());
		assertEquals(0, plum.getRow());
		assertEquals(11, plum.getColumn());
		
		// Mrs. Peacock
		Player peacock = board.getPlayer("blue");
		assertTrue(players.containsValue(peacock));
		assertTrue(peacock.getPlayerName().equals("Mrs. Peacock"));
		assertEquals("blue", peacock.getColor());
		assertEquals(20, peacock.getRow());
		assertEquals(13, peacock.getColumn());
		
		// Reverend Green
		Player green = board.getPlayer("green");
		assertTrue(players.containsValue(green));
		assertTrue(green.getPlayerName().equals("Reverend Green"));
		assertEquals("green", green.getColor());
		assertEquals(0, green.getRow());
		assertEquals(18, green.getColumn());
		
		//Colonel Mustard
		Player mustard = board.getPlayer("yellow");
		assertTrue(players.containsValue(mustard));
		assertTrue(mustard.getPlayerName().equals("Colonel Mustard"));
		assertEquals("yellow", mustard.getColor());
		assertEquals(20, mustard.getRow());
		assertEquals(17, mustard.getColumn());
		
		// Mrs. White
		Player white = board.getPlayer("white");
		assertTrue(players.containsValue(white));
		assertTrue(white.getPlayerName().equals("Mrs. White"));
		assertEquals("white", white.getColor());
		assertEquals(20, white.getRow());
		assertEquals(5, white.getColumn());
		
		// Size
		assertEquals(NUM_PEOPLE, players.size());
	}
	
	@Test
	public void testCreateDeck() {
		//Take all the cards and place into one hashset
		
		boolean containsRoom = false;
		boolean containsPerson = false;
		boolean containsWeapon = false;
		
		int numRooms = 0, numPeople = 0, numWeapons = 0;
		
		for ( Card card : board.getAllCards() ) {
			if ( card.getCardType() == CardType.ROOM ) {
				numRooms++;
				if ( card.getName().equals("Ballroom") ) {
					containsRoom = true;
				}
			}
			else if ( card.getCardType() == CardType.PERSON ) {
				numPeople++;
				if ( card.getName().equals("Colonel Mustard") ) {
					containsPerson = true;
				}
			}
			else if ( card.getCardType() == CardType.WEAPON ) {
				numWeapons++;
				if (card.getName().equals("Candlestick") ) {
					containsWeapon = true;
				}
			}
		}
		
		// Test deck sizes
		assertEquals(NUM_ROOMS, numRooms);
		assertEquals(NUM_PEOPLE, numPeople);
		assertEquals(NUM_WEAPONS, numWeapons);
		assertEquals(TOTAL_CARDS, board.getAllCards().size());
		
		// Test for containment
		assertTrue(containsRoom);
		assertTrue(containsPerson);
		assertTrue(containsWeapon);
	}
	
	@Test
	public void testDealCards() {
		ArrayList deck = board.getCardDeck();

		// Test deck is empty
		assertEquals(0, deck.size());
		
		// Test player hands
		boolean notEnoughCards = false;
		
		Set<String> playerKeys = board.getPlayerMap().keySet();
		for (String key : playerKeys) {
			Player player = board.getPlayerMap().get(key);
			if ( ( (TOTAL_CARDS - 3) / NUM_PEOPLE) > player.getHand().size() ) {
				notEnoughCards = true;
			}
		}
		
		assertFalse(notEnoughCards);
	}
}
	

	
