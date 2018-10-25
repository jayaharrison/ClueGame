package tests;

/**
 * This program tests the Loading of People, Load/create the deck of cards, and Dealing of the Cards
 */

//Doing a static import allows me to write assertEquals rather than
//Assert.assertEquals
import static org.junit.Assert.*;

import java.util.Map;
import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;

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
		board.setConfigFiles("ClueRooms.csv", "ClueRooms.txt");		
		// Initialize will load BOTH config files 
		board.initialize();
	}
	
	@Test
	public void testLoadPeople() {
	
	}
	
	@Test
	public void testCreateDeck() {
		
	}
	
	@Test
	public void testDealCards() {
		
	}
}
	

	
