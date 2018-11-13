/**
 * @author Jay Harrison
 * @author Adam Kinard
 * 
 * This class governs the gameboard. Performs set up and calculates available moves.
 */
package clueGame;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Graphics;
import java.lang.reflect.Field;

import clueGame.BoardCell;

import java.io.FileNotFoundException;
import java.io.FileReader;

public final class Board extends JPanel {

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
	private Map<String,Player> players; //Each playable chracter contains a Player attribute and Card(their hand)
	private Set<Card> weapons;
	private Set<Card> rooms;
	private Set<Card> people;
	
	
	private ArrayList<Card> deck;
	private ArrayList<Card> allCards;
	
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
		players = new HashMap<String, Player>();
		people = new HashSet<Card>();
		weapons = new HashSet<Card>();
		rooms = new HashSet<Card>();
		deck = new ArrayList<Card>();
		allCards = new ArrayList<Card>();
		
		//JPanel panel = new JPanel();
		//panel = paintComponent();
	}
	
	// this method returns the only Board
	public static Board getInstance() {
		return theInstance;
	}

	public void initialize() {
		
		try {
			loadRoomConfig();
		} catch (BadConfigFormatException e1) {
			e1.getMessage();
		}
		try {
			loadBoardConfig();
		} catch (BadConfigFormatException e) {
			e.getMessage();
		}
		try {
			loadPlayerConfig();
		} catch (BadConfigFormatException e2) {
			e2.getMessage();
		}
		try {
			loadWeaponConfig();
		} catch (BadConfigFormatException e3) {
			e3.getMessage();
		}
		
		//Calculates room adj
		calcAdjacencies();
		loadCardDeck();
		
		// Can't call when testing, will remove cards from deck permanently
		dealCards();
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
					throw new BadConfigFormatException();
				}
				legend.put(key, value);
				if(cardType.equals("Card")) rooms.add(new Card(value, CardType.ROOM));	
				
			}
			
		}catch (FileNotFoundException e) {
			e.getMessage();
		}
	}
	
	/**
	 * Loads board configuration file, throws exception if error is found in file format
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
					
					char roomChar;
					roomInitial = rowArray.get(colCount).charAt(0);
					
					// TODO: Add message
					if (!legend.containsKey(roomInitial)) throw new BadConfigFormatException();
					
					if ( (rowArray.get(colCount).length() == 2) && rowArray.get(colCount).charAt(1) != 'N' ) {
						roomChar = rowArray.get(colCount).charAt(1);
					}
					else {
						// Random char so D or R don't register as doors
						roomChar = '_';
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
					board[rowCount][colCount] = new BoardCell(rowCount, colCount, roomInitial, doorDirection);
				}
				rowCount++;
			}
			numRows = rowCount;
		} catch (FileNotFoundException e) {
			System.out.println("There was an error with the boardConfigFile");
			e.getMessage();
		}
	}
	
	/**
	 * Loads player config file, throws exception if error is found in file format
	 * @throws BadConfigFormatException
	 */
	public void loadPlayerConfig() throws BadConfigFormatException {

		try {
			FileReader file = new FileReader(playerConfigFile);
			Scanner in = new Scanner(file);
			
			//Split based on commas with limit equal to 2 commas
			while(in.hasNextLine()){
				String temp = in.nextLine();
				List<String> playerArray = Arrays.asList(temp.split(", "));
				
				String name = playerArray.get(0);
				String color = playerArray.get(1).toLowerCase();
				
				// Switch for start loc
				int row = 0, col = 0; 
				switch( color ) {
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
				players.put(color, player);
				people.add(new Card(name, CardType.PERSON));
			}
			
		}catch (FileNotFoundException e) {
			e.getMessage();
		}
	}
	
	/**
	 * Loads in weapons to weapon list, throws exception if error is found in file format
	 * @throws BadConfigFormatException
	 */
	public void loadWeaponConfig() throws BadConfigFormatException {

		try {
			FileReader file = new FileReader(weaponConfigFile);
			
			Scanner in = new Scanner(file);
			
			//Split based on commas with limit equal to 2 commas
			while(in.hasNextLine()){
				String weapon = in.nextLine();
				weapons.add(new Card(weapon, CardType.WEAPON));
			}
			
		} catch (FileNotFoundException e) {
			e.getMessage();
		}
		
	}
	
	// Loads in cards from weapons list, legend, and player list
	public void loadCardDeck() {
		
		// Add all people
			deck.addAll(people);
			allCards.addAll(people);
		
		// Add all rooms
			deck.addAll(rooms);
			allCards.addAll(rooms);
		
		
		// Add all weapons
			deck.addAll(weapons);
			allCards.addAll(weapons);
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
		
		Card roomSolution;
		Card personSolution;
		Card weaponSolution;
		
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
		}

		roomSolution = new Card(roomCard, CardType.ROOM);
		personSolution = new Card(personCard, CardType.PERSON);
		weaponSolution = new Card(weaponCard, CardType.WEAPON);

		setSolution(new Solution(roomSolution, personSolution, weaponSolution));
		//move on to dealing deack if person, weapon and room are filled


		// While deck is not empty, assign card at random to a player,
		// move on to next one. Removes card after assignment
		
		while ( deck.size() > 0 ) {
			// Random card
			Card random = deck.get((int)(Math.random() * deck.size()));

			// Random card to each person in cyclical order
			Set<String> playerKeys = players.keySet();
			for (String person : playerKeys) {
				players.get(person).getHand().add(random);
				deck.remove(random);
			}
		}
		
	}

	/**
	 * Handles suggestions made by players and returns any card offered from other players
	 * @param suggestor
	 * @param suggestion
	 * @param players
	 * @return
	 */
	public Card handleSuggestion(Player suggestor, Solution suggestion, ArrayList<Player> players) {
		
		// Starting and ending location
		int endLoc = players.indexOf(suggestor);
		int curLoc = endLoc + 1;
		
		// wrapping loop for players
		while ( curLoc != endLoc ) {
			if (curLoc >= players.size() ) {
				curLoc = 0;
			}
			
			// if player can disprove, return card
			Player player = players.get(curLoc);
			Card proof = player.disproveSuggestion(suggestion);
			
			if ( proof != null ) {
				return proof;
			}
			// else go to next player
			curLoc++;
		}
		// if reach the suggestor, return null	
		return null;
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
		return players.get(color);
	}
	
	/**
	 * Get entire map of players
	 * @return
	 */
	public Map<String,Player> getPlayerMap() {
		return players;
	}
	
	/**
	 * Get all Players
	 * @return
	 */
	public Set<Card> getPeople(){
		return people;
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
	public Set<Card>getWeapons() {
		return weapons;
	}
	
	/**
	 * Get room with initial 'i'
	 * @param initial
	 * @return
	 */
	public Card getRoomWithInitial(char initial) {
		String roomName = legend.get(initial);
		for (Card r : rooms) {
			if (r.getName().equals(roomName)) {
				return r;
			}
		}
		return null;
	}
	
	/**
	 * Return players that are controlled by Computer
	 * @return
	 */
	public ArrayList<ComputerPlayer> getComputerPlayers(){
		ArrayList<ComputerPlayer> compPlayers = new ArrayList<ComputerPlayer>();
		
		return compPlayers;
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
	
	/**
	 * Sets the solution
	 * @param theSolution
	 */
	public void setSolution(Solution theSolution) {
		this.theSolution = theSolution;
	}
	
	/**
	 * Gets the solution
	 * @return
	 */
	public Solution getSolution() {
		return theSolution;
	}

	/**
	 * Checks the given accusation with the actual solution
	 * @param accusation
	 * @return
	 */
	public boolean checkAccusaton(Solution accusation) {
		return accusation.equals(theSolution);
	}

	/**
	 * Gets list of all cards, seperate from the deck
	 * @return
	 */
	public ArrayList<Card> getAllCards() {
		return allCards;
	}
	
	/**
	 * Get a card based on name given, must match name or given null
	 * @param name
	 * @return
	 */
	public Card getSpecificCard(String name) {
		for ( Card c : allCards ) {
			if ( c.getName().equals(name) ) {
				return c;
			}
		}
		// Only returns if name does not match
		return null;
	}
	
	/**
	 * Swing method for drawing
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		//Draw frame around board
		g.setColor(Color.BLACK);
		g.drawRect(0, 0, 25 * numColumns, 25 * numRows);

		//print each boardcell
		for (int row = 0; row < getNumRows(); row++) { //numRows is 0 tho
			for(int col = 0; col < getNumColumns(); col++) { //numColumns is 0 tho
				getCellAt(row, col).drawCell(g);
			}
		}
		
		//print room names
		g.setColor(Color.BLUE);
		g.drawString("CINEMA", 15, 70);
		g.drawString("CREMATORIUM", 152, 40);
		g.drawString("TOOL CLOSET", 335, 40);
		g.drawString("HYDRO-", 490, 70);
		g.drawString("PONICS LAB", 477, 85);
		g.drawString("BALLROOM", 15, 285);
		g.drawString("KITCHEN", 25, 475);
		g.drawString("DINING ROOM", 185, 475);
		g.drawString("PLANET-", 365, 465);
		g.drawString("ARIUM", 370, 480);
		g.drawString("REPTILE ROOM", 455, 375);
		
		//print Players
		Collection<Player> playerKeys = players.values();
		for (Player person : playerKeys) {
			person.drawPlayer(g);
		}
	}

}

	

