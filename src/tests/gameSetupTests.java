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

	// Left board as static due to suggestion of other test class
	private static Board board;
	
	@BeforeClass
	public static void setUp() {
		// Board is singleton, get the only instance
		board = Board.getInstance();
		// set the file names to use our specific files
		board.setConfigFiles("ClueRooms.csv", "ClueRooms.txt", "CluePlayers.txt");		
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
		assertEquals(6, players.size());
	}
	
	@Test
	public void testCreateDeck() {
		//Take all the cards and place into one hashset
		// Check 2 rooms, 2 players, 2 weapons
		ArrayList<Card> deck = board.getCardDeck();
		
		Set<Card> rooms = new TreeSet<Card>();
		Set<Card> players = new TreeSet<Card>();
		Set<Card> weapons = new TreeSet<Card>();
		
		// set up sets
		for (Card card : deck) {
			CardType type = card.getCardType();
			switch (type) {
			case ROOM:
				rooms.add(card);
				break;
			case PERSON:
				players.add(card);
				break;
			case WEAPON:
				weapons.add(card);
				break;
			default:
				break;
			}
		}
		
		// Sizes
		assertEquals(9, rooms.size());
		assertEquals(6, players.size());
		assertEquals(6, weapons.size());
		
		// Test cases
		assertTrue(deck.contains(board.getPlayer("White")));
		assertTrue(deck.contains(board.getLegend().containsKey('C')));
		assertEquals("Candlestick", deck.contains(board.getWeapons().get(0)));
	}
	
	@Test
	public void testDealCards() {
		//randomly pick one of each card (player, weapon, room) to be placed as the solution
		//randomly sort cards into 7 piles, one for each player and the rest of the cards into the deck
	}
}
	

	
