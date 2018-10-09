package clueGame;
/**
 * 
 * @author Adam Kinard
 * @author Jay Harrison
 *
 *This class represents one cell in the grid.
 *It includes two member variables of type int to represent the row and column
 *
 */

public class BoardCell {
	int row = 0;
	int column = 0;
	
	BoardCell() {
		super();
		this.row = 0;
		this.column = 0;
	}
	
	/**
	 * Constructor for new board cell including row and column params
	 * @param row
	 * @param column
	 */
	BoardCell(int row, int column) {
		super();
		this.row = row;
		this.column = column;
	}


	@Override
	public String toString() {
		return "(" + row + ", " + column + ")";
	}
}