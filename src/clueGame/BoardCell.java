/**
 * 
 * @author Adam Kinard
 * @author Jay Harrison
 *
 *This class represents one cell in the grid.
 *Includes all member variables of the cells along with various test methods and getters/setters
 *
 */

package clueGame;

public class BoardCell {
	private int row;
	private int column;
	private char initial;
	private DoorDirection direction;
	
	public BoardCell() {
		row = 0;
		column = 0;
		initial = ' ';
		direction = DoorDirection.NONE;
	}
	
	/**
	 * Constructor for new board cell including row, column, room initial, and door direction params
	 * @param row
	 * @param column
	 * @param initial
	 * @param direction
	 */
	public BoardCell(int row, int column, char initial, DoorDirection direction) {
		this.row = row;
		this.column = column;
		this.initial = initial;
		this.direction = direction;
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
		if (this == null) return false;
		if (direction == DoorDirection.NONE) return false;
		else return true;
	}

	@Override
	public String toString() {
		return "(" + row + ", " + column + ", " + initial + ", " + direction + ")";
	}

	public DoorDirection getDoorDirection() {
		return direction;
	}
	
}