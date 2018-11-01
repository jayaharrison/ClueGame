package tests;

/**
 * This program tests the Loading of People, Load/create the deck of cards, and Dealing of the Cards
 */

//Doing a static import allows me to write assertEquals rather than
//Assert.assertEquals
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.Card;
import clueGame.CardType;
import clueGame.DoorDirection;
import clueGame.Player;

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
		Player scarlett = board.getPlayer("Scarlett");
		assertTrue(players.containsKey(scarlett));
		assertEquals("Miss Scarlet", scarlett.getPlayerName());
		assertEquals("Red", scarlett.getColor());
		assertEquals(0, scarlett.getRow());
		assertEquals(0, scarlett.getColumn());
		
		// Mr. Plum
		Player plum = board.getPlayer("Plum");
		assertTrue(players.containsKey(plum));
		assertEquals("Mr. Plum", plum.getPlayerName());
		assertEquals("Purple", plum.getColor());
		assertEquals(0, plum.getRow());
		assertEquals(0, plum.getColumn());
		
		// Mrs. Peacock
		Player peacock = board.getPlayer("Peacock");
		assertTrue(players.containsKey(peacock));
		assertEquals("Mrs. Peacock", peacock.getPlayerName());
		assertEquals("Blue", peacock.getColor());
		assertEquals(0, peacock.getRow());
		assertEquals(0, peacock.getColumn());
		
		// Reverend Green
		Player green = board.getPlayer("Green");
		assertTrue(players.containsKey(green));
		assertEquals("Mr. Green", green.getPlayerName());
		assertEquals("Green", green.getColor());
		assertEquals(0, green.getRow());
		assertEquals(0, green.getColumn());
		
		//Colonel Mustard
		Player mustard = board.getPlayer("Mustard");
		assertTrue(players.containsKey(mustard));
		assertEquals("Colonel Mustard", mustard.getPlayerName());
		assertEquals("Yellow", mustard.getColor());
		assertEquals(0, mustard.getRow());
		assertEquals(0, mustard.getColumn());
		
		// Mrs. White
		Player white = board.getPlayer("White");
		assertTrue(players.containsKey(white));
		assertEquals("Mrs. White", white.getPlayerName());
		assertEquals("White", white.getColor());
		assertEquals(0, white.getRow());
		assertEquals(0, white.getColumn());
		
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
		
		for ( Card card : board.getCardDeck() ) {
			if ( card.getCardType() == CardType.ROOM ) {
				numRooms++;
				if ( card.getName().equals("Ballroom") ) {
					containsRoom = true;
				}
			}
			else if ( card.getCardType() == CardType.PERSON ) {
				numPeople++;
				if ( card.getName().equals("Mrs. White") ) {
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
		assertEquals(TOTAL_CARDS, board.getCardDeck().size());
		
		// Test for containment
		assertTrue(containsRoom);
		assertTrue(containsPerson);
		assertTrue(containsWeapon);
	}
	
	@Test
	public void testDealCards() {
		ArrayList deck = board.getCardDeck();
		
		// Test deck is empty
		assertEquals(-1, deck.size());
		
		// Test player hands
		boolean notEnoughCards = false;
		
		Set<String> playerKeys = board.getPlayerMap().keySet();
		for (String key : playerKeys) {
			Player player = board.getPlayerMap().get(key);
			if ( (TOTAL_CARDS - 3) / NUM_PEOPLE <= player.getPlayerCards().size() ) {
				notEnoughCards = true;
			}
		}
		
		assertFalse(notEnoughCards);
	}
}
	

	
