package tests;

/*
 * This program tests that config files are loaded properly.
 */

// Doing a static import allows me to write assertEquals rather than
// Assert.assertEquals
import static org.junit.Assert.*;

import java.util.Map;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.BoardCell;
import clueGame.DoorDirection;

public class CustomBoardTests {
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
		// Initialize will load BOTH config files 
		board.initialize();
	}
	@Test
	public void testRooms() {
		// Get the map of initial => room 
		Map<Character, String> legend = board.getLegend();
		// Ensure we read the correct number of rooms
		assertEquals(LEGEND_SIZE, legend.size());
		// To ensure data is correctly loaded, test retrieving a few rooms 
		// from the hash, including the first and last in the file and a few others
		assertEquals("Kitchen", legend.get('K'));
		assertEquals("Planetarium", legend.get('P'));
		assertEquals("Reptile Room", legend.get('R'));
		assertEquals("Hydroponics Lab", legend.get('H'));
		assertEquals("Walkway", legend.get('W'));
	}
	
	@Test
	public void testBoardDimensions() {
		// Ensure we have the proper number of rows and columns
		assertEquals(NUM_ROWS, board.getNumRows());
		assertEquals(NUM_COLUMNS, board.getNumColumns());		
	}
	
	// Test a doorway in each direction (RIGHT/LEFT/UP/DOWN), plus 
	// two cells that are not a doorway.
	// These cells are white on the planning spreadsheet
	// Shown as WHITE on board
	@Test
	public void FourDoorDirections() {
		BoardCell room = board.getCellAt(17, 4);
		// Kitchen- Right doorway
		assertTrue(room.isDoorway());
		assertEquals(DoorDirection.RIGHT, room.getDoorDirection());
		// Crematorium- Down doorway
		room = board.getCellAt(3, 8);
		assertTrue(room.isDoorway());
		assertEquals(DoorDirection.DOWN, room.getDoorDirection());
		// Reptile- Left doorway
		room = board.getCellAt(19, 18);
		assertTrue(room.isDoorway());
		assertEquals(DoorDirection.LEFT, room.getDoorDirection());
		// Reptile- Up doorway
		room = board.getCellAt(11, 18);
		assertTrue(room.isDoorway());
		assertEquals(DoorDirection.UP, room.getDoorDirection());
		// Toolshed- No doorways 
		room = board.getCellAt(1, 16);
		assertFalse(room.isDoorway());
		assertEquals(DoorDirection.NONE, room.getDoorDirection());
		
		// Test that room pieces that aren't doors know it, ie Closet cell
		room = board.getCellAt(9, 13);
		assertFalse(room.isDoorway());	
		assertEquals(DoorDirection.NONE, room.getDoorDirection());
		
		// Test that walkways are not doors
		BoardCell cell = board.getCellAt(8, 5);
		assertFalse(cell.isDoorway());
		BoardCell cell2 = board.getCellAt(13, 17);
		assertFalse(cell2.isDoorway());

	}
	
	// Test that we have the correct number of doors, will adjust later with doorways set
	@Test
	public void testNumberOfDoorways() 
	{
		int numDoors = 0;
		for (int row=0; row<board.getNumRows(); row++)
			for (int col=0; col<board.getNumColumns(); col++) {
				BoardCell cell = board.getCellAt(row, col);
				if (cell.isDoorway())
					numDoors++;
			}
		Assert.assertEquals(15, numDoors);
	}

	// Test a few room cells to ensure the room initial is correct.
	@Test
	public void testRoomInitials() {
		// Test extrema cells, including 4 corners
		assertEquals('C', board.getCellAt(0, 0).getInitial());
		assertEquals('R', board.getCellAt(20, 21).getInitial());
		assertEquals('D', board.getCellAt(20, 9).getInitial());
		assertEquals('H', board.getCellAt(0, 21).getInitial());
		assertEquals('K', board.getCellAt(20, 0).getInitial());
		// Test a walkway
		assertEquals('W', board.getCellAt(20, 17).getInitial());
		// Test the closet
		assertEquals('X', board.getCellAt(7,9).getInitial());
	}
	

}