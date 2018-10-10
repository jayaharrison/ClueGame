package clueGame;
/**
 * 
 * @author Adam Kinard
 * @author Jay Harrison
 *
 *This class represents one cell in the grid.
 *Includes all member variables of the cells along with various test methods and getters/setters
 *
 */


public class BoardCell {
	private int row = 0;
	private int column = 0;
	private char initial = '.';
	private DoorDirection direction;
	
	public BoardCell() {
		row = 0;
		column = 0;
	}
	
	/**
	 * Constructor for new board cell including row, column, room initial, and door direction params
	 * @param row
	 * @param column
	 * @param initial
	 * @param direction
	 */
	public BoardCell(int row, int column, char initial, DoorDirection direction) {
		super();
		this.row = row;
		this.column = column;
		this.initial = initial;
		this.direction = direction;
	}
	
	/**
	 * BoardCell constructor for room and column
	 * @param row
	 * @param column
	 */
	public BoardCell(int row, int column) {
		super();
		this.row = row;
		this.column = column;
	}
	
	public char getInitial() {
		return initial;
	}

	public int getRow() {
		return row;
	}

	/**
	 * Set row val
	 * @param row
	 */
	public void setRow(int row) {
		this.row = row;
	}

	public int getColumn() {
		return column;
	}

	/**
	 * Set column val
	 * @param column
	 */
	public void setColumn(int column) {
		this.column = column;
	}
	
	public boolean isWalkway() {
		if (initial == 'W') return true;
		else return false;
	}
	
	public boolean isRoom() {
		if(initial == 'W' || initial == 'X') return false;
		else return true;
	}
	
	public boolean isDoorway() {
		if (direction == DoorDirection.NONE) return false;
		else return true;
	}

	@Override
	public String toString() {
		return "BoardCell [row=" + row + ", column=" + column + ", initial=" + initial + ", direction=" + direction
				+ "]";
	}

	public DoorDirection getDoorDirection() {
		return direction;
	}
	
}