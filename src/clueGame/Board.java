/**
 * @author Jay Harrison
 * @author Adam Kinard
 * 
 * This class governs the gameboard. Performs set up and calculates available moves.
 */
package clueGame;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import clueGame.BoardCell;

import java.io.*;

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
		
		try {
			loadRoomConfig();
		} catch (BadConfigFormatException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			loadBoardConfig();
		} catch (BadConfigFormatException e) {
			e.printStackTrace();
		}
		
		//Calculates room adj
		calcAdjacencies();
	}
	
	/**
	 * Loads room configuration files, throws exception if error is found in file format
	 * @throws BadConfigFormatException
	 */
	public void loadRoomConfig() throws BadConfigFormatException {
		//Read in legend.txt one line at a time, storing vals into legend set
		FileReader file = null;
		try {
			file = new FileReader(roomConfigFile);
		}catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		Scanner in = new Scanner(file);
		//Split based on commas with limit equal to 2 commas
		while(in.hasNext()){
			String temp = in.nextLine();
			List<String> legendArray = Arrays.asList(temp.split(", "));
			
			String keyTemp = legendArray.get(0);
			char key = keyTemp.charAt(0);
			
			String value = legendArray.get(1);
			String cardType = legendArray.get(2);
			//System.out.println("Card: '" + cardType + "'");
			if(!cardType.equals("Other") && !cardType.equals("Card")) {
				throw new BadConfigFormatException();
			}
			legend.put(key, value);
		}
		
	}
	
	/**
	 * Loads board configuration files, throws exception if error is found in file format
	 * @throws BadConfigFormatException
	 */
	public void loadBoardConfig() throws BadConfigFormatException {

		//Read in csv file one letter at a time,
		FileReader file = null;
		try {
			file = new FileReader(boardConfigFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		Scanner in = new Scanner(file);
		int rowCount = 0;
		
		int max = 0;
		
		while(in.hasNext()) {
			String temp = in.nextLine();
			List<String> rowArray = Arrays.asList(temp.split(","));
			numColumns = rowArray.size();
			
			if ( rowArray.size() < max ) {
				throw new BadConfigFormatException();
			} else {
				max = rowArray.size();
			}
			
			char init;
			char roomChar = '?';
			DoorDirection d;
			
			for ( int i =0; i < rowArray.size(); i++ ) {
				init = rowArray.get(i).charAt(0);
				//int colCount = 0;
				System.out.println(init);
				if (!legend.containsKey(init)) throw new BadConfigFormatException();
				if ( (rowArray.get(i).length() == 2) && rowArray.get(i).charAt(1) != 'N' ) {
					roomChar = rowArray.get(i).charAt(1);
				}
				
				
				switch (roomChar) {
				case 'R':
					d = DoorDirection.RIGHT;
					break;
				case 'L':
					d = DoorDirection.LEFT;
					break;
				case 'D':
					d = DoorDirection.DOWN;
					break;
				case 'U':
					d = DoorDirection.UP;
					break;

				default:
					d = DoorDirection.NONE;
					break;
				}
				
				roomChar = '?';
				BoardCell cell = new BoardCell(rowCount, i, init, d);
				board[rowCount][i] = cell;
				
				if (board[rowCount][i].getDoorDirection() != DoorDirection.NONE) {
					doorways.add(board[rowCount][i]);
				}
				//colCount++;
				//if(numColumns != 0 && colCount != numColumns) throw new BadConfigFormatException();
				//numColumns = colCount;
			}
			rowCount++;
		}
		numRows = rowCount;
	}
	
	public void calcAdjacencies() {
		
		// calculate adjacent squares
		for (int i = 0; i < numRows; i++) {
			for (int j = 0; j < numColumns; j++) {

				// valid neighbor if x+1,x-1,y+1,y-1 are not negative or greater than row/col
				// store vals into temp set, put set into adjMtx under grid[r][c] 

				Set<BoardCell> temp = new HashSet<BoardCell>();
				
				//test row - 1 (up)
				if ( i-1 >= 0 ) {
					// handles going out of doorways
					if ( getCellAt(i-1, j).isWalkway() && getCellAt(i, j).isDoorway() ) {
						if ( getCellAt(i,j).getDoorDirection() == DoorDirection.UP ) {
							temp.add(board[i-1][j]);
						}
					} else if ( getCellAt(i-1, j).isWalkway() && !getCellAt(i,j).isRoom() ) {
						temp.add(board[i-1][j]);
					}
					// handles going in to doors
					if ( getCellAt(i-1, j).getDoorDirection() == DoorDirection.DOWN ) {
						temp.add(board[i-1][j]);
					}
				} 
				
				//test row + 1 (down)
				if ( i+1 < numRows ) {
					if ( getCellAt(i+1, j).isWalkway() && getCellAt(i, j).isDoorway() ) {
						if ( getCellAt(i, j).getDoorDirection() == DoorDirection.DOWN ) {
							temp.add(board[i+1][j]);
						}
					} else if ( getCellAt(i+1, j).isWalkway() && !getCellAt(i,j).isRoom() ) {
						temp.add(board[i+1][j]);
					}
					if ( getCellAt(i+1, j).getDoorDirection() == DoorDirection.UP ) {
						temp.add(board[i+1][j]);
					}
				} 
				
				//test col - 1 (left)
				if ( j-1 >= 0 ) {
					if ( getCellAt(i, j-1).isWalkway() && getCellAt(i, j).isDoorway() ) {
						if ( getCellAt(i, j).getDoorDirection() == DoorDirection.LEFT ) {
							temp.add(board[i][j-1]);
						}
					} else if ( getCellAt(i, j-1).isWalkway() && !getCellAt(i,j).isRoom() ) {
						temp.add(board[i][j-1]);
					}
					if ( getCellAt(i, j-1).getDoorDirection() == DoorDirection.RIGHT ) {
						temp.add(board[i][j-1]);
					}
				} 
				
				//test col + 1 (right)
				if ( j+1 < numColumns ) {
					if ( getCellAt(i, j+1).isWalkway() && getCellAt(i, j).isDoorway() ) {
						if ( getCellAt(i, j).getDoorDirection() == DoorDirection.RIGHT ) {
							temp.add(board[i][j+1]);
						}
					} else if ( getCellAt(i, j+1).isWalkway() && !getCellAt(i,j).isRoom() ) {
						temp.add(board[i][j+1]);
					}
					if ( getCellAt(i, j+1).getDoorDirection() == DoorDirection.LEFT ) {
						temp.add(board[i][j+1]);
					}
				} 

				// add temp set and board cell to map
				adjMatrix.put(board[i][j], temp);
			}
		}
	}
	
	/**
	 * Returns set of adjacent cells based on row, col
	 * @param r
	 * @param c
	 * @return
	 */
	public Set<BoardCell> getAdjList(int r, int c) {
		return adjMatrix.get(getCellAt(r,c));
	}
	
	/**
	 * Calculates all targets from a given cell with a given number of steps, calls findAllTargets
	 * @param cell
	 * @param pathLength
	 */
	public void calcTargets(int r, int c, int s) {
		// clear visited/target lists
		targets.clear();
		visited.clear();

		// add starting cell so no backtracking
		visited.add(getCellAt(r,c));

		// call recursive function
		findAllTargets(r, c, s);
	}
	
	/**
	 * Recursive function to find targets from specified cell with specified moves
	 * @param row
	 * @param col
	 * @param moves
	 */
	private void findAllTargets(int row, int col, int moves) {
		
		for ( BoardCell currentCell : getAdjList(row, col) ) {
			if ( !(visited.contains(currentCell) ) ) {
				
				// add currentCell to visited
				visited.add(currentCell);
				
				if ( moves == 1 || currentCell.isDoorway()) {
					targets.add(currentCell);
				}
				else {
					findAllTargets(currentCell.getRow(), currentCell.getColumn(), moves-1);
				}
				// remove current cell from visited
				visited.remove(currentCell);
			}
		}
	}
	
	public Set<BoardCell> getTargets() {
		return targets;
	}
	
	public Map<Character, String> getLegend() {
		return legend;
	}
	
	/**
	 * Sets the respective configuration files to the board and room referenced in the test
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
	
	/**
	 * Returns cell at given row, col
	 * @param r
	 * @param c
	 * @return
	 */
	public BoardCell getCellAt(int r, int c) {
		return board[r][c];
	}
	
}
