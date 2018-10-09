package experiment;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 
 * @author Adam Kinard
 * @author Jay Harrison
 * 
 *
 * This class contains the grid and adjacency lists
 * It includes a data structure to contain the board cells
 * 
 */


public class IntBoard {

	private BoardCell[][] grid; 
	private Map<BoardCell, Set<BoardCell>> adjMtx;
	private Set<BoardCell> visited;
	private Set<BoardCell> targets;
	
	// Row/Col constants
	private final static int ROWS = 4;
	private final static int COLUMNS = 4;
	
	public IntBoard() {
		
		// initialize board (grid)
		grid = new BoardCell[ROWS][COLUMNS];
		adjMtx = new HashMap<BoardCell, Set<BoardCell>>();
		
		// initialize cells in grid
		for (int i = 0; i < ROWS; i++) {
			for (int j = 0; j < COLUMNS; j++) {
				grid[i][j] = new BoardCell(i, j);
			}
		}
		
		
		// initialize sets/map
		targets = new HashSet<BoardCell>();
		visited = new HashSet<BoardCell>();
		calcAdjacencies();
	}
	
	public void calcAdjacencies() {
		
		// calculate adjacent squares
		for (int i = 0; i < ROWS; i++) {
			for (int j = 0; j < COLUMNS; j++) {
				
				// valid neighbor if x+1,x-1,y+1,y-1 are not negative or greater than row/col
				// store vals into temp set, put set into adjMtx under grid[r][c] 
				
				Set<BoardCell> temp = new HashSet<BoardCell>();
				
				//test x - 1
				if ( i-1 >= 0 ) {
					temp.add(grid[i-1][j]);
				} 
				//test x + 1
				if ( i+1 <= ROWS-1 ) {
					temp.add(grid[i+1][j]);
				}
				//test y - 1
				if ( j-1 >= 0 ) {
					temp.add(grid[i][j-1]);
				}
				//test y + 1
				if (j+1 <= COLUMNS-1 ) {
					temp.add(grid[i][j+1]);
				}
				
				// add temp set and board cell to map
				adjMtx.put(grid[i][j], temp);
			}
		}
	}
	
	/**
	 * Adjacent cell getter for given cell
	 * @param cell
	 * @return
	 */
	public Set<BoardCell> getAdjList(BoardCell cell) {
		return adjMtx.get(cell);
	}
	
	/**
	 * Target calculating function, calls findAllTargets() inside
	 * @param startCell
	 * @param moves
	 */
	public void calcTargets(BoardCell startCell, int moves) {
		
		// clear visited/target lists
		targets.clear();
		visited.clear();
		
		// add starting cell so no backtracking
		visited.add(startCell);
		
		// call recursive function
		findAllTargets(startCell, moves);

	}
	
	/**
	 * Recursive helper function to calculate valid moves with given start cell
	 * @param startCell
	 * @param moves
	 */
	public void findAllTargets(BoardCell startCell, int moves) {
		
		for ( BoardCell currentCell : getAdjList(startCell) ) {
			if ( !(visited.contains(currentCell)) ) {
				
				// add currentCell to visited
				visited.add(currentCell);
				
				if ( moves == 1 ) {
					targets.add(currentCell);
				}
				else {
					findAllTargets(currentCell, moves-1);
				}
				// remove current cell from visited
				visited.remove(currentCell);
			}
		}
	}
	
	public Set<BoardCell> getTargets() {
		return targets;
	}
	
	/**
	 * Cell getting funtion, returns boardCell at row and column specified
	 * @param r
	 * @param c
	 * @return
	 */
	public BoardCell getCell(int r, int c) {
		//System.out.println("Board cell: " + grid[r][c]);
		return grid[r][c];
	}
	
}