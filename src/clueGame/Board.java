package clueGame;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

//import clueGame.BoardCell;

public final class Board {

	public static final int MAX_BOARD_SIZE = 50;
	
	private int numRows;
	private int numColumns;
	
	private BoardCell[][] board = new BoardCell[MAX_BOARD_SIZE][MAX_BOARD_SIZE];
	
	private Map<Character, String> legend = new HashMap<Character, String>();
	private Map<BoardCell, Set<BoardCell>> adjMatrix = new HashMap<BoardCell, Set<BoardCell>>();
	private Set<BoardCell> doorways = new HashSet<BoardCell>();
	
	private Set<BoardCell> targets = new HashSet<BoardCell>();
	
	private Set<BoardCell> visited = new HashSet<BoardCell>();
	
	private String boardConfigFile;
	private String roomConfigFile;
	
	// variable used for singleton pattern
	private static Board theInstance = new Board();
	// constructor is private to ensure only one can be created
	private Board() {}
	// this method returns the only Board
	public static Board getInstance() {
		return theInstance;
	}

	public void initialize() {
		for ( int i = 0; i < MAX_BOARD_SIZE; i++ ) {
			for ( int j = 0; j < MAX_BOARD_SIZE; j++ ) {
				BoardCell cell = new BoardCell();
				board[i][j] = cell;
			}
		}
		
		loadRoomConfig();
		loadBoardConfig();
		
	}
	public void loadRoomConfig() {
		
	}
	public void loadBoardConfig() {
		
	}
	public void calcAdjacencies() {
				
	}
	/**
	 * Calculate all targets from a given cell with a given number of steps
	 * @param cell
	 * @param pathLength
	 */
	
	public void calcTargets(BoardCell cell, int pathLength) {
		
	}
	public Map<Character, String> getLegend() {
		return legend;
	}
	
	/**
	 * Sets the respective ConfigFiles to the board and room referenced in the test
	 * @param board
	 * @param room
	 */
	
	public void setConfigFiles(String boardFile, String roomFile) {
		boardConfigFile = boardFile;
		roomConfigFile = roomFile;
		
	}
	
	public int getNumRows() {
		return numRows;
	}
	
	public int getNumColumns() {
		return numColumns;
	}
	
	public BoardCell getCellAt(int r, int c) {
		return board[r][c];
	}
	
}
