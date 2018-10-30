/**
 * @author Jay Harrison
 * @author Adam Kinard
 * 
 * Test class for adjacency and target testing
 */
package tests;

import static org.junit.Assert.*;

import java.util.Set;

import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.BoardCell;

public class CustomAdjacencyTargetTests {
	// We make the Board static because we can load it one time and 
	// then do all the tests. 
	private static Board board;
	@BeforeClass
	public static void setUp() {
		// Board is singleton, get the only instance
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("ClueRooms.csv", "ClueRooms.txt","CluePlayers.txt");		
		// Initialize will load BOTH config files 
		board.initialize();
	}

	// Ensure that player does not move around within room
	// These cells are ORANGE on the planning spreadsheet
	@Test
	public void testAdjacenciesInsideRooms()
	{
		// 
		Set<BoardCell> testList = board.getAdjList(0, 15);
		assertEquals(0, testList.size());
		// 
		testList = board.getAdjList(5, 19);
		assertEquals(0, testList.size());
		// 
		testList = board.getAdjList(17, 15);
		assertEquals(0, testList.size());
		// 
		testList = board.getAdjList(5, 0);
		assertEquals(0, testList.size());
		// 
		testList = board.getAdjList(2, 10);
		assertEquals(0, testList.size());
		// 
		testList = board.getAdjList(19, 2);
		assertEquals(0, testList.size());
	}

