package tests;

import static org.junit.Assert.*;

import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import experiment.BoardCell;
import experiment.IntBoard;

public class IntBoardTests {

	IntBoard board;
	
	@Before
    public void beforeAll() {
       board = new IntBoard();  // constructor should call calcAdjacencies() so you can test them
    }
	
	/*
	 * Test adjacencies for top left corner
	 * 
	 */
	
	@Test
	public void testAdjacency0() {
		BoardCell cell = board.getCell(0,0);
		Set<BoardCell> testList = board.getAdjList(cell);
		assertTrue(testList.contains(board.getCell(1,0)));
		assertTrue(testList.contains(board.getCell(0,1)));
		assertEquals(2, testList.size());
	}
	
	/*
	 * Test adjacencies for bottom right corner
	 */
	
	@Test
	public void testAdjacency1() {
		BoardCell cell = board.getCell(3, 3);
		Set<BoardCell> testList = board.getAdjList(cell);
		assertTrue(testList.contains(board.getCell(2, 3)));
		assertTrue(testList.contains(board.getCell(3, 2)));
		assertEquals(2, testList.size());
	}
	
	/*
	 * Test adjacencies for right edge
	 */
	
	@Test
	public void testAdjacency2() {
		BoardCell cell = board.getCell(0, 3);
		Set<BoardCell> testList = board.getAdjList(cell);
		assertTrue(testList.contains(board.getCell(0, 2)));
		assertTrue(testList.contains(board.getCell(1, 3)));
		assertEquals(2, testList.size());
	}
	/*
	 * Test adjacencies for left edge
	 */
	
	@Test
	public void testAdjacency3() {
		BoardCell cell = board.getCell(3, 0);
		Set<BoardCell> testList = board.getAdjList(cell);
		assertTrue(testList.contains(board.getCell(2, 0)));
		assertTrue(testList.contains(board.getCell(3, 1)));
		assertEquals(2, testList.size());
	}
	
	/*
	 * Test adjacencies for second column middle of grid
	 */
	
	@Test
	public void testAdjacency4() {
		BoardCell cell = board.getCell(1, 1);
		Set<BoardCell> testList = board.getAdjList(cell);
		assertTrue(testList.contains(board.getCell(1, 0)));
		assertTrue(testList.contains(board.getCell(1, 2)));
		assertTrue(testList.contains(board.getCell(2, 1)));
		assertTrue(testList.contains(board.getCell(0, 1)));
		assertEquals(4, testList.size());
	}
	
	/*
	 * Test adjacencies for second from last column, middle of grid
	 */
	
	@Test
	public void testAdjacency5() {
		BoardCell cell = board.getCell(1, 1);
		Set<BoardCell> testList = board.getAdjList(cell);
		assertTrue(testList.contains(board.getCell(0, 1)));
		assertTrue(testList.contains(board.getCell(2, 1)));
		assertTrue(testList.contains(board.getCell(1, 0)));
		assertTrue(testList.contains(board.getCell(1, 2)));
		assertEquals(4, testList.size());
	}
	
	/*
	 * target test from [0][0] for 3 steps
	 */
	
	@Test
	public void testTargets0_3()
	{
		BoardCell cell = board.getCell(0, 0);
		board.calcTargets(cell, 3);
		Set targets = board.getTargets();
		assertEquals(6, targets.size());
		assertTrue(targets.contains(board.getCell(3, 0)));
		assertTrue(targets.contains(board.getCell(2, 1)));
		assertTrue(targets.contains(board.getCell(0, 1)));
		assertTrue(targets.contains(board.getCell(1, 2)));
		assertTrue(targets.contains(board.getCell(0, 3)));
		assertTrue(targets.contains(board.getCell(1, 0)));
	}
	
	/*
	 * target test from [0][0] for 2 steps
	 */
	
	@Test
	public void testTargets00_2()
	{
		BoardCell cell = board.getCell(0, 0);
		board.calcTargets(cell, 2);
		Set targets = board.getTargets();
		assertEquals(6, targets.size());
		assertTrue(targets.contains(board.getCell(2, 0)));
		assertTrue(targets.contains(board.getCell(1, 1)));
		assertTrue(targets.contains(board.getCell(0, 2)));
	}
	
	/*
	 * target test from [0][0] for 1 step
	 */
	@Test
	public void testTargets00_1()
	{
		BoardCell cell = board.getCell(0, 0);
		board.calcTargets(cell, 1);
		Set targets = board.getTargets();
		assertEquals(6, targets.size());
		assertTrue(targets.contains(board.getCell(1, 0)));
		assertTrue(targets.contains(board.getCell(0, 1)));
	}
	
	/*
	 * target test from [2][2] for 3 steps
	 */
	
	@Test
	public void testTargets22_3()
	{
		BoardCell cell = board.getCell(2, 2);
		board.calcTargets(cell, 3);
		Set targets = board.getTargets();
		assertEquals(6, targets.size());
		assertTrue(targets.contains(board.getCell(2, 3)));
		assertTrue(targets.contains(board.getCell(3, 0)));
		assertTrue(targets.contains(board.getCell(2, 1)));
		assertTrue(targets.contains(board.getCell(1, 2)));
		assertTrue(targets.contains(board.getCell(0, 1)));
		assertTrue(targets.contains(board.getCell(1, 0)));
		assertTrue(targets.contains(board.getCell(0, 3)));
	}
	
	/*
	 * target test from [3][3] for 2 steps
	 */
	
	@Test
	public void testTargets33_2()
	{
		BoardCell cell = board.getCell(3, 3);
		board.calcTargets(cell, 2);
		Set targets = board.getTargets();
		assertEquals(6, targets.size());
		assertTrue(targets.contains(board.getCell(3, 1)));
		assertTrue(targets.contains(board.getCell(2, 2)));
		assertTrue(targets.contains(board.getCell(1, 3)));
	}
	
	/*
	 * target test from [3][0] for 4 steps
	 */
	
	@Test
	public void testTargets30_4()
	{
		BoardCell cell = board.getCell(3, 0);
		board.calcTargets(cell, 4);
		Set targets = board.getTargets();
		assertEquals(6, targets.size());
		assertTrue(targets.contains(board.getCell(1, 0)));
		assertTrue(targets.contains(board.getCell(0, 1)));
		assertTrue(targets.contains(board.getCell(2, 1)));
		assertTrue(targets.contains(board.getCell(1, 2)));
		assertTrue(targets.contains(board.getCell(3, 2)));
		assertTrue(targets.contains(board.getCell(2, 3)));
	}
	

}