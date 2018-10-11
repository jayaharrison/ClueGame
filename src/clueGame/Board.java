package clueGame;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.io.*;

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
		
		try {
			loadRoomConfig();
		} catch (BadConfigFormatException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			loadBoardConfig();
		} catch (BadConfigFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
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
