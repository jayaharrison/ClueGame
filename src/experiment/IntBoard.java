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

	private BoardCell[][] grid; //???
	//Hashset-- data structure to conatin the board cells
	private Map<BoardCell, Set<BoardCell>> adjMtx;
	private Set<BoardCell> visited;
	private Set<BoardCell> targets;
	
	public IntBoard() {
		
		// initialize board
		grid = new BoardCell[4][4];
		adjMtx = new HashMap<BoardCell, Set<BoardCell>>();
		
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				grid[i][j] = new BoardCell(i, j);
				System.out.println(grid[i][j]);
				adjMtx.put(grid[i][j], new HashSet<BoardCell>());
			}
		}
		
		
		// initialize sets/map
		targets = new HashSet<BoardCell>();
		visited = new HashSet<BoardCell>();
		calcAdjacencies();
	}
	
	public void calcAdjacencies() {
		// calculate adjacent squares
		// algorithm here
		// store result in adjMtx.at(grid[r][c]).put(BoardCell)
		
		
		
	}
	
	public Set<BoardCell> getAdjList(BoardCell cell) {
		//Returns the adjacency list for one cell
		// will use visited in coordination
		
		return adjMtx.get(cell);
		
	}
	
	public void calcTargets(BoardCell startCell, int pathLength) {
		// access adjmatrix, calculate paths
		// store results into target
		
	}
	
	public Set<BoardCell> getTargets() {
		// returns the list of targets as a hashset?
		return targets;
		
	}
	
	public BoardCell getCell(int r, int c) {
		//System.out.println("Board cell: " + grid[r][c]);
		return grid[r][c];
	}
	
}