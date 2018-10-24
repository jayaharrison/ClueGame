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
	
	/**
	 * Get room initial
	 * @return
	 */
	public char getInitial() {
		return initial;
	}
	
	/**
	 * Get door direction
	 * @return
	 */
	public DoorDirection getDoorDirection() {
		return direction;
	}
	
	/**
	 * Get row val
	 * @return
	 */
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
	
	/**
	 * Get column val
	 * @return
	 */
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
	
	/**
	 * Walkway boolean
	 * @return
	 */
	public boolean isWalkway() {
		return (initial == 'W');
	}
	
	/**
	 * Room boolean
	 * @return
	 */
	public boolean isRoom() {
		return (initial != 'W' && initial != 'X');
	}
	
	/**
	 * Doorway boolean
	 * @return
	 */
	public boolean isDoorway() {
		return (direction != DoorDirection.NONE);
	}
	
	@Override
	public String toString() {
		return "(" + row + ", " + column + ", " + initial + ", " + direction + ")";
	}

	
	
}