	// Ensure that the adjacency list from a doorway is only the
	// walkway. Merged with door tests
	// These tests are PURPLE on the planning spreadsheet
	@Test
	public void testAdjacencyRoomExit()
	{
		// TEST DOORWAY RIGHT: (18,11) 
		Set<BoardCell> testList = board.getAdjList(18, 11);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCellAt(18, 12)));
		// TEST DOORWAY LEFT : (1,5)
		testList = board.getAdjList(1, 5);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCellAt(1, 4)));
		//TEST DOORWAY DOWN: (6,19)
		testList = board.getAdjList(6, 19);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCellAt(7, 19)));
		//TEST DOORWAY UP: (14,9)
		testList = board.getAdjList(14, 9);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCellAt(13, 9)));
		//TEST DOORWAY RIGHT, WHERE THERE'S A WALKWAY BELOW: (6,2)
		testList = board.getAdjList(5, 2);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCellAt(5, 3)));
		
	}
	
	// Test adjacency at entrance to rooms
	// These tests are LIGHT BLUE in planning spreadsheet
	@Test
	public void testAdjacencyDoorways()
	{
		// Test beside a door direction RIGHT: (18,12)
		Set<BoardCell> testList = board.getAdjList(18, 12);
		assertTrue(testList.contains(board.getCellAt(19, 12)));
		assertTrue(testList.contains(board.getCellAt(17, 12)));
		assertTrue(testList.contains(board.getCellAt(18, 13)));
		assertTrue(testList.contains(board.getCellAt(18, 11)));
		assertEquals(4, testList.size());
		
		// Test beside a door direction DOWN: (7,19)
		testList = board.getAdjList(7, 19);
		assertTrue(testList.contains(board.getCellAt(7, 20)));
		assertTrue(testList.contains(board.getCellAt(7, 18)));
		assertTrue(testList.contains(board.getCellAt(8, 19)));
		assertTrue(testList.contains(board.getCellAt(6, 19)));
		assertEquals(4, testList.size());
		
		// Test beside a door direction LEFT: (1,4)
		testList = board.getAdjList(1, 4);
		assertTrue(testList.contains(board.getCellAt(0, 4)));
		assertTrue(testList.contains(board.getCellAt(2, 4)));
		assertTrue(testList.contains(board.getCellAt(1, 3)));
		assertTrue(testList.contains(board.getCellAt(1, 5)));
		assertEquals(4, testList.size());
		
		// Test beside a door direction UP: (13,9)
		testList = board.getAdjList(13, 9);
		assertTrue(testList.contains(board.getCellAt(12, 9)));
		assertTrue(testList.contains(board.getCellAt(14, 9)));
		assertTrue(testList.contains(board.getCellAt(13, 8)));
		assertTrue(testList.contains(board.getCellAt(13, 10)));
		assertEquals(4, testList.size());
	}

	// Test a variety of walkway scenarios
	// These tests are RED on the planning spreadsheet
	@Test
	public void testAdjacencyWalkways()
	{
		// Top edge of board, 2 walkways, (0,3)
		Set<BoardCell> testList = board.getAdjList(0, 3);
		assertTrue(testList.contains(board.getCellAt(0, 4)));
		assertTrue(testList.contains(board.getCellAt(1, 3)));
		assertEquals(2, testList.size());
		
		// Next to a door, 2 walkways, (0,17)
		testList = board.getAdjList(0, 17);
		assertTrue(testList.contains(board.getCellAt(1, 17)));
		assertTrue(testList.contains(board.getCellAt(0, 18)));
		assertEquals(2, testList.size());

		// Right edge of board, 3 walkways, (8,21)
		testList = board.getAdjList(8, 21);
		assertTrue(testList.contains(board.getCellAt(7, 21)));
		assertTrue(testList.contains(board.getCellAt(9, 21)));
		assertTrue(testList.contains(board.getCellAt(8, 20)));
		assertEquals(3, testList.size());

		// Bottom edge, 1 walkway, (20,5)
		testList = board.getAdjList(20,5);
		assertTrue(testList.contains(board.getCellAt(19, 5)));
		assertEquals(1, testList.size());
		
		// Next to a room, diagonal from doorway, 3 walkways, (12,5)
		testList = board.getAdjList(12, 5);
		assertTrue(testList.contains(board.getCellAt(11, 5)));
		assertTrue(testList.contains(board.getCellAt(13, 5)));
		assertTrue(testList.contains(board.getCellAt(12, 6)));
		assertEquals(3, testList.size());
		
		// Left edge, 2 walkways, (7,0)
		testList = board.getAdjList(7, 0);
		assertTrue(testList.contains(board.getCellAt(6, 0)));
		assertTrue(testList.contains(board.getCellAt(7, 1)));
		assertEquals(2, testList.size());
	}
	
	
	// Tests of just walkways, 1 step, includes on edge of board
	// and beside room
	// Have already tested adjacency lists on all four edges, will
	// only test two edges here
	// These are DARK BLUE on the planning spreadsheet
	@Test
	public void testTargetsOneStep() {
		// (15, 0)
		board.calcTargets(15, 0, 1);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(2, targets.size());
		assertTrue(targets.contains(board.getCellAt(14, 0)));
		assertTrue(targets.contains(board.getCellAt(15, 1)));	
		
		board.calcTargets(20, 17, 1);
		targets= board.getTargets();
		assertEquals(1, targets.size());
		assertTrue(targets.contains(board.getCellAt(19, 17)));		
	}
	
	// Tests of just walkways, 2 steps
	// These are DARK BLUE on the planning spreadsheet
	@Test
	public void testTargetsTwoSteps() {
		board.calcTargets(15, 0, 2);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(2, targets.size());
		assertTrue(targets.contains(board.getCellAt(14, 1)));
		assertTrue(targets.contains(board.getCellAt(15, 2)));
		
		// Can go into room, not using all steps
		board.calcTargets(20, 17, 2);
		targets= board.getTargets();
		assertEquals(2, targets.size());
		assertTrue(targets.contains(board.getCellAt(18, 17)));
		assertTrue(targets.contains(board.getCellAt(19, 18)));	// enter room		
	}
	
	// Tests of just walkways, 4 steps
	// These are DARK BLUE on the planning spreadsheet
	@Test
	public void testTargetsFourSteps() {
		board.calcTargets(15, 0, 4);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(5, targets.size());
		assertTrue(targets.contains(board.getCellAt(15, 4)));
		assertTrue(targets.contains(board.getCellAt(14, 3)));
		assertTrue(targets.contains(board.getCellAt(15, 2)));
		assertTrue(targets.contains(board.getCellAt(14, 1)));
		assertTrue(targets.contains(board.getCellAt(13, 1)));	// enter room
		
		board.calcTargets(20, 17, 4);
		targets= board.getTargets();
		assertEquals(2, targets.size());
		assertTrue(targets.contains(board.getCellAt(16, 17)));
		assertTrue(targets.contains(board.getCellAt(19, 18)));	// enter room
	}	
	
	// Tests of just walkways plus one door, 6 steps
	// These are DARK BLUE on the planning spreadsheet

	@Test
	public void testTargetsSixSteps() {
		board.calcTargets(20, 17, 6);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(2, targets.size());
		assertTrue(targets.contains(board.getCellAt(19, 18)));	// enter doorway
		assertTrue(targets.contains(board.getCellAt(14, 17)));	
	}	

	// Test getting out of a room
	// These are DARK BLUE on the planning spreadsheet
	@Test
	public void testRoomExit()
	{
		// Take one step, essentially just the adj list
		board.calcTargets(2, 13, 1);
		Set<BoardCell> targets= board.getTargets();
		// Ensure doesn't exit through the wall
		assertEquals(1, targets.size());
		assertTrue(targets.contains(board.getCellAt(3, 13)));
		// Take two steps
		board.calcTargets(2, 13, 2);
		targets= board.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCellAt(3, 12)));
		assertTrue(targets.contains(board.getCellAt(4, 13)));
		assertTrue(targets.contains(board.getCellAt(3, 14)));
	}

}
