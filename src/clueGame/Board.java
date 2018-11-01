/**
 * @author Jay Harrison
 * @author Adam Kinard
 * 
 * This class governs the gameboard. Performs set up and calculates available moves.
 */
package clueGame;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import java.awt.Color; 
import java.lang.reflect.Field;

import clueGame.BoardCell;

import java.io.FileNotFoundException;
import java.io.FileReader;

public final class Board {

	public static final int MAX_BOARD_SIZE = 50;
	
	private int numRows;
	private int numColumns;
	
	private String boardConfigFile;
	private String roomConfigFile;
	private String playerConfigFile;
	private String weaponConfigFile;
	
	private BoardCell[][] board;
	
	private Map<BoardCell, Set<BoardCell>> adjMatrix;
	private Set<BoardCell> targets;
	private Set<BoardCell> visited;
	
	private Map<Character, String> legend;
	private Map<String,Player> playableChars;
	private ArrayList<String> weapons;
	
	private ArrayList<Card> deck;
	
	private Solution theSolution;
	
	// variable used for singleton pattern
	private static Board theInstance = new Board();
	
	// constructor is private to ensure only one can be created
	private Board() {
		board = new BoardCell[MAX_BOARD_SIZE][MAX_BOARD_SIZE];
		legend = new HashMap<Character, String>();
		adjMatrix = new HashMap<BoardCell, Set<BoardCell>>();
		targets = new HashSet<BoardCell>();
		visited = new HashSet<BoardCell>();
		playableChars = new HashMap<String,Player>();
		weapons = new ArrayList<String>();
		deck = new ArrayList<Card>();
		
	}
	
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
			// TODO: Add message
			e.printStackTrace();
		}
		try {
			loadPlayerConfig();
		} catch (BadConfigFormatException e2) {
			// TODO: Add message
			e2.printStackTrace();
		}
		try {
			loadWeaponConfig();
		} catch (BadConfigFormatException e3) {
			// TODO: Add message
			e3.printStackTrace();
		}
		
		//Calculates room adj
		calcAdjacencies();
		loadCardDeck();
		
		// Can't call when testing, will remove cards from deck permanently
		//dealCards();
	}
	

	/**
	 * Loads room configuration files, throws exception if error is found in file format
	 * @throws BadConfigFormatException
	 */
	public void loadRoomConfig() throws BadConfigFormatException {
		//Read in legend.txt one line at a time, storing vals into legend set
		try {
			FileReader file = new FileReader(roomConfigFile);
			
			Scanner in = new Scanner(file);
			
			//Split based on commas with limit equal to 2 commas
			while(in.hasNext()){
				String temp = in.nextLine();
				List<String> legendArray = Arrays.asList(temp.split(", "));
				
				String keyTemp = legendArray.get(0);
				char key = keyTemp.charAt(0);
				
				String value = legendArray.get(1);
				String cardType = legendArray.get(2);

				if(!cardType.equals("Other") && !cardType.equals("Card")) {
					// TODO: Add message, pass stuff
					throw new BadConfigFormatException();
				}
				legend.put(key, value);
			}
			
		}catch (FileNotFoundException e) {
			// TODO: Add message
			e.printStackTrace();
		}
	}
	
	/**
	 * Loads board configuration files, throws exception if error is found in file format
	 * @throws BadConfigFormatException
	 */
	public void loadBoardConfig() throws BadConfigFormatException {

		//Read in csv file one letter at a time,
		try {
			FileReader file = new FileReader(boardConfigFile);
			
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
				
				DoorDirection doorDirection;
				char roomInitial;
				
				for ( int colCount = 0; colCount < rowArray.size(); colCount++ ) {
					
					char roomChar = '?';
					roomInitial = rowArray.get(colCount).charAt(0);
					
					// TODO: Add message
					if (!legend.containsKey(roomInitial)) throw new BadConfigFormatException();
					
					if ( (rowArray.get(colCount).length() == 2) && rowArray.get(colCount).charAt(1) != 'N' ) {
						roomChar = rowArray.get(colCount).charAt(1);
					}
					
					switch (roomChar) {
					case 'R':
						doorDirection = DoorDirection.RIGHT;
						break;
					case 'L':
						doorDirection = DoorDirection.LEFT;
						break;
					case 'D':
						doorDirection = DoorDirection.DOWN;
						break;
					case 'U':
						doorDirection = DoorDirection.UP;
						break;
					default:
						doorDirection = DoorDirection.NONE;
						break;
					}
					
					BoardCell cell = new BoardCell(rowCount, colCount, roomInitial, doorDirection);
					board[rowCount][colCount] = cell;
				}
				rowCount++;
			}
			numRows = rowCount;
		} catch (FileNotFoundException e) {
			// TODO: Add message
			e.printStackTrace();
		}
	}
	
	public void loadPlayerConfig() throws BadConfigFormatException {

		try {
			FileReader file = new FileReader(playerConfigFile);
			Scanner in = new Scanner(file);
			
			//Split based on commas with limit equal to 2 commas
			while(in.hasNextLine()){
				String temp = in.nextLine();
				List<String> playerArray = Arrays.asList(temp.split(", "));
				
				String name = playerArray.get(0);
				String charColor = playerArray.get(1).toLowerCase();
				Color color = convertColor(charColor);
				
				
				// Switch for start loc
				int row = 0, col = 0; 
				switch( charColor ) {
					case "red":
						row = 0;
						col = 3;
						break;
					case "magenta":
						row = 0;
						col = 11;
						break;
					case "blue":
						row = 20;
						col = 13;
						break;
					case "green":
						row = 0;
						col = 18;
						break;
					case "yellow":
						row = 20;
						col = 17;
						break;
					case "white":
						row = 20;
						col = 5;
						break;
					default:
						break;
				}
						
				Player player = new Player(name, color, row, col);
				playableChars.put(playerArray.get(1), player);
			}
			
		}catch (FileNotFoundException e) {
			// TODO: Add message
			e.printStackTrace();
		}
	}
	
	/**
	 * Loads in weapons to weapon list
	 * @throws BadConfigFormatException
	 */
	public void loadWeaponConfig() throws BadConfigFormatException {

		try {
			FileReader file = new FileReader(weaponConfigFile);
			
			Scanner in = new Scanner(file);
			
			//Split based on commas with limit equal to 2 commas
			while(in.hasNextLine()){
				String weapon = in.nextLine();
				weapons.add(weapon);
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} // ADD FORMAT Exc
		
	}
	
	// Loads in cards from weapons list, legend, and player list
	public void loadCardDeck() {
		
		// Add all people
		Set<String> playerKeys = playableChars.keySet();
		for (String person : playerKeys) {
			String personName = playableChars.get(person).getPlayerName();
			Card playerCard = new Card(personName, CardType.PERSON);
			deck.add(playerCard);
			
		}
		
		// Add all rooms
		Set<Character> roomSet = legend.keySet();
		for (char initial : roomSet) {
			String roomName = legend.get(initial);
			Card roomCard = new Card(roomName, CardType.ROOM);
			deck.add(roomCard);
			
		}
		
		
		// Add all weapons
		for ( String weapon : weapons ) {
			Card weaponCard = new Card(weapon, CardType.WEAPON);
			deck.add(weaponCard);
		}
	}
	
	public void calcAdjacencies() {
		
		// calculate adjacent squares
		for (int row = 0; row < numRows; row++) {
			for (int col = 0; col < numColumns; col++) {

				// valid neighbor if x+1,x-1,y+1,y-1 are not negative or greater than row/col
				// store vals into temp set, put set into adjMtx under grid[r][c] 

				Set<BoardCell> temp = new HashSet<BoardCell>();
				
				//test row - 1 (up)
				if ( row-1 >= 0 ) {
					// handles going out of doorways
					if ( getCellAt(row-1, col).isWalkway() && getCellAt(row, col).isDoorway() ) {
						if ( getCellAt(row,col).getDoorDirection() == DoorDirection.UP ) {
							temp.add(board[row-1][col]);
						}
					} else if ( getCellAt(row-1, col).isWalkway() && !getCellAt(row,col).isRoom() ) {
						temp.add(board[row-1][col]);
					}
					// handles going in to doors
					if ( getCellAt(row-1, col).getDoorDirection() == DoorDirection.DOWN ) {
						temp.add(board[row-1][col]);
					}
				} 
				
				//test row + 1 (down)
				if ( row+1 < numRows ) {
					if ( getCellAt(row+1, col).isWalkway() && getCellAt(row, col).isDoorway() ) {
						if ( getCellAt(row, col).getDoorDirection() == DoorDirection.DOWN ) {
							temp.add(board[row+1][col]);
						}
					} else if ( getCellAt(row+1, col).isWalkway() && !getCellAt(row,col).isRoom() ) {
						temp.add(board[row+1][col]);
					}
					if ( getCellAt(row+1, col).getDoorDirection() == DoorDirection.UP ) {
						temp.add(board[row+1][col]);
					}
				} 
				
				//test col - 1 (left)
				if ( col-1 >= 0 ) {
					if ( getCellAt(row, col-1).isWalkway() && getCellAt(row, col).isDoorway() ) {
						if ( getCellAt(row, col).getDoorDirection() == DoorDirection.LEFT ) {
							temp.add(board[row][col-1]);
						}
					} else if ( getCellAt(row, col-1).isWalkway() && !getCellAt(row,col).isRoom() ) {
						temp.add(board[row][col-1]);
					}
					if ( getCellAt(row, col-1).getDoorDirection() == DoorDirection.RIGHT ) {
						temp.add(board[row][col-1]);
					}
				} 
				
				//test col + 1 (right)
				if ( col+1 < numColumns ) {
					if ( getCellAt(row, col+1).isWalkway() && getCellAt(row, col).isDoorway() ) {
						if ( getCellAt(row, col).getDoorDirection() == DoorDirection.RIGHT ) {
							temp.add(board[row][col+1]);
						}
					} else if ( getCellAt(row, col+1).isWalkway() && !getCellAt(row,col).isRoom() ) {
						temp.add(board[row][col+1]);
					}
					if ( getCellAt(row, col+1).getDoorDirection() == DoorDirection.LEFT ) {
						temp.add(board[row][col+1]);
					}
				} 

				// add temp set and board cell to map
				adjMatrix.put(board[row][col], temp);
			}
		}
	}
	
	/**
	 * Setup function to call findAllTargets
	 * @param row
	 * @param col
	 * @param moves
	 */
	public void calcTargets(int row, int col, int moves) {
		// clear visited/target lists
		targets.clear();
		visited.clear();
		
		// add starting cell so no backtracking
		visited.add(getCellAt(row,col));

		// call recursive function
		findAllTargets(row, col, moves);
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
	
	// Deals cards to all players, starting with solution
	public void dealCards() {
		
		String roomCard = new String();
		String personCard = new String();
		String weaponCard = new String();
		
		boolean hasRoom = false;
		boolean hasPerson = false;
		boolean hasWeapon = false;
		
		// 3 cards randomly to each player
		
		//while solution is not complete{
		//pick a random card from the deck
		
		while ( !hasRoom || !hasPerson || !hasWeapon ) {
			Card next = deck.get((int)(Math.random() * deck.size()));
			
			//assign solution based on card type for all
			if (!hasRoom && next.getCardType() == CardType.ROOM) {
				roomCard += next.getName();
				deck.remove(next);
				hasRoom = true;
			}
			else if ( !hasPerson  && next.getCardType() == CardType.PERSON) {
				personCard += next.getName();
				deck.remove(next);
				hasPerson = true;
			}
			else if ( !hasWeapon && next.getCardType() == CardType.WEAPON) {
				weaponCard += next.getName();
				deck.remove(next);
				hasWeapon = true;
			}
			
			theSolution = new Solution(roomCard, personCard, weaponCard);
			//move on to dealing deack if person, weapon and room are filled
		}
		
		// While deck is not empty, assign card at random to a player,
		// move on to next one. Removes card after assignment
		
		while ( deck.size() > 0 ) {
			// Random card
			Card random = deck.get((int)(Math.random() * deck.size()));

			// Random card to each person in cyclical order
			Set<String> playerKeys = playableChars.keySet();
			for (String person : playerKeys) {
				playableChars.get(person).getPlayerCards().add(random);
				deck.remove(random);
			}
		}
	}

	/**
	 * Color converter to use with player colors
	 * @param strColor
	 * @return
	 */
	public Color convertColor(String strColor) {
		Color color;
		try {
			// We can use reflection to convert the string to a color
			Field field = Class.forName("java.awt.Color").getField(strColor.trim());
			color = (Color)field.get(null);
		} catch (Exception e) {
			color = null; // Not defined
		}
		return color;
	}

	
	/**
	 * Get targets
	 * @return
	 */
	public Set<BoardCell> getTargets() {
		return targets;
	}
	
	/**
	 * Get legend
	 * @return
	 */
	public Map<Character, String> getLegend() {
		return legend;
	}
	
	/**
	 * Returns set of adjacent cells based on row, col
	 * @param row
	 * @param col
	 * @return
	 */
	public Set<BoardCell> getAdjList(int row, int col) {
		return adjMatrix.get(getCellAt(row,col));
	}
	
	/**
	 * Set board and room configuration files
	 * @param boardFile
	 * @param roomFile
	 */
	public void setConfigFiles(String boardFile, String roomFile, String playerFile, String weaponFile) {
		boardConfigFile = boardFile;
		roomConfigFile = roomFile;
		playerConfigFile = playerFile;
		weaponConfigFile = weaponFile;
	}
	
	/**
	 * Get player of 'color'
	 * @param color
	 * @return
	 */
	public Player getPlayer(String color) {
		return playableChars.get(color);
	}
	
	/**
	 * Get entire map of players
	 * @return
	 */
	public Map<String,Player> getPlayerMap() {
		return playableChars;
	}
	
	/**
	 * Get deck
	 * @return
	 */
	public ArrayList<Card> getCardDeck() {
		return deck;
	}
	
	/**
	 * Get ArrayList of weapons
	 * @return
	 */
	public ArrayList<String> getWeapons() {
		return weapons;
	}
	
	/**
	 * Get numRows
	 * @return
	 */
	public int getNumRows() {
		return numRows;
	}
	
	/**
	 * Get numColumns
	 * @return
	 */
	public int getNumColumns() {
		return numColumns;
	}
	
	/**
	 * Get the cell at row, col
	 * @param row
	 * @param col
	 * @return
	 */
	public BoardCell getCellAt(int row, int col) {
		return board[row][col];
	}
	
}
